package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1139TheForestOutlaw extends QuestHandler {
    private static final int questId = 1139;
    private static final int[] mob_ids = new int[] { 210138, 210140 };

    public _1139TheForestOutlaw() {
        super(Integer.valueOf(1139));
    }

    public void register() {
        this.qe.setNpcQuestData(203124).addOnTalkEvent(1139);
        this.qe.addQuestLvlUp(1139);
        for (int mob_id : mob_ids) {
            this.qe.setNpcQuestData(mob_id).addOnKillEvent(1139);
        }
    }

    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1139);
        boolean lvlCheck = QuestService.checkLevelRequirement(1139, player.getCommonData().getLevel());
        if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
            return false;
        qs.setStatus(QuestStatus.START);
        updateQuestStatus(player, qs);
        return true;
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1139);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs.getStatus() == QuestStatus.START) {

            if (targetId == 203124) {
                switch (env.getDialogId().intValue()) {

                    case 25:
                        if (var == 0)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                    case 10000:
                    case 10001:
                        if (var == 0) {

                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                        }
                        break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 203124)
                return defaultQuestEndDialog(env);
        }
        return false;
    }

    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1139);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        switch (targetId) {

            case 210138:
                if (var >= 1 && var <= 8) {

                    qs.setQuestVarById(0, var + 1);
                    updateQuestStatus(player, qs);
                    return true;
                }
                if (var == 9) {

                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return true;
                }
            case 210140:
                if (var >= 1 && var <= 4) {

                    qs.setQuestVarById(0, var + 1);
                    updateQuestStatus(player, qs);
                    return true;
                }
                if (var == 5) {

                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return true;
                }
                break;
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1139TheForestOutlaw.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
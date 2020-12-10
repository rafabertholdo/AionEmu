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

public class _1022KrallDesecration extends QuestHandler {
    private static final int questId = 1022;
    private static final int[] mob_ids = new int[] { 210178 };

    public _1022KrallDesecration() {
        super(Integer.valueOf(1022));
    }

    public void register() {
        this.qe.setNpcQuestData(203178).addOnTalkEvent(1022);
        this.qe.addQuestLvlUp(1022);
        for (int mob_id : mob_ids) {
            this.qe.setNpcQuestData(mob_id).addOnKillEvent(1022);
        }
    }

    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1022);
        boolean lvlCheck = QuestService.checkLevelRequirement(1022, player.getCommonData().getLevel());
        if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
            return false;
        QuestState qs2 = player.getQuestStateList().getQuestState(1017);
        if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
            return false;
        qs.setStatus(QuestStatus.START);
        updateQuestStatus(player, qs);
        return true;
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1022);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs.getStatus() == QuestStatus.START) {

            if (targetId == 203178) {
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

            if (targetId == 203178)
                return defaultQuestEndDialog(env);
        }
        return false;
    }

    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1022);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
            /*     */ }
        /* 120 */ int var = qs.getQuestVarById(0);
        /* 121 */ int targetId = 0;
        /* 122 */ if (env.getVisibleObject() instanceof Npc)
            /* 123 */ targetId = ((Npc) env.getVisibleObject()).getNpcId();
        /* 124 */ switch (targetId) {
            /*     */
            /*     */ case 210178:
                /* 127 */ if (var >= 1 && var <= 4) {
                    /*     */
                    /* 129 */ qs.setQuestVarById(0, var + 1);
                    /* 130 */ updateQuestStatus(player, qs);
                    /* 131 */ return true;
                    /*     */ }
                /* 133 */ if (var == 5) {
                    /*     */
                    /* 135 */ qs.setStatus(QuestStatus.REWARD);
                    /* 136 */ updateQuestStatus(player, qs);
                    /* 137 */ return true;
                    /*     */ }
                break;
            /*     */ }
        /* 140 */ return false;
        /*     */ }
    /*     */ }

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1022KrallDesecration.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
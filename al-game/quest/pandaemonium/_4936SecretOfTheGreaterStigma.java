package quest.pandaemonium;

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

public class _4936SecretOfTheGreaterStigma extends QuestHandler {
    private static final int questId = 4936;
    private static final int[] npc_ids = new int[] { 204051, 204837 };

    public _4936SecretOfTheGreaterStigma() {
        super(Integer.valueOf(4936));
    }

    public void register() {
        this.qe.setNpcQuestData(204051).addOnQuestStart(4936);
        for (int npc_id : npc_ids) {
            this.qe.setNpcQuestData(npc_id).addOnTalkEvent(4936);
        }
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(4936);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {

            if (targetId == 204051) {

                if (env.getDialogId().intValue() == 25)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                return defaultQuestStartDialog(env);
            }

            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 204051) {
                return defaultQuestEndDialog(env);
            }
            return false;
        }
        if (qs.getStatus() == QuestStatus.START) {

            if (targetId == 204051 && var == 1) {

                switch (env.getDialogId().intValue()) {

                    case 25:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                    case 33:
                        if (QuestService.collectItemCheck(env, true)) {

                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(player, qs);
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                        }

                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
                }

            } else if (targetId == 204837 && var == 0) {

                switch (env.getDialogId().intValue()) {

                    case 25:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                    case 10000:
                        PacketSendUtility.sendPacket(player,
                                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(player, qs);
                        return true;
                }

            }
            return false;
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\pandaemonium\_4936SecretOfTheGreaterStigma.class Java compiler
 * version: 6 (50.0) JD-Core Version: 1.1.3
 */

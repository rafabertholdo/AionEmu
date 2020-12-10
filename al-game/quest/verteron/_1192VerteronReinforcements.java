package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1192VerteronReinforcements extends QuestHandler {
    private static final int questId = 1192;

    public _1192VerteronReinforcements() {
        super(Integer.valueOf(1192));
    }

    public void register() {
        this.qe.setNpcQuestData(203098).addOnQuestStart(1192);
        this.qe.setNpcQuestData(203098).addOnTalkEvent(1192);
        this.qe.setNpcQuestData(203701).addOnTalkEvent(1192);
        this.qe.setNpcQuestData(203833).addOnTalkEvent(1192);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1192);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 203098) {

                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                }

                return defaultQuestStartDialog(env);
            }
        }

        if (qs == null) {
            return false;
        }
        if (qs.getStatus() == QuestStatus.START) {

            switch (targetId) {

                case 203701:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);

                        case 10000:
                            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                    }

                case 203833:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);

                        case 10001:
                            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                    }

                    return defaultQuestEndDialog(env);

                case 203098:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);

                        case 1009:
                            qs.setQuestVar(3);
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                    }

                    return defaultQuestEndDialog(env);
            }

        } else if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 203098) {

                if (env.getDialogId().intValue() == 1009) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                }
                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }
}

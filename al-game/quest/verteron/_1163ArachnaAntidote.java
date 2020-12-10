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

public class _1163ArachnaAntidote extends QuestHandler {
    private static final int questId = 1163;

    public _1163ArachnaAntidote() {
        super(Integer.valueOf(1163));
    }

    public void register() {
        this.qe.setNpcQuestData(203096).addOnQuestStart(1163);
        this.qe.setNpcQuestData(203096).addOnTalkEvent(1163);
        this.qe.setNpcQuestData(203151).addOnTalkEvent(1163);
        this.qe.setNpcQuestData(203155).addOnTalkEvent(1163);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1163);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 203096) {

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

                case 203151:
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

                    return defaultQuestEndDialog(env);

                case 203155:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);

                        case 1009:
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                    }

                    return defaultQuestEndDialog(env);
            }

        } else if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 203155) {

                if (env.getDialogId().intValue() == 1009) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                }
                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1163ArachnaAntidote.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
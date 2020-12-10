package quest.pandaemonium;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2096TwiceasBright extends QuestHandler {
    private static final int questId = 2096;

    public _2096TwiceasBright() {
        super(Integer.valueOf(2096));
    }

    public void register() {
        this.qe.addQuestLvlUp(2096);
        this.qe.setNpcQuestData(204206).addOnQuestStart(2096);
        this.qe.setNpcQuestData(204206).addOnTalkEvent(2096);
        this.qe.setNpcQuestData(204207).addOnTalkEvent(2096);
        this.qe.setNpcQuestData(203550).addOnTalkEvent(2096);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(2096);
        if (targetId == 204206) {

            if (qs != null && qs.getStatus() == QuestStatus.START) {
                if (env.getDialogId().intValue() == 25)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                if (env.getDialogId().intValue() == 10000) {

                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(player, qs);
                    PacketSendUtility.sendPacket(player,
                            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                    return true;
                }

                return defaultQuestStartDialog(env);
            }

        } else if (targetId == 204207) {

            if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
                if (env.getDialogId().intValue() == 25)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                if (env.getDialogId().intValue() == 10001) {

                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(player, qs);
                    PacketSendUtility.sendPacket(player,
                            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                    return true;
                }

                return defaultQuestStartDialog(env);

            }

        } else if (targetId == 203550) {

            if (qs != null && qs.getStatus() == QuestStatus.REWARD) {

                if (env.getDialogId().intValue() == 25)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                if (env.getDialogId().intValue() == 1009) {

                    qs.setQuestVar(3);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return defaultQuestEndDialog(env);
                }

                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }

    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(2096);
        if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
            return false;
        int[] quests = { 2007, 2022, 2041, 2094, 2061, 2076, 2900 };
        for (int id : quests) {

            QuestState qs2 = player.getQuestStateList().getQuestState(id);
            if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
                return false;
            }
        }
        qs.setStatus(QuestStatus.START);
        updateQuestStatus(player, qs);
        return true;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\pandaemonium\_2096TwiceasBright.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
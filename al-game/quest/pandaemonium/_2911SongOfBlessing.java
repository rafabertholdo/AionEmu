package quest.pandaemonium;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

public class _2911SongOfBlessing extends QuestHandler {
    private static final int questId = 2911;

    public _2911SongOfBlessing() {
        super(Integer.valueOf(2911));
    }

    public void register() {
        this.qe.setNpcQuestData(204079).addOnQuestStart(2911);
        this.qe.setNpcQuestData(204079).addOnTalkEvent(2911);
        this.qe.setNpcQuestData(204193).addOnTalkEvent(2911);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(2911);
        if (targetId == 204079) {

            if (qs == null || qs.getStatus() == QuestStatus.NONE) {
                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                }
                return defaultQuestStartDialog(env);
            }

        } else if (targetId == 204193) {

            if (qs != null) {

                if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                if (env.getDialogId().intValue() == 10000) {

                    qs.setQuestVar(2);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                }
                if (env.getDialogId().intValue() == 10001) {

                    qs.setQuestVar(3);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
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
 * !\quest\pandaemonium\_2911SongOfBlessing.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */
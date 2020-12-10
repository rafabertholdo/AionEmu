package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1157GaphyrksLove extends QuestHandler {
    private static final int questId = 1157;

    public _1157GaphyrksLove() {
        super(Integer.valueOf(1157));
    }

    public void register() {
        this.qe.setNpcQuestData(798003).addOnQuestStart(1157);
        this.qe.setNpcQuestData(798003).addOnTalkEvent(1157);
        this.qe.setNpcQuestData(210319).addOnAttackEvent(1157);
        this.qe.setQuestMovieEndIds(17).add(1157);
    }

    public boolean onAttackEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1157);

        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        if (targetId != 210319) {
            return false;
        }
        Npc npc = (Npc) env.getVisibleObject();

        if (MathUtil.getDistance(892.0F, 2024.0F, 166.0F, npc.getX(), npc.getY(), npc.getZ()) > 13.0D) {
            return false;
        }
        npc.getController().onDespawn(true);
        npc.getController().scheduleRespawn();
        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 17));
        return true;
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1157);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {

            if (targetId == 798003) {
                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                }
                return defaultQuestStartDialog(env);
            }

        } else if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 798003) {

                if (env.getDialogId().intValue() == -1)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                if (env.getDialogId().intValue() == 1009)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                return defaultQuestEndDialog(env);
            }
            return false;
        }
        return false;
    }

    public boolean onMovieEndEvent(QuestEnv env, int movieId) {
        if (movieId != 17)
            return false;
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1157);
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        return true;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1157GaphyrksLove.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */

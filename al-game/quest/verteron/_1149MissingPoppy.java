package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1149MissingPoppy extends QuestHandler {
    private static final int questId = 1149;

    public _1149MissingPoppy() {
        super(Integer.valueOf(1149));
    }

    public void register() {
        this.qe.setNpcQuestData(203191).addOnQuestStart(1149);
        this.qe.setNpcQuestData(203145).addOnQuestStart(1149);
        this.qe.setNpcQuestData(203145).addOnTalkEvent(1149);
        this.qe.setNpcQuestData(203191).addOnTalkEvent(1149);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1149);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 203145) {

                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                }
                return defaultQuestStartDialog(env);
            }
        }

        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 203145) {

                if (env.getDialogId().intValue() == 1009) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                }
                return defaultQuestEndDialog(env);
            }
            return false;
        }
        if (qs.getStatus() == QuestStatus.START) {
            Npc npc;
            switch (targetId) {

                case 203191:
                    switch (env.getDialogId().intValue()) {

                        case -1:
                            if (var == 1) {

                                Npc npc1 = (Npc) env.getVisibleObject();
                                if (MathUtil.getDistance(1255.0F, 2223.0F, 144.0F, npc1.getX(), npc1.getY(),
                                        npc1.getZ()) > 5.0D) {

                                    if (!npc1.getMoveController().isScheduled())
                                        npc1.getMoveController().schedule();
                                    npc1.getMoveController().setFollowTarget(true);
                                    return true;
                                }

                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 12));
                                npc1.getController().onDie(null);
                                npc1.getController().onDespawn(false);
                                return true;
                            }
                        case 25:
                            if (var == 0)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                        case 10000:
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            npc = (Npc) env.getVisibleObject();
                            npc.getMoveController().setDistance(4.0F);
                            npc.getMoveController().setFollowTarget(true);
                            npc.getMoveController().schedule();
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));

                            return true;
                    }
                    break;
            }
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1149MissingPoppy.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
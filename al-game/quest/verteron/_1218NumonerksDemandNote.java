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

public class _1218NumonerksDemandNote extends QuestHandler {
    private static final int questId = 1218;

    public _1218NumonerksDemandNote() {
        super(Integer.valueOf(1218));
    }

    public void register() {
        this.qe.setNpcQuestData(203121).addOnQuestStart(1218);
        this.qe.setNpcQuestData(203121).addOnTalkEvent(1218);
        this.qe.setNpcQuestData(798004).addOnTalkEvent(1218);
        this.qe.setNpcQuestData(203172).addOnTalkEvent(1218);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(1218);
        if (targetId == 203121) {

            if (qs == null) {
                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                }
                return defaultQuestStartDialog(env);
            }

        } else if (targetId == 798004) {

            if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
                if (env.getDialogId().intValue() == 25)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                if (env.getDialogId().intValue() == 10000) {

                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(player, qs);
                    PacketSendUtility.sendPacket(player,
                            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                    return true;
                }

                return defaultQuestStartDialog(env);
            }

        } else if (targetId == 203172) {

            if (qs != null) {

                if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                if (env.getDialogId().intValue() == 1009) {

                    qs.setQuestVar(2);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return defaultQuestEndDialog(env);
                }

                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }
}

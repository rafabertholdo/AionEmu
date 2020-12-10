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

public class _1141BelbuasTreasure extends QuestHandler {
    private static final int questId = 1141;

    public _1141BelbuasTreasure() {
        super(Integer.valueOf(1141));
    }

    public void register() {
        this.qe.setNpcQuestData(730001).addOnQuestStart(1141);
        this.qe.setNpcQuestData(730001).addOnTalkEvent(1141);
        this.qe.setNpcQuestData(700122).addOnTalkEvent(1141);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(1141);
        if (qs == null) {

            if (targetId == 730001) {
                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                }
                return defaultQuestStartDialog(env);
            }

        } else if (targetId == 700122) {

            if (qs != null) {

                if ((qs.getQuestVarById(0) == 0 || qs.getQuestVarById(0) == 0) && env.getDialogId().intValue() == -1) {

                    qs.setQuestVar(2);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return defaultQuestEndDialog(env);
                }

                PacketSendUtility.sendPacket(player,
                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1141BelbuasTreasure.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */

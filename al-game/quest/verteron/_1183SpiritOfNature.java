package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _1183SpiritOfNature extends QuestHandler {
    private static final int questId = 1183;

    public _1183SpiritOfNature() {
        super(Integer.valueOf(1183));
    }

    public void register() {
        this.qe.setNpcQuestData(730012).addOnQuestStart(1183);
        this.qe.setNpcQuestData(730012).addOnTalkEvent(1183);
        this.qe.setNpcQuestData(730013).addOnTalkEvent(1183);
        this.qe.setNpcQuestData(730014).addOnTalkEvent(1183);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1183);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 730012) {

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

                case 730013:
                    switch (env.getDialogId().intValue()) {

                        case -1:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);

                        case 10000:
                            if (player.getInventory().getItemCountByItemId(182200550) == 0L && !ItemService
                                    .addItems(player, Collections.singletonList(new QuestItems(182200550, 1)))) {
                                return true;
                            }
                            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                    }

                case 730014:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);

                        case 10001:
                            if (player.getInventory().getItemCountByItemId(182200565) == 0L && !ItemService
                                    .addItems(player, Collections.singletonList(new QuestItems(182200565, 1)))) {
                                return true;
                            }
                            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                    }

                    return defaultQuestEndDialog(env);

                case 730012:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);

                        case 1009:
                            qs.setQuestVar(3);
                            ItemService.removeItemFromInventoryByItemId(player, 182200550);
                            ItemService.removeItemFromInventoryByItemId(player, 182200565);
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(player, qs);
                            return defaultQuestEndDialog(env);
                    }

                    break;
            }

        } else if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 730012) {

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
 * !\quest\verteron\_1183SpiritOfNature.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */

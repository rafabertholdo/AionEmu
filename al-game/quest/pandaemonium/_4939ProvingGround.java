package quest.pandaemonium;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _4939ProvingGround extends QuestHandler {
    private static final int questId = 4939;

    public _4939ProvingGround() {
        super(Integer.valueOf(4939));
    }

    public void register() {
        this.qe.setNpcQuestData(204053).addOnQuestStart(4939);
        this.qe.setNpcQuestData(204055).addOnTalkEvent(4939);
        this.qe.setNpcQuestData(204273).addOnTalkEvent(4939);
        this.qe.setNpcQuestData(204054).addOnTalkEvent(4939);
        this.qe.setNpcQuestData(204075).addOnTalkEvent(4939);
        this.qe.setNpcQuestData(204053).addOnTalkEvent(4939);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(4939);

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 204053) {

                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
                }
                return defaultQuestStartDialog(env);
            }
        }

        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START)

        {
            switch (targetId)

            {
                case 204055:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);

                        case 10000:
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                    }

                    return false;
                case 204273:
                    if (var == 1)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                            case 10001:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 204054:
                    if (var == 2)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                            case 10002:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    if (var == 3)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                            case 33:
                                if (player.getInventory().getItemCountByItemId(186000079) >= 30L) {
                                    ItemService.removeItemFromInventoryByItemId(player, 186000079);
                                    qs.setQuestVarById(0, var + 1);
                                    updateQuestStatus(player, qs);
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                                }
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
                        }
                    return false;
                case 204075:
                    if (var == 4)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                if (player.getInventory().getItemCountByItemId(186000085) >= 1L)
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
                            case 10255:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
                            case 1009:
                                ItemService.removeItemFromInventoryByItemId(player, 186000085);
                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(player, qs);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                        }
                    return false;
            }
            return defaultQuestStartDialog(env);
        }
        if (qs.getStatus() == QuestStatus.REWARD)
            if (targetId == 204053)
                return defaultQuestEndDialog(env);
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\pandaemonium\_4939ProvingGround.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
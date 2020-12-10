package quest.pandaemonium;

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

public class _4942ProvingProficiency extends QuestHandler {
    private static final int questId = 4942;

    public _4942ProvingProficiency() {
        super(Integer.valueOf(4942));
    }

    public void register() {
        this.qe.setNpcQuestData(204053).addOnQuestStart(4942);
        this.qe.setNpcQuestData(204104).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(204108).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(204106).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(204110).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(204100).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(204102).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(798317).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(204075).addOnTalkEvent(4942);
        this.qe.setNpcQuestData(204053).addOnTalkEvent(4942);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(4942);

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
                case 204053:
                    if (var == 0)

                    {
                        switch (env.getDialogId().intValue()) {

                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);

                            case 10000:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;

                            case 10001:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;

                            case 10002:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;

                            case 10003:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;

                            case 10004:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;

                            case 10005:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }

                        return false;
                    }
                case 204104:
                    if (var == 1)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                            case 10006:
                                if (player.getInventory().getItemCountByItemId(152201596) == 0L)
                                    if (!ItemService.addItems(player,
                                            Collections.singletonList(new QuestItems(152201596, 1))))
                                        return true;
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 204108:
                    if (var == 2)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                            case 10006:
                                if (player.getInventory().getItemCountByItemId(152201639) == 0L)
                                    if (!ItemService.addItems(player,
                                            Collections.singletonList(new QuestItems(152201639, 1))))
                                        return true;
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 204106:
                    if (var == 3)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                            case 10006:
                                if (player.getInventory().getItemCountByItemId(152201615) == 0L)
                                    if (!ItemService.addItems(player,
                                            Collections.singletonList(new QuestItems(152201615, 1))))
                                        return true;
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 204110:
                    if (var == 4)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                            case 10006:
                                if (player.getInventory().getItemCountByItemId(152201632) == 0L)
                                    if (!ItemService.addItems(player,
                                            Collections.singletonList(new QuestItems(152201632, 1))))
                                        return true;
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 204100:
                    if (var == 5)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
                            case 10006:
                                if (player.getInventory().getItemCountByItemId(152201644) == 0L)
                                    if (!ItemService.addItems(player,
                                            Collections.singletonList(new QuestItems(152201644, 1))))
                                        return true;
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 204102:
                    if (var == 6)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
                            case 10006:
                                if (player.getInventory().getItemCountByItemId(152201643) == 0L)
                                    if (!ItemService.addItems(player,
                                            Collections.singletonList(new QuestItems(152201643, 1))))
                                        return true;
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 798317:
                    if (var == 7)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398);
                            case 33:
                                if (player.getInventory().getItemCountByItemId(186000077) >= 1L) {
                                    ItemService.removeItemFromInventoryByItemId(player, 186000077);
                                    qs.setQuestVarById(0, var + 1);
                                    updateQuestStatus(player, qs);
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                                }
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
                        }
                    return false;
                case 204075:
                    if (var == 8)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                if (player.getInventory().getItemCountByItemId(186000084) >= 1L)
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3825);
                            case 10255:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
                            case 1009:
                                ItemService.removeItemFromInventoryByItemId(player, 186000084);
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

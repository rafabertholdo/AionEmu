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

public class _4938WorkOfTheFenrisFangs extends QuestHandler {
    private static final int questId = 4938;

    public _4938WorkOfTheFenrisFangs() {
        super(Integer.valueOf(4938));
    }

    public void register() {
        this.qe.setNpcQuestData(204053).addOnQuestStart(4938);
        this.qe.setNpcQuestData(798367).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(798368).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(798369).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(798370).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(798371).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(798372).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(798373).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(798374).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(204075).addOnTalkEvent(4938);
        this.qe.setNpcQuestData(204053).addOnTalkEvent(4938);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(4938);

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
                case 798367:
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
                case 798368:
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
                case 798369:
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
                    return false;
                case 798370:
                    if (var == 3)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                            case 10003:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 798371:
                    if (var == 4)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                            case 10004:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 798372:
                    if (var == 5)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
                            case 10005:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 798373:
                    if (var == 6)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
                            case 10006:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 798374:
                    if (var == 7)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398);
                            case 10007:
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                        }
                    return false;
                case 204075:
                    if (var == 8)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                if (player.getInventory().getItemCountByItemId(186000085) >= 1L)
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3825);
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

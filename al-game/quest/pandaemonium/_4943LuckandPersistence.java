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
import org.apache.log4j.Logger;

public class _4943LuckandPersistence extends QuestHandler {
    private static final int questId = 4943;
    private static final Logger log = Logger.getLogger(_4943LuckandPersistence.class);

    public _4943LuckandPersistence() {
        super(Integer.valueOf(4943));
    }

    public void register() {
        this.qe.setNpcQuestData(204053).addOnQuestStart(4943);
        this.qe.setNpcQuestData(204096).addOnTalkEvent(4943);
        this.qe.setNpcQuestData(204097).addOnTalkEvent(4943);
        this.qe.setNpcQuestData(204075).addOnTalkEvent(4943);
        this.qe.setNpcQuestData(204053).addOnTalkEvent(4943);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(4943);

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
                case 204096:
                    if (var == 0) {
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
                        }

                    }
                    if (var == 2) {
                        switch (env.getDialogId().intValue()) {

                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);

                            case 33:
                                if (player.getInventory().getItemCountByItemId(182207124) >= 20L) {

                                    ItemService.removeItemFromInventoryByItemId(player, 182207124);
                                    qs.setQuestVarById(0, var + 1);
                                    updateQuestStatus(player, qs);

                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                                }

                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
                        }

                    }
                    return false;
                case 204097:
                    if (var == 1) {
                        log.info("Received dialog id :" + env.getDialogId());
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                            case 1354:
                                PacketSendUtility.sendMessage(player,
                                        String.valueOf(player.getInventory().getKinahItem().getItemCount()));
                                if (ItemService.decreaseKinah(player, 3400000L)) {
                                    if (player.getInventory().getItemCountByItemId(182207123) == 0L)
                                        if (!ItemService.addItems(player,
                                                Collections.singletonList(new QuestItems(182207123, 1))))
                                            return true;
                                    qs.setQuestVarById(0, var + 1);
                                    updateQuestStatus(player, qs);
                                    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_DIALOG_WINDOW(
                                            env.getVisibleObject().getObjectId(), 10));
                                    return true;
                                }
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
                        }
                    }
                    return false;
                case 204075:
                    if (var == 3)
                        switch (env.getDialogId().intValue()) {
                            case 25:
                                if (player.getInventory().getItemCountByItemId(186000085) >= 1L)
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
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
 * !\quest\pandaemonium\_4943LuckandPersistence.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */
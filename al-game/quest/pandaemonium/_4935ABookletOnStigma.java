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
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _4935ABookletOnStigma extends QuestHandler {
    private static final int questId = 4935;
    private static final int[] npc_ids = new int[] { 204051, 204285, 279005 };

    public _4935ABookletOnStigma() {
        super(Integer.valueOf(4935));
    }

    public void register() {
        this.qe.setNpcQuestData(204051).addOnQuestStart(4935);
        this.qe.setQuestItemIds(182207107).add(4935);
        this.qe.setQuestItemIds(182207108).add(4935);
        for (int npc_id : npc_ids) {
            this.qe.setNpcQuestData(npc_id).addOnTalkEvent(4935);
        }
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(4935);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {

            if (targetId == 204051) {

                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
                }
                return defaultQuestStartDialog(env);
            }

            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 204051 && player.getInventory().getItemCountByItemId(182207108) == 1L) {

                if (env.getDialogId().intValue() == -1)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
                if (env.getDialogId().intValue() == 1009) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                }
                return defaultQuestEndDialog(env);
            }
            return false;
        }
        if (qs.getStatus() == QuestStatus.START) {

            if (targetId == 204285) {

                switch (env.getDialogId().intValue()) {

                    case 25:
                        if (var == 0)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                        if (var == 1)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                    case 33:
                        if (var == 1) {

                            if (QuestService.collectItemCheck(env, true)) {

                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                ItemService.addItems(player, Collections.singletonList(new QuestItems(182207107, 1)));
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                            }

                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
                        }
                    case 10000:
                        if (var == 0)
                            qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(player, qs);
                        PacketSendUtility.sendPacket(player,
                                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                        return true;
                }
                return false;
            }
            if (targetId == 279005 && player.getInventory().getItemCountByItemId(182207107) == 1L) {
                switch (env.getDialogId().intValue()) {

                    case 25:
                        if (var == 2)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                    case 10255:
                        if (var == 2)
                            ItemService.removeItemFromInventoryByItemId(player, 182207107);
                        ItemService.addItems(player, Collections.singletonList(new QuestItems(182207108, 1)));
                        PacketSendUtility.sendPacket(player,
                                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(player, qs);
                        return true;
                }

            }
            return false;
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\pandaemonium\_4935ABookletOnStigma.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */

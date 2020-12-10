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

public class _2988TheWiseinDisguise extends QuestHandler {
    private static final int questId = 2988;

    public _2988TheWiseinDisguise() {
        super(Integer.valueOf(2988));
    }

    public void register() {
        this.qe.setNpcQuestData(204182).addOnQuestStart(2988);
        this.qe.setNpcQuestData(204338).addOnQuestStart(2988);
        this.qe.setNpcQuestData(204213).addOnQuestStart(2988);
        this.qe.setNpcQuestData(204146).addOnQuestStart(2988);
        this.qe.setNpcQuestData(204182).addOnTalkEvent(2988);
        this.qe.setNpcQuestData(204146).addOnTalkEvent(2988);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(2988);

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 204182) {

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

        if (qs.getStatus() == QuestStatus.START) {

            if (targetId == 204338 && var == 0) {

                switch (env.getDialogId().intValue()) {

                    case 25:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                    case 10000:
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(player, qs);
                        PacketSendUtility.sendPacket(player,
                                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                }

            } else if (targetId == 204213 && var == 1) {

                switch (env.getDialogId().intValue()) {

                    case 25:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                    case 10001:
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(player, qs);
                        PacketSendUtility.sendPacket(player,
                                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                }

            } else if (targetId == 204146 && var == 2) {

                switch (env.getDialogId().intValue()) {

                    case 25:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                    case 2035:
                        if (player.getInventory().getItemCountByItemId(186000039) == 1L) {

                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(player, qs);
                            ItemService.removeItemFromInventoryByItemId(player, 186000039);
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
                        }

                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
                }

            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 204146)
                return defaultQuestEndDialog(env);
        }
        return false;
    }
}

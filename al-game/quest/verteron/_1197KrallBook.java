package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1197KrallBook extends QuestHandler {
    private static final int questId = 1197;

    public _1197KrallBook() {
        super(Integer.valueOf(1197));
    }

    public void register() {
        this.qe.setNpcQuestData(700004).addOnTalkEvent(1197);
        this.qe.setNpcQuestData(203129).addOnTalkEvent(1197);
        this.qe.setQuestItemIds(182200558).add(1197);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1197);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        if (targetId == 0) {

            if (env.getDialogId().intValue() == 1002) {

                QuestService.startQuest(env, QuestStatus.START);
                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_DIALOG_WINDOW(0, 0));
                return true;
            }
        } else {
            if (targetId == 700004) {

                if (qs == null || qs.getStatus() == QuestStatus.NONE) {
                    if (player.getInventory().getItemCountByItemId(182200558) == 0L) {
                        if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182200558, 1))))
                            ((Npc) player.getTarget()).getController().onDespawn(true);
                    }
                }
                return true;
            }
            if (targetId == 203129) {
                if (qs != null) {

                    if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START) {
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                    }
                    if (env.getDialogId().intValue() == 1009) {

                        ItemService.removeItemFromInventoryByItemId(player, 182200558);
                        qs.setQuestVar(1);
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(player, qs);
                        return defaultQuestEndDialog(env);
                    }

                    return defaultQuestEndDialog(env);
                }
            }
        }
        return false;
    }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182200558)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            
            _1197KrallBook.this.sendQuestDialog(player, 0, 4);
          }
        }3000L);
    return true;
  }
}

package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
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

public class _1182AncientStoneFragment extends QuestHandler {
    private static final int questId = 1182;

    public _1182AncientStoneFragment() {
        super(Integer.valueOf(1182));
    }

    public void register() {
        this.qe.setNpcQuestData(203099).addOnTalkEvent(1182);
        this.qe.setQuestItemIds(182200549).add(1182);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1182);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        if (targetId == 0) {

            if (env.getDialogId().intValue() == 1002) {
                QuestService.startQuest(env, QuestStatus.START);
                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_DIALOG_WINDOW(0, 0));
                return true;
            }

        } else if (targetId == 203099) {

            if (qs != null) {

                if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                }
                if (env.getDialogId().intValue() == 1009) {

                    ItemService.removeItemFromInventoryByItemId(player, 182200549);
                    qs.setQuestVar(1);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return defaultQuestEndDialog(env);
                }

                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182200549)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            
            _1182AncientStoneFragment.this.sendQuestDialog(player, 0, 4);
          }
        }3000L);
    return true;
  }
}

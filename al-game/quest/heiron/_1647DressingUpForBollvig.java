package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;
import java.util.List;

public class _1647DressingUpForBollvig extends QuestHandler {
  private static final int questId = 1647;

  public _1647DressingUpForBollvig() {
    super(Integer.valueOf(1647));
  }

  public void register() {
    this.qe.setNpcQuestData(790019).addOnQuestStart(1647);
    this.qe.setNpcQuestData(790019).addOnTalkEvent(1647);
    this.qe.setQuestItemIds(182201783).add(1647);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1647);

    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      if (targetId == 790019) {

        switch (env.getDialogId().intValue()) {

          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);

          case 1002:
            if (player.getInventory().getItemCountByItemId(182201783) == 0L) {
              if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182201783, 1)))) {
                return true;
              }
            }
            break;
        }
        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      switch (targetId) {

        case 790019:
          switch (env.getDialogId().intValue()) {

            case 25:
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);

            case 1009:
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }

          return defaultQuestEndDialog(env);
      }

    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201783)
    {
      return false;
    }
    
    final QuestState qs = player.getQuestStateList().getQuestState(1647);
    if (qs == null)
    {
      return false;
    }
    
    int var = qs.getQuestVars().getQuestVars();
    if (var != 0)
    {
      return false;
    }
    
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    
    if (MathUtil.getDistance(1677.0F, 2520.0F, 100.0F, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()) > 5.0D)
    {
      
      return false;
    }
    
    int itemId1 = 110100150;
    int itemId2 = 113100144;
    boolean CheckitemId1 = false;
    boolean CheckitemId2 = false;
    
    List<Item> items1 = player.getEquipment().getEquippedItemsByItemId(itemId1);
    
    for (Item ListeCheckitemId1 : items1)
    {
      CheckitemId1 = true;
    }
    
    List<Item> items2 = player.getEquipment().getEquippedItemsByItemId(itemId2);
    
    for (Item ListeCheckitemId2 : items2)
    {
      CheckitemId2 = true;
    }
    
    if ((!CheckitemId1 && CheckitemId2) || (CheckitemId1 && !CheckitemId2) || (!CheckitemId1 && !CheckitemId2))
    {
      return false;
    }
    
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            
            ItemService.removeItemFromInventory(player, item);
            qs.setStatus(QuestStatus.REWARD);
            _1647DressingUpForBollvig.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }
}

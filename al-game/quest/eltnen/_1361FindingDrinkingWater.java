package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _1361FindingDrinkingWater extends QuestHandler {
  private static final int questId = 1361;

  public _1361FindingDrinkingWater() {
    super(Integer.valueOf(1361));
  }

  public void register() {
    this.qe.setQuestItemIds(182201326).add(1361);
    this.qe.setNpcQuestData(203943).addOnQuestStart(1361);
    this.qe.setNpcQuestData(203943).addOnTalkEvent(1361);
    this.qe.setNpcQuestData(700173).addOnTalkEvent(1361);
  }

  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201326)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.MYSTIC_SPRING_OF_ANATHE))
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(1361);
    if (qs == null)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.removeItemFromInventory(player, item);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201327, 1)));
            qs.setQuestVar(1);
            _1361FindingDrinkingWater.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return false;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1361);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203943) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 1002) {
          
          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201326, 1)))) {
            return defaultQuestStartDialog(env);
          }
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
    } else {
      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009) {
          
          qs.setQuestVar(2);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        } 
        
        return defaultQuestEndDialog(env);
      } 
      
      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
      {
        switch (targetId) {

          
          case 700173:
            if (qs.getQuestVarById(0) == 1 && env.getDialogId().intValue() == -1) {

              
              final int targetObjectId = env.getVisibleObject().getObjectId();
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
              
              PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
              
              ThreadPoolManager.getInstance().schedule(new Runnable()
                  {
                    public void run()
                    {
                      if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
                        return; 
                      QuestState qs = player.getQuestStateList().getQuestState(1361);
                      qs.setStatus(QuestStatus.REWARD);
                      _1361FindingDrinkingWater.this.updateQuestStatus(player, qs);
                      ItemService.removeItemFromInventoryByItemId(player, 182201327);
                      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                      
                      PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    }
                  }3000L);
            } 
            break;
        } 
      }
    } 
    return false;
  }
}

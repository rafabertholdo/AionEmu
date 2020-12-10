package quest.ishalgen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;



















public class _2122AshestoAshes
  extends QuestHandler
{
  private static final int questId = 2122;
  
  public _2122AshestoAshes() {
    super(Integer.valueOf(2122));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203551).addOnTalkEvent(2122);
    this.qe.setNpcQuestData(700148).addOnTalkEvent(2122);
    this.qe.setNpcQuestData(730029).addOnTalkEvent(2122);
    this.qe.setQuestItemIds(182203120).add(2122);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2122);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (targetId == 0) {
      
      switch (env.getDialogId().intValue()) {
        
        case 1002:
          QuestService.startQuest(env, QuestStatus.START);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
          return true;
        case 1003:
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
          return true;
      } 
    
    } else if (targetId == 203551) {
      
      if (qs == null)
        return false; 
      if (qs.getStatus() == QuestStatus.START) {
        
        int var = qs.getQuestVarById(0);
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            break;
          case 1012:
            if (var == 0)
              ItemService.removeItemFromInventoryByItemId(player, 182203120); 
            break;
          case 10000:
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
        } 
      
      } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        
        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        }
        return defaultQuestEndDialog(env);
      }
    
    } else if (targetId == 700148) {
      
      if (qs != null && qs.getStatus() == QuestStatus.START) {
        return true;
      }
    } else if (targetId == 730029) {
      
      if (qs != null && qs.getStatus() == QuestStatus.START) {
        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        switch (env.getDialogId().intValue()) {
          
          case -1:
            if (player.getInventory().getItemCountByItemId(182203133) < 3L) {
              
              sendQuestDialog(player, targetObjectId, 1693);
              return false;
            } 
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    if (!player.isTargeting(targetObjectId))
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    
                    _2122AshestoAshes.this.sendQuestDialog(player, targetObjectId, 1352);
                  }
                }3000L);
            return false;
          case 10001:
            ItemService.removeItemFromInventoryByItemId(player, 182203133);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
            return true;
        } 
      
      } 
    } 
    return false;
  }


  
  public boolean onItemUseEvent(QuestEnv env, Item item) {
    Player player = env.getPlayer();
    int id = item.getItemTemplate().getTemplateId();
    int itemObjId = item.getObjectId();
    QuestState qs = player.getQuestStateList().getQuestState(2122);
    
    if (id != 182203120)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
      sendQuestDialog(player, 0, 4); 
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2122AshestoAshes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

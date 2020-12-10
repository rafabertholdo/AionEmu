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
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;


















public class _2136TheLostAxe
  extends QuestHandler
{
  private static final int questId = 2136;
  private static final int[] npc_ids = new int[] { 700146, 790009 };

  
  public _2136TheLostAxe() {
    super(Integer.valueOf(2136));
  }


  
  public void register() {
    this.qe.setQuestItemIds(182203130).add(2136);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2136);
    }
  }

  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    final QuestState qs = player.getQuestStateList().getQuestState(2136);
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (env.getDialogId().intValue() == 1002) {
        
        QuestService.startQuest(env, QuestStatus.START);
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
        return true;
      } 
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
    } 
    
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 790009)
      {
        final Npc npc = (Npc)env.getVisibleObject();
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                npc.getController().onDelete();
              }
            },  10000L);
        return defaultQuestEndDialog(env);
      }
    
    } else if (qs.getStatus() != QuestStatus.START) {
      return false;
    } 
    if (targetId == 790009) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        case 10000:
          if (var == 1) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182203130);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          } 
        case 10001:
          if (var == 1) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182203130);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          } 
          break;
      } 
    } else if (targetId == 700146) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 0) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            final int instanceId = player.getInstanceId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 59));
                    qs.setQuestVarById(0, 1);
                    _2136TheLostAxe.this.updateQuestStatus(player, qs);
                    QuestService.addNewSpawn(220010000, instanceId, 790009, 1088.5F, 2371.8F, 258.375F, (byte)87, true);
                  }
                }3000L);
          } 
          return true;
      } 
    } 
    return false;
  }


  
  public boolean onItemUseEvent(QuestEnv env, Item item) {
    Player player = env.getPlayer();
    int id = item.getItemTemplate().getTemplateId();
    int itemObjId = item.getObjectId();
    QuestState qs = player.getQuestStateList().getQuestState(2136);
    
    if (id != 182203130)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
      sendQuestDialog(player, 0, 4); 
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2136TheLostAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

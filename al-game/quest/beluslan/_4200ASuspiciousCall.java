package quest.beluslan;

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
import com.aionemu.gameserver.services.InstanceService;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;

public class _4200ASuspiciousCall extends QuestHandler {
  private static final int questId = 4200;
  private static final int[] npc_ids = new int[] { 204839, 798332, 700522, 279006, 204286 };

  public _4200ASuspiciousCall() {
    super(Integer.valueOf(4200));
  }

  public void register() {
    this.qe.setNpcQuestData(204839).addOnQuestStart(4200);
    this.qe.setQuestItemIds(182209097).add(4200);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(4200);
    }
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(4200);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 204839) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      } 
      
      return false;
    } 
    
    int var = qs.getQuestVarById(0);
    
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 204286) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
        if (env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    if (qs.getStatus() == QuestStatus.START)
    {
      if (targetId == 204839) {
        WorldMapInstance newInstance;
        switch (env.getDialogId().intValue()) {
          
          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1003);
          case 1011:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
          
          case 10000:
            newInstance = InstanceService.getNextAvailableInstance(300100000);
            InstanceService.registerPlayerWithInstance(newInstance, player);
            
            TeleportService.teleportTo(player, 300100000, newInstance.getInstanceId(), 403.55F, 508.11F, 885.77F, 0);
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            return true;
        } 

      
      } else if (targetId == 798332 && var == 1) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          case 10001:
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
        } 
      } else {
        if (targetId == 700522 && var == 2) {
          
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  _4200ASuspiciousCall.this.updateQuestStatus(player, qs);
                }
              }3000L);
          return true;
        } 
        if (targetId == 279006 && var == 3)
        {
          switch (env.getDialogId().intValue()) {
            
            case 25:
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
            case 10255:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              return true;
          } 
        }
      } 
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    final QuestState qs = player.getQuestStateList().getQuestState(4200);
    
    if (id != 182209097 || qs == null || qs.getQuestVarById(0) != 2) {
      return false;
    }
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            
            ItemService.removeItemFromInventoryByItemId(player, 182209097);
            
            TeleportService.teleportTo(player, 400010000, 3419.16F, 2445.43F, 2766.54F, 57);
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            _4200ASuspiciousCall.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }
}

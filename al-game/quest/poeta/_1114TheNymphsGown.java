package quest.poeta;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
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
import java.util.Collections;



















public class _1114TheNymphsGown
  extends QuestHandler
{
  private static final int questId = 1114;
  private static final int[] npc_ids = new int[] { 203075, 203058, 700008 };

  
  public _1114TheNymphsGown() {
    super(Integer.valueOf(1114));
  }


  
  public void register() {
    this.qe.setQuestItemIds(182200214).add(1114);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1114);
    }
  }

  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    final QuestState qs = player.getQuestStateList().getQuestState(1114);
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (targetId == 0)
    {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 1002) {
          
          if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200226, 1))));
          
          QuestService.startQuest(env, QuestStatus.START);
          ItemService.removeItemFromInventoryByItemId(player, 182200214);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
          
          return true;
        } 
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
      } 
    }
    
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203075 && var == 4) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6); 
        return defaultQuestEndDialog(env);
      } 
      if (targetId == 203058 && var == 3) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {
      return false;
    } 
    if (targetId == 203075) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          if (var == 3) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
          }
        case 1009:
          if (var == 2) {
            
            qs.setQuestVarById(0, var + 2);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182200217);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          } 
          if (var == 3) {
            
            qs.setQuestVarById(0, var + 1);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182200217);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          } 
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182200226);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10001:
          if (var == 2) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 700008) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 1) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    for (VisibleObject obj : player.getKnownList().getKnownObjects().values()) {
                      
                      if (!(obj instanceof Npc))
                        continue; 
                      if (((Npc)obj).getNpcId() != 203175)
                        continue; 
                      ((Npc)obj).getAggroList().addDamage((Creature)player, 50);
                    } 
                    
                    if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200217, 1))));
                    qs.setQuestVarById(0, 2);
                    _1114TheNymphsGown.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 
          return true;
      } 
    } 
    if (targetId == 203058)
    {
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        case 10002:
          if (var == 3) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182200217);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          } 
        case 10001:
          if (var == 3) {
            
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      }  } 
    return false;
  }


  
  public boolean onItemUseEvent(QuestEnv env, Item item) {
    Player player = env.getPlayer();
    int id = item.getItemTemplate().getTemplateId();
    int itemObjId = item.getObjectId();
    QuestState qs = player.getQuestStateList().getQuestState(1114);
    
    if (id != 182200214)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
      sendQuestDialog(player, 0, 4); 
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1114TheNymphsGown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

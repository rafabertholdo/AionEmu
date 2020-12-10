package quest.verteron;

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
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;



















public class _1014OdiumintheDukakiSettlement
  extends QuestHandler
{
  private static final int questId = 1014;
  private static final int[] npc_ids = new int[] { 203129, 730020, 203098, 700090 };
  private static final int[] mob_ids = new int[] { 210145, 210174, 210739 };

  
  public _1014OdiumintheDukakiSettlement() {
    super(Integer.valueOf(1014));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1014);
    this.qe.setQuestItemIds(182200012).add(1014);
    for (int mob_id : mob_ids)
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1014); 
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1014);
    }
  }

  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1014);
    boolean lvlCheck = QuestService.checkLevelRequirement(1014, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1014);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203098) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {
      
      return false;
    } 
    if (targetId == 203129) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 10)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 14)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          return false;
        
        case 1013:
          if (var == 0) {
            
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 26));
            return false;
          } 
        
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        
        case 10001:
          if (var == 10) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        
        case 10002:
          if (var == 14) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          
          return false;
      } 
    
    } else if (targetId == 730020) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          return false;
        
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 700090) {
      
      if (var == 11 && env.getDialogId().intValue() == -1) {
        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                if (!player.isTargeting(targetObjectId))
                  return; 
                if (player.getInventory().getItemCountByItemId(182200011) == 0L)
                  return; 
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                QuestService.addNewSpawn(210030000, 1, 210739, 757.7F, 2477.2F, 217.4F, (byte)0, true);
              }
            }3000L);
      } 
    } 
    return false;
  }



  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1014);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false; 
    if (targetId == 210145 && qs.getQuestVarById(0) < 10) {
      
      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
      updateQuestStatus(player, qs);
    } 
    return false;
  }


  
  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    final QuestState qs = player.getQuestStateList().getQuestState(1014);
    
    if (id != 182200012 || qs.getQuestVarById(0) != 11)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.ODIUM_REFINING_CAULDRON)) {
      return false;
    }
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 172));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.decreaseItemCountByItemId(player, 182200012, 1L);
            ItemService.decreaseItemCountByItemId(player, 182200011, 1L);
            qs.setQuestVarById(0, 14);
            _1014OdiumintheDukakiSettlement.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1014OdiumintheDukakiSettlement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

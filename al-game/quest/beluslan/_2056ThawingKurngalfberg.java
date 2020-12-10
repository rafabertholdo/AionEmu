package quest.beluslan;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
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
import java.util.Collections;





















public class _2056ThawingKurngalfberg
  extends QuestHandler
{
  private static final int questId = 2056;
  private static final int[] npc_ids = new int[] { 204753, 790016, 730036, 279000 };

  
  public _2056ThawingKurngalfberg() {
    super(Integer.valueOf(2056));
  }


  
  public void register() {
    this.qe.setQuestItemIds(182204313).add(2056);
    this.qe.setQuestItemIds(182204314).add(2056);
    this.qe.setQuestItemIds(182204315).add(2056);
    this.qe.addQuestLvlUp(2056);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2056);
    }
  }

  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2056);
    boolean lvlCheck = QuestService.checkLevelRequirement(2056, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(2500);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2056);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 204753) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 204753) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        case 1012:
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 242));
          break;
        case 2376:
          if (QuestService.collectItemCheck(env, false)) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2376);
          }
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10004:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 790016) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        case 2035:
          if (var == 1 && player.getInventory().getItemCountByItemId(182204315) != 1L) {
            
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182204315, 1)));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
      } 
    
    } else if (targetId == 730036) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        case 1353:
          if (var == 1 && player.getInventory().getItemCountByItemId(182204313) != 1L) {
            
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182204313, 1)));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
      } 
    
    } else if (targetId == 279000) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        case 1694:
          if (var == 1 && player.getInventory().getItemCountByItemId(182204314) != 1L) {
            
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182204314, 1)));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
      } 
    } 
    return false;
  }


  
  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    final QuestState qs = player.getQuestStateList().getQuestState(2056);
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.THE_SACRED_ORCHARD_220040000)) {
      return false;
    }
    if ((id != 182204313 && qs.getQuestVarById(0) == 2) || (id != 182204314 && qs.getQuestVarById(0) == 3) || (id != 182204315 && qs.getQuestVarById(0) == 4))
    {
      return false;
    }
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 2000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            if (qs.getQuestVarById(0) == 2) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 243));
              ItemService.removeItemFromInventoryByItemId(player, id);
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              _2056ThawingKurngalfberg.this.updateQuestStatus(player, qs);
            }
            else if (qs.getQuestVarById(0) == 3) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 244));
              ItemService.removeItemFromInventoryByItemId(player, id);
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              _2056ThawingKurngalfberg.this.updateQuestStatus(player, qs);
            }
            else if (qs.getQuestVarById(0) == 4) {
              
              ItemService.removeItemFromInventoryByItemId(player, id);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 245));
              qs.setStatus(QuestStatus.REWARD);
              _2056ThawingKurngalfberg.this.updateQuestStatus(player, qs);
            } 
          }
        }2000L);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2056ThawingKurngalfberg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

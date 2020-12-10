package quest.poeta;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ASCENSION_MORPH;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.InstanceService;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.Collections;
import java.util.List;






















public class _1002RequestoftheElim
  extends QuestHandler
{
  private static final int questId = 1002;
  
  public _1002RequestoftheElim() {
    super(Integer.valueOf(1002));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1002);
    this.qe.addOnEnterWorld(1002);
    this.qe.setNpcQuestData(203076).addOnTalkEvent(1002);
    this.qe.setNpcQuestData(730007).addOnTalkEvent(1002);
    this.qe.setNpcQuestData(730010).addOnTalkEvent(1002);
    this.qe.setNpcQuestData(730008).addOnTalkEvent(1002);
    this.qe.setNpcQuestData(205000).addOnTalkEvent(1002);
    this.qe.setNpcQuestData(203067).addOnTalkEvent(1002);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1002);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1002);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203067) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
        if (env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 203076) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          return false;
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
      return false;
    } 
    
    if (targetId == 730007) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 5) {
            
            ItemService.decreaseItemCountByItemId(player, 182200002, 1L);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
          } 
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          return false;
        case 33:
          if (var == 6) {
            
            if (QuestService.collectItemCheck(env, true)) {
              
              qs.setQuestVarById(0, 12);
              updateQuestStatus(player, qs);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
            } 
            
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2205);
          } 
          if (var == 12)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120); 
          return false;
        case 1353:
          if (var == 1)
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 20)); 
          return false;
        case 10001:
          if (var == 1) {
            
            if (player.getInventory().getItemCountByItemId(182200002) == 0L && 
              !ItemService.addItems(player, Collections.singletonList(new QuestItems(182200002, 1))))
              return true; 
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
        case 10002:
          if (var == 5) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
        case 10003:
          if (var == 12) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
      return false;
    } 
    
    if (targetId == 730010 && var >= 2 && var < 5) {
      List<Item> items; final int targetObjectId;
      switch (env.getDialogId().intValue()) {
        
        case -1:
          items = player.getInventory().getItemsByItemId(182200002);
          if (items.isEmpty())
            return false; 
          targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_QUESTLOOT, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  if (!player.isTargeting(targetObjectId))
                    return; 
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_QUESTLOOT, 0, targetObjectId), true);
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 10));
                }
              }3000L);
          return true;
        case 25:
          if (var == 2)
            var++; 
          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
          ((Npc)env.getVisibleObject()).getController().onDie(null);
          return true;
      } 
      return false;
    } 
    
    if (targetId == 730008) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 13) {
            
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 2375, 1002));
            return true;
          } 
          if (var == 14) {
            
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 2461, 1002));
            return true;
          } 
          return false;
        case 10004:
          if (var == 13) {
            
            qs.setQuestVarById(0, 20);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
            WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310010000);
            TeleportService.teleportTo(player, 310010000, newInstance.getInstanceId(), 52.0F, 174.0F, 229.0F, 0);
            return true;
          } 
          return false;
        case 10005:
          if (var == 14) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
      return false;
    } 
    
    if (targetId == 205000) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 20) {
            
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 1001, 0));
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    qs.setQuestVarById(0, 14);
                    _1002RequestoftheElim.this.updateQuestStatus(player, qs);
                    TeleportService.teleportTo(player, 210010000, 1, 603.0F, 1537.0F, 116.0F, (byte)20, 0);
                  }
                }43000L);
            return true;
          } 
          return false;
      } 
      return false;
    } 
    
    return false;
  }


  
  public boolean onEnterWorldEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1002);
    if (qs != null && qs.getStatus() == QuestStatus.START)
    {
      if (player.getWorldId() == 310010000) {
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ASCENSION_MORPH(1));
        return true;
      } 
    }
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1002RequestoftheElim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

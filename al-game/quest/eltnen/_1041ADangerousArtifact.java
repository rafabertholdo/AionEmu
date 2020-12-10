package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
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
import java.util.Collections;

public class _1041ADangerousArtifact extends QuestHandler {
  private static final int questId = 1041;

  public _1041ADangerousArtifact() {
    super(Integer.valueOf(1041));
  }

  public void register() {
    this.qe.setNpcQuestData(203901).addOnTalkEvent(1041);
    this.qe.setNpcQuestData(204015).addOnTalkEvent(1041);
    this.qe.setNpcQuestData(700267).addOnTalkEvent(1041);
    this.qe.setNpcQuestData(203833).addOnTalkEvent(1041);
    this.qe.setNpcQuestData(278500).addOnTalkEvent(1041);
    this.qe.setNpcQuestData(204042).addOnTalkEvent(1041);
    this.qe.setNpcQuestData(700181).addOnTalkEvent(1041);
    this.qe.addQuestLvlUp(1041);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1041);
    boolean lvlCheck = QuestService.checkLevelRequirement(1041, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1041);
    if (qs == null)
      return false; 
    if (targetId == 203901) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 10000) {
          
          qs.setQuestVar(1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        if (env.getDialogId().intValue() == 10002) {
          
          qs.setQuestVar(4);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 6) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
        if (env.getDialogId().intValue() == 10005) {
          
          qs.setQuestVar(7);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      if (qs.getStatus() == QuestStatus.REWARD)
      {
        return defaultQuestEndDialog(env);
      }
    }
    else if (targetId == 204015) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        if (env.getDialogId().intValue() == 10001) {
          
          qs.setQuestVar(2);
          updateQuestStatus(player, qs);
          QuestService.addNewSpawn(210020000, player.getInstanceId(), 700267, 2265.621F, 2357.8164F, 277.8047F, (byte)0, true);
          QuestService.addNewSpawn(210020000, player.getInstanceId(), 700267, 1827.1799F, 2537.9143F, 267.5F, (byte)0, true);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      
      }

    
    }
    else if (targetId == 203833) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        if (env.getDialogId().intValue() == 10003) {
          
          qs.setQuestVar(5);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      
      }
    
    }
    else if (targetId == 278500) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 10004) {
          
          qs.setQuestVar(6);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      }
    
    }
    else if (targetId == 204042) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 7) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
        if (env.getDialogId().intValue() == 10006) {
          
          qs.setQuestVar(8);
          ItemService.addItems(player, Collections.singletonList(new QuestItems(182201011, 1)));
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 37));
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 

      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 9)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
        if (env.getDialogId().intValue() == 10007) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 38));
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      
      }

    
    }
    else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
      
      switch (targetId) {

        
        case 700267:
          if (qs.getQuestVarById(0) == 2 && env.getDialogId().intValue() == -1) {

            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                  final QuestState qs = player.getQuestStateList().getQuestState(1041);

                  
                  public void run() {
                    this.qs.setQuestVar(3);
                    _1041ADangerousArtifact.this.updateQuestStatus(player, this.qs);
                    if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  }
                }3000L);
          } 
          break;
      } 



    
    } else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 8) {
      
      switch (targetId) {

        
        case 700181:
          if (qs.getQuestVarById(0) == 8 && env.getDialogId().intValue() == -1) {

            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                  final QuestState qs = player.getQuestStateList().getQuestState(1041);

                  
                  public void run() {
                    this.qs.setQuestVar(9);
                    _1041ADangerousArtifact.this.updateQuestStatus(player, this.qs);
                    ItemService.decreaseItemCountByItemId(player, 182201014, 1L);
                    if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  }
                }3000L);
          } 
          break;
      } 

    
    } 
    return false;
  }
}

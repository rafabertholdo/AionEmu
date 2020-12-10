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



















public class _1038TheShadowsCommand
  extends QuestHandler
{
  private static final int questId = 1038;
  private static final int[] npc_ids = new int[] { 203933, 700172, 203991, 700162 };

  
  public _1038TheShadowsCommand() {
    super(Integer.valueOf(1038));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1038);
    this.qe.setNpcQuestData(204005).addOnKillEvent(1038);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1038);
    }
  }

  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1038);
    boolean lvlCheck = QuestService.checkLevelRequirement(1038, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1038);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203991) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {
      
      return false;
    } 
    if (targetId == 700162 && var == 0) {
      final int targetObjectId;
      switch (env.getDialogId().intValue()) {

        
        case -1:
          targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 34));
                  qs.setQuestVarById(0, 1);
                  _1038TheShadowsCommand.this.updateQuestStatus(player, qs);
                }
              }3000L);
          
          return false;
      } 
    
    } else if (targetId == 203933) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694); 
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        case 33:
          if (QuestService.collectItemCheck(env, true)) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
          }
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10002:
          if (var == 3) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10003:
          if (var == 4) {
            
            qs.setQuestVarById(0, var + 2);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 700172 && var == 2) {
      final int targetObjectId;
      switch (env.getDialogId().intValue()) {

        
        case -1:
          targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  ItemService.addItems(player, Collections.singletonList(new QuestItems(182201007, 1)));
                  qs.setQuestVar(3);
                  _1038TheShadowsCommand.this.updateQuestStatus(player, qs);
                }
              }3000L);
          
          return false;
      } 
    
    } else if (targetId == 203991) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        case 10004:
          if (var == 6) {
            
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 35));
            qs.setQuestVarById(0, 7);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
            QuestService.addNewSpawn(210020000, 1, 204005, 1768.16F, 924.47F, 422.02F, (byte)0, true);
            return true;
          } 
          return false;
      } 
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1038);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 7) {
      return false;
    }
    if (targetId == 204005) {
      
      qs.setStatus(QuestStatus.REWARD);
      updateQuestStatus(player, qs);
      return true;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1038TheShadowsCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

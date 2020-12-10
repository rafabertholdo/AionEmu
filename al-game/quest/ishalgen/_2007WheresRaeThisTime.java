package quest.ishalgen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;




















public class _2007WheresRaeThisTime
  extends QuestHandler
{
  private static final int questId = 2007;
  
  public _2007WheresRaeThisTime() {
    super(Integer.valueOf(2007));
  }


  
  public void register() {
    int[] talkNpcs = { 203516, 203519, 203539, 203552, 203554, 700081, 700082, 700083 };
    this.qe.addQuestLvlUp(2007);
    for (int id : talkNpcs) {
      this.qe.setNpcQuestData(id).addOnTalkEvent(2007);
    }
  }

  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2007);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203516:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            case 10000:
              if (var == 0) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203519:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            case 10001:
              if (var == 1) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203539:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
            case 1694:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 55));
              break;
            case 10002:
              if (var == 2) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203552:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 3)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
            case 10003:
              if (var == 3) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203554:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 4)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
              if (var == 8)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
            case 10004:
              if (var == 4) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
            case 10005:
              if (var == 8) {
                
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 700081:
          if (var == 5) {
            
            destroy(6, env);
            return false;
          } 
          break;
        case 700082:
          if (var == 6) {
            
            destroy(7, env);
            return false;
          } 
          break;
        case 700083:
          if (var == 7) {
            
            destroy(-1, env);
            return false;
          } 
          break;
      } 
    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203516) {
        
        if (env.getDialogId().intValue() == -1) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 58));
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
        } 
        
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2007);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    int[] quests = { 2001, 2002, 2003, 2004, 2005, 2006 };
    for (int id : quests) {
      
      QuestState qs2 = player.getQuestStateList().getQuestState(id);
      if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
        return false; 
    } 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  
  private void destroy(final int var, QuestEnv env) {
    final int targetObjectId = env.getVisibleObject().getObjectId();
    
    final Player player = env.getPlayer();
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            if (player.getTarget().getObjectId() != targetObjectId)
              return; 
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
            PacketSendUtility.broadcastPacket(player.getTarget(), (AionServerPacket)new SM_EMOTION((Creature)player.getTarget(), EmotionType.EMOTE, 128, 0));
            QuestState qs = player.getQuestStateList().getQuestState(2007);
            if (var == -1)
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 56)); 
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            _2007WheresRaeThisTime.this.updateQuestStatus(player, qs);
          }
        }3000L);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2007WheresRaeThisTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

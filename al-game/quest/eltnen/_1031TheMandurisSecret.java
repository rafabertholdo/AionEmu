package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _1031TheMandurisSecret extends QuestHandler {
  private static final int[] mob_ids = new int[] { 210770, 210771, 210759, 210758 };

  private static final int questId = 1031;

  public _1031TheMandurisSecret() {
    super(Integer.valueOf(1031));
  }

  public void register() {
    this.qe.setNpcQuestData(203902).addOnTalkEvent(1031);
    this.qe.setNpcQuestData(203936).addOnTalkEvent(1031);
    this.qe.setNpcQuestData(700179).addOnTalkEvent(1031);
    this.qe.setNpcQuestData(204043).addOnTalkEvent(1031);
    this.qe.setNpcQuestData(204030).addOnTalkEvent(1031);
    this.qe.addQuestLvlUp(1031);
    for (int mob_id : mob_ids) {
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1031);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1031);
    boolean lvlCheck = QuestService.checkLevelRequirement(1031, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1031);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    switch (targetId) {

      case 210758:
      case 210759:
      case 210770:
      case 210771:
        if (var >= 1 && var <= 6) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1031);
    if (qs == null)
      return false; 
    if (targetId == 203902) {
      
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
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 7) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        if (env.getDialogId().intValue() == 10001) {
          
          qs.setQuestVar(8);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
      if (qs.getStatus() == QuestStatus.REWARD)
      {
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
        return defaultQuestEndDialog(env);
      }
    
    } else if (targetId == 203936) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 8)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        if (env.getDialogId().intValue() == 10002) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      
      }
    
    }
    else if (targetId == 204043) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 10)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 10004) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 2);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      
      }
    
    }
    else if (targetId == 204030) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 12)
      {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
        if (env.getDialogId().intValue() == 10006) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      
      }
    
    }
    else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 9) {
      
      switch (targetId) {

        
        case 700179:
          if (qs.getQuestVarById(0) == 9 && env.getDialogId().intValue() == -1) {



            
            qs.setQuestVar(10);
            updateQuestStatus(player, qs);
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
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

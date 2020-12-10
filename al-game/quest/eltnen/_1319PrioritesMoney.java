package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;





















public class _1319PrioritesMoney
  extends QuestHandler
{
  private static final int questId = 1319;
  
  public _1319PrioritesMoney() {
    super(Integer.valueOf(1319));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203908).addOnQuestStart(1319);
    this.qe.setNpcQuestData(203908).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(203923).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(203910).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(203906).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(203915).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(203907).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(798050).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(798049).addOnTalkEvent(1319);
    this.qe.setNpcQuestData(798046).addOnTalkEvent(1319);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1319);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203908) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
    } else {
      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
        if (env.getDialogId().intValue() == 1009) {
          
          qs.setQuestVar(8);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        } 
        
        return defaultQuestEndDialog(env);
      } 
      
      if (targetId == 203923) {

        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (env.getDialogId().intValue() == 10000) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        
        }
      
      }
      else if (targetId == 203910) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          if (env.getDialogId().intValue() == 10001) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        }
      
      }
      else if (targetId == 203906) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          if (env.getDialogId().intValue() == 10002) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        }
      
      }
      else if (targetId == 203915) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
          if (env.getDialogId().intValue() == 10003) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        }
      
      }
      else if (targetId == 203907) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
          if (env.getDialogId().intValue() == 10004) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        }
      
      }
      else if (targetId == 798050) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
          if (env.getDialogId().intValue() == 10005) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        }
      
      }
      else if (targetId == 798049) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 6)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
          if (env.getDialogId().intValue() == 10006) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        }
      
      }
      else if (targetId == 798046) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 7) {
          
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
          if (env.getDialogId().intValue() == 10007) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        } 
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1319PrioritesMoney.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

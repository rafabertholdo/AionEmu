package quest.theobomos;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;



public class _3020AgrintAfire
  extends QuestHandler
{
  private static final int questId = 3020;
  
  public _3020AgrintAfire() {
    super(Integer.valueOf(3020));
  }


  
  public void register() {
    this.qe.setNpcQuestData(798143).addOnQuestStart(3020);
    this.qe.setNpcQuestData(798143).addOnTalkEvent(3020);
    this.qe.setNpcQuestData(798149).addOnTalkEvent(3020);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(3020);
    if (targetId == 798143) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
      if (qs != null && qs.getStatus() == QuestStatus.START) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        } 
        
        return defaultQuestEndDialog(env);
      } 
      if (qs != null && qs.getStatus() == QuestStatus.REWARD)
      {
        return defaultQuestEndDialog(env);
      }
    }
    else if (targetId == 798149) {
      
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
    
    } else if (targetId == 798143) {
      
      if (qs != null) {
        
        if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009) {
          
          qs.setQuestVar(3);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        } 
        
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3020AgrintAfire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

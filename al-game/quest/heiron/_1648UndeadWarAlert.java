package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;




public class _1648UndeadWarAlert
  extends QuestHandler
{
  private static final int questId = 1648;
  
  public _1648UndeadWarAlert() {
    super(Integer.valueOf(1648));
  }


  
  public void register() {
    this.qe.setNpcQuestData(204545).addOnQuestStart(1648);
    this.qe.setNpcQuestData(204545).addOnTalkEvent(1648);
    this.qe.setNpcQuestData(204612).addOnTalkEvent(1648);
    this.qe.setNpcQuestData(204500).addOnTalkEvent(1648);
    this.qe.setNpcQuestData(204590).addOnTalkEvent(1648);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1648);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
    {
      if (targetId == 204545) {
        
        if (env.getDialogId().intValue() == 25)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        
        return defaultQuestStartDialog(env);
      } 
    }
    
    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 204612:
          switch (env.getDialogId().intValue()) {

            
            case 25:
              if (qs.getQuestVarById(0) == 0)
              {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              }

            
            case 10000:
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
              
              return true;
          } 
        
        
        
        case 204500:
          switch (env.getDialogId().intValue()) {

            
            case 25:
              if (qs.getQuestVarById(0) == 1)
              {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
              }

            
            case 10001:
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
              
              return true;
          } 
          
          break;
      } 
    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 204590) {
        
        if (env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1648UndeadWarAlert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

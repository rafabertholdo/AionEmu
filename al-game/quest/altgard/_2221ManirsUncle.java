package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;





















public class _2221ManirsUncle
  extends QuestHandler
{
  private static final int questId = 2221;
  
  public _2221ManirsUncle() {
    super(Integer.valueOf(2221));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203607).addOnQuestStart(2221);
    this.qe.setNpcQuestData(203607).addOnTalkEvent(2221);
    this.qe.setNpcQuestData(203608).addOnTalkEvent(2221);
    this.qe.setNpcQuestData(700214).addOnTalkEvent(2221);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(2221);
    if (qs == null) {
      
      if (targetId == 203607)
      {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
    
    } else if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 203608:
          if (qs.getQuestVarById(0) == 0) {
            
            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            if (env.getDialogId().intValue() == 10000) {
              
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
          } 
          if (qs.getQuestVarById(0) == 2) {
            
            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
            if (env.getDialogId().intValue() == 1009) {
              
              ItemService.removeItemFromInventoryByItemId(player, 182203215);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              return defaultQuestEndDialog(env);
            } 
            
            return defaultQuestEndDialog(env);
          } 
          break;
        
        case 700214:
          if ((qs.getQuestVarById(0) == 1 || qs.getQuestVarById(0) == 2) && env.getDialogId().intValue() == -1) {
            
            qs.setQuestVarById(0, 2);
            updateQuestStatus(player, qs);
          } 
          return true;
      } 
    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203608)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2221ManirsUncle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

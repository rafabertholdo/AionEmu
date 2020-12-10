package quest.sanctum;

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




















public class _1988AMeetingwithaSage
  extends QuestHandler
{
  private static final int questId = 1988;
  
  public _1988AMeetingwithaSage() {
    super(Integer.valueOf(1988));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203725).addOnQuestStart(1988);
    this.qe.setNpcQuestData(203989).addOnQuestStart(1988);
    this.qe.setNpcQuestData(798018).addOnQuestStart(1988);
    this.qe.setNpcQuestData(203771).addOnQuestStart(1988);
    this.qe.setNpcQuestData(203725).addOnTalkEvent(1988);
    this.qe.setNpcQuestData(203771).addOnTalkEvent(1988);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1988);
    
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
    {
      if (targetId == 203725) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
    }
    
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203989 && var == 0) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          case 10000:
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
        } 
      
      } else if (targetId == 798018 && var == 1) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
          case 10001:
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
        } 
      
      } else if (targetId == 203771 && var == 2) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          case 2035:
            if (player.getInventory().getItemCountByItemId(186000039) == 1L) {
              
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              ItemService.removeItemFromInventoryByItemId(player, 186000039);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
            } 
            
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
        } 
      
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203771)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1988AMeetingwithaSage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

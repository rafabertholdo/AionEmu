package quest.beluslan;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _2653SpyFindingBollvig
  extends QuestHandler
{
  private static final int questId = 2653;
  
  public _2653SpyFindingBollvig() {
    super(Integer.valueOf(2653));
  }


  
  public void register() {
    this.qe.setNpcQuestData(204650).addOnQuestStart(2653);
    this.qe.setNpcQuestData(204650).addOnTalkEvent(2653);
    this.qe.setNpcQuestData(204655).addOnTalkEvent(2653);
    this.qe.setNpcQuestData(204775).addOnTalkEvent(2653);
  }

  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2653);
    QuestState qs2 = player.getQuestStateList().getQuestState(2652);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(2653);
    if (targetId == 204650) {
      
      if (qs == null) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
    } else if (targetId == 204655) {
      
      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        
        if (env.getDialogId().intValue() == -1) {
          
          PacketSendUtility.sendMessage(player, "25");
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        } 
        if (env.getDialogId().intValue() == 10000) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
    } else if (targetId == 204775) {
      
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2653SpyFindingBollvig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

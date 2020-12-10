package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;






















public class _1470HannetsVengeance
  extends QuestHandler
{
  private static final int questId = 1470;
  
  public _1470HannetsVengeance() {
    super(Integer.valueOf(1470));
  }


  
  public void register() {
    this.qe.setNpcQuestData(790004).addOnQuestStart(1470);
    this.qe.setNpcQuestData(790004).addOnTalkEvent(1470);
    this.qe.setNpcQuestData(212846).addOnKillEvent(1470);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1470);
    if (targetId == 790004) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
      if (qs.getStatus() == QuestStatus.REWARD)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }

  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1470);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    switch (targetId) {
      
      case 212846:
        qs.setQuestVarById(0, var + 1);
        updateQuestStatus(player, qs);
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        return true;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1470HannetsVengeance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

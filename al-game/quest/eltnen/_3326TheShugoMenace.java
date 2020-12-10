package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;





















public class _3326TheShugoMenace
  extends QuestHandler
{
  private static final int questId = 3326;
  
  public _3326TheShugoMenace() {
    super(Integer.valueOf(3326));
  }


  
  public void register() {
    this.qe.setNpcQuestData(798053).addOnQuestStart(3326);
    this.qe.setNpcQuestData(798053).addOnTalkEvent(3326);
    this.qe.setNpcQuestData(210897).addOnKillEvent(3326);
    this.qe.setNpcQuestData(210939).addOnKillEvent(3326);
    this.qe.setNpcQuestData(210873).addOnKillEvent(3326);
    this.qe.setNpcQuestData(210919).addOnKillEvent(3326);
    this.qe.setNpcQuestData(211754).addOnKillEvent(3326);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(3326);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE)
    {
      if (targetId == 798053) {
        
        if (env.getDialogId().intValue() == 25)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4);
        }
        
        return defaultQuestStartDialog(env);
      } 
    }
    
    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 798053)
      {
        switch (env.getDialogId().intValue()) {

          
          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);

          
          case 1009:
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return defaultQuestEndDialog(env);
        } 

      
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 798053) {
        
        if (env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(3326);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (targetId == 210897 || targetId == 210939 || targetId == 210873 || targetId == 210919 || targetId == 211754)
    {
      if (var >= 0 && var < 20) {
        
        qs.setQuestVarById(0, var + 1);
        updateQuestStatus(player, qs);
        return true;
      } 
    }
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_3326TheShugoMenace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

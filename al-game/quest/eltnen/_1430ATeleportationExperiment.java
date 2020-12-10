package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.TeleportService;






















public class _1430ATeleportationExperiment
  extends QuestHandler
{
  private static final int questId = 1430;
  
  public _1430ATeleportationExperiment() {
    super(Integer.valueOf(1430));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203919).addOnQuestStart(1430);
    this.qe.setNpcQuestData(203919).addOnTalkEvent(1430);
    this.qe.setNpcQuestData(203337).addOnTalkEvent(1430);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1430);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203919)
      {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      }
    
    }
    else if (targetId == 203337) {

      
      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 10000) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          qs.setStatus(QuestStatus.REWARD);
          TeleportService.teleportTo(player, 220020000, 1, 638.0F, 2337.0F, 425.0F, (byte)20, 0);
        } else {
          
          return defaultQuestStartDialog(env);
        }
      
      } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
        if (env.getDialogId().intValue() == 1009) {
          
          qs.setQuestVar(2);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        } 
        
        return defaultQuestEndDialog(env);
      } 
    } 
    
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1430ATeleportationExperiment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

package quest.poeta;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;





















public class _1205ANewSkill
  extends QuestHandler
{
  private static final int questId = 1205;
  
  public _1205ANewSkill() {
    super(Integer.valueOf(1205));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1205);
    this.qe.setNpcQuestData(203087).addOnTalkEvent(1205);
    this.qe.setNpcQuestData(203088).addOnTalkEvent(1205);
    this.qe.setNpcQuestData(203089).addOnTalkEvent(1205);
    this.qe.setNpcQuestData(203090).addOnTalkEvent(1205);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    boolean lvlCheck = QuestService.checkLevelRequirement(1205, player.getCommonData().getLevel());
    if (!lvlCheck)
      return false; 
    QuestState qs = player.getQuestStateList().getQuestState(1205);
    if (qs != null)
      return false; 
    env.setQuestId(Integer.valueOf(1205));
    if (QuestService.startQuest(env, QuestStatus.START)) {
      
      qs = player.getQuestStateList().getQuestState(1205);
      qs.setStatus(QuestStatus.REWARD);
      switch (player.getCommonData().getPlayerClass()) {
        
        case WARRIOR:
          qs.setQuestVar(1);
          break;
        case SCOUT:
          qs.setQuestVar(2);
          break;
        case MAGE:
          qs.setQuestVar(3);
          break;
        case PRIEST:
          qs.setQuestVar(4);
          break;
      } 
      updateQuestStatus(player, qs);
    } 
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1205);
    if (qs == null || qs.getStatus() != QuestStatus.REWARD) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    PlayerClass playerClass = player.getCommonData().getPlayerClass();
    switch (targetId) {
      
      case 203087:
        if (playerClass == PlayerClass.WARRIOR) {
          
          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }
          return defaultQuestEndDialog(env);
        } 
        return false;
      case 203088:
        if (playerClass == PlayerClass.SCOUT) {
          
          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          }
          return defaultQuestEndDialog(env);
        } 
        return false;
      case 203089:
        if (playerClass == PlayerClass.MAGE) {
          
          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
          }
          return defaultQuestEndDialog(env);
        } 
        return false;
      case 203090:
        if (playerClass == PlayerClass.PRIEST) {
          
          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
          }
          return defaultQuestEndDialog(env);
        } 
        return false;
    } 
    
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1205ANewSkill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

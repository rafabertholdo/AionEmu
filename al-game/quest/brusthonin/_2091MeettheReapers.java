package quest.brusthonin;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;






















public class _2091MeettheReapers
  extends QuestHandler
{
  private static final int questId = 2091;
  
  public _2091MeettheReapers() {
    super(Integer.valueOf(2091));
  }


  
  public void register() {
    this.qe.setNpcQuestData(205150).addOnTalkEvent(2091);
    this.qe.setQuestEnterZone(ZoneName.SETTLERS_CAMPSITE_220050000).add(2091);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2091);
    boolean lvlCheck = QuestService.checkLevelRequirement(2091, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2091);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (targetId != 205150)
      return false; 
    if (qs.getStatus() == QuestStatus.START) {
      
      if (env.getDialogId().intValue() == 25)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
      if (env.getDialogId().intValue() == 1009) {
        
        qs.setStatus(QuestStatus.REWARD);
        qs.setQuestVarById(0, 1);
        updateQuestStatus(player, qs);
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
      } 
      return false;
    } 
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (env.getDialogId().intValue() == 17) {
        
        int[] ids = { 2092, 2093, 2094 };
        for (int id : ids)
        {
          QuestService.startQuest(new QuestEnv(env.getVisibleObject(), env.getPlayer(), Integer.valueOf(id), env.getDialogId()), QuestStatus.LOCKED);
        }
      } 
      return defaultQuestEndDialog(env);
    } 
    return false;
  }


  
  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.SETTLERS_CAMPSITE_220050000)
      return false; 
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2091);
    if (qs != null)
      return false; 
    env.setQuestId(Integer.valueOf(2091));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_2091MeettheReapers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

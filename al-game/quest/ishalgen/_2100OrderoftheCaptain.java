package quest.ishalgen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;





















public class _2100OrderoftheCaptain
  extends QuestHandler
{
  private static final int questId = 2100;
  
  public _2100OrderoftheCaptain() {
    super(Integer.valueOf(2100));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203516).addOnTalkEvent(2100);
    this.qe.setQuestEnterZone(ZoneName.ALDELLE_VILLAGE).add(2100);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2100);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (targetId != 203516)
      return false; 
    if (qs.getStatus() == QuestStatus.START) {
      
      if (env.getDialogId().intValue() == 25) {
        
        qs.setQuestVar(1);
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
      } 
      
      return defaultQuestStartDialog(env);
    } 
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (env.getDialogId().intValue() == 17) {
        
        int[] ids = { 2001, 2002, 2003, 2004, 2005, 2006, 2007 };
        for (int id : ids)
          QuestService.startQuest(new QuestEnv(env.getVisibleObject(), env.getPlayer(), Integer.valueOf(id), env.getDialogId()), QuestStatus.LOCKED); 
      } 
      return defaultQuestEndDialog(env);
    } 
    return false;
  }


  
  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.ALDELLE_VILLAGE)
      return false; 
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2100);
    if (qs != null)
      return false; 
    env.setQuestId(Integer.valueOf(2100));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2100OrderoftheCaptain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

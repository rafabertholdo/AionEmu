package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;




















public class _1347RaidingKlaw
  extends QuestHandler
{
  private static final int questId = 1347;
  private static final int[] npc_ids = new int[] { 203965, 203966 };
  private static final int[] mob_ids = new int[] { 210908, 210874, 212137, 212056, 210917 };

  
  public _1347RaidingKlaw() {
    super(Integer.valueOf(1347));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203965).addOnQuestStart(1347);
    for (int npc_id : npc_ids)
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1347); 
    for (int mob_id : mob_ids) {
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1347);
    }
  }

  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1347);
    if (targetId == 203965)
    {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
    }
    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203966)
      {
        if (env.getDialogId().intValue() == 25) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        } 
      }
      return false;
    } 
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203966) {
        
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1347);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    switch (targetId) {
      
      case 210874:
      case 210908:
        if (qs.getQuestVarById(0) < 15) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        } 
        break;
      case 210917:
      case 212056:
      case 212137:
        if (qs.getQuestVarById(1) < 7) {
          
          qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
          updateQuestStatus(player, qs);
          return true;
        }  break;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1347RaidingKlaw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;























public class _1367MabangtahsFeast
  extends QuestHandler
{
  private static final int questId = 1367;
  
  public _1367MabangtahsFeast() {
    super(Integer.valueOf(1367));
  }


  
  public void register() {
    this.qe.setNpcQuestData(204023).addOnQuestStart(1367);
    this.qe.setNpcQuestData(204023).addOnTalkEvent(1367);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1367);
    if (targetId == 204023) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
      if (qs.getStatus() == QuestStatus.START) {



        
        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 0) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182201333);
          long itemCount1 = player.getInventory().getItemCountByItemId(182201332);
          long itemCount2 = player.getInventory().getItemCountByItemId(182201331);
          if (itemCount > 1L || itemCount1 > 5L || itemCount2 > 0L)
          {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          }

          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        } 
        
        if (env.getDialogId().intValue() == 10000) {
          
          long itemCount2 = player.getInventory().getItemCountByItemId(182201331);
          if (itemCount2 > 0L) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            qs.setQuestVarById(0, 1);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        } 
        if (env.getDialogId().intValue() == 10001) {
          
          long itemCount1 = player.getInventory().getItemCountByItemId(182201332);
          if (itemCount1 > 4L) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            qs.setQuestVarById(0, 2);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        } 
        if (env.getDialogId().intValue() == 10002) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182201333);
          if (itemCount > 1L) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            qs.setQuestVarById(0, 3);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
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
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1367MabangtahsFeast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

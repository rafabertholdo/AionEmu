package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;





















public class _2015TaketheInitiative
  extends QuestHandler
{
  private static final int questId = 2015;
  
  public _2015TaketheInitiative() {
    super(Integer.valueOf(2015));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2015);
    this.qe.setNpcQuestData(203631).addOnTalkEvent(2015);
    this.qe.setNpcQuestData(210510).addOnKillEvent(2015);
    this.qe.setNpcQuestData(210504).addOnKillEvent(2015);
    this.qe.setNpcQuestData(210506).addOnKillEvent(2015);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2015);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203631:
          switch (env.getDialogId().intValue()) {
            
            case -1:
              if (qs.getQuestVarById(1) >= 1 && qs.getQuestVarById(2) >= 5 && qs.getQuestVarById(3) >= 5) {
                
                qs.setQuestVarById(0, var + 1);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              } 
              break;
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              break;
            case 10000:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203631)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2015);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    
    int targetId = 0;
    int var = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    switch (targetId) {
      
      case 210510:
        var = qs.getQuestVarById(1);
        if (var == 0) {
          
          qs.setQuestVarById(1, 1);
          updateQuestStatus(player, qs);
        } 
        break;
      case 210504:
        var = qs.getQuestVarById(2);
        if (var < 5) {
          
          qs.setQuestVarById(2, var + 1);
          updateQuestStatus(player, qs);
        } 
        break;
      case 210506:
        var = qs.getQuestVarById(3);
        if (var < 5) {
          
          qs.setQuestVarById(3, var + 1);
          updateQuestStatus(player, qs);
        }  break;
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2015);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    QuestState qs2 = player.getQuestStateList().getQuestState(2014);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2015TaketheInitiative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

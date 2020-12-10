package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;





















public class _2017TrespassersattheObservatory
  extends QuestHandler
{
  private static final int questId = 2017;
  
  public _2017TrespassersattheObservatory() {
    super(Integer.valueOf(2017));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2017);
    this.qe.setNpcQuestData(203654).addOnTalkEvent(2017);
    this.qe.setNpcQuestData(210528).addOnKillEvent(2017);
    this.qe.setNpcQuestData(210721).addOnKillEvent(2017);
    this.qe.setNpcQuestData(203558).addOnTalkEvent(2017);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2017);
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
        
        case 203654:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              if (var == 7)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              break;
            case 10000:
            case 10001:
              if (var == 0 || var == 6) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
            case 33:
              if (var == 7) {
                
                if (QuestService.collectItemCheck(env, true)) {
                  
                  qs.setStatus(QuestStatus.REWARD);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
                } 
                
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
              }  break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203558) {
        
        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2017);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    
    int targetId = 0;
    int var = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    switch (targetId) {
      
      case 210528:
      case 210721:
        var = qs.getQuestVarById(0);
        if (var < 6) {
          
          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
        } 
        break;
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2017);
    boolean lvlCheck = QuestService.checkLevelRequirement(2017, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false; 
    QuestState qs2 = player.getQuestStateList().getQuestState(2015);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2017TrespassersattheObservatory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

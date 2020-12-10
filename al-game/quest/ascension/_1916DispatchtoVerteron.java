package quest.ascension;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _1916DispatchtoVerteron
  extends QuestHandler
{
  private static final int questId = 1916;
  
  public _1916DispatchtoVerteron() {
    super(Integer.valueOf(1916));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1916);
    this.qe.setNpcQuestData(203726).addOnTalkEvent(1916);
    this.qe.setNpcQuestData(203097).addOnTalkEvent(1916);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1916);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 203726:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              break;
            case 10000:
              if (var == 0) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                TeleportService.teleportTo(player, 210030000, player.getInstanceId(), 1643.0F, 1500.0F, 120.0F, 1000);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                return true;
              } 
              break;
          } 
        case 203097:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 1) {
                
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
              }  break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203097)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1916);
    boolean lvlCheck = QuestService.checkLevelRequirement(1916, player.getCommonData().getLevel());
    if (qs != null || !lvlCheck) {
      return false;
    }
    env.setQuestId(Integer.valueOf(1916));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_1916DispatchtoVerteron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

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




















public class _2901DispatchtoAltgard
  extends QuestHandler
{
  private static final int questId = 2901;
  
  public _2901DispatchtoAltgard() {
    super(Integer.valueOf(2901));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2901);
    this.qe.setNpcQuestData(204191).addOnTalkEvent(2901);
    this.qe.setNpcQuestData(203559).addOnTalkEvent(2901);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2901);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 204191:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              break;
            case 10000:
              if (var == 0) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                TeleportService.teleportTo(player, 220030000, player.getInstanceId(), 1748.0F, 1807.0F, 255.0F, 1000);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                return true;
              } 
              break;
          } 
        case 203559:
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
      
      if (targetId == 203559)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2901);
    boolean lvlCheck = QuestService.checkLevelRequirement(2901, player.getCommonData().getLevel());
    if (qs != null || !lvlCheck) {
      return false;
    }
    env.setQuestId(Integer.valueOf(2901));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_2901DispatchtoAltgard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

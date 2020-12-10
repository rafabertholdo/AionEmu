package quest.pandaemonium;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;






















public class _2097SpiritBlade
  extends QuestHandler
{
  private static final int questId = 2097;
  
  public _2097SpiritBlade() {
    super(Integer.valueOf(2097));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2097);
    this.qe.setNpcQuestData(203550).addOnTalkEvent(2097);
    this.qe.setNpcQuestData(203546).addOnTalkEvent(2097);
    this.qe.setNpcQuestData(279034).addOnTalkEvent(2097);
    this.qe.setQuestItemIds(182207085).add(2097);
    this.deletebleItems = new int[] { 182207086, 182207087, 182207088 };
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2097);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVars().getQuestVars();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203550:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            case 10000:
              if (var == 0) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203546:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              return true;
            case 10001:
              if (var == 1) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 279034:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
            case 33:
              if (var == 2) {
                
                if (QuestService.collectItemCheck(env, true)) {
                  
                  qs.setStatus(QuestStatus.REWARD);
                  updateQuestStatus(player, qs);
                  ItemService.addItems(player, Collections.singletonList(new QuestItems(182207085, 1)));
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                } 
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
              } 
              break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD && targetId == 203550) {
      return defaultQuestEndDialog(env);
    }  return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2097);
    if (qs != null)
      return false; 
    boolean lvlCheck = QuestService.checkLevelRequirement(2097, player.getCommonData().getLevel());
    if (!lvlCheck)
      return false; 
    env.setQuestId(Integer.valueOf(2097));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\pandaemonium\_2097SpiritBlade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

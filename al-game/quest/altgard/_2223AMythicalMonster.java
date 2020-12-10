package quest.altgard;

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
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;






















public class _2223AMythicalMonster
  extends QuestHandler
{
  private static final int questId = 2223;
  
  public _2223AMythicalMonster() {
    super(Integer.valueOf(2223));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203616).addOnQuestStart(2223);
    this.qe.setNpcQuestData(203616).addOnTalkEvent(2223);
    this.qe.setNpcQuestData(211621).addOnKillEvent(2223);
    this.deletebleItems = new int[] { 182203217 };
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(2223);
    
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203616)
      {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
    
    } else if (qs.getStatus() == QuestStatus.START) {
      
      int var = qs.getQuestVarById(0);
      switch (targetId) {
        
        case 203620:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              break;
            case 10000:
              if (var == 0) {
                
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203217, 1))))
                  return true; 
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203616)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2223);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    
    int targetId = 0;
    int var = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    switch (targetId) {
      
      case 211621:
        var = qs.getQuestVarById(0);
        if (var == 1) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
        } 
        break;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2223AMythicalMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

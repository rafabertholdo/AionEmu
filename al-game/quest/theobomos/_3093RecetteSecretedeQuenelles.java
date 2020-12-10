package quest.theobomos;

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






















public class _3093RecetteSecretedeQuenelles
  extends QuestHandler
{
  private static final int questId = 3093;
  
  public _3093RecetteSecretedeQuenelles() {
    super(Integer.valueOf(3093));
  }


  
  public void register() {
    this.qe.setNpcQuestData(798185).addOnQuestStart(3093);
    this.qe.setNpcQuestData(798185).addOnTalkEvent(3093);
    this.qe.setNpcQuestData(798177).addOnTalkEvent(3093);
    this.qe.setNpcQuestData(798179).addOnTalkEvent(3093);
    this.qe.setNpcQuestData(203784).addOnTalkEvent(3093);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(3093);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 798185) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 1002) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          
          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206062, 1))));
          return true;
        } 

        
        return defaultQuestStartDialog(env);
      } 
    } else {
      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009) {
          
          qs.setQuestVar(4);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        } 
        
        return defaultQuestEndDialog(env);
      } 
      
      if (targetId == 798177) {

        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (env.getDialogId().intValue() == 10000) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        
        }
      
      }
      else if (targetId == 798179) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
        {
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          if (env.getDialogId().intValue() == 10001) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        }
      
      }
      else if (targetId == 203784) {
        
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
          
          if (env.getDialogId().intValue() == 25)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          if (env.getDialogId().intValue() == 10002) {
            
            if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182208052, 1))));
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return defaultQuestStartDialog(env);
        } 
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3093RecetteSecretedeQuenelles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

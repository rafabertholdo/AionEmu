package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;





















public class _1311AGermOfHope
  extends QuestHandler
{
  private static final int questId = 1311;
  
  public _1311AGermOfHope() {
    super(Integer.valueOf(1311));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203997).addOnQuestStart(1311);
    this.qe.setNpcQuestData(203997).addOnTalkEvent(1311);
    this.qe.setNpcQuestData(700164).addOnTalkEvent(1311);
    this.qe.setNpcQuestData(203997).addOnTalkEvent(1311);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    final QuestState qs = player.getQuestStateList().getQuestState(1311);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203997)
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 1013) {
          
          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201305, 1)))) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4);
          }
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      }
    
    } else if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 700164:
          if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    if (!player.isTargeting(targetObjectId))
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    
                    ItemService.removeItemFromInventoryByItemId(player, 182201305);
                    qs.setStatus(QuestStatus.REWARD);
                    qs.setQuestVarById(0, 1);
                    _1311AGermOfHope.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 


        
        case 203997:
          if (qs.getQuestVarById(0) == 1) {
            
            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
            if (env.getDialogId().intValue() == 33) {
              
              ItemService.removeItemFromInventoryByItemId(player, 182201305);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
            } 
            
            return defaultQuestEndDialog(env);
          } 
          break;
      } 
    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203997)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1311AGermOfHope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

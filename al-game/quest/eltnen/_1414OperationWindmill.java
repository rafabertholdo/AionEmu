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





















public class _1414OperationWindmill
  extends QuestHandler
{
  private static final int questId = 1414;
  
  public _1414OperationWindmill() {
    super(Integer.valueOf(1414));
  }


  
  public void register() {
    this.qe.setNpcQuestData(203989).addOnQuestStart(1414);
    this.qe.setNpcQuestData(203989).addOnTalkEvent(1414);
    this.qe.setNpcQuestData(700175).addOnTalkEvent(1414);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1414);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203989) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 1002) {
          
          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201349, 1)))) {
            return defaultQuestStartDialog(env);
          }
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
    } else {
      
      if (qs != null && qs.getStatus() == QuestStatus.REWARD)
      {
        return defaultQuestEndDialog(env);
      }

      
      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
      {
        switch (targetId) {

          
          case 700175:
            if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {

              
              final int targetObjectId = env.getVisibleObject().getObjectId();
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
              
              PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
              
              ThreadPoolManager.getInstance().schedule(new Runnable()
                  {
                    public void run()
                    {
                      if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
                        return; 
                      QuestState qs = player.getQuestStateList().getQuestState(1414);
                      qs.setQuestVar(1);
                      qs.setStatus(QuestStatus.REWARD);
                      _1414OperationWindmill.this.updateQuestStatus(player, qs);
                      ItemService.removeItemFromInventoryByItemId(player, 182201349);
                      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                      
                      PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    }
                  }3000L);
            } 
            break;
        } 
      }
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1414OperationWindmill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

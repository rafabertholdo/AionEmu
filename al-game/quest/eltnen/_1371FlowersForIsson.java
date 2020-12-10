package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
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




















public class _1371FlowersForIsson
  extends QuestHandler
{
  private static final int questId = 1371;
  
  public _1371FlowersForIsson() {
    super(Integer.valueOf(1371));
  }



  
  public void register() {
    this.qe.setNpcQuestData(203949).addOnQuestStart(1371);
    this.qe.setNpcQuestData(203949).addOnTalkEvent(1371);
    this.qe.setNpcQuestData(730039).addOnTalkEvent(1371);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1371);
    long itemCount = 0L;
    if (targetId == 203949) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START) {
        
        int var = qs.getQuestVarById(0);
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
            {
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
            }
          
          case 33:
            if (var == 0)
              itemCount = player.getInventory().getItemCountByItemId(152000601); 
            if (itemCount > 4L)
            {
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
            }

            
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
          
          case 10000:
            ItemService.removeItemFromInventoryByItemId(player, 152000601);
            qs.setQuestVar(2);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 0);
        } 

        
        return false;
      } 
      
      if (qs != null && qs.getStatus() == QuestStatus.REWARD)
      {
        return defaultQuestEndDialog(env);
      
      }
    }
    else if (targetId == 730039) {
      
      int var = qs.getQuestVarById(0);
      if (qs.getStatus() == QuestStatus.START && var == 2) {

        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
        
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
                  return; 
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
              }
            }3000L);
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1371FlowersForIsson.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

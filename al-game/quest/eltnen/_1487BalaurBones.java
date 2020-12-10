package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;




















public class _1487BalaurBones
  extends QuestHandler
{
  private static final int questId = 1487;
  
  public _1487BalaurBones() {
    super(Integer.valueOf(1487));
  }



  
  public void register() {
    this.qe.setNpcQuestData(798126).addOnQuestStart(1487);
    this.qe.setNpcQuestData(798126).addOnTalkEvent(1487);
    this.qe.setNpcQuestData(700313).addOnTalkEvent(1487);
    this.qe.setNpcQuestData(700314).addOnTalkEvent(1487);
    this.qe.setNpcQuestData(700315).addOnTalkEvent(1487);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1487);
    if (targetId == 798126) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START) {



        
        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 0)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        }
        if (env.getDialogId().intValue() == 33 && qs.getQuestVarById(0) == 0) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182201407);
          long itemCount1 = player.getInventory().getItemCountByItemId(182201408);
          long itemCount2 = player.getInventory().getItemCountByItemId(182201409);
          if (itemCount > 0L && itemCount1 > 2L && itemCount2 > 1L) {
            
            ItemService.removeItemFromInventoryByItemId(player, 182201407);
            ItemService.removeItemFromInventoryByItemId(player, 182201408);
            ItemService.removeItemFromInventoryByItemId(player, 182201409);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          } 

          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        } 

        
        return defaultQuestEndDialog(env);
      } 
      
      if (qs != null && qs.getStatus() == QuestStatus.REWARD)
      {
        return defaultQuestEndDialog(env);
      }
    } else {
      if (targetId == 700313) {

        
        long itemCount = player.getInventory().getItemCountByItemId(182201407);
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount < 1L) {
          
          final int targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                
                public void run()
                {
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                }
              }3000L);
          return true;
        } 

        
        return defaultQuestEndDialog(env);
      } 
      
      if (targetId == 700314) {

        
        long itemCount1 = player.getInventory().getItemCountByItemId(182201408);
        if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount1 < 3L) {
          
          final int targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                
                public void run()
                {
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                }
              }3000L);
          return true;
        } 

        
        return defaultQuestEndDialog(env);
      } 
      
      if (targetId == 700315) {

        
        long itemCount2 = player.getInventory().getItemCountByItemId(182201409);
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount2 < 2L) {
          
          final int targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                
                public void run()
                {
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                }
              }3000L);
          return true;
        } 

        
        return defaultQuestEndDialog(env);
      } 


      
      return defaultQuestEndDialog(env);
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1487BalaurBones.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

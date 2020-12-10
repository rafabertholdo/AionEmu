package quest.morheim;

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

public class _2495SpriggNightlights extends QuestHandler {
  private static final int questId = 2495;

  public _2495SpriggNightlights() {
    super(Integer.valueOf(2495));
  }

  public void register() {
    this.qe.setNpcQuestData(798125).addOnQuestStart(2495);
    this.qe.setNpcQuestData(798125).addOnTalkEvent(2495);
    this.qe.setNpcQuestData(700317).addOnTalkEvent(2495);
    this.qe.setNpcQuestData(700319).addOnTalkEvent(2495);
    this.qe.setNpcQuestData(700318).addOnTalkEvent(2495);
  }

  public boolean onDialogEvent(QuestEnv env) {
     final Player player = env.getPlayer();
     QuestState qs = player.getQuestStateList().getQuestState(2495);
     int targetId = 0;
     if (player.getCommonData().getLevel() < 21)
       return false; 
     if (env.getVisibleObject() instanceof Npc)
       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
     if (targetId == 798125) {
       
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
           
           long itemCount = player.getInventory().getItemCountByItemId(182204227);
           long itemCount1 = player.getInventory().getItemCountByItemId(182204228);
           long itemCount2 = player.getInventory().getItemCountByItemId(182204229);
           if (itemCount > 4L && itemCount1 > 4L && itemCount2 > 4L) {
             
             ItemService.removeItemFromInventoryByItemId(player, 182204227);
             ItemService.removeItemFromInventoryByItemId(player, 182204228);
             ItemService.removeItemFromInventoryByItemId(player, 182204229);
             qs.setStatus(QuestStatus.REWARD);
             updateQuestStatus(player, qs);
             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
           } 
 
           
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
         } 
 
         
         return defaultQuestEndDialog(env);
       } 
       if (qs.getStatus() == QuestStatus.REWARD) {
         
         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 5)
         {
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
         }
         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 6)
         {
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
         }
         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 7)
         {
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
         }
 
         
         return defaultQuestEndDialog(env);
       } 
 
 
       
       return defaultQuestEndDialog(env);
     } 
     
     if (targetId == 700317) {
 
       
       long itemCount = player.getInventory().getItemCountByItemId(182204227);
       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount < 5L) {
         
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
     
     if (targetId == 700318) {
 
       
       long itemCount1 = player.getInventory().getItemCountByItemId(182204228);
       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount1 < 5L) {
         
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
     
     if (targetId == 700319) {
 
       
       long itemCount2 = player.getInventory().getItemCountByItemId(182204229);
       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount2 < 5L) {
         
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
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2495SpriggNightlights.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
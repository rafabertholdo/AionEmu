/*    */ package quest.eltnen;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import com.aionemu.gameserver.services.ItemService;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class _1351EarningMaranasRespect
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 1351;
/*    */   
/*    */   public _1351EarningMaranasRespect() {
/* 36 */     super(Integer.valueOf(1351));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 42 */     this.qe.setNpcQuestData(203965).addOnQuestStart(1351);
/* 43 */     this.qe.setNpcQuestData(203965).addOnTalkEvent(1351);
/* 44 */     this.qe.setNpcQuestData(203983).addOnTalkEvent(1351);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 50 */     Player player = env.getPlayer();
/* 51 */     int targetId = 0;
/* 52 */     if (env.getVisibleObject() instanceof Npc)
/* 53 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 54 */     QuestState qs = player.getQuestStateList().getQuestState(1351);
/*    */     
/* 56 */     if (targetId == 203965) {
/*    */       
/* 58 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*    */       {
/* 60 */         if (env.getDialogId().intValue() == 25) {
/* 61 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*    */         }
/* 63 */         return defaultQuestStartDialog(env);
/*    */       }
/*    */     
/* 66 */     } else if (targetId == 203983) {
/*    */       
/* 68 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*    */         
/* 70 */         if (env.getDialogId().intValue() == 25)
/* 71 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 72 */         if (env.getDialogId().intValue() == 33) {
/*    */           
/* 74 */           long itemCount = player.getInventory().getItemCountByItemId(182201321);
/* 75 */           if (itemCount > 9L) {
/*    */             
/* 77 */             ItemService.removeItemFromInventoryByItemId(player, 182201321);
/* 78 */             qs.setStatus(QuestStatus.REWARD);
/* 79 */             updateQuestStatus(player, qs);
/* 80 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*    */           } 
/*    */           
/* 83 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
/*    */         } 
/*    */         
/* 86 */         return defaultQuestStartDialog(env);
/*    */       } 
/* 88 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*    */       {
/* 90 */         return defaultQuestEndDialog(env);
/*    */       }
/*    */     } 
/* 93 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1351EarningMaranasRespect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
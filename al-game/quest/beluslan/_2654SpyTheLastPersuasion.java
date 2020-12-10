/*    */ package quest.beluslan;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class _2654SpyTheLastPersuasion
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 2654;
/*    */   
/*    */   public _2654SpyTheLastPersuasion() {
/* 20 */     super(Integer.valueOf(2654));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 26 */     this.qe.setNpcQuestData(204775).addOnQuestStart(2654);
/* 27 */     this.qe.setNpcQuestData(204775).addOnTalkEvent(2654);
/* 28 */     this.qe.setNpcQuestData(204655).addOnTalkEvent(2654);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onLvlUpEvent(QuestEnv env) {
/* 33 */     Player player = env.getPlayer();
/* 34 */     QuestState qs = player.getQuestStateList().getQuestState(2654);
/* 35 */     QuestState qs2 = player.getQuestStateList().getQuestState(2653);
/* 36 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/* 37 */       return false; 
/* 38 */     qs.setStatus(QuestStatus.START);
/* 39 */     updateQuestStatus(player, qs);
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 46 */     Player player = env.getPlayer();
/* 47 */     int targetId = 0;
/* 48 */     if (env.getVisibleObject() instanceof Npc)
/* 49 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 50 */     QuestState qs = player.getQuestStateList().getQuestState(2654);
/* 51 */     if (targetId == 204775) {
/*    */       
/* 53 */       if (qs == null)
/*    */       {
/* 55 */         if (env.getDialogId().intValue() == 25) {
/* 56 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*    */         }
/* 58 */         return defaultQuestStartDialog(env);
/*    */       }
/*    */     
/* 61 */     } else if (targetId == 204655) {
/*    */       
/* 63 */       if (qs != null) {
/*    */         
/* 65 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 66 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 67 */         if (env.getDialogId().intValue() == 1009) {
/*    */           
/* 69 */           qs.setQuestVar(0);
/* 70 */           qs.setStatus(QuestStatus.REWARD);
/* 71 */           updateQuestStatus(player, qs);
/* 72 */           return defaultQuestEndDialog(env);
/*    */         } 
/*    */         
/* 75 */         return defaultQuestEndDialog(env);
/*    */       } 
/*    */     } 
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2654SpyTheLastPersuasion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
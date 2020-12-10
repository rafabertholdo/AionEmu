/*    */ package quest.eltnen;
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
/*    */ public class _1470HannetsVengeance
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 1470;
/*    */   
/*    */   public _1470HannetsVengeance() {
/* 37 */     super(Integer.valueOf(1470));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 43 */     this.qe.setNpcQuestData(790004).addOnQuestStart(1470);
/* 44 */     this.qe.setNpcQuestData(790004).addOnTalkEvent(1470);
/* 45 */     this.qe.setNpcQuestData(212846).addOnKillEvent(1470);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 51 */     Player player = env.getPlayer();
/* 52 */     int targetId = 0;
/*    */     
/* 54 */     if (env.getVisibleObject() instanceof Npc)
/* 55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 56 */     QuestState qs = player.getQuestStateList().getQuestState(1470);
/* 57 */     if (targetId == 790004) {
/*    */       
/* 59 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*    */         
/* 61 */         if (env.getDialogId().intValue() == 25) {
/* 62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*    */         }
/* 64 */         return defaultQuestStartDialog(env);
/*    */       } 
/* 66 */       if (qs.getStatus() == QuestStatus.REWARD)
/*    */       {
/* 68 */         return defaultQuestEndDialog(env);
/*    */       }
/*    */     } 
/* 71 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onKillEvent(QuestEnv env) {
/* 76 */     Player player = env.getPlayer();
/* 77 */     QuestState qs = player.getQuestStateList().getQuestState(1470);
/* 78 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 79 */       return false;
/*    */     }
/* 81 */     int var = qs.getQuestVarById(0);
/* 82 */     int targetId = 0;
/* 83 */     if (env.getVisibleObject() instanceof Npc)
/* 84 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 85 */     switch (targetId) {
/*    */       
/*    */       case 212846:
/* 88 */         qs.setQuestVarById(0, var + 1);
/* 89 */         updateQuestStatus(player, qs);
/* 90 */         qs.setStatus(QuestStatus.REWARD);
/* 91 */         updateQuestStatus(player, qs);
/* 92 */         return true;
/*    */     } 
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1470HannetsVengeance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
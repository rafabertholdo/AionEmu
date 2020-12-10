/*    */ package quest.verteron;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import com.aionemu.gameserver.services.QuestService;
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
/*    */ public class _1018MarkofVengeance
/*    */   extends QuestHandler
/*    */ {
/*    */   private static final int questId = 1018;
/*    */   
/*    */   public _1018MarkofVengeance() {
/* 37 */     super(Integer.valueOf(1018));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void register() {
/* 43 */     this.qe.addQuestLvlUp(1018);
/* 44 */     this.qe.setNpcQuestData(203098).addOnTalkEvent(1018);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onLvlUpEvent(QuestEnv env) {
/* 50 */     Player player = env.getPlayer();
/* 51 */     QuestState qs = player.getQuestStateList().getQuestState(1018);
/* 52 */     boolean lvlCheck = QuestService.checkLevelRequirement(1018, player.getCommonData().getLevel());
/* 53 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/* 54 */       return false; 
/* 55 */     qs.setStatus(QuestStatus.START);
/* 56 */     updateQuestStatus(player, qs);
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onDialogEvent(QuestEnv env) {
/* 63 */     Player player = env.getPlayer();
/* 64 */     QuestState qs = player.getQuestStateList().getQuestState(1018);
/* 65 */     if (qs == null) {
/* 66 */       return false;
/*    */     }
/* 68 */     int var = qs.getQuestVarById(0);
/* 69 */     int targetId = 0;
/* 70 */     if (env.getVisibleObject() instanceof Npc) {
/* 71 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*    */     }
/* 73 */     if (targetId == 203098 && qs.getStatus() == QuestStatus.START) {
/*    */       
/* 75 */       switch (env.getDialogId().intValue()) {
/*    */         
/*    */         case 25:
/* 78 */           if (var == 0)
/* 79 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*    */         case 33:
/* 81 */           if (QuestService.collectItemCheck(env, true)) {
/*    */             
/* 83 */             qs.setQuestVarById(0, var + 1);
/* 84 */             qs.setStatus(QuestStatus.REWARD);
/* 85 */             updateQuestStatus(player, qs);
/* 86 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*    */           } 
/*    */           
/* 89 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1097);
/*    */       } 
/*    */     
/* 92 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*    */       
/* 94 */       if (targetId == 203098)
/* 95 */         return defaultQuestEndDialog(env); 
/*    */     } 
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1018MarkofVengeance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
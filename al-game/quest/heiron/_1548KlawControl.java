/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _1548KlawControl
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1548;
/*     */   
/*     */   public _1548KlawControl() {
/*  36 */     super(Integer.valueOf(1548));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  42 */     this.qe.setNpcQuestData(204583).addOnQuestStart(1548);
/*  43 */     this.qe.setNpcQuestData(204583).addOnTalkEvent(1548);
/*  44 */     this.qe.setNpcQuestData(700169).addOnKillEvent(1548);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  50 */     Player player = env.getPlayer();
/*  51 */     int targetId = 0;
/*     */     
/*  53 */     if (env.getVisibleObject() instanceof Npc)
/*  54 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  55 */     QuestState qs = player.getQuestStateList().getQuestState(1548);
/*  56 */     if (targetId == 204583) {
/*     */       
/*  58 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  60 */         if (env.getDialogId().intValue() == 25) {
/*  61 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  63 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  65 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  67 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  75 */     Player player = env.getPlayer();
/*  76 */     QuestState qs = player.getQuestStateList().getQuestState(1548);
/*  77 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/*  78 */       return false;
/*     */     }
/*  80 */     int var = qs.getQuestVarById(0);
/*  81 */     int targetId = 0;
/*  82 */     if (env.getVisibleObject() instanceof Npc)
/*  83 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  84 */     switch (targetId) {
/*     */       
/*     */       case 700169:
/*  87 */         if (var >= 0 && var < 4) {
/*     */           
/*  89 */           qs.setQuestVarById(0, var + 1);
/*  90 */           updateQuestStatus(player, qs);
/*  91 */           return true;
/*     */         } 
/*  93 */         if (var == 4) {
/*     */           
/*  95 */           qs.setQuestVarById(0, var + 1);
/*  96 */           updateQuestStatus(player, qs);
/*  97 */           qs.setStatus(QuestStatus.REWARD);
/*  98 */           updateQuestStatus(player, qs);
/*  99 */           return true;
/*     */         }  break;
/*     */     } 
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1548KlawControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
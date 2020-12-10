/*     */ package quest.eltnen;
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
/*     */ public class _1346KillingforCastor
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1346;
/*  33 */   private static final int[] mob_ids = new int[] { 210898, 210878, 210872, 210844 };
/*     */ 
/*     */   
/*     */   public _1346KillingforCastor() {
/*  37 */     super(Integer.valueOf(1346));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(203966).addOnQuestStart(1346);
/*  44 */     this.qe.setNpcQuestData(203966).addOnTalkEvent(1346);
/*  45 */     this.qe.setNpcQuestData(203965).addOnTalkEvent(1346);
/*  46 */     for (int mob_id : mob_ids) {
/*  47 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1346);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     QuestState qs = player.getQuestStateList().getQuestState(1346);
/*  55 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/*  56 */       return false;
/*     */     }
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc) {
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  62 */     switch (targetId) {
/*     */       
/*     */       case 210844:
/*     */       case 210872:
/*  66 */         if (qs.getQuestVarById(0) < 15) {
/*     */           
/*  68 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  69 */           updateQuestStatus(player, qs);
/*  70 */           return true;
/*     */         } 
/*     */         break;
/*     */       case 210878:
/*     */       case 210898:
/*  75 */         if (qs.getQuestVarById(1) < 20) {
/*     */           
/*  77 */           qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
/*  78 */           updateQuestStatus(player, qs);
/*  79 */           return true;
/*     */         }  break;
/*     */     } 
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  88 */     Player player = env.getPlayer();
/*  89 */     QuestState qs = player.getQuestStateList().getQuestState(1346);
/*  90 */     if (player.getCommonData().getLevel() < 27)
/*  91 */       return false; 
/*  92 */     int targetId = 0;
/*  93 */     if (env.getVisibleObject() instanceof Npc) {
/*  94 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  96 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  98 */       if (targetId == 203966)
/*     */       {
/* 100 */         if (env.getDialogId().intValue() == 25) {
/* 101 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/* 103 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 106 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/* 108 */       if (targetId == 203965)
/*     */       {
/* 110 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(1) == 20 && qs.getQuestVarById(0) == 15) {
/*     */           
/* 112 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 113 */           qs.setStatus(QuestStatus.REWARD);
/* 114 */           updateQuestStatus(player, qs);
/* 115 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         } 
/*     */         
/* 118 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 121 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 123 */       if (targetId == 203965) {
/*     */         
/* 125 */         if (env.getDialogId().intValue() == 25)
/* 126 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 127 */         if (env.getDialogId().intValue() == 1009) {
/* 128 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 130 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 133 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1346KillingforCastor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
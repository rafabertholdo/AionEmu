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
/*     */ 
/*     */ public class _3326TheShugoMenace
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3326;
/*     */   
/*     */   public _3326TheShugoMenace() {
/*  36 */     super(Integer.valueOf(3326));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  42 */     this.qe.setNpcQuestData(798053).addOnQuestStart(3326);
/*  43 */     this.qe.setNpcQuestData(798053).addOnTalkEvent(3326);
/*  44 */     this.qe.setNpcQuestData(210897).addOnKillEvent(3326);
/*  45 */     this.qe.setNpcQuestData(210939).addOnKillEvent(3326);
/*  46 */     this.qe.setNpcQuestData(210873).addOnKillEvent(3326);
/*  47 */     this.qe.setNpcQuestData(210919).addOnKillEvent(3326);
/*  48 */     this.qe.setNpcQuestData(211754).addOnKillEvent(3326);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     QuestState qs = player.getQuestStateList().getQuestState(3326);
/*     */     
/*  57 */     int targetId = 0;
/*  58 */     if (env.getVisibleObject() instanceof Npc) {
/*  59 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  61 */     if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE)
/*     */     {
/*  63 */       if (targetId == 798053) {
/*     */         
/*  65 */         if (env.getDialogId().intValue() == 25)
/*     */         {
/*  67 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4);
/*     */         }
/*     */         
/*  70 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */     
/*  74 */     if (qs == null) {
/*  75 */       return false;
/*     */     }
/*  77 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  79 */       if (targetId == 798053)
/*     */       {
/*  81 */         switch (env.getDialogId().intValue()) {
/*     */ 
/*     */           
/*     */           case 25:
/*  85 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
/*     */ 
/*     */           
/*     */           case 1009:
/*  89 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  90 */             qs.setStatus(QuestStatus.REWARD);
/*  91 */             updateQuestStatus(player, qs);
/*  92 */             return defaultQuestEndDialog(env);
/*     */         } 
/*     */ 
/*     */       
/*     */       }
/*  97 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  99 */       if (targetId == 798053) {
/*     */         
/* 101 */         if (env.getDialogId().intValue() == 1009) {
/* 102 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 104 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 113 */     Player player = env.getPlayer();
/* 114 */     QuestState qs = player.getQuestStateList().getQuestState(3326);
/* 115 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 116 */       return false;
/*     */     }
/* 118 */     int var = qs.getQuestVarById(0);
/* 119 */     int targetId = 0;
/* 120 */     if (env.getVisibleObject() instanceof Npc) {
/* 121 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 123 */     if (targetId == 210897 || targetId == 210939 || targetId == 210873 || targetId == 210919 || targetId == 211754)
/*     */     {
/* 125 */       if (var >= 0 && var < 20) {
/*     */         
/* 127 */         qs.setQuestVarById(0, var + 1);
/* 128 */         updateQuestStatus(player, qs);
/* 129 */         return true;
/*     */       } 
/*     */     }
/* 132 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_3326TheShugoMenace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
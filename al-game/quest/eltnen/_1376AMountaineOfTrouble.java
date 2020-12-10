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
/*     */ 
/*     */ public class _1376AMountaineOfTrouble
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1376;
/*     */   
/*     */   public _1376AMountaineOfTrouble() {
/*  37 */     super(Integer.valueOf(1376));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(203947).addOnQuestStart(1376);
/*  44 */     this.qe.setNpcQuestData(203947).addOnTalkEvent(1376);
/*  45 */     this.qe.setNpcQuestData(203964).addOnTalkEvent(1376);
/*  46 */     this.qe.setNpcQuestData(210976).addOnKillEvent(1376);
/*  47 */     this.qe.setNpcQuestData(210986).addOnKillEvent(1376);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*     */     
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1376);
/*  59 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  61 */       if (targetId == 203947)
/*     */       {
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  69 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  71 */       if (targetId == 203964) {
/*     */         
/*  73 */         if (env.getDialogId().intValue() == -1)
/*  74 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  75 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  83 */     Player player = env.getPlayer();
/*  84 */     QuestState qs = player.getQuestStateList().getQuestState(1376);
/*  85 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/*  86 */       return false;
/*     */     }
/*  88 */     int var = qs.getQuestVarById(0);
/*  89 */     int targetId = 0;
/*  90 */     if (env.getVisibleObject() instanceof Npc)
/*  91 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  92 */     switch (targetId) {
/*     */       
/*     */       case 210976:
/*  95 */         if (var >= 0 && var < 6) {
/*     */           
/*  97 */           qs.setQuestVarById(0, var + 1);
/*  98 */           updateQuestStatus(player, qs);
/*  99 */           return true;
/*     */         } 
/* 101 */         if (var == 6) {
/*     */           
/* 103 */           qs.setStatus(QuestStatus.REWARD);
/* 104 */           updateQuestStatus(player, qs);
/* 105 */           return true;
/*     */         } 
/*     */       case 210986:
/* 108 */         if (var >= 0 && var < 6) {
/*     */           
/* 110 */           qs.setQuestVarById(0, var + 1);
/* 111 */           updateQuestStatus(player, qs);
/* 112 */           return true;
/*     */         } 
/* 114 */         if (var == 6) {
/*     */           
/* 116 */           qs.setStatus(QuestStatus.REWARD);
/* 117 */           updateQuestStatus(player, qs);
/* 118 */           return true;
/*     */         }  break;
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1376AMountaineOfTrouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
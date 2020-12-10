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
/*     */ public class _1347RaidingKlaw
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1347;
/*  33 */   private static final int[] npc_ids = new int[] { 203965, 203966 };
/*  34 */   private static final int[] mob_ids = new int[] { 210908, 210874, 212137, 212056, 210917 };
/*     */ 
/*     */   
/*     */   public _1347RaidingKlaw() {
/*  38 */     super(Integer.valueOf(1347));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203965).addOnQuestStart(1347);
/*  45 */     for (int npc_id : npc_ids)
/*  46 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1347); 
/*  47 */     for (int mob_id : mob_ids) {
/*  48 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1347);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     int targetId = 0;
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1347);
/*  59 */     if (targetId == 203965)
/*     */     {
/*  61 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*  69 */     if (qs == null) {
/*  70 */       return false;
/*     */     }
/*  72 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  74 */       if (targetId == 203966)
/*     */       {
/*  76 */         if (env.getDialogId().intValue() == 25) {
/*     */           
/*  78 */           qs.setStatus(QuestStatus.REWARD);
/*  79 */           updateQuestStatus(player, qs);
/*  80 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         } 
/*     */       }
/*  83 */       return false;
/*     */     } 
/*  85 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  87 */       if (targetId == 203966) {
/*     */         
/*  89 */         if (env.getDialogId().intValue() == 1009)
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  91 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  93 */       return false;
/*     */     } 
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 101 */     Player player = env.getPlayer();
/* 102 */     QuestState qs = player.getQuestStateList().getQuestState(1347);
/* 103 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 104 */       return false;
/*     */     }
/* 106 */     int targetId = 0;
/* 107 */     if (env.getVisibleObject() instanceof Npc) {
/* 108 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 110 */     switch (targetId) {
/*     */       
/*     */       case 210874:
/*     */       case 210908:
/* 114 */         if (qs.getQuestVarById(0) < 15) {
/*     */           
/* 116 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 117 */           updateQuestStatus(player, qs);
/* 118 */           return true;
/*     */         } 
/*     */         break;
/*     */       case 210917:
/*     */       case 212056:
/*     */       case 212137:
/* 124 */         if (qs.getQuestVarById(1) < 7) {
/*     */           
/* 126 */           qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
/* 127 */           updateQuestStatus(player, qs);
/* 128 */           return true;
/*     */         }  break;
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1347RaidingKlaw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
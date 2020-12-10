/*     */ package quest.ishalgen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _2132ANewSkill
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2132;
/*     */   
/*     */   public _2132ANewSkill() {
/*  38 */     super(Integer.valueOf(2132));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.addQuestLvlUp(2132);
/*  45 */     this.qe.setNpcQuestData(203527).addOnTalkEvent(2132);
/*  46 */     this.qe.setNpcQuestData(203528).addOnTalkEvent(2132);
/*  47 */     this.qe.setNpcQuestData(203529).addOnTalkEvent(2132);
/*  48 */     this.qe.setNpcQuestData(203530).addOnTalkEvent(2132);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     boolean lvlCheck = QuestService.checkLevelRequirement(2132, player.getCommonData().getLevel());
/*  56 */     if (!lvlCheck)
/*  57 */       return false; 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(2132);
/*  59 */     if (qs != null)
/*  60 */       return false; 
/*  61 */     env.setQuestId(Integer.valueOf(2132));
/*  62 */     if (QuestService.startQuest(env, QuestStatus.START)) {
/*     */       
/*  64 */       qs = player.getQuestStateList().getQuestState(2132);
/*  65 */       qs.setStatus(QuestStatus.REWARD);
/*  66 */       switch (player.getCommonData().getPlayerClass()) {
/*     */         
/*     */         case WARRIOR:
/*  69 */           qs.setQuestVar(1);
/*     */           break;
/*     */         case SCOUT:
/*  72 */           qs.setQuestVar(2);
/*     */           break;
/*     */         case MAGE:
/*  75 */           qs.setQuestVar(3);
/*     */           break;
/*     */         case PRIEST:
/*  78 */           qs.setQuestVar(4);
/*     */           break;
/*     */       } 
/*  81 */       updateQuestStatus(player, qs);
/*     */     } 
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  89 */     Player player = env.getPlayer();
/*  90 */     QuestState qs = player.getQuestStateList().getQuestState(2132);
/*  91 */     if (qs == null || qs.getStatus() != QuestStatus.REWARD) {
/*  92 */       return false;
/*     */     }
/*  94 */     int targetId = 0;
/*  95 */     if (env.getVisibleObject() instanceof Npc) {
/*  96 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  98 */     PlayerClass playerClass = player.getCommonData().getPlayerClass();
/*  99 */     switch (targetId) {
/*     */       
/*     */       case 203527:
/* 102 */         if (playerClass == PlayerClass.WARRIOR) {
/*     */           
/* 104 */           if (env.getDialogId().intValue() == -1)
/* 105 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 106 */           if (env.getDialogId().intValue() == 1009) {
/* 107 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           }
/* 109 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 111 */         return false;
/*     */       case 203528:
/* 113 */         if (playerClass == PlayerClass.SCOUT) {
/*     */           
/* 115 */           if (env.getDialogId().intValue() == -1)
/* 116 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 117 */           if (env.getDialogId().intValue() == 1009) {
/* 118 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           }
/* 120 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 122 */         return false;
/*     */       case 203529:
/* 124 */         if (playerClass == PlayerClass.MAGE) {
/*     */           
/* 126 */           if (env.getDialogId().intValue() == -1)
/* 127 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 128 */           if (env.getDialogId().intValue() == 1009) {
/* 129 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */           }
/* 131 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 133 */         return false;
/*     */       case 203530:
/* 135 */         if (playerClass == PlayerClass.PRIEST) {
/*     */           
/* 137 */           if (env.getDialogId().intValue() == -1)
/* 138 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 139 */           if (env.getDialogId().intValue() == 1009) {
/* 140 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
/*     */           }
/* 142 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 144 */         return false;
/*     */     } 
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2132ANewSkill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
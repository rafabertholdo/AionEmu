/*     */ package quest.poeta;
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
/*     */ public class _1205ANewSkill
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1205;
/*     */   
/*     */   public _1205ANewSkill() {
/*  38 */     super(Integer.valueOf(1205));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.addQuestLvlUp(1205);
/*  45 */     this.qe.setNpcQuestData(203087).addOnTalkEvent(1205);
/*  46 */     this.qe.setNpcQuestData(203088).addOnTalkEvent(1205);
/*  47 */     this.qe.setNpcQuestData(203089).addOnTalkEvent(1205);
/*  48 */     this.qe.setNpcQuestData(203090).addOnTalkEvent(1205);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     boolean lvlCheck = QuestService.checkLevelRequirement(1205, player.getCommonData().getLevel());
/*  56 */     if (!lvlCheck)
/*  57 */       return false; 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1205);
/*  59 */     if (qs != null)
/*  60 */       return false; 
/*  61 */     env.setQuestId(Integer.valueOf(1205));
/*  62 */     if (QuestService.startQuest(env, QuestStatus.START)) {
/*     */       
/*  64 */       qs = player.getQuestStateList().getQuestState(1205);
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
/*  90 */     QuestState qs = player.getQuestStateList().getQuestState(1205);
/*  91 */     if (qs == null || qs.getStatus() != QuestStatus.REWARD) {
/*  92 */       return false;
/*     */     }
/*  94 */     int targetId = 0;
/*  95 */     if (env.getVisibleObject() instanceof Npc)
/*  96 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  97 */     PlayerClass playerClass = player.getCommonData().getPlayerClass();
/*  98 */     switch (targetId) {
/*     */       
/*     */       case 203087:
/* 101 */         if (playerClass == PlayerClass.WARRIOR) {
/*     */           
/* 103 */           if (env.getDialogId().intValue() == -1)
/* 104 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 105 */           if (env.getDialogId().intValue() == 1009) {
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           }
/* 108 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 110 */         return false;
/*     */       case 203088:
/* 112 */         if (playerClass == PlayerClass.SCOUT) {
/*     */           
/* 114 */           if (env.getDialogId().intValue() == -1)
/* 115 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 116 */           if (env.getDialogId().intValue() == 1009) {
/* 117 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           }
/* 119 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 121 */         return false;
/*     */       case 203089:
/* 123 */         if (playerClass == PlayerClass.MAGE) {
/*     */           
/* 125 */           if (env.getDialogId().intValue() == -1)
/* 126 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 127 */           if (env.getDialogId().intValue() == 1009) {
/* 128 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */           }
/* 130 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 132 */         return false;
/*     */       case 203090:
/* 134 */         if (playerClass == PlayerClass.PRIEST) {
/*     */           
/* 136 */           if (env.getDialogId().intValue() == -1)
/* 137 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 138 */           if (env.getDialogId().intValue() == 1009) {
/* 139 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
/*     */           }
/* 141 */           return defaultQuestEndDialog(env);
/*     */         } 
/* 143 */         return false;
/*     */     } 
/*     */     
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1205ANewSkill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
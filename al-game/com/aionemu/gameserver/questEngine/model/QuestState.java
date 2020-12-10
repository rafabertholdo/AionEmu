/*     */ package com.aionemu.gameserver.questEngine.model;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
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
/*     */ public class QuestState
/*     */ {
/*     */   private final int questId;
/*     */   private QuestVars questVars;
/*     */   private QuestStatus status;
/*     */   private int completeCount;
/*     */   private PersistentState persistentState;
/*     */   
/*     */   public QuestState(int questId) {
/*  35 */     this.questId = questId;
/*  36 */     this.status = QuestStatus.START;
/*  37 */     this.questVars = new QuestVars();
/*  38 */     this.completeCount = 0;
/*  39 */     this.persistentState = PersistentState.NEW;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuestState(int questId, QuestStatus status, int questVars, int completeCount) {
/*  44 */     this.questId = questId;
/*  45 */     this.status = status;
/*  46 */     this.questVars = new QuestVars(questVars);
/*  47 */     this.completeCount = completeCount;
/*  48 */     this.persistentState = PersistentState.NEW;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuestVars getQuestVars() {
/*  53 */     return this.questVars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQuestVarById(int id, int var) {
/*  62 */     this.questVars.setVarById(id, var);
/*  63 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQuestVarById(int id) {
/*  72 */     return this.questVars.getVarById(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setQuestVar(int var) {
/*  77 */     this.questVars.setVar(var);
/*  78 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuestStatus getStatus() {
/*  83 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(QuestStatus status) {
/*  88 */     this.status = status;
/*  89 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getQuestId() {
/*  94 */     return this.questId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompliteCount(int completeCount) {
/*  99 */     this.completeCount = completeCount;
/* 100 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCompliteCount() {
/* 105 */     return this.completeCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 113 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 121 */     switch (persistentState) {
/*     */       
/*     */       case DELETED:
/* 124 */         if (this.persistentState == PersistentState.NEW)
/* 125 */           throw new IllegalArgumentException("Cannot change state to DELETED from NEW"); 
/*     */       case UPDATE_REQUIRED:
/* 127 */         if (this.persistentState == PersistentState.NEW)
/*     */           return;  break;
/*     */     } 
/* 130 */     this.persistentState = persistentState;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\QuestState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
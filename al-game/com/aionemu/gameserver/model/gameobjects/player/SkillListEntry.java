/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SkillListEntry
/*     */ {
/*     */   private int skillId;
/*     */   private int skillLvl;
/*     */   private boolean isStigma;
/*     */   private int currentXp;
/*     */   private PersistentState persistentState;
/*     */   
/*     */   public SkillListEntry(int skillId, boolean isStigma, int skillLvl, PersistentState persistentState) {
/*  43 */     this.skillId = skillId;
/*  44 */     this.skillLvl = skillLvl;
/*  45 */     this.isStigma = isStigma;
/*  46 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillId() {
/*  54 */     return this.skillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillLevel() {
/*  62 */     return this.skillLvl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStigma() {
/*  71 */     return this.isStigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSkillName() {
/*  80 */     return DataManager.SKILL_DATA.getSkillTemplate(this.skillId).getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkillLvl(int skillLvl) {
/*  88 */     this.skillLvl = skillLvl;
/*  89 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraLvl() {
/*  97 */     switch (this.skillId) {
/*     */       
/*     */       case 30002:
/*     */       case 30003:
/*     */       case 40001:
/*     */       case 40002:
/*     */       case 40003:
/*     */       case 40004:
/*     */       case 40007:
/*     */       case 40008:
/* 107 */         return this.skillLvl / 100;
/*     */     } 
/* 109 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentXp() {
/* 116 */     return this.currentXp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentXp(int currentXp) {
/* 124 */     this.currentXp = currentXp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addSkillXp(int xp) {
/* 133 */     this.currentXp += xp;
/* 134 */     if (this.currentXp > 0.15D * (this.skillLvl + 30) * (this.skillLvl + 30)) {
/*     */       
/* 136 */       this.currentXp = 0;
/* 137 */       setSkillLvl(this.skillLvl + 1);
/* 138 */       return true;
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 148 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 156 */     switch (persistentState) {
/*     */       
/*     */       case DELETED:
/* 159 */         if (this.persistentState == PersistentState.NEW) {
/* 160 */           this.persistentState = PersistentState.NOACTION;
/*     */         } else {
/* 162 */           this.persistentState = PersistentState.DELETED;
/*     */         }  return;
/*     */       case UPDATE_REQUIRED:
/* 165 */         if (this.persistentState != PersistentState.NEW)
/* 166 */           this.persistentState = PersistentState.UPDATE_REQUIRED; 
/*     */         return;
/*     */     } 
/* 169 */     this.persistentState = persistentState;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\SkillListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.model.gameobjects.player;
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
/*     */ public class PlayerSettings
/*     */ {
/*     */   private PersistentState persistentState;
/*     */   private byte[] uiSettings;
/*     */   private byte[] shortcuts;
/*  31 */   private int deny = 0;
/*  32 */   private int display = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerSettings() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerSettings(byte[] uiSettings, byte[] shortcuts, int deny, int display) {
/*  41 */     this.uiSettings = uiSettings;
/*  42 */     this.shortcuts = shortcuts;
/*  43 */     this.deny = deny;
/*  44 */     this.display = display;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/*  52 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/*  60 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getUiSettings() {
/*  68 */     return this.uiSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUiSettings(byte[] uiSettings) {
/*  76 */     this.uiSettings = uiSettings;
/*  77 */     this.persistentState = PersistentState.UPDATE_REQUIRED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getShortcuts() {
/*  85 */     return this.shortcuts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShortcuts(byte[] shortcuts) {
/*  93 */     this.shortcuts = shortcuts;
/*  94 */     this.persistentState = PersistentState.UPDATE_REQUIRED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplay() {
/* 102 */     return this.display;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplay(int display) {
/* 110 */     this.display = display;
/* 111 */     this.persistentState = PersistentState.UPDATE_REQUIRED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeny() {
/* 119 */     return this.deny;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeny(int deny) {
/* 127 */     this.deny = deny;
/* 128 */     this.persistentState = PersistentState.UPDATE_REQUIRED;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInDeniedStatus(DeniedStatus deny) {
/* 133 */     int isDeniedStatus = this.deny & deny.getId();
/*     */     
/* 135 */     if (isDeniedStatus == deny.getId()) {
/* 136 */       return true;
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\PlayerSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
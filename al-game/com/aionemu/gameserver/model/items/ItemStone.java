/*     */ package com.aionemu.gameserver.model.items;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStone
/*     */ {
/*     */   private int itemObjId;
/*     */   private int itemId;
/*     */   private int slot;
/*     */   private PersistentState persistentState;
/*     */   private ItemStoneType itemStoneType;
/*     */   
/*     */   public enum ItemStoneType
/*     */   {
/*  39 */     MANASTONE,
/*  40 */     GODSTONE;
/*     */   }
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
/*     */   public ItemStone(int itemObjId, int itemId, int slot, ItemStoneType itemStoneType, PersistentState persistentState) {
/*  53 */     this.itemObjId = itemObjId;
/*  54 */     this.itemId = itemId;
/*  55 */     this.slot = slot;
/*  56 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemObjId() {
/*  64 */     return this.itemObjId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemId() {
/*  72 */     return this.itemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlot() {
/*  80 */     return this.slot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlot(int slot) {
/*  88 */     this.slot = slot;
/*  89 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/*  97 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 106 */     switch (persistentState) {
/*     */       
/*     */       case DELETED:
/* 109 */         if (this.persistentState == PersistentState.NEW) {
/* 110 */           this.persistentState = PersistentState.NOACTION;
/*     */         } else {
/* 112 */           this.persistentState = PersistentState.DELETED;
/*     */         }  return;
/*     */       case UPDATE_REQUIRED:
/* 115 */         if (this.persistentState == PersistentState.NEW)
/*     */           return;  break;
/*     */     } 
/* 118 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStoneType getItemStoneType() {
/* 127 */     return this.itemStoneType;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\ItemStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.model.trade;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class Exchange
/*     */ {
/*     */   private Player activeplayer;
/*     */   private Player targetPlayer;
/*     */   private boolean confirmed;
/*     */   private boolean locked;
/*     */   private long kinahCount;
/*  38 */   private Map<Integer, ExchangeItem> items = new HashMap<Integer, ExchangeItem>();
/*     */ 
/*     */   
/*     */   public Exchange(Player activeplayer, Player targetPlayer) {
/*  42 */     this.activeplayer = activeplayer;
/*  43 */     this.targetPlayer = targetPlayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirm() {
/*  48 */     this.confirmed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConfirmed() {
/*  56 */     return this.confirmed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lock() {
/*  61 */     this.locked = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/*  69 */     return this.locked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(int parentItemObjId, ExchangeItem exchangeItem) {
/*  77 */     this.items.put(Integer.valueOf(parentItemObjId), exchangeItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKinah(long countToAdd) {
/*  85 */     this.kinahCount += countToAdd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getActiveplayer() {
/*  93 */     return this.activeplayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getTargetPlayer() {
/* 101 */     return this.targetPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getKinahCount() {
/* 109 */     return this.kinahCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, ExchangeItem> getItems() {
/* 117 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExchangeListFull() {
/* 122 */     return (this.items.size() > 18);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 127 */     this.activeplayer = null;
/* 128 */     this.targetPlayer = null;
/* 129 */     this.items.clear();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\trade\Exchange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
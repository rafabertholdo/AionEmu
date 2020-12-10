/*     */ package com.aionemu.gameserver.model.account;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccountTime
/*     */ {
/*     */   private long accumulatedOnlineTime;
/*     */   private long accumulatedRestTime;
/*     */   
/*     */   public long getAccumulatedOnlineTime() {
/*  44 */     return this.accumulatedOnlineTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccumulatedOnlineTime(long accumulatedOnlineTime) {
/*  55 */     this.accumulatedOnlineTime = accumulatedOnlineTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAccumulatedRestTime() {
/*  65 */     return this.accumulatedRestTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccumulatedRestTime(long accumulatedRestTime) {
/*  76 */     this.accumulatedRestTime = accumulatedRestTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAccumulatedOnlineHours() {
/*  87 */     return toHours(this.accumulatedOnlineTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAccumulatedOnlineMinutes() {
/*  98 */     return toMinutes(this.accumulatedOnlineTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAccumulatedRestHours() {
/* 109 */     return toHours(this.accumulatedRestTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAccumulatedRestMinutes() {
/* 120 */     return toMinutes(this.accumulatedRestTime);
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
/*     */   private static int toHours(long millis) {
/* 133 */     return (int)(millis / 1000L) / 3600;
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
/*     */   private static int toMinutes(long millis) {
/* 146 */     return (int)(millis / 1000L % 3600L) / 60;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\account\AccountTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
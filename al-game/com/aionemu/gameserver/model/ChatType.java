/*     */ package com.aionemu.gameserver.model;
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
/*     */ public enum ChatType
/*     */ {
/*  32 */   NORMAL(0),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   SHOUT(3),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   WHISPER(4),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   GROUP(5),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   ALLIANCE(6),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   GROUP_LEADER(7),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   LEGION(8),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   UNKNOWN_0x18(24),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   ANNOUNCEMENTS(25, true),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   PERIOD_NOTICE(28, true),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   PERIOD_ANNOUNCEMENTS(32, true),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   SYSTEM_NOTICE(33, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int intValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sysMsg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int toInteger() {
/* 117 */     return this.intValue;
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
/*     */   
/*     */   public static ChatType getChatTypeByInt(int integerValue) throws IllegalArgumentException {
/* 131 */     for (ChatType ct : values()) {
/*     */       
/* 133 */       if (ct.toInteger() == integerValue)
/*     */       {
/* 135 */         return ct;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     throw new IllegalArgumentException("Unsupported chat type: " + integerValue);
/*     */   }
/*     */ 
/*     */   
/*     */   ChatType(int intValue, boolean sysMsg) {
/* 144 */     this.intValue = intValue;
/* 145 */     this.sysMsg = sysMsg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSysMsg() {
/* 154 */     return this.sysMsg;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\ChatType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
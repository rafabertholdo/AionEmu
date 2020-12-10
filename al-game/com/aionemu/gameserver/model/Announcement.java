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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Announcement
/*     */ {
/*     */   private int id;
/*     */   private String faction;
/*     */   private String announce;
/*     */   private String chatType;
/*     */   private int delay;
/*     */   
/*     */   public Announcement(String announce, String faction, String chatType, int delay) {
/*  42 */     this.announce = announce;
/*     */ 
/*     */     
/*  45 */     if (!faction.equalsIgnoreCase("ELYOS") && !faction.equalsIgnoreCase("ASMODIANS")) {
/*  46 */       faction = "ALL";
/*     */     }
/*  48 */     this.faction = faction;
/*  49 */     this.chatType = chatType;
/*  50 */     this.delay = delay;
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
/*     */   public Announcement(int id, String announce, String faction, String chatType, int delay) {
/*  64 */     this.id = id;
/*  65 */     this.announce = announce;
/*     */ 
/*     */     
/*  68 */     if (!faction.equalsIgnoreCase("ELYOS") && !faction.equalsIgnoreCase("ASMODIANS")) {
/*  69 */       faction = "ALL";
/*     */     }
/*  71 */     this.faction = faction;
/*  72 */     this.chatType = chatType;
/*  73 */     this.delay = delay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  84 */     if (this.id != 0) {
/*  85 */       return this.id;
/*     */     }
/*  87 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAnnounce() {
/*  97 */     return this.announce;
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
/*     */   public String getFaction() {
/* 110 */     return this.faction;
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
/*     */   public Race getFactionEnum() {
/* 122 */     if (this.faction.equalsIgnoreCase("ELYOS"))
/* 123 */       return Race.ELYOS; 
/* 124 */     if (this.faction.equalsIgnoreCase("ASMODIANS")) {
/* 125 */       return Race.ASMODIANS;
/*     */     }
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 137 */     return this.chatType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatType getChatType() {
/* 147 */     if (this.chatType.equalsIgnoreCase("NORMAL"))
/* 148 */       return ChatType.PERIOD_NOTICE; 
/* 149 */     if (this.chatType.equalsIgnoreCase("YELLOW"))
/* 150 */       return ChatType.ANNOUNCEMENTS; 
/* 151 */     if (this.chatType.equalsIgnoreCase("SHOUT"))
/* 152 */       return ChatType.SHOUT; 
/* 153 */     if (this.chatType.equalsIgnoreCase("ORANGE")) {
/* 154 */       return ChatType.GROUP_LEADER;
/*     */     }
/* 156 */     return ChatType.SYSTEM_NOTICE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDelay() {
/* 166 */     return this.delay;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\Announcement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
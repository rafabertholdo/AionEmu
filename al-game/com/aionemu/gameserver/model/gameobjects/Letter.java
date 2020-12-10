/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import java.sql.Timestamp;
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
/*     */ public class Letter
/*     */   extends AionObject
/*     */ {
/*     */   private int recipientId;
/*     */   private Item attachedItem;
/*     */   private long attachedKinahCount;
/*     */   private String senderName;
/*     */   private String title;
/*     */   private String message;
/*     */   private boolean unread;
/*     */   private boolean express;
/*     */   private Timestamp timeStamp;
/*     */   private PersistentState persistentState;
/*     */   
/*     */   public Letter(int objId, int recipientId, Item attachedItem, long attachedKinahCount, String title, String message, String senderName, Timestamp timeStamp, boolean unread, boolean express) {
/*  52 */     super(Integer.valueOf(objId));
/*     */     
/*  54 */     this.recipientId = recipientId;
/*  55 */     this.attachedItem = attachedItem;
/*  56 */     this.attachedKinahCount = attachedKinahCount;
/*  57 */     this.title = title;
/*  58 */     this.message = message;
/*  59 */     this.senderName = senderName;
/*  60 */     this.timeStamp = timeStamp;
/*  61 */     this.unread = unread;
/*  62 */     this.express = express;
/*  63 */     this.persistentState = PersistentState.NEW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  70 */     return String.valueOf(this.attachedItem.getItemTemplate().getNameId());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRecipientId() {
/*  75 */     return this.recipientId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getAttachedItem() {
/*  80 */     return this.attachedItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getAttachedKinah() {
/*  85 */     return this.attachedKinahCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  90 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  95 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSenderName() {
/* 100 */     return this.senderName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnread() {
/* 105 */     return this.unread;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReadLetter() {
/* 110 */     this.unread = false;
/* 111 */     this.persistentState = PersistentState.UPDATE_REQUIRED;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExpress() {
/* 116 */     return this.express;
/*     */   }
/*     */ 
/*     */   
/*     */   public PersistentState getLetterPersistentState() {
/* 121 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttachedItem() {
/* 126 */     this.attachedItem = null;
/* 127 */     this.persistentState = PersistentState.UPDATE_REQUIRED;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttachedKinah() {
/* 132 */     this.attachedKinahCount = 0L;
/* 133 */     this.persistentState = PersistentState.UPDATE_REQUIRED;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 138 */     this.persistentState = PersistentState.DELETED;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPersistState(PersistentState state) {
/* 143 */     this.persistentState = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getTimeStamp() {
/* 151 */     return this.timeStamp;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Letter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
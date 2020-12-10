/*     */ package com.aionemu.gameserver.model.legion;
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
/*     */ public class LegionEmblem
/*     */ {
/*  27 */   private int emblemId = 0;
/*  28 */   private int color_r = 0;
/*  29 */   private int color_g = 0;
/*  30 */   private int color_b = 0;
/*     */   
/*     */   private boolean defaultEmblem = true;
/*     */   private PersistentState persistentState;
/*     */   private boolean isUploading = false;
/*  35 */   private int uploadSize = 0;
/*  36 */   private int uploadedSize = 0;
/*     */   
/*     */   private byte[] uploadData;
/*     */   
/*     */   public LegionEmblem() {
/*  41 */     setPersistentState(PersistentState.NEW);
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
/*     */   
/*     */   public void setEmblem(int emblemId, int color_r, int color_g, int color_b) {
/*  56 */     this.emblemId = emblemId;
/*  57 */     this.color_r = color_r;
/*  58 */     this.color_g = color_g;
/*  59 */     this.color_b = color_b;
/*  60 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*  61 */     this.defaultEmblem = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEmblemId() {
/*  69 */     return this.emblemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor_r() {
/*  77 */     return this.color_r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor_g() {
/*  85 */     return this.color_g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor_b() {
/*  93 */     return this.color_b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefaultEmblem() {
/* 101 */     return this.defaultEmblem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUploading(boolean isUploading) {
/* 110 */     this.isUploading = isUploading;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUploading() {
/* 118 */     return this.isUploading;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUploadSize(int emblemSize) {
/* 127 */     this.uploadSize = emblemSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUploadSize() {
/* 135 */     return this.uploadSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUploadData(byte[] data) {
/* 144 */     byte[] newData = new byte[this.uploadedSize];
/* 145 */     int i = 0;
/* 146 */     if (this.uploadData.length > 0)
/*     */     {
/* 148 */       for (byte dataByte : this.uploadData) {
/*     */         
/* 150 */         newData[i] = dataByte;
/* 151 */         i++;
/*     */       } 
/*     */     }
/* 154 */     for (byte dataByte : data) {
/*     */       
/* 156 */       newData[i] = dataByte;
/* 157 */       i++;
/*     */     } 
/* 159 */     this.uploadData = newData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getUploadData() {
/* 167 */     return this.uploadData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUploadedSize(int uploadedSize) {
/* 176 */     this.uploadedSize += uploadedSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUploadedSize() {
/* 184 */     return this.uploadedSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetUploadSettings() {
/* 192 */     this.isUploading = false;
/* 193 */     this.uploadedSize = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 202 */     switch (persistentState) {
/*     */       
/*     */       case UPDATE_REQUIRED:
/* 205 */         if (this.persistentState == PersistentState.NEW)
/*     */           return;  break;
/*     */     } 
/* 208 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 217 */     return this.persistentState;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionEmblem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
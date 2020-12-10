/*     */ package com.aionemu.gameserver.model;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlEnum;
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
/*     */ @XmlEnum
/*     */ public enum PlayerClass
/*     */ {
/*  31 */   WARRIOR(0, true),
/*  32 */   GLADIATOR(1),
/*  33 */   TEMPLAR(2),
/*  34 */   SCOUT(3, true),
/*  35 */   ASSASSIN(4),
/*  36 */   RANGER(5),
/*  37 */   MAGE(6, true),
/*  38 */   SORCERER(7),
/*  39 */   SPIRIT_MASTER(8),
/*  40 */   PRIEST(9, true),
/*  41 */   CLERIC(10),
/*  42 */   CHANTER(11);
/*     */ 
/*     */ 
/*     */   
/*     */   private byte classId;
/*     */ 
/*     */ 
/*     */   
/*     */   private int idMask;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean startingClass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlayerClass(int classId, boolean startingClass) {
/*  60 */     this.classId = (byte)classId;
/*  61 */     this.startingClass = startingClass;
/*  62 */     this.idMask = (int)Math.pow(2.0D, classId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getClassId() {
/*  72 */     return this.classId;
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
/*     */   public static PlayerClass getPlayerClassById(byte classId) {
/*  85 */     for (PlayerClass pc : values()) {
/*     */       
/*  87 */       if (pc.getClassId() == classId) {
/*  88 */         return pc;
/*     */       }
/*     */     } 
/*  91 */     throw new IllegalArgumentException("There is no player class with id " + classId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartingClass() {
/* 100 */     return this.startingClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlayerClass getStartingClassFor(PlayerClass pc) {
/* 110 */     switch (pc) {
/*     */       
/*     */       case ASSASSIN:
/*     */       case RANGER:
/* 114 */         return SCOUT;
/*     */       case GLADIATOR:
/*     */       case TEMPLAR:
/* 117 */         return WARRIOR;
/*     */       case CHANTER:
/*     */       case CLERIC:
/* 120 */         return PRIEST;
/*     */       case SORCERER:
/*     */       case SPIRIT_MASTER:
/* 123 */         return MAGE;
/*     */     } 
/* 125 */     throw new IllegalArgumentException("Given player class is starting class: " + pc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMask() {
/* 131 */     return this.idMask;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\PlayerClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
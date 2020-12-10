/*     */ package com.aionemu.gameserver.controllers.movement;
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
/*     */ public enum MovementType
/*     */ {
/*  30 */   MOVEMENT_START_MOUSE(-32),
/*     */ 
/*     */ 
/*     */   
/*  34 */   MOVEMENT_START_KEYBOARD(-64),
/*     */ 
/*     */ 
/*     */   
/*  38 */   VALIDATE_MOUSE(-96),
/*     */ 
/*     */ 
/*     */   
/*  42 */   VALIDATE_KEYBOARD(-128),
/*     */ 
/*     */ 
/*     */   
/*  46 */   VALIDATE_JUMP(8),
/*     */ 
/*     */ 
/*     */   
/*  50 */   VALIDATE_JUMP_WHILE_MOVING(72),
/*     */ 
/*     */ 
/*     */   
/*  54 */   MOVEMENT_GLIDE_UP(-124),
/*     */ 
/*     */ 
/*     */   
/*  58 */   MOVEMENT_GLIDE_DOWN(-60),
/*     */ 
/*     */ 
/*     */   
/*  62 */   MOVEMENT_GLIDE_START_MOUSE(-28),
/*     */ 
/*     */ 
/*     */   
/*  66 */   VALIDATE_GLIDE_MOUSE(-92),
/*     */ 
/*     */ 
/*     */   
/*  70 */   MOVEMENT_STOP(0),
/*     */   
/*  72 */   MOVEMENT_STAYIN_ELEVATOR(24),
/*  73 */   MOVEMENT_JUMPIN_ELEVATOR(-48),
/*  74 */   MOVEMENT_VALIDATEIN_ELEVATOR(-112),
/*  75 */   MOVEMENT_MOVIN_ELEVATOR(-16),
/*  76 */   MOVEMENT_ON_ELEVATOR(16),
/*  77 */   MOVEMENT_GO_UPDOWN_ELEVATOR(-80),
/*     */   
/*  79 */   UNKNOWN_NPC_MOVEMENT(-22),
/*     */   
/*  81 */   UNKNOWN(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int typeId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MovementType(int typeId) {
/*  92 */     this.typeId = typeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMovementTypeId() {
/* 101 */     return this.typeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MovementType getMovementTypeById(int id) {
/* 111 */     for (MovementType mt : values()) {
/*     */       
/* 113 */       if (mt.typeId == id)
/* 114 */         return mt; 
/*     */     } 
/* 116 */     return UNKNOWN;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\MovementType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
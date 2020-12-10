/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.movement.MovementType;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ public class SM_MOVE
/*     */   extends AionServerPacket
/*     */ {
/*     */   private final int movingCreatureId;
/*     */   private final float x;
/*     */   private final float y;
/*     */   private final float z;
/*     */   private final float x2;
/*     */   private final float y2;
/*     */   private final float z2;
/*     */   private final byte heading;
/*     */   private final MovementType moveType;
/*     */   private final byte glideFlag;
/*     */   private boolean hasDirection;
/*     */   private boolean hasGlideFlag;
/*     */   
/*     */   public SM_MOVE(int movingCreatureId, float x, float y, float z, float x2, float y2, float z2, byte heading, byte glideFlag, MovementType moveType) {
/*  62 */     this.movingCreatureId = movingCreatureId;
/*  63 */     this.x = x;
/*  64 */     this.y = y;
/*  65 */     this.z = z;
/*  66 */     this.x2 = x2;
/*  67 */     this.y2 = y2;
/*  68 */     this.z2 = z2;
/*  69 */     this.heading = heading;
/*  70 */     this.glideFlag = glideFlag;
/*  71 */     this.moveType = moveType;
/*     */     
/*  73 */     this.hasDirection = true;
/*  74 */     this.hasGlideFlag = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MOVE(int movingCreatureId, float x, float y, float z, float x2, float y2, float z2, byte heading, MovementType moveType) {
/*  80 */     this(movingCreatureId, x, y, z, x2, y2, z2, heading, (byte)0, moveType);
/*  81 */     this.hasDirection = true;
/*  82 */     this.hasGlideFlag = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MOVE(int movingCreatureId, float x, float y, float z, byte heading, MovementType moveType) {
/*  88 */     this(movingCreatureId, x, y, z, 0.0F, 0.0F, 0.0F, heading, (byte)0, moveType);
/*  89 */     this.hasDirection = false;
/*  90 */     this.hasGlideFlag = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MOVE(int movingCreatureId, float x, float y, float z, byte heading, byte glideFlag, MovementType moveType) {
/*  96 */     this(movingCreatureId, x, y, z, 0.0F, 0.0F, 0.0F, heading, glideFlag, moveType);
/*  97 */     this.hasDirection = false;
/*  98 */     this.hasGlideFlag = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 107 */     writeD(buf, this.movingCreatureId);
/* 108 */     writeF(buf, this.x);
/* 109 */     writeF(buf, this.y);
/* 110 */     writeF(buf, this.z);
/* 111 */     writeC(buf, this.heading);
/* 112 */     writeC(buf, this.moveType.getMovementTypeId());
/*     */     
/* 114 */     if (this.hasDirection) {
/*     */       
/* 116 */       writeF(buf, this.x2);
/* 117 */       writeF(buf, this.y2);
/* 118 */       writeF(buf, this.z2);
/*     */     } 
/*     */     
/* 121 */     if (this.hasGlideFlag)
/*     */     {
/* 123 */       writeC(buf, this.glideFlag);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_MOVE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
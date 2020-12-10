/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.movement.MovementType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class CM_SUMMON_MOVE
/*     */   extends AionClientPacket
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(CM_SUMMON_MOVE.class);
/*     */   
/*     */   private MovementType type;
/*     */   
/*     */   private byte heading;
/*     */   
/*     */   private byte movementType;
/*     */   
/*     */   private float x;
/*     */   
/*     */   private float y;
/*     */   private float z;
/*     */   private float x2;
/*     */   private float y2;
/*     */   private float z2;
/*     */   
/*     */   public CM_SUMMON_MOVE(int opcode) {
/*  52 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  61 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*     */     
/*  63 */     if (!player.isSpawned()) {
/*     */       return;
/*     */     }
/*  66 */     readD();
/*     */     
/*  68 */     this.x = readF();
/*  69 */     this.y = readF();
/*  70 */     this.z = readF();
/*     */     
/*  72 */     this.heading = (byte)readC();
/*  73 */     this.movementType = (byte)readC();
/*  74 */     this.type = MovementType.getMovementTypeById(this.movementType);
/*     */     
/*  76 */     switch (this.type) {
/*     */       
/*     */       case MOVEMENT_START_MOUSE:
/*     */       case MOVEMENT_START_KEYBOARD:
/*  80 */         this.x2 = readF();
/*  81 */         this.y2 = readF();
/*  82 */         this.z2 = readF();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*     */     StringBuilder sb;
/*  95 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*  96 */     if (player == null) {
/*     */       
/*  98 */       log.error("CM_SUMMON_MOVE packet received but cannot get master player.");
/*     */       return;
/*     */     } 
/* 101 */     Summon summon = player.getSummon();
/* 102 */     if (summon == null) {
/*     */       return;
/*     */     }
/* 105 */     if (this.type == null) {
/*     */       return;
/*     */     }
/* 108 */     switch (this.type) {
/*     */       
/*     */       case MOVEMENT_START_MOUSE:
/*     */       case MOVEMENT_START_KEYBOARD:
/* 112 */         World.getInstance().updatePosition((VisibleObject)summon, this.x, this.y, this.z, this.heading);
/* 113 */         PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_MOVE(summon.getObjectId(), this.x, this.y, this.z, this.x2, this.y2, this.z2, this.heading, this.type));
/*     */         break;
/*     */       case VALIDATE_MOUSE:
/*     */       case VALIDATE_KEYBOARD:
/* 117 */         PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_MOVE(summon.getObjectId(), this.x, this.y, this.z, this.x2, this.y2, this.z2, this.heading, (this.type == MovementType.VALIDATE_MOUSE) ? MovementType.MOVEMENT_START_MOUSE : MovementType.MOVEMENT_START_KEYBOARD));
/*     */         break;
/*     */ 
/*     */       
/*     */       case MOVEMENT_STOP:
/* 122 */         PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_MOVE(summon.getObjectId(), this.x, this.y, this.z, this.heading, this.type));
/* 123 */         World.getInstance().updatePosition((VisibleObject)summon, this.x, this.y, this.z, this.heading);
/*     */         break;
/*     */       case UNKNOWN:
/* 126 */         sb = new StringBuilder();
/* 127 */         sb.append("Unknown movement type: ").append(this.movementType);
/* 128 */         sb.append("Coordinates: X=").append(this.x);
/* 129 */         sb.append(" Y=").append(this.y);
/* 130 */         sb.append(" Z=").append(this.z);
/* 131 */         sb.append(" player=").append(player.getName());
/* 132 */         log.warn(sb.toString());
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SUMMON_MOVE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
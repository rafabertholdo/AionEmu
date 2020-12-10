/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.FallDamageConfig;
/*     */ import com.aionemu.gameserver.controllers.MoveController;
/*     */ import com.aionemu.gameserver.controllers.movement.MovementType;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.GroupAllianceUpdater;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.stats.StatFunctions;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CM_MOVE
/*     */   extends AionClientPacket
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(CM_MOVE.class);
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
/*     */   
/*     */   private float z;
/*     */   private float x2;
/*     */   private float y2;
/*     */   private float z2;
/*     */   private byte glideFlag;
/*     */   
/*     */   public CM_MOVE(int opcode) {
/*  64 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  73 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*     */     
/*  75 */     if (!player.isSpawned()) {
/*     */       return;
/*     */     }
/*  78 */     this.x = readF();
/*  79 */     this.y = readF();
/*  80 */     this.z = readF();
/*     */     
/*  82 */     this.heading = (byte)readC();
/*  83 */     this.movementType = (byte)readC();
/*  84 */     this.type = MovementType.getMovementTypeById(this.movementType);
/*     */     
/*  86 */     switch (this.type) {
/*     */       
/*     */       case MOVEMENT_START_MOUSE:
/*     */       case MOVEMENT_START_KEYBOARD:
/*  90 */         this.x2 = readF();
/*  91 */         this.y2 = readF();
/*  92 */         this.z2 = readF();
/*     */         break;
/*     */       case MOVEMENT_GLIDE_DOWN:
/*     */       case MOVEMENT_GLIDE_START_MOUSE:
/*  96 */         this.x2 = readF();
/*  97 */         this.y2 = readF();
/*  98 */         this.z2 = readF();
/*     */       
/*     */       case MOVEMENT_GLIDE_UP:
/*     */       case VALIDATE_GLIDE_MOUSE:
/* 102 */         this.glideFlag = (byte)readC();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*     */     float glideSpeed;
/*     */     double angle;
/*     */     MoveController mc;
/*     */     StringBuilder sb;
/* 115 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 116 */     World world = World.getInstance();
/*     */     
/* 118 */     if (this.type == null) {
/*     */       return;
/*     */     }
/* 121 */     float playerZ = player.getZ();
/*     */     
/* 123 */     switch (this.type) {
/*     */       
/*     */       case MOVEMENT_START_MOUSE:
/*     */       case MOVEMENT_START_KEYBOARD:
/*     */       case MOVEMENT_MOVIN_ELEVATOR:
/*     */       case MOVEMENT_ON_ELEVATOR:
/*     */       case MOVEMENT_STAYIN_ELEVATOR:
/* 130 */         world.updatePosition((VisibleObject)player, this.x, this.y, this.z, this.heading);
/* 131 */         player.getMoveController().setNewDirection(this.x2, this.y2, this.z2);
/* 132 */         player.getController().onStartMove();
/* 133 */         player.getFlyController().onStopGliding();
/* 134 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, this.x2, this.y2, this.z2, this.heading, this.type), false);
/*     */         break;
/*     */       
/*     */       case MOVEMENT_GLIDE_START_MOUSE:
/* 138 */         player.getMoveController().setNewDirection(this.x2, this.y2, this.z2);
/*     */       
/*     */       case MOVEMENT_GLIDE_DOWN:
/* 141 */         world.updatePosition((VisibleObject)player, this.x, this.y, this.z, this.heading);
/* 142 */         player.getController().onMove();
/* 143 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, this.x2, this.y2, this.z2, this.heading, this.glideFlag, this.type), false);
/*     */         
/* 145 */         player.getFlyController().switchToGliding();
/*     */         break;
/*     */       case MOVEMENT_GLIDE_UP:
/* 148 */         world.updatePosition((VisibleObject)player, this.x, this.y, this.z, this.heading);
/* 149 */         player.getController().onMove();
/* 150 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, this.heading, this.glideFlag, this.type), false);
/*     */         
/* 152 */         player.getFlyController().switchToGliding();
/*     */         break;
/*     */       case VALIDATE_GLIDE_MOUSE:
/* 155 */         world.updatePosition((VisibleObject)player, this.x, this.y, this.z, this.heading);
/* 156 */         player.getController().onMove();
/* 157 */         player.getFlyController().switchToGliding();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 163 */         glideSpeed = player.getGameStats().getCurrentStat(StatEnum.SPEED);
/* 164 */         angle = Math.toRadians((this.heading * 3));
/* 165 */         this.x2 = (float)(glideSpeed * Math.cos(angle));
/* 166 */         this.y2 = (float)(glideSpeed * Math.sin(angle));
/*     */         
/* 168 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, this.x2, this.y2, this.z2, this.heading, this.glideFlag, MovementType.MOVEMENT_GLIDE_DOWN), false);
/*     */         break;
/*     */ 
/*     */       
/*     */       case VALIDATE_MOUSE:
/*     */       case VALIDATE_KEYBOARD:
/* 174 */         player.getController().onMove();
/* 175 */         player.getFlyController().onStopGliding();
/* 176 */         world.updatePosition((VisibleObject)player, this.x, this.y, this.z, this.heading);
/*     */         
/* 178 */         mc = player.getMoveController();
/*     */         
/* 180 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, mc.getTargetX(), mc.getTargetY(), mc.getTargetZ(), this.heading, (this.type == MovementType.VALIDATE_MOUSE) ? MovementType.MOVEMENT_START_MOUSE : MovementType.MOVEMENT_START_KEYBOARD), false);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case MOVEMENT_STOP:
/* 186 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, this.heading, this.type), false);
/*     */         
/* 188 */         world.updatePosition((VisibleObject)player, this.x, this.y, this.z, this.heading);
/* 189 */         player.getController().onStopMove();
/* 190 */         player.getFlyController().onStopGliding();
/*     */         break;
/*     */       case UNKNOWN:
/* 193 */         sb = new StringBuilder();
/* 194 */         sb.append("Unknown movement type: ").append(this.movementType);
/* 195 */         sb.append("Coordinates: X=").append(this.x);
/* 196 */         sb.append(" Y=").append(this.y);
/* 197 */         sb.append(" Z=").append(this.z);
/* 198 */         sb.append(" player=").append(player.getName());
/* 199 */         log.warn(sb.toString());
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (player.isInGroup() || player.isInAlliance())
/*     */     {
/* 207 */       GroupAllianceUpdater.getInstance().add(player);
/*     */     }
/*     */     
/* 210 */     float distance = playerZ - this.z;
/* 211 */     if (FallDamageConfig.ACTIVE_FALL_DAMAGE && player.isInState(CreatureState.ACTIVE) && !player.isInState(CreatureState.FLYING) && !player.isInState(CreatureState.GLIDING) && (this.type == MovementType.MOVEMENT_STOP || distance >= FallDamageConfig.MAXIMUM_DISTANCE_MIDAIR))
/*     */     {
/*     */ 
/*     */       
/* 215 */       if (StatFunctions.calculateFallDamage(player, distance)) {
/*     */         return;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 221 */     if (this.type != MovementType.MOVEMENT_STOP && player.isProtectionActive())
/*     */     {
/* 223 */       player.getController().stopProtectionActiveTask();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MOVE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
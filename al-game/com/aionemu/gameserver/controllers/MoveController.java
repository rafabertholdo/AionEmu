/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.movement.MovementType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.concurrent.Future;
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
/*     */ 
/*     */ public class MoveController
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(MoveController.class);
/*     */   
/*     */   private Future<?> moveTask;
/*     */   
/*     */   private Creature owner;
/*     */   
/*     */   private boolean directionChanged = true;
/*     */   
/*     */   private float targetX;
/*     */   private float targetY;
/*     */   private float targetZ;
/*     */   private boolean isFollowTarget;
/*     */   private boolean isStopped = false;
/*     */   private int moveCounter;
/*  55 */   private float speed = 0.0F;
/*  56 */   private float distance = 2.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean walking;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MoveController(Creature owner) {
/*  66 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFollowTarget(boolean isFollowTarget) {
/*  74 */     this.isFollowTarget = isFollowTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirectionChanged(boolean directionChanged) {
/*  82 */     this.directionChanged = directionChanged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpeed(float speed) {
/*  90 */     this.speed = speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpeed() {
/*  97 */     return this.speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDistance(float distance) {
/* 105 */     this.distance = distance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWalking() {
/* 113 */     return this.walking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWalking(boolean walking) {
/* 121 */     this.walking = walking;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNewDirection(float x, float y, float z) {
/* 126 */     if (x != this.targetX || y != this.targetY || z != this.targetZ)
/* 127 */       this.directionChanged = true; 
/* 128 */     this.targetX = x;
/* 129 */     this.targetY = y;
/* 130 */     this.targetZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTargetX() {
/* 135 */     return this.targetX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTargetY() {
/* 140 */     return this.targetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTargetZ() {
/* 145 */     return this.targetZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScheduled() {
/* 150 */     return (this.moveTask != null && !this.moveTask.isCancelled());
/*     */   }
/*     */ 
/*     */   
/*     */   public void schedule() {
/* 155 */     if (isScheduled()) {
/*     */       return;
/*     */     }
/* 158 */     this.moveTask = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 163 */             MoveController.this.move();
/*     */           }
/*     */         },  0L, 500L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void move() {
/* 170 */     if (this.speed == 0.0F) {
/* 171 */       this.speed = (this.owner.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000);
/*     */     }
/*     */ 
/*     */     
/* 175 */     if (!this.owner.canPerformMove() || this.owner.isCasting() || this.speed <= 0.0F) {
/*     */       
/* 177 */       if (!this.isStopped) {
/*     */         
/* 179 */         this.isStopped = true;
/* 180 */         this.owner.getController().stopMoving();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 185 */     VisibleObject target = this.owner.getTarget();
/*     */     
/* 187 */     if (this.isFollowTarget && target != null)
/*     */     {
/* 189 */       setNewDirection(target.getX(), target.getY(), target.getZ());
/*     */     }
/*     */     
/* 192 */     float ownerX = this.owner.getX();
/* 193 */     float ownerY = this.owner.getY();
/* 194 */     float ownerZ = this.owner.getZ();
/* 195 */     byte heading = this.owner.getHeading();
/*     */     
/* 197 */     float distX = this.targetX - ownerX;
/* 198 */     float distY = this.targetY - ownerY;
/* 199 */     float distZ = this.targetZ - ownerZ;
/*     */     
/* 201 */     double dist = MathUtil.getDistance(ownerX, ownerY, ownerZ, this.targetX, this.targetY, this.targetZ);
/*     */     
/* 203 */     if (dist > this.distance) {
/*     */       
/* 205 */       this.isStopped = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       float x2 = (float)(distX / dist);
/* 212 */       float y2 = (float)(distY / dist);
/* 213 */       float z2 = (float)(distZ / dist);
/*     */       
/* 215 */       dist -= (this.distance - 0.02F);
/*     */       
/* 217 */       if (this.directionChanged) {
/*     */         
/* 219 */         heading = (byte)(int)(Math.toDegrees(Math.atan2(distY, distX)) / 3.0D);
/* 220 */         PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_MOVE(this.owner.getObjectId(), ownerX, ownerY, ownerZ, (float)(x2 * dist) + ownerX, (float)(y2 * dist) + ownerY, (float)(z2 * dist) + ownerZ, heading, MovementType.UNKNOWN_NPC_MOVEMENT));
/*     */         
/* 222 */         this.directionChanged = false;
/*     */       } 
/* 224 */       double maxDist = this.speed * 0.5D;
/* 225 */       if (dist > maxDist) {
/* 226 */         dist = maxDist;
/*     */       }
/* 228 */       x2 = (float)(x2 * dist) + ownerX;
/* 229 */       y2 = (float)(y2 * dist) + ownerY;
/* 230 */       z2 = (float)(z2 * dist) + ownerZ;
/*     */       
/* 232 */       this.moveCounter++;
/* 233 */       World.getInstance().updatePosition((VisibleObject)this.owner, x2, y2, z2, heading, (this.moveCounter % 5 == 0));
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 238 */     else if (!this.isStopped) {
/*     */       
/* 240 */       this.isStopped = true;
/* 241 */       this.owner.getController().stopMoving();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistanceToTarget() {
/* 248 */     if (this.isFollowTarget) {
/*     */       
/* 250 */       VisibleObject target = this.owner.getTarget();
/* 251 */       if (target != null) {
/* 252 */         return MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), target.getX(), target.getY(), target.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 256 */     return MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), this.targetX, this.targetY, this.targetZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 261 */     this.walking = false;
/*     */     
/* 263 */     if (this.moveTask != null) {
/*     */       
/* 265 */       if (!this.moveTask.isCancelled())
/* 266 */         this.moveTask.cancel(true); 
/* 267 */       this.moveTask = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\MoveController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
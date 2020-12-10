/*     */ package com.aionemu.gameserver.ai.desires.impl;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*     */ import com.aionemu.gameserver.ai.desires.MoveDesire;
/*     */ import com.aionemu.gameserver.configs.main.NpcMovementConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.templates.walker.RouteData;
/*     */ import com.aionemu.gameserver.model.templates.walker.RouteStep;
/*     */ import com.aionemu.gameserver.model.templates.walker.WalkerTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class WalkDesire
/*     */   extends AbstractDesire
/*     */   implements MoveDesire
/*     */ {
/*     */   private Npc owner;
/*     */   private RouteData route;
/*     */   private boolean isWalkingToNextPoint = false;
/*     */   private int targetPosition;
/*     */   private long nextMoveTime;
/*     */   private boolean isRandomWalk = false;
/*  47 */   private RouteStep randomPoint = null;
/*  48 */   private float walkArea = 10.0F;
/*  49 */   private float halfWalkArea = 5.0F;
/*  50 */   private float minRandomDistance = 2.0F;
/*     */ 
/*     */   
/*     */   public WalkDesire(Npc npc, int power) {
/*  54 */     super(power);
/*  55 */     this.owner = npc;
/*     */     
/*  57 */     WalkerTemplate template = DataManager.WALKER_DATA.getWalkerTemplate(this.owner.getSpawn().getWalkerId());
/*  58 */     this.isRandomWalk = this.owner.getSpawn().hasRandomWalk();
/*  59 */     if (template != null) {
/*     */       
/*  61 */       this.route = template.getRouteData();
/*     */       
/*  63 */       this.owner.getMoveController().setSpeed(this.owner.getObjectTemplate().getStatsTemplate().getWalkSpeed());
/*  64 */       this.owner.getMoveController().setWalking(true);
/*  65 */       PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_EMOTION((Creature)this.owner, EmotionType.WALK));
/*     */     }
/*  67 */     else if (this.isRandomWalk && NpcMovementConfig.ACTIVE_NPC_MOVEMENT) {
/*     */       
/*  69 */       this.walkArea = Math.max(5, this.owner.getSpawn().getRandomWalkNr());
/*  70 */       this.halfWalkArea = this.walkArea / 2.0F;
/*  71 */       this.minRandomDistance = Math.min(this.walkArea / 5.0F, 2.0F);
/*     */       
/*  73 */       this.route = null;
/*  74 */       this.randomPoint = new RouteStep(this.owner.getX(), this.owner.getY(), this.owner.getZ());
/*     */       
/*  76 */       this.owner.getMoveController().setSpeed(this.owner.getObjectTemplate().getStatsTemplate().getWalkSpeed());
/*  77 */       this.owner.getMoveController().setWalking(true);
/*  78 */       this.owner.getMoveController().setDistance(this.minRandomDistance);
/*  79 */       PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_EMOTION((Creature)this.owner, EmotionType.WALK));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleDesire(AI ai) {
/*  87 */     if (this.owner == null) {
/*  88 */       return false;
/*     */     }
/*  90 */     if (this.route == null && !this.isRandomWalk) {
/*  91 */       return false;
/*     */     }
/*  93 */     if (isWalkingToNextPoint()) {
/*  94 */       checkArrivedToPoint();
/*     */     }
/*  96 */     walkToLocation();
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkArrivedToPoint() {
/* 105 */     RouteStep step = this.randomPoint;
/* 106 */     if (this.route != null)
/*     */     {
/* 108 */       step = this.route.getRouteSteps().get(this.targetPosition);
/*     */     }
/*     */     
/* 111 */     float x = step.getX();
/* 112 */     float y = step.getY();
/* 113 */     float z = step.getZ();
/*     */     
/* 115 */     double dist = MathUtil.getDistance((VisibleObject)this.owner, x, y, z);
/* 116 */     float minDist = (this.route == null) ? this.minRandomDistance : 2.0F;
/* 117 */     if (dist <= minDist) {
/*     */       
/* 119 */       setWalkingToNextPoint(false);
/* 120 */       getNextTime();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void walkToLocation() {
/* 129 */     if (!isWalkingToNextPoint() && this.nextMoveTime <= System.currentTimeMillis()) {
/*     */       
/* 131 */       setNextPosition();
/* 132 */       setWalkingToNextPoint(true);
/*     */       
/* 134 */       RouteStep step = this.randomPoint;
/* 135 */       if (this.route != null)
/*     */       {
/* 137 */         step = this.route.getRouteSteps().get(this.targetPosition);
/*     */       }
/*     */       
/* 140 */       float x = step.getX();
/* 141 */       float y = step.getY();
/* 142 */       float z = step.getZ();
/* 143 */       this.owner.getMoveController().setNewDirection(x, y, z);
/* 144 */       if (!this.owner.getMoveController().isScheduled()) {
/* 145 */         this.owner.getMoveController().schedule();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isWalkingToNextPoint() {
/* 151 */     return this.isWalkingToNextPoint;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setWalkingToNextPoint(boolean value) {
/* 156 */     this.isWalkingToNextPoint = value;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNextPosition() {
/* 161 */     if (this.route == null) {
/*     */       
/* 163 */       getNextRandomPoint();
/*     */       
/*     */       return;
/*     */     } 
/* 167 */     if (this.isRandomWalk) {
/*     */       
/* 169 */       this.targetPosition = Rnd.get(0, this.route.getRouteSteps().size() - 1);
/*     */ 
/*     */     
/*     */     }
/* 173 */     else if (this.targetPosition < this.route.getRouteSteps().size() - 1) {
/* 174 */       this.targetPosition++;
/*     */     } else {
/* 176 */       this.targetPosition = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void getNextTime() {
/*     */     int nextDelay;
/* 183 */     if (this.route == null) {
/*     */       
/* 185 */       nextDelay = Rnd.get(NpcMovementConfig.MINIMIMUM_DELAY, NpcMovementConfig.MAXIMUM_DELAY);
/*     */     }
/*     */     else {
/*     */       
/* 189 */       nextDelay = this.isRandomWalk ? Rnd.get(5, 60) : ((RouteStep)this.route.getRouteSteps().get(this.targetPosition)).getRestTime();
/*     */     } 
/* 191 */     this.nextMoveTime = System.currentTimeMillis() + (nextDelay * 1000);
/*     */   }
/*     */ 
/*     */   
/*     */   private void getNextRandomPoint() {
/* 196 */     float x = this.owner.getSpawn().getX() - this.halfWalkArea + Rnd.get() * this.walkArea;
/* 197 */     float y = this.owner.getSpawn().getY() - this.halfWalkArea + Rnd.get() * this.walkArea;
/* 198 */     this.randomPoint = new RouteStep(x, y, this.owner.getSpawn().getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExecutionInterval() {
/* 204 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClear() {
/* 210 */     this.owner.getMoveController().stop();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\WalkDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
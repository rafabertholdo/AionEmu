/*    */ package com.aionemu.gameserver.ai.desires.impl;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*    */ import com.aionemu.gameserver.ai.desires.MoveDesire;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MoveToTargetDesire
/*    */   extends AbstractDesire
/*    */   implements MoveDesire
/*    */ {
/*    */   private Npc owner;
/*    */   private Creature target;
/*    */   
/*    */   public MoveToTargetDesire(Npc owner, Creature target, int desirePower) {
/* 40 */     super(desirePower);
/* 41 */     this.owner = owner;
/* 42 */     this.target = target;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handleDesire(AI ai) {
/* 49 */     if (this.owner == null || this.owner.getLifeStats().isAlreadyDead())
/* 50 */       return false; 
/* 51 */     if (this.target == null || this.target.getLifeStats().isAlreadyDead()) {
/* 52 */       return false;
/*    */     }
/* 54 */     this.owner.getMoveController().setFollowTarget(true);
/*    */     
/* 56 */     if (!this.owner.getMoveController().isScheduled()) {
/* 57 */       this.owner.getMoveController().schedule();
/*    */     }
/* 59 */     double distance = this.owner.getMoveController().getDistanceToTarget();
/* 60 */     if (distance > 150.0D) {
/* 61 */       return false;
/*    */     }
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 69 */     if (this == o)
/* 70 */       return true; 
/* 71 */     if (!(o instanceof MoveToTargetDesire)) {
/* 72 */       return false;
/*    */     }
/* 74 */     MoveToTargetDesire that = (MoveToTargetDesire)o;
/* 75 */     return this.target.equals(that.target);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Creature getTarget() {
/* 83 */     return this.target;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExecutionInterval() {
/* 89 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClear() {
/* 95 */     this.owner.getMoveController().stop();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\MoveToTargetDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
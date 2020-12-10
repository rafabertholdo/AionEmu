/*    */ package com.aionemu.gameserver.ai.desires.impl;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*    */ import com.aionemu.gameserver.ai.desires.MoveDesire;
/*    */ import com.aionemu.gameserver.ai.events.Event;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*    */ import com.aionemu.gameserver.utils.MathUtil;
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
/*    */ public class MoveToHomeDesire
/*    */   extends AbstractDesire
/*    */   implements MoveDesire
/*    */ {
/*    */   private Npc owner;
/*    */   private float x;
/*    */   private float y;
/*    */   private float z;
/*    */   
/*    */   public MoveToHomeDesire(Npc owner, int desirePower) {
/* 40 */     super(desirePower);
/* 41 */     this.owner = owner;
/* 42 */     SpawnTemplate template = owner.getSpawn();
/* 43 */     this.x = template.getX();
/* 44 */     this.y = template.getY();
/* 45 */     this.z = template.getZ();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handleDesire(AI ai) {
/* 52 */     if (this.owner == null || this.owner.getLifeStats().isAlreadyDead()) {
/* 53 */       return false;
/*    */     }
/* 55 */     this.owner.getMoveController().setNewDirection(this.x, this.y, this.z);
/* 56 */     this.owner.getMoveController().setFollowTarget(false);
/* 57 */     this.owner.getMoveController().setDistance(0.0F);
/*    */     
/* 59 */     if (!this.owner.getMoveController().isScheduled()) {
/* 60 */       this.owner.getMoveController().schedule();
/*    */     }
/* 62 */     double dist = MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), this.x, this.y, this.z);
/* 63 */     if (dist < 2.0D) {
/*    */       
/* 65 */       ai.handleEvent(Event.BACK_HOME);
/* 66 */       return false;
/*    */     } 
/* 68 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExecutionInterval() {
/* 74 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClear() {
/* 80 */     this.owner.getMoveController().stop();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\MoveToHomeDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.ai.desires.impl;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*    */ import com.aionemu.gameserver.ai.events.Event;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
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
/*    */ public class RestoreHealthDesire
/*    */   extends AbstractDesire
/*    */ {
/*    */   private Creature owner;
/*    */   private int restoreHpValue;
/*    */   
/*    */   public RestoreHealthDesire(Creature owner, int desirePower) {
/* 36 */     super(desirePower);
/* 37 */     this.owner = owner;
/* 38 */     this.restoreHpValue = owner.getLifeStats().getMaxHp() / 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handleDesire(AI<?> ai) {
/* 44 */     if (this.owner == null || this.owner.getLifeStats().isAlreadyDead()) {
/* 45 */       return false;
/*    */     }
/* 47 */     this.owner.getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, this.restoreHpValue);
/* 48 */     if (this.owner.getLifeStats().isFullyRestoredHpMp()) {
/*    */       
/* 50 */       ai.handleEvent(Event.RESTORED_HEALTH);
/* 51 */       return false;
/*    */     } 
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExecutionInterval() {
/* 60 */     return 1;
/*    */   }
/*    */   
/*    */   public void onClear() {}
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\RestoreHealthDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
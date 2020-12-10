/*     */ package com.aionemu.gameserver.ai.desires.impl;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*     */ import com.aionemu.gameserver.ai.events.Event;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
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
/*     */ public final class AttackDesire
/*     */   extends AbstractDesire
/*     */ {
/*     */   private int attackNotPossibleCounter;
/*  39 */   private int attackCounter = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Creature target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Npc owner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttackDesire(Npc npc, Creature target, int desirePower) {
/*  60 */     super(desirePower);
/*  61 */     this.target = target;
/*  62 */     this.owner = npc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleDesire(AI<?> ai) {
/*  71 */     if (this.target == null || this.target.getLifeStats().isAlreadyDead()) {
/*     */ 
/*     */       
/*  74 */       this.owner.getAggroList().stopHating(this.target);
/*  75 */       this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
/*  76 */       return false;
/*     */     } 
/*     */     
/*  79 */     double distance = MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), this.target.getX(), this.target.getY(), this.target.getZ());
/*     */     
/*  81 */     if (distance > 50.0D) {
/*     */       
/*  83 */       this.owner.getAggroList().stopHating(this.target);
/*  84 */       this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
/*  85 */       return false;
/*     */     } 
/*     */     
/*  88 */     this.attackCounter++;
/*     */     
/*  90 */     if (this.attackCounter % 2 == 0)
/*     */     {
/*  92 */       if (!this.owner.getAggroList().isMostHated(this.target)) {
/*     */         
/*  94 */         this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
/*  95 */         return false;
/*     */       } 
/*     */     }
/*  98 */     int attackRange = this.owner.getGameStats().getCurrentStat(StatEnum.ATTACK_RANGE);
/*  99 */     if (distance * 1000.0D <= attackRange) {
/*     */       
/* 101 */       this.owner.getController().attackTarget(this.target);
/* 102 */       this.attackNotPossibleCounter = 0;
/*     */     }
/*     */     else {
/*     */       
/* 106 */       this.attackNotPossibleCounter++;
/*     */     } 
/*     */     
/* 109 */     if (this.attackNotPossibleCounter > 10) {
/*     */       
/* 111 */       this.owner.getAggroList().stopHating(this.target);
/* 112 */       this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
/* 113 */       return false;
/*     */     } 
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 124 */     if (this == o)
/* 125 */       return true; 
/* 126 */     if (!(o instanceof AttackDesire)) {
/* 127 */       return false;
/*     */     }
/* 129 */     AttackDesire that = (AttackDesire)o;
/*     */     
/* 131 */     return this.target.equals(that.target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     return this.target.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getTarget() {
/* 150 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExecutionInterval() {
/* 156 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClear() {
/* 162 */     this.owner.unsetState(CreatureState.WEAPON_EQUIPPED);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\AttackDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
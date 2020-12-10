/*     */ package com.aionemu.gameserver.ai.npcai;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*     */ import com.aionemu.gameserver.ai.desires.Desire;
/*     */ import com.aionemu.gameserver.ai.events.Event;
/*     */ import com.aionemu.gameserver.ai.state.AIState;
/*     */ import com.aionemu.gameserver.ai.state.handler.NoneNpcStateHandler;
/*     */ import com.aionemu.gameserver.ai.state.handler.StateHandler;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Servant;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
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
/*     */ public class ServantAi
/*     */   extends NpcAi
/*     */ {
/*     */   public ServantAi() {
/*  43 */     addStateHandler(new ActiveServantStateHandler());
/*  44 */     addStateHandler((StateHandler)new NoneNpcStateHandler());
/*     */   }
/*     */ 
/*     */   
/*     */   class ActiveServantStateHandler
/*     */     extends StateHandler
/*     */   {
/*     */     public AIState getState() {
/*  52 */       return AIState.ACTIVE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleState(AIState state, AI<?> ai) {
/*  58 */       ai.clearDesires();
/*  59 */       Servant owner = (Servant)ai.getOwner();
/*  60 */       Creature servantOwner = owner.getCreator();
/*     */       
/*  62 */       VisibleObject servantOwnerTarget = servantOwner.getTarget();
/*  63 */       if (servantOwnerTarget instanceof Creature)
/*     */       {
/*  65 */         ai.addDesire((Desire)new ServantAi.ServantSkillUseDesire(owner, (Creature)servantOwnerTarget, AIState.ACTIVE.getPriority()));
/*     */       }
/*     */ 
/*     */       
/*  69 */       if (ai.desireQueueSize() == 0) {
/*  70 */         ai.handleEvent(Event.NOTHING_TODO);
/*     */       } else {
/*  72 */         ai.schedule();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class ServantSkillUseDesire
/*     */     extends AbstractDesire
/*     */   {
/*     */     private Servant owner;
/*     */ 
/*     */ 
/*     */     
/*     */     private Creature target;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ServantSkillUseDesire(Servant owner, Creature target, int desirePower) {
/*  94 */       super(desirePower);
/*  95 */       this.owner = owner;
/*  96 */       this.target = target;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean handleDesire(AI<?> ai) {
/* 102 */       if (this.target == null || this.target.getLifeStats().isAlreadyDead()) {
/* 103 */         return true;
/*     */       }
/* 105 */       if (!this.owner.getActingCreature().isEnemy((VisibleObject)this.target)) {
/* 106 */         return false;
/*     */       }
/* 108 */       Skill skill = SkillEngine.getInstance().getSkill((Creature)this.owner, this.owner.getSkillId(), 1, (VisibleObject)this.target);
/* 109 */       if (skill != null) {
/*     */         
/* 111 */         skill.useSkill();
/*     */ 
/*     */         
/* 114 */         int maxHp = this.owner.getLifeStats().getMaxHp();
/* 115 */         this.owner.getLifeStats().reduceHp(Math.round(maxHp / 3.0F), null);
/*     */       } 
/* 117 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getExecutionInterval() {
/* 123 */       return 10;
/*     */     }
/*     */     
/*     */     public void onClear() {}
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\npcai\ServantAi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
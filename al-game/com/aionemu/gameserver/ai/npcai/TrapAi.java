/*     */ package com.aionemu.gameserver.ai.npcai;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*     */ import com.aionemu.gameserver.ai.desires.Desire;
/*     */ import com.aionemu.gameserver.ai.events.Event;
/*     */ import com.aionemu.gameserver.ai.events.handler.EventHandler;
/*     */ import com.aionemu.gameserver.ai.state.AIState;
/*     */ import com.aionemu.gameserver.ai.state.handler.NoneNpcStateHandler;
/*     */ import com.aionemu.gameserver.ai.state.handler.StateHandler;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Trap;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class TrapAi
/*     */   extends NpcAi
/*     */ {
/*     */   public TrapAi() {
/*  45 */     addEventHandler(new SeeObjectEventHandler());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     addStateHandler(new ActiveTrapStateHandler());
/*  51 */     addStateHandler((StateHandler)new NoneNpcStateHandler());
/*     */   }
/*     */ 
/*     */   
/*     */   public class SeeObjectEventHandler
/*     */     implements EventHandler
/*     */   {
/*     */     public Event getEvent() {
/*  59 */       return Event.SEE_CREATURE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleEvent(Event event, AI<?> ai) {
/*  65 */       ai.setAiState(AIState.ACTIVE);
/*  66 */       if (!ai.isScheduled()) {
/*  67 */         ai.analyzeState();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class ActiveTrapStateHandler
/*     */     extends StateHandler
/*     */   {
/*     */     public AIState getState() {
/*  77 */       return AIState.ACTIVE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleState(AIState state, AI<?> ai) {
/*  83 */       ai.clearDesires();
/*  84 */       Trap owner = (Trap)ai.getOwner();
/*  85 */       Creature trapCreator = owner.getCreator();
/*     */       
/*  87 */       int enemyCount = 0;
/*  88 */       for (VisibleObject visibleObject : owner.getKnownList().getKnownObjects().values()) {
/*     */         
/*  90 */         if (trapCreator.isEnemy(visibleObject)) {
/*  91 */           enemyCount++;
/*     */         }
/*     */       } 
/*  94 */       if (enemyCount > 0)
/*     */       {
/*  96 */         ai.addDesire((Desire)new TrapAi.TrapExplodeDesire(owner, trapCreator, AIState.ACTIVE.getPriority()));
/*     */       }
/*     */       
/*  99 */       if (ai.desireQueueSize() == 0) {
/* 100 */         ai.handleEvent(Event.NOTHING_TODO);
/*     */       } else {
/* 102 */         ai.schedule();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class TrapExplodeDesire
/*     */     extends AbstractDesire
/*     */   {
/*     */     private Trap owner;
/*     */ 
/*     */ 
/*     */     
/*     */     private Creature creator;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private TrapExplodeDesire(Trap owner, Creature creator, int desirePower) {
/* 124 */       super(desirePower);
/* 125 */       this.owner = owner;
/* 126 */       this.creator = creator;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean handleDesire(AI<?> ai) {
/* 132 */       for (VisibleObject visibleObject : this.owner.getKnownList().getKnownObjects().values()) {
/*     */         
/* 134 */         if (visibleObject == null) {
/*     */           continue;
/*     */         }
/* 137 */         if (visibleObject instanceof Creature) {
/*     */           
/* 139 */           Creature creature = (Creature)visibleObject;
/*     */           
/* 141 */           if (!creature.getLifeStats().isAlreadyDead() && MathUtil.isIn3dRange((VisibleObject)this.owner, (VisibleObject)creature, this.owner.getAggroRange())) {
/*     */ 
/*     */ 
/*     */             
/* 145 */             if (!this.creator.isEnemy((VisibleObject)creature)) {
/*     */               continue;
/*     */             }
/* 148 */             this.owner.getAi().setAiState(AIState.NONE);
/*     */             
/* 150 */             int skillId = this.owner.getSkillId();
/* 151 */             Skill skill = SkillEngine.getInstance().getSkill((Creature)this.owner, skillId, 1, (VisibleObject)creature);
/* 152 */             skill.useSkill();
/* 153 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 157 */                     TrapAi.TrapExplodeDesire.this.owner.getController().onDespawn(true);
/*     */                   }
/*     */                 },  (skill.getSkillTemplate().getDuration() + 1000));
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 164 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getExecutionInterval() {
/* 170 */       return 2;
/*     */     }
/*     */     
/*     */     public void onClear() {}
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\npcai\TrapAi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
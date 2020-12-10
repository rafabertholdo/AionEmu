/*     */ package com.aionemu.gameserver.ai;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.desires.Desire;
/*     */ import com.aionemu.gameserver.ai.desires.DesireIteratorFilter;
/*     */ import com.aionemu.gameserver.ai.desires.DesireIteratorHandler;
/*     */ import com.aionemu.gameserver.ai.desires.DesireQueue;
/*     */ import com.aionemu.gameserver.ai.desires.impl.CounterBasedDesireFilter;
/*     */ import com.aionemu.gameserver.ai.desires.impl.GeneralDesireIteratorHandler;
/*     */ import com.aionemu.gameserver.ai.events.Event;
/*     */ import com.aionemu.gameserver.ai.events.handler.EventHandler;
/*     */ import com.aionemu.gameserver.ai.npcai.DummyAi;
/*     */ import com.aionemu.gameserver.ai.state.AIState;
/*     */ import com.aionemu.gameserver.ai.state.handler.StateHandler;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import javolution.util.FastMap;
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
/*     */ public abstract class AI<T extends Creature>
/*     */   implements Runnable
/*     */ {
/*  42 */   private static final DummyAi dummyAi = new DummyAi();
/*     */   
/*  44 */   protected Map<Event, EventHandler> eventHandlers = (Map<Event, EventHandler>)new FastMap();
/*  45 */   protected Map<AIState, StateHandler> stateHandlers = (Map<AIState, StateHandler>)new FastMap();
/*     */   
/*  47 */   protected DesireQueue desireQueue = new DesireQueue();
/*     */   
/*     */   protected Creature owner;
/*     */   
/*  51 */   protected AIState aiState = AIState.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isStateChanged = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Future<?> aiTask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEvent(Event event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleTalk(Player player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getOwner() {
/*  81 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(Creature owner) {
/*  90 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AIState getAiState() {
/*  98 */     return this.aiState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addEventHandler(EventHandler eventHandler) {
/* 107 */     this.eventHandlers.put(eventHandler.getEvent(), eventHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addStateHandler(StateHandler stateHandler) {
/* 116 */     this.stateHandlers.put(stateHandler.getState(), stateHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAiState(AIState aiState) {
/* 124 */     if (this.aiState != aiState) {
/*     */       
/* 126 */       this.aiState = aiState;
/* 127 */       this.isStateChanged = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void analyzeState() {
/* 133 */     this.isStateChanged = false;
/* 134 */     StateHandler stateHandler = this.stateHandlers.get(this.aiState);
/* 135 */     if (stateHandler != null) {
/* 136 */       stateHandler.handleState(this.aiState, this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 142 */     this.desireQueue.iterateDesires((DesireIteratorHandler)new GeneralDesireIteratorHandler(this), new DesireIteratorFilter[] { (DesireIteratorFilter)new CounterBasedDesireFilter() });
/*     */     
/* 144 */     if (this.desireQueue.isEmpty() || this.isStateChanged)
/*     */     {
/* 146 */       analyzeState();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void schedule() {
/* 152 */     if (!isScheduled())
/*     */     {
/* 154 */       this.aiTask = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(this, 1000L, 1000L);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 160 */     if (this.aiTask != null && !this.aiTask.isCancelled()) {
/*     */       
/* 162 */       this.aiTask.cancel(true);
/* 163 */       this.aiTask = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScheduled() {
/* 169 */     return (this.aiTask != null && !this.aiTask.isCancelled());
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearDesires() {
/* 174 */     this.desireQueue.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDesire(Desire desire) {
/* 179 */     this.desireQueue.addDesire(desire);
/*     */   }
/*     */ 
/*     */   
/*     */   public int desireQueueSize() {
/* 184 */     return this.desireQueue.isEmpty() ? 0 : this.desireQueue.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DummyAi dummyAi() {
/* 192 */     return dummyAi;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\AI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
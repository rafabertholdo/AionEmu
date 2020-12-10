/*     */ package com.aionemu.gameserver.skillengine.task;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.concurrent.Future;
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
/*     */ public abstract class AbstractInteractionTask
/*     */ {
/*     */   private Future<?> task;
/*  32 */   private int interval = 2500;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Player requestor;
/*     */ 
/*     */   
/*     */   protected VisibleObject responder;
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInteractionTask(Player requestor, VisibleObject responder) {
/*  44 */     this.requestor = requestor;
/*  45 */     if (responder == null) {
/*  46 */       this.responder = (VisibleObject)requestor;
/*     */     } else {
/*  48 */       this.responder = responder;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean onInteraction();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onInteractionFinish();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onInteractionStart();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onInteractionAbort();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  78 */     onInteractionStart();
/*     */     
/*  80 */     this.task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/*  85 */             if (!AbstractInteractionTask.this.validateParticipants()) {
/*  86 */               AbstractInteractionTask.this.stop();
/*     */             }
/*  88 */             boolean stopTask = AbstractInteractionTask.this.onInteraction();
/*  89 */             if (stopTask) {
/*  90 */               AbstractInteractionTask.this.stop();
/*     */             }
/*     */           }
/*     */         },  1000L, this.interval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 101 */     onInteractionFinish();
/*     */     
/* 103 */     if (this.task != null && !this.task.isCancelled()) {
/*     */       
/* 105 */       this.task.cancel(true);
/* 106 */       this.task = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abort() {
/* 115 */     onInteractionAbort();
/* 116 */     stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInProgress() {
/* 124 */     return (this.task != null && !this.task.isCancelled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateParticipants() {
/* 132 */     return (this.requestor != null);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\task\AbstractInteractionTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.skillengine.task;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCraftTask
/*     */   extends AbstractInteractionTask
/*     */ {
/*     */   protected int successValue;
/*     */   protected int failureValue;
/*     */   protected int currentSuccessValue;
/*     */   protected int currentFailureValue;
/*     */   protected int skillLvlDiff;
/*     */   protected boolean critical;
/*     */   protected boolean setCritical = false;
/*     */   
/*     */   public AbstractCraftTask(Player requestor, VisibleObject responder, int successValue, int failureValue, int skillLvlDiff) {
/*  46 */     super(requestor, responder);
/*  47 */     this.successValue = successValue;
/*  48 */     this.failureValue = failureValue;
/*  49 */     this.skillLvlDiff = skillLvlDiff;
/*  50 */     this.critical = (Rnd.get(100) <= 15);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean onInteraction() {
/*  56 */     if (this.currentSuccessValue == this.successValue) {
/*     */       
/*  58 */       onSuccessFinish();
/*  59 */       return true;
/*     */     } 
/*  61 */     if (this.currentFailureValue == this.failureValue) {
/*     */       
/*  63 */       onFailureFinish();
/*  64 */       return true;
/*     */     } 
/*     */     
/*  67 */     analyzeInteraction();
/*     */     
/*  69 */     sendInteractionUpdate();
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void analyzeInteraction() {
/*  80 */     int multi = Math.max(0, 33 - this.skillLvlDiff * 5);
/*  81 */     if (Rnd.get(100) > multi) {
/*     */       
/*  83 */       if (this.critical && Rnd.get(100) < 30)
/*  84 */         this.setCritical = true; 
/*  85 */       this.currentSuccessValue += Rnd.get(this.successValue / (multi + 1) / 2, this.successValue);
/*     */     }
/*     */     else {
/*     */       
/*  89 */       this.currentFailureValue += Rnd.get(this.failureValue / (multi + 1) / 2, this.failureValue);
/*     */     } 
/*     */     
/*  92 */     if (this.currentSuccessValue >= this.successValue) {
/*     */       
/*  94 */       if (this.critical)
/*  95 */         this.setCritical = true; 
/*  96 */       this.currentSuccessValue = this.successValue;
/*     */     }
/*  98 */     else if (this.currentFailureValue >= this.failureValue) {
/*     */       
/* 100 */       this.currentFailureValue = this.failureValue;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void sendInteractionUpdate();
/*     */   
/*     */   protected abstract void onSuccessFinish();
/*     */   
/*     */   protected abstract void onFailureFinish();
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\task\AbstractCraftTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.ai.desires;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
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
/*     */ public abstract class AbstractDesire
/*     */   implements Desire
/*     */ {
/*     */   protected int executionCounter;
/*     */   protected int desirePower;
/*     */   
/*     */   protected AbstractDesire(int desirePower) {
/*  49 */     this.desirePower = desirePower;
/*     */   }
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
/*     */   public int compareTo(Desire o) {
/*  63 */     return o.getDesirePower() - getDesirePower();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDesirePower() {
/*  72 */     return this.desirePower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void increaseDesirePower(int desirePower) {
/*  81 */     this.desirePower += desirePower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleDesire(AI<?> ai) {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadyToRun() {
/*  96 */     boolean isReady = (this.executionCounter % getExecutionInterval() == 0);
/*  97 */     this.executionCounter++;
/*  98 */     return isReady;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reduceDesirePower(int desirePower) {
/* 107 */     this.desirePower -= desirePower;
/*     */   }
/*     */   
/*     */   public abstract int getExecutionInterval();
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\AbstractDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
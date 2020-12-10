/*    */ package com.aionemu.gameserver.ai.desires.impl;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.Desire;
/*    */ import com.aionemu.gameserver.ai.desires.DesireIteratorHandler;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneralDesireIteratorHandler
/*    */   implements DesireIteratorHandler
/*    */ {
/*    */   private AI ai;
/*    */   
/*    */   public GeneralDesireIteratorHandler(AI ai) {
/* 40 */     this.ai = ai;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void next(Desire desire, Iterator<Desire> iterator) {
/* 46 */     boolean isOk = desire.handleDesire(this.ai);
/* 47 */     if (!isOk) {
/*    */       
/* 49 */       desire.onClear();
/* 50 */       iterator.remove();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\GeneralDesireIteratorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
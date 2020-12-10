/*    */ package com.aionemu.gameserver.controllers.movement;
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
/*    */ public class StartMovingListener
/*    */   extends ActionObserver
/*    */ {
/*    */   private boolean effectorMoved = false;
/*    */   
/*    */   public StartMovingListener() {
/* 30 */     super(ActionObserver.ObserverType.MOVE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEffectorMoved() {
/* 38 */     return this.effectorMoved;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void moved() {
/* 44 */     this.effectorMoved = true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\StartMovingListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
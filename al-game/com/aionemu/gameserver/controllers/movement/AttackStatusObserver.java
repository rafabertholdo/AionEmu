/*    */ package com.aionemu.gameserver.controllers.movement;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
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
/*    */ public class AttackStatusObserver
/*    */   extends AttackCalcObserver
/*    */ {
/*    */   protected int value;
/*    */   protected AttackStatus status;
/*    */   
/*    */   public AttackStatusObserver(int value, AttackStatus status) {
/* 37 */     this.value = value;
/* 38 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\AttackStatusObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.controllers.movement;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.attack.AttackResult;
/*    */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*    */ import java.util.List;
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
/*    */ 
/*    */ public class AttackCalcObserver
/*    */ {
/*    */   public boolean checkStatus(AttackStatus status) {
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkShield(List<AttackResult> attackList) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkAttackerStatus(AttackStatus status) {
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\AttackCalcObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
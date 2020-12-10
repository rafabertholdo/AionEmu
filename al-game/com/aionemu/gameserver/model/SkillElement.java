/*    */ package com.aionemu.gameserver.model;
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
/*    */ public enum SkillElement
/*    */ {
/* 25 */   NONE(0),
/* 26 */   FIRE(1),
/* 27 */   WATER(2),
/* 28 */   WIND(3),
/* 29 */   EARTH(4);
/*    */   
/*    */   private int element;
/*    */   
/*    */   SkillElement(int id) {
/* 34 */     this.element = id;
/*    */   }
/*    */   
/*    */   public int getElementId() {
/* 38 */     return this.element;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\SkillElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.questEngine.model;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
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
/*    */ @XmlEnum
/*    */ public enum ConditionOperation
/*    */ {
/* 29 */   EQUAL,
/* 30 */   GREATER,
/* 31 */   GREATER_EQUAL,
/* 32 */   LESSER,
/* 33 */   LESSER_EQUAL,
/* 34 */   NOT_EQUAL,
/* 35 */   IN,
/* 36 */   NOT_IN;
/*    */   
/*    */   public String value() {
/* 39 */     return name();
/*    */   }
/*    */   
/*    */   public static ConditionOperation fromValue(String v) {
/* 43 */     return valueOf(v);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\ConditionOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
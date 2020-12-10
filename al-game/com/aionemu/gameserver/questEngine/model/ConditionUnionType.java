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
/*    */ public enum ConditionUnionType
/*    */ {
/* 29 */   AND,
/* 30 */   OR;
/*    */   
/*    */   public String value() {
/* 33 */     return name();
/*    */   }
/*    */   
/*    */   public static ConditionUnionType fromValue(String v) {
/* 37 */     return valueOf(v);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\ConditionUnionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
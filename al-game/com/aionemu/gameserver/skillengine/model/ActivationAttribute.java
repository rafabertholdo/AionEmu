/*    */ package com.aionemu.gameserver.skillengine.model;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlType(name = "activationAttribute")
/*    */ @XmlEnum
/*    */ public enum ActivationAttribute
/*    */ {
/* 30 */   NONE,
/* 31 */   ACTIVE,
/* 32 */   PROVOKED,
/* 33 */   MAINTAIN,
/* 34 */   TOGGLE,
/* 35 */   PASSIVE;
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\ActivationAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
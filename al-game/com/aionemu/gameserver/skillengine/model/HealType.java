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
/*    */ @XmlType(name = "HealType")
/*    */ @XmlEnum
/*    */ public enum HealType
/*    */ {
/* 30 */   HP,
/* 31 */   MP,
/* 32 */   DP,
/* 33 */   FP;
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\HealType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
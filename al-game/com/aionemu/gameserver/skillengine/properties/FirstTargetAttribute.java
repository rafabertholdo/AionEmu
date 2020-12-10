/*    */ package com.aionemu.gameserver.skillengine.properties;
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
/*    */ 
/*    */ @XmlType(name = "FirstTargetAttribute")
/*    */ @XmlEnum
/*    */ public enum FirstTargetAttribute
/*    */ {
/* 31 */   NONE,
/* 32 */   TARGETORME,
/* 33 */   ME,
/* 34 */   MYPET,
/* 35 */   TARGET,
/* 36 */   PASSIVE,
/* 37 */   TARGET_MYPARTY_NONVISIBLE,
/* 38 */   POINT;
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\FirstTargetAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
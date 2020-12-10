/*    */ package com.aionemu.gameserver.model.templates.item;
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
/*    */ @XmlType(name = "quality")
/*    */ @XmlEnum
/*    */ public enum ItemQuality
/*    */ {
/* 31 */   COMMON,
/* 32 */   RARE,
/* 33 */   UNIQUE,
/* 34 */   LEGEND,
/* 35 */   MYTHIC,
/* 36 */   EPIC,
/* 37 */   JUNK;
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\ItemQuality.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
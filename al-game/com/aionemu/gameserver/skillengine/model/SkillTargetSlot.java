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
/*    */ 
/*    */ @XmlType(name = "TargetSlot")
/*    */ @XmlEnum
/*    */ public enum SkillTargetSlot
/*    */ {
/* 31 */   BUFF,
/* 32 */   DEBUFF,
/* 33 */   CHANT,
/* 34 */   SPEC,
/* 35 */   SPEC2,
/* 36 */   BOOST,
/* 37 */   NOSHOW,
/* 38 */   NONE;
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\SkillTargetSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
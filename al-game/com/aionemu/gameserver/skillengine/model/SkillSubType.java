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
/*    */ @XmlType(name = "skillSubType")
/*    */ @XmlEnum
/*    */ public enum SkillSubType
/*    */ {
/* 30 */   NONE,
/* 31 */   ATTACK,
/* 32 */   CHANT,
/* 33 */   HEAL,
/* 34 */   BUFF,
/* 35 */   DEBUFF,
/* 36 */   SUMMON,
/* 37 */   SUMMONHOMING,
/* 38 */   SUMMONTRAP;
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\SkillSubType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
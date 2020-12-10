/*    */ package com.aionemu.gameserver.questEngine.handlers.models;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "MonsterInfo")
/*    */ public class MonsterInfo
/*    */ {
/*    */   @XmlAttribute(name = "var_id", required = true)
/*    */   protected int varId;
/*    */   @XmlAttribute(name = "min_var_value")
/*    */   protected Integer minVarValue;
/*    */   @XmlAttribute(name = "max_kill", required = true)
/*    */   protected int maxKill;
/*    */   @XmlAttribute(name = "npc_id", required = true)
/*    */   protected int npcId;
/*    */   
/*    */   public int getVarId() {
/* 48 */     return this.varId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getMinVarValue() {
/* 59 */     return this.minVarValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxKill() {
/* 68 */     return this.maxKill;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNpcId() {
/* 77 */     return this.npcId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\MonsterInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
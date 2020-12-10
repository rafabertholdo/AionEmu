/*    */ package com.aionemu.gameserver.model.gameobjects.stats.modifiers;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "SimpleModifier")
/*    */ public abstract class SimpleModifier
/*    */   extends StatModifier
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   
/*    */   public int getValue() {
/* 40 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setValue(int value) {
/* 45 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     String s = super.toString() + ",v:" + this.value;
/* 52 */     return s;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\SimpleModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
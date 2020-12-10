/*    */ package com.aionemu.gameserver.model.gameobjects.stats.modifiers;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;
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
/*    */ @XmlType(name = "MeanModifier")
/*    */ public class MeanModifier
/*    */   extends StatModifier
/*    */ {
/*    */   @XmlAttribute
/*    */   private int min;
/*    */   @XmlAttribute
/*    */   private int max;
/*    */   
/*    */   public int apply(int baseStat, int currentStat) {
/* 43 */     return baseStat + Math.round((this.min + this.max) / 2.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public StatModifierPriority getPriority() {
/* 49 */     return StatModifierPriority.HIGH;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     String s = super.toString() + ",m:" + this.min + ",M:" + this.max;
/* 56 */     return s;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\MeanModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
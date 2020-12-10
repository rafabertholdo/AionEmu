/*    */ package com.aionemu.gameserver.skillengine.change;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "Change")
/*    */ public class Change
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   private StatEnum stat;
/*    */   @XmlAttribute(required = true)
/*    */   private Func func;
/*    */   @XmlAttribute(required = true)
/*    */   private int value;
/*    */   @XmlAttribute
/*    */   private int delta;
/*    */   
/*    */   public StatEnum getStat() {
/* 47 */     return this.stat;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Func getFunc() {
/* 55 */     return this.func;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 63 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDelta() {
/* 71 */     return this.delta;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\change\Change.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
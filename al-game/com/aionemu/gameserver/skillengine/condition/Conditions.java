/*    */ package com.aionemu.gameserver.skillengine.condition;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlElements;
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
/*    */ @XmlType(name = "Conditions", propOrder = {"conditions"})
/*    */ public class Conditions
/*    */ {
/*    */   @XmlElements({@XmlElement(name = "target", type = TargetCondition.class), @XmlElement(name = "mp", type = MpCondition.class), @XmlElement(name = "hp", type = HpCondition.class), @XmlElement(name = "dp", type = DpCondition.class), @XmlElement(name = "playermove", type = PlayerMovedCondition.class), @XmlElement(name = "arrowcheck", type = ArrowCheckCondition.class)})
/*    */   protected List<Condition> conditions;
/*    */   
/*    */   public List<Condition> getConditions() {
/* 65 */     if (this.conditions == null) {
/* 66 */       this.conditions = new ArrayList<Condition>();
/*    */     }
/* 68 */     return this.conditions;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\condition\Conditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
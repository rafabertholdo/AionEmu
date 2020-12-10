/*    */ package com.aionemu.gameserver.skillengine.effect.modifier;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "ActionModifiers")
/*    */ public class ActionModifiers
/*    */ {
/*    */   @XmlElements({@XmlElement(name = "stumbledamage", type = StumbleDamageModifier.class), @XmlElement(name = "frontdamage", type = FrontDamageModifier.class), @XmlElement(name = "backdamage", type = BackDamageModifier.class), @XmlElement(name = "stundamage", type = StunDamageModifier.class), @XmlElement(name = "poisondamage", type = PoisonDamageModifier.class), @XmlElement(name = "targetrace", type = TargetRaceDamageModifier.class)})
/*    */   protected List<ActionModifier> actionModifiers;
/*    */   
/*    */   public List<ActionModifier> getActionModifiers() {
/* 59 */     if (this.actionModifiers == null) {
/* 60 */       this.actionModifiers = new ArrayList<ActionModifier>();
/*    */     }
/* 62 */     return this.actionModifiers;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\modifier\ActionModifiers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
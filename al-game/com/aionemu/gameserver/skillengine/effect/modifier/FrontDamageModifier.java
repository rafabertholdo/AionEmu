/*    */ package com.aionemu.gameserver.skillengine.effect.modifier;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.utils.PositionUtil;
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
/*    */ @XmlType(name = "FrontDamageModifier")
/*    */ public class FrontDamageModifier
/*    */   extends ActionModifier
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int delta;
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */   
/*    */   public int analyze(Effect effect, int originalValue) {
/* 46 */     return originalValue + this.value + effect.getSkillLevel() * this.delta;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean check(Effect effect) {
/* 52 */     return PositionUtil.isInFrontOfTarget((VisibleObject)effect.getEffector(), (VisibleObject)effect.getEffected());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\modifier\FrontDamageModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.skillengine.effect.modifier;
/*    */ 
/*    */ import com.aionemu.gameserver.skillengine.effect.EffectId;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "StunDamageModifier")
/*    */ public class StunDamageModifier
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
/* 52 */     return effect.getEffected().getEffectController().isAbnoramlSet(EffectId.STUN);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\modifier\StunDamageModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
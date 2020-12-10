/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*    */ import com.aionemu.gameserver.skillengine.action.DamageType;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.HealType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "SpellAtkDrainInstantEffect")
/*    */ public class SpellAtkDrainInstantEffect
/*    */   extends DamageEffect
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int percent;
/*    */   @XmlAttribute(name = "heal_type")
/*    */   protected HealType healType;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 45 */     super.applyEffect(effect);
/* 46 */     int value = effect.getReserved1() * this.percent / 100;
/* 47 */     switch (this.healType) {
/*    */       
/*    */       case HP:
/* 50 */         effect.getEffector().getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, value);
/*    */         break;
/*    */       case MP:
/* 53 */         effect.getEffector().getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, value);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 61 */     calculate(effect, DamageType.MAGICAL);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SpellAtkDrainInstantEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
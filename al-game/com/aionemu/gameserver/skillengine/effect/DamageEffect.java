/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*    */ import com.aionemu.gameserver.controllers.attack.AttackUtil;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*    */ import com.aionemu.gameserver.skillengine.action.DamageType;
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
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "DamageEffect")
/*    */ public abstract class DamageEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */   @XmlAttribute
/*    */   protected int delta;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 51 */     effect.getEffected().getController().onAttack(effect.getEffector(), effect.getSkillId(), SM_ATTACK_STATUS.TYPE.REGULAR, effect.getReserved1());
/*    */     
/* 53 */     effect.getEffector().getObserveController().notifyAttackObservers(effect.getEffected());
/*    */   }
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect, DamageType damageType) {
/* 58 */     int skillLvl = effect.getSkillLevel();
/* 59 */     int valueWithDelta = this.value + this.delta * skillLvl;
/* 60 */     valueWithDelta = applyActionModifiers(effect, valueWithDelta);
/*    */ 
/*    */     
/* 63 */     if (effect.getEffected() instanceof com.aionemu.gameserver.model.gameobjects.player.Player && effect.getPvpDamage() != 0) {
/* 64 */       valueWithDelta = Math.round(valueWithDelta * effect.getPvpDamage() / 100.0F);
/*    */     }
/* 66 */     switch (damageType) {
/*    */       
/*    */       case PHYSICAL:
/* 69 */         AttackUtil.calculatePhysicalSkillAttackResult(effect, valueWithDelta);
/*    */         break;
/*    */       case MAGICAL:
/* 72 */         AttackUtil.calculateMagicalSkillAttackResult(effect, valueWithDelta, getElement());
/*    */         break;
/*    */       default:
/* 75 */         AttackUtil.calculatePhysicalSkillAttackResult(effect, 0);
/*    */         break;
/*    */     } 
/* 78 */     if (effect.getAttackStatus() != AttackStatus.RESIST && effect.getAttackStatus() != AttackStatus.DODGE)
/* 79 */       effect.addSucessEffect(this); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\DamageEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
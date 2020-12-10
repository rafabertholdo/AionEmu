/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import com.aionemu.gameserver.utils.stats.StatFunctions;
/*    */ import java.util.concurrent.Future;
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
/*    */ @XmlType(name = "BleedEffect")
/*    */ public class BleedEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int checktime;
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   @XmlAttribute
/*    */   protected int delta;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 51 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 57 */     if (calculateEffectResistRate(effect, StatEnum.BLEED_RESISTANCE)) {
/* 58 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 64 */     Creature effected = effect.getEffected();
/* 65 */     effected.getEffectController().unsetAbnormal(EffectId.BLEED.getEffectId());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPeriodicAction(Effect effect) {
/* 71 */     Creature effected = effect.getEffected();
/* 72 */     Creature effector = effect.getEffector();
/* 73 */     int valueWithDelta = this.value + this.delta * effect.getSkillLevel();
/* 74 */     int damage = StatFunctions.calculateMagicDamageToTarget(effector, effected, valueWithDelta, getElement());
/* 75 */     effected.getController().onAttack(effector, effect.getSkillId(), SM_ATTACK_STATUS.TYPE.DAMAGE, damage);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(final Effect effect) {
/* 81 */     Creature effected = effect.getEffected();
/*    */     
/* 83 */     effected.getEffectController().setAbnormal(EffectId.BLEED.getEffectId());
/*    */     
/* 85 */     Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 90 */             BleedEffect.this.onPeriodicAction(effect);
/*    */           }
/*    */         },  this.checktime, this.checktime);
/* 93 */     effect.setPeriodicTask(task, this.position);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\BleedEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
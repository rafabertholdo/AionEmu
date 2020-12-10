/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "DamageOverTimeEffect")
/*    */ public class DamageOverTimeEffect
/*    */   extends DamageEffect
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int checktime;
/*    */   
/*    */   public void calculate(Effect effect) {
/* 47 */     if (calculateEffectResistRate(effect, null)) {
/* 48 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 54 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPeriodicAction(Effect effect) {
/* 66 */     Creature effected = effect.getEffected();
/* 67 */     Creature effector = effect.getEffector();
/* 68 */     int valueWithDelta = this.value + this.delta * effect.getSkillLevel();
/* 69 */     int damage = StatFunctions.calculateMagicDamageToTarget(effector, effected, valueWithDelta, getElement());
/* 70 */     effected.getController().onAttack(effector, effect.getSkillId(), SM_ATTACK_STATUS.TYPE.DAMAGE, damage);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(final Effect effect) {
/* 76 */     Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 81 */             DamageOverTimeEffect.this.onPeriodicAction(effect);
/*    */           }
/*    */         },  this.checktime, this.checktime);
/* 84 */     effect.setPeriodicTask(task, this.position);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\DamageOverTimeEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
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
/*    */ @XmlType(name = "SnareEffect")
/*    */ public class SnareEffect
/*    */   extends BufEffect
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 38 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 44 */     if (calculateEffectResistRate(effect, StatEnum.SNARE_RESISTANCE)) {
/* 45 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 51 */     super.endEffect(effect);
/* 52 */     effect.getEffected().getEffectController().unsetAbnormal(EffectId.SNARE.getEffectId());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 58 */     super.startEffect(effect);
/* 59 */     effect.getEffected().getEffectController().setAbnormal(EffectId.SNARE.getEffectId());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SnareEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
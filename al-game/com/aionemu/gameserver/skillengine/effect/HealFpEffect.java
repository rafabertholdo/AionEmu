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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "HealFpEffect")
/*    */ public class HealFpEffect
/*    */   extends AbstractHealEffect
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 37 */     effect.getEffected().getLifeStats().increaseFp(-effect.getReserved1());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 43 */     super.calculate(effect);
/* 44 */     effect.addSucessEffect(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getCurrentStatValue(Effect effect) {
/* 50 */     return effect.getEffected().getLifeStats().getCurrentFp();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getMaxStatValue(Effect effect) {
/* 56 */     return effect.getEffected().getGameStats().getCurrentStat(StatEnum.FLY_TIME);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\HealFpEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
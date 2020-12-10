/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "HealDpEffect")
/*    */ public class HealDpEffect
/*    */   extends AbstractHealEffect
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 41 */     ((Player)effect.getEffected()).getCommonData().addDp(-effect.getReserved1());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 47 */     super.calculate(effect);
/* 48 */     effect.addSucessEffect(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getCurrentStatValue(Effect effect) {
/* 54 */     return ((Player)effect.getEffected()).getCommonData().getDp();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getMaxStatValue(Effect effect) {
/* 60 */     return effect.getEffected().getGameStats().getCurrentStat(StatEnum.MAXDP);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\HealDpEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
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
/*    */ @XmlType(name = "ItemHealMpEffect")
/*    */ public class ItemHealMpEffect
/*    */   extends AbstractHealEffect
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 42 */     effect.getEffected().getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, -effect.getReserved1());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 48 */     super.calculate(effect);
/* 49 */     effect.addSucessEffect(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getCurrentStatValue(Effect effect) {
/* 55 */     return ((Player)effect.getEffected()).getLifeStats().getCurrentMp();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getMaxStatValue(Effect effect) {
/* 61 */     return effect.getEffected().getGameStats().getCurrentStat(StatEnum.MAXMP);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ItemHealMpEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
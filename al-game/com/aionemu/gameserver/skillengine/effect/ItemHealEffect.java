/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
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
/*    */ @XmlType(name = "ItemHealEffect")
/*    */ public class ItemHealEffect
/*    */   extends AbstractHealEffect
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 40 */     effect.getEffected().getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, -effect.getReserved1());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 46 */     super.calculate(effect);
/* 47 */     effect.addSucessEffect(this);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ItemHealEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
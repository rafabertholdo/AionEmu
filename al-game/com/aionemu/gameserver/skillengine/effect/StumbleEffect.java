/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.SpellStatus;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "StumbleEffect")
/*    */ public class StumbleEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 41 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 47 */     if (calculateEffectResistRate(effect, StatEnum.STUMBLE_RESISTANCE)) {
/*    */       
/* 49 */       effect.addSucessEffect(this);
/* 50 */       effect.setSpellStatus(SpellStatus.STUMBLE);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 57 */     Creature effected = effect.getEffected();
/* 58 */     effected.getController().cancelCurrentSkill();
/* 59 */     effected.getEffectController().setAbnormal(EffectId.STUMBLE.getEffectId());
/* 60 */     PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_FORCED_MOVE(effect.getEffector(), effected));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 66 */     effect.getEffected().getEffectController().unsetAbnormal(EffectId.STUMBLE.getEffectId());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\StumbleEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
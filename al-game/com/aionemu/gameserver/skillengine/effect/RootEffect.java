/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_IMMOBILIZE;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "RootEffect")
/*    */ public class RootEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 42 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 48 */     if (calculateEffectResistRate(effect, StatEnum.ROOT_RESISTANCE)) {
/* 49 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(final Effect effect) {
/* 55 */     final Creature effected = effect.getEffected();
/* 56 */     effected.getEffectController().setAbnormal(EffectId.ROOT.getEffectId());
/* 57 */     PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_TARGET_IMMOBILIZE(effected));
/*    */     
/* 59 */     effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.ATTACKED)
/*    */         {
/*    */ 
/*    */           
/*    */           public void attacked(Creature creature)
/*    */           {
/* 65 */             effected.getEffectController().removeEffect(effect.getSkillId());
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 74 */     effect.getEffected().getEffectController().unsetAbnormal(EffectId.ROOT.getEffectId());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\RootEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
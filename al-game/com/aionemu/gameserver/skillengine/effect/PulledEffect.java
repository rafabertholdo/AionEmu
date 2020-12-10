/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.SpellStatus;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ @XmlType(name = "PulledEffect")
/*    */ public class PulledEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 42 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 48 */     if (effect.getEffector() instanceof com.aionemu.gameserver.model.gameobjects.player.Player && effect.getEffected() != null)
/*    */     {
/* 50 */       effect.addSucessEffect(this);
/*    */     }
/* 52 */     effect.setSpellStatus(SpellStatus.NONE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 58 */     final Creature effector = effect.getEffector();
/* 59 */     final Creature effected = effect.getEffected();
/* 60 */     effected.getEffectController().setAbnormal(EffectId.CANNOT_MOVE.getEffectId());
/* 61 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 65 */             World.getInstance().updatePosition((VisibleObject)effected, effector.getX(), effector.getY(), effector.getZ() + 0.25F, effected.getHeading());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 71 */             effector.getMoveController().setDirectionChanged(true);
/* 72 */             PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_FORCED_MOVE(effector, effected));
/*    */           }
/*    */         }1000L);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 80 */     effect.getEffected().getEffectController().unsetAbnormal(EffectId.CANNOT_MOVE.getEffectId());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\PulledEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
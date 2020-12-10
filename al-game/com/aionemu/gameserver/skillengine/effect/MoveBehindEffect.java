/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_MOVE;
/*    */ import com.aionemu.gameserver.skillengine.action.DamageType;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.utils.MathUtil;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ @XmlType(name = "MoveBehindEffect")
/*    */ public class MoveBehindEffect
/*    */   extends DamageEffect
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 43 */     super.applyEffect(effect);
/* 44 */     Player effector = (Player)effect.getEffector();
/* 45 */     Creature effected = effect.getEffected();
/*    */ 
/*    */     
/* 48 */     double radian = Math.toRadians(MathUtil.convertHeadingToDegree(effected.getHeading()));
/* 49 */     float x1 = (float)(Math.cos(Math.PI + radian) * 1.2999999523162842D);
/* 50 */     float y1 = (float)(Math.sin(Math.PI + radian) * 1.2999999523162842D);
/* 51 */     World.getInstance().updatePosition((VisibleObject)effector, effected.getX() + x1, effected.getY() + y1, effected.getZ() + 0.25F, effected.getHeading());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     PacketSendUtility.sendPacket(effector, (AionServerPacket)new SM_PLAYER_MOVE(effector.getX(), effector.getY(), effector.getZ(), effector.getHeading()));
/*    */   }
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
/*    */   public void calculate(Effect effect) {
/* 71 */     if (effect.getEffector() instanceof Player && effect.getEffected() != null)
/*    */     {
/* 73 */       calculate(effect, DamageType.PHYSICAL);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\MoveBehindEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
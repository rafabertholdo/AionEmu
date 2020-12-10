/*    */ package com.aionemu.gameserver.skillengine.properties;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
/*    */ import com.aionemu.gameserver.utils.MathUtil;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import org.apache.log4j.Logger;
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
/*    */ @XmlType(name = "FirstTargetRangeProperty")
/*    */ public class FirstTargetRangeProperty
/*    */   extends Property
/*    */ {
/* 44 */   private static final Logger log = Logger.getLogger(FirstTargetRangeProperty.class);
/*    */ 
/*    */   
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */ 
/*    */   
/*    */   public boolean set(Skill skill) {
/* 52 */     if (!skill.isFirstTargetRangeCheck()) {
/* 53 */       return true;
/*    */     }
/* 55 */     Creature effector = skill.getEffector();
/* 56 */     Creature firstTarget = skill.getFirstTarget();
/* 57 */     if (firstTarget == null) {
/* 58 */       return false;
/*    */     }
/* 60 */     if (firstTarget.getPosition().getMapId() == 0) {
/* 61 */       log.warn("FirstTarget has mapId of 0. (" + firstTarget.getName() + ")");
/*    */     }
/* 63 */     skill.setFirstTargetRange(this.value);
/*    */     
/* 65 */     if (MathUtil.isIn3dRange((VisibleObject)effector, (VisibleObject)firstTarget, (this.value + 4)))
/*    */     {
/* 67 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 71 */     if (effector instanceof Player)
/*    */     {
/* 73 */       PacketSendUtility.sendPacket((Player)effector, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ATTACK_TOO_FAR_FROM_TARGET());
/*    */     }
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\FirstTargetRangeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
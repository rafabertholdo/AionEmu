/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.services.TeleportService;
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
/*    */ @XmlType(name = "ReturnEffect")
/*    */ public class ReturnEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 39 */     TeleportService.moveToBindLocation((Player)effect.getEffector(), true, 500);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 45 */     if (effect.getEffected().isSpawned())
/* 46 */       effect.addSucessEffect(this); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ReturnEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
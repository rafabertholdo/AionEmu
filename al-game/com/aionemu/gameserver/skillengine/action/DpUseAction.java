/*    */ package com.aionemu.gameserver.skillengine.action;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "DpUseAction")
/*    */ public class DpUseAction
/*    */   extends Action
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */   
/*    */   public void act(Skill skill) {
/* 43 */     Player effector = (Player)skill.getEffector();
/* 44 */     int currentDp = effector.getCommonData().getDp();
/*    */     
/* 46 */     if (currentDp <= 0 || currentDp < this.value) {
/*    */       return;
/*    */     }
/* 49 */     effector.getCommonData().setDp(currentDp - this.value);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\action\DpUseAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
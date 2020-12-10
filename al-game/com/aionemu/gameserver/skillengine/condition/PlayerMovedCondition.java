/*    */ package com.aionemu.gameserver.skillengine.condition;
/*    */ 
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "PlayerMovedCondition")
/*    */ public class PlayerMovedCondition
/*    */   extends Condition
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected boolean allow;
/*    */   
/*    */   public boolean isAllow() {
/* 30 */     return this.allow;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean verify(Skill skill) {
/* 36 */     return (this.allow == skill.getConditionChangeListener().isEffectorMoved());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\condition\PlayerMovedCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
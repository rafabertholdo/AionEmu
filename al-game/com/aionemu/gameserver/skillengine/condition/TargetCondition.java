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
/*    */ @XmlType(name = "TargetCondition")
/*    */ public class TargetCondition
/*    */   extends Condition
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected TargetAttribute value;
/*    */   
/*    */   public TargetAttribute getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean verify(Skill skill) {
/* 57 */     if (this.value != TargetAttribute.NONE && skill.getFirstTarget() == null)
/*    */     {
/* 59 */       return false;
/*    */     }
/* 61 */     switch (this.value) {
/*    */       
/*    */       case NPC:
/* 64 */         return skill.getFirstTarget() instanceof com.aionemu.gameserver.model.gameobjects.Npc;
/*    */       case PC:
/* 66 */         return skill.getFirstTarget() instanceof com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */     } 
/* 68 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\condition\TargetCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
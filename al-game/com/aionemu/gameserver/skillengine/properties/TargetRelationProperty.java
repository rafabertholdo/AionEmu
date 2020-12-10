/*    */ package com.aionemu.gameserver.skillengine.properties;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "TargetRelationProperty")
/*    */ public class TargetRelationProperty
/*    */   extends Property
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected TargetRelationAttribute value;
/*    */   
/*    */   public TargetRelationAttribute getValue() {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean set(Skill skill) {
/*    */     Iterator<Creature> iter;
/* 55 */     List<Creature> effectedList = skill.getEffectedList();
/* 56 */     Creature effector = skill.getEffector();
/*    */     
/* 58 */     switch (this.value) {
/*    */ 
/*    */ 
/*    */       
/*    */       case ENEMY:
/* 63 */         for (iter = effectedList.iterator(); iter.hasNext(); ) {
/*    */           
/* 65 */           Creature nextEffected = iter.next();
/*    */           
/* 67 */           if (effector.isEnemy((VisibleObject)nextEffected)) {
/*    */             continue;
/*    */           }
/* 70 */           iter.remove();
/*    */         } 
/*    */         break;
/*    */       case FRIEND:
/* 74 */         for (iter = effectedList.iterator(); iter.hasNext(); ) {
/*    */           
/* 76 */           Creature nextEffected = iter.next();
/*    */           
/* 78 */           if (!effector.isEnemy((VisibleObject)nextEffected)) {
/*    */             continue;
/*    */           }
/* 81 */           iter.remove();
/*    */         } 
/*    */         
/* 84 */         if (effectedList.size() == 0) {
/*    */           
/* 86 */           skill.setFirstTarget(skill.getEffector());
/* 87 */           effectedList.add(skill.getEffector());
/*    */           
/*    */           break;
/*    */         } 
/* 91 */         skill.setFirstTarget(effectedList.get(0));
/*    */         break;
/*    */     } 
/*    */ 
/*    */     
/* 96 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\TargetRelationProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
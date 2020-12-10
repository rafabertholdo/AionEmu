/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.ConditionOperation;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "DialogIdCondition")
/*    */ public class DialogIdCondition
/*    */   extends QuestCondition
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */   
/*    */   public int getValue() {
/* 45 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doCheck(QuestEnv env) {
/* 54 */     int data = env.getDialogId().intValue();
/* 55 */     switch (getOp()) {
/*    */       
/*    */       case EQUAL:
/* 58 */         return (data == this.value);
/*    */       case NOT_EQUAL:
/* 60 */         return (data != this.value);
/*    */     } 
/* 62 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\DialogIdCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
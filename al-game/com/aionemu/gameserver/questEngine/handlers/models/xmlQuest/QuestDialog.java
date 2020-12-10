/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions.QuestConditions;
/*    */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations.QuestOperations;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
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
/*    */ @XmlType(name = "QuestDialog", propOrder = {"conditions", "operations"})
/*    */ public class QuestDialog
/*    */ {
/*    */   protected QuestConditions conditions;
/*    */   protected QuestOperations operations;
/*    */   @XmlAttribute(required = true)
/*    */   protected int id;
/*    */   
/*    */   public boolean operate(QuestEnv env, QuestState qs) {
/* 46 */     if (env.getDialogId().intValue() != this.id)
/* 47 */       return false; 
/* 48 */     if (this.conditions == null || this.conditions.checkConditionOfSet(env))
/*    */     {
/* 50 */       if (this.operations != null)
/*    */       {
/* 52 */         return this.operations.operate(env);
/*    */       }
/*    */     }
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\QuestDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
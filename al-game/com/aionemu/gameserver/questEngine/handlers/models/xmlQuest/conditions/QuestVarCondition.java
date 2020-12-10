/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.ConditionOperation;
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
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "QuestVarCondition")
/*    */ public class QuestVarCondition
/*    */   extends QuestCondition
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */   @XmlAttribute(name = "var_id", required = true)
/*    */   protected int varId;
/*    */   
/*    */   public boolean doCheck(QuestEnv env) {
/* 48 */     QuestState qs = env.getPlayer().getQuestStateList().getQuestState(env.getQuestId().intValue());
/* 49 */     if (qs == null)
/*    */     {
/* 51 */       return false;
/*    */     }
/* 53 */     int var = qs.getQuestVars().getVarById(this.varId);
/* 54 */     switch (getOp()) {
/*    */       
/*    */       case EQUAL:
/* 57 */         return (var == this.value);
/*    */       case GREATER:
/* 59 */         return (var > this.value);
/*    */       case GREATER_EQUAL:
/* 61 */         return (var >= this.value);
/*    */       case LESSER:
/* 63 */         return (var < this.value);
/*    */       case LESSER_EQUAL:
/* 65 */         return (var <= this.value);
/*    */       case NOT_EQUAL:
/* 67 */         return (var != this.value);
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\QuestVarCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
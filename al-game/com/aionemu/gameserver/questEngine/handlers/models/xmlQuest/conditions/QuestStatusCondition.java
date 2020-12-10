/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.questEngine.model.ConditionOperation;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
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
/*    */ @XmlType(name = "QuestStatusCondition")
/*    */ public class QuestStatusCondition
/*    */   extends QuestCondition
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected QuestStatus value;
/*    */   @XmlAttribute(name = "quest_id")
/*    */   protected Integer questId;
/*    */   
/*    */   public boolean doCheck(QuestEnv env) {
/* 51 */     Player player = env.getPlayer();
/* 52 */     int qstatus = 0;
/* 53 */     int id = env.getQuestId().intValue();
/* 54 */     if (this.questId != null)
/* 55 */       id = this.questId.intValue(); 
/* 56 */     QuestState qs = player.getQuestStateList().getQuestState(id);
/* 57 */     if (qs != null) {
/* 58 */       qstatus = qs.getStatus().value();
/*    */     }
/* 60 */     switch (getOp()) {
/*    */       
/*    */       case EQUAL:
/* 63 */         return (qstatus == this.value.value());
/*    */       case GREATER:
/* 65 */         return (qstatus > this.value.value());
/*    */       case GREATER_EQUAL:
/* 67 */         return (qstatus >= this.value.value());
/*    */       case LESSER:
/* 69 */         return (qstatus < this.value.value());
/*    */       case LESSER_EQUAL:
/* 71 */         return (qstatus <= this.value.value());
/*    */       case NOT_EQUAL:
/* 73 */         return (qstatus != this.value.value());
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\QuestStatusCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
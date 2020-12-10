/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions.QuestConditions;
/*    */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations.QuestOperations;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlSeeAlso;
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
/*    */ @XmlType(name = "QuestEvent", propOrder = {"conditions", "operations"})
/*    */ @XmlSeeAlso({OnKillEvent.class, OnTalkEvent.class})
/*    */ public abstract class QuestEvent
/*    */ {
/*    */   protected QuestConditions conditions;
/*    */   protected QuestOperations operations;
/*    */   @XmlAttribute
/*    */   protected List<Integer> ids;
/*    */   
/*    */   public boolean operate(QuestEnv env) {
/* 51 */     return false;
/*    */   }
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
/*    */   public List<Integer> getIds() {
/* 77 */     if (this.ids == null)
/*    */     {
/* 79 */       this.ids = new ArrayList<Integer>();
/*    */     }
/* 81 */     return this.ids;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\events\QuestEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
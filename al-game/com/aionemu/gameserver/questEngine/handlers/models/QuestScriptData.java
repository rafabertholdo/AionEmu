/*    */ package com.aionemu.gameserver.questEngine.handlers.models;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "QuestScriptData")
/*    */ @XmlSeeAlso({ReportToData.class, MonsterHuntData.class, ItemCollectingData.class, WorkOrdersData.class, XmlQuestData.class})
/*    */ public abstract class QuestScriptData
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int id;
/*    */   
/*    */   public int getId() {
/* 50 */     return this.id;
/*    */   }
/*    */   
/*    */   public abstract void register(QuestEngine paramQuestEngine);
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\QuestScriptData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
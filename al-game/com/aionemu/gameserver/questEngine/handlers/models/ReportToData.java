/*    */ package com.aionemu.gameserver.questEngine.handlers.models;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.handlers.template.ReportTo;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "ReportToData")
/*    */ public class ReportToData
/*    */   extends QuestScriptData
/*    */ {
/*    */   @XmlAttribute(name = "start_npc_id")
/*    */   protected int startNpcId;
/*    */   @XmlAttribute(name = "end_npc_id")
/*    */   protected int endNpc;
/*    */   @XmlAttribute(name = "item_id", required = true)
/*    */   protected int itemId;
/*    */   
/*    */   public void register(QuestEngine questEngine) {
/* 45 */     ReportTo template = new ReportTo(this.id, this.startNpcId, this.endNpc, this.itemId);
/* 46 */     questEngine.addQuestHandler((QuestHandler)template);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\ReportToData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
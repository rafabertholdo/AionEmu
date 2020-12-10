/*    */ package com.aionemu.gameserver.questEngine.handlers.models;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.handlers.template.ItemCollecting;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "ItemCollectingData")
/*    */ public class ItemCollectingData
/*    */   extends QuestScriptData
/*    */ {
/*    */   @XmlAttribute(name = "start_npc_id", required = true)
/*    */   protected int startNpcId;
/*    */   @XmlAttribute(name = "action_item_id")
/*    */   protected int actionItemId;
/*    */   @XmlAttribute(name = "end_npc_id")
/*    */   protected int endNpcId;
/*    */   
/*    */   public void register(QuestEngine questEngine) {
/* 46 */     ItemCollecting template = new ItemCollecting(this.id, this.startNpcId, this.actionItemId, this.endNpcId);
/* 47 */     questEngine.addQuestHandler((QuestHandler)template);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\ItemCollectingData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
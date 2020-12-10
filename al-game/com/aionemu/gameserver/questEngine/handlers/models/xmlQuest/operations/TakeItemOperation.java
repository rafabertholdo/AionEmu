/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.services.ItemService;
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
/*    */ @XmlType(name = "TakeItemOperation")
/*    */ public class TakeItemOperation
/*    */   extends QuestOperation
/*    */ {
/*    */   @XmlAttribute(name = "item_id", required = true)
/*    */   protected int itemId;
/*    */   @XmlAttribute(required = true)
/*    */   protected int count;
/*    */   
/*    */   public void doOperate(QuestEnv env) {
/* 48 */     ItemService.decreaseItemCountByItemId(env.getPlayer(), this.itemId, this.count);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\TakeItemOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
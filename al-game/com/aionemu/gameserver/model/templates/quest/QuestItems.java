/*    */ package com.aionemu.gameserver.model.templates.quest;
/*    */ 
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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "QuestItems")
/*    */ public class QuestItems
/*    */ {
/*    */   @XmlAttribute(name = "item_id")
/*    */   protected Integer itemId;
/*    */   @XmlAttribute
/*    */   protected Integer count;
/*    */   
/*    */   public QuestItems() {}
/*    */   
/*    */   public QuestItems(int itemId, int count) {
/* 48 */     this.itemId = Integer.valueOf(itemId);
/* 49 */     this.count = Integer.valueOf(count);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getItemId() {
/* 60 */     return this.itemId;
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
/*    */   public Integer getCount() {
/* 72 */     return this.count;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\quest\QuestItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
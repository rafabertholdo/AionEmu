/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ @XmlType(name = "PcInventoryCondition")
/*    */ public class PcInventoryCondition
/*    */   extends QuestCondition
/*    */ {
/*    */   @XmlAttribute(name = "item_id", required = true)
/*    */   protected int itemId;
/*    */   @XmlAttribute(required = true)
/*    */   protected long count;
/*    */   
/*    */   public int getItemId() {
/* 48 */     return this.itemId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getCount() {
/* 57 */     return this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doCheck(QuestEnv env) {
/* 66 */     Player player = env.getPlayer();
/* 67 */     long itemCount = player.getInventory().getItemCountByItemId(this.itemId);
/* 68 */     switch (getOp()) {
/*    */       
/*    */       case EQUAL:
/* 71 */         return (itemCount == this.count);
/*    */       case GREATER:
/* 73 */         return (itemCount > this.count);
/*    */       case GREATER_EQUAL:
/* 75 */         return (itemCount >= this.count);
/*    */       case LESSER:
/* 77 */         return (itemCount < this.count);
/*    */       case LESSER_EQUAL:
/* 79 */         return (itemCount <= this.count);
/*    */       case NOT_EQUAL:
/* 81 */         return (itemCount != this.count);
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\PcInventoryCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
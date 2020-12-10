/*    */ package com.aionemu.gameserver.questEngine.model;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
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
/*    */ @XmlEnum
/*    */ public enum QuestStatus
/*    */ {
/* 28 */   NONE(0),
/* 29 */   START(3),
/* 30 */   REWARD(4),
/* 31 */   COMPLETE(5),
/* 32 */   LOCKED(6);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   QuestStatus(int id) {
/* 38 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int value() {
/* 43 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\QuestStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
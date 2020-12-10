/*    */ package com.aionemu.gameserver.questEngine.model;
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
/*    */ public class QuestVars
/*    */ {
/* 26 */   private Integer[] questVars = new Integer[5];
/*    */ 
/*    */ 
/*    */   
/*    */   public QuestVars() {}
/*    */ 
/*    */   
/*    */   public QuestVars(int var) {
/* 34 */     setVar(var);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getVarById(int id) {
/* 43 */     return this.questVars[id].intValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setVarById(int id, int var) {
/* 52 */     this.questVars[id] = Integer.valueOf(var);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuestVars() {
/* 60 */     int var = 0;
/* 61 */     var |= this.questVars[4].intValue();
/* 62 */     for (int i = 3; i >= 0; i--) {
/*    */       
/* 64 */       var <<= 6;
/* 65 */       var |= this.questVars[i].intValue();
/*    */     } 
/* 67 */     return var;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVar(int var) {
/* 72 */     for (int i = 0; i < 5; i++) {
/*    */ 
/*    */       
/* 75 */       this.questVars[i] = Integer.valueOf(var & 0x3F);
/* 76 */       var >>= 6;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\QuestVars.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
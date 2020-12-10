/*    */ package com.aionemu.gameserver.model.gameobjects.stats.id;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;
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
/*    */ public class StatEffectId
/*    */   implements Comparable<StatEffectId>
/*    */ {
/*    */   private int id;
/*    */   private StatEffectType type;
/*    */   
/*    */   protected StatEffectId(int id, StatEffectType type) {
/* 32 */     this.id = id;
/* 33 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public static StatEffectId getInstance(int id, StatEffectType type) {
/* 38 */     return new StatEffectId(id, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 44 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 50 */     boolean result = true;
/* 51 */     result = (result && o != null);
/* 52 */     result = (result && o instanceof StatEffectId);
/* 53 */     result = (result && ((StatEffectId)o).id == this.id);
/* 54 */     result = (result && ((StatEffectId)o).type.getValue() == this.type.getValue());
/* 55 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(StatEffectId o) {
/* 61 */     int result = 0;
/* 62 */     if (o == null) {
/*    */       
/* 64 */       result = this.id;
/*    */     }
/*    */     else {
/*    */       
/* 68 */       result = this.type.getValue() - o.type.getValue();
/* 69 */       if (result == 0)
/*    */       {
/* 71 */         result = this.id - o.id;
/*    */       }
/*    */     } 
/* 74 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 80 */     String str = "id:" + this.id + ",type:" + this.type;
/* 81 */     return str;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\StatEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
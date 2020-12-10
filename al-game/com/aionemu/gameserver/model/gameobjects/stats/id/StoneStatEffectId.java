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
/*    */ 
/*    */ public class StoneStatEffectId
/*    */   extends StatEffectId
/*    */ {
/*    */   private int slot;
/*    */   
/*    */   private StoneStatEffectId(int id, int slot) {
/* 32 */     super(id, StatEffectType.STONE_EFFECT);
/* 33 */     this.slot = slot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static StoneStatEffectId getInstance(int id, int slot) {
/* 44 */     return new StoneStatEffectId(id, slot);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 50 */     boolean result = super.equals(o);
/* 51 */     result = (result && o != null);
/* 52 */     result = (result && o instanceof StoneStatEffectId);
/* 53 */     result = (result && ((StoneStatEffectId)o).slot == this.slot);
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(StatEffectId o) {
/* 60 */     int result = super.compareTo(o);
/* 61 */     if (result == 0)
/*    */     {
/* 63 */       if (o instanceof StoneStatEffectId)
/*    */       {
/* 65 */         result = this.slot - ((StoneStatEffectId)o).slot;
/*    */       }
/*    */     }
/* 68 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     String str = super.toString() + ",slot:" + this.slot;
/* 75 */     return str;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSlot() {
/* 80 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\StoneStatEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
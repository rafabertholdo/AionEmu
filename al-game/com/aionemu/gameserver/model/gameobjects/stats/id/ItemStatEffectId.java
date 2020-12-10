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
/*    */ public class ItemStatEffectId
/*    */   extends StatEffectId
/*    */ {
/*    */   private int slot;
/*    */   
/*    */   private ItemStatEffectId(int id, int slot) {
/* 31 */     super(id, StatEffectType.ITEM_EFFECT);
/* 32 */     this.slot = slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ItemStatEffectId getInstance(int id, int slot) {
/* 37 */     return new ItemStatEffectId(id, slot);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 43 */     boolean result = super.equals(o);
/* 44 */     result = (result && o != null);
/* 45 */     result = (result && o instanceof ItemStatEffectId);
/* 46 */     result = (result && ((ItemStatEffectId)o).slot == this.slot);
/* 47 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(StatEffectId o) {
/* 53 */     int result = super.compareTo(o);
/* 54 */     if (result == 0)
/*    */     {
/* 56 */       if (o instanceof ItemStatEffectId)
/*    */       {
/* 58 */         result = this.slot - ((ItemStatEffectId)o).slot;
/*    */       }
/*    */     }
/* 61 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     String str = super.toString() + ",slot:" + this.slot;
/* 68 */     return str;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSlot() {
/* 73 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\ItemStatEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
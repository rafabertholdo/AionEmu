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
/*    */ public class EnchantStatEffectId
/*    */   extends StatEffectId
/*    */ {
/*    */   private int slot;
/*    */   
/*    */   private EnchantStatEffectId(int id, int slot) {
/* 32 */     super(id, StatEffectType.ENCHANT_EFFECT);
/* 33 */     this.slot = slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public static EnchantStatEffectId getInstance(int id, int slot) {
/* 38 */     return new EnchantStatEffectId(id, slot);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 44 */     boolean result = super.equals(o);
/* 45 */     result = (result && o != null);
/* 46 */     result = (result && o instanceof EnchantStatEffectId);
/* 47 */     result = (result && ((EnchantStatEffectId)o).slot == this.slot);
/* 48 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(StatEffectId o) {
/* 54 */     int result = super.compareTo(o);
/* 55 */     if (result == 0)
/*    */     {
/* 57 */       if (o instanceof EnchantStatEffectId)
/*    */       {
/* 59 */         result = this.slot - ((EnchantStatEffectId)o).slot;
/*    */       }
/*    */     }
/* 62 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 68 */     String str = super.toString() + ",slot:" + this.slot;
/* 69 */     return str;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSlot() {
/* 74 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\EnchantStatEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
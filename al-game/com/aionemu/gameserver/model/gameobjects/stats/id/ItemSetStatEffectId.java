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
/*    */ public class ItemSetStatEffectId
/*    */   extends StatEffectId
/*    */ {
/*    */   private int setpart;
/*    */   
/*    */   private ItemSetStatEffectId(int id, int setpart) {
/* 32 */     super(id, StatEffectType.ITEM_SET_EFFECT);
/* 33 */     this.setpart = setpart;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ItemSetStatEffectId getInstance(int id, int setpart) {
/* 38 */     return new ItemSetStatEffectId(id, setpart);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 44 */     boolean result = super.equals(o);
/* 45 */     result = (result && o != null);
/* 46 */     result = (result && o instanceof ItemSetStatEffectId);
/* 47 */     result = (result && ((ItemSetStatEffectId)o).setpart == this.setpart);
/* 48 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(StatEffectId o) {
/* 54 */     int result = super.compareTo(o);
/* 55 */     if (result == 0)
/*    */     {
/* 57 */       if (o instanceof ItemSetStatEffectId)
/*    */       {
/* 59 */         result = this.setpart - ((ItemSetStatEffectId)o).setpart;
/*    */       }
/*    */     }
/* 62 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 68 */     String str = super.toString() + ",parts:" + this.setpart;
/* 69 */     return str;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\ItemSetStatEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
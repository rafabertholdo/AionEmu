/*    */ package com.aionemu.gameserver.model.gameobjects.stats.modifiers;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;
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
/*    */ public class SubModifier
/*    */   extends SimpleModifier
/*    */ {
/*    */   public int apply(int baseStat, int currentStat) {
/* 30 */     if (isBonus())
/*    */     {
/* 32 */       return Math.round((-1 * this.value));
/*    */     }
/*    */ 
/*    */     
/* 36 */     return Math.round((baseStat - this.value));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StatModifierPriority getPriority() {
/* 43 */     return StatModifierPriority.LOW;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\SubModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.model.gameobjects.stats.modifiers;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
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
/*    */ public class SetModifier
/*    */   extends SimpleModifier
/*    */ {
/*    */   public int apply(int baseStat, int currentStat) {
/* 31 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public StatModifierPriority getPriority() {
/* 37 */     return StatModifierPriority.HIGH;
/*    */   }
/*    */ 
/*    */   
/*    */   public static SetModifier newInstance(StatEnum stat, int value, boolean isBonus) {
/* 42 */     SetModifier m = new SetModifier();
/* 43 */     m.setStat(stat);
/* 44 */     m.setValue(value);
/* 45 */     m.setBonus(isBonus);
/* 46 */     m.nextId();
/* 47 */     return m;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\SetModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
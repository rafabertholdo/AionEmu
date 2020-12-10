/*    */ package com.aionemu.gameserver.model.gameobjects.stats;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*    */ import javolution.util.FastList;
/*    */ import javolution.util.FastMap;
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
/*    */ public class StatModifiers
/*    */ {
/* 34 */   private FastMap<StatModifierPriority, FastList<StatModifier>> modifiers = new FastMap();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean add(StatModifier modifier) {
/* 39 */     if (!this.modifiers.containsKey(modifier.getPriority()))
/*    */     {
/* 41 */       this.modifiers.put(modifier.getPriority(), new FastList());
/*    */     }
/* 43 */     return ((FastList)this.modifiers.get(modifier.getPriority())).add(modifier);
/*    */   }
/*    */ 
/*    */   
/*    */   public FastList<StatModifier> getModifiers(StatModifierPriority priority) {
/* 48 */     if (!this.modifiers.containsKey(priority))
/*    */     {
/* 50 */       this.modifiers.put(priority, new FastList());
/*    */     }
/* 52 */     return (FastList<StatModifier>)this.modifiers.get(priority);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\StatModifiers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
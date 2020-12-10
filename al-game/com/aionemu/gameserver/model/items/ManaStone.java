/*    */ package com.aionemu.gameserver.model.items;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*    */ import java.util.TreeSet;
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
/*    */ public class ManaStone
/*    */   extends ItemStone
/*    */ {
/*    */   private TreeSet<StatModifier> modifiers;
/*    */   
/*    */   public ManaStone(int itemObjId, int itemId, int slot, PersistentState persistentState) {
/* 37 */     super(itemObjId, itemId, slot, ItemStone.ItemStoneType.MANASTONE, persistentState);
/*    */     
/* 39 */     ItemTemplate stoneTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
/* 40 */     if (stoneTemplate != null && stoneTemplate.getModifiers() != null)
/*    */     {
/* 42 */       this.modifiers = stoneTemplate.getModifiers();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TreeSet<StatModifier> getModifiers() {
/* 51 */     return this.modifiers;
/*    */   }
/*    */ 
/*    */   
/*    */   public StatModifier getFirstModifier() {
/* 56 */     return (this.modifiers != null) ? this.modifiers.first() : null;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\ManaStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
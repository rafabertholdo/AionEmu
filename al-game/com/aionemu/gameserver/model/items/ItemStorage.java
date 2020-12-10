/*     */ package com.aionemu.gameserver.model.items;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javolution.util.FastList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStorage
/*     */ {
/*     */   public static final int FIRST_AVAILABLE_SLOT = 65535;
/*     */   private FastList<Item> storageItems;
/*  41 */   private int limit = 0;
/*     */ 
/*     */   
/*     */   public ItemStorage(int limit) {
/*  45 */     this.limit = limit;
/*  46 */     this.storageItems = new FastList(limit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getStorageItems() {
/*  55 */     return Collections.unmodifiableList((List<? extends Item>)this.storageItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLimit() {
/*  63 */     return this.limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(int limit) {
/*  71 */     this.limit = limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemFromStorageByItemId(int itemId) {
/*  80 */     for (Item item : this.storageItems) {
/*     */       
/*  82 */       ItemTemplate itemTemplate = item.getItemTemplate();
/*  83 */       if (itemTemplate.getTemplateId() == itemId)
/*     */       {
/*  85 */         return item;
/*     */       }
/*     */     } 
/*     */     
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getItemsFromStorageByItemId(int itemId) {
/*  99 */     List<Item> itemList = new ArrayList<Item>();
/*     */     
/* 101 */     for (Item item : this.storageItems) {
/*     */       
/* 103 */       ItemTemplate itemTemplate = item.getItemTemplate();
/* 104 */       if (itemTemplate.getTemplateId() == itemId)
/*     */       {
/* 106 */         itemList.add(item);
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return itemList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemFromStorageByItemObjId(int itemObjId) {
/* 119 */     for (Item item : this.storageItems) {
/*     */       
/* 121 */       if (item.getObjectId() == itemObjId)
/*     */       {
/* 123 */         return item;
/*     */       }
/*     */     } 
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlotIdByItemId(int itemId) {
/* 135 */     for (Item item : this.storageItems) {
/*     */       
/* 137 */       ItemTemplate itemTemplate = item.getItemTemplate();
/* 138 */       if (itemTemplate.getTemplateId() == itemId)
/*     */       {
/* 140 */         return item.getEquipmentSlot();
/*     */       }
/*     */     } 
/* 143 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlotIdByObjId(int objId) {
/* 152 */     for (Item item : this.storageItems) {
/*     */       
/* 154 */       if (item.getObjectId() == objId)
/*     */       {
/* 156 */         return item.getEquipmentSlot();
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextAvailableSlot() {
/* 170 */     return 65535;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item putToNextAvailableSlot(Item item) {
/* 183 */     if (!isFull() && this.storageItems.add(item)) {
/* 184 */       return item;
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeItemFromStorage(Item item) {
/* 198 */     return this.storageItems.remove(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 203 */     return (this.storageItems.size() >= this.limit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfFreeSlots() {
/* 211 */     return this.limit - this.storageItems.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 221 */     return this.storageItems.size();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\ItemStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.items.ItemStorage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
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
/*     */ public class Storage
/*     */ {
/*     */   private int ownerId;
/*     */   protected ItemStorage storage;
/*     */   private Item kinahItem;
/*     */   protected int storageType;
/*  43 */   protected Queue<Item> deletedItems = new ConcurrentLinkedQueue<Item>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private PersistentState persistentState = PersistentState.UPDATED;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Storage(StorageType storageType) {
/*  55 */     switch (storageType) {
/*     */       
/*     */       case CUBE:
/*  58 */         this.storage = new ItemStorage(109);
/*  59 */         this.storageType = storageType.getId();
/*     */         break;
/*     */       case REGULAR_WAREHOUSE:
/*  62 */         this.storage = new ItemStorage(104);
/*  63 */         this.storageType = storageType.getId();
/*     */         break;
/*     */       case ACCOUNT_WAREHOUSE:
/*  66 */         this.storage = new ItemStorage(17);
/*  67 */         this.storageType = storageType.getId();
/*     */         break;
/*     */       case LEGION_WAREHOUSE:
/*  70 */         this.storage = new ItemStorage(25);
/*  71 */         this.storageType = storageType.getId();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOwnerId() {
/*  81 */     return this.ownerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwnerId(int ownerId) {
/*  89 */     this.ownerId = ownerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getKinahItem() {
/*  97 */     return this.kinahItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKinahItem(Item kinahItem) {
/* 105 */     this.kinahItem = kinahItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStorageType() {
/* 110 */     return this.storageType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increaseKinah(long amount) {
/* 119 */     this.kinahItem.increaseItemCount(amount);
/* 120 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decreaseKinah(long amount) {
/* 129 */     boolean operationResult = this.kinahItem.decreaseItemCount(amount);
/* 130 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/* 131 */     return operationResult;
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
/*     */   
/*     */   public Item putToBag(Item item) {
/* 145 */     Item resultItem = this.storage.putToNextAvailableSlot(item);
/* 146 */     if (resultItem != null)
/*     */     {
/* 148 */       resultItem.setItemLocation(this.storageType);
/*     */     }
/* 150 */     item.setOwnerId(this.ownerId);
/* 151 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/* 152 */     return resultItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFromBag(Item item, boolean persist) {
/* 163 */     boolean operationResult = this.storage.removeItemFromStorage(item);
/* 164 */     if (operationResult && persist) {
/*     */       
/* 166 */       item.setPersistentState(PersistentState.DELETED);
/* 167 */       this.deletedItems.add(item);
/* 168 */       setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getFirstItemByItemId(int itemId) {
/* 179 */     List<Item> items = this.storage.getItemsFromStorageByItemId(itemId);
/* 180 */     if (items.size() == 0)
/* 181 */       return null; 
/* 182 */     return items.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getAllItems() {
/* 192 */     List<Item> allItems = new ArrayList<Item>();
/* 193 */     if (this.kinahItem != null)
/* 194 */       allItems.add(this.kinahItem); 
/* 195 */     allItems.addAll(this.storage.getStorageItems());
/* 196 */     return allItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Queue<Item> getDeletedItems() {
/* 206 */     return this.deletedItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getAllItemsByItemId(int itemId) {
/* 217 */     List<Item> allItemsByItemId = new ArrayList<Item>();
/*     */     
/* 219 */     for (Item item : this.storage.getStorageItems()) {
/*     */       
/* 221 */       if (item.getItemTemplate().getTemplateId() == itemId)
/* 222 */         allItemsByItemId.add(item); 
/*     */     } 
/* 224 */     return allItemsByItemId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getStorageItems() {
/* 230 */     return this.storage.getStorageItems();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemByObjId(int value) {
/* 241 */     return this.storage.getItemFromStorageByItemObjId(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getItemsByItemId(int value) {
/* 251 */     return this.storage.getItemsFromStorageByItemId(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getItemCountByItemId(int itemId) {
/* 261 */     List<Item> items = getItemsByItemId(itemId);
/* 262 */     long count = 0L;
/* 263 */     for (Item item : items)
/*     */     {
/* 265 */       count += item.getItemCount();
/*     */     }
/* 267 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 277 */     return this.storage.isFull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfFreeSlots() {
/* 287 */     return this.storage.getNumberOfFreeSlots();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(int limit) {
/* 297 */     this.storage.setLimit(limit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLimit() {
/* 307 */     return this.storage.getLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 315 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 323 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increaseItemCount(Item item, long count) {
/* 332 */     item.increaseItemCount(count);
/* 333 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item putToNextAvailableSlot(Item item) {
/* 338 */     return this.storage.putToNextAvailableSlot(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 347 */     return this.storage.size();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Storage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
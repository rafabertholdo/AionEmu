/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.items.GodStone;
/*     */ import com.aionemu.gameserver.model.items.ManaStone;
/*     */ import com.aionemu.gameserver.model.templates.item.EquipType;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.ItemUpdater;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import java.util.Comparator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class Item
/*     */   extends AionObject
/*     */ {
/*  38 */   private long itemCount = 1L;
/*     */   
/*  40 */   private int ownerId = 0;
/*  41 */   private int itemColor = 0;
/*     */   
/*     */   private ItemTemplate itemTemplate;
/*     */   
/*     */   private ItemTemplate itemSkinTemplate;
/*     */   
/*     */   private boolean isEquipped = false;
/*  48 */   private int equipmentSlot = 65535;
/*     */ 
/*     */ 
/*     */   
/*     */   private PersistentState persistentState;
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<ManaStone> manaStones;
/*     */ 
/*     */ 
/*     */   
/*     */   private GodStone godStone;
/*     */ 
/*     */   
/*     */   private boolean isSoulBound = false;
/*     */ 
/*     */   
/*     */   private int itemLocation;
/*     */ 
/*     */   
/*     */   private int enchantLevel;
/*     */ 
/*     */   
/*     */   private int fusionedItemId;
/*     */ 
/*     */ 
/*     */   
/*     */   public Item(int ownerId, int objId, ItemTemplate itemTemplate, long itemCount, boolean isEquipped, int equipmentSlot) {
/*  77 */     super(Integer.valueOf(objId));
/*  78 */     this.ownerId = ownerId;
/*  79 */     this.itemTemplate = itemTemplate;
/*  80 */     this.itemCount = itemCount;
/*  81 */     this.isEquipped = isEquipped;
/*  82 */     this.equipmentSlot = equipmentSlot;
/*  83 */     this.persistentState = PersistentState.NEW;
/*  84 */     if (ownerId != 0) {
/*  85 */       ItemUpdater.getInstance().add(this);
/*     */     }
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
/*     */   public Item(int ownerId, int objId, int itemId, long itemCount, int itemColor, boolean isEquipped, boolean isSoulBound, int equipmentSlot, int itemLocation, int enchant, int itemSkin, int fusionedItem) {
/* 100 */     super(Integer.valueOf(objId));
/* 101 */     this.ownerId = ownerId;
/* 102 */     this.itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
/* 103 */     this.itemCount = itemCount;
/* 104 */     this.itemColor = itemColor;
/* 105 */     this.isEquipped = isEquipped;
/* 106 */     this.isSoulBound = isSoulBound;
/* 107 */     this.equipmentSlot = equipmentSlot;
/* 108 */     this.itemLocation = itemLocation;
/* 109 */     this.enchantLevel = enchant;
/* 110 */     this.fusionedItemId = fusionedItem;
/* 111 */     this.itemSkinTemplate = DataManager.ITEM_DATA.getItemTemplate(itemSkin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOwnerId() {
/* 119 */     return this.ownerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwnerId(int ownerId) {
/* 127 */     this.ownerId = ownerId;
/* 128 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 136 */     return String.valueOf(this.itemTemplate.getNameId());
/*     */   }
/*     */   
/*     */   public String getItemName() {
/* 140 */     return this.itemTemplate.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemTemplate getItemTemplate() {
/* 148 */     return this.itemTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemTemplate getItemSkinTemplate() {
/* 156 */     if (this.itemSkinTemplate == null)
/* 157 */       return this.itemTemplate; 
/* 158 */     return this.itemSkinTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItemSkinTemplate(ItemTemplate newTemplate) {
/* 163 */     this.itemSkinTemplate = newTemplate;
/* 164 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemColor() {
/* 172 */     return this.itemColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemColor(int itemColor) {
/* 180 */     this.itemColor = itemColor;
/* 181 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemTemplate(ItemTemplate itemTemplate) {
/* 189 */     this.itemTemplate = itemTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getItemCount() {
/* 198 */     return this.itemCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemCount(long itemCount) {
/* 206 */     this.itemCount = itemCount;
/* 207 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
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
/*     */   public void increaseItemCount(long addCount) {
/* 220 */     this.itemCount += addCount;
/* 221 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
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
/*     */   public boolean decreaseItemCount(long remCount) {
/* 233 */     if (this.itemCount - remCount >= 0L) {
/*     */       
/* 235 */       this.itemCount -= remCount;
/* 236 */       if (this.itemCount == 0L && !this.itemTemplate.isKinah()) {
/*     */         
/* 238 */         setPersistentState(PersistentState.DELETED);
/*     */       }
/*     */       else {
/*     */         
/* 242 */         setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */       } 
/* 244 */       return true;
/*     */     } 
/*     */     
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEquipped() {
/* 255 */     return this.isEquipped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEquipped(boolean isEquipped) {
/* 263 */     this.isEquipped = isEquipped;
/* 264 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEquipmentSlot() {
/* 273 */     return this.equipmentSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEquipmentSlot(int equipmentSlot) {
/* 281 */     this.equipmentSlot = equipmentSlot;
/* 282 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<ManaStone> getItemStones() {
/* 291 */     if (this.manaStones == null) {
/* 292 */       this.manaStones = new TreeSet<ManaStone>(new Comparator<ManaStone>()
/*     */           {
/*     */             
/*     */             public int compare(ManaStone o1, ManaStone o2)
/*     */             {
/* 297 */               if (o1.getSlot() == o2.getSlot())
/* 298 */                 return 0; 
/* 299 */               return (o1.getSlot() > o2.getSlot()) ? 1 : -1;
/*     */             }
/*     */           });
/*     */     }
/* 303 */     return this.manaStones;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasManaStones() {
/* 313 */     return (this.manaStones != null && this.manaStones.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GodStone getGodStone() {
/* 321 */     return this.godStone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GodStone addGodStone(int itemId) {
/* 331 */     PersistentState state = (this.godStone != null) ? PersistentState.UPDATE_REQUIRED : PersistentState.NEW;
/* 332 */     this.godStone = new GodStone(getObjectId(), itemId, state);
/* 333 */     return this.godStone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGoodStone(GodStone goodStone) {
/* 341 */     this.godStone = goodStone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnchantLevel() {
/* 349 */     return this.enchantLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnchantLevel(int enchantLevel) {
/* 357 */     this.enchantLevel = enchantLevel;
/* 358 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 366 */     return this.persistentState;
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
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 381 */     if (persistentState != PersistentState.UPDATED && persistentState != PersistentState.NOACTION && this.ownerId != 0)
/* 382 */       ItemUpdater.getInstance().add(this); 
/* 383 */     switch (persistentState) {
/*     */       
/*     */       case DELETED:
/* 386 */         if (this.persistentState == PersistentState.NEW) {
/* 387 */           this.persistentState = PersistentState.NOACTION;
/*     */         } else {
/* 389 */           this.persistentState = PersistentState.DELETED;
/*     */         }  return;
/*     */       case UPDATE_REQUIRED:
/* 392 */         if (this.persistentState == PersistentState.NEW)
/*     */           return;  break;
/*     */     } 
/* 395 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemLocation(int storageType) {
/* 402 */     this.itemLocation = storageType;
/* 403 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemLocation() {
/* 408 */     return this.itemLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemMask() {
/* 413 */     return this.itemTemplate.getMask();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSoulBound() {
/* 418 */     return this.isSoulBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTradeable() {
/* 427 */     return ((getItemMask() & 0x2) == 2 && !this.isSoulBound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSoulBound(boolean isSoulBound) {
/* 432 */     this.isSoulBound = isSoulBound;
/* 433 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */   
/*     */   public EquipType getEquipmentType() {
/* 438 */     if (this.itemTemplate.isStigma()) {
/* 439 */       return EquipType.STIGMA;
/*     */     }
/* 441 */     return this.itemTemplate.getEquipmentType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 447 */     return "Item [equipmentSlot=" + this.equipmentSlot + ", godStone=" + this.godStone + ", isEquipped=" + this.isEquipped + ", itemColor=" + this.itemColor + ", itemCount=" + this.itemCount + ", itemLocation=" + this.itemLocation + ", itemTemplate=" + this.itemTemplate + ", manaStones=" + this.manaStones + ", persistentState=" + this.persistentState + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemId() {
/* 455 */     return this.itemTemplate.getTemplateId();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNameID() {
/* 460 */     return this.itemTemplate.getNameId();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFusionedItem() {
/* 465 */     return (this.fusionedItemId != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFusionedItem() {
/* 470 */     return this.fusionedItemId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFusionedItem(int itemTemplateId) {
/* 475 */     this.fusionedItemId = itemTemplateId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finalize() {
/* 485 */     if (this.persistentState == PersistentState.NOACTION || this.persistentState == PersistentState.DELETED || this.ownerId == 0)
/*     */     {
/* 487 */       IDFactory.getInstance().releaseId(getObjectId());
/*     */     }
/*     */     
/*     */     try {
/* 491 */       super.finalize();
/*     */     }
/* 493 */     catch (Throwable e) {
/*     */       
/* 495 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
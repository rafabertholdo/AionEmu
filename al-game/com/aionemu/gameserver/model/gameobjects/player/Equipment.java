/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.listeners.ItemEquipmentListener;
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
/*     */ import com.aionemu.gameserver.model.templates.item.ArmorType;
/*     */ import com.aionemu.gameserver.model.templates.item.EquipType;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemRace;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*     */ import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.StigmaService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import javolution.util.FastList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Equipment
/*     */ {
/*  49 */   private SortedMap<Integer, Item> equipment = new TreeMap<Integer, Item>();
/*     */   private Player owner;
/*  51 */   private static final Logger log = Logger.getLogger(Equipment.class);
/*  52 */   private Set<Integer> markedFreeSlots = new HashSet<Integer>();
/*  53 */   private PersistentState persistentState = PersistentState.UPDATED;
/*     */ 
/*     */   
/*     */   public Equipment(Player player) {
/*  57 */     this.owner = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item equipItem(int itemUniqueId, int slot) {
/*  68 */     Item item = this.owner.getInventory().getItemByObjId(itemUniqueId);
/*     */     
/*  70 */     if (item == null) {
/*  71 */       return null;
/*     */     }
/*  73 */     ItemTemplate itemTemplate = item.getItemTemplate();
/*     */ 
/*     */     
/*  76 */     if (itemTemplate.getLevel() > this.owner.getCommonData().getLevel()) {
/*     */       
/*  78 */       PacketSendUtility.sendPacket(this.owner, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_USE_ITEM_TOO_LOW_LEVEL_MUST_BE_THIS_LEVEL(itemTemplate.getLevel(), new DescriptionId(Integer.parseInt(item.getName()))));
/*     */       
/*  80 */       return null;
/*     */     } 
/*     */     
/*  83 */     if (this.owner.getAccessLevel() == 0) {
/*     */       
/*  85 */       switch (itemTemplate.getRace()) {
/*     */         
/*     */         case ARROW:
/*  88 */           if (this.owner.getCommonData().getRace() != Race.ASMODIANS)
/*  89 */             return null; 
/*     */           break;
/*     */         case SHIELD:
/*  92 */           if (this.owner.getCommonData().getRace() != Race.ELYOS) {
/*  93 */             return null;
/*     */           }
/*     */           break;
/*     */       } 
/*  97 */       if (!itemTemplate.isAllowedFor(this.owner.getCommonData().getPlayerClass(), this.owner.getLevel())) {
/*  98 */         return null;
/*     */       }
/*     */     } 
/* 101 */     int itemSlotToEquip = 0;
/*     */     
/* 103 */     synchronized (this.equipment) {
/*     */       
/* 105 */       this.markedFreeSlots.clear();
/*     */ 
/*     */       
/* 108 */       switch (item.getEquipmentType()) {
/*     */         
/*     */         case ARROW:
/* 111 */           if (!validateEquippedArmor(item, true))
/* 112 */             return null; 
/*     */           break;
/*     */         case SHIELD:
/* 115 */           if (!validateEquippedWeapon(item, true)) {
/* 116 */             return null;
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 121 */       int itemSlotMask = 0;
/* 122 */       switch (item.getEquipmentType()) {
/*     */         
/*     */         case null:
/* 125 */           itemSlotMask = slot;
/*     */           break;
/*     */         default:
/* 128 */           itemSlotMask = itemTemplate.getItemSlot();
/*     */           break;
/*     */       } 
/*     */       
/* 132 */       if (!StigmaService.notifyEquipAction(this.owner, item)) {
/* 133 */         return null;
/*     */       }
/* 135 */       FastList<ItemSlot> fastList = ItemSlot.getSlotsFor(itemSlotMask);
/* 136 */       for (ItemSlot possibleSlot : fastList) {
/*     */         
/* 138 */         int slotId = possibleSlot.getSlotIdMask();
/* 139 */         if (this.equipment.get(Integer.valueOf(slotId)) == null || this.markedFreeSlots.contains(Integer.valueOf(slotId))) {
/*     */           
/* 141 */           itemSlotToEquip = slotId;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       if (itemSlotToEquip == 0)
/*     */       {
/* 149 */         itemSlotToEquip = ((ItemSlot)fastList.get(0)).getSlotIdMask();
/*     */       }
/*     */     } 
/*     */     
/* 153 */     if (itemSlotToEquip == 0) {
/* 154 */       return null;
/*     */     }
/* 156 */     if (itemTemplate.isSoulBound() && !item.isSoulBound()) {
/*     */       
/* 158 */       soulBindItem(this.owner, item, itemSlotToEquip);
/* 159 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 163 */     return equip(itemSlotToEquip, item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Item equip(int itemSlotToEquip, Item item) {
/* 174 */     synchronized (this.equipment) {
/*     */ 
/*     */       
/* 177 */       ItemService.removeItem(this.owner, this.owner.getInventory(), item, false, false);
/*     */       
/* 179 */       Item equippedItem = this.equipment.get(Integer.valueOf(itemSlotToEquip));
/* 180 */       if (equippedItem != null) {
/* 181 */         unEquip(itemSlotToEquip);
/*     */       }
/* 183 */       switch (item.getEquipmentType()) {
/*     */         
/*     */         case ARROW:
/* 186 */           validateEquippedArmor(item, false);
/*     */           break;
/*     */         case SHIELD:
/* 189 */           validateEquippedWeapon(item, false);
/*     */           break;
/*     */       } 
/*     */       
/* 193 */       if (this.equipment.get(Integer.valueOf(itemSlotToEquip)) != null) {
/*     */         
/* 195 */         log.error("CHECKPOINT : putting item to already equiped slot. Info slot: " + itemSlotToEquip + " new item: " + item.getItemTemplate().getTemplateId() + " old item: " + ((Item)this.equipment.get(Integer.valueOf(itemSlotToEquip))).getItemTemplate().getTemplateId());
/*     */ 
/*     */         
/* 198 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 202 */       this.equipment.put(Integer.valueOf(itemSlotToEquip), item);
/* 203 */       item.setEquipped(true);
/* 204 */       item.setEquipmentSlot(itemSlotToEquip);
/* 205 */       PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item));
/*     */ 
/*     */       
/* 208 */       ItemEquipmentListener.onItemEquipment(item, this.owner);
/* 209 */       this.owner.getObserveController().notifyItemEquip(item, this.owner);
/* 210 */       this.owner.getLifeStats().updateCurrentStats();
/* 211 */       setPersistentState(PersistentState.UPDATE_REQUIRED);
/* 212 */       return item;
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
/*     */   public Item unEquipItem(int itemUniqueId, int slot) {
/* 227 */     if (this.owner.getInventory().isFull()) {
/* 228 */       return null;
/*     */     }
/* 230 */     synchronized (this.equipment) {
/*     */       
/* 232 */       Item itemToUnequip = null;
/*     */       
/* 234 */       for (Item item : this.equipment.values()) {
/*     */         
/* 236 */         if (item.getObjectId() == itemUniqueId)
/*     */         {
/* 238 */           itemToUnequip = item;
/*     */         }
/*     */       } 
/*     */       
/* 242 */       if (itemToUnequip == null || !itemToUnequip.isEquipped()) {
/* 243 */         return null;
/*     */       }
/*     */       
/* 246 */       if (itemToUnequip.getItemTemplate().getWeaponType() == WeaponType.BOW) {
/*     */         
/* 248 */         Item possibleArrows = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/* 249 */         if (possibleArrows != null && possibleArrows.getItemTemplate().getArmorType() == ArmorType.ARROW) {
/*     */ 
/*     */           
/* 252 */           if (this.owner.getInventory().getNumberOfFreeSlots() < 1)
/* 253 */             return null; 
/* 254 */           unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
/*     */         } 
/*     */       } 
/*     */       
/* 258 */       if (itemToUnequip.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask()) {
/*     */         
/* 260 */         Item ohWeapon = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/* 261 */         if (ohWeapon != null && ohWeapon.getItemTemplate().isWeapon()) {
/*     */           
/* 263 */           if (this.owner.getInventory().getNumberOfFreeSlots() < 2) {
/* 264 */             return null;
/*     */           }
/* 266 */           unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 271 */       if (itemToUnequip.getItemTemplate().isArmor() && itemToUnequip.getItemTemplate().getArmorType() == ArmorType.SHARD) {
/*     */         
/* 273 */         this.owner.unsetState(CreatureState.POWERSHARD);
/* 274 */         PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_EMOTION(this.owner, EmotionType.POWERSHARD_OFF, 0, 0));
/*     */       } 
/*     */       
/* 277 */       if (!StigmaService.notifyUnequipAction(this.owner, itemToUnequip)) {
/* 278 */         return null;
/*     */       }
/* 280 */       unEquip(itemToUnequip.getEquipmentSlot());
/* 281 */       return itemToUnequip;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void unEquip(int slot) {
/* 287 */     Item item = this.equipment.remove(Integer.valueOf(slot));
/* 288 */     if (item == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 293 */     item.setEquipped(false);
/*     */     
/* 295 */     ItemEquipmentListener.onItemUnequipment(item, this.owner);
/* 296 */     this.owner.getObserveController().notifyItemUnEquip(item, this.owner);
/*     */     
/* 298 */     this.owner.getLifeStats().updateCurrentStats();
/* 299 */     this.owner.getInventory().putToBag(item);
/* 300 */     PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item));
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
/*     */   private boolean validateEquippedWeapon(Item item, boolean validateOnly) {
/*     */     Item possibleArrows;
/* 315 */     int[] requiredSkills = item.getItemTemplate().getWeaponType().getRequiredSkills();
/*     */     
/* 317 */     if (!checkAvaialbeEquipSkills(requiredSkills)) {
/* 318 */       return false;
/*     */     }
/* 320 */     Item itemInMainHand = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/* 321 */     Item itemInSubHand = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/*     */     
/* 323 */     int requiredSlots = 0;
/* 324 */     switch (item.getItemTemplate().getWeaponType().getRequiredSlots()) {
/*     */       
/*     */       case 2:
/* 327 */         switch (item.getItemTemplate().getWeaponType()) {
/*     */ 
/*     */           
/*     */           case ARROW:
/* 331 */             if (itemInSubHand != null && itemInSubHand.getItemTemplate().getArmorType() != ArmorType.ARROW) {
/*     */ 
/*     */               
/* 334 */               if (validateOnly) {
/*     */                 
/* 336 */                 requiredSlots++;
/* 337 */                 this.markedFreeSlots.add(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/*     */                 break;
/*     */               } 
/* 340 */               unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
/*     */             } 
/*     */             break;
/*     */           
/*     */           default:
/* 345 */             if (itemInSubHand != null) {
/*     */               
/* 347 */               if (validateOnly) {
/*     */                 
/* 349 */                 requiredSlots++;
/* 350 */                 this.markedFreeSlots.add(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/*     */                 break;
/*     */               } 
/* 353 */               unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       case 1:
/* 358 */         if (itemInMainHand != null && !this.owner.getSkillList().isSkillPresent(19) && !this.owner.getSkillList().isSkillPresent(360)) {
/*     */           
/* 360 */           if (validateOnly) {
/*     */             
/* 362 */             requiredSlots++;
/* 363 */             this.markedFreeSlots.add(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/*     */           } else {
/*     */             
/* 366 */             unEquip(ItemSlot.MAIN_HAND.getSlotIdMask());
/*     */           }
/*     */         
/* 369 */         } else if (itemInMainHand != null && itemInMainHand.getItemTemplate().getWeaponType().getRequiredSlots() == 2) {
/*     */           
/* 371 */           if (validateOnly) {
/*     */             
/* 373 */             requiredSlots++;
/* 374 */             this.markedFreeSlots.add(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/*     */           } else {
/*     */             
/* 377 */             unEquip(ItemSlot.MAIN_HAND.getSlotIdMask());
/*     */           } 
/*     */         } 
/*     */         
/* 381 */         possibleArrows = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/* 382 */         if (possibleArrows != null && possibleArrows.getItemTemplate().getArmorType() == ArmorType.ARROW) {
/*     */           
/* 384 */           if (validateOnly) {
/*     */             
/* 386 */             requiredSlots++;
/* 387 */             this.markedFreeSlots.add(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/*     */             break;
/*     */           } 
/* 390 */           unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 396 */     return (this.owner.getInventory().getNumberOfFreeSlots() >= requiredSlots - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkAvaialbeEquipSkills(int[] requiredSkills) {
/* 406 */     boolean isSkillPresent = false;
/*     */ 
/*     */     
/* 409 */     if (requiredSkills.length == 0) {
/* 410 */       return true;
/*     */     }
/* 412 */     for (int skill : requiredSkills) {
/*     */       
/* 414 */       if (this.owner.getSkillList().isSkillPresent(skill)) {
/*     */         
/* 416 */         isSkillPresent = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 420 */     return isSkillPresent;
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
/*     */   private boolean validateEquippedArmor(Item item, boolean validateOnly) {
/* 433 */     ArmorType armorType = item.getItemTemplate().getArmorType();
/* 434 */     if (armorType == null) {
/* 435 */       return true;
/*     */     }
/*     */     
/* 438 */     int[] requiredSkills = armorType.getRequiredSkills();
/* 439 */     if (!checkAvaialbeEquipSkills(requiredSkills)) {
/* 440 */       return false;
/*     */     }
/* 442 */     Item itemInMainHand = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/* 443 */     switch (item.getItemTemplate().getArmorType()) {
/*     */       
/*     */       case ARROW:
/* 446 */         if (itemInMainHand == null || itemInMainHand.getItemTemplate().getWeaponType() != WeaponType.BOW)
/*     */         {
/*     */           
/* 449 */           if (validateOnly)
/* 450 */             return false; 
/*     */         }
/*     */         break;
/*     */       case SHIELD:
/* 454 */         if (itemInMainHand != null && itemInMainHand.getItemTemplate().getWeaponType().getRequiredSlots() == 2) {
/*     */ 
/*     */           
/* 457 */           if (validateOnly) {
/*     */             
/* 459 */             if (this.owner.getInventory().isFull())
/* 460 */               return false; 
/* 461 */             this.markedFreeSlots.add(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 466 */           unEquip(ItemSlot.MAIN_HAND.getSlotIdMask());
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     
/* 471 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEquippedItemByObjId(int value) {
/* 482 */     synchronized (this.equipment) {
/*     */       
/* 484 */       for (Item item : this.equipment.values()) {
/*     */         
/* 486 */         if (item.getObjectId() == value) {
/* 487 */           return item;
/*     */         }
/*     */       } 
/*     */     } 
/* 491 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getEquippedItemsByItemId(int value) {
/* 501 */     List<Item> equippedItemsById = new ArrayList<Item>();
/* 502 */     synchronized (this.equipment) {
/*     */       
/* 504 */       for (Item item : this.equipment.values()) {
/*     */         
/* 506 */         if (item.getItemTemplate().getTemplateId() == value) {
/* 507 */           equippedItemsById.add(item);
/*     */         }
/*     */       } 
/*     */     } 
/* 511 */     return equippedItemsById;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getEquippedItems() {
/* 520 */     List<Item> equippedItems = new ArrayList<Item>();
/* 521 */     equippedItems.addAll(this.equipment.values());
/*     */     
/* 523 */     return equippedItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getEquippedItemsWithoutStigma() {
/* 532 */     List<Item> equippedItems = new ArrayList<Item>();
/* 533 */     for (Item item : this.equipment.values()) {
/*     */       
/* 535 */       if (item.getEquipmentSlot() < ItemSlot.STIGMA1.getSlotIdMask()) {
/* 536 */         equippedItems.add(item);
/*     */       }
/*     */     } 
/* 539 */     return equippedItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getEquippedItemsStigma() {
/* 548 */     List<Item> equippedItems = new ArrayList<Item>();
/* 549 */     for (Item item : this.equipment.values()) {
/*     */       
/* 551 */       if (item.getEquipmentSlot() >= ItemSlot.STIGMA1.getSlotIdMask()) {
/* 552 */         equippedItems.add(item);
/*     */       }
/*     */     } 
/* 555 */     return equippedItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int itemSetPartsEquipped(int itemSetTemplateId) {
/* 563 */     int number = 0;
/*     */     
/* 565 */     for (Item item : this.equipment.values()) {
/*     */       
/* 567 */       ItemSetTemplate setTemplate = item.getItemTemplate().getItemSet();
/* 568 */       if (setTemplate != null && setTemplate.getId() == itemSetTemplateId)
/*     */       {
/* 570 */         number++;
/*     */       }
/*     */     } 
/*     */     
/* 574 */     return number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadHandler(Item item) {
/* 584 */     if (this.equipment.containsKey(Integer.valueOf(item.getEquipmentSlot()))) {
/*     */       
/* 586 */       log.warn("Duplicate equipped item in slot : " + item.getEquipmentSlot() + " " + this.owner.getObjectId());
/*     */       return;
/*     */     } 
/* 589 */     item.setOwnerId(this.owner.getObjectId());
/* 590 */     this.equipment.put(Integer.valueOf(item.getEquipmentSlot()), item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadApplyEquipmentStats() {
/* 599 */     for (Item item : this.equipment.values()) {
/*     */       
/* 601 */       if (this.owner.getGameStats() != null)
/*     */       {
/* 603 */         if (item.getEquipmentSlot() != ItemSlot.MAIN_OFF_HAND.getSlotIdMask() && item.getEquipmentSlot() != ItemSlot.SUB_OFF_HAND.getSlotIdMask())
/*     */         {
/* 605 */           ItemEquipmentListener.onItemEquipment(item, this.owner); } 
/*     */       }
/* 607 */       if (this.owner.getLifeStats() != null)
/*     */       {
/* 609 */         if (item.getEquipmentSlot() != ItemSlot.MAIN_OFF_HAND.getSlotIdMask() && item.getEquipmentSlot() != ItemSlot.SUB_OFF_HAND.getSlotIdMask())
/*     */         {
/* 611 */           this.owner.getLifeStats().synchronizeWithMaxStats();
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShieldEquipped() {
/* 621 */     Item subHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/* 622 */     return (subHandItem != null && subHandItem.getItemTemplate().getArmorType() == ArmorType.SHIELD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeaponType getMainHandWeaponType() {
/* 631 */     Item mainHandItem = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/* 632 */     if (mainHandItem == null) {
/* 633 */       return null;
/*     */     }
/* 635 */     return mainHandItem.getItemTemplate().getWeaponType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeaponType getOffHandWeaponType() {
/* 644 */     Item offHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/* 645 */     if (offHandItem != null && offHandItem.getItemTemplate().isWeapon()) {
/* 646 */       return offHandItem.getItemTemplate().getWeaponType();
/*     */     }
/* 648 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPowerShardEquipped() {
/* 654 */     Item leftPowershard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_LEFT.getSlotIdMask()));
/* 655 */     if (leftPowershard != null) {
/* 656 */       return true;
/*     */     }
/* 658 */     Item rightPowershard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_RIGHT.getSlotIdMask()));
/* 659 */     if (rightPowershard != null) {
/* 660 */       return true;
/*     */     }
/* 662 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getMainHandPowerShard() {
/* 667 */     Item mainHandPowerShard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_RIGHT.getSlotIdMask()));
/* 668 */     if (mainHandPowerShard != null) {
/* 669 */       return mainHandPowerShard;
/*     */     }
/* 671 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getOffHandPowerShard() {
/* 676 */     Item offHandPowerShard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_LEFT.getSlotIdMask()));
/* 677 */     if (offHandPowerShard != null) {
/* 678 */       return offHandPowerShard;
/*     */     }
/* 680 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void usePowerShard(Item powerShardItem, int count) {
/* 689 */     decreaseEquippedItemCount(powerShardItem.getObjectId(), count);
/*     */     
/* 691 */     if (powerShardItem.getItemCount() <= 0L) {
/*     */       
/* 693 */       List<Item> powerShardStacks = this.owner.getInventory().getItemsByItemId(powerShardItem.getItemTemplate().getTemplateId());
/* 694 */       if (powerShardStacks.size() != 0) {
/*     */         
/* 696 */         equipItem(((Item)powerShardStacks.get(0)).getObjectId(), powerShardItem.getEquipmentSlot());
/*     */       }
/*     */       else {
/*     */         
/* 700 */         PacketSendUtility.sendPacket(this.owner, (AionServerPacket)SM_SYSTEM_MESSAGE.NO_POWER_SHARD_LEFT());
/* 701 */         this.owner.unsetState(CreatureState.POWERSHARD);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void useArrow() {
/* 708 */     Item arrow = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/*     */     
/* 710 */     if (arrow == null || (!arrow.getItemTemplate().isArmor() && arrow.getItemTemplate().getArmorType() != ArmorType.ARROW)) {
/*     */       return;
/*     */     }
/* 713 */     decreaseEquippedItemCount(arrow.getObjectId(), 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void decreaseEquippedItemCount(int itemObjId, int count) {
/* 719 */     Item equippedItem = getEquippedItemByObjId(itemObjId);
/*     */ 
/*     */     
/* 722 */     if (equippedItem.getItemTemplate().getArmorType() != ArmorType.SHARD && equippedItem.getItemTemplate().getArmorType() != ArmorType.ARROW) {
/*     */       return;
/*     */     }
/*     */     
/* 726 */     if (equippedItem.getItemCount() >= count) {
/* 727 */       equippedItem.decreaseItemCount(count);
/*     */     } else {
/* 729 */       equippedItem.decreaseItemCount(equippedItem.getItemCount());
/*     */     } 
/* 731 */     if (equippedItem.getItemCount() == 0L) {
/*     */       
/* 733 */       this.equipment.remove(Integer.valueOf(equippedItem.getEquipmentSlot()));
/* 734 */       PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_DELETE_ITEM(equippedItem.getObjectId()));
/*     */     } 
/*     */     
/* 737 */     PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(equippedItem));
/* 738 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void switchHands() {
/* 746 */     Item mainHandItem = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/* 747 */     Item subHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/* 748 */     Item mainOffHandItem = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_OFF_HAND.getSlotIdMask()));
/* 749 */     Item subOffHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_OFF_HAND.getSlotIdMask()));
/*     */     
/* 751 */     List<Item> equippedWeapon = new ArrayList<Item>();
/*     */     
/* 753 */     if (mainHandItem != null)
/* 754 */       equippedWeapon.add(mainHandItem); 
/* 755 */     if (subHandItem != null)
/* 756 */       equippedWeapon.add(subHandItem); 
/* 757 */     if (mainOffHandItem != null)
/* 758 */       equippedWeapon.add(mainOffHandItem); 
/* 759 */     if (subOffHandItem != null) {
/* 760 */       equippedWeapon.add(subOffHandItem);
/*     */     }
/* 762 */     for (Item item : equippedWeapon) {
/*     */       
/* 764 */       this.equipment.remove(Integer.valueOf(item.getEquipmentSlot()));
/* 765 */       item.setEquipped(false);
/* 766 */       PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item, true));
/* 767 */       if (this.owner.getGameStats() != null)
/*     */       {
/* 769 */         if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask() || item.getEquipmentSlot() == ItemSlot.SUB_HAND.getSlotIdMask())
/*     */         {
/* 771 */           ItemEquipmentListener.onItemUnequipment(item, this.owner);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 776 */     for (Item item : equippedWeapon) {
/*     */       
/* 778 */       if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask()) {
/* 779 */         item.setEquipmentSlot(ItemSlot.MAIN_OFF_HAND.getSlotIdMask()); continue;
/*     */       } 
/* 781 */       if (item.getEquipmentSlot() == ItemSlot.SUB_HAND.getSlotIdMask()) {
/* 782 */         item.setEquipmentSlot(ItemSlot.SUB_OFF_HAND.getSlotIdMask()); continue;
/*     */       } 
/* 784 */       if (item.getEquipmentSlot() == ItemSlot.MAIN_OFF_HAND.getSlotIdMask()) {
/* 785 */         item.setEquipmentSlot(ItemSlot.MAIN_HAND.getSlotIdMask()); continue;
/*     */       } 
/* 787 */       if (item.getEquipmentSlot() == ItemSlot.SUB_OFF_HAND.getSlotIdMask()) {
/* 788 */         item.setEquipmentSlot(ItemSlot.SUB_HAND.getSlotIdMask());
/*     */       }
/*     */     } 
/* 791 */     for (Item item : equippedWeapon) {
/*     */       
/* 793 */       this.equipment.put(Integer.valueOf(item.getEquipmentSlot()), item);
/* 794 */       item.setEquipped(true);
/* 795 */       PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item, true));
/*     */     } 
/*     */     
/* 798 */     if (this.owner.getGameStats() != null)
/*     */     {
/* 800 */       for (Item item : equippedWeapon) {
/*     */         
/* 802 */         if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask() || item.getEquipmentSlot() == ItemSlot.SUB_HAND.getSlotIdMask())
/*     */         {
/* 804 */           ItemEquipmentListener.onItemEquipment(item, this.owner);
/*     */         }
/*     */       } 
/*     */     }
/* 808 */     this.owner.getLifeStats().updateCurrentStats();
/* 809 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWeaponEquipped(WeaponType weaponType) {
/* 817 */     if (this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask())) != null && ((Item)this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()))).getItemTemplate().getWeaponType() == weaponType)
/*     */     {
/*     */       
/* 820 */       return true;
/*     */     }
/* 822 */     if (this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask())) != null && ((Item)this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()))).getItemTemplate().getWeaponType() == weaponType)
/*     */     {
/*     */       
/* 825 */       return true;
/*     */     }
/* 827 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmorEquipped(ArmorType armorType) {
/* 835 */     int[] armorSlots = { ItemSlot.BOOTS.getSlotIdMask(), ItemSlot.GLOVES.getSlotIdMask(), ItemSlot.HELMET.getSlotIdMask(), ItemSlot.PANTS.getSlotIdMask(), ItemSlot.SHOULDER.getSlotIdMask(), ItemSlot.TORSO.getSlotIdMask() };
/*     */ 
/*     */ 
/*     */     
/* 839 */     for (int slot : armorSlots) {
/*     */       
/* 841 */       if (this.equipment.get(Integer.valueOf(slot)) != null && ((Item)this.equipment.get(Integer.valueOf(slot))).getItemTemplate().getArmorType() != armorType)
/* 842 */         return false; 
/*     */     } 
/* 844 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getMainHandWeapon() {
/* 849 */     return this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getOffHandWeapon() {
/* 854 */     return this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 862 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 870 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(Player player) {
/* 878 */     this.owner = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean soulBindItem(final Player player, final Item item, final int slot) {
/* 889 */     RequestResponseHandler responseHandler = new RequestResponseHandler(player)
/*     */       {
/*     */         
/*     */         public void acceptRequest(Creature requester, Player responder)
/*     */         {
/* 894 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), item.getItemId(), 5000, 4), true);
/*     */ 
/*     */           
/* 897 */           final WorldPosition position = player.getCommonData().getPosition().clone();
/*     */ 
/*     */           
/* 900 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 904 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), item.getItemId(), 0, 6), true);
/*     */                   
/* 906 */                   if (!position.equals(player.getCommonData().getPosition())) {
/*     */                     return;
/*     */                   }
/*     */                   
/* 910 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SOUL_BOUND_ITEM_SUCCEED(new DescriptionId(item.getNameID())));
/*     */                   
/* 912 */                   item.setSoulBound(true);
/* 913 */                   Equipment.this.equip(slot, item);
/* 914 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), Equipment.this.getEquippedItemsWithoutStigma()), true);
/*     */                 }
/*     */               }5100L);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void denyRequest(Creature requester, Player responder) {
/* 923 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SOUL_BOUND_ITEM_CANCELED(new DescriptionId(item.getNameID())));
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 928 */     boolean requested = player.getResponseRequester().putRequest(95006, responseHandler);
/*     */     
/* 930 */     if (requested)
/*     */     {
/* 932 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(95006, 0, new Object[] { new DescriptionId(item.getNameID()) }));
/*     */     }
/*     */ 
/*     */     
/* 936 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Equipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
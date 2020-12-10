/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.configs.main.EnchantsConfig;
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.EnchantStatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.listeners.ItemEquipmentListener;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.AddModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.RateModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.items.ManaStone;
/*     */ import com.aionemu.gameserver.model.templates.item.ArmorType;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemQuality;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class EnchantService
/*     */ {
/*  52 */   private static final Logger log = Logger.getLogger(EnchantService.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void breakItem(Player player, Item targetItem, Item parentItem) {
/*  62 */     ItemTemplate itemTemplate = targetItem.getItemTemplate();
/*  63 */     ItemQuality quality = itemTemplate.getItemQuality();
/*     */     
/*  65 */     int number = 0;
/*  66 */     int level = 0;
/*  67 */     switch (quality) {
/*     */       
/*     */       case BOOK_2H:
/*     */       case DAGGER_1H:
/*  71 */         number = Rnd.get(1, 2);
/*  72 */         level = Rnd.get(0, 5);
/*     */         break;
/*     */       case BOW:
/*  75 */         number = Rnd.get(1, 3);
/*  76 */         level = Rnd.get(0, 10);
/*     */         break;
/*     */       case ORB_2H:
/*     */       case STAFF_2H:
/*  80 */         number = Rnd.get(1, 3);
/*  81 */         level = Rnd.get(0, 15);
/*     */         break;
/*     */       case SWORD_1H:
/*     */       case SWORD_2H:
/*  85 */         number = Rnd.get(1, 3);
/*  86 */         level = Rnd.get(0, 20);
/*     */         break;
/*     */     } 
/*     */     
/*  90 */     int enchantItemLevel = targetItem.getItemTemplate().getLevel() + level;
/*  91 */     int enchantItemId = 166000000 + enchantItemLevel;
/*     */     
/*  93 */     ItemService.removeItemFromInventory(player, targetItem);
/*  94 */     ItemService.decreaseItemCount(player, parentItem, 1L);
/*     */     
/*  96 */     ItemService.addItem(player, enchantItemId, number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enchantItem(Player player, Item parentItem, Item targetItem, Item supplementItem) {
/* 106 */     int enchantStoneLevel = parentItem.getItemTemplate().getLevel();
/* 107 */     int targetItemLevel = targetItem.getItemTemplate().getLevel();
/*     */     
/* 109 */     if (targetItemLevel > enchantStoneLevel) {
/* 110 */       return false;
/*     */     }
/* 112 */     int qualityCap = 0;
/*     */     
/* 114 */     ItemQuality quality = targetItem.getItemTemplate().getItemQuality();
/*     */     
/* 116 */     switch (quality) {
/*     */       
/*     */       case BOOK_2H:
/*     */       case DAGGER_1H:
/* 120 */         qualityCap = 0;
/*     */         break;
/*     */       case BOW:
/* 123 */         qualityCap = 5;
/*     */         break;
/*     */       case ORB_2H:
/*     */       case STAFF_2H:
/* 127 */         qualityCap = 10;
/*     */         break;
/*     */       case SWORD_1H:
/*     */       case SWORD_2H:
/* 131 */         qualityCap = 15;
/*     */         break;
/*     */     } 
/*     */     
/* 135 */     int success = 50;
/*     */     
/* 137 */     int levelDiff = enchantStoneLevel - targetItemLevel;
/*     */     
/* 139 */     int extraSuccess = levelDiff - qualityCap;
/* 140 */     if (extraSuccess > 0)
/*     */     {
/* 142 */       success += extraSuccess * 5;
/*     */     }
/*     */     
/* 145 */     if (supplementItem != null) {
/*     */       
/* 147 */       int supplementUseCount = 1;
/* 148 */       int addsuccessRate = 10;
/* 149 */       int supplementId = supplementItem.getItemTemplate().getTemplateId();
/* 150 */       int enchantstoneLevel = parentItem.getItemTemplate().getLevel();
/* 151 */       int enchantitemLevel = targetItem.getEnchantLevel() + 1;
/*     */       
/* 153 */       switch (supplementId) {
/*     */ 
/*     */         
/*     */         case 166100000:
/*     */         case 166100003:
/*     */         case 166100006:
/* 159 */           addsuccessRate = EnchantsConfig.LSUP;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 166100001:
/*     */         case 166100004:
/*     */         case 166100007:
/* 166 */           addsuccessRate = EnchantsConfig.RSUP;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 166100002:
/*     */         case 166100005:
/*     */         case 166100008:
/* 173 */           addsuccessRate = EnchantsConfig.GSUP;
/*     */           break;
/*     */       } 
/*     */       
/* 177 */       if (enchantstoneLevel > 30 && enchantstoneLevel < 41) {
/* 178 */         supplementUseCount = 5;
/*     */       }
/* 180 */       if (enchantstoneLevel > 40 && enchantstoneLevel < 51) {
/* 181 */         supplementUseCount = 10;
/*     */       }
/* 183 */       if (enchantstoneLevel > 50 && enchantstoneLevel < 61) {
/* 184 */         supplementUseCount = 25;
/*     */       }
/* 186 */       if (enchantstoneLevel > 60 && enchantstoneLevel < 71) {
/* 187 */         supplementUseCount = 55;
/*     */       }
/* 189 */       if (enchantstoneLevel > 70 && enchantstoneLevel < 81) {
/* 190 */         supplementUseCount = 85;
/*     */       }
/* 192 */       if (enchantstoneLevel > 80 && enchantstoneLevel < 91) {
/* 193 */         supplementUseCount = 115;
/*     */       }
/* 195 */       if (enchantstoneLevel > 90) {
/* 196 */         supplementUseCount = 145;
/*     */       }
/*     */       
/* 199 */       if (enchantitemLevel > 10) {
/* 200 */         supplementUseCount *= 2;
/*     */       }
/* 202 */       ItemService.decreaseItemCount(player, supplementItem, supplementUseCount);
/*     */ 
/*     */       
/* 205 */       success += addsuccessRate;
/*     */     } 
/*     */     
/* 208 */     if (success >= 95) {
/* 209 */       success = 95;
/*     */     }
/* 211 */     boolean result = false;
/*     */     
/* 213 */     if (Rnd.get(0, 100) < success) {
/* 214 */       result = true;
/*     */     }
/* 216 */     int currentEnchant = targetItem.getEnchantLevel();
/*     */     
/* 218 */     if (!result) {
/*     */ 
/*     */ 
/*     */       
/* 222 */       if (currentEnchant > 10) {
/* 223 */         currentEnchant = 10;
/* 224 */       } else if (currentEnchant > 0) {
/* 225 */         currentEnchant--;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 230 */       ItemQuality targetQuality = targetItem.getItemTemplate().getItemQuality();
/* 231 */       if (targetQuality == ItemQuality.UNIQUE || targetQuality == ItemQuality.EPIC) {
/*     */         
/* 233 */         if (currentEnchant < 15) {
/* 234 */           currentEnchant++;
/*     */         
/*     */         }
/*     */       }
/* 238 */       else if (currentEnchant < 10) {
/* 239 */         currentEnchant++;
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     if (targetItem.isEquipped()) {
/* 244 */       onItemUnequip(player, targetItem);
/*     */     }
/* 246 */     targetItem.setEnchantLevel(currentEnchant);
/*     */     
/* 248 */     if (targetItem.isEquipped()) {
/* 249 */       onItemEquip(player, targetItem);
/*     */     }
/* 251 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(targetItem));
/*     */     
/* 253 */     if (targetItem.isEquipped()) {
/* 254 */       player.getEquipment().setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */     } else {
/* 256 */       player.getInventory().setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */     } 
/* 258 */     if (result) {
/*     */       
/* 260 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_SUCCEED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 265 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_FAILED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
/*     */     } 
/*     */     
/* 268 */     ItemService.decreaseItemCount(player, parentItem, 1L);
/*     */     
/* 270 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean socketManastone(Player player, Item parentItem, Item targetItem, Item supplementItem) {
/* 280 */     boolean result = false;
/* 281 */     int successRate = 76;
/*     */     
/* 283 */     int stoneCount = targetItem.getItemStones().size();
/* 284 */     switch (stoneCount) {
/*     */       
/*     */       case 1:
/* 287 */         successRate = EnchantsConfig.MSPERCENT;
/*     */         break;
/*     */       case 2:
/* 290 */         successRate = EnchantsConfig.MSPERCENT1;
/*     */         break;
/*     */       case 3:
/* 293 */         successRate = EnchantsConfig.MSPERCENT2;
/*     */         break;
/*     */       case 4:
/* 296 */         successRate = EnchantsConfig.MSPERCENT3;
/*     */         break;
/*     */       case 5:
/* 299 */         successRate = EnchantsConfig.MSPERCENT4;
/*     */         break;
/*     */     } 
/*     */     
/* 303 */     if (stoneCount >= 6) {
/* 304 */       successRate = EnchantsConfig.MSPERCENT5;
/*     */     }
/* 306 */     if (supplementItem != null) {
/*     */       
/* 308 */       int supplementUseCount = 1;
/* 309 */       int addsuccessRate = 10;
/* 310 */       int supplementId = supplementItem.getItemTemplate().getTemplateId();
/* 311 */       int manastoneId = parentItem.getItemTemplate().getTemplateId();
/* 312 */       int manastoneLevel = parentItem.getItemTemplate().getLevel();
/* 313 */       int manastoneCount = targetItem.getItemStones().size() + 1;
/*     */ 
/*     */       
/* 316 */       if (supplementId == 166100000 || supplementId == 166100003 || supplementId == 166100006) {
/* 317 */         addsuccessRate = 10;
/*     */       }
/* 319 */       if (supplementId == 166100001 || supplementId == 166100004 || supplementId == 166100007) {
/* 320 */         addsuccessRate = 15;
/*     */       }
/* 322 */       if (supplementId == 166100002 || supplementId == 166100005 || supplementId == 166100008) {
/* 323 */         addsuccessRate = 20;
/*     */       }
/*     */       
/* 326 */       if (manastoneLevel > 30) {
/* 327 */         supplementUseCount++;
/*     */       }
/* 329 */       if (manastoneLevel > 40) {
/* 330 */         supplementUseCount++;
/*     */       }
/* 332 */       if (manastoneLevel > 50) {
/* 333 */         supplementUseCount++;
/*     */       }
/*     */       
/* 336 */       switch (manastoneId) {
/*     */         
/*     */         case 167000230:
/*     */         case 167000235:
/*     */         case 167000267:
/*     */         case 167000294:
/*     */         case 167000299:
/* 343 */           supplementUseCount = 5;
/*     */           break;
/*     */         case 167000331:
/* 346 */           supplementUseCount = 10;
/*     */           break;
/*     */         case 167000358:
/*     */         case 167000363:
/* 350 */           supplementUseCount = 15;
/*     */           break;
/*     */         case 167000550:
/* 353 */           supplementUseCount = 20;
/*     */           break;
/*     */         case 167000427:
/*     */         case 167000454:
/*     */         case 167000459:
/* 358 */           supplementUseCount = 25;
/*     */           break;
/*     */         case 167000491:
/* 361 */           supplementUseCount = 50;
/*     */           break;
/*     */         case 167000518:
/*     */         case 167000522:
/* 365 */           supplementUseCount = 75;
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 370 */       if (stoneCount > 0) {
/* 371 */         supplementUseCount *= manastoneCount;
/*     */       }
/* 373 */       if (ItemService.decreaseItemCount(player, supplementItem, supplementUseCount) != 0L) {
/* 374 */         return false;
/*     */       }
/*     */       
/* 377 */       successRate += addsuccessRate;
/*     */     } 
/*     */     
/* 380 */     if (Rnd.get(0, 100) < successRate)
/* 381 */       result = true; 
/* 382 */     if (result) {
/*     */       
/* 384 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_SUCCEED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
/*     */       
/* 386 */       ManaStone manaStone = ItemService.addManaStone(targetItem, parentItem.getItemTemplate().getTemplateId());
/* 387 */       if (targetItem.isEquipped())
/*     */       {
/* 389 */         ItemEquipmentListener.addStoneStats(manaStone, (CreatureGameStats)player.getGameStats());
/* 390 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_STATS_INFO(player));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 395 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_FAILED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
/*     */       
/* 397 */       Set<ManaStone> manaStones = targetItem.getItemStones();
/* 398 */       if (targetItem.isEquipped()) {
/*     */         
/* 400 */         ItemEquipmentListener.removeStoneStats(manaStones, (CreatureGameStats)player.getGameStats());
/* 401 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_STATS_INFO(player));
/*     */       } 
/* 403 */       ItemService.removeAllManastone(player, targetItem);
/*     */     } 
/*     */     
/* 406 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(targetItem));
/* 407 */     ItemService.decreaseItemCount(player, parentItem, 1L);
/* 408 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onItemEquip(Player player, Item item) {
/*     */     try {
/* 420 */       int enchantLevel = item.getEnchantLevel();
/*     */       
/* 422 */       if (enchantLevel == 0) {
/*     */         return;
/*     */       }
/* 425 */       boolean isWeapon = item.getItemTemplate().isWeapon();
/* 426 */       boolean isArmor = item.getItemTemplate().isArmor();
/* 427 */       if (isWeapon) {
/*     */         
/* 429 */         TreeSet<StatModifier> modifiers = getWeaponModifiers(item);
/*     */         
/* 431 */         if (modifiers == null) {
/*     */           return;
/*     */         }
/* 434 */         EnchantStatEffectId statId = EnchantStatEffectId.getInstance(item.getObjectId(), item.getEquipmentSlot());
/*     */ 
/*     */         
/* 437 */         player.getGameStats().addModifiers((StatEffectId)statId, modifiers);
/*     */         
/*     */         return;
/*     */       } 
/* 441 */       if (isArmor)
/*     */       {
/* 443 */         TreeSet<StatModifier> modifiers = getArmorModifiers(item);
/*     */         
/* 445 */         if (modifiers == null) {
/*     */           return;
/*     */         }
/* 448 */         EnchantStatEffectId statId = EnchantStatEffectId.getInstance(item.getObjectId(), item.getEquipmentSlot());
/*     */         
/* 450 */         player.getGameStats().addModifiers((StatEffectId)statId, modifiers);
/*     */       }
/*     */     
/* 453 */     } catch (Exception ex) {
/*     */       
/* 455 */       log.error((ex.getCause() != null) ? ex.getCause().getMessage() : "Error on item equip.");
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
/*     */   public static void onItemUnequip(Player player, Item item) {
/*     */     try {
/* 468 */       int enchantLevel = item.getEnchantLevel();
/*     */       
/* 470 */       if (enchantLevel == 0) {
/*     */         return;
/*     */       }
/* 473 */       EnchantStatEffectId statId = EnchantStatEffectId.getInstance(item.getObjectId(), item.getEquipmentSlot());
/*     */       
/* 475 */       if (player.getGameStats().effectAlreadyAdded((StatEffectId)statId)) {
/* 476 */         player.getGameStats().endEffect((StatEffectId)statId);
/*     */       }
/*     */     }
/* 479 */     catch (Exception ex) {
/*     */       
/* 481 */       log.error((ex.getCause() != null) ? ex.getCause().getMessage() : null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TreeSet<StatModifier> getArmorModifiers(Item item) {
/* 492 */     TreeSet<StatModifier> modifiers = null;
/*     */     
/* 494 */     ArmorType armorType = item.getItemTemplate().getArmorType();
/* 495 */     if (armorType == null) {
/* 496 */       return null;
/*     */     }
/* 498 */     int enchantLevel = item.getEnchantLevel();
/*     */     
/* 500 */     switch (armorType) {
/*     */       
/*     */       case BOOK_2H:
/* 503 */         switch (item.getEquipmentSlot()) {
/*     */           
/*     */           case 8:
/* 506 */             modifiers = EnchantWeapon.DEF3.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 32:
/* 509 */             modifiers = EnchantWeapon.DEF1.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 2048:
/* 512 */             modifiers = EnchantWeapon.DEF1.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 4096:
/* 515 */             modifiers = EnchantWeapon.DEF2.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 16:
/* 518 */             modifiers = EnchantWeapon.DEF1.getModifiers(enchantLevel);
/*     */             break;
/*     */         } 
/*     */         break;
/*     */       case DAGGER_1H:
/* 523 */         switch (item.getEquipmentSlot()) {
/*     */           
/*     */           case 8:
/* 526 */             modifiers = EnchantWeapon.DEF6.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 32:
/* 529 */             modifiers = EnchantWeapon.DEF4.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 2048:
/* 532 */             modifiers = EnchantWeapon.DEF4.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 4096:
/* 535 */             modifiers = EnchantWeapon.DEF5.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 16:
/* 538 */             modifiers = EnchantWeapon.DEF4.getModifiers(enchantLevel);
/*     */             break;
/*     */         } 
/*     */         break;
/*     */       case BOW:
/* 543 */         switch (item.getEquipmentSlot()) {
/*     */           
/*     */           case 8:
/* 546 */             modifiers = EnchantWeapon.DEF9.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 32:
/* 549 */             modifiers = EnchantWeapon.DEF7.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 2048:
/* 552 */             modifiers = EnchantWeapon.DEF7.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 4096:
/* 555 */             modifiers = EnchantWeapon.DEF8.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 16:
/* 558 */             modifiers = EnchantWeapon.DEF7.getModifiers(enchantLevel);
/*     */             break;
/*     */         } 
/*     */         break;
/*     */       case ORB_2H:
/* 563 */         switch (item.getEquipmentSlot()) {
/*     */           
/*     */           case 8:
/* 566 */             modifiers = EnchantWeapon.DEF12.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 32:
/* 569 */             modifiers = EnchantWeapon.DEF10.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 2048:
/* 572 */             modifiers = EnchantWeapon.DEF10.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 4096:
/* 575 */             modifiers = EnchantWeapon.DEF11.getModifiers(enchantLevel);
/*     */             break;
/*     */           case 16:
/* 578 */             modifiers = EnchantWeapon.DEF10.getModifiers(enchantLevel);
/*     */             break;
/*     */         } 
/*     */         break;
/*     */     } 
/* 583 */     return modifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TreeSet<StatModifier> getWeaponModifiers(Item item) {
/* 593 */     WeaponType weaponType = item.getItemTemplate().getWeaponType();
/*     */     
/* 595 */     if (weaponType == null) {
/* 596 */       return null;
/*     */     }
/* 598 */     int enchantLevel = item.getEnchantLevel();
/*     */     
/* 600 */     TreeSet<StatModifier> modifiers = null;
/* 601 */     switch (weaponType) {
/*     */       
/*     */       case BOOK_2H:
/* 604 */         modifiers = EnchantWeapon.SPELLBOOK.getModifiers(enchantLevel);
/*     */         break;
/*     */       case DAGGER_1H:
/* 607 */         modifiers = EnchantWeapon.DAGGER.getModifiers(enchantLevel);
/*     */         break;
/*     */       case BOW:
/* 610 */         modifiers = EnchantWeapon.BOW.getModifiers(enchantLevel);
/*     */         break;
/*     */       case ORB_2H:
/* 613 */         modifiers = EnchantWeapon.ORB.getModifiers(enchantLevel);
/*     */         break;
/*     */       case STAFF_2H:
/* 616 */         modifiers = EnchantWeapon.STAFF.getModifiers(enchantLevel);
/*     */         break;
/*     */       case SWORD_1H:
/* 619 */         modifiers = EnchantWeapon.SWORD.getModifiers(enchantLevel);
/*     */         break;
/*     */       case SWORD_2H:
/* 622 */         modifiers = EnchantWeapon.GREATSWORD.getModifiers(enchantLevel);
/*     */         break;
/*     */       case MACE_1H:
/* 625 */         modifiers = EnchantWeapon.MACE.getModifiers(enchantLevel);
/*     */         break;
/*     */       case POLEARM_2H:
/* 628 */         modifiers = EnchantWeapon.POLEARM.getModifiers(enchantLevel);
/*     */         break;
/*     */     } 
/* 631 */     return modifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum EnchantWeapon
/*     */   {
/* 639 */     DAGGER
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 644 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 645 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 2 * level, true));
/* 646 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 650 */     SWORD
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 655 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 656 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 2 * level, true));
/* 657 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 661 */     GREATSWORD
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 666 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 667 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 4 * level, true));
/* 668 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 672 */     POLEARM
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 677 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 678 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 4 * level, true));
/* 679 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 683 */     BOW
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 688 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 689 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 4 * level, true));
/* 690 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 694 */     MACE
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 699 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 700 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
/* 701 */         mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
/* 702 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 706 */     STAFF
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 711 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 712 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
/* 713 */         mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
/* 714 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 718 */     SPELLBOOK
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 723 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 724 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
/* 725 */         mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
/* 726 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 730 */     ORB
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 735 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 736 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
/* 737 */         mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
/* 738 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 742 */     SHIELD
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 747 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 748 */         mod.add(RateModifier.newInstance(StatEnum.BLOCK, 2 * level, true));
/* 749 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 753 */     DEF1
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 758 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 759 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 1 * level, true));
/* 760 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 10 * level, true));
/* 761 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
/* 762 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 766 */     DEF2
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 771 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 772 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 2 * level, true));
/* 773 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 12 * level, true));
/* 774 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
/* 775 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 779 */     DEF3
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 784 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 785 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 3 * level, true));
/* 786 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 14 * level, true));
/* 787 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
/* 788 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 792 */     DEF4
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 797 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 798 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 3 * level, true));
/* 799 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 8 * level, true));
/* 800 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
/* 801 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 805 */     DEF5
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 810 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 811 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 5 * level, true));
/* 812 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 10 * level, true));
/* 813 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
/* 814 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 818 */     DEF6
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 823 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 824 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 4 * level, true));
/* 825 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 12 * level, true));
/* 826 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
/* 827 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 831 */     DEF7
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 836 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 837 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 3 * level, true));
/* 838 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 6 * level, true));
/* 839 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
/* 840 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 844 */     DEF8
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 849 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 850 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 4 * level, true));
/* 851 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 8 * level, true));
/* 852 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
/* 853 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 857 */     DEF9
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 862 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 863 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 5 * level, true));
/* 864 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 10 * level, true));
/* 865 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
/* 866 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 870 */     DEF10
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 875 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 876 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 4 * level, true));
/* 877 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 4 * level, true));
/* 878 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
/* 879 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 883 */     DEF11
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 888 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 889 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 5 * level, true));
/* 890 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 6 * level, true));
/* 891 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
/* 892 */         return mod;
/*     */       }
/*     */     },
/*     */     
/* 896 */     DEF12
/*     */     {
/*     */       
/*     */       public TreeSet<StatModifier> getModifiers(int level)
/*     */       {
/* 901 */         TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
/* 902 */         mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 6 * level, true));
/* 903 */         mod.add(AddModifier.newInstance(StatEnum.MAXHP, 8 * level, true));
/* 904 */         mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
/* 905 */         return mod;
/*     */       }
/*     */     };
/*     */     
/*     */     public abstract TreeSet<StatModifier> getModifiers(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\EnchantService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
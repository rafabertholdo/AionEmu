/*     */ package com.aionemu.gameserver.model.gameobjects.stats.listeners;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.ItemSetStatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.ItemStatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.StoneStatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.items.ManaStone;
/*     */ import com.aionemu.gameserver.model.templates.item.ArmorType;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*     */ import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
/*     */ import com.aionemu.gameserver.model.templates.itemset.PartBonus;
/*     */ import com.aionemu.gameserver.services.EnchantService;
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
/*     */ 
/*     */ 
/*     */ public class ItemEquipmentListener
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(ItemEquipmentListener.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onItemEquipment(ItemTemplate itemTemplate, int slot, CreatureGameStats<?> cgs) {
/*  55 */     TreeSet<StatModifier> modifiers = itemTemplate.getModifiers();
/*  56 */     if (modifiers == null)
/*     */     {
/*  58 */       if (cgs instanceof com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats)
/*     */       {
/*  60 */         log.debug("No effect was found for item " + itemTemplate.getTemplateId());
/*     */       }
/*     */     }
/*     */     
/*  64 */     cgs.addModifiers((StatEffectId)ItemStatEffectId.getInstance(itemTemplate.getTemplateId(), slot), modifiers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onItemEquipment(Item item, Player owner) {
/*  73 */     ItemTemplate itemTemplate = item.getItemTemplate();
/*  74 */     onItemEquipment(itemTemplate, item.getEquipmentSlot(), (CreatureGameStats<?>)owner.getGameStats());
/*     */ 
/*     */     
/*  77 */     if (itemTemplate.isItemSet()) {
/*  78 */       onItemSetPartEquipment(itemTemplate.getItemSet(), owner);
/*     */     }
/*  80 */     if (item.hasManaStones()) {
/*  81 */       addStonesStats(item.getItemStones(), (CreatureGameStats<?>)owner.getGameStats());
/*     */     }
/*  83 */     addGodstoneEffect(owner, item);
/*     */     
/*  85 */     if (item.getItemTemplate().isWeapon()) {
/*  86 */       recalculateWeaponMastery(owner);
/*     */     }
/*  88 */     if (item.getItemTemplate().isArmor()) {
/*  89 */       recalculateArmorMastery(owner);
/*     */     }
/*  91 */     EnchantService.onItemEquip(owner, item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onItemSetPartEquipment(ItemSetTemplate itemSetTemplate, Player player) {
/* 101 */     if (itemSetTemplate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     int itemSetPartsEquipped = player.getEquipment().itemSetPartsEquipped(itemSetTemplate.getId());
/*     */ 
/*     */     
/* 108 */     for (PartBonus itempartbonus : itemSetTemplate.getPartbonus()) {
/*     */       
/* 110 */       ItemSetStatEffectId setEffectId = ItemSetStatEffectId.getInstance(itemSetTemplate.getId(), itempartbonus.getCount());
/*     */ 
/*     */       
/* 113 */       if (itempartbonus.getCount() <= itemSetPartsEquipped && !player.getGameStats().effectAlreadyAdded((StatEffectId)setEffectId))
/*     */       {
/*     */         
/* 116 */         player.getGameStats().addModifiers((StatEffectId)setEffectId, itempartbonus.getModifiers());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 121 */     if (itemSetTemplate.getFullbonus() != null && itemSetPartsEquipped == itemSetTemplate.getFullbonus().getCount()) {
/*     */       
/* 123 */       ItemSetStatEffectId setEffectId = ItemSetStatEffectId.getInstance(itemSetTemplate.getId(), itemSetPartsEquipped + 1);
/*     */       
/* 125 */       if (!player.getGameStats().effectAlreadyAdded((StatEffectId)setEffectId))
/*     */       {
/*     */ 
/*     */         
/* 129 */         player.getGameStats().addModifiers((StatEffectId)setEffectId, itemSetTemplate.getFullbonus().getModifiers());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void recalculateWeaponMastery(Player owner) {
/* 140 */     if (owner.getEquipment() == null) {
/*     */       return;
/*     */     }
/* 143 */     WeaponType weaponType = owner.getEquipment().getMainHandWeaponType();
/* 144 */     int currentWeaponMasterySkill = owner.getEffectController().getWeaponMastery();
/* 145 */     if (weaponType == null && currentWeaponMasterySkill != 0) {
/*     */       
/* 147 */       owner.getEffectController().removePassiveEffect(currentWeaponMasterySkill);
/*     */       
/*     */       return;
/*     */     } 
/* 151 */     boolean weaponEquiped = owner.getEquipment().isWeaponEquipped(weaponType);
/* 152 */     Integer skillId = owner.getSkillList().getWeaponMasterySkill(weaponType);
/* 153 */     if (skillId == null)
/*     */       return; 
/* 155 */     boolean masterySet = owner.getEffectController().isWeaponMasterySet(skillId.intValue());
/*     */ 
/*     */     
/* 158 */     if (masterySet && !weaponEquiped)
/*     */     {
/* 160 */       owner.getEffectController().removePassiveEffect(skillId.intValue());
/*     */     }
/*     */     
/* 163 */     if (!masterySet && weaponEquiped)
/*     */     {
/* 165 */       owner.getController().useSkill(skillId.intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void recalculateArmorMastery(Player owner) {
/* 175 */     if (owner.getEquipment() == null) {
/*     */       return;
/*     */     }
/* 178 */     for (ArmorType armorType : ArmorType.values()) {
/*     */       
/* 180 */       boolean armorEquiped = owner.getEquipment().isArmorEquipped(armorType);
/* 181 */       Integer skillId = owner.getSkillList().getArmorMasterySkill(armorType);
/* 182 */       if (skillId != null) {
/*     */         
/* 184 */         boolean masterySet = owner.getEffectController().isArmorMasterySet(skillId.intValue());
/*     */ 
/*     */         
/* 187 */         if (masterySet && !armorEquiped)
/*     */         {
/* 189 */           owner.getEffectController().removePassiveEffect(skillId.intValue());
/*     */         }
/*     */         
/* 192 */         if (!masterySet && armorEquiped)
/*     */         {
/* 194 */           owner.getController().useSkill(skillId.intValue());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addStonesStats(Set<ManaStone> itemStones, CreatureGameStats<?> cgs) {
/* 207 */     if (itemStones == null || itemStones.size() == 0) {
/*     */       return;
/*     */     }
/* 210 */     for (ManaStone stone : itemStones)
/*     */     {
/* 212 */       addStoneStats(stone, cgs);
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
/*     */   public static void removeStoneStats(Set<ManaStone> itemStones, CreatureGameStats<?> cgs) {
/* 224 */     if (itemStones == null || itemStones.size() == 0) {
/*     */       return;
/*     */     }
/* 227 */     for (ManaStone stone : itemStones) {
/*     */       
/* 229 */       TreeSet<StatModifier> modifiers = stone.getModifiers();
/* 230 */       if (modifiers != null)
/*     */       {
/* 232 */         cgs.endEffect((StatEffectId)StoneStatEffectId.getInstance(stone.getItemObjId(), stone.getSlot()));
/*     */       }
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
/*     */   public static void addStoneStats(ManaStone stone, CreatureGameStats<?> cgs) {
/* 245 */     TreeSet<StatModifier> modifiers = stone.getModifiers();
/* 246 */     if (modifiers != null)
/*     */     {
/* 248 */       cgs.addModifiers((StatEffectId)StoneStatEffectId.getInstance(stone.getItemObjId(), stone.getSlot()), modifiers);
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
/*     */   public static void onItemUnequipment(Item item, Player owner) {
/* 260 */     if (item.getItemTemplate().isItemSet()) {
/* 261 */       onItemSetPartUnequipment(item.getItemTemplate().getItemSet(), owner);
/*     */     }
/* 263 */     owner.getGameStats().endEffect((StatEffectId)ItemStatEffectId.getInstance(item.getItemTemplate().getTemplateId(), item.getEquipmentSlot()));
/*     */ 
/*     */     
/* 266 */     if (item.hasManaStones()) {
/* 267 */       removeStoneStats(item.getItemStones(), (CreatureGameStats<?>)owner.getGameStats());
/*     */     }
/* 269 */     removeGodstoneEffect(owner, item);
/*     */     
/* 271 */     if (item.getItemTemplate().isWeapon()) {
/* 272 */       recalculateWeaponMastery(owner);
/*     */     }
/* 274 */     if (item.getItemTemplate().isArmor()) {
/* 275 */       recalculateArmorMastery(owner);
/*     */     }
/* 277 */     EnchantService.onItemUnequip(owner, item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onItemSetPartUnequipment(ItemSetTemplate itemSetTemplate, Player player) {
/* 288 */     if (itemSetTemplate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 292 */     int previousItemSetPartsEquipped = player.getEquipment().itemSetPartsEquipped(itemSetTemplate.getId()) + 1;
/*     */ 
/*     */     
/* 295 */     if (itemSetTemplate.getFullbonus() != null && previousItemSetPartsEquipped == itemSetTemplate.getFullbonus().getCount())
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 300 */       player.getGameStats().endEffect((StatEffectId)ItemSetStatEffectId.getInstance(itemSetTemplate.getId(), itemSetTemplate.getFullbonus().getCount() + 1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     for (PartBonus itempartbonus : itemSetTemplate.getPartbonus()) {
/*     */ 
/*     */       
/* 310 */       if (itempartbonus.getCount() == previousItemSetPartsEquipped)
/*     */       {
/* 312 */         player.getGameStats().endEffect((StatEffectId)ItemSetStatEffectId.getInstance(itemSetTemplate.getId(), itempartbonus.getCount()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addGodstoneEffect(Player player, Item item) {
/* 324 */     if (item.getGodStone() != null)
/*     */     {
/* 326 */       item.getGodStone().onEquip(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeGodstoneEffect(Player player, Item item) {
/* 335 */     if (item.getGodStone() != null)
/*     */     {
/* 337 */       item.getGodStone().onUnEquip(player);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\listeners\ItemEquipmentListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
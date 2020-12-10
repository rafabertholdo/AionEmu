/*     */ package com.aionemu.gameserver.model.broker;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.broker.filter.BrokerContainsExtraFilter;
/*     */ import com.aionemu.gameserver.model.broker.filter.BrokerContainsFilter;
/*     */ import com.aionemu.gameserver.model.broker.filter.BrokerFilter;
/*     */ import com.aionemu.gameserver.model.broker.filter.BrokerMinMaxFilter;
/*     */ import com.aionemu.gameserver.model.broker.filter.BrokerPlayerClassExtraFilter;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
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
/*     */ public enum BrokerItemMask
/*     */ {
/*  37 */   WEAPON(9010, (BrokerFilter)new BrokerMinMaxFilter(1000, 1018), null, true),
/*  38 */   WEAPON_SWORD(1000, (BrokerFilter)new BrokerContainsFilter(new int[] { 1000 }, ), WEAPON, false),
/*  39 */   WEAPON_MACE(1001, (BrokerFilter)new BrokerContainsFilter(new int[] { 1001 }, ), WEAPON, false),
/*  40 */   WEAPON_DAGGER(1002, (BrokerFilter)new BrokerContainsFilter(new int[] { 1002 }, ), WEAPON, false),
/*  41 */   WEAPON_ORB(1005, (BrokerFilter)new BrokerContainsFilter(new int[] { 1005 }, ), WEAPON, false),
/*  42 */   WEAPON_SPELLBOOK(1006, (BrokerFilter)new BrokerContainsFilter(new int[] { 1006 }, ), WEAPON, false),
/*  43 */   WEAPON_GREATSWORD(1009, (BrokerFilter)new BrokerContainsFilter(new int[] { 1009 }, ), WEAPON, false),
/*  44 */   WEAPON_POLEARM(1013, (BrokerFilter)new BrokerContainsFilter(new int[] { 1013 }, ), WEAPON, false),
/*  45 */   WEAPON_STAFF(1015, (BrokerFilter)new BrokerContainsFilter(new int[] { 1015 }, ), WEAPON, false),
/*  46 */   WEAPON_BOW(1017, (BrokerFilter)new BrokerContainsFilter(new int[] { 1017 }, ), WEAPON, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   ARMOR(9020, (BrokerFilter)new BrokerMinMaxFilter(1101, 1160), null, true),
/*  52 */   ARMOR_CLOTHING(8010, (BrokerFilter)new BrokerContainsFilter(new int[] { 1100, 1110, 1120, 1130, 1140 }, ), ARMOR, true),
/*  53 */   ARMOR_CLOTHING_JACKET(1100, (BrokerFilter)new BrokerContainsFilter(new int[] { 1100 }, ), ARMOR_CLOTHING, false),
/*  54 */   ARMOR_CLOTHING_GLOVES(1110, (BrokerFilter)new BrokerContainsFilter(new int[] { 1110 }, ), ARMOR_CLOTHING, false),
/*  55 */   ARMOR_CLOTHING_PAULDRONS(1120, (BrokerFilter)new BrokerContainsFilter(new int[] { 1120 }, ), ARMOR_CLOTHING, false),
/*  56 */   ARMOR_CLOTHING_PANTS(1130, (BrokerFilter)new BrokerContainsFilter(new int[] { 1130 }, ), ARMOR_CLOTHING, false),
/*  57 */   ARMOR_CLOTHING_SHOES(1140, (BrokerFilter)new BrokerContainsFilter(new int[] { 1140 }, ), ARMOR_CLOTHING, false),
/*  58 */   ARMOR_CLOTH(8020, (BrokerFilter)new BrokerContainsFilter(new int[] { 1101, 1111, 1121, 1131, 1141 }, ), ARMOR, true),
/*  59 */   ARMOR_CLOTH_JACKET(1101, (BrokerFilter)new BrokerContainsFilter(new int[] { 1101 }, ), ARMOR_CLOTH, false),
/*  60 */   ARMOR_CLOTH_GLOVES(1111, (BrokerFilter)new BrokerContainsFilter(new int[] { 1111 }, ), ARMOR_CLOTH, false),
/*  61 */   ARMOR_CLOTH_PAULDRONS(1121, (BrokerFilter)new BrokerContainsFilter(new int[] { 1121 }, ), ARMOR_CLOTH, false),
/*  62 */   ARMOR_CLOTH_PANTS(1131, (BrokerFilter)new BrokerContainsFilter(new int[] { 1131 }, ), ARMOR_CLOTH, false),
/*  63 */   ARMOR_CLOTH_SHOES(1141, (BrokerFilter)new BrokerContainsFilter(new int[] { 1141 }, ), ARMOR_CLOTH, false),
/*  64 */   ARMOR_LEATHER(8030, (BrokerFilter)new BrokerContainsFilter(new int[] { 1103, 1113, 1123, 1133, 1143 }, ), ARMOR, true),
/*  65 */   ARMOR_LEATHER_JACKET(1103, (BrokerFilter)new BrokerContainsFilter(new int[] { 1103 }, ), ARMOR_LEATHER, false),
/*  66 */   ARMOR_LEATHER_GLOVES(1113, (BrokerFilter)new BrokerContainsFilter(new int[] { 1113 }, ), ARMOR_LEATHER, false),
/*  67 */   ARMOR_LEATHER_PAULDRONS(1123, (BrokerFilter)new BrokerContainsFilter(new int[] { 1123 }, ), ARMOR_LEATHER, false),
/*  68 */   ARMOR_LEATHER_PANTS(1133, (BrokerFilter)new BrokerContainsFilter(new int[] { 1133 }, ), ARMOR_LEATHER, false),
/*  69 */   ARMOR_LEATHER_SHOES(1143, (BrokerFilter)new BrokerContainsFilter(new int[] { 1143 }, ), ARMOR_LEATHER, false),
/*  70 */   ARMOR_CHAIN(8040, (BrokerFilter)new BrokerContainsFilter(new int[] { 1105, 1115, 1125, 1135, 1145 }, ), ARMOR, true),
/*  71 */   ARMOR_CHAIN_JACKET(1105, (BrokerFilter)new BrokerContainsFilter(new int[] { 1105 }, ), ARMOR_CHAIN, false),
/*  72 */   ARMOR_CHAIN_GLOVES(1115, (BrokerFilter)new BrokerContainsFilter(new int[] { 1115 }, ), ARMOR_CHAIN, false),
/*  73 */   ARMOR_CHAIN_PAULDRONS(1125, (BrokerFilter)new BrokerContainsFilter(new int[] { 1125 }, ), ARMOR_CHAIN, false),
/*  74 */   ARMOR_CHAIN_PANTS(1135, (BrokerFilter)new BrokerContainsFilter(new int[] { 1135 }, ), ARMOR_CHAIN, false),
/*  75 */   ARMOR_CHAIN_SHOES(1145, (BrokerFilter)new BrokerContainsFilter(new int[] { 1145 }, ), ARMOR_CHAIN, false),
/*  76 */   ARMOR_PLATE(8050, (BrokerFilter)new BrokerContainsFilter(new int[] { 1106, 1116, 1126, 1136, 1146 }, ), ARMOR, true),
/*  77 */   ARMOR_PLATE_JACKET(1106, (BrokerFilter)new BrokerContainsFilter(new int[] { 1106 }, ), ARMOR_PLATE, false),
/*  78 */   ARMOR_PLATE_GLOVES(1116, (BrokerFilter)new BrokerContainsFilter(new int[] { 1116 }, ), ARMOR_PLATE, false),
/*  79 */   ARMOR_PLATE_PAULDRONS(1126, (BrokerFilter)new BrokerContainsFilter(new int[] { 1126 }, ), ARMOR_PLATE, false),
/*  80 */   ARMOR_PLATE_PANTS(1136, (BrokerFilter)new BrokerContainsFilter(new int[] { 1136 }, ), ARMOR_PLATE, false),
/*  81 */   ARMOR_PLATE_SHOES(1146, (BrokerFilter)new BrokerContainsFilter(new int[] { 1146 }, ), ARMOR_PLATE, false),
/*  82 */   ARMOR_SHIELD(1150, (BrokerFilter)new BrokerContainsFilter(new int[] { 1150 }, ), ARMOR, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   ACCESSORY(9030, (BrokerFilter)new BrokerMinMaxFilter(1200, 1270), null, true),
/*  88 */   ACCESSORY_EARRINGS(1200, (BrokerFilter)new BrokerContainsFilter(new int[] { 1200 }, ), ACCESSORY, false),
/*  89 */   ACCESSORY_NECKLACE(1210, (BrokerFilter)new BrokerContainsFilter(new int[] { 1210 }, ), ACCESSORY, false),
/*  90 */   ACCESSORY_RING(1220, (BrokerFilter)new BrokerContainsFilter(new int[] { 1220 }, ), ACCESSORY, false),
/*  91 */   ACCESSORY_BELT(1230, (BrokerFilter)new BrokerContainsFilter(new int[] { 1230 }, ), ACCESSORY, false),
/*  92 */   ACCESSORY_HEADGEAR(7030, (BrokerFilter)new BrokerMinMaxFilter(1250, 1270), ACCESSORY, false),
/*     */ 
/*     */ 
/*     */   
/*  96 */   SKILL_RELATED(9040, (BrokerFilter)new BrokerContainsFilter(new int[] { 1400, 1695 }, ), null, true),
/*  97 */   SKILL_RELATED_STIGMA(1400, (BrokerFilter)new BrokerContainsFilter(new int[] { 1400 }, ), SKILL_RELATED, true),
/*  98 */   SKILL_RELATED_STIGMA_GLADIATOR(6010, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.GLADIATOR), SKILL_RELATED_STIGMA, false),
/*  99 */   SKILL_RELATED_STIGMA_TEMPLAR(6011, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.TEMPLAR), SKILL_RELATED_STIGMA, false),
/* 100 */   SKILL_RELATED_STIGMA_ASSASSIN(6012, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.ASSASSIN), SKILL_RELATED_STIGMA, false),
/* 101 */   SKILL_RELATED_STIGMA_RANGER(6013, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.RANGER), SKILL_RELATED_STIGMA, false),
/* 102 */   SKILL_RELATED_STIGMA_SORCERER(6014, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.SORCERER), SKILL_RELATED_STIGMA, false),
/* 103 */   SKILL_RELATED_STIGMA_SPIRITMASTER(6015, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.SPIRIT_MASTER), SKILL_RELATED_STIGMA, false),
/* 104 */   SKILL_RELATED_STIGMA_CLERIC(6016, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.CLERIC), SKILL_RELATED_STIGMA, false),
/* 105 */   SKILL_RELATED_STIGMA_CHANTER(6017, (BrokerFilter)new BrokerPlayerClassExtraFilter(1400, PlayerClass.CHANTER), SKILL_RELATED_STIGMA, false),
/* 106 */   SKILL_RELATED_SKILL_MANUAL(1695, (BrokerFilter)new BrokerContainsFilter(new int[] { 1695 }, ), SKILL_RELATED, true),
/* 107 */   SKILL_RELATED_SKILL_MANUAL_GLADIATOR(6020, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.GLADIATOR), SKILL_RELATED_SKILL_MANUAL, false),
/* 108 */   SKILL_RELATED_SKILL_MANUAL_TEMPLAR(6021, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.TEMPLAR), SKILL_RELATED_SKILL_MANUAL, false),
/* 109 */   SKILL_RELATED_SKILL_MANUAL_ASSASSIN(6022, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.ASSASSIN), SKILL_RELATED_SKILL_MANUAL, false),
/* 110 */   SKILL_RELATED_SKILL_MANUAL_RANGER(6023, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.RANGER), SKILL_RELATED_SKILL_MANUAL, false),
/* 111 */   SKILL_RELATED_SKILL_MANUAL_SORCERER(6024, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.SORCERER), SKILL_RELATED_SKILL_MANUAL, false),
/* 112 */   SKILL_RELATED_SKILL_MANUAL_SPIRITMASTER(6025, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.SPIRIT_MASTER), SKILL_RELATED_SKILL_MANUAL, false),
/* 113 */   SKILL_RELATED_SKILL_MANUAL_CLERIC(6026, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.CLERIC), SKILL_RELATED_SKILL_MANUAL, false),
/* 114 */   SKILL_RELATED_SKILL_MANUAL_CHANTER(6027, (BrokerFilter)new BrokerPlayerClassExtraFilter(1695, PlayerClass.CHANTER), SKILL_RELATED_SKILL_MANUAL, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   CRAFT(9050, (BrokerFilter)new BrokerContainsFilter(new int[] { 1520, 1522 }, ), null, true),
/* 120 */   CRAFT_MATERIALS(1520, (BrokerFilter)new BrokerContainsFilter(new int[] { 1520 }, ), CRAFT, true),
/* 121 */   CRAFT_MATERIALS_COLLECTION(6030, (BrokerFilter)new BrokerContainsExtraFilter(new int[] { 15200 }, ), CRAFT_MATERIALS, false),
/* 122 */   CRAFT_MATERIALS_GAIN(6031, (BrokerFilter)new BrokerContainsExtraFilter(new int[] { 15201 }, ), CRAFT_MATERIALS, false),
/* 123 */   CRAFT_MATERIALS_PARTS(6032, (BrokerFilter)new BrokerContainsExtraFilter(new int[] { 15202 }, ), CRAFT_MATERIALS, false),
/* 124 */   CRAFT_DESIGN(1522, (BrokerFilter)new BrokerContainsFilter(new int[] { 1522 }, ), CRAFT, true),
/* 125 */   CRAFT_DESIGN_WEAPONSMITHING(6040, (BrokerFilter)new BrokerContainsFilter(new int[] { 1522 }, ), CRAFT_DESIGN, false),
/* 126 */   CRAFT_DESIGN_ARMORSMITHING(6041, (BrokerFilter)new BrokerContainsFilter(new int[] { 1522 }, ), CRAFT_DESIGN, false),
/* 127 */   CRAFT_DESIGN_TAILORING(6042, (BrokerFilter)new BrokerContainsFilter(new int[] { 1522 }, ), CRAFT_DESIGN, false),
/* 128 */   CRAFT_DESIGN_HANDICRAFTING(6043, (BrokerFilter)new BrokerContainsFilter(new int[] { 1522 }, ), CRAFT_DESIGN, false),
/* 129 */   CRAFT_DESIGN_ALCHEMY(6044, (BrokerFilter)new BrokerContainsFilter(new int[] { 1522 }, ), CRAFT_DESIGN, false),
/* 130 */   CRAFT_DESIGN_COOKING(6045, (BrokerFilter)new BrokerContainsFilter(new int[] { 1522 }, ), CRAFT_DESIGN, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   CONSUMABLES(9060, (BrokerFilter)new BrokerContainsFilter(new int[] { 1410, 1600, 1620, 1640, 1690, 1694 }, ), null, true),
/* 136 */   CONSUMABLES_FOOD(1600, (BrokerFilter)new BrokerContainsFilter(new int[] { 1600 }, ), CONSUMABLES, false),
/* 137 */   CONSUMABLES_POTION(1620, (BrokerFilter)new BrokerContainsFilter(new int[] { 1620 }, ), CONSUMABLES, false),
/* 138 */   CONSUMABLES_SCROLL(7060, (BrokerFilter)new BrokerContainsFilter(new int[] { 1640 }, ), CONSUMABLES, false),
/* 139 */   CONSUMABLES_MODIFY(8060, (BrokerFilter)new BrokerContainsFilter(new int[] { 1660, 1670, 1680, 1692 }, ), CONSUMABLES, true),
/* 140 */   CONSUMABLES_MODIFY_ENCHANTMENT_STONE(1660, (BrokerFilter)new BrokerContainsFilter(new int[] { 1660 }, ), CONSUMABLES_MODIFY, false),
/* 141 */   CONSUMABLES_MODIFY_MANASTONE(1670, (BrokerFilter)new BrokerContainsFilter(new int[] { 1670 }, ), CONSUMABLES_MODIFY, false),
/* 142 */   CONSUMABLES_MODIFY_GODSTONE(1680, (BrokerFilter)new BrokerContainsFilter(new int[] { 1680 }, ), CONSUMABLES_MODIFY, false),
/* 143 */   CONSUMABLES_MODIFY_DYE(7061, (BrokerFilter)new BrokerContainsFilter(new int[] { 1692 }, ), CONSUMABLES_MODIFY, false),
/* 144 */   CONSUMABLES_OTHER(7062, (BrokerFilter)new BrokerContainsFilter(new int[] { 1410, 1690, 1694 }, ), CONSUMABLES, false),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   OTHER(7070, (BrokerFilter)new BrokerContainsFilter(new int[] { 1860, 1880 }, ), null, false),
/*     */   
/* 151 */   UNKNOWN(1, (BrokerFilter)new BrokerContainsFilter(new int[] { 0 }, ), null, false);
/*     */ 
/*     */   
/*     */   private int typeId;
/*     */ 
/*     */   
/*     */   private BrokerFilter filter;
/*     */ 
/*     */   
/*     */   private BrokerItemMask parent;
/*     */ 
/*     */   
/*     */   private boolean childrenExist;
/*     */ 
/*     */   
/*     */   BrokerItemMask(int typeId, BrokerFilter filter, BrokerItemMask parent, boolean childrenExist) {
/* 167 */     this.typeId = typeId;
/* 168 */     this.filter = filter;
/* 169 */     this.parent = parent;
/* 170 */     this.childrenExist = childrenExist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 178 */     return this.typeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatches(Item item) {
/* 188 */     return this.filter.accept(item.getItemTemplate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChildrenMask(int maskId) {
/* 198 */     for (BrokerItemMask p = this.parent; p != null; p = p.parent) {
/*     */       
/* 200 */       if (p.typeId == maskId)
/* 201 */         return true; 
/*     */     } 
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BrokerItemMask getBrokerMaskById(int id) {
/* 214 */     for (BrokerItemMask mt : values()) {
/*     */       
/* 216 */       if (mt.typeId == id)
/* 217 */         return mt; 
/*     */     } 
/* 219 */     return UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChildren() {
/* 227 */     return this.childrenExist;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\BrokerItemMask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
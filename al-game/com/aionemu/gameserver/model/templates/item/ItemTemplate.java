/*     */ package com.aionemu.gameserver.model.templates.item;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.itemengine.actions.ItemActions;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.items.ItemId;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
/*     */ import java.util.TreeSet;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
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
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.NONE)
/*     */ @XmlRootElement(name = "item_templates")
/*     */ public class ItemTemplate
/*     */   extends VisibleObjectTemplate
/*     */ {
/*     */   @XmlAttribute(name = "id", required = true)
/*     */   @XmlID
/*     */   private String id;
/*     */   @XmlElement(name = "modifiers", required = false)
/*     */   protected ModifiersTemplate modifiers;
/*     */   @XmlElement(name = "actions", required = false)
/*     */   protected ItemActions actions;
/*     */   @XmlAttribute(name = "mask")
/*     */   private int mask;
/*     */   @XmlAttribute(name = "slot")
/*     */   private int itemSlot;
/*     */   @XmlAttribute(name = "usedelayid")
/*     */   private int useDelayId;
/*     */   @XmlAttribute(name = "usedelay")
/*     */   private int useDelay;
/*     */   @XmlAttribute(name = "equipment_type")
/*  71 */   private EquipType equipmentType = EquipType.NONE;
/*     */   
/*     */   @XmlAttribute(name = "cash_item")
/*     */   private int cashItem;
/*     */   
/*     */   @XmlAttribute(name = "dmg_decal")
/*     */   private int dmgDecal;
/*     */   
/*     */   @XmlAttribute(name = "weapon_boost")
/*     */   private int weaponBoost;
/*     */   
/*     */   @XmlAttribute(name = "price")
/*     */   private int price;
/*     */   
/*     */   @XmlAttribute(name = "ap")
/*     */   private int abyssPoints;
/*     */   
/*     */   @XmlAttribute(name = "ai")
/*     */   private int abyssItem;
/*     */   
/*     */   @XmlAttribute(name = "aic")
/*     */   private int abyssItemCount;
/*     */   
/*     */   @XmlAttribute(name = "max_stack_count")
/*     */   private int maxStackCount;
/*     */   
/*     */   @XmlAttribute(name = "level")
/*     */   private int level;
/*     */   
/*     */   @XmlAttribute(name = "quality")
/*     */   private ItemQuality itemQuality;
/*     */   
/*     */   @XmlAttribute(name = "item_type")
/*     */   private String itemType;
/*     */   
/*     */   @XmlAttribute(name = "weapon_type")
/*     */   private WeaponType weaponType;
/*     */   
/*     */   @XmlAttribute(name = "armor_type")
/*     */   private ArmorType armorType;
/*     */   
/*     */   @XmlAttribute(name = "attack_type")
/*     */   private String attackType;
/*     */   
/*     */   @XmlAttribute(name = "attack_gap")
/*     */   private float attackGap;
/*     */   
/*     */   @XmlAttribute(name = "desc")
/*     */   private String description;
/*     */   
/*     */   @XmlAttribute(name = "gender")
/*     */   private String genderPermitted;
/*     */   
/*     */   @XmlAttribute(name = "option_slot_bonus")
/*     */   private int optionSlotBonus;
/*     */   
/*     */   @XmlAttribute(name = "bonus_apply")
/*     */   private String bonusApply;
/*     */   
/*     */   @XmlAttribute(name = "no_enchant")
/*     */   private boolean noEnchant;
/*     */   
/*     */   @XmlAttribute(name = "can_proc_enchant")
/*     */   private boolean canProcEnchant;
/*     */   
/*     */   @XmlAttribute(name = "can_split")
/*     */   private boolean canSplit;
/*     */   
/*     */   @XmlAttribute(name = "drop")
/*     */   private boolean itemDropPermitted;
/*     */   
/*     */   @XmlAttribute(name = "dye")
/*     */   private boolean itemDyePermitted;
/*     */   
/*     */   @XmlAttribute(name = "race")
/* 146 */   private ItemRace race = ItemRace.ALL;
/*     */ 
/*     */   
/*     */   private int itemId;
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "return_world")
/*     */   private int returnWorldId;
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "return_alias")
/*     */   private String returnAlias;
/*     */ 
/*     */   
/*     */   @XmlElement(name = "godstone")
/*     */   private GodstoneInfo godstoneInfo;
/*     */   
/*     */   @XmlElement(name = "stigma")
/*     */   private Stigma stigma;
/*     */   
/*     */   @XmlAttribute(name = "name")
/*     */   private String name;
/*     */   
/*     */   @XmlAttribute(name = "restrict")
/*     */   private String restrict;
/*     */   
/*     */   @XmlTransient
/*     */   private int[] restricts;
/*     */ 
/*     */   
/*     */   public int getMask() {
/* 177 */     return this.mask;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemSlot() {
/* 182 */     return this.itemSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClassSpecific(PlayerClass playerClass) {
/* 192 */     boolean related = (this.restricts[playerClass.ordinal()] > 0);
/* 193 */     if (!related && !playerClass.isStartingClass())
/*     */     {
/* 195 */       related = (this.restricts[PlayerClass.getStartingClassFor(playerClass).ordinal()] > 0);
/*     */     }
/* 197 */     return related;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAllowedFor(PlayerClass playerClass, int level) {
/* 208 */     return (this.restricts[playerClass.ordinal()] <= level && this.restricts[playerClass.ordinal()] != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeSet<StatModifier> getModifiers() {
/* 216 */     if (this.modifiers != null)
/*     */     {
/* 218 */       return this.modifiers.getModifiers();
/*     */     }
/*     */ 
/*     */     
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemActions getActions() {
/* 231 */     return this.actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EquipType getEquipmentType() {
/* 240 */     return this.equipmentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrice() {
/* 248 */     return this.price;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAbyssPoints() {
/* 256 */     return this.abyssPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAbyssItem() {
/* 264 */     return this.abyssItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAbyssItemCount() {
/* 272 */     return this.abyssItemCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 280 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemQuality getItemQuality() {
/* 288 */     return this.itemQuality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemType() {
/* 296 */     return this.itemType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeaponType getWeaponType() {
/* 304 */     return this.weaponType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArmorType getArmorType() {
/* 312 */     return this.armorType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameId() {
/*     */     try {
/* 323 */       int val = Integer.parseInt(this.description);
/* 324 */       return val;
/*     */     }
/* 326 */     catch (NumberFormatException nfe) {
/*     */       
/* 328 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCashItem() {
/* 337 */     return this.cashItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDmgDecal() {
/* 345 */     return this.dmgDecal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackCount() {
/* 353 */     return this.maxStackCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttackType() {
/* 361 */     return this.attackType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAttackGap() {
/* 369 */     return this.attackGap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGenderPermitted() {
/* 377 */     return this.genderPermitted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptionSlotBonus() {
/* 385 */     return this.optionSlotBonus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBonusApply() {
/* 393 */     return this.bonusApply;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNoEnchant() {
/* 401 */     return this.noEnchant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCanProcEnchant() {
/* 409 */     return this.canProcEnchant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCanSplit() {
/* 417 */     return this.canSplit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemDyePermitted() {
/* 425 */     return this.itemDyePermitted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemDropPermitted() {
/* 433 */     return this.itemDropPermitted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemRace getRace() {
/* 441 */     return this.race;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeaponBoost() {
/* 449 */     return this.weaponBoost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWeapon() {
/* 457 */     return (this.equipmentType == EquipType.WEAPON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmor() {
/* 465 */     return (this.equipmentType == EquipType.ARMOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isKinah() {
/* 470 */     return (this.itemId == ItemId.KINAH.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStigma() {
/* 475 */     return (this.itemId > 140000000 && this.itemId < 140001000);
/*     */   }
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 480 */     setItemId(Integer.parseInt(this.id));
/* 481 */     String[] parts = this.restrict.split(",");
/* 482 */     this.restricts = new int[12];
/* 483 */     for (int i = 0; i < parts.length; i++)
/*     */     {
/* 485 */       this.restricts[i] = Integer.parseInt(parts[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItemId(int itemId) {
/* 491 */     this.itemId = itemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemSetTemplate getItemSet() {
/* 499 */     return DataManager.ITEM_SET_DATA.getItemSetTemplateByItemId(this.itemId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemSet() {
/* 507 */     return (getItemSet() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GodstoneInfo getGodstoneInfo() {
/* 515 */     return this.godstoneInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 521 */     return (this.name != null) ? this.name : "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTemplateId() {
/* 527 */     return this.itemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReturnWorldId() {
/* 535 */     return this.returnWorldId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReturnAlias() {
/* 543 */     return this.returnAlias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDelayTime() {
/* 551 */     return this.useDelay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDelayId() {
/* 559 */     return this.useDelayId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stigma getStigma() {
/* 567 */     return this.stigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSoulBound() {
/* 576 */     return ((getMask() & 0x40) == 64);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\ItemTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
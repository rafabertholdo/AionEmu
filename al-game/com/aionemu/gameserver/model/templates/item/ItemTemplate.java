package com.aionemu.gameserver.model.templates.item;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.itemengine.actions.ItemActions;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
import java.util.TreeSet;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "item_templates")
public class ItemTemplate extends VisibleObjectTemplate {
  @XmlAttribute(name = "id", required = true)
  @XmlID
  private String id;
  @XmlElement(name = "modifiers", required = false)
  protected ModifiersTemplate modifiers;
  @XmlElement(name = "actions", required = false)
  protected ItemActions actions;
  @XmlAttribute(name = "mask")
  private int mask;
  @XmlAttribute(name = "slot")
  private int itemSlot;
  @XmlAttribute(name = "usedelayid")
  private int useDelayId;
  @XmlAttribute(name = "usedelay")
  private int useDelay;
  @XmlAttribute(name = "equipment_type")
  private EquipType equipmentType = EquipType.NONE;

  @XmlAttribute(name = "cash_item")
  private int cashItem;

  @XmlAttribute(name = "dmg_decal")
  private int dmgDecal;

  @XmlAttribute(name = "weapon_boost")
  private int weaponBoost;

  @XmlAttribute(name = "price")
  private int price;

  @XmlAttribute(name = "ap")
  private int abyssPoints;

  @XmlAttribute(name = "ai")
  private int abyssItem;

  @XmlAttribute(name = "aic")
  private int abyssItemCount;

  @XmlAttribute(name = "max_stack_count")
  private int maxStackCount;

  @XmlAttribute(name = "level")
  private int level;

  @XmlAttribute(name = "quality")
  private ItemQuality itemQuality;

  @XmlAttribute(name = "item_type")
  private String itemType;

  @XmlAttribute(name = "weapon_type")
  private WeaponType weaponType;

  @XmlAttribute(name = "armor_type")
  private ArmorType armorType;

  @XmlAttribute(name = "attack_type")
  private String attackType;

  @XmlAttribute(name = "attack_gap")
  private float attackGap;

  @XmlAttribute(name = "desc")
  private String description;

  @XmlAttribute(name = "gender")
  private String genderPermitted;

  @XmlAttribute(name = "option_slot_bonus")
  private int optionSlotBonus;

  @XmlAttribute(name = "bonus_apply")
  private String bonusApply;

  @XmlAttribute(name = "no_enchant")
  private boolean noEnchant;

  @XmlAttribute(name = "can_proc_enchant")
  private boolean canProcEnchant;

  @XmlAttribute(name = "can_split")
  private boolean canSplit;

  @XmlAttribute(name = "drop")
  private boolean itemDropPermitted;

  @XmlAttribute(name = "dye")
  private boolean itemDyePermitted;

  @XmlAttribute(name = "race")
  private ItemRace race = ItemRace.ALL;

  private int itemId;

  @XmlAttribute(name = "return_world")
  private int returnWorldId;

  @XmlAttribute(name = "return_alias")
  private String returnAlias;

  @XmlElement(name = "godstone")
  private GodstoneInfo godstoneInfo;

  @XmlElement(name = "stigma")
  private Stigma stigma;

  @XmlAttribute(name = "name")
  private String name;

  @XmlAttribute(name = "restrict")
  private String restrict;

  @XmlTransient
  private int[] restricts;

  public int getMask() {
    return this.mask;
  }

  public int getItemSlot() {
    return this.itemSlot;
  }

  public boolean isClassSpecific(PlayerClass playerClass) {
    boolean related = (this.restricts[playerClass.ordinal()] > 0);
    if (!related && !playerClass.isStartingClass()) {
      related = (this.restricts[PlayerClass.getStartingClassFor(playerClass).ordinal()] > 0);
    }
    return related;
  }

  public boolean isAllowedFor(PlayerClass playerClass, int level) {
    return (this.restricts[playerClass.ordinal()] <= level && this.restricts[playerClass.ordinal()] != 0);
  }

  public TreeSet<StatModifier> getModifiers() {
    if (this.modifiers != null) {
      return this.modifiers.getModifiers();
    }

    return null;
  }

  public ItemActions getActions() {
    return this.actions;
  }

  public EquipType getEquipmentType() {
    return this.equipmentType;
  }

  public int getPrice() {
    return this.price;
  }

  public int getAbyssPoints() {
    return this.abyssPoints;
  }

  public int getAbyssItem() {
    return this.abyssItem;
  }

  public int getAbyssItemCount() {
    return this.abyssItemCount;
  }

  public int getLevel() {
    return this.level;
  }

  public ItemQuality getItemQuality() {
    return this.itemQuality;
  }

  public String getItemType() {
    return this.itemType;
  }

  public WeaponType getWeaponType() {
    return this.weaponType;
  }

  public ArmorType getArmorType() {
    return this.armorType;
  }

  public int getNameId() {
    try {
      int val = Integer.parseInt(this.description);
      return val;
    } catch (NumberFormatException nfe) {

      return 0;
    }
  }

  public int getCashItem() {
    return this.cashItem;
  }

  public int getDmgDecal() {
    return this.dmgDecal;
  }

  public int getMaxStackCount() {
    return this.maxStackCount;
  }

  public String getAttackType() {
    return this.attackType;
  }

  public float getAttackGap() {
    return this.attackGap;
  }

  public String getGenderPermitted() {
    return this.genderPermitted;
  }

  public int getOptionSlotBonus() {
    return this.optionSlotBonus;
  }

  public String getBonusApply() {
    return this.bonusApply;
  }

  public boolean isNoEnchant() {
    return this.noEnchant;
  }

  public boolean isCanProcEnchant() {
    return this.canProcEnchant;
  }

  public boolean isCanSplit() {
    return this.canSplit;
  }

  public boolean isItemDyePermitted() {
    return this.itemDyePermitted;
  }

  public boolean isItemDropPermitted() {
    return this.itemDropPermitted;
  }

  public ItemRace getRace() {
    return this.race;
  }

  public int getWeaponBoost() {
    return this.weaponBoost;
  }

  public boolean isWeapon() {
    return (this.equipmentType == EquipType.WEAPON);
  }

  public boolean isArmor() {
    return (this.equipmentType == EquipType.ARMOR);
  }

  public boolean isKinah() {
    return (this.itemId == ItemId.KINAH.value());
  }

  public boolean isStigma() {
    return (this.itemId > 140000000 && this.itemId < 140001000);
  }

  void afterUnmarshal(Unmarshaller u, Object parent) {
    setItemId(Integer.parseInt(this.id));
    String[] parts = this.restrict.split(",");
    this.restricts = new int[12];
    for (int i = 0; i < parts.length; i++) {
      this.restricts[i] = Integer.parseInt(parts[i]);
    }
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public ItemSetTemplate getItemSet() {
    return DataManager.ITEM_SET_DATA.getItemSetTemplateByItemId(this.itemId);
  }

  public boolean isItemSet() {
    return (getItemSet() != null);
  }

  public GodstoneInfo getGodstoneInfo() {
    return this.godstoneInfo;
  }

  public String getName() {
    return (this.name != null) ? this.name : "";
  }

  public int getTemplateId() {
    return this.itemId;
  }

  public int getReturnWorldId() {
    return this.returnWorldId;
  }

  public String getReturnAlias() {
    return this.returnAlias;
  }

  public int getDelayTime() {
    return this.useDelay;
  }

  public int getDelayId() {
    return this.useDelayId;
  }

  public Stigma getStigma() {
    return this.stigma;
  }

  public boolean isSoulBound() {
    return ((getMask() & 0x40) == 64);
  }
}

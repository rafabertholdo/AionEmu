package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.model.templates.item.EquipType;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.taskmanager.tasks.ItemUpdater;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Item extends AionObject {
  private long itemCount = 1L;

  private int ownerId = 0;
  private int itemColor = 0;

  private ItemTemplate itemTemplate;

  private ItemTemplate itemSkinTemplate;

  private boolean isEquipped = false;
  private int equipmentSlot = 65535;

  private PersistentState persistentState;

  private Set<ManaStone> manaStones;

  private GodStone godStone;

  private boolean isSoulBound = false;

  private int itemLocation;

  private int enchantLevel;

  private int fusionedItemId;

  public Item(int ownerId, int objId, ItemTemplate itemTemplate, long itemCount, boolean isEquipped,
      int equipmentSlot) {
    super(Integer.valueOf(objId));
    this.ownerId = ownerId;
    this.itemTemplate = itemTemplate;
    this.itemCount = itemCount;
    this.isEquipped = isEquipped;
    this.equipmentSlot = equipmentSlot;
    this.persistentState = PersistentState.NEW;
    if (ownerId != 0) {
      ItemUpdater.getInstance().add(this);
    }
  }

  public Item(int ownerId, int objId, int itemId, long itemCount, int itemColor, boolean isEquipped,
      boolean isSoulBound, int equipmentSlot, int itemLocation, int enchant, int itemSkin, int fusionedItem) {
    super(Integer.valueOf(objId));
    this.ownerId = ownerId;
    this.itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
    this.itemCount = itemCount;
    this.itemColor = itemColor;
    this.isEquipped = isEquipped;
    this.isSoulBound = isSoulBound;
    this.equipmentSlot = equipmentSlot;
    this.itemLocation = itemLocation;
    this.enchantLevel = enchant;
    this.fusionedItemId = fusionedItem;
    this.itemSkinTemplate = DataManager.ITEM_DATA.getItemTemplate(itemSkin);
  }

  public int getOwnerId() {
    return this.ownerId;
  }

  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public String getName() {
    return String.valueOf(this.itemTemplate.getNameId());
  }

  public String getItemName() {
    return this.itemTemplate.getName();
  }

  public ItemTemplate getItemTemplate() {
    return this.itemTemplate;
  }

  public ItemTemplate getItemSkinTemplate() {
    if (this.itemSkinTemplate == null)
      return this.itemTemplate;
    return this.itemSkinTemplate;
  }

  public void setItemSkinTemplate(ItemTemplate newTemplate) {
    this.itemSkinTemplate = newTemplate;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public int getItemColor() {
    return this.itemColor;
  }

  public void setItemColor(int itemColor) {
    this.itemColor = itemColor;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public void setItemTemplate(ItemTemplate itemTemplate) {
    this.itemTemplate = itemTemplate;
  }

  public long getItemCount() {
    return this.itemCount;
  }

  public void setItemCount(long itemCount) {
    this.itemCount = itemCount;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public void increaseItemCount(long addCount) {
    this.itemCount += addCount;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public boolean decreaseItemCount(long remCount) {
    if (this.itemCount - remCount >= 0L) {

      this.itemCount -= remCount;
      if (this.itemCount == 0L && !this.itemTemplate.isKinah()) {

        setPersistentState(PersistentState.DELETED);
      } else {

        setPersistentState(PersistentState.UPDATE_REQUIRED);
      }
      return true;
    }

    return false;
  }

  public boolean isEquipped() {
    return this.isEquipped;
  }

  public void setEquipped(boolean isEquipped) {
    this.isEquipped = isEquipped;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public int getEquipmentSlot() {
    return this.equipmentSlot;
  }

  public void setEquipmentSlot(int equipmentSlot) {
    this.equipmentSlot = equipmentSlot;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public Set<ManaStone> getItemStones() {
    if (this.manaStones == null) {
      this.manaStones = new TreeSet<ManaStone>(new Comparator<ManaStone>() {

        public int compare(ManaStone o1, ManaStone o2) {
          if (o1.getSlot() == o2.getSlot())
            return 0;
          return (o1.getSlot() > o2.getSlot()) ? 1 : -1;
        }
      });
    }
    return this.manaStones;
  }

  public boolean hasManaStones() {
    return (this.manaStones != null && this.manaStones.size() > 0);
  }

  public GodStone getGodStone() {
    return this.godStone;
  }

  public GodStone addGodStone(int itemId) {
    PersistentState state = (this.godStone != null) ? PersistentState.UPDATE_REQUIRED : PersistentState.NEW;
    this.godStone = new GodStone(getObjectId(), itemId, state);
    return this.godStone;
  }

  public void setGoodStone(GodStone goodStone) {
    this.godStone = goodStone;
  }

  public int getEnchantLevel() {
    return this.enchantLevel;
  }

  public void setEnchantLevel(int enchantLevel) {
    this.enchantLevel = enchantLevel;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public PersistentState getPersistentState() {
    return this.persistentState;
  }

  public void setPersistentState(PersistentState persistentState) {
    if (persistentState != PersistentState.UPDATED && persistentState != PersistentState.NOACTION && this.ownerId != 0)
      ItemUpdater.getInstance().add(this);
    switch (persistentState) {

      case DELETED:
        if (this.persistentState == PersistentState.NEW) {
          this.persistentState = PersistentState.NOACTION;
        } else {
          this.persistentState = PersistentState.DELETED;
        }
        return;
      case UPDATE_REQUIRED:
        if (this.persistentState == PersistentState.NEW)
          return;
        break;
    }
    this.persistentState = persistentState;
  }

  public void setItemLocation(int storageType) {
    this.itemLocation = storageType;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public int getItemLocation() {
    return this.itemLocation;
  }

  public int getItemMask() {
    return this.itemTemplate.getMask();
  }

  public boolean isSoulBound() {
    return this.isSoulBound;
  }

  public boolean isTradeable() {
    return ((getItemMask() & 0x2) == 2 && !this.isSoulBound);
  }

  public void setSoulBound(boolean isSoulBound) {
    this.isSoulBound = isSoulBound;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public EquipType getEquipmentType() {
    if (this.itemTemplate.isStigma()) {
      return EquipType.STIGMA;
    }
    return this.itemTemplate.getEquipmentType();
  }

  public String toString() {
    return "Item [equipmentSlot=" + this.equipmentSlot + ", godStone=" + this.godStone + ", isEquipped="
        + this.isEquipped + ", itemColor=" + this.itemColor + ", itemCount=" + this.itemCount + ", itemLocation="
        + this.itemLocation + ", itemTemplate=" + this.itemTemplate + ", manaStones=" + this.manaStones
        + ", persistentState=" + this.persistentState + "]";
  }

  public int getItemId() {
    return this.itemTemplate.getTemplateId();
  }

  public int getNameID() {
    return this.itemTemplate.getNameId();
  }

  public boolean hasFusionedItem() {
    return (this.fusionedItemId != 0);
  }

  public int getFusionedItem() {
    return this.fusionedItemId;
  }

  public void setFusionedItem(int itemTemplateId) {
    this.fusionedItemId = itemTemplateId;
  }

  public void finalize() {
    if (this.persistentState == PersistentState.NOACTION || this.persistentState == PersistentState.DELETED
        || this.ownerId == 0) {
      IDFactory.getInstance().releaseId(getObjectId());
    }

    try {
      super.finalize();
    } catch (Throwable e) {

      e.printStackTrace();
    }
  }
}

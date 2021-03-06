package com.aionemu.gameserver.model.items;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import java.util.TreeSet;

public class ManaStone extends ItemStone {
  private TreeSet<StatModifier> modifiers;

  public ManaStone(int itemObjId, int itemId, int slot, PersistentState persistentState) {
    super(itemObjId, itemId, slot, ItemStone.ItemStoneType.MANASTONE, persistentState);

    ItemTemplate stoneTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
    if (stoneTemplate != null && stoneTemplate.getModifiers() != null) {
      this.modifiers = stoneTemplate.getModifiers();
    }
  }

  public TreeSet<StatModifier> getModifiers() {
    return this.modifiers;
  }

  public StatModifier getFirstModifier() {
    return (this.modifiers != null) ? this.modifiers.first() : null;
  }
}

package com.aionemu.gameserver.model.gameobjects.stats.listeners;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.id.ItemSetStatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.id.ItemStatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.id.StoneStatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
import com.aionemu.gameserver.model.templates.itemset.PartBonus;
import com.aionemu.gameserver.services.EnchantService;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class ItemEquipmentListener {
  private static final Logger log = Logger.getLogger(ItemEquipmentListener.class);

  private static void onItemEquipment(ItemTemplate itemTemplate, int slot, CreatureGameStats<?> cgs) {
    TreeSet<StatModifier> modifiers = itemTemplate.getModifiers();
    if (modifiers == null) {
      if (cgs instanceof com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats) {
        log.debug("No effect was found for item " + itemTemplate.getTemplateId());
      }
    }

    cgs.addModifiers((StatEffectId) ItemStatEffectId.getInstance(itemTemplate.getTemplateId(), slot), modifiers);
  }

  public static void onItemEquipment(Item item, Player owner) {
    ItemTemplate itemTemplate = item.getItemTemplate();
    onItemEquipment(itemTemplate, item.getEquipmentSlot(), (CreatureGameStats<?>) owner.getGameStats());

    if (itemTemplate.isItemSet()) {
      onItemSetPartEquipment(itemTemplate.getItemSet(), owner);
    }
    if (item.hasManaStones()) {
      addStonesStats(item.getItemStones(), (CreatureGameStats<?>) owner.getGameStats());
    }
    addGodstoneEffect(owner, item);

    if (item.getItemTemplate().isWeapon()) {
      recalculateWeaponMastery(owner);
    }
    if (item.getItemTemplate().isArmor()) {
      recalculateArmorMastery(owner);
    }
    EnchantService.onItemEquip(owner, item);
  }

  private static void onItemSetPartEquipment(ItemSetTemplate itemSetTemplate, Player player) {
    if (itemSetTemplate == null) {
      return;
    }

    int itemSetPartsEquipped = player.getEquipment().itemSetPartsEquipped(itemSetTemplate.getId());

    for (PartBonus itempartbonus : itemSetTemplate.getPartbonus()) {

      ItemSetStatEffectId setEffectId = ItemSetStatEffectId.getInstance(itemSetTemplate.getId(),
          itempartbonus.getCount());

      if (itempartbonus.getCount() <= itemSetPartsEquipped
          && !player.getGameStats().effectAlreadyAdded((StatEffectId) setEffectId)) {

        player.getGameStats().addModifiers((StatEffectId) setEffectId, itempartbonus.getModifiers());
      }
    }

    if (itemSetTemplate.getFullbonus() != null && itemSetPartsEquipped == itemSetTemplate.getFullbonus().getCount()) {

      ItemSetStatEffectId setEffectId = ItemSetStatEffectId.getInstance(itemSetTemplate.getId(),
          itemSetPartsEquipped + 1);

      if (!player.getGameStats().effectAlreadyAdded((StatEffectId) setEffectId)) {

        player.getGameStats().addModifiers((StatEffectId) setEffectId, itemSetTemplate.getFullbonus().getModifiers());
      }
    }
  }

  private static void recalculateWeaponMastery(Player owner) {
    if (owner.getEquipment() == null) {
      return;
    }
    WeaponType weaponType = owner.getEquipment().getMainHandWeaponType();
    int currentWeaponMasterySkill = owner.getEffectController().getWeaponMastery();
    if (weaponType == null && currentWeaponMasterySkill != 0) {

      owner.getEffectController().removePassiveEffect(currentWeaponMasterySkill);

      return;
    }
    boolean weaponEquiped = owner.getEquipment().isWeaponEquipped(weaponType);
    Integer skillId = owner.getSkillList().getWeaponMasterySkill(weaponType);
    if (skillId == null)
      return;
    boolean masterySet = owner.getEffectController().isWeaponMasterySet(skillId.intValue());

    if (masterySet && !weaponEquiped) {
      owner.getEffectController().removePassiveEffect(skillId.intValue());
    }

    if (!masterySet && weaponEquiped) {
      owner.getController().useSkill(skillId.intValue());
    }
  }

  private static void recalculateArmorMastery(Player owner) {
    if (owner.getEquipment() == null) {
      return;
    }
    for (ArmorType armorType : ArmorType.values()) {

      boolean armorEquiped = owner.getEquipment().isArmorEquipped(armorType);
      Integer skillId = owner.getSkillList().getArmorMasterySkill(armorType);
      if (skillId != null) {

        boolean masterySet = owner.getEffectController().isArmorMasterySet(skillId.intValue());

        if (masterySet && !armorEquiped) {
          owner.getEffectController().removePassiveEffect(skillId.intValue());
        }

        if (!masterySet && armorEquiped) {
          owner.getController().useSkill(skillId.intValue());
        }
      }
    }
  }

  private static void addStonesStats(Set<ManaStone> itemStones, CreatureGameStats<?> cgs) {
    if (itemStones == null || itemStones.size() == 0) {
      return;
    }
    for (ManaStone stone : itemStones) {
      addStoneStats(stone, cgs);
    }
  }

  public static void removeStoneStats(Set<ManaStone> itemStones, CreatureGameStats<?> cgs) {
    if (itemStones == null || itemStones.size() == 0) {
      return;
    }
    for (ManaStone stone : itemStones) {

      TreeSet<StatModifier> modifiers = stone.getModifiers();
      if (modifiers != null) {
        cgs.endEffect((StatEffectId) StoneStatEffectId.getInstance(stone.getItemObjId(), stone.getSlot()));
      }
    }
  }

  public static void addStoneStats(ManaStone stone, CreatureGameStats<?> cgs) {
    TreeSet<StatModifier> modifiers = stone.getModifiers();
    if (modifiers != null) {
      cgs.addModifiers((StatEffectId) StoneStatEffectId.getInstance(stone.getItemObjId(), stone.getSlot()), modifiers);
    }
  }

  public static void onItemUnequipment(Item item, Player owner) {
    if (item.getItemTemplate().isItemSet()) {
      onItemSetPartUnequipment(item.getItemTemplate().getItemSet(), owner);
    }
    owner.getGameStats().endEffect(
        (StatEffectId) ItemStatEffectId.getInstance(item.getItemTemplate().getTemplateId(), item.getEquipmentSlot()));

    if (item.hasManaStones()) {
      removeStoneStats(item.getItemStones(), (CreatureGameStats<?>) owner.getGameStats());
    }
    removeGodstoneEffect(owner, item);

    if (item.getItemTemplate().isWeapon()) {
      recalculateWeaponMastery(owner);
    }
    if (item.getItemTemplate().isArmor()) {
      recalculateArmorMastery(owner);
    }
    EnchantService.onItemUnequip(owner, item);
  }

  private static void onItemSetPartUnequipment(ItemSetTemplate itemSetTemplate, Player player) {
    if (itemSetTemplate == null) {
      return;
    }

    int previousItemSetPartsEquipped = player.getEquipment().itemSetPartsEquipped(itemSetTemplate.getId()) + 1;

    if (itemSetTemplate.getFullbonus() != null
        && previousItemSetPartsEquipped == itemSetTemplate.getFullbonus().getCount()) {

      player.getGameStats().endEffect((StatEffectId) ItemSetStatEffectId.getInstance(itemSetTemplate.getId(),
          itemSetTemplate.getFullbonus().getCount() + 1));
    }

    for (PartBonus itempartbonus : itemSetTemplate.getPartbonus()) {

      if (itempartbonus.getCount() == previousItemSetPartsEquipped) {
        player.getGameStats().endEffect(
            (StatEffectId) ItemSetStatEffectId.getInstance(itemSetTemplate.getId(), itempartbonus.getCount()));
      }
    }
  }

  private static void addGodstoneEffect(Player player, Item item) {
    if (item.getGodStone() != null) {
      item.getGodStone().onEquip(player);
    }
  }

  private static void removeGodstoneEffect(Player player, Item item) {
    if (item.getGodStone() != null) {
      item.getGodStone().onUnEquip(player);
    }
  }
}

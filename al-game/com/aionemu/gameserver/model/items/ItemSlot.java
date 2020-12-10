package com.aionemu.gameserver.model.items;

import javolution.util.FastList;

public enum ItemSlot {
  MAIN_HAND(1), SUB_HAND(2), HELMET(4), TORSO(8), GLOVES(16), BOOTS(32), EARRINGS_LEFT(64), EARRINGS_RIGHT(128),
  RING_LEFT(256), RING_RIGHT(512), NECKLACE(1024), SHOULDER(2048), PANTS(4096), POWER_SHARD_RIGHT(8192),
  POWER_SHARD_LEFT(16384), WINGS(32768),

  WAIST(65536), MAIN_OFF_HAND(131072), SUB_OFF_HAND(262144),

  MAIN_OR_SUB(MAIN_HAND.slotIdMask | SUB_HAND.slotIdMask, true),
  EARRING_RIGHT_OR_LEFT(EARRINGS_LEFT.slotIdMask | EARRINGS_RIGHT.slotIdMask, true),
  RING_RIGHT_OR_LEFT(RING_LEFT.slotIdMask | RING_RIGHT.slotIdMask, true),
  SHARD_RIGHT_OR_LEFT(POWER_SHARD_LEFT.slotIdMask | POWER_SHARD_RIGHT.slotIdMask, true),
  TORSO_GLOVE_FOOT_SHOULDER_LEG(0, true),

  STIGMA1(524288), STIGMA2(1048576), STIGMA3(2097152), STIGMA4(4194304), STIGMA5(8388608), STIGMA6(16777216),

  NONE(33554432),

  ADV_STIGMA1(67108864), ADV_STIGMA2(134217728), ADV_STIGMA3(268435456), ADV_STIGMA4(536870912),
  ADV_STIGMA5(1073741824);

  private int slotIdMask;

  private boolean combo;

  ItemSlot(int mask, boolean combo) {
    this.slotIdMask = mask;
    this.combo = combo;
  }

  public int getSlotIdMask() {
    return this.slotIdMask;
  }

  public boolean isCombo() {
    return this.combo;
  }

  public static FastList<ItemSlot> getSlotsFor(int slotIdMask) {
    FastList<ItemSlot> slots = new FastList();
    for (ItemSlot itemSlot : values()) {

      int sumMask = itemSlot.slotIdMask & slotIdMask;

      if (sumMask > 0 && sumMask <= slotIdMask && !itemSlot.isCombo()) {
        slots.add(itemSlot);
      }
    }
    if (slots.size() == 0) {
      throw new IllegalArgumentException("Invalid provided slotIdMask " + slotIdMask);
    }
    return slots;
  }
}

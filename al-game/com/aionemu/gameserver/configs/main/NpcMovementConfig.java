package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class NpcMovementConfig {
  @Property(key = "npc.movement.active", defaultValue = "true")
  public static boolean ACTIVE_NPC_MOVEMENT;

  @Property(key = "npc.movement.delay.minimum", defaultValue = "3")
  public static int MINIMIMUM_DELAY;

  @Property(key = "npc.movement.delay.maximum", defaultValue = "15")
  public static int MAXIMUM_DELAY;
}

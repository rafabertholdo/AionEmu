package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class EnchantsConfig {
  @Property(key = "manastone.percent", defaultValue = "57")
  public static int MSPERCENT;

  @Property(key = "manastone.percent1", defaultValue = "43")
  public static int MSPERCENT1;

  @Property(key = "manastone.percent2", defaultValue = "33")
  public static int MSPERCENT2;

  @Property(key = "manastone.percent3", defaultValue = "25")
  public static int MSPERCENT3;

  @Property(key = "manastone.percent4", defaultValue = "19")
  public static int MSPERCENT4;

  @Property(key = "manastone.percent5", defaultValue = "2")
  public static int MSPERCENT5;

  @Property(key = "supplement.lesser", defaultValue = "10")
  public static int LSUP;

  @Property(key = "supplement.regular", defaultValue = "15")
  public static int RSUP;

  @Property(key = "supplement.greater", defaultValue = "20")
  public static int GSUP;
}

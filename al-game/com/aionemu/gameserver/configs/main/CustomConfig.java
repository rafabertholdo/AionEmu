package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class CustomConfig {
  @Property(key = "factions.speaking.mode", defaultValue = "0")
  public static int FACTIONS_SPEAKING_MODE;

  @Property(key = "factions.search.mode", defaultValue = "false")
  public static boolean FACTIONS_SEARCH_MODE;

  @Property(key = "skill.autolearn", defaultValue = "false")
  public static boolean SKILL_AUTOLEARN;

  @Property(key = "stigma.autolearn", defaultValue = "false")
  public static boolean STIGMA_AUTOLEARN;

  @Property(key = "disable.mob.aggro", defaultValue = "false")
  public static boolean DISABLE_MOB_AGGRO;

  @Property(key = "enable.simple.2ndclass", defaultValue = "false")
  public static boolean ENABLE_SIMPLE_2NDCLASS;

  @Property(key = "unstuck.delay", defaultValue = "3600")
  public static int UNSTUCK_DELAY;

  @Property(key = "instances.enable", defaultValue = "true")
  public static boolean ENABLE_INSTANCES;

  @Property(key = "base.flytime", defaultValue = "60")
  public static int BASE_FLYTIME;

  @Property(key = "cross.faction.binding", defaultValue = "false")
  public static boolean ENABLE_CROSS_FACTION_BINDING;

  @Property(key = "disable.drop.reduction", defaultValue = "false")
  public static boolean DISABLE_DROP_REDUCTION;

  @Property(key = "pvp.maxkills", defaultValue = "5")
  public static int MAX_DAILY_PVP_KILLS;

  @Property(key = "enable.html.welcome", defaultValue = "false")
  public static boolean ENABLE_HTML_WELCOME;

  @Property(key = "chat.whisper.level", defaultValue = "10")
  public static int LEVEL_TO_WHISPER;
}

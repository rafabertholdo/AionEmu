package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;
import java.util.regex.Pattern;

public class GSConfig {
  @Property(key = "gameserver.name", defaultValue = "aion private")
  public static String SERVER_NAME;

  @Property(key = "gameserver.character.name.pattern", defaultValue = "[a-zA-Z]{2,16}")
  public static Pattern CHAR_NAME_PATTERN;

  @Property(key = "gameserver.country.code", defaultValue = "1")
  public static int SERVER_COUNTRY_CODE;

  @Property(key = "gameserver.mode", defaultValue = "1")
  public static int SERVER_MODE;

  @Property(key = "gameserver.disable.chatserver", defaultValue = "true")
  public static boolean DISABLE_CHAT_SERVER;
}

package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class RateConfig {
  @Property(key = "rate.regular.group.xp", defaultValue = "1")
  public static int GROUPXP_RATE;
  
  @Property(key = "rate.premium.group.xp", defaultValue = "2")
  public static int PREMIUM_GROUPXP_RATE;
  
  @Property(key = "rate.regular.xp", defaultValue = "1")
  public static int XP_RATE;
  
  @Property(key = "rate.regular.quest.xp", defaultValue = "1")
  public static int QUEST_XP_RATE;
  
  @Property(key = "rate.regular.gathering.xp", defaultValue = "1")
  public static float GATHERING_XP_RATE;
  
  @Property(key = "rate.regular.crafting.xp", defaultValue = "1")
  public static float CRAFTING_XP_RATE;
  
  @Property(key = "rate.regular.quest.kinah", defaultValue = "1")
  public static int QUEST_KINAH_RATE;
  
  @Property(key = "rate.regular.drop", defaultValue = "1")
  public static int DROP_RATE;
  
  @Property(key = "rate.regular.ap.player", defaultValue = "1")
  public static float AP_PLAYER_RATE;
  
  @Property(key = "rate.regular.ap.npc", defaultValue = "1")
  public static float AP_NPC_RATE;
  
  @Property(key = "rate.premium.xp", defaultValue = "2")
  public static int PREMIUM_XP_RATE;
  
  @Property(key = "rate.premium.quest.xp", defaultValue = "2")
  public static int PREMIUM_QUEST_XP_RATE;
  
  @Property(key = "rate.premium.gathering.xp", defaultValue = "1")
  public static float PREMIUM_GATHERING_XP_RATE;
  
  @Property(key = "rate.premium.crafting.xp", defaultValue = "1")
  public static float PREMIUM_CRAFTING_XP_RATE;
  
  @Property(key = "rate.premium.quest.kinah", defaultValue = "2")
  public static int PREMIUM_QUEST_KINAH_RATE;
  
  @Property(key = "rate.premium.drop", defaultValue = "2")
  public static int PREMIUM_DROP_RATE;
  
  @Property(key = "rate.premium.ap.player", defaultValue = "2")
  public static float PREMIUM_AP_PLAYER_RATE;
  
  @Property(key = "rate.premium.ap.npc", defaultValue = "2")
  public static float PREMIUM_AP_NPC_RATE;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\main\RateConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
package com.aionemu.gameserver.utils.rates;

import com.aionemu.gameserver.configs.main.RateConfig;

public class PremiumRates extends Rates {
  public int getGroupXpRate() {
    return RateConfig.PREMIUM_GROUPXP_RATE;
  }

  public float getApNpcRate() {
    return RateConfig.PREMIUM_AP_NPC_RATE;
  }

  public float getApPlayerRate() {
    return RateConfig.PREMIUM_AP_PLAYER_RATE;
  }

  public int getDropRate() {
    return RateConfig.PREMIUM_DROP_RATE;
  }

  public int getQuestKinahRate() {
    return RateConfig.PREMIUM_QUEST_KINAH_RATE;
  }

  public int getQuestXpRate() {
    return RateConfig.PREMIUM_QUEST_XP_RATE;
  }

  public int getXpRate() {
    return RateConfig.PREMIUM_XP_RATE;
  }

  public float getCraftingXPRate() {
    return RateConfig.PREMIUM_CRAFTING_XP_RATE;
  }

  public float getGatheringXPRate() {
    return RateConfig.PREMIUM_GATHERING_XP_RATE;
  }
}

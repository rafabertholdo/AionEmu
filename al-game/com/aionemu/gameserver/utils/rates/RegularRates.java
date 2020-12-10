package com.aionemu.gameserver.utils.rates;

import com.aionemu.gameserver.configs.main.RateConfig;

public class RegularRates extends Rates {
  public int getGroupXpRate() {
    return RateConfig.GROUPXP_RATE;
  }

  public int getDropRate() {
    return RateConfig.DROP_RATE;
  }

  public float getApNpcRate() {
    return RateConfig.AP_NPC_RATE;
  }

  public float getApPlayerRate() {
    return RateConfig.AP_PLAYER_RATE;
  }

  public int getQuestKinahRate() {
    return RateConfig.QUEST_KINAH_RATE;
  }

  public int getQuestXpRate() {
    return RateConfig.QUEST_XP_RATE;
  }

  public int getXpRate() {
    return RateConfig.XP_RATE;
  }

  public float getCraftingXPRate() {
    return RateConfig.CRAFTING_XP_RATE;
  }

  public float getGatheringXPRate() {
    return RateConfig.GATHERING_XP_RATE;
  }
}

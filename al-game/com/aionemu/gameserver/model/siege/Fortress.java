package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;

public class Fortress extends SiegeLocation {
  public Fortress(SiegeLocationTemplate template) {
    super(template);
  }

  public int getInfluenceValue() {
    return SiegeConfig.SIEGE_POINTS_FORTRESS;
  }
}

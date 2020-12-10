package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;

public class Artifact extends SiegeLocation {
  public Artifact(SiegeLocationTemplate template) {
    super(template);

    setVulnerable(true);
    setNextState(Integer.valueOf(1));
  }

  public int getInfluenceValue() {
    return SiegeConfig.SIEGE_POINTS_ARTIFACT;
  }
}

package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;

public class Commander extends SiegeLocation {
  public Commander(SiegeLocationTemplate template) {
    super(template);

    setVulnerable(false);
    setNextState(Integer.valueOf(0));
  }

  public int getInfluenceValue() {
    return 0;
  }
}

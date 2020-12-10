package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;





















public class Artifact
  extends SiegeLocation
{
  public Artifact(SiegeLocationTemplate template) {
    super(template);

    
    setVulnerable(true);
    setNextState(Integer.valueOf(1));
  }


  
  public int getInfluenceValue() {
    return SiegeConfig.SIEGE_POINTS_ARTIFACT;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\Artifact.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

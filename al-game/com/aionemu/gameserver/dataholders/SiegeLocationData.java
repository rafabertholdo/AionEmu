package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.siege.Artifact;
import com.aionemu.gameserver.model.siege.Commander;
import com.aionemu.gameserver.model.siege.Fortress;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.model.siege.SiegeType;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javolution.util.FastMap;


























@XmlRootElement(name = "siege_locations")
@XmlAccessorType(XmlAccessType.FIELD)
public class SiegeLocationData
{
  @XmlElement(name = "siege_location")
  private List<SiegeLocationTemplate> siegeLocationTemplates;
  private FastMap<Integer, SiegeLocation> siegeLocations = new FastMap();

  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.siegeLocations.clear();
    for (SiegeLocationTemplate template : this.siegeLocationTemplates) {
      
      switch (template.getType()) {
        
        case FORTRESS:
          this.siegeLocations.put(Integer.valueOf(template.getId()), new Fortress(template));
        
        case ARTIFACT:
          this.siegeLocations.put(Integer.valueOf(template.getId()), new Artifact(template));
        
        case BOSSRAID_LIGHT:
        case BOSSRAID_DARK:
          this.siegeLocations.put(Integer.valueOf(template.getId()), new Commander(template));
      } 
    } 
  }




  
  public int size() {
    return this.siegeLocations.size();
  }

  
  public FastMap<Integer, SiegeLocation> getSiegeLocations() {
    return this.siegeLocations;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\SiegeLocationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

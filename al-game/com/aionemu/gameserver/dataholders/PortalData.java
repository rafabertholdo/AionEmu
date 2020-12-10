package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
import gnu.trove.THashMap;
import gnu.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


























@XmlRootElement(name = "portal_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class PortalData
{
  @XmlElement(name = "portal")
  private List<PortalTemplate> portals;
  private TIntObjectHashMap<PortalTemplate> portalData = new TIntObjectHashMap();
  private TIntObjectHashMap<ArrayList<PortalTemplate>> instancesMap = new TIntObjectHashMap();
  private THashMap<String, PortalTemplate> namedPortals = new THashMap();







  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.portalData.clear();
    this.instancesMap.clear();
    this.namedPortals.clear();
    
    for (PortalTemplate portal : this.portals) {
      
      this.portalData.put(portal.getNpcId(), portal);
      if (portal.isInstance()) {
        
        int mapId = portal.getExitPoint().getMapId();
        ArrayList<PortalTemplate> templates = (ArrayList<PortalTemplate>)this.instancesMap.get(mapId);
        if (templates == null) {
          
          templates = new ArrayList<PortalTemplate>();
          this.instancesMap.put(mapId, templates);
        } 
        templates.add(portal);
      } 
      if (portal.getName() != null && !portal.getName().isEmpty()) {
        this.namedPortals.put(portal.getName(), portal);
      }
    } 
  }
  
  public int size() {
    return this.portalData.size();
  }






  
  public PortalTemplate getPortalTemplate(int npcId) {
    return (PortalTemplate)this.portalData.get(npcId);
  }







  
  public PortalTemplate getInstancePortalTemplate(int worldId, Race race) {
    List<PortalTemplate> portals = (List<PortalTemplate>)this.instancesMap.get(worldId);
    
    if (portals == null)
    {
      
      return null;
    }
    
    for (PortalTemplate portal : portals) {
      
      if (portal.getRace() == null || portal.getRace().equals(race)) {
        return portal;
      }
    } 
    throw new IllegalArgumentException("There is no portal template for: " + worldId + " " + race);
  }







  
  public PortalTemplate getTemplateByNameAndWorld(int worldId, String name) {
    PortalTemplate portal = (PortalTemplate)this.namedPortals.get(name);
    
    if (portal != null && portal.getExitPoint().getMapId() != worldId) {
      throw new IllegalArgumentException("Invalid combination of world and name: " + worldId + " " + name);
    }
    return portal;
  }




  
  public List<PortalTemplate> getPortals() {
    return this.portals;
  }




  
  public void setPortals(List<PortalTemplate> portals) {
    this.portals = portals;
    afterUnmarshal(null, null);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\PortalData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

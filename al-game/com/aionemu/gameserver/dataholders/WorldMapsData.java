package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.WorldMapTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "world_maps")
@XmlAccessorType(XmlAccessType.NONE)
public class WorldMapsData implements Iterable<WorldMapTemplate> {
  @XmlElement(name = "map")
  private List<WorldMapTemplate> worldMaps;
  private TIntObjectHashMap<WorldMapTemplate> worldIdMap = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (WorldMapTemplate map : this.worldMaps) {
      this.worldIdMap.put(map.getMapId().intValue(), map);
    }
  }

  public Iterator<WorldMapTemplate> iterator() {
    return this.worldMaps.iterator();
  }

  public int size() {
    return (this.worldMaps == null) ? 0 : this.worldMaps.size();
  }

  public WorldMapTemplate getTemplate(int worldId) {
    return (WorldMapTemplate) this.worldIdMap.get(worldId);
  }
}

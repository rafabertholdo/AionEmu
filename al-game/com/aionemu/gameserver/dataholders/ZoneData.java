package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.zone.ZoneTemplate;
import com.aionemu.gameserver.world.zone.ZoneName;
import gnu.trove.THashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "zones")
public class ZoneData implements Iterable<ZoneTemplate> {
  @XmlElement(name = "zone")
  protected List<ZoneTemplate> zoneList;
  private THashMap<ZoneName, ZoneTemplate> zoneNameMap = new THashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (ZoneTemplate zone : this.zoneList) {
      this.zoneNameMap.put(zone.getName(), zone);
    }
  }

  public Iterator<ZoneTemplate> iterator() {
    return this.zoneList.iterator();
  }

  public int size() {
    return this.zoneList.size();
  }
}

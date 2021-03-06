package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.walker.WalkerTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "npc_walker")
@XmlAccessorType(XmlAccessType.FIELD)
public class WalkerData {
  @XmlElement(name = "walker_template")
  private List<WalkerTemplate> walkerlist;
  private TIntObjectHashMap<WalkerTemplate> walkerlistData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (WalkerTemplate route : this.walkerlist) {
      this.walkerlistData.put(route.getRouteId(), route);
    }
  }

  public int size() {
    return this.walkerlistData.size();
  }

  public WalkerTemplate getWalkerTemplate(int id) {
    return (WalkerTemplate) this.walkerlistData.get(id);
  }
}

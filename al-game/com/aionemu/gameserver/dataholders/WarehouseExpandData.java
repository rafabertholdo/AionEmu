package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.WarehouseExpandTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "warehouse_expander")
@XmlAccessorType(XmlAccessType.FIELD)
public class WarehouseExpandData {
  @XmlElement(name = "warehouse_npc")
  private List<WarehouseExpandTemplate> clist;
  private TIntObjectHashMap<WarehouseExpandTemplate> npctlistData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (WarehouseExpandTemplate npc : this.clist) {
      this.npctlistData.put(npc.getNpcId(), npc);
    }
  }

  public int size() {
    return this.npctlistData.size();
  }

  public WarehouseExpandTemplate getWarehouseExpandListTemplate(int id) {
    return (WarehouseExpandTemplate) this.npctlistData.get(id);
  }
}

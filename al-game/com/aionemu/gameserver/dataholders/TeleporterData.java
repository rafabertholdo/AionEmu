package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "npc_teleporter")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleporterData {
  @XmlElement(name = "teleporter_template")
  private List<TeleporterTemplate> tlist;
  private TIntObjectHashMap<TeleporterTemplate> npctlistData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (TeleporterTemplate npc : this.tlist) {
      this.npctlistData.put(npc.getNpcId(), npc);
    }
  }

  public int size() {
    return this.npctlistData.size();
  }

  public TeleporterTemplate getTeleporterTemplate(int id) {
    return (TeleporterTemplate) this.npctlistData.get(id);
  }
}

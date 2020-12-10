package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.teleport.TelelocationTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teleport_location")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleLocationData {
  @XmlElement(name = "teleloc_template")
  private List<TelelocationTemplate> tlist;
  private TIntObjectHashMap<TelelocationTemplate> loctlistData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (TelelocationTemplate loc : this.tlist) {
      this.loctlistData.put(loc.getLocId(), loc);
    }
  }

  public int size() {
    return this.loctlistData.size();
  }

  public TelelocationTemplate getTelelocationTemplate(int id) {
    return (TelelocationTemplate) this.loctlistData.get(id);
  }
}

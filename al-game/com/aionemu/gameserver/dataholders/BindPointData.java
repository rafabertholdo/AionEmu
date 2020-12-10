package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.BindPointTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bind_points")
@XmlAccessorType(XmlAccessType.FIELD)
public class BindPointData {
  @XmlElement(name = "bind_point")
  private List<BindPointTemplate> bplist;
  private TIntObjectHashMap<BindPointTemplate> bindplistData = new TIntObjectHashMap();
  private TIntObjectHashMap<BindPointTemplate> bindplistData2 = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (BindPointTemplate bind : this.bplist) {

      this.bindplistData.put(bind.getNpcId(), bind);
      this.bindplistData2.put(bind.getBindId(), bind);
    }
  }

  public int size() {
    return this.bindplistData.size();
  }

  public BindPointTemplate getBindPointTemplate(int npcId) {
    return (BindPointTemplate) this.bindplistData.get(npcId);
  }

  public BindPointTemplate getBindPointTemplate2(int bindPointId) {
    return (BindPointTemplate) this.bindplistData2.get(bindPointId);
  }
}

package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.GatherableTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gatherable_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class GatherableData {
  @XmlElement(name = "gatherable_template")
  private List<GatherableTemplate> gatherables;
  private TIntObjectHashMap<GatherableTemplate> gatherableData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (GatherableTemplate gatherable : this.gatherables) {
      this.gatherableData.put(gatherable.getTemplateId(), gatherable);
    }
    this.gatherables = null;
  }

  public int size() {
    return this.gatherableData.size();
  }

  public GatherableTemplate getGatherableTemplate(int id) {
    return (GatherableTemplate) this.gatherableData.get(id);
  }
}

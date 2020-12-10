package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.NpcTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;































@XmlRootElement(name = "npc_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class NpcData
{
  @XmlElement(name = "npc_template")
  private List<NpcTemplate> npcs;
  private TIntObjectHashMap<NpcTemplate> npcData = new TIntObjectHashMap();

  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (NpcTemplate npc : this.npcs)
    {
      this.npcData.put(npc.getTemplateId(), npc);
    }
    this.npcs.clear();
    this.npcs = null;
  }

  
  public int size() {
    return this.npcData.size();
  }









  
  public NpcTemplate getNpcTemplate(int id) {
    return (NpcTemplate)this.npcData.get(id);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\NpcData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

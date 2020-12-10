package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.npcskill.NpcSkillList;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;



























@XmlRootElement(name = "npc_skill_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class NpcSkillData
{
  @XmlElement(name = "npcskills")
  private List<NpcSkillList> npcSkills;
  private TIntObjectHashMap<NpcSkillList> npcSkillData = new TIntObjectHashMap();

  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (NpcSkillList npcSkill : this.npcSkills) {
      
      this.npcSkillData.put(npcSkill.getNpcId(), npcSkill);
      
      if (npcSkill.getNpcSkills() == null) {
        Logger.getLogger(NpcSkillData.class).error("NO SKILL");
      }
    } 
  }

  
  public int size() {
    return this.npcSkillData.size();
  }

  
  public NpcSkillList getNpcSkillList(int id) {
    return (NpcSkillList)this.npcSkillData.get(id);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\NpcSkillData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

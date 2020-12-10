package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "skill_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillData {
  @XmlElement(name = "skill_template")
  private List<SkillTemplate> skillTemplates;
  private TIntObjectHashMap<SkillTemplate> skillData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.skillData.clear();
    for (SkillTemplate skillTempalte : this.skillTemplates) {
      this.skillData.put(skillTempalte.getSkillId(), skillTempalte);
    }
  }

  public SkillTemplate getSkillTemplate(int skillId) {
    return (SkillTemplate) this.skillData.get(skillId);
  }

  public int size() {
    return this.skillData.size();
  }

  public List<SkillTemplate> getSkillTemplates() {
    return this.skillTemplates;
  }

  public void setSkillTemplates(List<SkillTemplate> skillTemplates) {
    this.skillTemplates = skillTemplates;
    afterUnmarshal(null, null);
  }
}

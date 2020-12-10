package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.petskill.PetSkillTemplate;
import gnu.trove.TIntIntHashMap;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pet_skill_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class PetSkillData {
  @XmlElement(name = "pet_skill")
  private List<PetSkillTemplate> petSkills;
  private TIntObjectHashMap<TIntIntHashMap> petSkillData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (PetSkillTemplate petSkill : this.petSkills) {

      TIntIntHashMap orderSkillMap = (TIntIntHashMap) this.petSkillData.get(petSkill.getOrderSkill());
      if (orderSkillMap == null) {

        orderSkillMap = new TIntIntHashMap();
        this.petSkillData.put(petSkill.getOrderSkill(), orderSkillMap);
      }

      orderSkillMap.put(petSkill.getPetId(), petSkill.getSkillId());
    }
  }

  public int size() {
    return this.petSkillData.size();
  }

  public int getPetOrderSkill(int orderSkill, int petNpcId) {
    return ((TIntIntHashMap) this.petSkillData.get(orderSkill)).get(petNpcId);
  }
}

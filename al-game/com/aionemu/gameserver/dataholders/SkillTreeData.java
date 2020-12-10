package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.skillengine.model.learn.SkillClass;
import com.aionemu.gameserver.skillengine.model.learn.SkillLearnTemplate;
import com.aionemu.gameserver.skillengine.model.learn.SkillRace;
import gnu.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

























@XmlRootElement(name = "skill_tree")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillTreeData
{
  @XmlElement(name = "skill")
  private List<SkillLearnTemplate> skillTemplates;
  private final TIntObjectHashMap<ArrayList<SkillLearnTemplate>> templates = new TIntObjectHashMap();

  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (SkillLearnTemplate template : this.skillTemplates)
    {
      addTemplate(template);
    }
    this.skillTemplates = null;
  }

  
  private void addTemplate(SkillLearnTemplate template) {
    SkillRace race = template.getRace();
    if (race == null) {
      race = SkillRace.ALL;
    }
    int hash = makeHash(template.getClassId().ordinal(), race.ordinal(), template.getMinLevel());
    ArrayList<SkillLearnTemplate> value = (ArrayList<SkillLearnTemplate>)this.templates.get(hash);
    if (value == null) {
      
      value = new ArrayList<SkillLearnTemplate>();
      this.templates.put(hash, value);
    } 
    
    value.add(template);
  }




  
  public TIntObjectHashMap<ArrayList<SkillLearnTemplate>> getTemplates() {
    return this.templates;
  }












  
  public SkillLearnTemplate[] getTemplatesFor(PlayerClass playerClass, int level, Race race) {
    List<SkillLearnTemplate> newSkills = new ArrayList<SkillLearnTemplate>();
    
    List<SkillLearnTemplate> classRaceSpecificTemplates = (List<SkillLearnTemplate>)this.templates.get(makeHash(playerClass.ordinal(), race.ordinal(), level));
    
    List<SkillLearnTemplate> classSpecificTemplates = (List<SkillLearnTemplate>)this.templates.get(makeHash(playerClass.ordinal(), SkillRace.ALL.ordinal(), level));
    
    List<SkillLearnTemplate> generalTemplates = (List<SkillLearnTemplate>)this.templates.get(makeHash(SkillClass.ALL.ordinal(), SkillRace.ALL.ordinal(), level));

    
    if (classRaceSpecificTemplates != null)
      newSkills.addAll(classRaceSpecificTemplates); 
    if (classSpecificTemplates != null)
      newSkills.addAll(classSpecificTemplates); 
    if (generalTemplates != null) {
      newSkills.addAll(generalTemplates);
    }
    return newSkills.<SkillLearnTemplate>toArray(new SkillLearnTemplate[newSkills.size()]);
  }

  
  public int size() {
    int size = 0;
    for (int arr$[] = this.templates.keys(), len$ = arr$.length, i$ = 0; i$ < len$; ) { Integer key = Integer.valueOf(arr$[i$]);
      size += ((ArrayList)this.templates.get(key.intValue())).size(); i$++; }
     return size;
  }

  
  private static int makeHash(int classId, int race, int level) {
    int result = classId << 8;
    result = (result | race) << 8;
    return result | level;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\SkillTreeData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

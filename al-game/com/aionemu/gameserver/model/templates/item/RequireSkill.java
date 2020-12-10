package com.aionemu.gameserver.model.templates.item;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequireSkill", propOrder = {"skillId"})
public class RequireSkill
{
  @XmlElement(type = Integer.class)
  protected List<Integer> skillId;
  @XmlAttribute
  protected Integer skilllvl;
  
  public List<Integer> getSkillId() {
    if (this.skillId == null) {
      this.skillId = new ArrayList<Integer>();
    }
    return this.skillId;
  }








  
  public Integer getSkilllvl() {
    return this.skilllvl;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\RequireSkill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

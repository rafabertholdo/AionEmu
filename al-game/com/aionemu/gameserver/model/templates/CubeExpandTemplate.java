package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.templates.expand.Expand;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cube_npc")
@XmlAccessorType(XmlAccessType.FIELD)
public class CubeExpandTemplate {
  @XmlElement(name = "expand", required = true)
  protected List<Expand> cubeExpands;
  @XmlAttribute(name = "id", required = true)
  private int Id;
  @XmlAttribute(name = "name", required = true)
  private String name = "";

  public String getName() {
    return this.name;
  }

  public int getNpcId() {
    return this.Id;
  }

  public boolean contains(int level) {
    for (Expand expand : this.cubeExpands) {

      if (expand.getLevel() == level)
        return true;
    }
    return false;
  }

  public Expand get(int level) {
    for (Expand expand : this.cubeExpands) {

      if (expand.getLevel() == level)
        return expand;
    }
    return null;
  }
}

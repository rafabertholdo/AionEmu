package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.templates.gather.Materials;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gatherable_template")
@XmlAccessorType(XmlAccessType.FIELD)
public class GatherableTemplate extends VisibleObjectTemplate {
  @XmlElement(required = true)
  protected Materials materials;
  @XmlAttribute
  protected int aerialAdj;
  @XmlAttribute
  protected int failureAdj;
  @XmlAttribute
  protected int successAdj;
  @XmlAttribute
  protected int harvestSkill;
  @XmlAttribute
  protected int skillLevel;
  @XmlAttribute
  protected int harvestCount;
  @XmlAttribute
  protected String sourceType;
  @XmlAttribute
  protected int nameId;
  @XmlAttribute
  protected String name;
  @XmlAttribute
  protected String desc;
  @XmlAttribute
  protected int id;

  public Materials getMaterials() {
    return this.materials;
  }

  public int getTemplateId() {
    return this.id;
  }

  public int getAerialAdj() {
    return this.aerialAdj;
  }

  public int getFailureAdj() {
    return this.failureAdj;
  }

  public int getSuccessAdj() {
    return this.successAdj;
  }

  public int getHarvestSkill() {
    return this.harvestSkill;
  }

  public int getSkillLevel() {
    return this.skillLevel;
  }

  public int getHarvestCount() {
    return this.harvestCount;
  }

  public String getSourceType() {
    return this.sourceType;
  }

  public String getName() {
    return this.name;
  }

  public int getNameId() {
    return this.nameId;
  }

  public String getDesc() {
    return this.desc;
  }
}

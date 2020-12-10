package com.aionemu.gameserver.questEngine.handlers.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonsterInfo")
public class MonsterInfo {
  @XmlAttribute(name = "var_id", required = true)
  protected int varId;
  @XmlAttribute(name = "min_var_value")
  protected Integer minVarValue;
  @XmlAttribute(name = "max_kill", required = true)
  protected int maxKill;
  @XmlAttribute(name = "npc_id", required = true)
  protected int npcId;

  public int getVarId() {
    return this.varId;
  }

  public Integer getMinVarValue() {
    return this.minVarValue;
  }

  public int getMaxKill() {
    return this.maxKill;
  }

  public int getNpcId() {
    return this.npcId;
  }
}

package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.NpcType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.items.NpcEquippedGear;
import com.aionemu.gameserver.model.templates.stats.KiskStatsTemplate;
import com.aionemu.gameserver.model.templates.stats.NpcRank;
import com.aionemu.gameserver.model.templates.stats.NpcStatsTemplate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "npc_template")
public class NpcTemplate extends VisibleObjectTemplate {
  private int npcId;
  @XmlAttribute(name = "level", required = true)
  private byte level;
  @XmlAttribute(name = "name_id", required = true)
  private int nameId;
  @XmlAttribute(name = "title_id")
  private int titleId;
  @XmlAttribute(name = "name")
  private String name;
  @XmlAttribute(name = "height")
  private float height = 1.0F;
  @XmlAttribute(name = "talking_distance")
  private int talkingDistance = 2;

  @XmlAttribute(name = "npc_type", required = true)
  private NpcType npcType;
  @XmlElement(name = "stats")
  private NpcStatsTemplate statsTemplate;
  @XmlElement(name = "equipment")
  private NpcEquippedGear equipment;
  @XmlElement(name = "kisk_stats")
  private KiskStatsTemplate kiskStatsTemplate;
  @XmlElement(name = "ammo_speed")
  private int ammoSpeed = 0;

  @XmlAttribute(name = "rank")
  private NpcRank rank;

  @XmlAttribute(name = "srange")
  private int aggrorange;

  @XmlAttribute(name = "arange")
  private int attackRange;

  @XmlAttribute(name = "srange")
  private int attackRate;
  @XmlAttribute(name = "hpgauge")
  private int hpGauge;
  @XmlAttribute(name = "tribe")
  private String tribe;
  @XmlAttribute
  private Race race;
  @XmlAttribute
  private int state;

  public int getTemplateId() {
    return this.npcId;
  }

  public int getNameId() {
    return this.nameId;
  }

  public int getTitleId() {
    return this.titleId;
  }

  public String getName() {
    return this.name;
  }

  public float getHeight() {
    return this.height;
  }

  public NpcType getNpcType() {
    return this.npcType;
  }

  public NpcEquippedGear getEquipment() {
    return this.equipment;
  }

  public byte getLevel() {
    return this.level;
  }

  public NpcStatsTemplate getStatsTemplate() {
    return this.statsTemplate;
  }

  public void setStatsTemplate(NpcStatsTemplate statsTemplate) {
    this.statsTemplate = statsTemplate;
  }

  public KiskStatsTemplate getKiskStatsTemplate() {
    return this.kiskStatsTemplate;
  }

  public String getTribe() {
    return this.tribe;
  }

  public String toString() {
    return "Npc Template id: " + this.npcId + " name: " + this.name;
  }

  @XmlID
  @XmlAttribute(name = "npc_id", required = true)
  private void setXmlUid(String uid) {
    this.npcId = Integer.parseInt(uid);
  }

  public NpcRank getRank() {
    return this.rank;
  }

  public int getAggroRange() {
    return this.aggrorange;
  }

  public int getAttackRange() {
    return this.attackRange;
  }

  public int getAttackRate() {
    return this.attackRate;
  }

  public int getHpGauge() {
    return this.hpGauge;
  }

  public Race getRace() {
    return this.race;
  }

  public int getState() {
    return this.state;
  }
}

package com.aionemu.gameserver.model.templates.teleport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teleporter_template")
@XmlAccessorType(XmlAccessType.NONE)
public class TeleporterTemplate {
  @XmlAttribute(name = "npc_id", required = true)
  private int npcId;
  @XmlAttribute(name = "name", required = true)
  private String name = "";

  @XmlAttribute(name = "teleportId", required = true)
  private int teleportId = 0;

  @XmlAttribute(name = "type", required = true)
  private TeleportType type;

  @XmlElement(name = "locations")
  private TeleLocIdData teleLocIdData;

  public int getNpcId() {
    return this.npcId;
  }

  public String getName() {
    return this.name;
  }

  public int getTeleportId() {
    return this.teleportId;
  }

  public TeleportType getType() {
    return this.type;
  }

  public TeleLocIdData getTeleLocIdData() {
    return this.teleLocIdData;
  }
}

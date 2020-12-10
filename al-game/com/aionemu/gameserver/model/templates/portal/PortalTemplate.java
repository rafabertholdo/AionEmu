package com.aionemu.gameserver.model.templates.portal;

import com.aionemu.gameserver.model.Race;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Portal")
public class PortalTemplate
{
  @XmlAttribute(name = "npcid")
  protected int npcId;
  @XmlAttribute(name = "name")
  protected String name;
  @XmlAttribute(name = "instance")
  protected boolean instance;
  @XmlAttribute(name = "minlevel")
  protected int minLevel;
  @XmlAttribute(name = "maxlevel")
  protected int maxLevel;
  @XmlAttribute(name = "group")
  protected boolean group;
  @XmlAttribute(name = "race")
  protected Race race;
  @XmlElement(name = "entrypoint")
  protected List<EntryPoint> entryPoint;
  @XmlElement(name = "exitpoint")
  protected ExitPoint exitPoint;
  @XmlElement(name = "portalitem")
  protected List<PortalItem> portalItem;
  @XmlAttribute(name = "titleid")
  protected int IdTitle;
  
  public int getNpcId() {
    return this.npcId;
  }




  
  public String getName() {
    return this.name;
  }




  
  public boolean isInstance() {
    return this.instance;
  }




  
  public int getMinLevel() {
    return this.minLevel;
  }




  
  public int getMaxLevel() {
    return this.maxLevel;
  }




  
  public boolean isGroup() {
    return this.group;
  }




  
  public Race getRace() {
    return this.race;
  }




  
  public List<EntryPoint> getEntryPoint() {
    return this.entryPoint;
  }




  
  public ExitPoint getExitPoint() {
    return this.exitPoint;
  }




  
  public List<PortalItem> getPortalItem() {
    return this.portalItem;
  }




  
  public int getIdTitle() {
    return this.IdTitle;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\portal\PortalTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

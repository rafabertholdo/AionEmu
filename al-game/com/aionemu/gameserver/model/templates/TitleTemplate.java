package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
import java.util.TreeSet;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;



























@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "title_templates")
public class TitleTemplate
{
  @XmlAttribute(name = "id", required = true)
  @XmlID
  private String id;
  @XmlElement(name = "modifiers", required = false)
  protected ModifiersTemplate modifiers;
  @XmlAttribute(name = "race", required = true)
  private int race;
  private int titleId;
  
  public int getTitleId() {
    return this.titleId;
  }

  
  public int getRace() {
    return this.race;
  }

  
  public TreeSet<StatModifier> getModifiers() {
    if (this.modifiers != null)
    {
      return this.modifiers.getModifiers();
    }

    
    return null;
  }


  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.titleId = Integer.parseInt(this.id);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\TitleTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

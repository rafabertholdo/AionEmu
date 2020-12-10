package com.aionemu.gameserver.model.templates.tribe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Friend")
public class FriendlyRelations
{
  @XmlElement(required = true)
  protected List<String> to;
  
  public List<String> getTo() {
    if (this.to == null) {
      this.to = new ArrayList<String>();
    }
    return this.to;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\tribe\FriendlyRelations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

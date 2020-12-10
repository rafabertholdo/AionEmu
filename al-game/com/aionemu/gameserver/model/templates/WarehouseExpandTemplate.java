package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.templates.expand.Expand;
import com.aionemu.gameserver.utils.Util;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;





























@XmlRootElement(name = "warehouse_npc")
@XmlAccessorType(XmlAccessType.FIELD)
public class WarehouseExpandTemplate
{
  @XmlElement(name = "expand", required = true)
  protected List<Expand> warehouseExpands;
  @XmlAttribute(name = "id", required = true)
  protected int id;
  @XmlAttribute(name = "name", required = true)
  protected String name = "";


  
  public int getNpcId() {
    return this.id;
  }




  
  public List<Expand> getWarehouseExpand() {
    return this.warehouseExpands;
  }







  
  public String getName() {
    return Util.convertName(this.name);
  }





  
  public boolean contains(int level) {
    for (Expand expand : this.warehouseExpands) {
      
      if (expand.getLevel() == level)
        return true; 
    } 
    return false;
  }





  
  public Expand get(int level) {
    for (Expand expand : this.warehouseExpands) {
      
      if (expand.getLevel() == level)
        return expand; 
    } 
    return null;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\WarehouseExpandTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

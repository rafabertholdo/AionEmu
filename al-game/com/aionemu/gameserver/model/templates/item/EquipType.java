package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;






















@XmlType(name = "equipType")
@XmlEnum
public enum EquipType
{
  ARMOR,
  WEAPON,
  STIGMA,
  NONE;
  
  public String value() {
    return name();
  }
  
  public static EquipType fromValue(String v) {
    return valueOf(v);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\EquipType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

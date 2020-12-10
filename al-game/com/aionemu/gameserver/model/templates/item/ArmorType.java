package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;





















@XmlType(name = "armor_type")
@XmlEnum
public enum ArmorType
{
  CHAIN(new int[] { 6, 13 }),
  CLOTHES(new int[] { 4 }),
  LEATHER(new int[] { 5, 12 }),
  PLATE(new int[] { 18 }),
  ROBE(new int[] { 67, 70 }),
  SHARD(new int[0]),
  SHIELD(new int[] { 7, 14 }),
  ARROW(new int[0]);
  
  private int[] requiredSkills;

  
  ArmorType(int[] requiredSkills) {
    this.requiredSkills = requiredSkills;
  }

  
  public int[] getRequiredSkills() {
    return this.requiredSkills;
  }




  
  public int getMask() {
    return 1 << ordinal();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\ArmorType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

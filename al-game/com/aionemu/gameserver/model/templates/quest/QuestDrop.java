package com.aionemu.gameserver.model.templates.quest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestDrop")
public class QuestDrop
{
  @XmlAttribute(name = "npc_id")
  protected Integer npcId;
  @XmlAttribute(name = "item_id")
  protected Integer itemId;
  @XmlAttribute
  protected Integer chance;
  @XmlAttribute(name = "drop_each_member")
  protected Boolean dropEachMember;
  @XmlTransient
  protected Integer questId;
  
  public Integer getNpcId() {
    return this.npcId;
  }







  
  public Integer getItemId() {
    return this.itemId;
  }







  
  public Integer getChance() {
    return this.chance;
  }







  
  public Boolean isDropEachMember() {
    return this.dropEachMember;
  }




  
  public Integer getQuestId() {
    return this.questId;
  }





  
  public void setQuestId(Integer questId) {
    this.questId = questId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\quest\QuestDrop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

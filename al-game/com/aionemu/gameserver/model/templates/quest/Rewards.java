package com.aionemu.gameserver.model.templates.quest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;











































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rewards", propOrder = {"selectableRewardItem", "rewardItem"})
public class Rewards
{
  @XmlElement(name = "selectable_reward_item")
  protected List<QuestItems> selectableRewardItem;
  @XmlElement(name = "reward_item")
  protected List<QuestItems> rewardItem;
  @XmlAttribute
  protected Integer gold;
  @XmlAttribute
  protected Integer exp;
  @XmlAttribute(name = "reward_abyss_point")
  protected Integer rewardAbyssPoint;
  @XmlAttribute
  protected Integer title;
  @XmlAttribute(name = "extend_inventory")
  protected Integer extendInventory;
  @XmlAttribute(name = "extend_stigma")
  protected Integer extendStigma;
  
  public List<QuestItems> getSelectableRewardItem() {
    if (this.selectableRewardItem == null) {
      this.selectableRewardItem = new ArrayList<QuestItems>();
    }
    return this.selectableRewardItem;
  }






















  
  public List<QuestItems> getRewardItem() {
    if (this.rewardItem == null) {
      this.rewardItem = new ArrayList<QuestItems>();
    }
    return this.rewardItem;
  }








  
  public Integer getGold() {
    return this.gold;
  }








  
  public Integer getExp() {
    return this.exp;
  }








  
  public Integer getRewardAbyssPoint() {
    return this.rewardAbyssPoint;
  }








  
  public Integer getTitle() {
    return this.title;
  }




  
  public Integer getExtendInventory() {
    return this.extendInventory;
  }




  
  public Integer getExtendStigma() {
    return this.extendStigma;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\quest\Rewards.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

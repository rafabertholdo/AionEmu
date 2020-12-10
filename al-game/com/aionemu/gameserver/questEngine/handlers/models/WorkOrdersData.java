package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.handlers.template.WorkOrders;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;













































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrdersData", propOrder = {"giveComponent"})
public class WorkOrdersData
  extends QuestScriptData
{
  @XmlElement(name = "give_component", required = true)
  protected List<QuestItems> giveComponent;
  @XmlAttribute(name = "start_npc_id", required = true)
  protected int startNpcId;
  @XmlAttribute(name = "recipe_id", required = true)
  protected int recipeId;
  
  public List<QuestItems> getGiveComponent() {
    if (this.giveComponent == null)
    {
      this.giveComponent = new ArrayList<QuestItems>();
    }
    return this.giveComponent;
  }





  
  public int getStartNpcId() {
    return this.startNpcId;
  }





  
  public int getRecipeId() {
    return this.recipeId;
  }






  
  public void register(QuestEngine questEngine) {
    questEngine.addQuestHandler((QuestHandler)new WorkOrders(this));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\WorkOrdersData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

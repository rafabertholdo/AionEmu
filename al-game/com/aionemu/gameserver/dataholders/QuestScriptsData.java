package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.questEngine.handlers.models.ItemCollectingData;
import com.aionemu.gameserver.questEngine.handlers.models.MonsterHuntData;
import com.aionemu.gameserver.questEngine.handlers.models.QuestScriptData;
import com.aionemu.gameserver.questEngine.handlers.models.ReportToData;
import com.aionemu.gameserver.questEngine.handlers.models.WorkOrdersData;
import com.aionemu.gameserver.questEngine.handlers.models.XmlQuestData;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
































@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "quest_scripts")
public class QuestScriptsData
{
  @XmlElements({@XmlElement(name = "report_to", type = ReportToData.class), @XmlElement(name = "monster_hunt", type = MonsterHuntData.class), @XmlElement(name = "xml_quest", type = XmlQuestData.class), @XmlElement(name = "item_collecting", type = ItemCollectingData.class), @XmlElement(name = "work_order", type = WorkOrdersData.class)})
  protected List<QuestScriptData> data;
  
  public List<QuestScriptData> getData() {
    return this.data;
  }




  
  public void setData(List<QuestScriptData> data) {
    this.data = data;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\QuestScriptsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

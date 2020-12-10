package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.QuestTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "quests")
public class QuestsData {
  @XmlElement(name = "quest", required = true)
  protected List<QuestTemplate> questsData;
  private TIntObjectHashMap<QuestTemplate> questData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.questData.clear();
    for (QuestTemplate quest : this.questsData) {
      this.questData.put(quest.getId(), quest);
    }
  }

  public QuestTemplate getQuestById(int id) {
    return (QuestTemplate) this.questData.get(id);
  }

  public int size() {
    return this.questData.size();
  }

  public List<QuestTemplate> getQuestsData() {
    return this.questsData;
  }

  public void setQuestsData(List<QuestTemplate> questsData) {
    this.questsData = questsData;
    afterUnmarshal(null, null);
  }
}

package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnKillEvent;
import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnTalkEvent;
import com.aionemu.gameserver.questEngine.handlers.template.XmlQuest;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XmlQuest", propOrder = { "onTalkEvent", "onKillEvent" })
public class XmlQuestData extends QuestScriptData {
  @XmlElement(name = "on_talk_event")
  protected List<OnTalkEvent> onTalkEvent;
  @XmlElement(name = "on_kill_event")
  protected List<OnKillEvent> onKillEvent;
  @XmlAttribute(name = "start_npc_id")
  protected Integer startNpcId;
  @XmlAttribute(name = "end_npc_id")
  protected Integer endNpcId;

  public List<OnTalkEvent> getOnTalkEvent() {
    if (this.onTalkEvent == null) {
      this.onTalkEvent = new ArrayList<OnTalkEvent>();
    }
    return this.onTalkEvent;
  }

  public List<OnKillEvent> getOnKillEvent() {
    if (this.onKillEvent == null) {
      this.onKillEvent = new ArrayList<OnKillEvent>();
    }
    return this.onKillEvent;
  }

  public Integer getStartNpcId() {
    return this.startNpcId;
  }

  public Integer getEndNpcId() {
    return this.endNpcId;
  }

  public void register(QuestEngine questEngine) {
    questEngine.addQuestHandler((QuestHandler) new XmlQuest(this));
  }
}

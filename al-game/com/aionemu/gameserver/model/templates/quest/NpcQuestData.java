package com.aionemu.gameserver.model.templates.quest;

import java.util.ArrayList;
import java.util.List;

public class NpcQuestData {
  private final List<Integer> onQuestStart = new ArrayList<Integer>();
  private final List<Integer> onKillEvent = new ArrayList<Integer>();
  private final List<Integer> onTalkEvent = new ArrayList<Integer>();
  private final List<Integer> onAttackEvent = new ArrayList<Integer>();

  public void addOnQuestStart(int questId) {
    if (!this.onQuestStart.contains(Integer.valueOf(questId))) {
      this.onQuestStart.add(Integer.valueOf(questId));
    }
  }

  public List<Integer> getOnQuestStart() {
    return this.onQuestStart;
  }

  public void addOnAttackEvent(int questId) {
    if (!this.onAttackEvent.contains(Integer.valueOf(questId))) {
      this.onAttackEvent.add(Integer.valueOf(questId));
    }
  }

  public List<Integer> getOnAttackEvent() {
    return this.onAttackEvent;
  }

  public void addOnKillEvent(int questId) {
    if (!this.onKillEvent.contains(Integer.valueOf(questId))) {
      this.onKillEvent.add(Integer.valueOf(questId));
    }
  }

  public List<Integer> getOnKillEvent() {
    return this.onKillEvent;
  }

  public void addOnTalkEvent(int questId) {
    if (!this.onTalkEvent.contains(Integer.valueOf(questId))) {
      this.onTalkEvent.add(Integer.valueOf(questId));
    }
  }

  public List<Integer> getOnTalkEvent() {
    return this.onTalkEvent;
  }
}

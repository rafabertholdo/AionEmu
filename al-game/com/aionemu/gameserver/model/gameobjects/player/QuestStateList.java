package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.questEngine.model.QuestState;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class QuestStateList {
  private static final Logger log = Logger.getLogger(QuestStateList.class);

  private final SortedMap<Integer, QuestState> _quests = new TreeMap<Integer, QuestState>();

  public synchronized boolean addQuest(int questId, QuestState questState) {
    if (this._quests.containsKey(Integer.valueOf(questId))) {

      log.warn("Duplicate quest. ");
      return false;
    }
    this._quests.put(Integer.valueOf(questId), questState);
    return true;
  }

  public synchronized boolean removeQuest(int questId) {
    if (this._quests.containsKey(Integer.valueOf(questId))) {

      this._quests.remove(Integer.valueOf(questId));
      return true;
    }
    return false;
  }

  public QuestState getQuestState(int questId) {
    return this._quests.get(Integer.valueOf(questId));
  }

  public Collection<QuestState> getAllQuestState() {
    return this._quests.values();
  }
}

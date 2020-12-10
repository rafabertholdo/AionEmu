package com.aionemu.gameserver.questEngine;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
import com.aionemu.gameserver.GameServerError;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.QuestScriptsData;
import com.aionemu.gameserver.dataholders.QuestsData;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.model.templates.quest.NpcQuestData;
import com.aionemu.gameserver.model.templates.quest.QuestDrop;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.handlers.QuestHandlerLoader;
import com.aionemu.gameserver.questEngine.handlers.models.QuestScriptData;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.world.zone.ZoneName;
import gnu.trove.TIntArrayList;
import gnu.trove.TIntObjectHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class QuestEngine {
  private static final Logger log = Logger.getLogger(QuestEngine.class);

  private static final FastMap<Integer, QuestHandler> questHandlers = new FastMap();

  private static ScriptManager scriptManager = new ScriptManager();

  public static final File QUEST_DESCRIPTOR_FILE = new File("./data/scripts/system/quest_handlers.xml");

  private QuestsData questData = DataManager.QUEST_DATA;

  private QuestScriptsData questScriptsData = DataManager.QUEST_SCRIPTS_DATA;

  private TIntObjectHashMap<NpcQuestData> npcQuestData = new TIntObjectHashMap();
  private TIntObjectHashMap<TIntArrayList> questItemIds = new TIntObjectHashMap();
  private TIntArrayList questLvlUp = new TIntArrayList();
  private FastMap<ZoneName, TIntArrayList> questEnterZone = new FastMap();
  private TIntObjectHashMap<TIntArrayList> questMovieEndIds = new TIntObjectHashMap();
  private TIntArrayList questOnDie = new TIntArrayList();
  private TIntArrayList questOnEnterWorld = new TIntArrayList();
  private TIntObjectHashMap<List<QuestDrop>> questDrop = new TIntObjectHashMap();
  private TIntArrayList questOnQuestFinish = new TIntArrayList();
  private List<Integer> questOnQuestTimerEnd = new ArrayList<Integer>();
  private TIntArrayList questOnQuestAbort = new TIntArrayList();

  private final NpcQuestData emptyNpcQuestData = new NpcQuestData();

  public static final QuestEngine getInstance() {
    return SingletonHolder.instance;
  }

  private QuestEngine() {
    log.info("Initializing QuestEngine");
  }

  public void load() {
    for (QuestTemplate data : this.questData.getQuestsData()) {

      for (QuestDrop drop : data.getQuestDrop()) {

        drop.setQuestId(Integer.valueOf(data.getId()));
        setQuestDrop(drop.getNpcId().intValue()).add(drop);
      }
    }

    scriptManager = new ScriptManager();
    scriptManager.setGlobalClassListener((ClassListener) new QuestHandlerLoader());

    try {
      scriptManager.load(QUEST_DESCRIPTOR_FILE);
    } catch (Exception e) {

      throw new GameServerError("Can't initialize quest handlers.", e);
    }
    for (QuestScriptData data : this.questScriptsData.getData()) {
      data.register(this);
    }

    log.info("Loaded " + questHandlers.size() + " quest handler.");
  }

  public void shutdown() {
    scriptManager.shutdown();
    clear();
    scriptManager = null;
    log.info("Quests are shutdown...");
  }

  public boolean onDialog(QuestEnv env) {
    QuestHandler questHandler = null;
    if (env.getQuestId().intValue() != 0) {

      questHandler = getQuestHandlerByQuestId(env.getQuestId().intValue());
      if (questHandler != null && questHandler.onDialogEvent(env)) {
        return true;
      }
    } else {

      Npc npc = (Npc) env.getVisibleObject();
      for (Iterator<Integer> i$ = getNpcQuestData((npc == null) ? 0 : npc.getNpcId()).getOnTalkEvent().iterator(); i$
          .hasNext();) {
        int questId = ((Integer) i$.next()).intValue();

        questHandler = getQuestHandlerByQuestId(questId);
        if (questHandler != null && questHandler.onDialogEvent(env))
          return true;
      }

    }
    return false;
  }

  public boolean onKill(QuestEnv env) {
    Npc npc = (Npc) env.getVisibleObject();
    for (Iterator<Integer> i$ = getNpcQuestData(npc.getNpcId()).getOnKillEvent().iterator(); i$.hasNext();) {
      int questId = ((Integer) i$.next()).intValue();

      QuestHandler questHandler = getQuestHandlerByQuestId(questId);
      if (questHandler != null && questHandler.onKillEvent(env))
        return true;
    }

    return false;
  }

  public boolean onAttack(QuestEnv env) {
    Npc npc = (Npc) env.getVisibleObject();

    for (Iterator<Integer> i$ = getNpcQuestData(npc.getNpcId()).getOnAttackEvent().iterator(); i$.hasNext();) {
      int questId = ((Integer) i$.next()).intValue();

      QuestHandler questHandler = getQuestHandlerByQuestId(questId);
      if (questHandler != null && questHandler.onAttackEvent(env))
        return true;
    }

    return false;
  }

  public void onLvlUp(QuestEnv env) {
    for (int index = 0; index < this.questLvlUp.size(); index++) {

      QuestHandler questHandler = getQuestHandlerByQuestId(this.questLvlUp.get(index));
      if (questHandler != null) {
        questHandler.onLvlUpEvent(env);
      }
    }
  }

  public void onDie(QuestEnv env) {
    for (int index = 0; index < this.questOnDie.size(); index++) {

      QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnDie.get(index));
      if (questHandler != null) {
        questHandler.onDieEvent(env);
      }
    }
  }

  public void onEnterWorld(QuestEnv env) {
    for (int index = 0; index < this.questOnEnterWorld.size(); index++) {

      QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnEnterWorld.get(index));
      if (questHandler != null) {
        questHandler.onEnterWorldEvent(env);
      }
    }
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    TIntArrayList lists = getQuestItemIds(item.getItemTemplate().getTemplateId());
    for (int index = 0; index < lists.size(); index++) {

      QuestHandler questHandler = getQuestHandlerByQuestId(lists.get(index));
      if (questHandler != null && questHandler.onItemUseEvent(env, item))
        return true;
    }
    return false;
  }

  public boolean onEnterZone(QuestEnv env, ZoneName zoneName) {
    TIntArrayList lists = getQuestEnterZone(zoneName);
    for (int index = 0; index < lists.size(); index++) {

      QuestHandler questHandler = getQuestHandlerByQuestId(lists.get(index));
      if (questHandler != null && questHandler.onEnterZoneEvent(env, zoneName))
        return true;
    }
    return false;
  }

  public boolean onMovieEnd(QuestEnv env, int movieId) {
    TIntArrayList lists = getQuestMovieEndIds(movieId);
    for (int index = 0; index < lists.size(); index++) {

      env.setQuestId(Integer.valueOf(lists.get(index)));
      QuestHandler questHandler = getQuestHandlerByQuestId(env.getQuestId().intValue());
      if (questHandler != null && questHandler.onMovieEndEvent(env, movieId))
        return true;
    }
    return false;
  }

  public void onQuestFinish(QuestEnv env) {
    for (int index = 0; index < this.questOnQuestFinish.size(); index++) {

      QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnQuestFinish.get(index));
      if (questHandler != null) {
        questHandler.onQuestFinishEvent(env);
      }
    }
  }

  public void onQuestAbort(QuestEnv env) {
    for (int index = 0; index < this.questOnQuestAbort.size(); index++) {

      QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnQuestFinish.get(index));
      if (questHandler != null) {
        questHandler.onQuestAbortEvent(env);
      }
    }
  }

  public void onQuestTimerEnd(QuestEnv env) {
    for (Iterator<Integer> i$ = this.questOnQuestTimerEnd.iterator(); i$.hasNext();) {
      int questId = ((Integer) i$.next()).intValue();

      QuestHandler questHandler = getQuestHandlerByQuestId(questId);
      if (questHandler != null) {
        questHandler.onQuestTimerEndEvent(env);
      }
    }

  }

  public boolean deleteQuest(Player player, int questId) {
    if (this.questData.getQuestById(questId).isCannotGiveup()) {
      return false;
    }
    QuestState qs = player.getQuestStateList().getQuestState(questId);

    if (qs == null) {
      return false;
    }
    qs.setStatus(QuestStatus.NONE);

    onQuestAbort(new QuestEnv(null, player, Integer.valueOf(questId), Integer.valueOf(0)));

    QuestWorkItems qwi = this.questData.getQuestById(questId).getQuestWorkItems();

    if (qwi != null) {

      long count = 0L;
      for (QuestItems qi : qwi.getQuestWorkItem()) {

        if (qi != null) {
          ItemService.decreaseItemCountByItemId(player, qi.getItemId().intValue(), count);
        }
      }
    }

    return true;
  }

  public NpcQuestData getNpcQuestData(int npcTemplateId) {
    if (this.npcQuestData.containsKey(npcTemplateId)) {
      return (NpcQuestData) this.npcQuestData.get(npcTemplateId);
    }
    return this.emptyNpcQuestData;
  }

  public NpcQuestData setNpcQuestData(int npcTemplateId) {
    if (!this.npcQuestData.containsKey(npcTemplateId)) {
      this.npcQuestData.put(npcTemplateId, new NpcQuestData());
    }
    return (NpcQuestData) this.npcQuestData.get(npcTemplateId);
  }

  public TIntArrayList getQuestItemIds(int itemId) {
    if (this.questItemIds.containsKey(itemId)) {
      return (TIntArrayList) this.questItemIds.get(itemId);
    }
    return new TIntArrayList();
  }

  public TIntArrayList setQuestItemIds(int itemId) {
    if (!this.questItemIds.containsKey(itemId)) {
      this.questItemIds.put(itemId, new TIntArrayList());
    }
    return (TIntArrayList) this.questItemIds.get(itemId);
  }

  public List<QuestDrop> setQuestDrop(int npcId) {
    if (!this.questDrop.containsKey(npcId)) {
      this.questDrop.put(npcId, new ArrayList());
    }
    return (List<QuestDrop>) this.questDrop.get(npcId);
  }

  public List<QuestDrop> getQuestDrop(int npcId) {
    if (this.questDrop.containsKey(npcId)) {
      return (List<QuestDrop>) this.questDrop.get(npcId);
    }
    return new ArrayList<QuestDrop>();
  }

  public void addQuestLvlUp(int questId) {
    if (!this.questLvlUp.contains(questId)) {
      this.questLvlUp.add(questId);
    }
  }

  public void addOnEnterWorld(int questId) {
    if (!this.questOnEnterWorld.contains(questId)) {
      this.questOnEnterWorld.add(questId);
    }
  }

  public void addOnDie(int questId) {
    if (!this.questOnDie.contains(questId)) {
      this.questOnDie.add(questId);
    }
  }

  public TIntArrayList getQuestEnterZone(ZoneName zoneName) {
    if (this.questEnterZone.containsKey(zoneName)) {
      return (TIntArrayList) this.questEnterZone.get(zoneName);
    }
    return new TIntArrayList();
  }

  public TIntArrayList setQuestEnterZone(ZoneName zoneName) {
    if (!this.questEnterZone.containsKey(zoneName)) {
      this.questEnterZone.put(zoneName, new TIntArrayList());
    }
    return (TIntArrayList) this.questEnterZone.get(zoneName);
  }

  public TIntArrayList getQuestMovieEndIds(int moveId) {
    if (this.questMovieEndIds.containsKey(moveId)) {
      return (TIntArrayList) this.questMovieEndIds.get(moveId);
    }
    return new TIntArrayList();
  }

  public TIntArrayList setQuestMovieEndIds(int moveId) {
    if (!this.questMovieEndIds.containsKey(moveId)) {
      this.questMovieEndIds.put(moveId, new TIntArrayList());
    }
    return (TIntArrayList) this.questMovieEndIds.get(moveId);
  }

  public void addOnQuestFinish(int questId) {
    if (!this.questOnQuestFinish.contains(questId)) {
      this.questOnQuestFinish.add(questId);
    }
  }

  public void addOnQuestAbort(int questId) {
    if (!this.questOnQuestAbort.contains(questId)) {
      this.questOnQuestAbort.add(questId);
    }
  }

  public void addOnQuestTimerEnd(int questId) {
    if (!this.questOnQuestTimerEnd.contains(Integer.valueOf(questId))) {
      this.questOnQuestTimerEnd.add(Integer.valueOf(questId));
    }
  }

  public void clear() {
    this.npcQuestData.clear();
    this.questItemIds.clear();
    this.questLvlUp.clear();
    this.questOnEnterWorld.clear();
    this.questOnDie.clear();
    this.questEnterZone.clear();
    this.questMovieEndIds.clear();
    this.questDrop.clear();
    this.questOnQuestFinish.clear();
    this.questOnQuestTimerEnd.clear();
    questHandlers.clear();
  }

  public void addQuestHandler(QuestHandler questHandler) {
    questHandler.register();
    if (questHandlers.containsKey(questHandler.getQuestId()))
      log.warn("Duplicate quest: " + questHandler.getQuestId());
    questHandlers.put(questHandler.getQuestId(), questHandler);
  }

  private QuestHandler getQuestHandlerByQuestId(int questId) {
    return (QuestHandler) questHandlers.get(Integer.valueOf(questId));
  }

  private static class SingletonHolder {
    protected static final QuestEngine instance = new QuestEngine();
  }
}

/*     */ package com.aionemu.gameserver.questEngine;
/*     */ 
/*     */ import com.aionemu.commons.scripting.classlistener.ClassListener;
/*     */ import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
/*     */ import com.aionemu.gameserver.GameServerError;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.dataholders.QuestScriptsData;
/*     */ import com.aionemu.gameserver.dataholders.QuestsData;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.QuestTemplate;
/*     */ import com.aionemu.gameserver.model.templates.quest.NpcQuestData;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestDrop;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandlerLoader;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.QuestScriptData;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javolution.util.FastMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestEngine
/*     */ {
/*  58 */   private static final Logger log = Logger.getLogger(QuestEngine.class);
/*     */   
/*  60 */   private static final FastMap<Integer, QuestHandler> questHandlers = new FastMap();
/*     */   
/*  62 */   private static ScriptManager scriptManager = new ScriptManager();
/*     */   
/*  64 */   public static final File QUEST_DESCRIPTOR_FILE = new File("./data/scripts/system/quest_handlers.xml");
/*     */ 
/*     */   
/*  67 */   private QuestsData questData = DataManager.QUEST_DATA;
/*     */   
/*  69 */   private QuestScriptsData questScriptsData = DataManager.QUEST_SCRIPTS_DATA;
/*     */   
/*  71 */   private TIntObjectHashMap<NpcQuestData> npcQuestData = new TIntObjectHashMap();
/*  72 */   private TIntObjectHashMap<TIntArrayList> questItemIds = new TIntObjectHashMap();
/*  73 */   private TIntArrayList questLvlUp = new TIntArrayList();
/*  74 */   private FastMap<ZoneName, TIntArrayList> questEnterZone = new FastMap();
/*  75 */   private TIntObjectHashMap<TIntArrayList> questMovieEndIds = new TIntObjectHashMap();
/*  76 */   private TIntArrayList questOnDie = new TIntArrayList();
/*  77 */   private TIntArrayList questOnEnterWorld = new TIntArrayList();
/*  78 */   private TIntObjectHashMap<List<QuestDrop>> questDrop = new TIntObjectHashMap();
/*  79 */   private TIntArrayList questOnQuestFinish = new TIntArrayList();
/*  80 */   private List<Integer> questOnQuestTimerEnd = new ArrayList<Integer>();
/*  81 */   private TIntArrayList questOnQuestAbort = new TIntArrayList();
/*     */   
/*  83 */   private final NpcQuestData emptyNpcQuestData = new NpcQuestData();
/*     */ 
/*     */   
/*     */   public static final QuestEngine getInstance() {
/*  87 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private QuestEngine() {
/*  93 */     log.info("Initializing QuestEngine");
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  98 */     for (QuestTemplate data : this.questData.getQuestsData()) {
/*     */       
/* 100 */       for (QuestDrop drop : data.getQuestDrop()) {
/*     */         
/* 102 */         drop.setQuestId(Integer.valueOf(data.getId()));
/* 103 */         setQuestDrop(drop.getNpcId().intValue()).add(drop);
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     scriptManager = new ScriptManager();
/* 108 */     scriptManager.setGlobalClassListener((ClassListener)new QuestHandlerLoader());
/*     */ 
/*     */     
/*     */     try {
/* 112 */       scriptManager.load(QUEST_DESCRIPTOR_FILE);
/*     */     }
/* 114 */     catch (Exception e) {
/*     */       
/* 116 */       throw new GameServerError("Can't initialize quest handlers.", e);
/*     */     } 
/* 118 */     for (QuestScriptData data : this.questScriptsData.getData())
/*     */     {
/* 120 */       data.register(this);
/*     */     }
/*     */     
/* 123 */     log.info("Loaded " + questHandlers.size() + " quest handler.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 128 */     scriptManager.shutdown();
/* 129 */     clear();
/* 130 */     scriptManager = null;
/* 131 */     log.info("Quests are shutdown...");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialog(QuestEnv env) {
/* 136 */     QuestHandler questHandler = null;
/* 137 */     if (env.getQuestId().intValue() != 0) {
/*     */       
/* 139 */       questHandler = getQuestHandlerByQuestId(env.getQuestId().intValue());
/* 140 */       if (questHandler != null && 
/* 141 */         questHandler.onDialogEvent(env)) {
/* 142 */         return true;
/*     */       }
/*     */     } else {
/*     */       
/* 146 */       Npc npc = (Npc)env.getVisibleObject();
/* 147 */       for (Iterator<Integer> i$ = getNpcQuestData((npc == null) ? 0 : npc.getNpcId()).getOnTalkEvent().iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */         
/* 149 */         questHandler = getQuestHandlerByQuestId(questId);
/* 150 */         if (questHandler != null && 
/* 151 */           questHandler.onDialogEvent(env))
/* 152 */           return true;  }
/*     */     
/*     */     } 
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKill(QuestEnv env) {
/* 160 */     Npc npc = (Npc)env.getVisibleObject();
/* 161 */     for (Iterator<Integer> i$ = getNpcQuestData(npc.getNpcId()).getOnKillEvent().iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */       
/* 163 */       QuestHandler questHandler = getQuestHandlerByQuestId(questId);
/* 164 */       if (questHandler != null && 
/* 165 */         questHandler.onKillEvent(env))
/* 166 */         return true;  }
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onAttack(QuestEnv env) {
/* 173 */     Npc npc = (Npc)env.getVisibleObject();
/*     */     
/* 175 */     for (Iterator<Integer> i$ = getNpcQuestData(npc.getNpcId()).getOnAttackEvent().iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */       
/* 177 */       QuestHandler questHandler = getQuestHandlerByQuestId(questId);
/* 178 */       if (questHandler != null && 
/* 179 */         questHandler.onAttackEvent(env))
/* 180 */         return true;  }
/*     */     
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLvlUp(QuestEnv env) {
/* 187 */     for (int index = 0; index < this.questLvlUp.size(); index++) {
/*     */       
/* 189 */       QuestHandler questHandler = getQuestHandlerByQuestId(this.questLvlUp.get(index));
/* 190 */       if (questHandler != null) {
/* 191 */         questHandler.onLvlUpEvent(env);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onDie(QuestEnv env) {
/* 197 */     for (int index = 0; index < this.questOnDie.size(); index++) {
/*     */       
/* 199 */       QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnDie.get(index));
/* 200 */       if (questHandler != null) {
/* 201 */         questHandler.onDieEvent(env);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onEnterWorld(QuestEnv env) {
/* 207 */     for (int index = 0; index < this.questOnEnterWorld.size(); index++) {
/*     */       
/* 209 */       QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnEnterWorld.get(index));
/* 210 */       if (questHandler != null) {
/* 211 */         questHandler.onEnterWorldEvent(env);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 217 */     TIntArrayList lists = getQuestItemIds(item.getItemTemplate().getTemplateId());
/* 218 */     for (int index = 0; index < lists.size(); index++) {
/*     */       
/* 220 */       QuestHandler questHandler = getQuestHandlerByQuestId(lists.get(index));
/* 221 */       if (questHandler != null && 
/* 222 */         questHandler.onItemUseEvent(env, item))
/* 223 */         return true; 
/*     */     } 
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onEnterZone(QuestEnv env, ZoneName zoneName) {
/* 230 */     TIntArrayList lists = getQuestEnterZone(zoneName);
/* 231 */     for (int index = 0; index < lists.size(); index++) {
/*     */       
/* 233 */       QuestHandler questHandler = getQuestHandlerByQuestId(lists.get(index));
/* 234 */       if (questHandler != null && 
/* 235 */         questHandler.onEnterZoneEvent(env, zoneName))
/* 236 */         return true; 
/*     */     } 
/* 238 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMovieEnd(QuestEnv env, int movieId) {
/* 243 */     TIntArrayList lists = getQuestMovieEndIds(movieId);
/* 244 */     for (int index = 0; index < lists.size(); index++) {
/*     */       
/* 246 */       env.setQuestId(Integer.valueOf(lists.get(index)));
/* 247 */       QuestHandler questHandler = getQuestHandlerByQuestId(env.getQuestId().intValue());
/* 248 */       if (questHandler != null && 
/* 249 */         questHandler.onMovieEndEvent(env, movieId))
/* 250 */         return true; 
/*     */     } 
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onQuestFinish(QuestEnv env) {
/* 257 */     for (int index = 0; index < this.questOnQuestFinish.size(); index++) {
/*     */       
/* 259 */       QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnQuestFinish.get(index));
/* 260 */       if (questHandler != null) {
/* 261 */         questHandler.onQuestFinishEvent(env);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onQuestAbort(QuestEnv env) {
/* 267 */     for (int index = 0; index < this.questOnQuestAbort.size(); index++) {
/*     */       
/* 269 */       QuestHandler questHandler = getQuestHandlerByQuestId(this.questOnQuestFinish.get(index));
/* 270 */       if (questHandler != null) {
/* 271 */         questHandler.onQuestAbortEvent(env);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onQuestTimerEnd(QuestEnv env) {
/* 277 */     for (Iterator<Integer> i$ = this.questOnQuestTimerEnd.iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */       
/* 279 */       QuestHandler questHandler = getQuestHandlerByQuestId(questId);
/* 280 */       if (questHandler != null) {
/* 281 */         questHandler.onQuestTimerEndEvent(env);
/*     */       } }
/*     */   
/*     */   }
/*     */   
/*     */   public boolean deleteQuest(Player player, int questId) {
/* 287 */     if (this.questData.getQuestById(questId).isCannotGiveup()) {
/* 288 */       return false;
/*     */     }
/* 290 */     QuestState qs = player.getQuestStateList().getQuestState(questId);
/*     */     
/* 292 */     if (qs == null) {
/* 293 */       return false;
/*     */     }
/* 295 */     qs.setStatus(QuestStatus.NONE);
/*     */     
/* 297 */     onQuestAbort(new QuestEnv(null, player, Integer.valueOf(questId), Integer.valueOf(0)));
/*     */     
/* 299 */     QuestWorkItems qwi = this.questData.getQuestById(questId).getQuestWorkItems();
/*     */     
/* 301 */     if (qwi != null) {
/*     */       
/* 303 */       long count = 0L;
/* 304 */       for (QuestItems qi : qwi.getQuestWorkItem()) {
/*     */         
/* 306 */         if (qi != null)
/*     */         {
/* 308 */           ItemService.decreaseItemCountByItemId(player, qi.getItemId().intValue(), count);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 313 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public NpcQuestData getNpcQuestData(int npcTemplateId) {
/* 318 */     if (this.npcQuestData.containsKey(npcTemplateId))
/*     */     {
/* 320 */       return (NpcQuestData)this.npcQuestData.get(npcTemplateId);
/*     */     }
/* 322 */     return this.emptyNpcQuestData;
/*     */   }
/*     */ 
/*     */   
/*     */   public NpcQuestData setNpcQuestData(int npcTemplateId) {
/* 327 */     if (!this.npcQuestData.containsKey(npcTemplateId))
/*     */     {
/* 329 */       this.npcQuestData.put(npcTemplateId, new NpcQuestData());
/*     */     }
/* 331 */     return (NpcQuestData)this.npcQuestData.get(npcTemplateId);
/*     */   }
/*     */ 
/*     */   
/*     */   public TIntArrayList getQuestItemIds(int itemId) {
/* 336 */     if (this.questItemIds.containsKey(itemId))
/*     */     {
/* 338 */       return (TIntArrayList)this.questItemIds.get(itemId);
/*     */     }
/* 340 */     return new TIntArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public TIntArrayList setQuestItemIds(int itemId) {
/* 345 */     if (!this.questItemIds.containsKey(itemId))
/*     */     {
/* 347 */       this.questItemIds.put(itemId, new TIntArrayList());
/*     */     }
/* 349 */     return (TIntArrayList)this.questItemIds.get(itemId);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<QuestDrop> setQuestDrop(int npcId) {
/* 354 */     if (!this.questDrop.containsKey(npcId))
/*     */     {
/* 356 */       this.questDrop.put(npcId, new ArrayList());
/*     */     }
/* 358 */     return (List<QuestDrop>)this.questDrop.get(npcId);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<QuestDrop> getQuestDrop(int npcId) {
/* 363 */     if (this.questDrop.containsKey(npcId))
/*     */     {
/* 365 */       return (List<QuestDrop>)this.questDrop.get(npcId);
/*     */     }
/* 367 */     return new ArrayList<QuestDrop>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addQuestLvlUp(int questId) {
/* 372 */     if (!this.questLvlUp.contains(questId)) {
/* 373 */       this.questLvlUp.add(questId);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addOnEnterWorld(int questId) {
/* 378 */     if (!this.questOnEnterWorld.contains(questId)) {
/* 379 */       this.questOnEnterWorld.add(questId);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addOnDie(int questId) {
/* 384 */     if (!this.questOnDie.contains(questId)) {
/* 385 */       this.questOnDie.add(questId);
/*     */     }
/*     */   }
/*     */   
/*     */   public TIntArrayList getQuestEnterZone(ZoneName zoneName) {
/* 390 */     if (this.questEnterZone.containsKey(zoneName))
/*     */     {
/* 392 */       return (TIntArrayList)this.questEnterZone.get(zoneName);
/*     */     }
/* 394 */     return new TIntArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public TIntArrayList setQuestEnterZone(ZoneName zoneName) {
/* 399 */     if (!this.questEnterZone.containsKey(zoneName))
/*     */     {
/* 401 */       this.questEnterZone.put(zoneName, new TIntArrayList());
/*     */     }
/* 403 */     return (TIntArrayList)this.questEnterZone.get(zoneName);
/*     */   }
/*     */ 
/*     */   
/*     */   public TIntArrayList getQuestMovieEndIds(int moveId) {
/* 408 */     if (this.questMovieEndIds.containsKey(moveId))
/*     */     {
/* 410 */       return (TIntArrayList)this.questMovieEndIds.get(moveId);
/*     */     }
/* 412 */     return new TIntArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public TIntArrayList setQuestMovieEndIds(int moveId) {
/* 417 */     if (!this.questMovieEndIds.containsKey(moveId))
/*     */     {
/* 419 */       this.questMovieEndIds.put(moveId, new TIntArrayList());
/*     */     }
/* 421 */     return (TIntArrayList)this.questMovieEndIds.get(moveId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOnQuestFinish(int questId) {
/* 426 */     if (!this.questOnQuestFinish.contains(questId)) {
/* 427 */       this.questOnQuestFinish.add(questId);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addOnQuestAbort(int questId) {
/* 432 */     if (!this.questOnQuestAbort.contains(questId)) {
/* 433 */       this.questOnQuestAbort.add(questId);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addOnQuestTimerEnd(int questId) {
/* 438 */     if (!this.questOnQuestTimerEnd.contains(Integer.valueOf(questId))) {
/* 439 */       this.questOnQuestTimerEnd.add(Integer.valueOf(questId));
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 444 */     this.npcQuestData.clear();
/* 445 */     this.questItemIds.clear();
/* 446 */     this.questLvlUp.clear();
/* 447 */     this.questOnEnterWorld.clear();
/* 448 */     this.questOnDie.clear();
/* 449 */     this.questEnterZone.clear();
/* 450 */     this.questMovieEndIds.clear();
/* 451 */     this.questDrop.clear();
/* 452 */     this.questOnQuestFinish.clear();
/* 453 */     this.questOnQuestTimerEnd.clear();
/* 454 */     questHandlers.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addQuestHandler(QuestHandler questHandler) {
/* 459 */     questHandler.register();
/* 460 */     if (questHandlers.containsKey(questHandler.getQuestId()))
/* 461 */       log.warn("Duplicate quest: " + questHandler.getQuestId()); 
/* 462 */     questHandlers.put(questHandler.getQuestId(), questHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   private QuestHandler getQuestHandlerByQuestId(int questId) {
/* 467 */     return (QuestHandler)questHandlers.get(Integer.valueOf(questId));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 473 */     protected static final QuestEngine instance = new QuestEngine();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\QuestEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
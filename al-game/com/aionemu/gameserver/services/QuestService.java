/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.configs.main.GroupConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.dataholders.QuestsData;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.drop.DropItem;
/*     */ import com.aionemu.gameserver.model.drop.DropTemplate;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
/*     */ import com.aionemu.gameserver.model.templates.QuestTemplate;
/*     */ import com.aionemu.gameserver.model.templates.quest.CollectItem;
/*     */ import com.aionemu.gameserver.model.templates.quest.CollectItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestDrop;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.Rewards;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.spawnengine.SpawnEngine;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
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
/*     */ public final class QuestService
/*     */ {
/*  62 */   static QuestsData questsData = DataManager.QUEST_DATA;
/*     */ 
/*     */   
/*     */   public static boolean questFinish(QuestEnv env) {
/*  66 */     return questFinish(env, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean questFinish(QuestEnv env, int reward) {
/*  71 */     Player player = env.getPlayer();
/*  72 */     int id = env.getQuestId().intValue();
/*  73 */     QuestState qs = player.getQuestStateList().getQuestState(id);
/*  74 */     if (qs == null || qs.getStatus() != QuestStatus.REWARD)
/*  75 */       return false; 
/*  76 */     QuestTemplate template = questsData.getQuestById(id);
/*  77 */     Rewards rewards = template.getRewards().get(reward);
/*  78 */     List<QuestItems> questItems = new ArrayList<QuestItems>();
/*  79 */     questItems.addAll(rewards.getRewardItem());
/*     */     
/*  81 */     int dialogId = env.getDialogId().intValue();
/*  82 */     if (dialogId != 17 && dialogId != 0)
/*     */     {
/*  84 */       if (template.isUseClassReward()) {
/*     */         
/*  86 */         QuestItems classRewardItem = null;
/*  87 */         PlayerClass playerClass = player.getCommonData().getPlayerClass();
/*  88 */         switch (playerClass) {
/*     */           
/*     */           case ASSASSIN:
/*  91 */             classRewardItem = template.getAssassinSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */           case CHANTER:
/*  94 */             classRewardItem = template.getChanterSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */           case CLERIC:
/*  97 */             classRewardItem = template.getPriestSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */           case GLADIATOR:
/* 100 */             classRewardItem = template.getFighterSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */           case RANGER:
/* 103 */             classRewardItem = template.getRangerSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */           case SORCERER:
/* 106 */             classRewardItem = template.getWizardSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */           case SPIRIT_MASTER:
/* 109 */             classRewardItem = template.getElementalistSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */           case TEMPLAR:
/* 112 */             classRewardItem = template.getKnightSelectableReward().get(dialogId - 8);
/*     */             break;
/*     */         } 
/* 115 */         if (classRewardItem != null) {
/* 116 */           questItems.add(classRewardItem);
/*     */         }
/*     */       } else {
/*     */         
/* 120 */         QuestItems selectebleRewardItem = rewards.getSelectableRewardItem().get(dialogId - 8);
/* 121 */         if (selectebleRewardItem != null)
/* 122 */           questItems.add(selectebleRewardItem); 
/*     */       } 
/*     */     }
/* 125 */     if (ItemService.addItems(player, questItems)) {
/*     */       
/* 127 */       if (rewards.getGold() != null)
/*     */       {
/* 129 */         ItemService.increaseKinah(player, (player.getRates().getQuestKinahRate() * rewards.getGold().intValue()));
/*     */       }
/* 131 */       if (rewards.getExp() != null) {
/*     */         
/* 133 */         int rewardExp = player.getRates().getQuestXpRate() * rewards.getExp().intValue();
/* 134 */         player.getCommonData().addExp(rewardExp);
/*     */       } 
/*     */       
/* 137 */       if (rewards.getTitle() != null)
/*     */       {
/* 139 */         player.getTitleList().addTitle(rewards.getTitle().intValue());
/*     */       }
/*     */       
/* 142 */       if (rewards.getRewardAbyssPoint() != null)
/*     */       {
/* 144 */         player.getCommonData().addAp(rewards.getRewardAbyssPoint().intValue());
/*     */       }
/*     */       
/* 147 */       if (rewards.getExtendInventory() != null)
/*     */       {
/* 149 */         if (rewards.getExtendInventory().intValue() == 1) {
/* 150 */           CubeExpandService.expand(player);
/* 151 */         } else if (rewards.getExtendInventory().intValue() == 2) {
/* 152 */           WarehouseService.expand(player);
/*     */         } 
/*     */       }
/* 155 */       if (rewards.getExtendStigma() != null) {
/*     */         
/* 157 */         PlayerCommonData pcd = player.getCommonData();
/* 158 */         pcd.setAdvencedStigmaSlotSize(pcd.getAdvencedStigmaSlotSize() + 1);
/* 159 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_CUBE_UPDATE(player, 6, pcd.getAdvencedStigmaSlotSize()));
/*     */       } 
/*     */       
/* 162 */       QuestWorkItems qwi = questsData.getQuestById(id).getQuestWorkItems();
/*     */       
/* 164 */       if (qwi != null) {
/*     */         
/* 166 */         long count = 0L;
/* 167 */         for (QuestItems qi : qwi.getQuestWorkItem()) {
/*     */           
/* 169 */           if (qi != null)
/*     */           {
/* 171 */             ItemService.decreaseItemCountByItemId(player, qi.getItemId().intValue(), count);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       QuestEngine.getInstance().onQuestFinish(env);
/* 177 */       qs.setStatus(QuestStatus.COMPLETE);
/* 178 */       qs.setCompliteCount(qs.getCompliteCount() + 1);
/* 179 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id, qs.getStatus(), qs.getQuestVars().getQuestVars()));
/* 180 */       player.getController().updateNearbyQuests();
/* 181 */       QuestEngine.getInstance().onLvlUp(env);
/* 182 */       return true;
/*     */     } 
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkStartCondition(QuestEnv env) {
/* 190 */     Player player = env.getPlayer();
/* 191 */     QuestTemplate template = questsData.getQuestById(env.getQuestId().intValue());
/* 192 */     if (template.getRacePermitted() != null)
/*     */     {
/* 194 */       if (template.getRacePermitted() != player.getCommonData().getRace()) {
/* 195 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 200 */     if (player.getLevel() < template.getMinlevelPermitted().intValue() - 2)
/*     */     {
/* 202 */       return false;
/*     */     }
/*     */     
/* 205 */     if (template.getClassPermitted().size() != 0)
/*     */     {
/* 207 */       if (!template.getClassPermitted().contains(player.getCommonData().getPlayerClass())) {
/* 208 */         return false;
/*     */       }
/*     */     }
/* 211 */     if (template.getGenderPermitted() != null)
/*     */     {
/* 213 */       if (template.getGenderPermitted() != player.getGender()) {
/* 214 */         return false;
/*     */       }
/*     */     }
/* 217 */     for (Iterator<Integer> i$ = template.getFinishedQuestConds().iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */       
/* 219 */       QuestState questState = player.getQuestStateList().getQuestState(questId);
/* 220 */       if (questState == null || questState.getStatus() != QuestStatus.COMPLETE) {
/* 221 */         return false;
/*     */       } }
/*     */     
/* 224 */     if (template.getCombineSkill() != null) {
/*     */       
/* 226 */       SkillListEntry skill = player.getSkillList().getSkillEntry(template.getCombineSkill().intValue());
/* 227 */       if (skill == null)
/* 228 */         return false; 
/* 229 */       if (skill.getSkillLevel() < template.getCombineSkillPoint().intValue() || skill.getSkillLevel() - 40 > template.getCombineSkillPoint().intValue())
/* 230 */         return false; 
/* 231 */       return true;
/*     */     } 
/*     */     
/* 234 */     QuestState qs = player.getQuestStateList().getQuestState(template.getId());
/* 235 */     if (qs != null && qs.getStatus().value() > 0) {
/* 236 */       if (qs.getStatus() == QuestStatus.COMPLETE && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue())
/*     */       {
/* 238 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 242 */       return false;
/*     */     } 
/*     */     
/* 245 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean startQuest(QuestEnv env, QuestStatus questStatus) {
/* 250 */     Player player = env.getPlayer();
/* 251 */     int id = env.getQuestId().intValue();
/* 252 */     QuestTemplate template = questsData.getQuestById(env.getQuestId().intValue());
/* 253 */     if (questStatus != QuestStatus.LOCKED) {
/*     */       
/* 255 */       if (!checkStartCondition(env)) {
/* 256 */         return false;
/*     */       }
/* 258 */       if (player.getLevel() < template.getMinlevelPermitted().intValue())
/*     */       {
/*     */         
/* 261 */         return false;
/*     */       }
/*     */     } 
/* 264 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id, questStatus.value(), 0));
/* 265 */     QuestState qs = player.getQuestStateList().getQuestState(id);
/* 266 */     if (qs == null) {
/*     */       
/* 268 */       qs = new QuestState(template.getId(), questStatus, 0, 0);
/* 269 */       player.getQuestStateList().addQuest(id, qs);
/*     */ 
/*     */     
/*     */     }
/* 273 */     else if (template.getMaxRepeatCount().intValue() >= qs.getCompliteCount()) {
/*     */       
/* 275 */       qs.setStatus(questStatus);
/* 276 */       qs.setQuestVar(0);
/*     */     } 
/*     */ 
/*     */     
/* 280 */     player.getController().updateNearbyQuests();
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean questComplite(QuestEnv env) {
/* 286 */     Player player = env.getPlayer();
/* 287 */     int id = env.getQuestId().intValue();
/* 288 */     QuestState qs = player.getQuestStateList().getQuestState(id);
/* 289 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 290 */       return false;
/*     */     }
/* 292 */     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 293 */     qs.setStatus(QuestStatus.REWARD);
/* 294 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id, qs.getStatus(), qs.getQuestVars().getQuestVars()));
/* 295 */     player.getController().updateNearbyQuests();
/* 296 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean collectItemCheck(QuestEnv env, boolean removeItem) {
/* 301 */     Player player = env.getPlayer();
/* 302 */     int id = env.getQuestId().intValue();
/* 303 */     QuestState qs = player.getQuestStateList().getQuestState(id);
/* 304 */     if (qs == null)
/* 305 */       return false; 
/* 306 */     QuestTemplate template = questsData.getQuestById(env.getQuestId().intValue());
/* 307 */     CollectItems collectItems = template.getCollectItems();
/* 308 */     if (collectItems == null)
/* 309 */       return true; 
/* 310 */     for (CollectItem collectItem : collectItems.getCollectItem()) {
/*     */       
/* 312 */       long count = player.getInventory().getItemCountByItemId(collectItem.getItemId().intValue());
/* 313 */       if (collectItem.getCount().intValue() > count)
/* 314 */         return false; 
/*     */     } 
/* 316 */     if (removeItem)
/*     */     {
/* 318 */       for (CollectItem collectItem : collectItems.getCollectItem())
/*     */       {
/* 320 */         ItemService.decreaseItemCountByItemId(player, collectItem.getItemId().intValue(), collectItem.getCount().intValue());
/*     */       }
/*     */     }
/* 323 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static VisibleObject addNewSpawn(int worldId, int instanceId, int templateId, float x, float y, float z, byte heading, boolean noRespawn) {
/* 328 */     SpawnTemplate spawn = SpawnEngine.getInstance().addNewSpawn(worldId, instanceId, templateId, x, y, z, heading, 0, 0, noRespawn);
/* 329 */     return SpawnEngine.getInstance().spawnObject(spawn, instanceId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void getQuestDrop(Set<DropItem> droppedItems, int index, Npc npc, Player player) {
/* 334 */     List<QuestDrop> drops = QuestEngine.getInstance().getQuestDrop(npc.getNpcId());
/* 335 */     if (drops.isEmpty())
/*     */       return; 
/* 337 */     List<Player> players = new ArrayList<Player>();
/* 338 */     if (player.isInGroup()) {
/*     */       
/* 340 */       for (Player member : player.getPlayerGroup().getMembers())
/*     */       {
/* 342 */         if (MathUtil.isInRange((VisibleObject)member, (VisibleObject)npc, GroupConfig.GROUP_MAX_DISTANCE))
/*     */         {
/* 344 */           players.add(member);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 350 */       players.add(player);
/*     */     } 
/* 352 */     for (QuestDrop drop : drops) {
/*     */       
/* 354 */       for (Player member : players) {
/*     */         
/* 356 */         if (isDrop(member, drop)) {
/*     */           
/* 358 */           DropItem item = new DropItem(new DropTemplate(drop.getNpcId().intValue(), drop.getItemId().intValue(), 1, 1, drop.getChance().intValue()));
/* 359 */           item.setPlayerObjId(member.getObjectId());
/* 360 */           item.setIndex(index++);
/* 361 */           item.setCount(1L);
/* 362 */           droppedItems.add(item);
/* 363 */           if (!drop.isDropEachMember().booleanValue()) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isDrop(Player player, QuestDrop drop) {
/* 374 */     if (Rnd.get() * 100.0F > drop.getChance().intValue())
/* 375 */       return false; 
/* 376 */     int questId = drop.getQuestId().intValue();
/* 377 */     QuestState qs = player.getQuestStateList().getQuestState(questId);
/* 378 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/* 379 */       return false; 
/* 380 */     QuestTemplate template = questsData.getQuestById(questId);
/* 381 */     CollectItems collectItems = template.getCollectItems();
/* 382 */     if (collectItems == null) {
/* 383 */       return true;
/*     */     }
/* 385 */     for (CollectItem collectItem : collectItems.getCollectItem()) {
/*     */       
/* 387 */       int collectItemId = collectItem.getItemId().intValue();
/* 388 */       int dropItemId = drop.getItemId().intValue();
/* 389 */       if (collectItemId != dropItemId)
/*     */         continue; 
/* 391 */       long count = player.getInventory().getItemCountByItemId(collectItemId);
/* 392 */       if (collectItem.getCount().intValue() > count)
/* 393 */         return true; 
/*     */     } 
/* 395 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkLevelRequirement(int questId, int playerLevel) {
/* 405 */     QuestTemplate template = questsData.getQuestById(questId);
/* 406 */     return (playerLevel >= template.getMinlevelPermitted().intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean questTimerStart(QuestEnv env, int timeInSeconds) {
/* 411 */     final Player player = env.getPlayer();
/* 412 */     final int id = env.getQuestId().intValue();
/*     */ 
/*     */     
/* 415 */     Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 420 */             QuestEngine.getInstance().onQuestTimerEnd(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)));
/* 421 */             QuestEngine.getInstance().deleteQuest(player, id);
/* 422 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id));
/* 423 */             player.getController().updateNearbyQuests();
/*     */           }
/*     */         }(timeInSeconds * 1000));
/* 426 */     player.getController().addTask(TaskId.QUEST_TIMER, task);
/* 427 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id, timeInSeconds));
/* 428 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean questTimerEnd(QuestEnv env) {
/* 433 */     Player player = env.getPlayer();
/* 434 */     int id = env.getQuestId().intValue();
/*     */     
/* 436 */     player.getController().cancelTask(TaskId.QUEST_TIMER);
/* 437 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id, 0));
/* 438 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\QuestService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
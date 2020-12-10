/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.attack.AttackResult;
/*     */ import com.aionemu.gameserver.controllers.attack.AttackUtil;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.group.GroupEvent;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GATHERABLE_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_KISK_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_NEARBY_QUESTS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_NPC_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_CANCEL;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_PANEL;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.services.AllianceService;
/*     */ import com.aionemu.gameserver.services.ClassChangeService;
/*     */ import com.aionemu.gameserver.services.DuelService;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.LegionService;
/*     */ import com.aionemu.gameserver.services.PvpService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.SkillLearnService;
/*     */ import com.aionemu.gameserver.services.ZoneService;
/*     */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*     */ import com.aionemu.gameserver.skillengine.model.HealType;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.spawnengine.SpawnEngine;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.zone.ZoneInstance;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class PlayerController
/*     */   extends CreatureController<Player>
/*     */ {
/*     */   private boolean isInShutdownProgress;
/*     */   private volatile byte zoneUpdateMask;
/* 104 */   private long lastAttackMilis = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void see(VisibleObject object) {
/* 112 */     super.see(object);
/* 113 */     if (object instanceof Player) {
/*     */       
/* 115 */       PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_PLAYER_INFO((Player)object, getOwner().isEnemyPlayer((Player)object)));
/* 116 */       getOwner().getEffectController().sendEffectIconsTo((Player)object);
/*     */     }
/* 118 */     else if (object instanceof Kisk) {
/*     */       
/* 120 */       Kisk kisk = (Kisk)object;
/* 121 */       PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_NPC_INFO(getOwner(), kisk));
/* 122 */       if (getOwner().getCommonData().getRace() == kisk.getOwnerRace()) {
/* 123 */         PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_KISK_UPDATE(kisk));
/*     */       }
/* 125 */     } else if (object instanceof Npc) {
/*     */       
/* 127 */       boolean update = false;
/* 128 */       Npc npc = (Npc)object;
/*     */       
/* 130 */       PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_NPC_INFO(npc, getOwner()));
/*     */       
/* 132 */       for (Iterator<Integer> i$ = QuestEngine.getInstance().getNpcQuestData(npc.getNpcId()).getOnQuestStart().iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */         
/* 134 */         if (QuestService.checkStartCondition(new QuestEnv(object, getOwner(), Integer.valueOf(questId), Integer.valueOf(0))))
/*     */         {
/* 136 */           if (!getOwner().getNearbyQuests().contains(Integer.valueOf(questId))) {
/*     */             
/* 138 */             update = true;
/* 139 */             getOwner().getNearbyQuests().add(Integer.valueOf(questId));
/*     */           } 
/*     */         } }
/*     */       
/* 143 */       if (update) {
/* 144 */         updateNearbyQuestList();
/*     */       }
/* 146 */     } else if (object instanceof Summon) {
/*     */       
/* 148 */       Summon npc = (Summon)object;
/* 149 */       PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_NPC_INFO(npc));
/*     */     }
/* 151 */     else if (object instanceof com.aionemu.gameserver.model.gameobjects.Gatherable || object instanceof com.aionemu.gameserver.model.gameobjects.StaticObject) {
/*     */       
/* 153 */       PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_GATHERABLE_INFO(object));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notSee(VisibleObject object, boolean isOutOfRange) {
/* 163 */     super.notSee(object, isOutOfRange);
/* 164 */     if (object instanceof Npc) {
/*     */       
/* 166 */       boolean update = false;
/* 167 */       for (Iterator<Integer> i$ = QuestEngine.getInstance().getNpcQuestData(((Npc)object).getNpcId()).getOnQuestStart().iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */         
/* 169 */         if (QuestService.checkStartCondition(new QuestEnv(object, getOwner(), Integer.valueOf(questId), Integer.valueOf(0))))
/*     */         {
/* 171 */           if (getOwner().getNearbyQuests().contains(Integer.valueOf(questId))) {
/*     */             
/* 173 */             update = true;
/* 174 */             getOwner().getNearbyQuests().remove(getOwner().getNearbyQuests().indexOf(Integer.valueOf(questId)));
/*     */           } 
/*     */         } }
/*     */       
/* 178 */       if (update) {
/* 179 */         updateNearbyQuestList();
/*     */       }
/*     */     } 
/* 182 */     PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_DELETE((AionObject)object, isOutOfRange ? 0 : 15));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNearbyQuests() {
/* 187 */     getOwner().getNearbyQuests().clear();
/* 188 */     for (VisibleObject obj : getOwner().getKnownList().getKnownObjects().values()) {
/*     */       
/* 190 */       if (obj instanceof Npc)
/*     */       {
/* 192 */         for (Iterator<Integer> i$ = QuestEngine.getInstance().getNpcQuestData(((Npc)obj).getNpcId()).getOnQuestStart().iterator(); i$.hasNext(); ) { int questId = ((Integer)i$.next()).intValue();
/*     */           
/* 194 */           if (QuestService.checkStartCondition(new QuestEnv(obj, getOwner(), Integer.valueOf(questId), Integer.valueOf(0))))
/*     */           {
/* 196 */             if (!getOwner().getNearbyQuests().contains(Integer.valueOf(questId)))
/*     */             {
/* 198 */               getOwner().getNearbyQuests().add(Integer.valueOf(questId));
/*     */             }
/*     */           } }
/*     */       
/*     */       }
/*     */     } 
/* 204 */     updateNearbyQuestList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnterZone(ZoneInstance zoneInstance) {
/* 214 */     QuestEngine.getInstance().onEnterZone(new QuestEnv(null, getOwner(), Integer.valueOf(0), Integer.valueOf(0)), zoneInstance.getTemplate().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLeaveZone(ZoneInstance zoneInstance) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetZone() {
/* 232 */     getOwner().setZoneInstance(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDie(Creature lastAttacker) {
/* 243 */     Player player = getOwner();
/*     */     
/* 245 */     Creature master = null;
/* 246 */     if (lastAttacker != null) {
/* 247 */       master = lastAttacker.getMaster();
/*     */     }
/* 249 */     if (master instanceof Player)
/*     */     {
/* 251 */       if (isDueling((Player)master)) {
/*     */         
/* 253 */         DuelService.getInstance().onDie(player);
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/* 258 */     doReward();
/*     */ 
/*     */     
/* 261 */     boolean hasSelfRezEffect = player.getReviveController().checkForSelfRezEffect(player);
/*     */     
/* 263 */     super.onDie(lastAttacker);
/*     */     
/* 265 */     if (master instanceof Npc || master == player)
/*     */     {
/* 267 */       if (player.getLevel() > 4) {
/* 268 */         player.getCommonData().calculateExpLoss();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 274 */     Summon summon = player.getSummon();
/* 275 */     if (summon != null) {
/* 276 */       summon.getController().release(SummonController.UnsummonType.UNSPECIFIED);
/*     */     }
/* 278 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.DIE, 0, (lastAttacker == null) ? 0 : lastAttacker.getObjectId()), true);
/*     */ 
/*     */ 
/*     */     
/* 282 */     int kiskTimeRemaining = (player.getKisk() != null) ? player.getKisk().getRemainingLifetime() : 0;
/* 283 */     boolean hasSelfRezItem = player.getReviveController().checkForSelfRezItem(player);
/* 284 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIE(hasSelfRezEffect, hasSelfRezItem, kiskTimeRemaining));
/*     */     
/* 286 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.DIE);
/* 287 */     QuestEngine.getInstance().onDie(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doReward() {
/* 296 */     Player victim = getOwner();
/* 297 */     PvpService.getInstance().doReward(victim);
/*     */   }
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
/*     */   public void onRespawn() {
/* 310 */     super.onRespawn();
/* 311 */     startProtectionActiveTask();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackTarget(Creature target) {
/* 317 */     Player player = getOwner();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (target == null || !player.canAttack()) {
/*     */       return;
/*     */     }
/* 325 */     PlayerGameStats gameStats = player.getGameStats();
/*     */ 
/*     */     
/* 328 */     if (Math.abs(player.getZ() - target.getZ()) > 6.0F) {
/*     */       return;
/*     */     }
/* 331 */     if (!RestrictionsManager.canAttack(player, (VisibleObject)target)) {
/*     */       return;
/*     */     }
/* 334 */     int attackSpeed = gameStats.getCurrentStat(StatEnum.ATTACK_SPEED);
/* 335 */     long milis = System.currentTimeMillis();
/* 336 */     if (milis - this.lastAttackMilis < attackSpeed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 343 */     this.lastAttackMilis = milis;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     super.attackTarget(target);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 353 */     List<AttackResult> attackResult = AttackUtil.calculateAttackResult((Creature)player, target);
/*     */     
/* 355 */     int damage = 0;
/* 356 */     for (AttackResult result : attackResult)
/*     */     {
/* 358 */       damage += result.getDamage();
/*     */     }
/*     */     
/* 361 */     long time = System.currentTimeMillis();
/* 362 */     int attackType = 0;
/* 363 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ATTACK((Creature)player, target, gameStats.getAttackCounter(), (int)time, attackType, attackResult), true);
/*     */ 
/*     */     
/* 366 */     target.getController().onAttack((Creature)player, damage);
/*     */     
/* 368 */     gameStats.increaseAttackCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
/* 374 */     if (getOwner().getLifeStats().isAlreadyDead()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 380 */     if (damage > getOwner().getLifeStats().getCurrentHp()) {
/* 381 */       damage = getOwner().getLifeStats().getCurrentHp() + 1;
/*     */     }
/* 383 */     super.onAttack(creature, skillId, type, damage);
/*     */     
/* 385 */     if (getOwner().isInvul()) {
/* 386 */       damage = 0;
/*     */     }
/* 388 */     getOwner().getLifeStats().reduceHp(damage, creature);
/* 389 */     PacketSendUtility.broadcastPacket(getOwner(), (AionServerPacket)new SM_ATTACK_STATUS((Creature)getOwner(), type, skillId, damage), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void useSkill(int skillId, int targetType, float x, float y, float z) {
/* 401 */     Player player = getOwner();
/*     */     
/* 403 */     Skill skill = SkillEngine.getInstance().getSkillFor(player, skillId, player.getTarget());
/*     */     
/* 405 */     if (skill != null) {
/*     */       
/* 407 */       skill.setTargetType(targetType, x, y, z);
/* 408 */       if (!RestrictionsManager.canUseSkill(player, skill)) {
/*     */         return;
/*     */       }
/* 411 */       skill.useSkill();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMove() {
/* 418 */     super.onMove();
/* 419 */     addZoneUpdateMask(ZoneService.ZoneUpdateMode.ZONE_UPDATE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStopMove() {
/* 425 */     super.onStopMove();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStartMove() {
/* 431 */     cancelCurrentSkill();
/* 432 */     super.onStartMove();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelCurrentSkill() {
/* 441 */     Player player = getOwner();
/* 442 */     Skill castingSkill = player.getCastingSkill();
/* 443 */     if (castingSkill != null) {
/*     */       
/* 445 */       int skillId = castingSkill.getSkillTemplate().getSkillId();
/* 446 */       player.removeSkillCoolDown(skillId);
/* 447 */       player.setCasting(null);
/* 448 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_CANCEL((Creature)player, skillId));
/* 449 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_SKILL_CANCELED());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePassiveStats() {
/* 458 */     Player player = getOwner();
/* 459 */     for (SkillListEntry skillEntry : player.getSkillList().getAllSkills()) {
/*     */       
/* 461 */       Skill skill = SkillEngine.getInstance().getSkillFor(player, skillEntry.getSkillId(), player.getTarget());
/* 462 */       if (skill != null && skill.isPassive())
/*     */       {
/* 464 */         skill.useSkill();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getOwner() {
/* 472 */     return (Player)super.getOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRestore(HealType healType, int value) {
/* 478 */     super.onRestore(healType, value);
/* 479 */     switch (healType) {
/*     */       
/*     */       case DP:
/* 482 */         getOwner().getCommonData().addDp(value);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDueling(Player player) {
/* 496 */     return DuelService.getInstance().isDueling(player.getObjectId(), getOwner().getObjectId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNearbyQuestList() {
/* 501 */     getOwner().addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_NEARBY_QUEST_LIST);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNearbyQuestListImpl() {
/* 506 */     PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_NEARBY_QUESTS(getOwner().getNearbyQuests()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInShutdownProgress() {
/* 511 */     return this.isInShutdownProgress;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInShutdownProgress(boolean isInShutdownProgress) {
/* 516 */     this.isInShutdownProgress = isInShutdownProgress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogSelect(int dialogId, Player player, int questId) {
/* 525 */     switch (dialogId) {
/*     */       
/*     */       case 2:
/* 528 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PRIVATE_STORE(getOwner().getStore()));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upgradePlayer(int level) {
/* 538 */     Player player = getOwner();
/*     */     
/* 540 */     PlayerStatsTemplate statsTemplate = DataManager.PLAYER_STATS_DATA.getTemplate(player);
/* 541 */     player.setPlayerStatsTemplate(statsTemplate);
/*     */ 
/*     */     
/* 544 */     player.getGameStats().doLevelUpgrade();
/* 545 */     player.getLifeStats().synchronizeWithMaxStats();
/* 546 */     player.getLifeStats().updateCurrentStats();
/*     */     
/* 548 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_LEVEL_UPDATE(player.getObjectId(), 0, level), true);
/*     */ 
/*     */     
/* 551 */     ClassChangeService.showClassChangeDialog(player);
/*     */     
/* 553 */     QuestEngine.getInstance().onLvlUp(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)));
/* 554 */     updateNearbyQuests();
/*     */     
/* 556 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_STATS_INFO(player));
/*     */     
/* 558 */     if (level == 10 && player.getSkillList().getSkillEntry(30001) != null) {
/*     */       
/* 560 */       int skillLevel = player.getSkillList().getSkillLevel(30001);
/* 561 */       player.getSkillList().removeSkill(player, 30001);
/* 562 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player));
/* 563 */       player.getSkillList().addSkill(player, 30002, skillLevel, true);
/*     */     } 
/*     */     
/* 566 */     SkillLearnService.addNewSkills(player, false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 571 */     if (player.isInGroup())
/* 572 */       player.getPlayerGroup().updateGroupUIToEvent(player, GroupEvent.UPDATE); 
/* 573 */     if (player.isInAlliance())
/* 574 */       AllianceService.getInstance().updateAllianceUIToEvent(player, PlayerAllianceEvent.UPDATE); 
/* 575 */     if (player.isLegionMember()) {
/* 576 */       LegionService.getInstance().updateMemberInfo(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startProtectionActiveTask() {
/* 585 */     getOwner().setVisualState(CreatureVisualState.BLINKING);
/* 586 */     PacketSendUtility.broadcastPacket(getOwner(), (AionServerPacket)new SM_PLAYER_STATE(getOwner()), true);
/* 587 */     Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 592 */             PlayerController.this.stopProtectionActiveTask();
/*     */           }
/*     */         },  60000L);
/* 595 */     addTask(TaskId.PROTECTION_ACTIVE, task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopProtectionActiveTask() {
/* 603 */     cancelTask(TaskId.PROTECTION_ACTIVE);
/* 604 */     Player player = getOwner();
/* 605 */     if (player != null && player.isSpawned()) {
/*     */       
/* 607 */       player.unsetVisualState(CreatureVisualState.BLINKING);
/* 608 */       PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_PLAYER_STATE(player), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFlyTeleportEnd() {
/* 617 */     Player player = getOwner();
/* 618 */     player.unsetState(CreatureState.FLIGHT_TELEPORT);
/* 619 */     player.setFlightTeleportId(0);
/* 620 */     player.setFlightDistance(0);
/* 621 */     player.setState(CreatureState.ACTIVE);
/* 622 */     addZoneUpdateMask(ZoneService.ZoneUpdateMode.ZONE_REFRESH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addZoneUpdateMask(ZoneService.ZoneUpdateMode mode) {
/* 632 */     this.zoneUpdateMask = (byte)(this.zoneUpdateMask | mode.mask());
/* 633 */     ZoneService.getInstance().add(getOwner());
/*     */   }
/*     */ 
/*     */   
/*     */   public final void removeZoneUpdateMask(ZoneService.ZoneUpdateMode mode) {
/* 638 */     this.zoneUpdateMask = (byte)(this.zoneUpdateMask & (mode.mask() ^ 0xFFFFFFFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte getZoneUpdateMask() {
/* 643 */     return this.zoneUpdateMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateZoneImpl() {
/* 651 */     ZoneService.getInstance().checkZone(getOwner());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshZoneImpl() {
/* 659 */     ZoneService.getInstance().findZoneInCurrentMap(getOwner());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkWaterLevel() {
/* 667 */     Player player = getOwner();
/* 668 */     World world = World.getInstance();
/* 669 */     float z = player.getZ();
/*     */     
/* 671 */     if (player.getLifeStats().isAlreadyDead()) {
/*     */       return;
/*     */     }
/* 674 */     if (z < world.getWorldMap(player.getWorldId()).getDeathLevel()) {
/*     */       
/* 676 */       die();
/*     */       
/*     */       return;
/*     */     } 
/* 680 */     ZoneInstance currentZone = player.getZoneInstance();
/* 681 */     if (currentZone != null && currentZone.isBreath()) {
/*     */       return;
/*     */     }
/*     */     
/* 685 */     float playerheight = player.getPlayerAppearance().getHeight() * 1.6F;
/* 686 */     if (z < world.getWorldMap(player.getWorldId()).getWaterLevel() - playerheight) {
/* 687 */       ZoneService.getInstance().startDrowning(player);
/*     */     } else {
/* 689 */       ZoneService.getInstance().stopDrowning(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createSummon(int npcId, int skillLvl) {
/* 695 */     Player master = getOwner();
/* 696 */     Summon summon = SpawnEngine.getInstance().spawnSummon(master, npcId, skillLvl);
/* 697 */     master.setSummon(summon);
/* 698 */     PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_PANEL(summon));
/* 699 */     PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_EMOTION((Creature)summon, EmotionType.START_EMOTE2));
/* 700 */     PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_SUMMON_UPDATE(summon));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItems(int itemId, int count) {
/* 705 */     return ItemService.addItems(getOwner(), Collections.singletonList(new QuestItems(itemId, count)));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\PlayerController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
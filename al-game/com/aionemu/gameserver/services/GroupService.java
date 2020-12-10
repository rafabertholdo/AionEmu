/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.GroupConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Monster;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.model.group.GroupEvent;
/*     */ import com.aionemu.gameserver.model.group.LootDistribution;
/*     */ import com.aionemu.gameserver.model.group.LootGroupRules;
/*     */ import com.aionemu.gameserver.model.group.LootRuleType;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_MEMBER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SHOW_BRAND;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import com.aionemu.gameserver.utils.stats.StatFunctions;
/*     */ import com.aionemu.gameserver.world.WorldType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledFuture;
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
/*     */ public class GroupService
/*     */ {
/*  57 */   private static final Logger log = Logger.getLogger(GroupService.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private final FastMap<Integer, PlayerGroup> groupMembers;
/*     */ 
/*     */ 
/*     */   
/*     */   private FastMap<Integer, ScheduledFuture<?>> playerGroup;
/*     */ 
/*     */   
/*     */   private List<Player> inRangePlayers;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final GroupService getInstance() {
/*  73 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GroupService() {
/*  82 */     this.groupMembers = new FastMap();
/*  83 */     this.playerGroup = new FastMap();
/*     */     
/*  85 */     log.info("GroupService: Initialized.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addGroupMemberToCache(Player player) {
/*  96 */     if (!this.groupMembers.containsKey(Integer.valueOf(player.getObjectId()))) {
/*  97 */       this.groupMembers.put(Integer.valueOf(player.getObjectId()), player.getPlayerGroup());
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeGroupMemberFromCache(int playerObjId) {
/* 102 */     if (this.groupMembers.containsKey(Integer.valueOf(playerObjId))) {
/* 103 */       this.groupMembers.remove(Integer.valueOf(playerObjId));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGroupMember(int playerObjId) {
/* 112 */     return this.groupMembers.containsKey(Integer.valueOf(playerObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlayerGroup getGroup(int playerObjId) {
/* 123 */     return (PlayerGroup)this.groupMembers.get(Integer.valueOf(playerObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invitePlayerToGroup(final Player inviter, final Player invited) {
/* 134 */     if (RestrictionsManager.canInviteToGroup(inviter, invited)) {
/*     */       
/* 136 */       RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)inviter)
/*     */         {
/*     */           public void acceptRequest(Creature requester, Player responder)
/*     */           {
/* 140 */             PlayerGroup group = inviter.getPlayerGroup();
/* 141 */             if (group != null && group.isFull()) {
/*     */               return;
/*     */             }
/* 144 */             PacketSendUtility.sendPacket(inviter, (AionServerPacket)SM_SYSTEM_MESSAGE.REQUEST_GROUP_INVITE(invited.getName()));
/* 145 */             if (group != null) {
/*     */               
/* 147 */               inviter.getPlayerGroup().addPlayerToGroup(invited);
/* 148 */               GroupService.this.addGroupMemberToCache(invited);
/*     */             }
/*     */             else {
/*     */               
/* 152 */               new PlayerGroup(IDFactory.getInstance().nextId(), inviter);
/* 153 */               inviter.getPlayerGroup().addPlayerToGroup(invited);
/* 154 */               GroupService.this.addGroupMemberToCache(inviter);
/* 155 */               GroupService.this.addGroupMemberToCache(invited);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void denyRequest(Creature requester, Player responder) {
/* 162 */             PacketSendUtility.sendPacket(inviter, (AionServerPacket)SM_SYSTEM_MESSAGE.REJECT_GROUP_INVITE(responder.getName()));
/*     */           }
/*     */         };
/*     */       
/* 166 */       boolean result = invited.getResponseRequester().putRequest(60000, responseHandler);
/*     */       
/* 168 */       if (result)
/*     */       {
/* 170 */         PacketSendUtility.sendPacket(invited, (AionServerPacket)new SM_QUESTION_WINDOW(60000, 0, new Object[] { inviter.getName() }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromGroup(Player player) {
/* 181 */     if (player.isInGroup()) {
/*     */       
/* 183 */       PlayerGroup group = player.getPlayerGroup();
/*     */       
/* 185 */       group.removePlayerFromGroup(player);
/* 186 */       removeGroupMemberFromCache(player.getObjectId());
/*     */       
/* 188 */       if (group.size() < 2) {
/* 189 */         disbandGroup(group);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setGroupLeader(Player player) {
/* 198 */     PlayerGroup group = player.getPlayerGroup();
/*     */     
/* 200 */     group.setGroupLeader(player);
/* 201 */     group.updateGroupUIToEvent(player.getPlayerGroup().getGroupLeader(), GroupEvent.CHANGELEADER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playerStatusInfo(int status, Player player) {
/* 211 */     switch (status) {
/*     */       
/*     */       case 2:
/* 214 */         removePlayerFromGroup(player);
/*     */         break;
/*     */       case 3:
/* 217 */         setGroupLeader(player);
/*     */         break;
/*     */       case 6:
/* 220 */         removePlayerFromGroup(player);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void groupDistribution(Player player, long amount) {
/* 231 */     PlayerGroup pg = player.getPlayerGroup();
/* 232 */     if (pg == null) {
/*     */       return;
/*     */     }
/* 235 */     long availableKinah = player.getInventory().getKinahItem().getItemCount();
/* 236 */     if (availableKinah < amount) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 242 */     long rewardcount = (pg.size() - 1);
/* 243 */     if (rewardcount <= amount) {
/*     */       
/* 245 */       long reward = amount / rewardcount;
/*     */       
/* 247 */       for (Player groupMember : pg.getMembers()) {
/*     */         
/* 249 */         if (groupMember.equals(player)) {
/* 250 */           ItemService.decreaseKinah(groupMember, amount); continue;
/*     */         } 
/* 252 */         ItemService.increaseKinah(groupMember, reward);
/*     */       } 
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
/*     */   public void doReward(PlayerGroup group, Monster owner) {
/* 265 */     List<Player> players = new ArrayList<Player>();
/* 266 */     int partyLvlSum = 0;
/* 267 */     int highestLevel = 0;
/* 268 */     for (Player member : group.getMembers()) {
/*     */       
/* 270 */       if (member.isOnline() && 
/* 271 */         MathUtil.isIn3dRange((VisibleObject)member, (VisibleObject)owner, GroupConfig.GROUP_MAX_DISTANCE)) {
/*     */         
/* 273 */         if (member.getLifeStats().isAlreadyDead())
/*     */           continue; 
/* 275 */         players.add(member);
/* 276 */         partyLvlSum += member.getLevel();
/* 277 */         if (member.getLevel() > highestLevel) {
/* 278 */           highestLevel = member.getLevel();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     if (players.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 287 */     int apRewardPerMember = 0;
/* 288 */     WorldType worldType = owner.getWorldType();
/*     */ 
/*     */     
/* 291 */     if (worldType == WorldType.ABYSS)
/*     */     {
/*     */       
/* 294 */       apRewardPerMember = Math.round((StatFunctions.calculateGroupAPReward(highestLevel, (Creature)owner) / players.size()));
/*     */     }
/*     */ 
/*     */     
/* 298 */     long expReward = StatFunctions.calculateGroupExperienceReward(highestLevel, (Creature)owner);
/*     */ 
/*     */     
/* 301 */     int bonus = 1;
/* 302 */     if (players.size() == 0)
/*     */       return; 
/* 304 */     if (players.size() > 0) {
/* 305 */       bonus = 100 + (players.size() - 1) * 10;
/*     */     }
/* 307 */     for (Player member : players) {
/*     */ 
/*     */       
/* 310 */       long currentExp = member.getCommonData().getExp();
/* 311 */       long reward = expReward * bonus * member.getLevel() / (partyLvlSum * 100);
/* 312 */       reward *= member.getRates().getGroupXpRate();
/*     */       
/* 314 */       if (highestLevel - member.getLevel() >= 10)
/* 315 */         reward = 0L; 
/* 316 */       member.getCommonData().setExp(currentExp + reward);
/*     */       
/* 318 */       PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.EXP(Long.toString(reward)));
/*     */ 
/*     */       
/* 321 */       int currentDp = member.getCommonData().getDp();
/* 322 */       int dpReward = StatFunctions.calculateGroupDPReward(member, (Creature)owner);
/* 323 */       member.getCommonData().setDp(dpReward + currentDp);
/*     */ 
/*     */       
/* 326 */       if (apRewardPerMember > 0) {
/* 327 */         member.getCommonData().addAp(Math.round(apRewardPerMember * member.getRates().getApNpcRate()));
/*     */       }
/* 329 */       QuestEngine.getInstance().onKill(new QuestEnv((VisibleObject)owner, member, Integer.valueOf(0), Integer.valueOf(0)));
/*     */     } 
/*     */ 
/*     */     
/* 333 */     setInRangePlayers(players);
/* 334 */     Player leader = group.getGroupLeader();
/* 335 */     DropService.getInstance().registerDrop((Npc)owner, leader, highestLevel);
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
/*     */   public void showBrand(PlayerGroup playerGroup, int brandId, int targetObjectId) {
/* 347 */     for (Player member : playerGroup.getMembers())
/*     */     {
/* 349 */       PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_SHOW_BRAND(brandId, targetObjectId));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void disbandGroup(PlayerGroup group) {
/* 358 */     IDFactory.getInstance().releaseId(group.getGroupId());
/* 359 */     group.getGroupLeader().setPlayerGroup(null);
/* 360 */     PacketSendUtility.sendPacket(group.getGroupLeader(), (AionServerPacket)SM_SYSTEM_MESSAGE.DISBAND_GROUP());
/* 361 */     group.disband();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLogin(Player activePlayer) {
/* 369 */     PlayerGroup group = activePlayer.getPlayerGroup();
/*     */ 
/*     */     
/* 372 */     PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_GROUP_INFO(group));
/* 373 */     for (Player member : group.getMembers()) {
/*     */       
/* 375 */       if (!activePlayer.equals(member)) {
/* 376 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_GROUP_MEMBER_INFO(group, member, GroupEvent.ENTER));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPlayerGroupCache(int playerObjId, ScheduledFuture<?> future) {
/* 386 */     if (!this.playerGroup.containsKey(Integer.valueOf(playerObjId))) {
/* 387 */       this.playerGroup.put(Integer.valueOf(playerObjId), future);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cancelScheduleRemove(int playerObjId) {
/* 397 */     if (this.playerGroup.containsKey(Integer.valueOf(playerObjId))) {
/*     */       
/* 399 */       ((ScheduledFuture)this.playerGroup.get(Integer.valueOf(playerObjId))).cancel(true);
/* 400 */       this.playerGroup.remove(Integer.valueOf(playerObjId));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scheduleRemove(final Player player) {
/* 411 */     ScheduledFuture<?> future = ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 415 */             GroupService.this.removePlayerFromGroup(player);
/* 416 */             GroupService.this.playerGroup.remove(Integer.valueOf(player.getObjectId()));
/*     */           }
/*     */         },  (GroupConfig.GROUP_REMOVE_TIME * 1000));
/* 419 */     addPlayerGroupCache(player.getObjectId(), future);
/* 420 */     player.getPlayerGroup().getMembers().remove(Integer.valueOf(player.getObjectId()));
/*     */     
/* 422 */     for (Player groupMember : player.getPlayerGroup().getMembers())
/*     */     {
/*     */       
/* 425 */       PacketSendUtility.sendPacket(groupMember, (AionServerPacket)SM_SYSTEM_MESSAGE.PARTY_HE_BECOME_OFFLINE(player.getName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroup(Player player) {
/* 434 */     if (!isGroupMember(player.getObjectId())) {
/*     */       return;
/*     */     }
/* 437 */     PlayerGroup group = getGroup(player.getObjectId());
/* 438 */     if (group.size() < 2) {
/*     */       
/* 440 */       removeGroupMemberFromCache(player.getObjectId());
/* 441 */       cancelScheduleRemove(player.getObjectId());
/*     */       return;
/*     */     } 
/* 444 */     player.setPlayerGroup(group);
/* 445 */     group.onGroupMemberLogIn(player);
/* 446 */     cancelScheduleRemove(player.getObjectId());
/* 447 */     if (group.getGroupLeader().getObjectId() == player.getObjectId()) {
/* 448 */       group.setGroupLeader(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> getMembersToRegistrateByRules(Player player, PlayerGroup group, Npc npc) {
/*     */     int roundRobinMember;
/* 456 */     LootGroupRules lootRules = group.getLootGroupRules();
/* 457 */     LootRuleType lootRule = lootRules.getLootRule();
/* 458 */     List<Integer> luckyMembers = new ArrayList<Integer>();
/*     */     
/* 460 */     switch (lootRule) {
/*     */       
/*     */       case NORMAL:
/* 463 */         roundRobinMember = group.getRoundRobinMember(npc);
/* 464 */         if (roundRobinMember != 0) {
/*     */           
/* 466 */           luckyMembers.add(Integer.valueOf(roundRobinMember));
/*     */           break;
/*     */         } 
/*     */       case ROLL_DICE:
/* 470 */         luckyMembers = getGroupMembers(group, false);
/*     */         break;
/*     */       case BID:
/* 473 */         luckyMembers.add(Integer.valueOf(group.getGroupLeader().getObjectId()));
/*     */         break;
/*     */     } 
/* 476 */     return luckyMembers;
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
/*     */   public List<Integer> getGroupMembers(PlayerGroup group, boolean except) {
/* 488 */     List<Integer> luckyMembers = new ArrayList<Integer>();
/* 489 */     for (Iterator<Integer> i$ = group.getMemberObjIds().iterator(); i$.hasNext(); ) { int memberObjId = ((Integer)i$.next()).intValue();
/*     */       
/* 491 */       if (except) {
/*     */         
/* 493 */         if (group.getGroupLeader().getObjectId() != memberObjId)
/* 494 */           luckyMembers.add(Integer.valueOf(memberObjId)); 
/*     */         continue;
/*     */       } 
/* 497 */       luckyMembers.add(Integer.valueOf(memberObjId)); }
/*     */     
/* 499 */     return luckyMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getLuckyPlayer(Player player) {
/* 507 */     PlayerGroup group = player.getPlayerGroup();
/* 508 */     switch (group.getLootGroupRules().getAutodistribution()) {
/*     */       
/*     */       case NORMAL:
/* 511 */         return player;
/*     */       
/*     */       case ROLL_DICE:
/* 514 */         return player;
/*     */       
/*     */       case BID:
/* 517 */         return player;
/*     */     } 
/* 519 */     return player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInRangePlayers(List<Player> inRangePlayers) {
/* 527 */     this.inRangePlayers = inRangePlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Player> getInRangePlayers() {
/* 535 */     return this.inRangePlayers;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 541 */     protected static final GroupService instance = new GroupService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\GroupService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
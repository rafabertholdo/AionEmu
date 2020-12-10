/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.GroupConfig;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Monster;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEAVE_GROUP_MEMBER;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_ID;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
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
/*     */ public class AllianceService
/*     */ {
/*  59 */   private static final Logger log = Logger.getLogger(AllianceService.class);
/*     */   
/*     */   private FastMap<Integer, ScheduledFuture<?>> playerAllianceRemovalTasks;
/*     */   
/*     */   private final FastMap<Integer, PlayerAlliance> allianceMembers;
/*     */ 
/*     */   
/*     */   public static final AllianceService getInstance() {
/*  67 */     return SingletonHolder.instance;
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
/*     */ 
/*     */   
/*     */   public AllianceService() {
/*  82 */     this.allianceMembers = new FastMap();
/*  83 */     this.playerAllianceRemovalTasks = new FastMap();
/*     */     
/*  85 */     log.info("AllianceService: Initialized.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAllianceMemberToCache(Player player) {
/*  95 */     if (!this.allianceMembers.containsKey(Integer.valueOf(player.getObjectId()))) {
/*  96 */       this.allianceMembers.put(Integer.valueOf(player.getObjectId()), player.getPlayerAlliance());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeAllianceMemberFromCache(int playerObjId) {
/* 105 */     if (this.allianceMembers.containsKey(Integer.valueOf(playerObjId))) {
/* 106 */       this.allianceMembers.remove(Integer.valueOf(playerObjId));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAllianceMember(int playerObjId) {
/* 116 */     return this.allianceMembers.containsKey(Integer.valueOf(playerObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerAlliance getPlayerAlliance(int playerObjId) {
/* 127 */     return (PlayerAlliance)this.allianceMembers.get(Integer.valueOf(playerObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAllianceRemovalTask(int playerObjectId, ScheduledFuture<?> task) {
/* 137 */     if (!this.playerAllianceRemovalTasks.containsKey(Integer.valueOf(playerObjectId))) {
/* 138 */       this.playerAllianceRemovalTasks.put(Integer.valueOf(playerObjectId), task);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cancelRemovalTask(int playerObjectId) {
/* 147 */     if (this.playerAllianceRemovalTasks.containsKey(Integer.valueOf(playerObjectId))) {
/*     */       
/* 149 */       ((ScheduledFuture)this.playerAllianceRemovalTasks.get(Integer.valueOf(playerObjectId))).cancel(true);
/* 150 */       this.playerAllianceRemovalTasks.remove(Integer.valueOf(playerObjectId));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scheduleRemove(final Player player) {
/* 160 */     ScheduledFuture<?> future = ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 164 */             AllianceService.this.removeMemberFromAlliance(player.getPlayerAlliance(), player.getObjectId(), PlayerAllianceEvent.LEAVE_TIMEOUT, new String[0]);
/*     */           }
/*     */         }(GroupConfig.ALLIANCE_REMOVE_TIME * 1000));
/*     */     
/* 168 */     addAllianceRemovalTask(player.getObjectId(), future);
/* 169 */     player.getPlayerAlliance().onPlayerDisconnect(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invitePlayerToAlliance(Player inviter, Player invited) {
/* 179 */     if (RestrictionsManager.canInviteToAlliance(inviter, invited)) {
/*     */       
/* 181 */       RequestResponseHandler responseHandler = getResponseHandler(inviter, invited);
/*     */       
/* 183 */       boolean result = invited.getResponseRequester().putRequest(70004, responseHandler);
/*     */       
/* 185 */       if (result) {
/*     */         
/* 187 */         if (invited.isInGroup()) {
/*     */           
/* 189 */           PacketSendUtility.sendPacket(inviter, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_INVITED_HIS_PARTY(invited.getName()));
/*     */         }
/*     */         else {
/*     */           
/* 193 */           PacketSendUtility.sendPacket(inviter, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_INVITED_HIM(invited.getName()));
/*     */         } 
/*     */         
/* 196 */         PacketSendUtility.sendPacket(invited, (AionServerPacket)new SM_QUESTION_WINDOW(70004, 0, new Object[] { inviter.getName() }));
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
/*     */   private RequestResponseHandler getResponseHandler(final Player inviter, final Player invited) {
/* 208 */     RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)inviter)
/*     */       {
/*     */         public void acceptRequest(Creature requester, Player responder)
/*     */         {
/* 212 */           List<Player> playersToAdd = new ArrayList<Player>();
/* 213 */           PlayerAlliance alliance = inviter.getPlayerAlliance();
/*     */           
/* 215 */           if (alliance == null) {
/*     */             
/* 217 */             alliance = new PlayerAlliance(IDFactory.getInstance().nextId(), inviter.getObjectId());
/*     */ 
/*     */             
/* 220 */             if (inviter.isInGroup()) {
/*     */               
/* 222 */               PlayerGroup group = inviter.getPlayerGroup();
/* 223 */               playersToAdd.addAll(group.getMembers());
/*     */               
/* 225 */               for (Player member : group.getMembers()) {
/* 226 */                 GroupService.getInstance().removePlayerFromGroup(member);
/*     */               }
/*     */             } else {
/*     */               
/* 230 */               playersToAdd.add(inviter);
/*     */             } 
/*     */           } else {
/* 233 */             if (alliance.size() == 24) {
/*     */               
/* 235 */               PacketSendUtility.sendMessage(invited, "That alliance is already full.");
/* 236 */               PacketSendUtility.sendMessage(inviter, "Your alliance is already full.");
/*     */               return;
/*     */             } 
/* 239 */             if (invited.isInGroup() && invited.getPlayerGroup().size() + alliance.size() > 24) {
/*     */               
/* 241 */               PacketSendUtility.sendMessage(invited, "That alliance is now too full for your group to join.");
/* 242 */               PacketSendUtility.sendMessage(inviter, "Your alliance is now too full for that group to join.");
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/* 247 */           if (invited.isInGroup()) {
/*     */             
/* 249 */             PlayerGroup group = invited.getPlayerGroup();
/* 250 */             playersToAdd.addAll(group.getMembers());
/*     */             
/* 252 */             for (Player member : group.getMembers()) {
/* 253 */               GroupService.getInstance().removePlayerFromGroup(member);
/*     */             }
/*     */           } else {
/*     */             
/* 257 */             playersToAdd.add(invited);
/*     */           } 
/*     */ 
/*     */           
/* 261 */           for (Player member : playersToAdd)
/*     */           {
/* 263 */             AllianceService.this.addMemberToAlliance(alliance, member);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void denyRequest(Creature requester, Player responder) {
/* 270 */           PacketSendUtility.sendPacket(inviter, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_HE_REJECT_INVITATION(responder.getName()));
/*     */         }
/*     */       };
/* 273 */     return responseHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addMemberToAlliance(PlayerAlliance alliance, Player newMember) {
/* 283 */     alliance.addMember(newMember);
/* 284 */     addAllianceMemberToCache(newMember);
/*     */     
/* 286 */     PacketSendUtility.sendPacket(newMember, (AionServerPacket)new SM_ALLIANCE_INFO(alliance));
/* 287 */     PacketSendUtility.sendPacket(newMember, (AionServerPacket)new SM_SHOW_BRAND(0, 0));
/* 288 */     PacketSendUtility.sendPacket(newMember, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_ENTERED_FORCE());
/*     */     
/* 290 */     broadcastAllianceMemberInfo(alliance, newMember.getObjectId(), PlayerAllianceEvent.ENTER, new String[0]);
/* 291 */     sendOtherMemberInfo(alliance, newMember);
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
/*     */   public void handleGroupChange(PlayerAlliance alliance, int playerObjectId, int allianceGroupId, int secondObjectId) {
/* 304 */     if (allianceGroupId == 0) {
/*     */       
/* 306 */       alliance.swapPlayers(playerObjectId, secondObjectId);
/*     */       
/* 308 */       broadcastAllianceMemberInfo(alliance, playerObjectId, PlayerAllianceEvent.MEMBER_GROUP_CHANGE, new String[0]);
/* 309 */       broadcastAllianceMemberInfo(alliance, secondObjectId, PlayerAllianceEvent.MEMBER_GROUP_CHANGE, new String[0]);
/*     */     }
/*     */     else {
/*     */       
/* 313 */       alliance.setAllianceGroupFor(playerObjectId, allianceGroupId);
/* 314 */       broadcastAllianceMemberInfo(alliance, playerObjectId, PlayerAllianceEvent.MEMBER_GROUP_CHANGE, new String[0]);
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
/*     */   private void broadcastAllianceMemberInfo(PlayerAlliance alliance, int playerObjectId, PlayerAllianceEvent event, String... params) {
/* 326 */     PlayerAllianceMember memberToUpdate = alliance.getPlayer(playerObjectId);
/* 327 */     broadcastAllianceMemberInfo(alliance, memberToUpdate, event, params);
/*     */   }
/*     */ 
/*     */   
/*     */   private void broadcastAllianceMemberInfo(PlayerAlliance alliance, PlayerAllianceMember memberToUpdate, PlayerAllianceEvent event, String... params) {
/* 332 */     for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */       
/* 334 */       if (!allianceMember.isOnline())
/* 335 */         continue;  Player member = allianceMember.getPlayer();
/* 336 */       PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_ALLIANCE_MEMBER_INFO(memberToUpdate, event));
/* 337 */       PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_PLAYER_ID((AionObject)memberToUpdate));
/* 338 */       switch (event) {
/*     */         
/*     */         case ENTER:
/* 341 */           if (!member.equals(memberToUpdate.getPlayer())) {
/* 342 */             PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_HE_ENTERED_FORCE(memberToUpdate.getName()));
/*     */           }
/*     */         case LEAVE:
/* 345 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_HE_LEAVED_PARTY(memberToUpdate.getName()));
/*     */         
/*     */         case LEAVE_TIMEOUT:
/* 348 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_HE_BECOME_OFFLINE_TIMEOUT(memberToUpdate.getName()));
/*     */         
/*     */         case BANNED:
/* 351 */           if (member.equals(memberToUpdate.getPlayer())) {
/* 352 */             PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_BAN_ME(params[0])); continue;
/*     */           } 
/* 354 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_BAN_HIM(params[0], memberToUpdate.getName()));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastAllianceInfo(PlayerAlliance alliance, PlayerAllianceEvent event, String... params) {
/* 370 */     SM_ALLIANCE_INFO packet = new SM_ALLIANCE_INFO(alliance);
/* 371 */     for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */       
/* 373 */       if (!allianceMember.isOnline())
/* 374 */         continue;  Player member = allianceMember.getPlayer();
/* 375 */       PacketSendUtility.sendPacket(member, (AionServerPacket)packet);
/* 376 */       switch (event) {
/*     */         
/*     */         case APPOINT_CAPTAIN:
/* 379 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_CHANGE_LEADER(params[0], alliance.getCaptain().getName()));
/*     */         
/*     */         case APPOINT_VICE_CAPTAIN:
/* 382 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_PROMOTE_MANAGER(params[0]));
/*     */         
/*     */         case DEMOTE_VICE_CAPTAIN:
/* 385 */           PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FORCE_DEMOTE_MANAGER(params[0]));
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
/*     */   private void sendOtherMemberInfo(PlayerAlliance alliance, Player memberToSend) {
/* 398 */     for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */       
/* 400 */       if (!allianceMember.isOnline() || 
/* 401 */         memberToSend.getObjectId() == allianceMember.getObjectId())
/* 402 */         continue;  PacketSendUtility.sendPacket(memberToSend, (AionServerPacket)new SM_ALLIANCE_MEMBER_INFO(allianceMember, PlayerAllianceEvent.UPDATE));
/* 403 */       PacketSendUtility.sendPacket(memberToSend, (AionServerPacket)new SM_PLAYER_ID((AionObject)allianceMember));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playerStatusInfo(Player actingMember, int status, int playerObjId) {
/*     */     String oldLeader;
/* 414 */     PlayerAlliance alliance = actingMember.getPlayerAlliance();
/*     */     
/* 416 */     if (alliance == null)
/*     */     {
/* 418 */       PacketSendUtility.sendMessage(actingMember, "Your alliance is null...");
/*     */     }
/*     */     
/* 421 */     switch (status) {
/*     */       
/*     */       case 12:
/* 424 */         removeMemberFromAlliance(alliance, actingMember.getObjectId(), PlayerAllianceEvent.LEAVE, new String[0]);
/*     */         break;
/*     */       case 14:
/* 427 */         removeMemberFromAlliance(alliance, playerObjId, PlayerAllianceEvent.BANNED, new String[] { actingMember.getName() });
/*     */         break;
/*     */       case 15:
/* 430 */         oldLeader = alliance.getCaptain().getName();
/* 431 */         alliance.setLeader(playerObjId);
/* 432 */         broadcastAllianceInfo(alliance, PlayerAllianceEvent.APPOINT_CAPTAIN, new String[] { oldLeader, alliance.getCaptain().getName() });
/*     */         break;
/*     */       case 19:
/* 435 */         PacketSendUtility.sendMessage(actingMember, "Readiness check is not implmeneted yet. (ID: " + playerObjId + ")");
/*     */         break;
/*     */       case 23:
/* 438 */         alliance.promoteViceLeader(playerObjId);
/* 439 */         broadcastAllianceInfo(alliance, PlayerAllianceEvent.APPOINT_VICE_CAPTAIN, new String[] { alliance.getPlayer(playerObjId).getName() });
/*     */         break;
/*     */       case 24:
/* 442 */         alliance.demoteViceLeader(playerObjId);
/* 443 */         broadcastAllianceInfo(alliance, PlayerAllianceEvent.DEMOTE_VICE_CAPTAIN, new String[] { alliance.getPlayer(playerObjId).getName() });
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
/*     */   private void removeMemberFromAlliance(PlayerAlliance alliance, int memberObjectId, PlayerAllianceEvent event, String... params) {
/* 457 */     PlayerAllianceMember allianceMember = alliance.getPlayer(memberObjectId);
/*     */ 
/*     */     
/* 460 */     if (allianceMember == null) {
/*     */       return;
/*     */     }
/* 463 */     if (allianceMember.isOnline()) {
/*     */       
/* 465 */       allianceMember.getPlayer().setPlayerAlliance(null);
/* 466 */       PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket)new SM_LEAVE_GROUP_MEMBER());
/*     */     } 
/*     */ 
/*     */     
/* 470 */     alliance.removeMember(memberObjectId);
/* 471 */     broadcastAllianceMemberInfo(alliance, allianceMember, event, params);
/* 472 */     removeAllianceMemberFromCache(memberObjectId);
/*     */ 
/*     */     
/* 475 */     if (alliance.size() == 1) {
/*     */       
/* 477 */       Player player = alliance.getCaptain().getPlayer();
/* 478 */       removeMemberFromAlliance(alliance, alliance.getCaptainObjectId(), event, new String[0]);
/* 479 */       if (player != null) {
/* 480 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_DISPERSED());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlliance(Player player) {
/* 491 */     if (!isAllianceMember(player.getObjectId())) {
/*     */       return;
/*     */     }
/* 494 */     PlayerAlliance alliance = getPlayerAlliance(player.getObjectId());
/*     */ 
/*     */     
/* 497 */     if (alliance.size() == 0) {
/*     */       
/* 499 */       removeAllianceMemberFromCache(player.getObjectId());
/*     */       
/*     */       return;
/*     */     } 
/* 503 */     player.setPlayerAlliance(alliance);
/* 504 */     cancelRemovalTask(player.getObjectId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLogin(Player player) {
/* 514 */     PlayerAlliance alliance = player.getPlayerAlliance();
/*     */     
/* 516 */     alliance.onPlayerLogin(player);
/*     */ 
/*     */     
/* 519 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAYER_INFO(player, false));
/*     */     
/* 521 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ALLIANCE_INFO(alliance));
/* 522 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SHOW_BRAND(0, 0));
/*     */     
/* 524 */     broadcastAllianceMemberInfo(alliance, player.getObjectId(), PlayerAllianceEvent.RECONNECT, new String[0]);
/* 525 */     sendOtherMemberInfo(alliance, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLogout(Player player) {
/* 533 */     scheduleRemove(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAllianceUIToEvent(Player player, PlayerAllianceEvent event) {
/*     */     SM_ALLIANCE_MEMBER_INFO packet;
/* 544 */     PlayerAlliance alliance = player.getPlayerAlliance();
/* 545 */     switch (event) {
/*     */       
/*     */       case MOVEMENT:
/*     */       case UPDATE:
/* 549 */         packet = new SM_ALLIANCE_MEMBER_INFO(alliance.getPlayer(player.getObjectId()), event);
/* 550 */         for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */           
/* 552 */           if (allianceMember.isOnline() && !player.equals(allianceMember.getPlayer())) {
/* 553 */             PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket)packet);
/*     */           }
/*     */         } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void showBrand(PlayerAlliance alliance, int brandId, int targetObjectId) {
/* 572 */     for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */       
/* 574 */       if (!allianceMember.isOnline())
/* 575 */         continue;  PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket)new SM_SHOW_BRAND(brandId, targetObjectId));
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
/*     */ 
/*     */   
/*     */   public void doReward(PlayerAlliance alliance, Monster owner) {
/* 590 */     List<Player> players = new ArrayList<Player>();
/* 591 */     int partyLvlSum = 0;
/* 592 */     int highestLevel = 0;
/* 593 */     for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */       
/* 595 */       if (!allianceMember.isOnline())
/* 596 */         continue;  Player member = allianceMember.getPlayer();
/* 597 */       if (MathUtil.isIn3dRange((VisibleObject)member, (VisibleObject)owner, GroupConfig.GROUP_MAX_DISTANCE)) {
/*     */         
/* 599 */         if (member.getLifeStats().isAlreadyDead())
/*     */           continue; 
/* 601 */         players.add(member);
/* 602 */         partyLvlSum += member.getLevel();
/* 603 */         if (member.getLevel() > highestLevel) {
/* 604 */           highestLevel = member.getLevel();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 609 */     if (players.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 613 */     int apRewardPerMember = 0;
/* 614 */     WorldType worldType = owner.getWorldType();
/*     */ 
/*     */     
/* 617 */     if (worldType == WorldType.ABYSS)
/*     */     {
/*     */       
/* 620 */       apRewardPerMember = Math.round((StatFunctions.calculateGroupAPReward(highestLevel, (Creature)owner) / players.size()));
/*     */     }
/*     */ 
/*     */     
/* 624 */     long expReward = StatFunctions.calculateGroupExperienceReward(highestLevel, (Creature)owner);
/*     */ 
/*     */ 
/*     */     
/* 628 */     double mod = 1.0D;
/* 629 */     if (players.size() == 0)
/*     */       return; 
/* 631 */     if (players.size() > 1) {
/* 632 */       mod = (1 + (players.size() - 1) * 10 / 100);
/*     */     }
/* 634 */     expReward = (long)(expReward * mod);
/*     */     
/* 636 */     for (Player member : players) {
/*     */ 
/*     */       
/* 639 */       long currentExp = member.getCommonData().getExp();
/* 640 */       long reward = expReward * member.getLevel() / partyLvlSum;
/* 641 */       reward *= member.getRates().getGroupXpRate();
/* 642 */       member.getCommonData().setExp(currentExp + reward);
/*     */       
/* 644 */       PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.EXP(Long.toString(reward)));
/*     */ 
/*     */       
/* 647 */       int currentDp = member.getCommonData().getDp();
/* 648 */       int dpReward = StatFunctions.calculateGroupDPReward(member, (Creature)owner);
/* 649 */       member.getCommonData().setDp(dpReward + currentDp);
/*     */ 
/*     */       
/* 652 */       if (apRewardPerMember > 0) {
/* 653 */         member.getCommonData().addAp(Math.round(apRewardPerMember * member.getRates().getApNpcRate()));
/*     */       }
/* 655 */       QuestEngine.getInstance().onKill(new QuestEnv((VisibleObject)owner, member, Integer.valueOf(0), Integer.valueOf(0)));
/*     */     } 
/*     */ 
/*     */     
/* 659 */     Player leader = alliance.getCaptain().getPlayer();
/*     */ 
/*     */     
/* 662 */     if (leader == null)
/*     */       return; 
/* 664 */     DropService.getInstance().registerDrop((Npc)owner, leader, highestLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 670 */     protected static final AllianceService instance = new AllianceService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\AllianceService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
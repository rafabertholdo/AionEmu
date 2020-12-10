/*      */ package com.aionemu.gameserver.services;
/*      */ 
/*      */ import com.aionemu.commons.database.dao.DAOManager;
/*      */ import com.aionemu.gameserver.configs.main.LegionConfig;
/*      */ import com.aionemu.gameserver.dao.LegionDAO;
/*      */ import com.aionemu.gameserver.dao.LegionMemberDAO;
/*      */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*      */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*      */ import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
/*      */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*      */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*      */ import com.aionemu.gameserver.model.gameobjects.player.StorageType;
/*      */ import com.aionemu.gameserver.model.legion.Legion;
/*      */ import com.aionemu.gameserver.model.legion.LegionEmblem;
/*      */ import com.aionemu.gameserver.model.legion.LegionHistory;
/*      */ import com.aionemu.gameserver.model.legion.LegionHistoryType;
/*      */ import com.aionemu.gameserver.model.legion.LegionMember;
/*      */ import com.aionemu.gameserver.model.legion.LegionMemberEx;
/*      */ import com.aionemu.gameserver.model.legion.LegionRank;
/*      */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_ADD_MEMBER;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_EDIT;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_INFO;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_LEAVE_MEMBER;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_MEMBERLIST;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_TABS;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_EMBLEM;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_MEMBER;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_NICKNAME;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_SELF_INTRO;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_TITLE;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*      */ import com.aionemu.gameserver.network.aion.serverpackets.SM_WAREHOUSE_INFO;
/*      */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*      */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*      */ import com.aionemu.gameserver.utils.Util;
/*      */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*      */ import com.aionemu.gameserver.world.World;
/*      */ import com.aionemu.gameserver.world.container.LegionContainer;
/*      */ import com.aionemu.gameserver.world.container.LegionMemberContainer;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LegionService
/*      */ {
/*   75 */   private static final Logger log = Logger.getLogger(LegionService.class);
/*      */   
/*   77 */   private final LegionContainer allCachedLegions = new LegionContainer();
/*   78 */   private final LegionMemberContainer allCachedLegionMembers = new LegionMemberContainer();
/*      */ 
/*      */   
/*      */   private World world;
/*      */ 
/*      */   
/*      */   private static final int MAX_LEGION_LEVEL = 5;
/*      */ 
/*      */   
/*      */   private static final int INVITE = 1;
/*      */ 
/*      */   
/*      */   private static final int KICK = 2;
/*      */ 
/*      */   
/*      */   private static final int WAREHOUSE = 3;
/*      */   
/*      */   private static final int ANNOUNCEMENT = 4;
/*      */   
/*      */   private static final int ARTIFACT = 5;
/*      */   
/*      */   private static final int GATEGUARDIAN = 6;
/*      */   
/*      */   private HashMap<Integer, Integer> legionRanking;
/*      */   
/*  103 */   private LegionRestrictions legionRestrictions = new LegionRestrictions();
/*      */ 
/*      */   
/*      */   public static final LegionService getInstance() {
/*  107 */     return SingletonHolder.instance;
/*      */   }
/*      */ 
/*      */   
/*      */   public LegionService() {
/*  112 */     this.world = World.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidName(String name) {
/*  124 */     return LegionConfig.LEGION_NAME_PATTERN.matcher(name).matches();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void storeLegion(Legion legion, boolean newLegion) {
/*  135 */     if (newLegion) {
/*      */       
/*  137 */       addCachedLegion(legion);
/*  138 */       ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).saveNewLegion(legion);
/*      */     }
/*      */     else {
/*      */       
/*  142 */       ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).storeLegion(legion);
/*  143 */       ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).storeLegionEmblem(legion.getLegionId(), legion.getLegionEmblem());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void storeLegion(Legion legion) {
/*  155 */     storeLegion(legion, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void storeLegionMember(LegionMember legionMember, boolean newMember) {
/*  166 */     if (newMember) {
/*      */       
/*  168 */       addCachedLegionMember(legionMember);
/*  169 */       ((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).saveNewLegionMember(legionMember);
/*      */     } else {
/*      */       
/*  172 */       ((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).storeLegionMember(legionMember.getObjectId(), legionMember);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void storeLegionMember(LegionMember legionMember) {
/*  183 */     storeLegionMember(legionMember, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void storeLegionMemberExInCache(Player player) {
/*  193 */     if (this.allCachedLegionMembers.containsEx(player.getObjectId())) {
/*      */       
/*  195 */       LegionMemberEx legionMemberEx = this.allCachedLegionMembers.getMemberEx(player.getObjectId());
/*  196 */       legionMemberEx.setNickname(player.getLegionMember().getNickname());
/*  197 */       legionMemberEx.setSelfIntro(player.getLegionMember().getSelfIntro());
/*  198 */       legionMemberEx.setPlayerClass(player.getPlayerClass());
/*  199 */       legionMemberEx.setExp(player.getCommonData().getExp());
/*  200 */       legionMemberEx.setLastOnline(player.getCommonData().getLastOnline());
/*  201 */       legionMemberEx.setWorldId(player.getPosition().getMapId());
/*  202 */       legionMemberEx.setOnline(false);
/*      */     }
/*      */     else {
/*      */       
/*  206 */       LegionMemberEx legionMemberEx = new LegionMemberEx(player, player.getLegionMember(), false);
/*  207 */       addCachedLegionMemberEx(legionMemberEx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Legion getCachedLegion(int legionId) {
/*  218 */     return this.allCachedLegions.get(legionId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Legion getCachedLegion(String legionName) {
/*  228 */     return this.allCachedLegions.get(legionName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<Legion> getCachedLegions() {
/*  238 */     return this.allCachedLegions.getLegions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addCachedLegion(Legion legion) {
/*  249 */     this.allCachedLegions.add(legion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addCachedLegionMember(LegionMember legionMember) {
/*  260 */     this.allCachedLegionMembers.addMember(legionMember);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addCachedLegionMemberEx(LegionMemberEx legionMemberEx) {
/*  271 */     this.allCachedLegionMembers.addMemberEx(legionMemberEx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteLegionFromDB(Legion legion) {
/*  282 */     this.allCachedLegions.remove(legion);
/*  283 */     ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).deleteLegion(legion.getLegionId());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteLegionMemberFromDB(LegionMemberEx legionMember) {
/*  293 */     this.allCachedLegionMembers.remove(legionMember);
/*  294 */     ((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).deleteLegionMember(legionMember.getObjectId());
/*  295 */     Legion legion = legionMember.getLegion();
/*  296 */     legion.deleteLegionMember(legionMember.getObjectId());
/*  297 */     addHistory(legion, legionMember.getName(), LegionHistoryType.KICK);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Legion getLegion(String legionName) {
/*  311 */     if (this.allCachedLegions.contains(legionName)) {
/*      */       
/*  313 */       Legion legion1 = getCachedLegion(legionName);
/*  314 */       return legion1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  320 */     Legion legion = ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadLegion(legionName);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  325 */     loadLegionInfo(legion);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  330 */     addCachedLegion(legion);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  335 */     return legion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Legion getLegion(int legionId) {
/*  349 */     if (this.allCachedLegions.contains(legionId)) {
/*      */       
/*  351 */       Legion legion1 = getCachedLegion(legionId);
/*  352 */       return legion1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  358 */     Legion legion = ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadLegion(legionId);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  363 */     loadLegionInfo(legion);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  368 */     addCachedLegion(legion);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  373 */     return legion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadLegionInfo(Legion legion) {
/*  386 */     if (legion == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  392 */     legion.setLegionMembers(((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).loadLegionMembers(legion.getLegionId()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  397 */     legion.setAnnouncementList(((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadAnnouncementList(legion.getLegionId()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     legion.setLegionEmblem(((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadLegionEmblem(legion.getLegionId()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  407 */     legion.setLegionWarehouse(((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadLegionStorage(legion));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  412 */     if (this.legionRanking == null) {
/*      */       
/*  414 */       int DELAY_LEGIONRANKING = LegionConfig.LEGION_RANKING_PERIODICUPDATE * 1000;
/*  415 */       ThreadPoolManager.getInstance().scheduleAtFixedRate(new LegionRankingUpdateTask(), DELAY_LEGIONRANKING, DELAY_LEGIONRANKING);
/*      */       
/*  417 */       setLegionRanking(((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadLegionRanking());
/*      */     } 
/*      */     
/*  420 */     if (this.legionRanking.containsKey(Integer.valueOf(legion.getLegionId()))) {
/*  421 */       legion.setLegionRank(((Integer)this.legionRanking.get(Integer.valueOf(legion.getLegionId()))).intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  426 */     ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadLegionHistory(legion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegionMember getLegionMember(int playerObjId) {
/*  437 */     LegionMember legionMember = null;
/*  438 */     if (this.allCachedLegionMembers.contains(playerObjId)) {
/*  439 */       legionMember = this.allCachedLegionMembers.getMember(playerObjId);
/*      */     } else {
/*      */       
/*  442 */       legionMember = ((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).loadLegionMember(playerObjId);
/*  443 */       if (legionMember != null) {
/*  444 */         addCachedLegionMember(legionMember);
/*      */       }
/*      */     } 
/*  447 */     if (legionMember != null && 
/*  448 */       checkDisband(legionMember.getLegion())) {
/*  449 */       return null;
/*      */     }
/*  451 */     return legionMember;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkDisband(Legion legion) {
/*  462 */     if (legion.isDisbanding())
/*      */     {
/*  464 */       if (System.currentTimeMillis() / 1000L > legion.getDisbandTime()) {
/*      */         
/*  466 */         disbandLegion(legion);
/*  467 */         return true;
/*      */       } 
/*      */     }
/*  470 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disbandLegion(Legion legion) {
/*  478 */     for (Integer memberObjId : legion.getLegionMembers())
/*      */     {
/*  480 */       this.allCachedLegionMembers.remove(getLegionMemberEx(memberObjId.intValue()));
/*      */     }
/*  482 */     updateAfterDisbandLegion(legion);
/*  483 */     deleteLegionFromDB(legion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LegionMemberEx getLegionMemberEx(int playerObjId) {
/*  494 */     if (this.allCachedLegionMembers.containsEx(playerObjId)) {
/*  495 */       return this.allCachedLegionMembers.getMemberEx(playerObjId);
/*      */     }
/*      */     
/*  498 */     LegionMemberEx legionMember = ((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).loadLegionMemberEx(playerObjId);
/*      */     
/*  500 */     addCachedLegionMemberEx(legionMember);
/*  501 */     return legionMember;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LegionMemberEx getLegionMemberEx(String playerName) {
/*  513 */     if (this.allCachedLegionMembers.containsEx(playerName)) {
/*  514 */       return this.allCachedLegionMembers.getMemberEx(playerName);
/*      */     }
/*      */     
/*  517 */     LegionMemberEx legionMember = ((LegionMemberDAO)DAOManager.getDAO(LegionMemberDAO.class)).loadLegionMemberEx(playerName);
/*  518 */     addCachedLegionMemberEx(legionMember);
/*  519 */     return legionMember;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requestDisbandLegion(Creature npc, Player activePlayer) {
/*  531 */     final Legion legion = activePlayer.getLegion();
/*  532 */     if (this.legionRestrictions.canDisbandLegion(activePlayer, legion)) {
/*      */       
/*  534 */       RequestResponseHandler disbandResponseHandler = new RequestResponseHandler(npc)
/*      */         {
/*      */           public void acceptRequest(Creature requester, Player responder)
/*      */           {
/*  538 */             int unixTime = (int)(System.currentTimeMillis() / 1000L + LegionConfig.LEGION_DISBAND_TIME);
/*  539 */             legion.setDisbandTime(unixTime);
/*  540 */             LegionService.this.updateMembersOfDisbandLegion(legion, unixTime);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void denyRequest(Creature requester, Player responder) {}
/*      */         };
/*  550 */       boolean disbandResult = activePlayer.getResponseRequester().putRequest(80008, disbandResponseHandler);
/*      */       
/*  552 */       if (disbandResult)
/*      */       {
/*  554 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_QUESTION_WINDOW(80008, 0, new Object[0]));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void createLegion(Player activePlayer, String legionName) {
/*  568 */     if (this.legionRestrictions.canCreateLegion(activePlayer, legionName)) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  573 */       Legion legion = new Legion(IDFactory.getInstance().nextId(), legionName);
/*  574 */       legion.addLegionMember(activePlayer.getObjectId());
/*  575 */       ItemService.decreaseKinah(activePlayer, LegionConfig.LEGION_CREATE_REQUIRED_KINAH);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  580 */       storeLegion(legion, true);
/*  581 */       addLegionMember(legion, activePlayer, LegionRank.BRIGADE_GENERAL);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  586 */       addHistory(legion, "", LegionHistoryType.CREATE);
/*  587 */       addHistory(legion, activePlayer.getName(), LegionHistoryType.JOIN);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  592 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CREATED(legion.getLegionName()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void invitePlayerToLegion(final Player activePlayer, final Player targetPlayer) {
/*  604 */     if (this.legionRestrictions.canInvitePlayer(activePlayer, targetPlayer)) {
/*      */       
/*  606 */       final Legion legion = activePlayer.getLegion();
/*      */       
/*  608 */       RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)activePlayer)
/*      */         {
/*      */           
/*      */           public void acceptRequest(Creature requester, Player responder)
/*      */           {
/*  613 */             if (!targetPlayer.getCommonData().isOnline()) {
/*      */               
/*  615 */               PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_INCORRECT_TARGET());
/*      */             }
/*      */             else {
/*      */               
/*  619 */               int playerObjId = targetPlayer.getObjectId();
/*  620 */               if (legion.addLegionMember(playerObjId)) {
/*      */ 
/*      */                 
/*  623 */                 LegionService.this.addLegionMember(legion, targetPlayer);
/*      */ 
/*      */                 
/*  626 */                 PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.NEW_MEMBER_JOINED(targetPlayer.getName()));
/*      */ 
/*      */ 
/*      */                 
/*  630 */                 LegionService.this.displayLegionMessage(targetPlayer, legion.getCurrentAnnouncement());
/*      */ 
/*      */                 
/*  633 */                 LegionService.this.addHistory(legion, targetPlayer.getName(), LegionHistoryType.JOIN);
/*      */               }
/*      */               else {
/*      */                 
/*  637 */                 PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CAN_NOT_ADD_MEMBER_ANY_MORE());
/*      */                 
/*  639 */                 targetPlayer.resetLegionMember();
/*      */               } 
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void denyRequest(Creature requester, Player responder) {
/*  648 */             PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.REJECTED_INVITE_REQUEST(targetPlayer.getName()));
/*      */           }
/*      */         };
/*      */ 
/*      */       
/*  653 */       boolean requested = targetPlayer.getResponseRequester().putRequest(80001, responseHandler);
/*      */ 
/*      */       
/*  656 */       if (!requested) {
/*      */         
/*  658 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_TARGET_BUSY());
/*      */       }
/*      */       else {
/*      */         
/*  662 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.SEND_INVITE_REQUEST(targetPlayer.getName()));
/*      */ 
/*      */ 
/*      */         
/*  666 */         PacketSendUtility.sendPacket(targetPlayer, (AionServerPacket)new SM_QUESTION_WINDOW(80001, 0, new Object[] { legion.getLegionName(), legion.getLegionLevel() + "", activePlayer.getName() }));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayLegionMessage(Player targetPlayer, Map.Entry<Timestamp, String> currentAnnouncement) {
/*  681 */     if (currentAnnouncement != null)
/*      */     {
/*  683 */       PacketSendUtility.sendPacket(targetPlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPLAY_ANNOUNCEMENT(currentAnnouncement.getValue(), (int)(((Timestamp)currentAnnouncement.getKey()).getTime() / 1000L), 2));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appointBrigadeGeneral(final Player activePlayer, final Player targetPlayer) {
/*  696 */     if (this.legionRestrictions.canAppointBrigadeGeneral(activePlayer, targetPlayer)) {
/*      */       
/*  698 */       final Legion legion = activePlayer.getLegion();
/*  699 */       RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)activePlayer)
/*      */         {
/*      */           
/*      */           public void acceptRequest(Creature requester, Player responder)
/*      */           {
/*  704 */             if (!targetPlayer.getCommonData().isOnline()) {
/*      */               
/*  706 */               PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_NO_SUCH_USER());
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  711 */               LegionMember legionMember = targetPlayer.getLegionMember();
/*  712 */               if (legionMember.getRank().getRankId() > LegionRank.BRIGADE_GENERAL.getRankId()) {
/*      */ 
/*      */                 
/*  715 */                 activePlayer.getLegionMember().setRank(LegionRank.CENTURION);
/*  716 */                 PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(activePlayer, 0, ""));
/*      */ 
/*      */ 
/*      */                 
/*  720 */                 legionMember.setRank(LegionRank.BRIGADE_GENERAL);
/*  721 */                 PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(targetPlayer, 1300273, targetPlayer.getName()));
/*      */ 
/*      */                 
/*  724 */                 LegionService.this.addHistory(legion, targetPlayer.getName(), LegionHistoryType.APPOINTED);
/*      */               } 
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void denyRequest(Creature requester, Player responder) {
/*  732 */             PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_HE_DECLINE_YOUR_OFFER(targetPlayer.getName()));
/*      */           }
/*      */         };
/*      */ 
/*      */       
/*  737 */       boolean requested = targetPlayer.getResponseRequester().putRequest(80011, responseHandler);
/*      */ 
/*      */       
/*  740 */       if (!requested) {
/*      */         
/*  742 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_SENT_CANT_OFFER_WHEN_HE_IS_QUESTION_ASKED());
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  747 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_SENT_OFFER_MSG_TO_HIM(targetPlayer.getName()));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  752 */         PacketSendUtility.sendPacket(targetPlayer, (AionServerPacket)new SM_QUESTION_WINDOW(80011, activePlayer.getObjectId(), new Object[0]));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appointRank(Player activePlayer, Player targetPlayer, int rank) {
/*  765 */     if (this.legionRestrictions.canAppointRank(activePlayer, targetPlayer)) {
/*      */       int msgId;
/*  767 */       Legion legion = activePlayer.getLegion();
/*      */       
/*  769 */       LegionMember legionMember = targetPlayer.getLegionMember();
/*  770 */       if (rank == LegionRank.CENTURION.getRankId() && legionMember.getRank() == LegionRank.LEGIONARY) {
/*      */ 
/*      */         
/*  773 */         legionMember.setRank(LegionRank.CENTURION);
/*  774 */         msgId = 1300267;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  779 */         legionMember.setRank(LegionRank.LEGIONARY);
/*  780 */         msgId = 1300268;
/*      */       } 
/*      */       
/*  783 */       PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(targetPlayer, msgId, targetPlayer.getName()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void changeSelfIntro(Player activePlayer, String newSelfIntro) {
/*  796 */     if (this.legionRestrictions.canChangeSelfIntro(activePlayer, newSelfIntro)) {
/*      */       
/*  798 */       LegionMember legionMember = activePlayer.getLegionMember();
/*  799 */       legionMember.setSelfIntro(newSelfIntro);
/*  800 */       PacketSendUtility.broadcastPacketToLegion(legionMember.getLegion(), (AionServerPacket)new SM_LEGION_UPDATE_SELF_INTRO(activePlayer.getObjectId(), newSelfIntro));
/*      */       
/*  802 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_WRITE_INTRO_DONE());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void changePermissions(Legion legion, int lP2, int cP1, int cP2) {
/*  813 */     if (legion.setLegionPermissions(lP2, cP1, cP2))
/*      */     {
/*  815 */       PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(2, legion));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void requestChangeLevel(Player activePlayer, long kinahAmount) {
/*  826 */     if (this.legionRestrictions.canChangeLevel(activePlayer, kinahAmount)) {
/*      */       
/*  828 */       Legion legion = activePlayer.getLegion();
/*  829 */       ItemService.decreaseKinah(activePlayer, legion.getKinahPrice());
/*  830 */       changeLevel(legion, legion.getLegionLevel() + 1, false);
/*  831 */       addHistory(legion, legion.getLegionLevel() + "", LegionHistoryType.LEVEL_UP);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void changeLevel(Legion legion, int newLevel, boolean save) {
/*  842 */     legion.setLegionLevel(newLevel);
/*  843 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(0, legion));
/*  844 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_LEVEL_UP(newLevel));
/*  845 */     if (save) {
/*  846 */       storeLegion(legion);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void changeNickname(Player activePlayer, Player targetPlayer, String newNickname) {
/*  857 */     Legion legion = activePlayer.getLegion();
/*  858 */     if (this.legionRestrictions.canChangeNickname(legion, targetPlayer.getObjectId(), newNickname)) {
/*      */       
/*  860 */       LegionMember targetLegionMember = targetPlayer.getLegionMember();
/*  861 */       targetLegionMember.setNickname(newNickname);
/*  862 */       PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_UPDATE_NICKNAME(targetPlayer.getObjectId(), newNickname));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateAfterDisbandLegion(Legion legion) {
/*  874 */     for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {
/*      */       
/*  876 */       PacketSendUtility.broadcastPacket(onlineLegionMember, (AionServerPacket)new SM_LEGION_UPDATE_TITLE(onlineLegionMember.getObjectId(), 0, "", 0), true);
/*      */       
/*  878 */       PacketSendUtility.sendPacket(onlineLegionMember, (AionServerPacket)new SM_LEGION_LEAVE_MEMBER(1300302, 0, legion.getLegionName()));
/*      */       
/*  880 */       onlineLegionMember.resetLegionMember();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMembersEmblem(Legion legion) {
/*  891 */     LegionEmblem legionEmblem = legion.getLegionEmblem();
/*  892 */     for (Player onlineLegionMember : legion.getOnlineLegionMembers())
/*      */     {
/*  894 */       PacketSendUtility.broadcastPacket(onlineLegionMember, (AionServerPacket)new SM_LEGION_UPDATE_EMBLEM(legion.getLegionId(), legionEmblem.getEmblemId(), legionEmblem.getColor_r(), legionEmblem.getColor_g(), legionEmblem.getColor_b()), true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMembersOfDisbandLegion(Legion legion, int unixTime) {
/*  908 */     for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {
/*      */       
/*  910 */       PacketSendUtility.sendPacket(onlineLegionMember, (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(onlineLegionMember, 1300303, unixTime + ""));
/*      */       
/*  912 */       PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(6, unixTime));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMembersOfRecreateLegion(Legion legion) {
/*  924 */     for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {
/*      */       
/*  926 */       PacketSendUtility.sendPacket(onlineLegionMember, (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(onlineLegionMember, 1300307, ""));
/*      */       
/*  928 */       PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(7));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void storeLegionEmblem(Player activePlayer, int legionId, int emblemId, int color_r, int color_g, int color_b) {
/*  941 */     if (this.legionRestrictions.canStoreLegionEmblem(activePlayer, legionId, emblemId)) {
/*      */       
/*  943 */       Legion legion = activePlayer.getLegion();
/*  944 */       if (legion.getLegionEmblem().isDefaultEmblem()) {
/*  945 */         addHistory(legion, "", LegionHistoryType.EMBLEM_REGISTER);
/*      */       } else {
/*  947 */         addHistory(legion, "", LegionHistoryType.EMBLEM_MODIFIED);
/*  948 */       }  ItemService.decreaseKinah(activePlayer, LegionConfig.LEGION_EMBLEM_REQUIRED_KINAH);
/*  949 */       legion.getLegionEmblem().setEmblem(emblemId, color_r, color_g, color_b);
/*  950 */       updateMembersEmblem(legion);
/*  951 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGED_EMBLEM());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<LegionMemberEx> loadLegionMemberExList(Legion legion) {
/*  960 */     ArrayList<LegionMemberEx> legionMembers = new ArrayList<LegionMemberEx>();
/*  961 */     for (Integer memberObjId : legion.getLegionMembers()) {
/*      */       LegionMemberEx legionMemberEx;
/*      */       
/*  964 */       Player memberPlayer = this.world.findPlayer(memberObjId.intValue());
/*  965 */       if (memberPlayer != null) {
/*      */         
/*  967 */         legionMemberEx = new LegionMemberEx(memberPlayer, memberPlayer.getLegionMember(), true);
/*      */       }
/*      */       else {
/*      */         
/*  971 */         legionMemberEx = getLegionMemberEx(memberObjId.intValue());
/*      */       } 
/*  973 */       legionMembers.add(legionMemberEx);
/*      */     } 
/*  975 */     return legionMembers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openLegionWarehouse(Player activePlayer) {
/*  983 */     if (this.legionRestrictions.canOpenWarehouse(activePlayer)) {
/*      */ 
/*      */ 
/*      */       
/*  987 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_DIALOG_WINDOW(activePlayer.getObjectId(), 25));
/*  988 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_WAREHOUSE_INFO(activePlayer.getLegion().getLegionWarehouse().getStorageItems(), StorageType.LEGION_WAREHOUSE.getId(), 0, true));
/*      */       
/*  990 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_WAREHOUSE_INFO(null, StorageType.LEGION_WAREHOUSE.getId(), 0, false));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recreateLegion(Npc npc, Player activePlayer) {
/* 1001 */     final Legion legion = activePlayer.getLegion();
/* 1002 */     if (this.legionRestrictions.canRecreateLegion(activePlayer, legion)) {
/*      */       
/* 1004 */       RequestResponseHandler disbandResponseHandler = new RequestResponseHandler((Creature)npc)
/*      */         {
/*      */           
/*      */           public void acceptRequest(Creature requester, Player responder)
/*      */           {
/* 1009 */             legion.setDisbandTime(0);
/* 1010 */             PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(7));
/* 1011 */             LegionService.this.updateMembersOfRecreateLegion(legion);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void denyRequest(Creature requester, Player responder) {}
/*      */         };
/* 1021 */       boolean disbandResult = activePlayer.getResponseRequester().putRequest(80009, disbandResponseHandler);
/*      */       
/* 1023 */       if (disbandResult)
/*      */       {
/* 1025 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_QUESTION_WINDOW(80009, 0, new Object[0]));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setLegionRanking(HashMap<Integer, Integer> legionRanking) {
/* 1037 */     this.legionRanking = legionRanking;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class LegionRankingUpdateTask
/*      */     implements Runnable
/*      */   {
/*      */     private LegionRankingUpdateTask() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/* 1050 */       LegionService.log.info("Legion ranking update task started");
/* 1051 */       long startTime = System.currentTimeMillis();
/* 1052 */       Collection<Legion> legions = LegionService.this.allCachedLegions.getLegions();
/* 1053 */       int legionsUpdated = 0;
/*      */       
/* 1055 */       LegionService.this.setLegionRanking(((LegionDAO)DAOManager.getDAO(LegionDAO.class)).loadLegionRanking());
/*      */       
/* 1057 */       for (Legion legion : legions) {
/*      */ 
/*      */         
/*      */         try {
/* 1061 */           if (LegionService.this.legionRanking.containsKey(Integer.valueOf(legion.getLegionId())))
/*      */           {
/* 1063 */             legion.setLegionRank(((Integer)LegionService.this.legionRanking.get(Integer.valueOf(legion.getLegionId()))).intValue());
/* 1064 */             PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(1, legion));
/*      */           }
/*      */         
/* 1067 */         } catch (Exception ex) {
/*      */           
/* 1069 */           LegionService.log.error("Exception during periodic update of legion ranking " + ex.getMessage());
/*      */         } 
/*      */         
/* 1072 */         legionsUpdated++;
/*      */       } 
/* 1074 */       long workTime = System.currentTimeMillis() - startTime;
/* 1075 */       LegionService.log.info("Legion ranking update: " + workTime + " ms, legions: " + legionsUpdated);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateMemberInfo(Player player) {
/* 1086 */     PacketSendUtility.broadcastPacketToLegion(player.getLegion(), (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(player, 0, ""));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addContributionPoints(Legion legion, int pointsGained) {
/* 1098 */     legion.addContributionPoints(pointsGained);
/* 1099 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(3, legion));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContributionPoints(Legion legion, int newPoints, boolean save) {
/* 1110 */     legion.setContributionPoints(newPoints);
/* 1111 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(3, legion));
/* 1112 */     if (save) {
/* 1113 */       storeLegion(legion);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uploadEmblemInfo(Player activePlayer, int totalSize) {
/* 1121 */     if (this.legionRestrictions.canUploadEmblemInfo(activePlayer)) {
/*      */       
/* 1123 */       LegionEmblem legionEmblem = activePlayer.getLegion().getLegionEmblem();
/* 1124 */       legionEmblem.setUploadSize(totalSize);
/* 1125 */       legionEmblem.setUploading(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uploadEmblemData(Player activePlayer, int size, byte[] data) {
/* 1135 */     if (this.legionRestrictions.canUploadEmblem(activePlayer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegionName(Legion legion, String newLegionName, boolean save) {
/* 1155 */     legion.setLegionName(newLegionName);
/* 1156 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_INFO(legion));
/*      */     
/* 1158 */     for (Player legionMember : legion.getOnlineLegionMembers())
/*      */     {
/* 1160 */       PacketSendUtility.broadcastPacket(legionMember, (AionServerPacket)new SM_LEGION_UPDATE_TITLE(legionMember.getObjectId(), legion.getLegionId(), legion.getLegionName(), legionMember.getLegionMember().getRank().getRankId()), true);
/*      */     }
/*      */ 
/*      */     
/* 1164 */     if (save) {
/* 1165 */       storeLegion(legion);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void changeAnnouncement(Player activePlayer, String announcement) {
/* 1177 */     if (this.legionRestrictions.canChangeAnnouncement(activePlayer.getLegionMember(), announcement)) {
/*      */       
/* 1179 */       Legion legion = activePlayer.getLegion();
/*      */       
/* 1181 */       Timestamp currentTime = new Timestamp(System.currentTimeMillis());
/* 1182 */       storeNewAnnouncement(legion.getLegionId(), currentTime, announcement);
/* 1183 */       legion.addAnnouncementToList(currentTime, announcement);
/* 1184 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_WRITE_NOTICE_DONE());
/* 1185 */       PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_EDIT(5, (int)(System.currentTimeMillis() / 1000L), announcement));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void storeLegionAnnouncements(Legion legion) {
/* 1197 */     for (int i = 0; i < legion.getAnnouncementList().size() - 7; i++) {
/*      */       
/* 1199 */       removeAnnouncement(legion.getLegionId(), (Timestamp)legion.getAnnouncementList().firstEntry().getKey());
/* 1200 */       legion.removeFirstEntry();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean storeNewAnnouncement(int legionId, Timestamp currentTime, String message) {
/* 1216 */     return ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).saveNewAnnouncement(legionId, currentTime, message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeAnnouncement(int legionId, Timestamp key) {
/* 1226 */     ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).removeAnnouncement(legionId, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addHistory(Legion legion, String text, LegionHistoryType legionHistoryType) {
/* 1237 */     LegionHistory legionHistory = new LegionHistory(legionHistoryType, text, new Timestamp(System.currentTimeMillis()));
/*      */     
/* 1239 */     legion.addHistory(legionHistory);
/* 1240 */     ((LegionDAO)DAOManager.getDAO(LegionDAO.class)).saveNewLegionHistory(legion.getLegionId(), legionHistory);
/*      */     
/* 1242 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_TABS(legion.getLegionHistory()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addLegionMember(Legion legion, Player player) {
/* 1253 */     addLegionMember(legion, player, LegionRank.LEGIONARY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addLegionMember(Legion legion, Player player, LegionRank rank) {
/* 1266 */     player.setLegionMember(new LegionMember(player.getObjectId(), legion, rank));
/* 1267 */     storeLegionMember(player.getLegionMember(), true);
/*      */ 
/*      */     
/* 1270 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LEGION_INFO(legion));
/*      */ 
/*      */     
/* 1273 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_ADD_MEMBER(player, false, 0, ""));
/*      */ 
/*      */     
/* 1276 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LEGION_MEMBERLIST(loadLegionMemberExList(legion)));
/*      */ 
/*      */     
/* 1279 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_LEGION_UPDATE_TITLE(player.getObjectId(), legion.getLegionId(), legion.getLegionName(), player.getLegionMember().getRank().getRankId()), true);
/*      */ 
/*      */ 
/*      */     
/* 1283 */     LegionEmblem legionEmblem = legion.getLegionEmblem();
/* 1284 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LEGION_UPDATE_EMBLEM(legion.getLegionId(), legionEmblem.getEmblemId(), legionEmblem.getColor_r(), legionEmblem.getColor_g(), legionEmblem.getColor_b()));
/*      */ 
/*      */ 
/*      */     
/* 1288 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LEGION_EDIT(8));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean removeLegionMember(String charName, boolean kick, String playerName) {
/* 1302 */     LegionMemberEx legionMember = getLegionMemberEx(charName);
/* 1303 */     if (legionMember == null) {
/*      */       
/* 1305 */       log.error("Char name does not exist in legion member table: " + charName);
/* 1306 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1312 */     deleteLegionMemberFromDB(legionMember);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1317 */     Player player = this.world.findPlayer(charName);
/* 1318 */     if (player != null)
/*      */     {
/* 1320 */       PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_LEGION_UPDATE_TITLE(player.getObjectId(), 0, "", 2), true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1326 */     if (kick) {
/*      */       
/* 1328 */       PacketSendUtility.broadcastPacketToLegion(legionMember.getLegion(), (AionServerPacket)new SM_LEGION_LEAVE_MEMBER(1300247, legionMember.getObjectId(), playerName, legionMember.getName()));
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1333 */       PacketSendUtility.broadcastPacketToLegion(legionMember.getLegion(), (AionServerPacket)new SM_LEGION_LEAVE_MEMBER(900699, legionMember.getObjectId(), charName));
/*      */     } 
/*      */ 
/*      */     
/* 1337 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCharNameRequest(int exOpcode, Player activePlayer, String charName, String newNickname, int rank) {
/* 1350 */     Legion legion = activePlayer.getLegion();
/*      */     
/* 1352 */     charName = Util.convertName(charName);
/* 1353 */     Player targetPlayer = this.world.findPlayer(charName);
/*      */     
/* 1355 */     switch (exOpcode) {
/*      */ 
/*      */       
/*      */       case 1:
/* 1359 */         if (targetPlayer != null) {
/*      */           
/* 1361 */           if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.GUILD)) {
/*      */             
/* 1363 */             PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_INVITE_GUILD(charName));
/*      */             
/*      */             return;
/*      */           } 
/* 1367 */           invitePlayerToLegion(activePlayer, targetPlayer);
/*      */           
/*      */           break;
/*      */         } 
/* 1371 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_NO_USER_TO_INVITE());
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1379 */         if (this.legionRestrictions.canKickPlayer(activePlayer, charName))
/*      */         {
/* 1381 */           if (removeLegionMember(charName, true, activePlayer.getName()))
/*      */           {
/*      */             
/* 1384 */             if (targetPlayer != null) {
/*      */               
/* 1386 */               PacketSendUtility.sendPacket(targetPlayer, (AionServerPacket)new SM_LEGION_LEAVE_MEMBER(1300246, 0, legion.getLegionName()));
/*      */               
/* 1388 */               targetPlayer.resetLegionMember();
/*      */             } 
/*      */           }
/*      */         }
/*      */         break;
/*      */       
/*      */       case 5:
/* 1395 */         if (targetPlayer != null) {
/*      */           
/* 1397 */           appointBrigadeGeneral(activePlayer, targetPlayer);
/*      */           
/*      */           break;
/*      */         } 
/* 1401 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_NO_SUCH_USER());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 6:
/* 1406 */         if (targetPlayer != null) {
/*      */           
/* 1408 */           appointRank(activePlayer, targetPlayer, rank);
/*      */           
/*      */           break;
/*      */         } 
/* 1412 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_NO_USER());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 15:
/* 1417 */         if (targetPlayer == null || targetPlayer.getLegion() != legion) {
/*      */           return;
/*      */         }
/* 1420 */         changeNickname(activePlayer, targetPlayer, newNickname);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleLegionRequest(int exOpcode, Player activePlayer, String text) {
/* 1434 */     switch (exOpcode) {
/*      */ 
/*      */       
/*      */       case 9:
/* 1438 */         changeAnnouncement(activePlayer, text);
/*      */         break;
/*      */       
/*      */       case 10:
/* 1442 */         changeSelfIntro(activePlayer, text);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleLegionRequest(int exOpcode, Player activePlayer) {
/* 1453 */     switch (exOpcode) {
/*      */ 
/*      */       
/*      */       case 2:
/* 1457 */         if (this.legionRestrictions.canLeave(activePlayer))
/*      */         {
/* 1459 */           if (removeLegionMember(activePlayer.getName(), false, "")) {
/*      */             
/* 1461 */             Legion legion = activePlayer.getLegion();
/* 1462 */             PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_LEGION_LEAVE_MEMBER(1300241, 0, legion.getLegionName()));
/*      */             
/* 1464 */             activePlayer.resetLegionMember();
/*      */           } 
/*      */         }
/*      */         break;
/*      */       
/*      */       case 14:
/* 1470 */         requestChangeLevel(activePlayer, activePlayer.getInventory().getKinahItem().getItemCount());
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLogin(Player activePlayer) {
/* 1480 */     Legion legion = activePlayer.getLegion();
/*      */ 
/*      */     
/* 1483 */     PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_LEGION_ADD_MEMBER(activePlayer, false, 0, ""));
/*      */ 
/*      */     
/* 1486 */     PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_LEGION_INFO(legion));
/*      */ 
/*      */     
/* 1489 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(activePlayer, 0, ""));
/*      */ 
/*      */     
/* 1492 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_NOTIFY_LOGIN_GUILD(activePlayer.getName()), activePlayer.getObjectId());
/*      */ 
/*      */ 
/*      */     
/* 1496 */     PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_LEGION_ADD_MEMBER(activePlayer, true, 0, ""));
/*      */ 
/*      */     
/* 1499 */     PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_LEGION_MEMBERLIST(loadLegionMemberExList(legion)));
/*      */ 
/*      */     
/* 1502 */     displayLegionMessage(activePlayer, legion.getCurrentAnnouncement());
/*      */     
/* 1504 */     if (legion.isDisbanding()) {
/* 1505 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_LEGION_EDIT(6, legion.getDisbandTime()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLogout(Player player) {
/* 1513 */     Legion legion = player.getLegion();
/* 1514 */     PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket)new SM_LEGION_UPDATE_MEMBER(player, 0, ""));
/* 1515 */     storeLegion(legion);
/* 1516 */     storeLegionMember(player.getLegionMember());
/* 1517 */     storeLegionMemberExInCache(player);
/* 1518 */     storeLegionAnnouncements(legion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class LegionRestrictions
/*      */   {
/*      */     private static final int MIN_EMBLEM_ID = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int MAX_EMBLEM_ID = 40;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private LegionRestrictions() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canCreateLegion(Player activePlayer, String legionName) {
/* 1542 */       if (!LegionService.this.isValidName(legionName)) {
/*      */         
/* 1544 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CREATE_INVALID_NAME());
/* 1545 */         return false;
/*      */       } 
/* 1547 */       if (!isFreeName(legionName)) {
/*      */         
/* 1549 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CREATE_NAME_EXISTS());
/* 1550 */         return false;
/*      */       } 
/* 1552 */       if (activePlayer.isLegionMember()) {
/*      */         
/* 1554 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CREATE_ALREADY_MEMBER());
/* 1555 */         return false;
/*      */       } 
/* 1557 */       if (activePlayer.getInventory().getKinahItem().getItemCount() < LegionConfig.LEGION_CREATE_REQUIRED_KINAH) {
/*      */         
/* 1559 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CREATE_NOT_ENOUGH_KINAH());
/* 1560 */         return false;
/*      */       } 
/* 1562 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canInvitePlayer(Player activePlayer, Player targetPlayer) {
/* 1574 */       Legion legion = activePlayer.getLegion();
/* 1575 */       if (activePlayer.getLifeStats().isAlreadyDead()) {
/*      */         
/* 1577 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CANT_INVITE_WHILE_DEAD());
/* 1578 */         return false;
/*      */       } 
/* 1580 */       if (isSelf(activePlayer, targetPlayer.getObjectId())) {
/*      */         
/* 1582 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CAN_NOT_INVITE_SELF());
/* 1583 */         return false;
/*      */       } 
/* 1585 */       if (targetPlayer.isLegionMember()) {
/*      */         
/* 1587 */         if (legion.isMember(targetPlayer.getObjectId())) {
/*      */           
/* 1589 */           PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_HE_IS_MY_GUILD_MEMBER(targetPlayer.getName()));
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1594 */           PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_HE_IS_OTHER_GUILD_MEMBER(targetPlayer.getName()));
/*      */         } 
/*      */         
/* 1597 */         return false;
/*      */       } 
/* 1599 */       if (!activePlayer.getLegionMember().hasRights(1))
/*      */       {
/*      */         
/* 1602 */         return false;
/*      */       }
/* 1604 */       if (activePlayer.getCommonData().getRace() != targetPlayer.getCommonData().getRace() && !LegionConfig.LEGION_INVITEOTHERFACTION)
/*      */       {
/*      */ 
/*      */         
/* 1608 */         return false;
/*      */       }
/* 1610 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canKickPlayer(Player activePlayer, String charName) {
/* 1625 */       LegionMemberEx legionMember = LegionService.this.getLegionMemberEx(charName);
/* 1626 */       if (legionMember == null) {
/*      */         
/* 1628 */         LegionService.log.error("Char name does not exist in legion member table: " + charName);
/* 1629 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 1633 */       Legion legion = activePlayer.getLegion();
/*      */       
/* 1635 */       if (isSelf(activePlayer, legionMember.getObjectId())) {
/*      */         
/* 1637 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CANT_KICK_YOURSELF());
/* 1638 */         return false;
/*      */       } 
/* 1640 */       if (legionMember.isBrigadeGeneral()) {
/*      */         
/* 1642 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CANT_KICK_BRIGADE_GENERAL());
/* 1643 */         return false;
/*      */       } 
/* 1645 */       if (legionMember.getRank() == activePlayer.getLegionMember().getRank())
/*      */       {
/*      */ 
/*      */         
/* 1649 */         return false;
/*      */       }
/* 1651 */       if (!legion.isMember(legionMember.getObjectId()))
/*      */       {
/*      */         
/* 1654 */         return false;
/*      */       }
/* 1656 */       if (!activePlayer.getLegionMember().hasRights(2))
/*      */       {
/*      */         
/* 1659 */         return false;
/*      */       }
/* 1661 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canAppointBrigadeGeneral(Player activePlayer, Player targetPlayer) {
/* 1673 */       Legion legion = activePlayer.getLegion();
/* 1674 */       if (!isBrigadeGeneral(activePlayer)) {
/*      */         
/* 1676 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_DONT_HAVE_RIGHT());
/*      */         
/* 1678 */         return false;
/*      */       } 
/* 1680 */       if (isSelf(activePlayer, targetPlayer.getObjectId())) {
/*      */         
/* 1682 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_ERROR_SELF());
/* 1683 */         return false;
/*      */       } 
/* 1685 */       if (!legion.isMember(targetPlayer.getObjectId()))
/*      */       {
/* 1687 */         return false; } 
/* 1688 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canAppointRank(Player activePlayer, Player targetPlayer) {
/* 1700 */       Legion legion = activePlayer.getLegion();
/* 1701 */       if (!isBrigadeGeneral(activePlayer)) {
/*      */         
/* 1703 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_DONT_HAVE_RIGHT());
/*      */         
/* 1705 */         return false;
/*      */       } 
/* 1707 */       if (isSelf(activePlayer, targetPlayer.getObjectId())) {
/*      */         
/* 1709 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_ERROR_SELF());
/* 1710 */         return false;
/*      */       } 
/* 1712 */       if (!legion.isMember(targetPlayer.getObjectId()))
/*      */       {
/*      */         
/* 1715 */         return false;
/*      */       }
/* 1717 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canChangeSelfIntro(Player activePlayer, String newSelfIntro) {
/* 1729 */       if (!isValidSelfIntro(newSelfIntro))
/* 1730 */         return false; 
/* 1731 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canChangeLevel(Player activePlayer, long kinahAmount) {
/* 1743 */       Legion legion = activePlayer.getLegion();
/* 1744 */       int levelContributionPrice = legion.getContributionPrice();
/*      */       
/* 1746 */       if (legion.getLegionLevel() == 5) {
/*      */         
/* 1748 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_CANT_LEVEL_UP());
/* 1749 */         return false;
/*      */       } 
/* 1751 */       if (activePlayer.getInventory().getKinahItem().getItemCount() < legion.getKinahPrice()) {
/*      */         
/* 1753 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_NOT_ENOUGH_KINAH());
/* 1754 */         return false;
/*      */       } 
/* 1756 */       if (!legion.hasRequiredMembers()) {
/*      */         
/* 1758 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_NOT_ENOUGH_MEMBER());
/* 1759 */         return false;
/*      */       } 
/* 1761 */       if (legion.getContributionPoints() < levelContributionPrice) {
/*      */         
/* 1763 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_NOT_ENOUGH_POINT());
/* 1764 */         return false;
/*      */       } 
/* 1766 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canChangeNickname(Legion legion, int targetObjectId, String newNickname) {
/* 1777 */       if (!isValidNickname(newNickname))
/*      */       {
/*      */         
/* 1780 */         return false;
/*      */       }
/* 1782 */       if (!legion.isMember(targetObjectId))
/*      */       {
/*      */         
/* 1785 */         return false;
/*      */       }
/* 1787 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canChangeAnnouncement(LegionMember legionMember, String announcement) {
/* 1799 */       if (!isValidAnnouncement(announcement) && legionMember.hasRights(4))
/* 1800 */         return false; 
/* 1801 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canDisbandLegion(Player activePlayer, Legion legion) {
/* 1815 */       if (!isBrigadeGeneral(activePlayer)) {
/*      */         
/* 1817 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_ONLY_MASTER_CAN_DISPERSE());
/*      */         
/* 1819 */         return false;
/*      */       } 
/* 1821 */       if (legion.getLegionWarehouse().size() > 0)
/*      */       {
/*      */         
/* 1824 */         return false;
/*      */       }
/* 1826 */       if (legion.isDisbanding()) {
/*      */         
/* 1828 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_ALREADY_REQUESTED());
/* 1829 */         return false;
/*      */       } 
/* 1831 */       if (legion.getLegionWarehouse().getStorageItems().size() > 0) {
/*      */         
/* 1833 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_CANT_DISPERSE_GUILD_STORE_ITEM_IN_WAREHOUSE());
/*      */         
/* 1835 */         return false;
/*      */       } 
/* 1837 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canLeave(Player activePlayer) {
/* 1848 */       if (isBrigadeGeneral(activePlayer)) {
/*      */         
/* 1850 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CANT_LEAVE_BEFORE_CHANGE_MASTER());
/* 1851 */         return false;
/*      */       } 
/* 1853 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canRecreateLegion(Player activePlayer, Legion legion) {
/* 1865 */       if (!isBrigadeGeneral(activePlayer)) {
/*      */         
/* 1867 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_ONLY_MASTER_CAN_DISPERSE());
/*      */         
/* 1869 */         return false;
/*      */       } 
/* 1871 */       if (!legion.isDisbanding())
/*      */       {
/*      */         
/* 1874 */         return false;
/*      */       }
/* 1876 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canUploadEmblemInfo(Player activePlayer) {
/* 1888 */       if (!isBrigadeGeneral(activePlayer))
/*      */       {
/* 1890 */         return false; } 
/* 1891 */       if (activePlayer.getLegion().getLegionLevel() < 3)
/*      */       {
/*      */         
/* 1894 */         return false;
/*      */       }
/* 1896 */       if (activePlayer.getLegion().getLegionEmblem().isUploading()) {
/*      */ 
/*      */         
/* 1899 */         activePlayer.getLegion().getLegionEmblem().setUploading(false);
/* 1900 */         return false;
/*      */       } 
/* 1902 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canUploadEmblem(Player activePlayer) {
/* 1913 */       if (!isBrigadeGeneral(activePlayer))
/*      */       {
/*      */         
/* 1916 */         return false;
/*      */       }
/* 1918 */       if (activePlayer.getLegion().getLegionLevel() < 3)
/*      */       {
/*      */         
/* 1921 */         return false;
/*      */       }
/* 1923 */       if (!activePlayer.getLegion().getLegionEmblem().isUploading())
/*      */       {
/*      */         
/* 1926 */         return false;
/*      */       }
/* 1928 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canOpenWarehouse(Player activePlayer) {
/* 1937 */       if (!activePlayer.isLegionMember())
/*      */       {
/*      */         
/* 1940 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1944 */       Legion legion = activePlayer.getLegion();
/* 1945 */       if (!activePlayer.getLegionMember().hasRights(3))
/*      */       {
/*      */         
/* 1948 */         return false;
/*      */       }
/* 1950 */       if (legion.isDisbanding()) {
/*      */         
/* 1952 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_WAREHOUSE_CANT_USE_WHILE_DISPERSE());
/*      */         
/* 1954 */         return false;
/*      */       } 
/* 1956 */       if (!LegionConfig.LEGION_WAREHOUSE)
/*      */       {
/*      */         
/* 1959 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1965 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_DIALOG_WINDOW(activePlayer.getObjectId(), 25));
/* 1966 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_WAREHOUSE_INFO(legion.getLegionWarehouse().getStorageItems(), StorageType.LEGION_WAREHOUSE.getId(), 0, true));
/*      */       
/* 1968 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_WAREHOUSE_INFO(null, StorageType.LEGION_WAREHOUSE.getId(), 0, false));
/*      */       
/* 1970 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canStoreLegionEmblem(Player activePlayer, int legionId, int emblemId) {
/* 1982 */       Legion legion = activePlayer.getLegion();
/* 1983 */       if (emblemId < 0 || emblemId > 40)
/*      */       {
/*      */         
/* 1986 */         return false;
/*      */       }
/* 1988 */       if (legionId != legion.getLegionId())
/*      */       {
/*      */         
/* 1991 */         return false;
/*      */       }
/* 1993 */       if (legion.getLegionLevel() < 2)
/*      */       {
/*      */         
/* 1996 */         return false;
/*      */       }
/* 1998 */       if (activePlayer.getInventory().getKinahItem().getItemCount() < LegionConfig.LEGION_EMBLEM_REQUIRED_KINAH) {
/*      */         
/* 2000 */         PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(LegionConfig.LEGION_EMBLEM_REQUIRED_KINAH));
/*      */         
/* 2002 */         return false;
/*      */       } 
/* 2004 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isBrigadeGeneral(Player player) {
/* 2016 */       return player.getLegionMember().isBrigadeGeneral();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isSelf(Player player, int targetObjId) {
/* 2029 */       return player.sameObjectId(targetObjId);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isFreeName(String name) {
/* 2041 */       return !((LegionDAO)DAOManager.getDAO(LegionDAO.class)).isNameUsed(name);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isValidSelfIntro(String name) {
/* 2053 */       return LegionConfig.SELF_INTRO_PATTERN.matcher(name).matches();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isValidNickname(String name) {
/* 2065 */       return LegionConfig.NICKNAME_PATTERN.matcher(name).matches();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isValidAnnouncement(String name) {
/* 2077 */       return LegionConfig.ANNOUNCEMENT_PATTERN.matcher(name).matches();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SingletonHolder
/*      */   {
/* 2084 */     protected static final LegionService instance = new LegionService();
/*      */   }
/*      */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\LegionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
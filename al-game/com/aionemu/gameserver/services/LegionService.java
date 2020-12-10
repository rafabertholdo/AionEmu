package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.LegionConfig;
import com.aionemu.gameserver.dao.LegionDAO;
import com.aionemu.gameserver.dao.LegionMemberDAO;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.model.legion.LegionEmblem;
import com.aionemu.gameserver.model.legion.LegionHistory;
import com.aionemu.gameserver.model.legion.LegionHistoryType;
import com.aionemu.gameserver.model.legion.LegionMember;
import com.aionemu.gameserver.model.legion.LegionMemberEx;
import com.aionemu.gameserver.model.legion.LegionRank;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_ADD_MEMBER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_EDIT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_LEAVE_MEMBER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_MEMBERLIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_TABS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_EMBLEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_MEMBER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_NICKNAME;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_SELF_INTRO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_TITLE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_WAREHOUSE_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.container.LegionContainer;
import com.aionemu.gameserver.world.container.LegionMemberContainer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class LegionService {
  private static final Logger log = Logger.getLogger(LegionService.class);

  private final LegionContainer allCachedLegions = new LegionContainer();
  private final LegionMemberContainer allCachedLegionMembers = new LegionMemberContainer();

  private World world;

  private static final int MAX_LEGION_LEVEL = 5;

  private static final int INVITE = 1;

  private static final int KICK = 2;

  private static final int WAREHOUSE = 3;

  private static final int ANNOUNCEMENT = 4;

  private static final int ARTIFACT = 5;

  private static final int GATEGUARDIAN = 6;

  private HashMap<Integer, Integer> legionRanking;

  private LegionRestrictions legionRestrictions = new LegionRestrictions();

  public static final LegionService getInstance() {
    return SingletonHolder.instance;
  }

  public LegionService() {
    this.world = World.getInstance();
  }

  public boolean isValidName(String name) {
    return LegionConfig.LEGION_NAME_PATTERN.matcher(name).matches();
  }

  private void storeLegion(Legion legion, boolean newLegion) {
    if (newLegion) {

      addCachedLegion(legion);
      ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).saveNewLegion(legion);
    } else {

      ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).storeLegion(legion);
      ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).storeLegionEmblem(legion.getLegionId(),
          legion.getLegionEmblem());
    }
  }

  private void storeLegion(Legion legion) {
    storeLegion(legion, false);
  }

  private void storeLegionMember(LegionMember legionMember, boolean newMember) {
    if (newMember) {

      addCachedLegionMember(legionMember);
      ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class)).saveNewLegionMember(legionMember);
    } else {

      ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class)).storeLegionMember(legionMember.getObjectId(),
          legionMember);
    }
  }

  private void storeLegionMember(LegionMember legionMember) {
    storeLegionMember(legionMember, false);
  }

  private void storeLegionMemberExInCache(Player player) {
    if (this.allCachedLegionMembers.containsEx(player.getObjectId())) {

      LegionMemberEx legionMemberEx = this.allCachedLegionMembers.getMemberEx(player.getObjectId());
      legionMemberEx.setNickname(player.getLegionMember().getNickname());
      legionMemberEx.setSelfIntro(player.getLegionMember().getSelfIntro());
      legionMemberEx.setPlayerClass(player.getPlayerClass());
      legionMemberEx.setExp(player.getCommonData().getExp());
      legionMemberEx.setLastOnline(player.getCommonData().getLastOnline());
      legionMemberEx.setWorldId(player.getPosition().getMapId());
      legionMemberEx.setOnline(false);
    } else {

      LegionMemberEx legionMemberEx = new LegionMemberEx(player, player.getLegionMember(), false);
      addCachedLegionMemberEx(legionMemberEx);
    }
  }

  private Legion getCachedLegion(int legionId) {
    return this.allCachedLegions.get(legionId);
  }

  private Legion getCachedLegion(String legionName) {
    return this.allCachedLegions.get(legionName);
  }

  public Collection<Legion> getCachedLegions() {
    return this.allCachedLegions.getLegions();
  }

  private void addCachedLegion(Legion legion) {
    this.allCachedLegions.add(legion);
  }

  private void addCachedLegionMember(LegionMember legionMember) {
    this.allCachedLegionMembers.addMember(legionMember);
  }

  private void addCachedLegionMemberEx(LegionMemberEx legionMemberEx) {
    this.allCachedLegionMembers.addMemberEx(legionMemberEx);
  }

  private void deleteLegionFromDB(Legion legion) {
    this.allCachedLegions.remove(legion);
    ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).deleteLegion(legion.getLegionId());
  }

  private void deleteLegionMemberFromDB(LegionMemberEx legionMember) {
    this.allCachedLegionMembers.remove(legionMember);
    ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class)).deleteLegionMember(legionMember.getObjectId());
    Legion legion = legionMember.getLegion();
    legion.deleteLegionMember(legionMember.getObjectId());
    addHistory(legion, legionMember.getName(), LegionHistoryType.KICK);
  }

  public Legion getLegion(String legionName) {
    if (this.allCachedLegions.contains(legionName)) {

      Legion legion1 = getCachedLegion(legionName);
      return legion1;
    }

    Legion legion = ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadLegion(legionName);

    loadLegionInfo(legion);

    addCachedLegion(legion);

    return legion;
  }

  public Legion getLegion(int legionId) {
    if (this.allCachedLegions.contains(legionId)) {

      Legion legion1 = getCachedLegion(legionId);
      return legion1;
    }

    Legion legion = ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadLegion(legionId);

    loadLegionInfo(legion);

    addCachedLegion(legion);

    return legion;
  }

  private void loadLegionInfo(Legion legion) {
    if (legion == null) {
      return;
    }

    legion.setLegionMembers(
        ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class)).loadLegionMembers(legion.getLegionId()));

    legion.setAnnouncementList(
        ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadAnnouncementList(legion.getLegionId()));

    legion.setLegionEmblem(((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadLegionEmblem(legion.getLegionId()));

    legion.setLegionWarehouse(((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadLegionStorage(legion));

    if (this.legionRanking == null) {

      int DELAY_LEGIONRANKING = LegionConfig.LEGION_RANKING_PERIODICUPDATE * 1000;
      ThreadPoolManager.getInstance().scheduleAtFixedRate(new LegionRankingUpdateTask(), DELAY_LEGIONRANKING,
          DELAY_LEGIONRANKING);

      setLegionRanking(((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadLegionRanking());
    }

    if (this.legionRanking.containsKey(Integer.valueOf(legion.getLegionId()))) {
      legion.setLegionRank(((Integer) this.legionRanking.get(Integer.valueOf(legion.getLegionId()))).intValue());
    }

    ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadLegionHistory(legion);
  }

  public LegionMember getLegionMember(int playerObjId) {
    LegionMember legionMember = null;
    if (this.allCachedLegionMembers.contains(playerObjId)) {
      legionMember = this.allCachedLegionMembers.getMember(playerObjId);
    } else {

      legionMember = ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class)).loadLegionMember(playerObjId);
      if (legionMember != null) {
        addCachedLegionMember(legionMember);
      }
    }
    if (legionMember != null && checkDisband(legionMember.getLegion())) {
      return null;
    }
    return legionMember;
  }

  private boolean checkDisband(Legion legion) {
    if (legion.isDisbanding()) {
      if (System.currentTimeMillis() / 1000L > legion.getDisbandTime()) {

        disbandLegion(legion);
        return true;
      }
    }
    return false;
  }

  public void disbandLegion(Legion legion) {
    for (Integer memberObjId : legion.getLegionMembers()) {
      this.allCachedLegionMembers.remove(getLegionMemberEx(memberObjId.intValue()));
    }
    updateAfterDisbandLegion(legion);
    deleteLegionFromDB(legion);
  }

  private LegionMemberEx getLegionMemberEx(int playerObjId) {
    if (this.allCachedLegionMembers.containsEx(playerObjId)) {
      return this.allCachedLegionMembers.getMemberEx(playerObjId);
    }

    LegionMemberEx legionMember = ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class))
        .loadLegionMemberEx(playerObjId);

    addCachedLegionMemberEx(legionMember);
    return legionMember;
  }

  private LegionMemberEx getLegionMemberEx(String playerName) {
    if (this.allCachedLegionMembers.containsEx(playerName)) {
      return this.allCachedLegionMembers.getMemberEx(playerName);
    }

    LegionMemberEx legionMember = ((LegionMemberDAO) DAOManager.getDAO(LegionMemberDAO.class))
        .loadLegionMemberEx(playerName);
    addCachedLegionMemberEx(legionMember);
    return legionMember;
  }

  public void requestDisbandLegion(Creature npc, Player activePlayer) {
    final Legion legion = activePlayer.getLegion();
    if (this.legionRestrictions.canDisbandLegion(activePlayer, legion)) {

      RequestResponseHandler disbandResponseHandler = new RequestResponseHandler(npc) {
        public void acceptRequest(Creature requester, Player responder) {
          int unixTime = (int) (System.currentTimeMillis() / 1000L + LegionConfig.LEGION_DISBAND_TIME);
          legion.setDisbandTime(unixTime);
          LegionService.this.updateMembersOfDisbandLegion(legion, unixTime);
        }

        public void denyRequest(Creature requester, Player responder) {
        }
      };
      boolean disbandResult = activePlayer.getResponseRequester().putRequest(80008, disbandResponseHandler);

      if (disbandResult) {
        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_QUESTION_WINDOW(80008, 0, new Object[0]));
      }
    }
  }

  public void createLegion(Player activePlayer, String legionName) {
    if (this.legionRestrictions.canCreateLegion(activePlayer, legionName)) {

      Legion legion = new Legion(IDFactory.getInstance().nextId(), legionName);
      legion.addLegionMember(activePlayer.getObjectId());
      ItemService.decreaseKinah(activePlayer, LegionConfig.LEGION_CREATE_REQUIRED_KINAH);

      storeLegion(legion, true);
      addLegionMember(legion, activePlayer, LegionRank.BRIGADE_GENERAL);

      addHistory(legion, "", LegionHistoryType.CREATE);
      addHistory(legion, activePlayer.getName(), LegionHistoryType.JOIN);

      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CREATED(legion.getLegionName()));
    }
  }

  private void invitePlayerToLegion(final Player activePlayer, final Player targetPlayer) {
    if (this.legionRestrictions.canInvitePlayer(activePlayer, targetPlayer)) {

      final Legion legion = activePlayer.getLegion();

      RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) activePlayer) {

        public void acceptRequest(Creature requester, Player responder) {
          if (!targetPlayer.getCommonData().isOnline()) {

            PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_INCORRECT_TARGET());
          } else {

            int playerObjId = targetPlayer.getObjectId();
            if (legion.addLegionMember(playerObjId)) {

              LegionService.this.addLegionMember(legion, targetPlayer);

              PacketSendUtility.sendPacket(activePlayer,
                  (AionServerPacket) SM_SYSTEM_MESSAGE.NEW_MEMBER_JOINED(targetPlayer.getName()));

              LegionService.this.displayLegionMessage(targetPlayer, legion.getCurrentAnnouncement());

              LegionService.this.addHistory(legion, targetPlayer.getName(), LegionHistoryType.JOIN);
            } else {

              PacketSendUtility.sendPacket(activePlayer,
                  (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CAN_NOT_ADD_MEMBER_ANY_MORE());

              targetPlayer.resetLegionMember();
            }
          }
        }

        public void denyRequest(Creature requester, Player responder) {
          PacketSendUtility.sendPacket(activePlayer,
              (AionServerPacket) SM_SYSTEM_MESSAGE.REJECTED_INVITE_REQUEST(targetPlayer.getName()));
        }
      };

      boolean requested = targetPlayer.getResponseRequester().putRequest(80001, responseHandler);

      if (!requested) {

        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_TARGET_BUSY());
      } else {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.SEND_INVITE_REQUEST(targetPlayer.getName()));

        PacketSendUtility.sendPacket(targetPlayer, (AionServerPacket) new SM_QUESTION_WINDOW(80001, 0,
            new Object[] { legion.getLegionName(), legion.getLegionLevel() + "", activePlayer.getName() }));
      }
    }
  }

  private void displayLegionMessage(Player targetPlayer, Map.Entry<Timestamp, String> currentAnnouncement) {
    if (currentAnnouncement != null) {
      PacketSendUtility.sendPacket(targetPlayer,
          (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_DISPLAY_ANNOUNCEMENT(currentAnnouncement.getValue(),
              (int) (((Timestamp) currentAnnouncement.getKey()).getTime() / 1000L), 2));
    }
  }

  private void appointBrigadeGeneral(final Player activePlayer, final Player targetPlayer) {
    if (this.legionRestrictions.canAppointBrigadeGeneral(activePlayer, targetPlayer)) {

      final Legion legion = activePlayer.getLegion();
      RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) activePlayer) {

        public void acceptRequest(Creature requester, Player responder) {
          if (!targetPlayer.getCommonData().isOnline()) {

            PacketSendUtility.sendPacket(activePlayer,
                (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_NO_SUCH_USER());

          } else {

            LegionMember legionMember = targetPlayer.getLegionMember();
            if (legionMember.getRank().getRankId() > LegionRank.BRIGADE_GENERAL.getRankId()) {

              activePlayer.getLegionMember().setRank(LegionRank.CENTURION);
              PacketSendUtility.broadcastPacketToLegion(legion,
                  (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(activePlayer, 0, ""));

              legionMember.setRank(LegionRank.BRIGADE_GENERAL);
              PacketSendUtility.broadcastPacketToLegion(legion,
                  (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(targetPlayer, 1300273, targetPlayer.getName()));

              LegionService.this.addHistory(legion, targetPlayer.getName(), LegionHistoryType.APPOINTED);
            }
          }
        }

        public void denyRequest(Creature requester, Player responder) {
          PacketSendUtility.sendPacket(activePlayer,
              (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_HE_DECLINE_YOUR_OFFER(targetPlayer.getName()));
        }
      };

      boolean requested = targetPlayer.getResponseRequester().putRequest(80011, responseHandler);

      if (!requested) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_SENT_CANT_OFFER_WHEN_HE_IS_QUESTION_ASKED());

      } else {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_SENT_OFFER_MSG_TO_HIM(targetPlayer.getName()));

        PacketSendUtility.sendPacket(targetPlayer,
            (AionServerPacket) new SM_QUESTION_WINDOW(80011, activePlayer.getObjectId(), new Object[0]));
      }
    }
  }

  private void appointRank(Player activePlayer, Player targetPlayer, int rank) {
    if (this.legionRestrictions.canAppointRank(activePlayer, targetPlayer)) {
      int msgId;
      Legion legion = activePlayer.getLegion();

      LegionMember legionMember = targetPlayer.getLegionMember();
      if (rank == LegionRank.CENTURION.getRankId() && legionMember.getRank() == LegionRank.LEGIONARY) {

        legionMember.setRank(LegionRank.CENTURION);
        msgId = 1300267;

      } else {

        legionMember.setRank(LegionRank.LEGIONARY);
        msgId = 1300268;
      }

      PacketSendUtility.broadcastPacketToLegion(legion,
          (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(targetPlayer, msgId, targetPlayer.getName()));
    }
  }

  private void changeSelfIntro(Player activePlayer, String newSelfIntro) {
    if (this.legionRestrictions.canChangeSelfIntro(activePlayer, newSelfIntro)) {

      LegionMember legionMember = activePlayer.getLegionMember();
      legionMember.setSelfIntro(newSelfIntro);
      PacketSendUtility.broadcastPacketToLegion(legionMember.getLegion(),
          (AionServerPacket) new SM_LEGION_UPDATE_SELF_INTRO(activePlayer.getObjectId(), newSelfIntro));

      PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_WRITE_INTRO_DONE());
    }
  }

  public void changePermissions(Legion legion, int lP2, int cP1, int cP2) {
    if (legion.setLegionPermissions(lP2, cP1, cP2)) {
      PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(2, legion));
    }
  }

  private void requestChangeLevel(Player activePlayer, long kinahAmount) {
    if (this.legionRestrictions.canChangeLevel(activePlayer, kinahAmount)) {

      Legion legion = activePlayer.getLegion();
      ItemService.decreaseKinah(activePlayer, legion.getKinahPrice());
      changeLevel(legion, legion.getLegionLevel() + 1, false);
      addHistory(legion, legion.getLegionLevel() + "", LegionHistoryType.LEVEL_UP);
    }
  }

  public void changeLevel(Legion legion, int newLevel, boolean save) {
    legion.setLegionLevel(newLevel);
    PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(0, legion));
    PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_LEVEL_UP(newLevel));
    if (save) {
      storeLegion(legion);
    }
  }

  private void changeNickname(Player activePlayer, Player targetPlayer, String newNickname) {
    Legion legion = activePlayer.getLegion();
    if (this.legionRestrictions.canChangeNickname(legion, targetPlayer.getObjectId(), newNickname)) {

      LegionMember targetLegionMember = targetPlayer.getLegionMember();
      targetLegionMember.setNickname(newNickname);
      PacketSendUtility.broadcastPacketToLegion(legion,
          (AionServerPacket) new SM_LEGION_UPDATE_NICKNAME(targetPlayer.getObjectId(), newNickname));
    }
  }

  private void updateAfterDisbandLegion(Legion legion) {
    for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {

      PacketSendUtility.broadcastPacket(onlineLegionMember,
          (AionServerPacket) new SM_LEGION_UPDATE_TITLE(onlineLegionMember.getObjectId(), 0, "", 0), true);

      PacketSendUtility.sendPacket(onlineLegionMember,
          (AionServerPacket) new SM_LEGION_LEAVE_MEMBER(1300302, 0, legion.getLegionName()));

      onlineLegionMember.resetLegionMember();
    }
  }

  private void updateMembersEmblem(Legion legion) {
    LegionEmblem legionEmblem = legion.getLegionEmblem();
    for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {
      PacketSendUtility.broadcastPacket(onlineLegionMember,
          (AionServerPacket) new SM_LEGION_UPDATE_EMBLEM(legion.getLegionId(), legionEmblem.getEmblemId(),
              legionEmblem.getColor_r(), legionEmblem.getColor_g(), legionEmblem.getColor_b()),
          true);
    }
  }

  private void updateMembersOfDisbandLegion(Legion legion, int unixTime) {
    for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {

      PacketSendUtility.sendPacket(onlineLegionMember,
          (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(onlineLegionMember, 1300303, unixTime + ""));

      PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(6, unixTime));
    }
  }

  private void updateMembersOfRecreateLegion(Legion legion) {
    for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {

      PacketSendUtility.sendPacket(onlineLegionMember,
          (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(onlineLegionMember, 1300307, ""));

      PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(7));
    }
  }

  public void storeLegionEmblem(Player activePlayer, int legionId, int emblemId, int color_r, int color_g,
      int color_b) {
    if (this.legionRestrictions.canStoreLegionEmblem(activePlayer, legionId, emblemId)) {

      Legion legion = activePlayer.getLegion();
      if (legion.getLegionEmblem().isDefaultEmblem()) {
        addHistory(legion, "", LegionHistoryType.EMBLEM_REGISTER);
      } else {
        addHistory(legion, "", LegionHistoryType.EMBLEM_MODIFIED);
      }
      ItemService.decreaseKinah(activePlayer, LegionConfig.LEGION_EMBLEM_REQUIRED_KINAH);
      legion.getLegionEmblem().setEmblem(emblemId, color_r, color_g, color_b);
      updateMembersEmblem(legion);
      PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGED_EMBLEM());
    }
  }

  public ArrayList<LegionMemberEx> loadLegionMemberExList(Legion legion) {
    ArrayList<LegionMemberEx> legionMembers = new ArrayList<LegionMemberEx>();
    for (Integer memberObjId : legion.getLegionMembers()) {
      LegionMemberEx legionMemberEx;

      Player memberPlayer = this.world.findPlayer(memberObjId.intValue());
      if (memberPlayer != null) {

        legionMemberEx = new LegionMemberEx(memberPlayer, memberPlayer.getLegionMember(), true);
      } else {

        legionMemberEx = getLegionMemberEx(memberObjId.intValue());
      }
      legionMembers.add(legionMemberEx);
    }
    return legionMembers;
  }

  public void openLegionWarehouse(Player activePlayer) {
    if (this.legionRestrictions.canOpenWarehouse(activePlayer)) {

      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) new SM_DIALOG_WINDOW(activePlayer.getObjectId(), 25));
      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) new SM_WAREHOUSE_INFO(activePlayer.getLegion().getLegionWarehouse().getStorageItems(),
              StorageType.LEGION_WAREHOUSE.getId(), 0, true));

      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) new SM_WAREHOUSE_INFO(null, StorageType.LEGION_WAREHOUSE.getId(), 0, false));
    }
  }

  public void recreateLegion(Npc npc, Player activePlayer) {
    final Legion legion = activePlayer.getLegion();
    if (this.legionRestrictions.canRecreateLegion(activePlayer, legion)) {

      RequestResponseHandler disbandResponseHandler = new RequestResponseHandler((Creature) npc) {

        public void acceptRequest(Creature requester, Player responder) {
          legion.setDisbandTime(0);
          PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(7));
          LegionService.this.updateMembersOfRecreateLegion(legion);
        }

        public void denyRequest(Creature requester, Player responder) {
        }
      };
      boolean disbandResult = activePlayer.getResponseRequester().putRequest(80009, disbandResponseHandler);

      if (disbandResult) {
        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_QUESTION_WINDOW(80009, 0, new Object[0]));
      }
    }
  }

  private void setLegionRanking(HashMap<Integer, Integer> legionRanking) {
    this.legionRanking = legionRanking;
  }

  private class LegionRankingUpdateTask implements Runnable {
    private LegionRankingUpdateTask() {
    }

    public void run() {
      LegionService.log.info("Legion ranking update task started");
      long startTime = System.currentTimeMillis();
      Collection<Legion> legions = LegionService.this.allCachedLegions.getLegions();
      int legionsUpdated = 0;

      LegionService.this.setLegionRanking(((LegionDAO) DAOManager.getDAO(LegionDAO.class)).loadLegionRanking());

      for (Legion legion : legions) {

        try {
          if (LegionService.this.legionRanking.containsKey(Integer.valueOf(legion.getLegionId()))) {
            legion.setLegionRank(
                ((Integer) LegionService.this.legionRanking.get(Integer.valueOf(legion.getLegionId()))).intValue());
            PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(1, legion));
          }

        } catch (Exception ex) {

          LegionService.log.error("Exception during periodic update of legion ranking " + ex.getMessage());
        }

        legionsUpdated++;
      }
      long workTime = System.currentTimeMillis() - startTime;
      LegionService.log.info("Legion ranking update: " + workTime + " ms, legions: " + legionsUpdated);
    }
  }

  public void updateMemberInfo(Player player) {
    PacketSendUtility.broadcastPacketToLegion(player.getLegion(),
        (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(player, 0, ""));
  }

  public void addContributionPoints(Legion legion, int pointsGained) {
    legion.addContributionPoints(pointsGained);
    PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(3, legion));
  }

  public void setContributionPoints(Legion legion, int newPoints, boolean save) {
    legion.setContributionPoints(newPoints);
    PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_EDIT(3, legion));
    if (save) {
      storeLegion(legion);
    }
  }

  public void uploadEmblemInfo(Player activePlayer, int totalSize) {
    if (this.legionRestrictions.canUploadEmblemInfo(activePlayer)) {

      LegionEmblem legionEmblem = activePlayer.getLegion().getLegionEmblem();
      legionEmblem.setUploadSize(totalSize);
      legionEmblem.setUploading(true);
    }
  }

  public void uploadEmblemData(Player activePlayer, int size, byte[] data) {
    if (this.legionRestrictions.canUploadEmblem(activePlayer))
      ;
  }

  public void setLegionName(Legion legion, String newLegionName, boolean save) {
    legion.setLegionName(newLegionName);
    PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_INFO(legion));

    for (Player legionMember : legion.getOnlineLegionMembers()) {
      PacketSendUtility
          .broadcastPacket(
              legionMember, (AionServerPacket) new SM_LEGION_UPDATE_TITLE(legionMember.getObjectId(),
                  legion.getLegionId(), legion.getLegionName(), legionMember.getLegionMember().getRank().getRankId()),
              true);
    }

    if (save) {
      storeLegion(legion);
    }
  }

  private void changeAnnouncement(Player activePlayer, String announcement) {
    if (this.legionRestrictions.canChangeAnnouncement(activePlayer.getLegionMember(), announcement)) {

      Legion legion = activePlayer.getLegion();

      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
      storeNewAnnouncement(legion.getLegionId(), currentTime, announcement);
      legion.addAnnouncementToList(currentTime, announcement);
      PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_WRITE_NOTICE_DONE());
      PacketSendUtility.broadcastPacketToLegion(legion,
          (AionServerPacket) new SM_LEGION_EDIT(5, (int) (System.currentTimeMillis() / 1000L), announcement));
    }
  }

  private void storeLegionAnnouncements(Legion legion) {
    for (int i = 0; i < legion.getAnnouncementList().size() - 7; i++) {

      removeAnnouncement(legion.getLegionId(), (Timestamp) legion.getAnnouncementList().firstEntry().getKey());
      legion.removeFirstEntry();
    }
  }

  private boolean storeNewAnnouncement(int legionId, Timestamp currentTime, String message) {
    return ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).saveNewAnnouncement(legionId, currentTime, message);
  }

  private void removeAnnouncement(int legionId, Timestamp key) {
    ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).removeAnnouncement(legionId, key);
  }

  private void addHistory(Legion legion, String text, LegionHistoryType legionHistoryType) {
    LegionHistory legionHistory = new LegionHistory(legionHistoryType, text, new Timestamp(System.currentTimeMillis()));

    legion.addHistory(legionHistory);
    ((LegionDAO) DAOManager.getDAO(LegionDAO.class)).saveNewLegionHistory(legion.getLegionId(), legionHistory);

    PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_TABS(legion.getLegionHistory()));
  }

  private void addLegionMember(Legion legion, Player player) {
    addLegionMember(legion, player, LegionRank.LEGIONARY);
  }

  private void addLegionMember(Legion legion, Player player, LegionRank rank) {
    player.setLegionMember(new LegionMember(player.getObjectId(), legion, rank));
    storeLegionMember(player.getLegionMember(), true);

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_LEGION_INFO(legion));

    PacketSendUtility.broadcastPacketToLegion(legion,
        (AionServerPacket) new SM_LEGION_ADD_MEMBER(player, false, 0, ""));

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_LEGION_MEMBERLIST(loadLegionMemberExList(legion)));

    PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_LEGION_UPDATE_TITLE(player.getObjectId(),
        legion.getLegionId(), legion.getLegionName(), player.getLegionMember().getRank().getRankId()), true);

    LegionEmblem legionEmblem = legion.getLegionEmblem();
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_LEGION_UPDATE_EMBLEM(legion.getLegionId(),
        legionEmblem.getEmblemId(), legionEmblem.getColor_r(), legionEmblem.getColor_g(), legionEmblem.getColor_b()));

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_LEGION_EDIT(8));
  }

  private boolean removeLegionMember(String charName, boolean kick, String playerName) {
    LegionMemberEx legionMember = getLegionMemberEx(charName);
    if (legionMember == null) {

      log.error("Char name does not exist in legion member table: " + charName);
      return false;
    }

    deleteLegionMemberFromDB(legionMember);

    Player player = this.world.findPlayer(charName);
    if (player != null) {
      PacketSendUtility.broadcastPacket(player,
          (AionServerPacket) new SM_LEGION_UPDATE_TITLE(player.getObjectId(), 0, "", 2), true);
    }

    if (kick) {

      PacketSendUtility.broadcastPacketToLegion(legionMember.getLegion(),
          (AionServerPacket) new SM_LEGION_LEAVE_MEMBER(1300247, legionMember.getObjectId(), playerName,
              legionMember.getName()));

    } else {

      PacketSendUtility.broadcastPacketToLegion(legionMember.getLegion(),
          (AionServerPacket) new SM_LEGION_LEAVE_MEMBER(900699, legionMember.getObjectId(), charName));
    }

    return true;
  }

  public void handleCharNameRequest(int exOpcode, Player activePlayer, String charName, String newNickname, int rank) {
    Legion legion = activePlayer.getLegion();

    charName = Util.convertName(charName);
    Player targetPlayer = this.world.findPlayer(charName);

    switch (exOpcode) {

      case 1:
        if (targetPlayer != null) {

          if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.GUILD)) {

            PacketSendUtility.sendPacket(activePlayer,
                (AionServerPacket) SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_INVITE_GUILD(charName));

            return;
          }
          invitePlayerToLegion(activePlayer, targetPlayer);

          break;
        }
        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_NO_USER_TO_INVITE());
        break;

      case 4:
        if (this.legionRestrictions.canKickPlayer(activePlayer, charName)) {
          if (removeLegionMember(charName, true, activePlayer.getName())) {

            if (targetPlayer != null) {

              PacketSendUtility.sendPacket(targetPlayer,
                  (AionServerPacket) new SM_LEGION_LEAVE_MEMBER(1300246, 0, legion.getLegionName()));

              targetPlayer.resetLegionMember();
            }
          }
        }
        break;

      case 5:
        if (targetPlayer != null) {

          appointBrigadeGeneral(activePlayer, targetPlayer);

          break;
        }
        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_NO_SUCH_USER());
        break;

      case 6:
        if (targetPlayer != null) {

          appointRank(activePlayer, targetPlayer, rank);

          break;
        }
        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_NO_USER());
        break;

      case 15:
        if (targetPlayer == null || targetPlayer.getLegion() != legion) {
          return;
        }
        changeNickname(activePlayer, targetPlayer, newNickname);
        break;
    }
  }

  public void handleLegionRequest(int exOpcode, Player activePlayer, String text) {
    switch (exOpcode) {

      case 9:
        changeAnnouncement(activePlayer, text);
        break;

      case 10:
        changeSelfIntro(activePlayer, text);
        break;
    }
  }

  public void handleLegionRequest(int exOpcode, Player activePlayer) {
    switch (exOpcode) {

      case 2:
        if (this.legionRestrictions.canLeave(activePlayer)) {
          if (removeLegionMember(activePlayer.getName(), false, "")) {

            Legion legion = activePlayer.getLegion();
            PacketSendUtility.sendPacket(activePlayer,
                (AionServerPacket) new SM_LEGION_LEAVE_MEMBER(1300241, 0, legion.getLegionName()));

            activePlayer.resetLegionMember();
          }
        }
        break;

      case 14:
        requestChangeLevel(activePlayer, activePlayer.getInventory().getKinahItem().getItemCount());
        break;
    }
  }

  public void onLogin(Player activePlayer) {
    Legion legion = activePlayer.getLegion();

    PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_LEGION_ADD_MEMBER(activePlayer, false, 0, ""));

    PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_LEGION_INFO(legion));

    PacketSendUtility.broadcastPacketToLegion(legion,
        (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(activePlayer, 0, ""));

    PacketSendUtility.broadcastPacketToLegion(legion,
        (AionServerPacket) SM_SYSTEM_MESSAGE.STR_MSG_NOTIFY_LOGIN_GUILD(activePlayer.getName()),
        activePlayer.getObjectId());

    PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_LEGION_ADD_MEMBER(activePlayer, true, 0, ""));

    PacketSendUtility.sendPacket(activePlayer,
        (AionServerPacket) new SM_LEGION_MEMBERLIST(loadLegionMemberExList(legion)));

    displayLegionMessage(activePlayer, legion.getCurrentAnnouncement());

    if (legion.isDisbanding()) {
      PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_LEGION_EDIT(6, legion.getDisbandTime()));
    }
  }

  public void onLogout(Player player) {
    Legion legion = player.getLegion();
    PacketSendUtility.broadcastPacketToLegion(legion, (AionServerPacket) new SM_LEGION_UPDATE_MEMBER(player, 0, ""));
    storeLegion(legion);
    storeLegionMember(player.getLegionMember());
    storeLegionMemberExInCache(player);
    storeLegionAnnouncements(legion);
  }

  private class LegionRestrictions {
    private static final int MIN_EMBLEM_ID = 0;

    private static final int MAX_EMBLEM_ID = 40;

    private LegionRestrictions() {
    }

    private boolean canCreateLegion(Player activePlayer, String legionName) {
      if (!LegionService.this.isValidName(legionName)) {

        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CREATE_INVALID_NAME());
        return false;
      }
      if (!isFreeName(legionName)) {

        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CREATE_NAME_EXISTS());
        return false;
      }
      if (activePlayer.isLegionMember()) {

        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CREATE_ALREADY_MEMBER());
        return false;
      }
      if (activePlayer.getInventory().getKinahItem().getItemCount() < LegionConfig.LEGION_CREATE_REQUIRED_KINAH) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CREATE_NOT_ENOUGH_KINAH());
        return false;
      }
      return true;
    }

    private boolean canInvitePlayer(Player activePlayer, Player targetPlayer) {
      Legion legion = activePlayer.getLegion();
      if (activePlayer.getLifeStats().isAlreadyDead()) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CANT_INVITE_WHILE_DEAD());
        return false;
      }
      if (isSelf(activePlayer, targetPlayer.getObjectId())) {

        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CAN_NOT_INVITE_SELF());
        return false;
      }
      if (targetPlayer.isLegionMember()) {

        if (legion.isMember(targetPlayer.getObjectId())) {

          PacketSendUtility.sendPacket(activePlayer,
              (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_HE_IS_MY_GUILD_MEMBER(targetPlayer.getName()));

        } else {

          PacketSendUtility.sendPacket(activePlayer,
              (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_HE_IS_OTHER_GUILD_MEMBER(targetPlayer.getName()));
        }

        return false;
      }
      if (!activePlayer.getLegionMember().hasRights(1)) {

        return false;
      }
      if (activePlayer.getCommonData().getRace() != targetPlayer.getCommonData().getRace()
          && !LegionConfig.LEGION_INVITEOTHERFACTION) {

        return false;
      }
      return true;
    }

    private boolean canKickPlayer(Player activePlayer, String charName) {
      LegionMemberEx legionMember = LegionService.this.getLegionMemberEx(charName);
      if (legionMember == null) {

        LegionService.log.error("Char name does not exist in legion member table: " + charName);
        return false;
      }

      Legion legion = activePlayer.getLegion();

      if (isSelf(activePlayer, legionMember.getObjectId())) {

        PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CANT_KICK_YOURSELF());
        return false;
      }
      if (legionMember.isBrigadeGeneral()) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CANT_KICK_BRIGADE_GENERAL());
        return false;
      }
      if (legionMember.getRank() == activePlayer.getLegionMember().getRank()) {

        return false;
      }
      if (!legion.isMember(legionMember.getObjectId())) {

        return false;
      }
      if (!activePlayer.getLegionMember().hasRights(2)) {

        return false;
      }
      return true;
    }

    private boolean canAppointBrigadeGeneral(Player activePlayer, Player targetPlayer) {
      Legion legion = activePlayer.getLegion();
      if (!isBrigadeGeneral(activePlayer)) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_DONT_HAVE_RIGHT());

        return false;
      }
      if (isSelf(activePlayer, targetPlayer.getObjectId())) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MASTER_ERROR_SELF());
        return false;
      }
      if (!legion.isMember(targetPlayer.getObjectId())) {
        return false;
      }
      return true;
    }

    private boolean canAppointRank(Player activePlayer, Player targetPlayer) {
      Legion legion = activePlayer.getLegion();
      if (!isBrigadeGeneral(activePlayer)) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_DONT_HAVE_RIGHT());

        return false;
      }
      if (isSelf(activePlayer, targetPlayer.getObjectId())) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_MEMBER_RANK_ERROR_SELF());
        return false;
      }
      if (!legion.isMember(targetPlayer.getObjectId())) {

        return false;
      }
      return true;
    }

    private boolean canChangeSelfIntro(Player activePlayer, String newSelfIntro) {
      if (!isValidSelfIntro(newSelfIntro))
        return false;
      return true;
    }

    private boolean canChangeLevel(Player activePlayer, long kinahAmount) {
      Legion legion = activePlayer.getLegion();
      int levelContributionPrice = legion.getContributionPrice();

      if (legion.getLegionLevel() == 5) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_CANT_LEVEL_UP());
        return false;
      }
      if (activePlayer.getInventory().getKinahItem().getItemCount() < legion.getKinahPrice()) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_NOT_ENOUGH_KINAH());
        return false;
      }
      if (!legion.hasRequiredMembers()) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_NOT_ENOUGH_MEMBER());
        return false;
      }
      if (legion.getContributionPoints() < levelContributionPrice) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CHANGE_LEVEL_NOT_ENOUGH_POINT());
        return false;
      }
      return true;
    }

    private boolean canChangeNickname(Legion legion, int targetObjectId, String newNickname) {
      if (!isValidNickname(newNickname)) {

        return false;
      }
      if (!legion.isMember(targetObjectId)) {

        return false;
      }
      return true;
    }

    private boolean canChangeAnnouncement(LegionMember legionMember, String announcement) {
      if (!isValidAnnouncement(announcement) && legionMember.hasRights(4))
        return false;
      return true;
    }

    private boolean canDisbandLegion(Player activePlayer, Legion legion) {
      if (!isBrigadeGeneral(activePlayer)) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_DISPERSE_ONLY_MASTER_CAN_DISPERSE());

        return false;
      }
      if (legion.getLegionWarehouse().size() > 0) {

        return false;
      }
      if (legion.isDisbanding()) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_DISPERSE_ALREADY_REQUESTED());
        return false;
      }
      if (legion.getLegionWarehouse().getStorageItems().size() > 0) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_DISPERSE_CANT_DISPERSE_GUILD_STORE_ITEM_IN_WAREHOUSE());

        return false;
      }
      return true;
    }

    private boolean canLeave(Player activePlayer) {
      if (isBrigadeGeneral(activePlayer)) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_CANT_LEAVE_BEFORE_CHANGE_MASTER());
        return false;
      }
      return true;
    }

    private boolean canRecreateLegion(Player activePlayer, Legion legion) {
      if (!isBrigadeGeneral(activePlayer)) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_DISPERSE_ONLY_MASTER_CAN_DISPERSE());

        return false;
      }
      if (!legion.isDisbanding()) {

        return false;
      }
      return true;
    }

    private boolean canUploadEmblemInfo(Player activePlayer) {
      if (!isBrigadeGeneral(activePlayer)) {
        return false;
      }
      if (activePlayer.getLegion().getLegionLevel() < 3) {

        return false;
      }
      if (activePlayer.getLegion().getLegionEmblem().isUploading()) {

        activePlayer.getLegion().getLegionEmblem().setUploading(false);
        return false;
      }
      return true;
    }

    private boolean canUploadEmblem(Player activePlayer) {
      if (!isBrigadeGeneral(activePlayer)) {

        return false;
      }
      if (activePlayer.getLegion().getLegionLevel() < 3) {

        return false;
      }
      if (!activePlayer.getLegion().getLegionEmblem().isUploading()) {

        return false;
      }
      return true;
    }

    public boolean canOpenWarehouse(Player activePlayer) {
      if (!activePlayer.isLegionMember()) {

        return false;
      }

      Legion legion = activePlayer.getLegion();
      if (!activePlayer.getLegionMember().hasRights(3)) {

        return false;
      }
      if (legion.isDisbanding()) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.LEGION_WAREHOUSE_CANT_USE_WHILE_DISPERSE());

        return false;
      }
      if (!LegionConfig.LEGION_WAREHOUSE) {

        return false;
      }

      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) new SM_DIALOG_WINDOW(activePlayer.getObjectId(), 25));
      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) new SM_WAREHOUSE_INFO(legion.getLegionWarehouse().getStorageItems(),
              StorageType.LEGION_WAREHOUSE.getId(), 0, true));

      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) new SM_WAREHOUSE_INFO(null, StorageType.LEGION_WAREHOUSE.getId(), 0, false));

      return true;
    }

    public boolean canStoreLegionEmblem(Player activePlayer, int legionId, int emblemId) {
      Legion legion = activePlayer.getLegion();
      if (emblemId < 0 || emblemId > 40) {

        return false;
      }
      if (legionId != legion.getLegionId()) {

        return false;
      }
      if (legion.getLegionLevel() < 2) {

        return false;
      }
      if (activePlayer.getInventory().getKinahItem().getItemCount() < LegionConfig.LEGION_EMBLEM_REQUIRED_KINAH) {

        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(LegionConfig.LEGION_EMBLEM_REQUIRED_KINAH));

        return false;
      }
      return true;
    }

    private boolean isBrigadeGeneral(Player player) {
      return player.getLegionMember().isBrigadeGeneral();
    }

    private boolean isSelf(Player player, int targetObjId) {
      return player.sameObjectId(targetObjId);
    }

    private boolean isFreeName(String name) {
      return !((LegionDAO) DAOManager.getDAO(LegionDAO.class)).isNameUsed(name);
    }

    private boolean isValidSelfIntro(String name) {
      return LegionConfig.SELF_INTRO_PATTERN.matcher(name).matches();
    }

    private boolean isValidNickname(String name) {
      return LegionConfig.NICKNAME_PATTERN.matcher(name).matches();
    }

    private boolean isValidAnnouncement(String name) {
      return LegionConfig.ANNOUNCEMENT_PATTERN.matcher(name).matches();
    }
  }

  private static class SingletonHolder {
    protected static final LegionService instance = new LegionService();
  }
}

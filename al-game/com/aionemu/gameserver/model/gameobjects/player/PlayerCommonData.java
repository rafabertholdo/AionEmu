package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DP_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_EDIT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_DP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_EXP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.stats.XPLossEnum;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldPosition;
import java.sql.Timestamp;
import org.apache.log4j.Logger;

public class PlayerCommonData extends VisibleObjectTemplate {
  private static final Logger log = Logger.getLogger(PlayerCommonData.class);

  private final int playerObjId;

  private Race race;
  private String name;
  private PlayerClass playerClass;
  private int level = 0;
  private long exp = 0L;
  private long expRecoverable = 0L;
  private Gender gender;
  private Timestamp lastOnline;
  private boolean online;
  private String note;
  private WorldPosition position;
  private int cubeSize = 0;
  private int warehouseSize = 0;
  private int advencedStigmaSlotSize = 0;
  private int bindPoint;
  private int titleId = -1;
  private int dp = 0;

  private int mailboxLetters;

  public PlayerCommonData(int objId) {
    this.playerObjId = objId;
  }

  public int getPlayerObjId() {
    return this.playerObjId;
  }

  public long getExp() {
    return this.exp;
  }

  public int getCubeSize() {
    return this.cubeSize;
  }

  public void setCubesize(int cubeSize) {
    this.cubeSize = cubeSize;
  }

  public int getAdvencedStigmaSlotSize() {
    return this.advencedStigmaSlotSize;
  }

  public void setAdvencedStigmaSlotSize(int advencedStigmaSlotSize) {
    this.advencedStigmaSlotSize = advencedStigmaSlotSize;
  }

  public long getExpShown() {
    return this.exp - DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(this.level);
  }

  public long getExpNeed() {
    if (this.level == DataManager.PLAYER_EXPERIENCE_TABLE.getMaxLevel()) {
      return 0L;
    }
    return DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(this.level + 1)
        - DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(this.level);
  }

  public void calculateExpLoss() {
    long expLost = XPLossEnum.getExpLoss(this.level, getExpNeed());
    int unrecoverable = (int) (expLost * 0.33333333D);
    int recoverable = (int) expLost - unrecoverable;
    long allExpLost = recoverable + this.expRecoverable;

    if (getExpShown() > unrecoverable) {

      this.exp -= unrecoverable;
    } else {

      this.exp -= getExpShown();
    }
    if (getExpShown() > recoverable) {

      this.expRecoverable = allExpLost;
      this.exp -= recoverable;
    } else {

      this.expRecoverable += getExpShown();
      this.exp -= getExpShown();
    }
    if (getPlayer() != null) {
      PacketSendUtility.sendPacket(getPlayer(),
          (AionServerPacket) new SM_STATUPDATE_EXP(getExpShown(), getExpRecoverable(), getExpNeed()));
    }
  }

  public void setRecoverableExp(long expRecoverable) {
    this.expRecoverable = expRecoverable;
  }

  public void resetRecoverableExp() {
    long el = this.expRecoverable;
    this.expRecoverable = 0L;
    setExp(this.exp + el);
  }

  public long getExpRecoverable() {
    return this.expRecoverable;
  }

  public void addExp(long value) {
    setExp(this.exp + value);
    if (getPlayer() != null) {
      PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket) SM_SYSTEM_MESSAGE.EXP(Long.toString(value)));
    }
  }

  public void setExp(long exp) {
    int maxLevel = DataManager.PLAYER_EXPERIENCE_TABLE.getMaxLevel();

    if (getPlayerClass() != null && getPlayerClass().isStartingClass()) {
      maxLevel = 10;
    }
    long maxExp = DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(maxLevel);
    int level = 1;

    if (exp > maxExp) {
      exp = maxExp;
    }

    while (level + 1 != maxLevel && exp >= DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(level + 1)) {
      level++;
    }

    if (level != this.level) {

      this.level = level;
      this.exp = exp;

      if (getPlayer() != null) {
        upgradePlayer();
      }
    } else {

      this.exp = exp;

      if (getPlayer() != null) {
        PacketSendUtility.sendPacket(getPlayer(),
            (AionServerPacket) new SM_STATUPDATE_EXP(getExpShown(), getExpRecoverable(), getExpNeed()));
      }
    }
  }

  public void upgradePlayer() {
    Player player = getPlayer();
    if (player != null) {
      player.getController().upgradePlayer(this.level);
    }
  }

  public void addAp(int value) {
    Player player = getPlayer();

    if (player == null) {
      return;
    }

    PacketSendUtility.sendPacket(player,
        (AionServerPacket) SM_SYSTEM_MESSAGE.EARNED_ABYSS_POINT(String.valueOf(value)));

    setAp(value);

    if (player.isLegionMember() && value > 0) {

      player.getLegion().addContributionPoints(value);
      PacketSendUtility.broadcastPacketToLegion(player.getLegion(),
          (AionServerPacket) new SM_LEGION_EDIT(3, player.getLegion()));
    }
  }

  public void setAp(int value) {
    Player player = getPlayer();

    if (player == null) {
      return;
    }
    AbyssRank rank = player.getAbyssRank();

    int oldAbyssRank = rank.getRank().getId();

    rank.addAp(value);

    if (rank.getRank().getId() != oldAbyssRank) {

      PacketSendUtility.broadcastPacket((VisibleObject) player, (AionServerPacket) new SM_ABYSS_RANK_UPDATE(player));

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_ABYSS_RANK_UPDATE(player));
    }

    if (rank.getRank().getId() > oldAbyssRank) {

      if (getRace().getRaceId() == 0) {

        switch (rank.getRank().getId()) {

          case 14:
            player.getSkillList().addSkill(player, 9737, 1, true);
            player.getSkillList().addSkill(player, 9747, 1, true);
            break;
          case 15:
            player.getSkillList().removeSkill(player, 9737);
            player.getSkillList().removeSkill(player, 9747);
            player.getSkillList().addSkill(player, 9738, 1, true);
            player.getSkillList().addSkill(player, 9748, 1, true);
            player.getSkillList().addSkill(player, 9751, 1, true);
            break;
          case 16:
            player.getSkillList().removeSkill(player, 9738);
            player.getSkillList().removeSkill(player, 9748);
            player.getSkillList().removeSkill(player, 9751);
            player.getSkillList().addSkill(player, 9739, 1, true);
            player.getSkillList().addSkill(player, 9749, 1, true);
            player.getSkillList().addSkill(player, 9751, 1, true);
            player.getSkillList().addSkill(player, 9755, 1, true);
            break;
          case 17:
            player.getSkillList().removeSkill(player, 9739);
            player.getSkillList().removeSkill(player, 9749);
            player.getSkillList().removeSkill(player, 9751);
            player.getSkillList().removeSkill(player, 9755);
            player.getSkillList().addSkill(player, 9740, 1, true);
            player.getSkillList().addSkill(player, 9750, 1, true);
            player.getSkillList().addSkill(player, 9752, 1, true);
            player.getSkillList().addSkill(player, 9755, 1, true);
            player.getSkillList().addSkill(player, 9756, 1, true);
            break;
          case 18:
            player.getSkillList().removeSkill(player, 9740);
            player.getSkillList().removeSkill(player, 9750);
            player.getSkillList().removeSkill(player, 9752);
            player.getSkillList().removeSkill(player, 9755);
            player.getSkillList().removeSkill(player, 9756);
            player.getSkillList().addSkill(player, 9741, 1, true);
            player.getSkillList().addSkill(player, 9750, 1, true);
            player.getSkillList().addSkill(player, 9752, 1, true);
            player.getSkillList().addSkill(player, 9755, 1, true);
            player.getSkillList().addSkill(player, 9756, 1, true);
            player.getSkillList().addSkill(player, 9757, 1, true);
            player.getSkillList().addSkill(player, 9758, 1, true);
            break;
        }

      } else {
        switch (rank.getRank().getId()) {

          case 14:
            player.getSkillList().addSkill(player, 9742, 1, true);
            player.getSkillList().addSkill(player, 9747, 1, true);
            break;
          case 15:
            player.getSkillList().removeSkill(player, 9742);
            player.getSkillList().removeSkill(player, 9747);
            player.getSkillList().addSkill(player, 9743, 1, true);
            player.getSkillList().addSkill(player, 9748, 1, true);
            player.getSkillList().addSkill(player, 9753, 1, true);
            break;
          case 16:
            player.getSkillList().removeSkill(player, 9743);
            player.getSkillList().removeSkill(player, 9748);
            player.getSkillList().removeSkill(player, 9753);
            player.getSkillList().addSkill(player, 9744, 1, true);
            player.getSkillList().addSkill(player, 9749, 1, true);
            player.getSkillList().addSkill(player, 9753, 1, true);
            player.getSkillList().addSkill(player, 9755, 1, true);
            break;
          case 17:
            player.getSkillList().removeSkill(player, 9744);
            player.getSkillList().removeSkill(player, 9749);
            player.getSkillList().removeSkill(player, 9753);
            player.getSkillList().removeSkill(player, 9755);
            player.getSkillList().addSkill(player, 9745, 1, true);
            player.getSkillList().addSkill(player, 9750, 1, true);
            player.getSkillList().addSkill(player, 9754, 1, true);
            player.getSkillList().addSkill(player, 9755, 1, true);
            player.getSkillList().addSkill(player, 9756, 1, true);
            break;
          case 18:
            player.getSkillList().removeSkill(player, 9745);
            player.getSkillList().removeSkill(player, 9750);
            player.getSkillList().removeSkill(player, 9754);
            player.getSkillList().removeSkill(player, 9755);
            player.getSkillList().removeSkill(player, 9756);
            player.getSkillList().addSkill(player, 9746, 1, true);
            player.getSkillList().addSkill(player, 9750, 1, true);
            player.getSkillList().addSkill(player, 9754, 1, true);
            player.getSkillList().addSkill(player, 9755, 1, true);
            player.getSkillList().addSkill(player, 9756, 1, true);
            player.getSkillList().addSkill(player, 9757, 1, true);
            player.getSkillList().addSkill(player, 9758, 1, true);
            break;
        }

      }
    } else if (getRace().getRaceId() == 0) {

      switch (rank.getRank().getId()) {

        case 17:
          player.getSkillList().removeSkill(player, 9741);
          player.getSkillList().removeSkill(player, 9750);
          player.getSkillList().removeSkill(player, 9752);
          player.getSkillList().removeSkill(player, 9755);
          player.getSkillList().removeSkill(player, 9756);
          player.getSkillList().removeSkill(player, 9757);
          player.getSkillList().removeSkill(player, 9758);
          player.getSkillList().addSkill(player, 9740, 1, true);
          player.getSkillList().addSkill(player, 9750, 1, true);
          player.getSkillList().addSkill(player, 9752, 1, true);
          player.getSkillList().addSkill(player, 9755, 1, true);
          player.getSkillList().addSkill(player, 9756, 1, true);
          break;
        case 16:
          player.getSkillList().removeSkill(player, 9738);
          player.getSkillList().removeSkill(player, 9748);
          player.getSkillList().removeSkill(player, 9751);
          player.getSkillList().addSkill(player, 9739, 1, true);
          player.getSkillList().addSkill(player, 9749, 1, true);
          player.getSkillList().addSkill(player, 9751, 1, true);
          player.getSkillList().addSkill(player, 9755, 1, true);
          break;
        case 15:
          player.getSkillList().removeSkill(player, 9737);
          player.getSkillList().removeSkill(player, 9747);
          player.getSkillList().addSkill(player, 9738, 1, true);
          player.getSkillList().addSkill(player, 9748, 1, true);
          player.getSkillList().addSkill(player, 9751, 1, true);
          break;
        case 14:
          player.getSkillList().removeSkill(player, 9738);
          player.getSkillList().removeSkill(player, 9748);
          player.getSkillList().removeSkill(player, 9751);
          player.getSkillList().addSkill(player, 9737, 1, true);
          player.getSkillList().addSkill(player, 9747, 1, true);
          break;
        case 13:
          player.getSkillList().removeSkill(player, 9737);
          player.getSkillList().removeSkill(player, 9747);
          break;
      }

    } else {
      switch (rank.getRank().getId()) {

        case 17:
          player.getSkillList().removeSkill(player, 9745);
          player.getSkillList().removeSkill(player, 9750);
          player.getSkillList().removeSkill(player, 9754);
          player.getSkillList().removeSkill(player, 9755);
          player.getSkillList().removeSkill(player, 9756);
          player.getSkillList().addSkill(player, 9745, 1, true);
          player.getSkillList().addSkill(player, 9750, 1, true);
          player.getSkillList().addSkill(player, 9754, 1, true);
          player.getSkillList().addSkill(player, 9755, 1, true);
          player.getSkillList().addSkill(player, 9756, 1, true);
          break;
        case 16:
          player.getSkillList().removeSkill(player, 9745);
          player.getSkillList().removeSkill(player, 9750);
          player.getSkillList().removeSkill(player, 9754);
          player.getSkillList().removeSkill(player, 9755);
          player.getSkillList().removeSkill(player, 9756);
          player.getSkillList().addSkill(player, 9744, 1, true);
          player.getSkillList().addSkill(player, 9749, 1, true);
          player.getSkillList().addSkill(player, 9753, 1, true);
          player.getSkillList().addSkill(player, 9755, 1, true);
          break;
        case 15:
          player.getSkillList().removeSkill(player, 9744);
          player.getSkillList().removeSkill(player, 9749);
          player.getSkillList().removeSkill(player, 9753);
          player.getSkillList().removeSkill(player, 9755);
          player.getSkillList().addSkill(player, 9743, 1, true);
          player.getSkillList().addSkill(player, 9748, 1, true);
          player.getSkillList().addSkill(player, 9753, 1, true);
          break;
        case 14:
          player.getSkillList().removeSkill(player, 9743);
          player.getSkillList().removeSkill(player, 9748);
          player.getSkillList().removeSkill(player, 9753);
          player.getSkillList().addSkill(player, 9742, 1, true);
          player.getSkillList().addSkill(player, 9747, 1, true);
          break;
        case 13:
          player.getSkillList().removeSkill(player, 9742);
          player.getSkillList().removeSkill(player, 9747);
          break;
      }

    }
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_ABYSS_RANK(player.getAbyssRank()));
  }

  public Race getRace() {
    return this.race;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PlayerClass getPlayerClass() {
    return this.playerClass;
  }

  public void setPlayerClass(PlayerClass playerClass) {
    this.playerClass = playerClass;
  }

  public boolean isOnline() {
    return this.online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public Gender getGender() {
    return this.gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public WorldPosition getPosition() {
    return this.position;
  }

  public Timestamp getLastOnline() {
    return this.lastOnline;
  }

  public void setBindPoint(int bindId) {
    this.bindPoint = bindId;
  }

  public int getBindPoint() {
    return this.bindPoint;
  }

  public void setLastOnline(Timestamp timestamp) {
    this.lastOnline = timestamp;
  }

  public int getLevel() {
    return this.level;
  }

  public void setLevel(int level) {
    if (level <= DataManager.PLAYER_EXPERIENCE_TABLE.getMaxLevel()) {
      setExp(DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(level));
    }
  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public int getTitleId() {
    return this.titleId;
  }

  public void setTitleId(int titleId) {
    this.titleId = titleId;
  }

  public void setPosition(WorldPosition position) {
    if (this.position != null) {
      throw new IllegalStateException("position already set");
    }
    this.position = position;
  }

  public Player getPlayer() {
    if (this.online && getPosition() != null) {
      return World.getInstance().findPlayer(this.playerObjId);
    }
    return null;
  }

  public void addDp(int dp) {
    setDp(this.dp + dp);
  }

  public void setDp(int dp) {
    if (getPlayer() != null) {

      if (this.playerClass.isStartingClass()) {
        return;
      }
      int maxDp = getPlayer().getGameStats().getCurrentStat(StatEnum.MAXDP);
      this.dp = (dp > maxDp) ? maxDp : dp;

      PacketSendUtility.broadcastPacket(getPlayer(), (AionServerPacket) new SM_DP_INFO(this.playerObjId, this.dp),
          true);
      PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket) new SM_STATS_INFO(getPlayer()));
      PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket) new SM_STATUPDATE_DP(this.dp));
    } else {

      log.warn("CHECKPOINT : getPlayer in PCD return null for setDP " + isOnline() + " " + getPosition());
    }
  }

  public int getDp() {
    return this.dp;
  }

  public int getTemplateId() {
    return 100000 + this.race.getRaceId() * 2 + this.gender.getGenderId();
  }

  public int getNameId() {
    return 0;
  }

  public void setWarehouseSize(int warehouseSize) {
    this.warehouseSize = warehouseSize;
  }

  public int getWarehouseSize() {
    return this.warehouseSize;
  }

  public void setMailboxLetters(int count) {
    this.mailboxLetters = count;
  }

  public int getMailboxLetters() {
    return this.mailboxLetters;
  }
}

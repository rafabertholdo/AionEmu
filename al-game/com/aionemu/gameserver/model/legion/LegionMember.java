package com.aionemu.gameserver.model.legion;

public class LegionMember {
  private static final int LP_CENT_NONE = 96;
  private int objectId = 0;
  protected Legion legion = null;
  protected String nickname = "";
  protected String selfIntro = "";

  protected LegionRank rank = LegionRank.LEGIONARY;

  public LegionMember(int objectId) {
    this.objectId = objectId;
  }

  public LegionMember(int objectId, Legion legion, LegionRank rank) {
    setObjectId(objectId);
    setLegion(legion);
    setRank(rank);
  }

  public LegionMember() {
  }

  public void setLegion(Legion legion) {
    this.legion = legion;
  }

  public Legion getLegion() {
    return this.legion;
  }

  public void setRank(LegionRank rank) {
    this.rank = rank;
  }

  public LegionRank getRank() {
    return this.rank;
  }

  public boolean isBrigadeGeneral() {
    return (this.rank == LegionRank.BRIGADE_GENERAL);
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setSelfIntro(String selfIntro) {
    this.selfIntro = selfIntro;
  }

  public String getSelfIntro() {
    return this.selfIntro;
  }

  public void setObjectId(int objectId) {
    this.objectId = objectId;
  }

  public int getObjectId() {
    return this.objectId;
  }

  public boolean hasRights(int type) {
    if (getRank() == LegionRank.BRIGADE_GENERAL) {
      return true;
    }
    int legionarPermission2 = getLegion().getLegionarPermission2();
    int centurionPermission1 = getLegion().getCenturionPermission1() - 96;
    int centurionPermission2 = getLegion().getCenturionPermission2();

    switch (type) {

      case 1:
        if (getRank().canInviteToLegion(centurionPermission1)) {
          return true;
        }
      case 2:
        if (getRank().canKickFromLegion(centurionPermission1)) {
          return true;
        }
      case 3:
        if (getRank().canUseLegionWarehouse(centurionPermission1)) {
          return true;
        }
      case 4:
        if (getRank().canEditAnnouncement(centurionPermission2)) {
          return true;
        }
      case 5:
        if (getRank().canUseArtifact(centurionPermission2)) {
          return true;
        }
      case 6:
        if (getRank().canUseGateGuardianStone(centurionPermission2, legionarPermission2))
          return true;
        break;
    }
    return false;
  }
}

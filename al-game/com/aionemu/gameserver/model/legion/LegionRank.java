package com.aionemu.gameserver.model.legion;

public enum LegionRank {
  BRIGADE_GENERAL(0), CENTURION(1), LEGIONARY(2);

  private static final int LP_LEGION_WAREHOUSE = 4;

  private static final int LP_INVITE_TO_LEGION = 8;

  private static final int LP_KICK_FROM_LEGION = 16;

  private static final int LP_COMBINATION_60_12 = 12;

  private static final int LP_COMBINATION_60_13 = 20;

  private static final int LP_COMBINATION_60_23 = 24;

  private static final int LP_COMBINATION_60_123 = 28;
  private static final int LP_EDIT_ANNOUNCEMENT = 2;
  private static final int LP_ARTIFACT = 4;
  private static final int LP_GATE_GUARDIAN_STONE = 8;
  private static final int LP_COMBINATION_00_12 = 6;
  private static final int LP_COMBINATION_00_13 = 10;
  private static final int LP_COMBINATION_00_23 = 12;
  private static final int LP_COMBINATION_00_123 = 14;
  private byte rank;

  LegionRank(int rank) {
    this.rank = (byte) rank;
  }

  public byte getRankId() {
    return this.rank;
  }

  public boolean canUseGateGuardianStone(int centurionPermission2, int legionarPermission2) {
    switch (this) {

      case CENTURION:
        if (centurionPermission2 == 8 || centurionPermission2 == 10 || centurionPermission2 == 12
            || centurionPermission2 == 14) {

          return true;
        }
        break;
      case LEGIONARY:
        if (legionarPermission2 == 8)
          return true;
        break;
    }
    return false;
  }

  public boolean canUseArtifact(int centurionPermission2) {
    switch (this) {

      case CENTURION:
        if (centurionPermission2 == 4 || centurionPermission2 == 6 || centurionPermission2 == 12
            || centurionPermission2 == 14) {

          return true;
        }
        break;
    }
    return false;
  }

  public boolean canEditAnnouncement(int centurionPermission2) {
    switch (this) {

      case CENTURION:
        if (centurionPermission2 == 2 || centurionPermission2 == 10 || centurionPermission2 == 12
            || centurionPermission2 == 14) {

          return true;
        }
        break;
    }
    return false;
  }

  public boolean canUseLegionWarehouse(int centurionPermission1) {
    switch (this) {

      case CENTURION:
        if (centurionPermission1 == 4 || centurionPermission1 == 20 || centurionPermission1 == 20
            || centurionPermission1 == 28) {

          return true;
        }
        break;
    }
    return false;
  }

  public boolean canKickFromLegion(int centurionPermission1) {
    switch (this) {

      case CENTURION:
        if (centurionPermission1 == 16 || centurionPermission1 == 12 || centurionPermission1 == 24
            || centurionPermission1 == 28) {

          return true;
        }
        break;
    }
    return false;
  }

  public boolean canInviteToLegion(int centurionPermission1) {
    switch (this) {

      case CENTURION:
        if (centurionPermission1 == 8 || centurionPermission1 == 20 || centurionPermission1 == 24
            || centurionPermission1 == 28) {

          return true;
        }
        break;
    }
    return false;
  }
}

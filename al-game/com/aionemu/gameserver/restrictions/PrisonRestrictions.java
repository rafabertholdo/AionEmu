package com.aionemu.gameserver.restrictions;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapType;

public class PrisonRestrictions extends AbstractRestrictions {
  public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You are in prison!");
      return true;
    }

    return false;
  }

  public boolean canAttack(Player player, VisibleObject target) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You cannot attack in prison!");
      return false;
    }

    return true;
  }

  public boolean canUseSkill(Player player, Skill skill) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You cannot use skills in prison!");
      return false;
    }

    return true;
  }

  public boolean canAffectBySkill(Player player, VisibleObject target) {
    return true;
  }

  public boolean canChat(Player player) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You cannot chat in prison!");
      return false;
    }

    return true;
  }

  public boolean canInviteToGroup(Player player, Player target) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You cannot invite members to group in prison!");
      return false;
    }

    return true;
  }

  public boolean canInviteToAlliance(Player player, Player target) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You cannot invite members to alliance in prison!");
      return false;
    }

    return true;
  }

  public boolean canChangeEquip(Player player) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You cannot equip / unequip item in prison!");
      return false;
    }

    return true;
  }

  public boolean canUseItem(Player player) {
    if (isInPrison(player)) {

      PacketSendUtility.sendMessage(player, "You cannot use item in prison!");
      return false;
    }
    return true;
  }

  private boolean isInPrison(Player player) {
    return (player.isInPrison() || player.getWorldId() == WorldMapType.PRISON.getId());
  }
}

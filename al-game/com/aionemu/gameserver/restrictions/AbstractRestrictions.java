package com.aionemu.gameserver.restrictions;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;

public abstract class AbstractRestrictions implements Restrictions {
  public void activate() {
    RestrictionsManager.activate(this);
  }

  public void deactivate() {
    RestrictionsManager.deactivate(this);
  }

  public int hashCode() {
    return getClass().hashCode();
  }

  public boolean equals(Object obj) {
    return getClass().equals(obj.getClass());
  }

  @DisabledRestriction
  public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canAttack(Player player, VisibleObject target) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canAffectBySkill(Player player, VisibleObject target) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canUseSkill(Player player, Skill skill) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canChat(Player player) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canInviteToGroup(Player player, Player target) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canChangeEquip(Player player) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canUseWarehouse(Player player) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canTrade(Player player) {
    throw new AbstractMethodError();
  }

  @DisabledRestriction
  public boolean canUseItem(Player player) {
    throw new AbstractMethodError();
  }
}

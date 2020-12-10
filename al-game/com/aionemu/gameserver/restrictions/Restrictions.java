package com.aionemu.gameserver.restrictions;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;

public interface Restrictions {
  boolean isRestricted(Player paramPlayer, Class<? extends Restrictions> paramClass);

  boolean canAttack(Player paramPlayer, VisibleObject paramVisibleObject);

  boolean canAffectBySkill(Player paramPlayer, VisibleObject paramVisibleObject);

  boolean canUseSkill(Player paramPlayer, Skill paramSkill);

  boolean canChat(Player paramPlayer);

  boolean canInviteToGroup(Player paramPlayer1, Player paramPlayer2);

  boolean canInviteToAlliance(Player paramPlayer1, Player paramPlayer2);

  boolean canChangeEquip(Player paramPlayer);

  boolean canUseWarehouse(Player paramPlayer);

  boolean canTrade(Player paramPlayer);

  boolean canUseItem(Player paramPlayer);
}

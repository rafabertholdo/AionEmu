package com.aionemu.gameserver.restrictions;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.lang.ArrayUtils;

public final class RestrictionsManager {
  private enum RestrictionMode implements Comparator<Restrictions> {
    isRestricted, canAttack, canAffectBySkill, canUseSkill, canChat, canInviteToGroup, canInviteToAlliance,
    canChangeEquip, canTrade, canUseWarehouse, canUseItem;

    private final Method METHOD;

    RestrictionMode() {
      for (Method method : Restrictions.class.getMethods()) {
        if (name().equals(method.getName())) {
          this.METHOD = method;
          return;
        }
      }
      throw new InternalError();
    }

    private static final RestrictionMode[] VALUES = values();

    private boolean equalsMethod(Method method) {
      if (!this.METHOD.getName().equals(method.getName()))
        return false;
      if (!this.METHOD.getReturnType().equals(method.getReturnType()))
        return false;
      return Arrays.equals((Object[]) this.METHOD.getParameterTypes(), (Object[]) method.getParameterTypes());
    }

    private static RestrictionMode parse(Method method) {
      for (RestrictionMode mode : VALUES) {

        if (mode.equalsMethod(method)) {
          return mode;
        }
      }
      return null;
    }

    public int compare(Restrictions o1, Restrictions o2) {
      return Double.compare(getPriority(o2), getPriority(o1));
    }

    static {

    }

    private double getPriority(Restrictions restriction) {
      RestrictionPriority a1 = getMatchingMethod((Class) restriction.getClass())
          .<RestrictionPriority>getAnnotation(RestrictionPriority.class);
      if (a1 != null) {
        return a1.value();
      }
      RestrictionPriority a2 = restriction.getClass().<RestrictionPriority>getAnnotation(RestrictionPriority.class);
      if (a2 != null) {
        return a2.value();
      }
      return 0.0D;
    }

    private Method getMatchingMethod(Class<? extends Restrictions> clazz) {
      for (Method method : clazz.getMethods()) {

        if (equalsMethod(method)) {
          return method;
        }
      }
      throw new InternalError();
    }
  }

  private static final Restrictions[][] RESTRICTIONS = new Restrictions[RestrictionMode.VALUES.length][0];

  public static synchronized void activate(Restrictions restriction) {
    for (Method method : restriction.getClass().getMethods()) {

      RestrictionMode mode = RestrictionMode.parse(method);

      if (mode != null) {

        if (method.getAnnotation(DisabledRestriction.class) == null) {

          Restrictions[] restrictions = RESTRICTIONS[mode.ordinal()];

          if (!ArrayUtils.contains((Object[]) restrictions, restriction)) {
            restrictions = (Restrictions[]) ArrayUtils.add((Object[]) restrictions, restriction);
          }
          Arrays.sort(restrictions, mode);

          RESTRICTIONS[mode.ordinal()] = restrictions;
        }
      }
    }
  }

  public static synchronized void deactivate(Restrictions restriction) {
    for (RestrictionMode mode : RestrictionMode.VALUES) {

      Restrictions[] restrictions = RESTRICTIONS[mode.ordinal()];
      int index;
      while ((index = ArrayUtils.indexOf((Object[]) restrictions, restriction)) != -1) {
        restrictions = (Restrictions[]) ArrayUtils.remove((Object[]) restrictions, index);
      }
      RESTRICTIONS[mode.ordinal()] = restrictions;
    }
  }

  static {
    activate(new PlayerRestrictions());

    activate(new ShutdownRestrictions());

    activate(new PrisonRestrictions());
  }

  public static boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
    if (player == null) {
      return true;
    }
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.isRestricted.ordinal()]) {

      if (!restrictions.isRestricted(player, callingRestriction)) {
        return false;
      }
    }
    return false;
  }

  public static boolean canAttack(Player player, VisibleObject target) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canAttack.ordinal()]) {

      if (!restrictions.canAttack(player, target)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canAffectBySkill(Player player, VisibleObject target) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canAffectBySkill.ordinal()]) {

      if (!restrictions.canAffectBySkill(player, target)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canUseSkill(Player player, Skill skill) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canUseSkill.ordinal()]) {

      if (!restrictions.canUseSkill(player, skill)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canChat(Player player) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canChat.ordinal()]) {

      if (!restrictions.canChat(player)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canInviteToGroup(Player player, Player target) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canInviteToGroup.ordinal()]) {

      if (!restrictions.canInviteToGroup(player, target)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canInviteToAlliance(Player player, Player target) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canInviteToAlliance.ordinal()]) {

      if (!restrictions.canInviteToAlliance(player, target)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canChangeEquip(Player player) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canChangeEquip.ordinal()]) {

      if (!restrictions.canChangeEquip(player)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canTrade(Player player) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canTrade.ordinal()]) {

      if (!restrictions.canTrade(player)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canUseWarehouse(Player player) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canUseWarehouse.ordinal()]) {

      if (!restrictions.canUseWarehouse(player)) {
        return false;
      }
    }
    return true;
  }

  public static boolean canUseItem(Player player) {
    for (Restrictions restrictions : RESTRICTIONS[RestrictionMode.canUseItem.ordinal()]) {

      if (!restrictions.canUseItem(player))
        return false;
    }
    return true;
  }
}

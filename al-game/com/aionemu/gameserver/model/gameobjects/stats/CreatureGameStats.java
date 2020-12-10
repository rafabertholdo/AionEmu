package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.SkillElement;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.id.ItemStatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javolution.text.TextBuilder;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class CreatureGameStats<T extends Creature> {
  protected static final Logger log = Logger.getLogger(CreatureGameStats.class);

  private static final int ATTACK_MAX_COUNTER = 2147483647;

  protected FastMap<StatEnum, Stat> stats;

  protected FastMap<StatEffectId, TreeSet<StatModifier>> statsModifiers;

  private int attackCounter = 0;
  protected T owner = null;
  protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  protected CreatureGameStats(T owner) {
    this.owner = owner;
    this.stats = new FastMap();
    this.statsModifiers = (new FastMap()).shared();
  }

  public int getAttackCounter() {
    return this.attackCounter;
  }

  protected void setAttackCounter(int attackCounter) {
    if (attackCounter <= 0) {

      this.attackCounter = 1;
    } else {

      this.attackCounter = attackCounter;
    }
  }

  public void increaseAttackCounter() {
    if (this.attackCounter == Integer.MAX_VALUE) {

      this.attackCounter = 1;
    } else {

      this.attackCounter++;
    }
  }

  public void setStat(StatEnum stat, int value) {
    this.lock.writeLock().lock();

    try {
      setStat(stat, value, false);
    } finally {

      this.lock.writeLock().unlock();
    }
  }

  public int getBaseStat(StatEnum stat) {
    int value = 0;
    this.lock.readLock().lock();

    try {
      if (this.stats.containsKey(stat)) {
        value = ((Stat) this.stats.get(stat)).getBase();
      }
    } finally {

      this.lock.readLock().unlock();
    }
    return value;
  }

  public int getStatBonus(StatEnum stat) {
    int value = 0;
    this.lock.readLock().lock();

    try {
      if (this.stats.containsKey(stat)) {
        value = ((Stat) this.stats.get(stat)).getBonus();
      }
    } finally {

      this.lock.readLock().unlock();
    }
    return value;
  }

  public int getCurrentStat(StatEnum stat) {
    int value = 0;

    this.lock.readLock().lock();

    try {
      if (this.stats.containsKey(stat)) {
        value = ((Stat) this.stats.get(stat)).getCurrent();
      }
    } finally {

      this.lock.readLock().unlock();
    }

    return value;
  }

  public int getOldStat(StatEnum stat) {
    int value = 0;

    this.lock.readLock().lock();

    try {
      if (this.stats.containsKey(stat)) {
        value = ((Stat) this.stats.get(stat)).getOld();
      }
    } finally {

      this.lock.readLock().unlock();
    }

    return value;
  }

  public void addModifiers(StatEffectId id, TreeSet<StatModifier> modifiers) {
    if (modifiers == null) {
      return;
    }
    if (this.statsModifiers.containsKey(id)) {
      throw new IllegalArgumentException("Effect " + id + " already active");
    }
    this.statsModifiers.put(id, modifiers);
    recomputeStats();
  }

  public boolean effectAlreadyAdded(StatEffectId id) {
    return this.statsModifiers.containsKey(id);
  }

  public void recomputeStats() {
    this.lock.writeLock().lock();

    try {
      resetStats();
      FastMap<StatEnum, StatModifiers> orderedModifiers = new FastMap();

      for (Map.Entry<StatEffectId, TreeSet<StatModifier>> modifiers : (Iterable<Map.Entry<StatEffectId, TreeSet<StatModifier>>>) this.statsModifiers
          .entrySet()) {

        StatEffectId eid = modifiers.getKey();
        int slots = 0;

        if (modifiers.getValue() == null) {
          continue;
        }
        for (StatModifier modifier : modifiers.getValue()) {

          if (eid instanceof ItemStatEffectId) {
            slots = ((ItemStatEffectId) eid).getSlot();
          }
          if (slots == 0)
            slots = ItemSlot.NONE.getSlotIdMask();
          if (modifier.getStat().isMainOrSubHandStat() && this.owner instanceof Player) {
            if (slots != ItemSlot.MAIN_HAND.getSlotIdMask() && slots != ItemSlot.SUB_HAND.getSlotIdMask()) {

              if (((Player) this.owner).getEquipment().getOffHandWeaponType() != null) {
                slots = ItemSlot.MAIN_OR_SUB.getSlotIdMask();
              } else {

                slots = ItemSlot.MAIN_HAND.getSlotIdMask();
                setStat(StatEnum.OFF_HAND_ACCURACY, 0, false);
              }

            } else if (slots == ItemSlot.MAIN_HAND.getSlotIdMask()) {
              setStat(StatEnum.MAIN_HAND_POWER, 0);
            }
          }
          FastList fastList = ItemSlot.getSlotsFor(slots);
          for (ItemSlot slot : fastList) {

            StatEnum statToModify = modifier.getStat().getMainOrSubHandStat(slot);
            if (!orderedModifiers.containsKey(statToModify)) {
              orderedModifiers.put(statToModify, new StatModifiers());
            }
            ((StatModifiers) orderedModifiers.get(statToModify)).add(modifier);
          }
        }
      }

      for (Map.Entry<StatEnum, StatModifiers> entry : (Iterable<Map.Entry<StatEnum, StatModifiers>>) orderedModifiers
          .entrySet()) {
        applyModifiers(entry.getKey(), entry.getValue());
      }

      setStat(StatEnum.ATTACK_SPEED,
          Math.round(
              getBaseStat(StatEnum.MAIN_HAND_ATTACK_SPEED) + getBaseStat(StatEnum.OFF_HAND_ATTACK_SPEED) * 0.25F),
          false);

      setStat(StatEnum.ATTACK_SPEED,
          getStatBonus(StatEnum.MAIN_HAND_ATTACK_SPEED) + getStatBonus(StatEnum.OFF_HAND_ATTACK_SPEED), true);

    } finally {

      this.lock.writeLock().unlock();
    }
  }

  public void endEffect(StatEffectId id) {
    this.statsModifiers.remove(id);
    recomputeStats();
  }

  public int getMagicalDefenseFor(SkillElement element) {
    switch (element) {

      case MAXHP:
        return getCurrentStat(StatEnum.EARTH_RESISTANCE);
      case MAXMP:
        return getCurrentStat(StatEnum.FIRE_RESISTANCE);
      case null:
        return getCurrentStat(StatEnum.WATER_RESISTANCE);
      case null:
        return getCurrentStat(StatEnum.WIND_RESISTANCE);
    }
    return 0;
  }

  protected void resetStats() {
    for (Stat stat : this.stats.values()) {
      stat.reset();
    }
  }

  protected void applyModifiers(final StatEnum stat, StatModifiers modifiers) {
    if (modifiers == null) {
      return;
    }
    if (!this.stats.containsKey(stat)) {
      initStat(stat, 0);
    }

    Stat oStat = (Stat) this.stats.get(stat);
    for (StatModifierPriority priority : StatModifierPriority.values()) {

      FastList<StatModifier> mod = modifiers.getModifiers(priority);
      for (FastList.Node<StatModifier> n = mod.head(), listEnd = mod.tail(); (n = n.getNext()) != listEnd;) {

        StatModifier modifier = (StatModifier) n.getValue();
        final int newValue = modifier.apply(oStat.getBase(), oStat.getCurrent());
        oStat.increase(newValue, modifier.isBonus());
      }
    }

    if (stat == StatEnum.MAXHP || stat == StatEnum.MAXMP) {

      final int oldValue = getOldStat(stat);
      final int newValue = getCurrentStat(stat);
      if (oldValue == newValue) {
        return;
      }

      final CreatureLifeStats<? extends Creature> lifeStats = this.owner.getLifeStats();
      ThreadPoolManager.getInstance().schedule(new Runnable() {
        public void run() {
          int hp;
          int mp;
          switch (stat) {

            case MAXHP:
              hp = lifeStats.getCurrentHp();
              hp = hp * newValue / oldValue;
              lifeStats.setCurrentHp(hp);
              break;
            case MAXMP:
              mp = lifeStats.getCurrentMp();
              mp = mp * newValue / oldValue;
              lifeStats.setCurrentMp(mp);
              break;
          }
        }
      }, 0L);
    }
  }

  protected void initStat(StatEnum stat, int value) {
    if (!this.stats.containsKey(stat)) {

      this.stats.put(stat, new Stat(stat, value));
    } else {

      ((Stat) this.stats.get(stat)).reset();
      ((Stat) this.stats.get(stat)).set(value, false);
    }
  }

  protected void setStat(StatEnum stat, int value, boolean bonus) {
    if (!this.stats.containsKey(stat)) {
      this.stats.put(stat, new Stat(stat, 0));
    }
    ((Stat) this.stats.get(stat)).set(value, bonus);
  }

  public String toString() {
    TextBuilder tb = TextBuilder.newInstance();

    tb.append('{');
    tb.append("owner:" + this.owner.getObjectId());
    for (Stat stat : this.stats.values()) {
      tb.append(stat);
    }
    tb.append('}');
    String toString = tb.toString();

    TextBuilder.recycle(tb);

    return toString;
  }
}

package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.items.ItemSlot;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "StatEnum")
@XmlEnum
public enum StatEnum {
  MAXDP(0, "maxdp"), MAXHP(18, "maxhp"), MAXMP(20, "maxmp"),

  AGILITY(107, "agility", true), BLOCK(33, "block"), EVASION(31, "dodge"), CONCENTRATION(41, "concentration"),
  WILL(0, "will", true), HEALTH(0, "health", true), ACCURACY(0, "accuracy", true), KNOWLEDGE(106, "knowledge", true),
  PARRY(32, "parry"), POWER(0, "strength", true), SPEED(36, "speed", true), HIT_COUNT(0, "hitcount", true),

  ATTACK_RANGE(0, "attackrange", true), ATTACK_SPEED(29, "attackdelay", -1, true), PHYSICAL_ATTACK(25, "phyattack"),
  PHYSICAL_ACCURACY(30, "hitaccuracy"), PHYSICAL_CRITICAL(34, "critical"), PHYSICAL_DEFENSE(26, "physicaldefend"),
  MAIN_HAND_HITS(0, "mainhandhits"), MAIN_HAND_ACCURACY(0, "mainhandaccuracy"),
  MAIN_HAND_CRITICAL(0, "mainhandcritical"), MAIN_HAND_POWER(0, "mainhandpower"),
  MAIN_HAND_ATTACK_SPEED(0, "mainhandattackspeed"), OFF_HAND_HITS(0, "offhandhits"),
  OFF_HAND_ACCURACY(0, "offhandaccuracy"), OFF_HAND_CRITICAL(0, "offhandcritical"), OFF_HAND_POWER(0, "offhandpower"),
  OFF_HAND_ATTACK_SPEED(0, "offhandattackspeed"), CRITICAL_RESIST(0, "physicalcriticalreducerate"),

  MAGICAL_ATTACK(27, "magicalattack"), MAGICAL_ACCURACY(105, "magicalhitaccuracy"),
  MAGICAL_CRITICAL(40, "magicalcritical"), MAGICAL_RESIST(28, "magicalresist"), MAX_DAMAGES(0, "maxdamages"),
  MIN_DAMAGES(0, "mindamages"), IS_MAGICAL_ATTACK(0, "ismagicalattack", true),

  EARTH_RESISTANCE(0, "elementaldefendearth"), FIRE_RESISTANCE(15, "elementaldefendfire"),
  WIND_RESISTANCE(0, "elementaldefendair"), WATER_RESISTANCE(0, "elementaldefendwater"),

  BOOST_MAGICAL_SKILL(104, "magicalskillboost"), BOOST_CASTING_TIME(0, "boostcastingtime"),
  BOOST_HATE(109, "boosthate"), BOOST_HEAL(0, "boostheal"),

  FLY_TIME(23, "maxfp"), FLY_SPEED(37, "flyspeed"),

  PVP_ATTACK_RATIO(0, "pvpattackratio"), PVP_DEFEND_RATIO(0, "pvpdefendratio"),

  DAMAGE_REDUCE(0, "damagereduce"),

  BLEED_RESISTANCE(0, "arbleed"), BLIND_RESISTANCE(0, "arblind"), CHARM_RESISTANCE(0, "archarm"),
  CONFUSE_RESISTANCE(0, "arconfuse"), CURSE_RESISTANCE(0, "arcurse"), DISEASE_RESISTANCE(0, "ardisease"),
  FEAR_RESISTANCE(0, "arfear"), OPENAREIAL_RESISTANCE(0, "aropenareial"), PARALYZE_RESISTANCE(0, "arparalyze"),
  PERIFICATION_RESISTANCE(0, "arperification"), POISON_RESISTANCE(0, "arpoison"), ROOT_RESISTANCE(0, "arroot"),
  SILENCE_RESISTANCE(0, "arsilence"), SLEEP_RESISTANCE(0, "arsleep"), SLOW_RESISTANCE(0, "arslow"),
  SNARE_RESISTANCE(0, "arsnare"), SPIN_RESISTANCE(0, "arspin"), STAGGER_RESISTANCE(0, "arstagger"),
  STUMBLE_RESISTANCE(0, "arstumble"), STUN_RESISTANCE(0, "arstun"),

  REGEN_MP(0, "mpregen"), REGEN_HP(0, "hpregen"),

  REGEN_FP(0, "fpregen"), STAGGER_BOOST(0, "stagger_arp"), STUMBLE_BOOST(0, "stumble_arp"), STUN_BOOST(0, "stun_arp"),
  HEAL_BOOST(0, "healskillboost"), ALLRESIST(0, "allresist"), STUNLIKE_RESISTANCE(0, "arstunlike"),
  ELEMENTAL_RESISTANCE_DARK(0, "elemental_resistance_dark"),
  ELEMENTAL_RESISTANCE_LIGHT(0, "elemental_resistance_light"), MAGICAL_CRITICAL_RESIST(0, "magicalcriticalresist"),
  MAGICAL_CRITICAL_DAMAGE_REDUCE(0, "magicalcriticaldamagereduce"),
  PHYSICAL_CRITICAL_RESIST(0, "physicalcriticalresist"),
  PHYSICAL_CRITICAL_DAMAGE_REDUCE(0, "physicalcriticalreducerate"), ERFIRE(0, "erfire"), ERAIR(0, "erair"),
  EREARTH(0, "erearth"), ERWATER(0, "erwater"), ABNORMAL_RESISTANCE_ALL(0, "abnormal_resistance_all"),
  MAGICAL_DEFEND(0, "magical_defend"), ALLPARA(0, "allpara"), KNOWIL(0, "knowil"), AGIDEX(0, "agidex"),
  STRVIT(0, "strvit");

  private String name;

  private boolean replace;

  private int sign;

  private int itemStoneMask;

  StatEnum(int stoneMask, String name, int sign, boolean replace) {
    this.itemStoneMask = stoneMask;
    this.name = name;
    this.replace = replace;
    this.sign = sign;
  }

  public String getName() {
    return this.name;
  }

  public int getSign() {
    return this.sign;
  }

  public int getItemStoneMask() {
    return this.itemStoneMask;
  }

  public static StatEnum find(String name) {
    for (StatEnum sEnum : values()) {

      if (sEnum.getName().toLowerCase().equals(name.toLowerCase())) {
        return sEnum;
      }
    }
    throw new IllegalArgumentException("Cannot find StatEnum for: " + name);
  }

  public static StatEnum findByItemStoneMask(int mask) {
    for (StatEnum sEnum : values()) {

      if (sEnum.getItemStoneMask() == mask) {
        return sEnum;
      }
    }
    throw new IllegalArgumentException("Cannot find StatEnum for stone mask: " + mask);
  }

  public StatEnum getMainOrSubHandStat(ItemSlot slot) {
    if (slot == null)
      return this;
    switch (this) {

      case PHYSICAL_ATTACK:
      case POWER:
        switch (slot) {

          case PHYSICAL_ATTACK:
            return OFF_HAND_POWER;
          case POWER:
            return MAIN_HAND_POWER;
        }
      case PHYSICAL_ACCURACY:
        switch (slot) {

          case PHYSICAL_ATTACK:
            return OFF_HAND_ACCURACY;
          case POWER:
            return MAIN_HAND_ACCURACY;
        }
      case PHYSICAL_CRITICAL:
        switch (slot) {

          case PHYSICAL_ATTACK:
            return OFF_HAND_CRITICAL;
          case POWER:
            return MAIN_HAND_CRITICAL;
        }
      case HIT_COUNT:
        switch (slot) {

          case PHYSICAL_ATTACK:
            return OFF_HAND_HITS;
          case POWER:
            return MAIN_HAND_HITS;
        }
      case ATTACK_SPEED:
        switch (slot) {

          case PHYSICAL_ATTACK:
            return OFF_HAND_ATTACK_SPEED;
        }
        return MAIN_HAND_ATTACK_SPEED;
    }

    return this;
  }

  public boolean isMainOrSubHandStat() {
    switch (this) {

      case PHYSICAL_ATTACK:
      case POWER:
      case PHYSICAL_ACCURACY:
      case PHYSICAL_CRITICAL:
        return true;
    }

    return false;
  }

  public boolean isReplace() {
    return this.replace;
  }
}

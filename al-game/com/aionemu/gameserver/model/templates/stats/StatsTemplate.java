package com.aionemu.gameserver.model.templates.stats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stats_template")
public abstract class StatsTemplate {
  @XmlAttribute(name = "maxHp")
  private int maxHp;
  @XmlAttribute(name = "maxMp")
  private int maxMp;
  @XmlAttribute(name = "walk_speed")
  private float walkSpeed;
  @XmlAttribute(name = "run_speed")
  private float runSpeed;
  @XmlAttribute(name = "fly_speed")
  private float flySpeed;
  @XmlAttribute(name = "attack_speed")
  private float attackSpeed;
  @XmlAttribute(name = "evasion")
  private int evasion;
  @XmlAttribute(name = "block")
  private int block;
  @XmlAttribute(name = "parry")
  private int parry;
  @XmlAttribute(name = "main_hand_attack")
  private int mainHandAttack;
  @XmlAttribute(name = "main_hand_accuracy")
  private int mainHandAccuracy;
  @XmlAttribute(name = "main_hand_crit_rate")
  private int mainHandCritRate;
  @XmlAttribute(name = "magic_accuracy")
  private int magicAccuracy;

  public int getMaxHp() {
    return this.maxHp;
  }

  public int getMaxMp() {
    return this.maxMp;
  }

  public float getWalkSpeed() {
    return this.walkSpeed;
  }

  public float getRunSpeed() {
    return this.runSpeed;
  }

  public float getFlySpeed() {
    return this.flySpeed;
  }

  public float getAttackSpeed() {
    return this.attackSpeed;
  }

  public int getEvasion() {
    return this.evasion;
  }

  public int getBlock() {
    return this.block;
  }

  public int getParry() {
    return this.parry;
  }

  public int getMainHandAttack() {
    return this.mainHandAttack;
  }

  public int getMainHandAccuracy() {
    return this.mainHandAccuracy;
  }

  public int getMainHandCritRate() {
    return this.mainHandCritRate;
  }

  public int getMagicAccuracy() {
    return this.magicAccuracy;
  }
}

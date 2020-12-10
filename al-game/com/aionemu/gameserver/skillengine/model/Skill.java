package com.aionemu.gameserver.skillengine.model;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.controllers.movement.StartMovingListener;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CASTSPELL;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CASTSPELL_END;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.action.Action;
import com.aionemu.gameserver.skillengine.action.Actions;
import com.aionemu.gameserver.skillengine.condition.Condition;
import com.aionemu.gameserver.skillengine.condition.Conditions;
import com.aionemu.gameserver.skillengine.effect.EffectId;
import com.aionemu.gameserver.skillengine.properties.Properties;
import com.aionemu.gameserver.skillengine.properties.Property;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Skill {
  private List<Creature> effectedList;
  private Creature firstTarget;
  private Creature effector;
  private int skillLevel;
  private int skillStackLvl;
  private StartMovingListener conditionChangeListener;
  private SkillTemplate skillTemplate;
  private boolean firstTargetRangeCheck = true;
  private ItemTemplate itemTemplate;
  private int targetType;
  private boolean chainSuccess = true;
  private float x;
  private float y;
  private float z;
  private int changeMpConsumptionValue;
  private float firstTargetRange;
  private int duration;

  public enum SkillType {
    CAST, ITEM, PASSIVE;
  }

  public Skill(SkillTemplate skillTemplate, Player effector, Creature firstTarget) {
    this(skillTemplate, (Creature) effector, effector.getSkillList().getSkillLevel(skillTemplate.getSkillId()),
        firstTarget);
  }

  public Skill(SkillTemplate skillTemplate, Creature effector, int skillLvl, Creature firstTarget) {
    this.effectedList = new ArrayList<Creature>();
    this.conditionChangeListener = new StartMovingListener();
    this.firstTarget = firstTarget;
    this.skillLevel = skillLvl;
    this.skillStackLvl = skillTemplate.getLvl();
    this.skillTemplate = skillTemplate;
    this.effector = effector;
  }

  public boolean canUseSkill() {
    if (!setProperties(this.skillTemplate.getInitproperties())) {
      return false;
    }
    if (!preCastCheck()) {
      return false;
    }
    if (!setProperties(this.skillTemplate.getSetproperties())) {
      return false;
    }

    this.effector.setCasting(this);
    Iterator<Creature> effectedIter = this.effectedList.iterator();
    while (effectedIter.hasNext()) {

      Creature effected = effectedIter.next();
      if (effected == null) {
        effected = this.effector;
      }
      if (this.effector instanceof Player) {

        if (!RestrictionsManager.canAffectBySkill((Player) this.effector, (VisibleObject) effected)
            && this.skillTemplate.getSkillId() != 1968) {
          effectedIter.remove();
        }
        continue;
      }
      if (this.effector.getEffectController().isAbnormalState(EffectId.CANT_ATTACK_STATE)
          && this.skillTemplate.getSkillId() != 1968) {
        effectedIter.remove();
      }
    }
    this.effector.setCasting(null);

    if (this.targetType == 0 && this.effectedList.size() == 0) {
      return false;
    }
    return true;
  }

  public void useSkill() {
    if (!canUseSkill()) {
      return;
    }
    this.changeMpConsumptionValue = 0;

    this.effector.getObserveController().notifySkilluseObservers(this);

    this.effector.setCasting(this);

    checkSkillSetException();

    int skillDuration = this.skillTemplate.getDuration();
    int currentStat = this.effector.getGameStats().getCurrentStat(StatEnum.BOOST_CASTING_TIME);
    this.duration = skillDuration + Math.round((skillDuration * (100 - currentStat)) / 100.0F);

    int cooldown = this.skillTemplate.getCooldown();
    if (cooldown != 0) {
      this.effector.setSkillCoolDown(this.skillTemplate.getSkillId(),
          (cooldown * 100 + this.duration) + System.currentTimeMillis());
    }
    if (this.duration < 0) {
      this.duration = 0;
    }
    if (this.skillTemplate.isActive() || this.skillTemplate.isToggle()) {
      startCast();
    }

    this.effector.getObserveController().attach((ActionObserver) this.conditionChangeListener);

    if (this.duration > 0) {

      schedule(this.duration);
    } else {

      endCast();
    }
  }

  private void startPenaltySkill() {
    if (this.skillTemplate.getPenaltySkillId() == 0) {
      return;
    }
    Skill skill = SkillEngine.getInstance().getSkill(this.effector, this.skillTemplate.getPenaltySkillId(), 1,
        (VisibleObject) this.firstTarget);
    skill.useSkill();
  }

  private void startCast() {
    int targetObjId = (this.firstTarget != null) ? this.firstTarget.getObjectId() : 0;

    switch (this.targetType) {

      case 0:
        PacketSendUtility.broadcastPacketAndReceive((VisibleObject) this.effector,
            (AionServerPacket) new SM_CASTSPELL(this.effector.getObjectId(), this.skillTemplate.getSkillId(),
                this.skillLevel, this.targetType, targetObjId, this.duration));
        break;

      case 1:
        PacketSendUtility.broadcastPacketAndReceive((VisibleObject) this.effector,
            (AionServerPacket) new SM_CASTSPELL(this.effector.getObjectId(), this.skillTemplate.getSkillId(),
                this.skillLevel, this.targetType, this.x, this.y, this.z, this.duration));
        break;
    }
  }

  private void endCast() {
    if (!this.effector.isCasting()) {
      return;
    }

    if (!checkEndCast()) {
      if (this.effector instanceof Player) {

        Player player = (Player) this.effector;
        player.getController().cancelCurrentSkill();
        PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_ATTACK_TOO_FAR_FROM_TARGET());

        return;
      }
    }

    this.effector.setCasting(null);

    if (!preUsageCheck()) {
      return;
    }

    int spellStatus = 0;

    List<Effect> effects = new ArrayList<Effect>();
    if (this.skillTemplate.getEffects() != null) {
      for (Creature effected : this.effectedList) {

        Effect effect = new Effect(this.effector, effected, this.skillTemplate, this.skillLevel, 0, this.itemTemplate);
        effect.initialize();
        spellStatus = effect.getSpellStatus().getId();
        effects.add(effect);
      }
    }

    int chainProb = this.skillTemplate.getChainSkillProb();
    if (chainProb != 0) {
      if (Rnd.get(100) < chainProb) {
        this.chainSuccess = true;
      } else {
        this.chainSuccess = false;
      }
    }

    if (this.skillTemplate.isActive() || this.skillTemplate.isToggle()) {
      sendCastspellEnd(spellStatus, effects);
    }

    Actions skillActions = this.skillTemplate.getActions();
    if (skillActions != null) {
      for (Action action : skillActions.getActions()) {
        action.act(this);
      }
    }

    for (Effect effect : effects) {
      effect.applyEffect();
    }

    startPenaltySkill();
  }

  private void sendCastspellEnd(int spellStatus, List<Effect> effects) {
    switch (this.targetType) {

      case 0:
        PacketSendUtility.broadcastPacketAndReceive((VisibleObject) this.effector,
            (AionServerPacket) new SM_CASTSPELL_END(this.effector, this.firstTarget, effects,
                this.skillTemplate.getSkillId(), this.skillLevel, this.skillTemplate.getCooldown(), this.chainSuccess,
                spellStatus));
        break;

      case 1:
        PacketSendUtility.broadcastPacketAndReceive((VisibleObject) this.effector,
            (AionServerPacket) new SM_CASTSPELL_END(this.effector, this.firstTarget, effects,
                this.skillTemplate.getSkillId(), this.skillLevel, this.skillTemplate.getCooldown(), this.chainSuccess,
                spellStatus, this.x, this.y, this.z));
        break;
    }
  }

  private void schedule(int delay) {
    ThreadPoolManager.getInstance().schedule(new Runnable() {
      public void run() {
        Skill.this.endCast();
      }
    }, delay);
  }

  private boolean preCastCheck() {
    Conditions skillConditions = this.skillTemplate.getStartconditions();
    return checkConditions(skillConditions);
  }

  private boolean preUsageCheck() {
    Conditions skillConditions = this.skillTemplate.getUseconditions();
    return checkConditions(skillConditions);
  }

  private boolean checkConditions(Conditions conditions) {
    if (conditions != null) {
      for (Condition condition : conditions.getConditions()) {

        if (!condition.verify(this)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean setProperties(Properties properties) {
    if (properties != null) {
      for (Property property : properties.getProperties()) {

        if (!property.set(this)) {
          return false;
        }
      }
    }
    return true;
  }

  private void checkSkillSetException() {
    int setNumber = this.skillTemplate.getSkillSetException();
    if (this.effector instanceof Player) {

      Player player = (Player) this.effector;
      if (setNumber != 0) {
        player.getEffectController().removeEffectBySetNumber(setNumber);
      } else {
        player.getEffectController().removeEffectWithSetNumberReserved();
      }
    }
  }

  public void setChangeMpConsumption(int value) {
    this.changeMpConsumptionValue = value;
  }

  public int getChangeMpConsumption() {
    return this.changeMpConsumptionValue;
  }

  private boolean checkEndCast() {
    if (this.firstTargetRange == 0.0F) {
      return true;
    }
    if (this.effector == this.firstTarget)
      return true;
    if (!MathUtil.isIn3dRange((VisibleObject) this.effector, (VisibleObject) this.firstTarget,
        this.firstTargetRange + 4.0F))
      return false;
    return true;
  }

  public void setFirstTargetRange(float value) {
    this.firstTargetRange = value;
  }

  public List<Creature> getEffectedList() {
    return this.effectedList;
  }

  public Creature getEffector() {
    return this.effector;
  }

  public int getSkillLevel() {
    return this.skillLevel;
  }

  public int getSkillStackLvl() {
    return this.skillStackLvl;
  }

  public StartMovingListener getConditionChangeListener() {
    return this.conditionChangeListener;
  }

  public SkillTemplate getSkillTemplate() {
    return this.skillTemplate;
  }

  public Creature getFirstTarget() {
    return this.firstTarget;
  }

  public void setFirstTarget(Creature firstTarget) {
    this.firstTarget = firstTarget;
  }

  public boolean isPassive() {
    return (this.skillTemplate.getActivationAttribute() == ActivationAttribute.PASSIVE);
  }

  public boolean isFirstTargetRangeCheck() {
    return this.firstTargetRangeCheck;
  }

  public void setFirstTargetRangeCheck(boolean firstTargetRangeCheck) {
    this.firstTargetRangeCheck = firstTargetRangeCheck;
  }

  public void setItemTemplate(ItemTemplate itemTemplate) {
    this.itemTemplate = itemTemplate;
  }

  public void setTargetType(int targetType, float x, float y, float z) {
    this.targetType = targetType;
    this.x = x;
    this.y = y;
    this.z = z;
  }
}

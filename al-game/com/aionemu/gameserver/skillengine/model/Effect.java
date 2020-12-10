package com.aionemu.gameserver.skillengine.model;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_ACTIVATION;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.effect.Effects;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import javolution.util.FastList;

public class Effect {
  private SkillTemplate skillTemplate;
  private int skillLevel;
  private int duration;
  private int endTime;
  private Creature effected;
  private Creature effector;
  private Future<?> checkTask = null;
  private Future<?> task = null;
  private Future<?>[] periodicTasks = null;
  private Future<?> mpUseTask = null;

  private int reserved1;

  private int reserved2;

  private int reserved3;

  private SpellStatus spellStatus = SpellStatus.NONE;

  private AttackStatus attackStatus = AttackStatus.NORMALHIT;

  private int shieldDefense;

  private boolean addedToController;

  private AttackCalcObserver[] attackStatusObserver;
  private AttackCalcObserver[] attackShieldObserver;
  private boolean launchSubEffect = true;
  private Effect subEffect = null;

  private boolean isStopped;

  private ItemTemplate itemTemplate;

  private int tauntHate;

  private int effectHate;

  private Collection<EffectTemplate> sucessEffects = (new FastList()).shared();

  private ActionObserver[] actionObserver;

  public Effect(Creature effector, Creature effected, SkillTemplate skillTemplate, int skillLevel, int duration) {
    this.effector = effector;
    this.effected = effected;
    this.skillTemplate = skillTemplate;
    this.skillLevel = skillLevel;
    this.duration = duration;
  }

  public Effect(Creature effector, Creature effected, SkillTemplate skillTemplate, int skillLevel, int duration,
      ItemTemplate itemTemplate) {
    this(effector, effected, skillTemplate, skillLevel, duration);
    this.itemTemplate = itemTemplate;
  }

  public int getEffectorId() {
    return this.effector.getObjectId();
  }

  public int getSkillId() {
    return this.skillTemplate.getSkillId();
  }

  public int getSkillSetException() {
    return this.skillTemplate.getSkillSetException();
  }

  public String getStack() {
    return this.skillTemplate.getStack();
  }

  public int getSkillLevel() {
    return this.skillLevel;
  }

  public int getSkillStackLvl() {
    return this.skillTemplate.getLvl();
  }

  public SkillType getSkillType() {
    return this.skillTemplate.getType();
  }

  public int getDuration() {
    return this.duration;
  }

  public void setDuration(int newDuration) {
    this.duration = newDuration;
  }

  public Creature getEffected() {
    return this.effected;
  }

  public Creature getEffector() {
    return this.effector;
  }

  public boolean isPassive() {
    return this.skillTemplate.isPassive();
  }

  public void setTask(Future<?> task) {
    this.task = task;
  }

  public Future<?> getPeriodicTask(int i) {
    return this.periodicTasks[i - 1];
  }

  public void setPeriodicTask(Future<?> periodicTask, int i) {
    if (this.periodicTasks == null)
      this.periodicTasks = (Future<?>[]) new Future[4];
    this.periodicTasks[i - 1] = periodicTask;
  }

  public Future<?> getMpUseTask() {
    return this.mpUseTask;
  }

  public void setMpUseTask(Future<?> mpUseTask) {
    this.mpUseTask = mpUseTask;
  }

  public int getReserved1() {
    return this.reserved1;
  }

  public void setReserved1(int reserved1) {
    this.reserved1 = reserved1;
  }

  public int getReserved2() {
    return this.reserved2;
  }

  public void setReserved2(int reserved2) {
    this.reserved2 = reserved2;
  }

  public int getReserved3() {
    return this.reserved3;
  }

  public void setReserved3(int reserved3) {
    this.reserved3 = reserved3;
  }

  public AttackStatus getAttackStatus() {
    return this.attackStatus;
  }

  public void setAttackStatus(AttackStatus attackStatus) {
    this.attackStatus = attackStatus;
  }

  public List<EffectTemplate> getEffectTemplates() {
    return this.skillTemplate.getEffects().getEffects();
  }

  public boolean isFood() {
    Effects effects = this.skillTemplate.getEffects();
    return (effects != null && effects.isFood());
  }

  public boolean isToggle() {
    return (this.skillTemplate.getActivationAttribute() == ActivationAttribute.TOGGLE);
  }

  public int getTargetSlot() {
    return this.skillTemplate.getTargetSlot().ordinal();
  }

  public AttackCalcObserver getAttackStatusObserver(int i) {
    return this.attackStatusObserver[i - 1];
  }

  public void setAttackStatusObserver(AttackCalcObserver attackStatusObserver, int i) {
    if (this.attackStatusObserver == null)
      this.attackStatusObserver = new AttackCalcObserver[4];
    this.attackStatusObserver[i - 1] = attackStatusObserver;
  }

  public AttackCalcObserver getAttackShieldObserver(int i) {
    return this.attackShieldObserver[i - 1];
  }

  public void setAttackShieldObserver(AttackCalcObserver attackShieldObserver, int i) {
    if (this.attackShieldObserver == null)
      this.attackShieldObserver = new AttackCalcObserver[4];
    this.attackShieldObserver[i - 1] = attackShieldObserver;
  }

  public boolean isLaunchSubEffect() {
    return this.launchSubEffect;
  }

  public void setLaunchSubEffect(boolean launchSubEffect) {
    this.launchSubEffect = launchSubEffect;
  }

  public int getShieldDefense() {
    return this.shieldDefense;
  }

  public void setShieldDefense(int shieldDefense) {
    this.shieldDefense = shieldDefense;
  }

  public SpellStatus getSpellStatus() {
    return this.spellStatus;
  }

  public void setSpellStatus(SpellStatus spellStatus) {
    this.spellStatus = spellStatus;
  }

  public Effect getSubEffect() {
    return this.subEffect;
  }

  public void setSubEffect(Effect subEffect) {
    this.subEffect = subEffect;
  }

  public boolean containsEffectId(int effectId) {
    for (EffectTemplate template : this.sucessEffects) {

      if (template.getEffectid() == effectId)
        return true;
    }
    return false;
  }

  public void initialize() {
    if (this.skillTemplate.getEffects() == null) {
      return;
    }
    boolean isDmgEffect = false;

    for (EffectTemplate template : getEffectTemplates()) {

      template.calculate(this);
      if (template instanceof com.aionemu.gameserver.skillengine.effect.DamageEffect
          && !(template instanceof com.aionemu.gameserver.skillengine.effect.DamageOverTimeEffect)) {
        isDmgEffect = true;
      }
    }

    for (EffectTemplate template : this.sucessEffects) {

      template.calculateSubEffect(this);
      template.calculateHate(this);
    }

    if (isDmgEffect) {

      if (getAttackStatus() == AttackStatus.RESIST || getAttackStatus() == AttackStatus.DODGE) {

        this.sucessEffects.clear();

        return;
      }

      if (this.sucessEffects.size() != getEffectTemplates().size()) {
        for (EffectTemplate template : this.sucessEffects) {
          if (template instanceof com.aionemu.gameserver.skillengine.effect.DamageEffect
              && !(template instanceof com.aionemu.gameserver.skillengine.effect.DamageOverTimeEffect))
            continue;
          this.sucessEffects.remove(template);

        }

      }
    } else if (this.sucessEffects.size() != getEffectTemplates().size()) {

      this.sucessEffects.clear();
      if (getSkillType() == SkillType.MAGICAL) {
        setAttackStatus(AttackStatus.RESIST);
      } else {
        setAttackStatus(AttackStatus.DODGE);
      }
    }
  }

  public void applyEffect() {
    if (this.skillTemplate.getEffects() == null || this.sucessEffects.isEmpty()) {
      return;
    }
    for (EffectTemplate template : this.sucessEffects) {

      template.applyEffect(this);
      template.startSubEffect(this);
    }

    if (this.effectHate != 0) {
      this.effector.getController().broadcastHate(this.effectHate);
    }
  }

  public void startEffect(boolean restored) {
    if (this.sucessEffects.isEmpty()) {
      return;
    }
    for (EffectTemplate template : this.sucessEffects) {
      template.startEffect(this);
    }

    if (isToggle() && this.effector instanceof Player) {
      activateToggleSkill();
    }

    if (!restored)
      this.duration = getEffectsDuration();
    if (this.duration == 0) {
      return;
    }
    this.endTime = (int) System.currentTimeMillis() + this.duration;

    this.task = ThreadPoolManager.getInstance().scheduleEffect(new Runnable() {

      public void run() {
        Effect.this.endEffect();
      }
    }, this.duration);
  }

  private void activateToggleSkill() {
    PacketSendUtility.sendPacket((Player) this.effector,
        (AionServerPacket) new SM_SKILL_ACTIVATION(getSkillId(), true));
  }

  private void deactivateToggleSkill() {
    PacketSendUtility.sendPacket((Player) this.effector,
        (AionServerPacket) new SM_SKILL_ACTIVATION(getSkillId(), false));
  }

  public synchronized void endEffect() {
    if (this.isStopped) {
      return;
    }
    for (EffectTemplate template : this.sucessEffects) {
      template.endEffect(this);
    }

    if (isToggle() && this.effector instanceof Player) {
      deactivateToggleSkill();
    }
    stopTasks();
    this.effected.getEffectController().clearEffect(this);
    this.isStopped = true;
  }

  public void stopTasks() {
    if (this.task != null) {

      this.task.cancel(true);
      this.task = null;
    }

    if (this.checkTask != null) {

      this.checkTask.cancel(true);
      this.checkTask = null;
    }

    if (this.periodicTasks != null) {
      for (Future<?> periodicTask : this.periodicTasks) {

        if (periodicTask != null) {

          periodicTask.cancel(true);
          periodicTask = null;
        }
      }
    }

    if (this.mpUseTask != null) {

      this.mpUseTask.cancel(true);
      this.mpUseTask = null;
    }
  }

  public int getElapsedTime() {
    int elapsedTime = this.endTime - (int) System.currentTimeMillis();
    return (elapsedTime > 0) ? elapsedTime : 0;
  }

  public int getCurrentTime() {
    return this.duration - getElapsedTime();
  }

  public int getPvpDamage() {
    return this.skillTemplate.getPvpDamage();
  }

  public ItemTemplate getItemTemplate() {
    return this.itemTemplate;
  }

  public void addToEffectedController() {
    if (!this.addedToController) {

      this.effected.getEffectController().addEffect(this);
      this.addedToController = true;
    }
  }

  public int getEffectHate() {
    return this.effectHate;
  }

  public void setEffectHate(int effectHate) {
    this.effectHate = effectHate;
  }

  public int getTauntHate() {
    return this.tauntHate;
  }

  public void setTauntHate(int tauntHate) {
    this.tauntHate = tauntHate;
  }

  public ActionObserver getActionObserver(int i) {
    return this.actionObserver[i - 1];
  }

  public void setActionObserver(ActionObserver observer, int i) {
    if (this.actionObserver == null)
      this.actionObserver = new ActionObserver[4];
    this.actionObserver[i - 1] = observer;
  }

  public void addSucessEffect(EffectTemplate effect) {
    this.sucessEffects.add(effect);
  }

  public Collection<EffectTemplate> getSuccessEffect() {
    return this.sucessEffects;
  }

  public void addAllEffectToSucess() {
    for (EffectTemplate template : getEffectTemplates()) {
      this.sucessEffects.add(template);
    }
  }

  public int getEffectsDuration() {
    int duration = 0;
    for (EffectTemplate template : this.sucessEffects) {

      int effectDuration = template.getDuration();
      if (template.getRandomTime() > 0)
        effectDuration -= Rnd.get(template.getRandomTime());
      duration = (duration > effectDuration) ? duration : effectDuration;
    }
    if (this.effected instanceof Player && this.skillTemplate.getPvpDuration() != 0)
      duration = duration * this.skillTemplate.getPvpDuration() / 100;
    return duration;
  }
}

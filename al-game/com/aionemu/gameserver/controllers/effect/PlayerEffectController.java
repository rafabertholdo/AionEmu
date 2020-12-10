package com.aionemu.gameserver.controllers.effect;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.GroupEvent;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABNORMAL_STATE;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class PlayerEffectController extends EffectController {
  private int weaponEffects;
  private int armorEffects;
  private Effect foodEffect;

  public PlayerEffectController(Creature owner) {
    super(owner);
  }

  public void addEffect(Effect effect) {
    if (effect.isFood()) {
      addFoodEffect(effect);
    }
    if (checkDuelCondition(effect)) {
      return;
    }
    super.addEffect(effect);
    updatePlayerIconsAndGroup(effect);
  }

  public void clearEffect(Effect effect) {
    if (effect.isFood()) {
      this.foodEffect = null;
    }
    super.clearEffect(effect);
    updatePlayerIconsAndGroup(effect);
  }

  public Player getOwner() {
    return (Player) super.getOwner();
  }

  private void updatePlayerIconsAndGroup(Effect effect) {
    if (!effect.isPassive()) {

      updatePlayerEffectIcons();
      if (getOwner().isInGroup())
        getOwner().getPlayerGroup().updateGroupUIToEvent(getOwner(), GroupEvent.UPDATE);
      if (getOwner().isInAlliance()) {
        AllianceService.getInstance().updateAllianceUIToEvent(getOwner(), PlayerAllianceEvent.UPDATE);
      }
    }
  }

  private boolean checkDuelCondition(Effect effect) {
    Creature creature = effect.getEffector();
    if (creature instanceof Player) {
      if (getOwner().isFriend((Player) creature) && effect.getTargetSlot() == SkillTargetSlot.DEBUFF.ordinal()) {
        return true;
      }
    }
    return false;
  }

  private void addFoodEffect(Effect effect) {
    if (this.foodEffect != null)
      this.foodEffect.endEffect();
    this.foodEffect = effect;
  }

  public void setWeaponMastery(int skillId) {
    this.weaponEffects = skillId;
  }

  public void unsetWeaponMastery() {
    this.weaponEffects = 0;
  }

  public int getWeaponMastery() {
    return this.weaponEffects;
  }

  public boolean isWeaponMasterySet(int skillId) {
    return (this.weaponEffects == skillId);
  }

  public void setArmorMastery(int skillId) {
    this.armorEffects = skillId;
  }

  public void unsetArmorMastery() {
    this.armorEffects = 0;
  }

  public int getArmorMastery() {
    return this.armorEffects;
  }

  public boolean isArmorMasterySet(int skillId) {
    return (this.armorEffects == skillId);
  }

  public void addSavedEffect(int skillId, int skillLvl, int currentTime) {
    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
    int duration = template.getEffectsDuration();
    int remainingTime = duration - currentTime;

    if (remainingTime <= 0) {
      return;
    }
    Effect effect = new Effect((Creature) getOwner(), (Creature) getOwner(), template, skillLvl, remainingTime);
    if (effect.isFood())
      addFoodEffect(effect);
    this.abnormalEffectMap.put(effect.getStack(), effect);
    effect.addAllEffectToSucess();
    effect.startEffect(true);

    PacketSendUtility.sendPacket(getOwner(),
        (AionServerPacket) new SM_ABNORMAL_STATE(Collections.singletonList(effect), this.abnormals));
  }
}

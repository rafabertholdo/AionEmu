package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.id.SkillEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeaponStatupEffect")
public class WeaponStatupEffect extends BufEffect {
  @XmlAttribute(name = "weapon")
  private WeaponType weaponType;

  public void startEffect(Effect effect) {
    if (!(effect.getEffector() instanceof Player)) {
      return;
    }

    final Player effected = (Player) effect.getEffected();

    final SkillEffectId skillEffectId = getSkillEffectId(effect);
    final TreeSet<StatModifier> stats = getModifiers(effect);

    if (effected.getEquipment().isWeaponEquipped(this.weaponType)) {
      effected.getGameStats().addModifiers((StatEffectId) skillEffectId, stats);
    }

    ActionObserver aObserver = new ActionObserver(ActionObserver.ObserverType.EQUIP) {

      public void equip(Item item, Player owner) {
        if (item.getItemTemplate().getWeaponType() == WeaponStatupEffect.this.weaponType) {
          effected.getGameStats().addModifiers((StatEffectId) skillEffectId, stats);
        }
      }

      public void unequip(Item item, Player owner) {
        if (item.getItemTemplate().getWeaponType() == WeaponStatupEffect.this.weaponType) {
          effected.getGameStats().endEffect((StatEffectId) skillEffectId);
        }
      }
    };

    effected.getObserveController().addEquipObserver(aObserver);
    effect.setActionObserver(aObserver, this.position);
  }

  public void endEffect(Effect effect) {
    ActionObserver observer = effect.getActionObserver(this.position);
    if (observer != null) {
      effect.getEffected().getObserveController().removeEquipObserver(observer);
    }
    SkillEffectId skillEffectId = getSkillEffectId(effect);

    if (effect.getEffected().getGameStats().effectAlreadyAdded((StatEffectId) skillEffectId)) {
      effect.getEffected().getGameStats().endEffect((StatEffectId) skillEffectId);
    }
  }

  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }
}

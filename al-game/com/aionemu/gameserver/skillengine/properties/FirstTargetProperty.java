package com.aionemu.gameserver.skillengine.properties;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Monster;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirstTargetProperty")
public class FirstTargetProperty extends Property {
  @XmlAttribute(required = true)
  protected FirstTargetAttribute value;

  public FirstTargetAttribute getValue() {
    return this.value;
  }

  public boolean set(Skill skill) {
    Creature effector;
    switch (this.value) {

      case ME:
        skill.setFirstTarget(skill.getEffector());
        break;
      case TARGETORME:
        if (skill.getFirstTarget() == null) {
          skill.setFirstTarget(skill.getEffector());
          break;
        }
        if (skill.getFirstTarget() instanceof Monster) {

          Monster monsterEffected = (Monster) skill.getFirstTarget();
          Player playerEffector = (Player) skill.getEffector();
          if (monsterEffected.isEnemy((VisibleObject) playerEffector))
            skill.setFirstTarget(skill.getEffector());
          break;
        }
        if (skill.getFirstTarget() instanceof Player && skill.getEffector() instanceof Player) {

          Player playerEffected = (Player) skill.getFirstTarget();
          Player playerEffector = (Player) skill.getEffector();
          if (playerEffected.getCommonData().getRace().getRaceId() != playerEffector.getCommonData().getRace()
              .getRaceId())
            skill.setFirstTarget(skill.getEffector());
          break;
        }
        if (skill.getFirstTarget() instanceof Npc) {

          Npc npcEffected = (Npc) skill.getFirstTarget();
          Player playerEffector = (Player) skill.getEffector();
          if (npcEffected.isEnemy((VisibleObject) playerEffector))
            skill.setFirstTarget(skill.getEffector());
          break;
        }
        if (skill.getFirstTarget() instanceof Summon && skill.getEffector() instanceof Player) {

          Summon summon = (Summon) skill.getFirstTarget();
          Player playerEffected = summon.getMaster();
          Player playerEffector = (Player) skill.getEffector();
          if (playerEffected.getCommonData().getRace().getRaceId() != playerEffector.getCommonData().getRace()
              .getRaceId())
            skill.setFirstTarget(skill.getEffector());
        }
        break;
      case TARGET:
        if (skill.getFirstTarget() == null)
          return false;
        break;
      case MYPET:
        effector = skill.getEffector();
        if (effector instanceof Player) {

          Summon summon = ((Player) effector).getSummon();
          if (summon != null) {
            skill.setFirstTarget((Creature) summon);
            break;
          }
          return false;
        }

        return false;

      case PASSIVE:
        skill.setFirstTarget(skill.getEffector());
        break;

      case POINT:
        skill.setFirstTargetRangeCheck(false);
        return true;
    }

    if (skill.getFirstTarget() != null)
      skill.getEffectedList().add(skill.getFirstTarget());
    return true;
  }
}

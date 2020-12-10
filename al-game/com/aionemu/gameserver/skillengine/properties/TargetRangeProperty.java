package com.aionemu.gameserver.skillengine.properties;

import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Trap;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.MathUtil;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.apache.log4j.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetRangeProperty")
public class TargetRangeProperty extends Property {
  private static final Logger log = Logger.getLogger(TargetRangeProperty.class);

  @XmlAttribute(required = true)
  protected TargetRangeAttribute value;

  @XmlAttribute
  protected int distance;

  @XmlAttribute
  protected int maxcount;

  public TargetRangeAttribute getValue() {
    return this.value;
  }

  public boolean set(Skill skill) {
    Creature firstTarget;
    List<Creature> effectedList = skill.getEffectedList();
    int counter = 0;
    switch (this.value) {

      case AREA:
        firstTarget = skill.getFirstTarget();
        if (firstTarget == null) {

          log.warn("CHECKPOINT: first target is null for skillid " + skill.getSkillTemplate().getSkillId());
          return false;
        }

        for (VisibleObject nextCreature : firstTarget.getKnownList().getKnownObjects().values()) {

          if (counter >= this.maxcount) {
            break;
          }

          if (firstTarget == nextCreature) {
            continue;
          }

          if (skill.getEffector() instanceof Trap && ((Trap) skill.getEffector()).getCreator() == nextCreature) {
            continue;
          }

          if (nextCreature instanceof Creature
              && MathUtil.isIn3dRange((VisibleObject) firstTarget, nextCreature, (this.distance + 4))) {

            effectedList.add((Creature) nextCreature);
            counter++;
          }
        }
        break;
      case PARTY:
        if (skill.getEffector() instanceof Player) {

          Player effector = (Player) skill.getEffector();
          if (effector.isInAlliance()) {

            effectedList.clear();
            for (PlayerAllianceMember allianceMember : effector.getPlayerAlliance()
                .getMembersForGroup(effector.getObjectId())) {

              if (!allianceMember.isOnline())
                continue;
              Player member = allianceMember.getPlayer();
              if (MathUtil.isIn3dRange((VisibleObject) effector, (VisibleObject) member, (this.distance + 4)))
                effectedList.add(member);
            }
            break;
          }
          if (effector.isInGroup()) {

            effectedList.clear();
            for (Player member : effector.getPlayerGroup().getMembers()) {

              if (member != null
                  && MathUtil.isIn3dRange((VisibleObject) effector, (VisibleObject) member, (this.distance + 4))) {
                effectedList.add(member);
              }
            }
          }
        }
        break;
    }

    return true;
  }
}

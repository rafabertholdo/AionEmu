package com.aionemu.gameserver.skillengine.properties;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.apache.log4j.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirstTargetRangeProperty")
public class FirstTargetRangeProperty extends Property {
  private static final Logger log = Logger.getLogger(FirstTargetRangeProperty.class);

  @XmlAttribute(required = true)
  protected int value;

  public boolean set(Skill skill) {
    if (!skill.isFirstTargetRangeCheck()) {
      return true;
    }
    Creature effector = skill.getEffector();
    Creature firstTarget = skill.getFirstTarget();
    if (firstTarget == null) {
      return false;
    }
    if (firstTarget.getPosition().getMapId() == 0) {
      log.warn("FirstTarget has mapId of 0. (" + firstTarget.getName() + ")");
    }
    skill.setFirstTargetRange(this.value);

    if (MathUtil.isIn3dRange((VisibleObject) effector, (VisibleObject) firstTarget, (this.value + 4))) {
      return true;
    }

    if (effector instanceof Player) {
      PacketSendUtility.sendPacket((Player) effector,
          (AionServerPacket) SM_SYSTEM_MESSAGE.STR_ATTACK_TOO_FAR_FROM_TARGET());
    }
    return false;
  }
}

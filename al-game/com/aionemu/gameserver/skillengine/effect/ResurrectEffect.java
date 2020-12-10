package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RESURRECT;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResurrectEffect")
public class ResurrectEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    PacketSendUtility.sendPacket((Player) effect.getEffected(),
        (AionServerPacket) new SM_RESURRECT(effect.getEffector(), effect.getSkillId()));
  }

  public void calculate(Effect effect) {
    if (effect.getEffected() instanceof Player && effect.getEffected().getLifeStats().isAlreadyDead())
      effect.addSucessEffect(this);
  }
}

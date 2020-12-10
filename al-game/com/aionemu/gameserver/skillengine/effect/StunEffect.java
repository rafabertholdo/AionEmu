package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_IMMOBILIZE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StunEffect")
public class StunEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.STUN_RESISTANCE)) {
      effect.addSucessEffect(this);
    }
  }

  public void startEffect(Effect effect) {
    Creature effected = effect.getEffected();
    effected.getController().cancelCurrentSkill();
    effect.getEffected().getEffectController().setAbnormal(EffectId.STUN.getEffectId());
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject) effect.getEffected(),
        (AionServerPacket) new SM_TARGET_IMMOBILIZE(effect.getEffected()));
  }

  public void endEffect(Effect effect) {
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.STUN.getEffectId());
  }
}

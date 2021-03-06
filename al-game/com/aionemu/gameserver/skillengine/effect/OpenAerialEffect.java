package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SpellStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenAerialEffect")
public class OpenAerialEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.OPENAREIAL_RESISTANCE)) {

      effect.addSucessEffect(this);
      effect.setSpellStatus(SpellStatus.OPENAERIAL);
    }
  }

  public void startEffect(Effect effect) {
    Creature effected = effect.getEffected();
    effected.getController().cancelCurrentSkill();
    effected.getEffectController().setAbnormal(EffectId.OPENAERIAL.getEffectId());
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject) effected,
        (AionServerPacket) new SM_FORCED_MOVE(effect.getEffector(), effected));
  }

  public void endEffect(Effect effect) {
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.OPENAERIAL.getEffectId());
  }
}

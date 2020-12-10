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
@XmlType(name = "FearEffect")
public class FearEffect
  extends EffectTemplate
{
  public void applyEffect(Effect effect) {
    effect.setDuration(effect.getDuration() / 2);
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.FEAR_RESISTANCE)) {
      effect.addSucessEffect(this);
    }
  }

  
  public void startEffect(Effect effect) {
    Creature obj = effect.getEffected();
    obj.getController().cancelCurrentSkill();
    obj.getEffectController().setAbnormal(EffectId.FEAR.getEffectId());
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject)obj, (AionServerPacket)new SM_TARGET_IMMOBILIZE(obj));
    obj.getController().stopMoving();
  }


  
  public void endEffect(Effect effect) {
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.FEAR.getEffectId());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\FearEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

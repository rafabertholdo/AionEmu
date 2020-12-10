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
public class ResurrectEffect
  extends EffectTemplate
{
  public void applyEffect(Effect effect) {
    PacketSendUtility.sendPacket((Player)effect.getEffected(), (AionServerPacket)new SM_RESURRECT(effect.getEffector(), effect.getSkillId()));
  }


  
  public void calculate(Effect effect) {
    if (effect.getEffected() instanceof Player && effect.getEffected().getLifeStats().isAlreadyDead())
      effect.addSucessEffect(this); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ResurrectEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

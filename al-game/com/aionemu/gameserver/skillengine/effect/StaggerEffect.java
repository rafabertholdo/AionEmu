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
@XmlType(name = "StaggerEffect")
public class StaggerEffect
  extends EffectTemplate
{
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.STAGGER_RESISTANCE)) {
      
      effect.addSucessEffect(this);
      effect.setSpellStatus(SpellStatus.STAGGER);
    } 
  }


  
  public void startEffect(Effect effect) {
    Creature effected = effect.getEffected();
    effected.getController().cancelCurrentSkill();
    effect.getEffected().getEffectController().setAbnormal(EffectId.STAGGER.getEffectId());
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effect.getEffected(), (AionServerPacket)new SM_FORCED_MOVE(effect.getEffector(), effect.getEffected()));
  }


  
  public void endEffect(Effect effect) {
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.STAGGER.getEffectId());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\StaggerEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

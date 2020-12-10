package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SpellStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;





















@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PulledEffect")
public class PulledEffect
  extends EffectTemplate
{
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    if (effect.getEffector() instanceof com.aionemu.gameserver.model.gameobjects.player.Player && effect.getEffected() != null)
    {
      effect.addSucessEffect(this);
    }
    effect.setSpellStatus(SpellStatus.NONE);
  }


  
  public void startEffect(Effect effect) {
    final Creature effector = effect.getEffector();
    final Creature effected = effect.getEffected();
    effected.getEffectController().setAbnormal(EffectId.CANNOT_MOVE.getEffectId());
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            World.getInstance().updatePosition((VisibleObject)effected, effector.getX(), effector.getY(), effector.getZ() + 0.25F, effected.getHeading());




            
            effector.getMoveController().setDirectionChanged(true);
            PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_FORCED_MOVE(effector, effected));
          }
        }1000L);
  }


  
  public void endEffect(Effect effect) {
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.CANNOT_MOVE.getEffectId());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\PulledEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

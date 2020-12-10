package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HideEffect")
public class HideEffect
  extends BufEffect
{
  @XmlAttribute
  protected int value;
  
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }



  
  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }

  
  public void endEffect(Effect effect) {
    CreatureVisualState visualState;
    super.endEffect(effect);
    
    Creature effected = effect.getEffected();
    effected.getEffectController().unsetAbnormal(EffectId.INVISIBLE_RELATED.getEffectId());


    
    switch (this.value) {
      
      case 1:
        visualState = CreatureVisualState.HIDE1;
        break;
      case 2:
        visualState = CreatureVisualState.HIDE2;
        break;
      case 3:
        visualState = CreatureVisualState.HIDE3;
        break;
      case 10:
        visualState = CreatureVisualState.HIDE10;
        break;
      case 13:
        visualState = CreatureVisualState.HIDE13;
        break;
      case 20:
        visualState = CreatureVisualState.HIDE20;
        break;
      default:
        visualState = CreatureVisualState.VISIBLE;
        break;
    } 
    effected.unsetVisualState(visualState);
    
    if (effected instanceof Player)
    {
      PacketSendUtility.broadcastPacket((Player)effected, (AionServerPacket)new SM_PLAYER_STATE((Player)effected), true);
    }
  }

  
  public void startEffect(final Effect effect) {
    CreatureVisualState visualState;
    super.startEffect(effect);
    
    final Creature effected = effect.getEffected();
    effected.getEffectController().setAbnormal(EffectId.INVISIBLE_RELATED.getEffectId());


    
    switch (this.value) {
      
      case 1:
        visualState = CreatureVisualState.HIDE1;
        break;
      case 2:
        visualState = CreatureVisualState.HIDE2;
        break;
      case 3:
        visualState = CreatureVisualState.HIDE3;
        break;
      case 10:
        visualState = CreatureVisualState.HIDE10;
        break;
      case 13:
        visualState = CreatureVisualState.HIDE13;
        break;
      case 20:
        visualState = CreatureVisualState.HIDE20;
        break;
      default:
        visualState = CreatureVisualState.VISIBLE;
        break;
    } 
    effected.setVisualState(visualState);
    
    if (effected instanceof Player)
    {
      PacketSendUtility.broadcastPacket((Player)effected, (AionServerPacket)new SM_PLAYER_STATE((Player)effected), true);
    }

    
    effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.SKILLUSE)
        {

          
          public void skilluse(Skill skill)
          {
            effected.getEffectController().removeEffect(effect.getSkillId());
          }
        });


    
    effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.ATTACKED)
        {

          
          public void attacked(Creature creature)
          {
            effected.getEffectController().removeEffect(effect.getSkillId());
          }
        });


    
    effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.ATTACK)
        {

          
          public void attack(Creature creature)
          {
            effected.getEffectController().removeEffect(effect.getSkillId());
          }
        });
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\HideEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

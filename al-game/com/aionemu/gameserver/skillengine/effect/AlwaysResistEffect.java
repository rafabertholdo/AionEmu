package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
import com.aionemu.gameserver.controllers.movement.AttackStatusObserver;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAttribute;























public class AlwaysResistEffect
  extends EffectTemplate
{
  @XmlAttribute
  protected int value;
  
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }


  
  public void startEffect(Effect effect) {
    AttackStatusObserver attackStatusObserver = new AttackStatusObserver(this.value, AttackStatus.RESIST)
      {

        
        public boolean checkStatus(AttackStatus status)
        {
          if (status == AttackStatus.RESIST && this.value > 0) {
            
            this.value--;
            return true;
          } 
          return false;
        }
      };
    
    effect.getEffected().getObserveController().addAttackCalcObserver((AttackCalcObserver)attackStatusObserver);
    effect.setAttackStatusObserver((AttackCalcObserver)attackStatusObserver, this.position);
  }


  
  public void endEffect(Effect effect) {
    AttackCalcObserver acObserver = effect.getAttackStatusObserver(this.position);
    if (acObserver != null)
      effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\AlwaysResistEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

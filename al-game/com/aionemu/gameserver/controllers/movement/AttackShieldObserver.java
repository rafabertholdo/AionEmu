package com.aionemu.gameserver.controllers.movement;

import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.util.List;





























public class AttackShieldObserver
  extends AttackCalcObserver
{
  private int hit;
  private int totalHit;
  private Effect effect;
  private boolean percent;
  
  public AttackShieldObserver(int hit, int totalHit, boolean percent, Effect effect) {
    this.hit = hit;
    this.totalHit = totalHit;
    this.effect = effect;
    this.percent = percent;
  }


  
  public void checkShield(List<AttackResult> attackList) {
    for (AttackResult attackResult : attackList) {
      
      int damage = attackResult.getDamage();
      
      int absorbedDamage = 0;
      if (this.percent) {
        absorbedDamage = damage * this.hit / 100;
      } else {
        absorbedDamage = (damage >= this.hit) ? this.hit : damage;
      } 
      absorbedDamage = (absorbedDamage >= this.totalHit) ? this.totalHit : absorbedDamage;
      this.totalHit -= absorbedDamage;
      
      if (absorbedDamage > 0)
        attackResult.setShieldType(2); 
      attackResult.setDamage(damage - absorbedDamage);
      
      if (this.totalHit <= 0) {
        
        this.effect.endEffect();
        return;
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\AttackShieldObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

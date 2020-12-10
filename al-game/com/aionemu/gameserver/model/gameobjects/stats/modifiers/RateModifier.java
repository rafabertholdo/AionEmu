package com.aionemu.gameserver.model.gameobjects.stats.modifiers;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;























public class RateModifier
  extends SimpleModifier
{
  public int apply(int baseStat, int currentStat) {
    int minLimit = 0;
    int maxLimit = 0;

    
    switch (getStat()) {
      
      case ATTACK_SPEED:
      case SPEED:
        minLimit = 600;
        maxLimit = 12000;
        break;
      case FLY_SPEED:
        minLimit = 600;
        maxLimit = 16000;
        break;
    } 
    
    if (isBonus()) {
      
      int i = Math.round((this.value * baseStat) / 100.0F);
      if (minLimit == 0 && maxLimit == 0) {

        
        if (i + currentStat < 0) {
          return -currentStat;
        }
        return i;
      } 

      
      if (i + currentStat < minLimit) {
        
        i = currentStat - minLimit;
        return -i;
      } 
      if (i + currentStat > maxLimit) {
        
        i = maxLimit - currentStat;
        return i;
      } 
      
      return i;
    } 


    
    int chkValue = Math.round(baseStat * (1.0F + this.value / 100.0F));
    if (minLimit == 0 && maxLimit == 0) {
      
      if (chkValue < 0) {
        return 0;
      }
      return chkValue;
    } 

    
    if (chkValue + currentStat < minLimit) {
      
      chkValue = currentStat - minLimit;
      return -chkValue;
    } 
    if (chkValue + currentStat > maxLimit) {
      
      chkValue = maxLimit - currentStat;
      return chkValue;
    } 
    
    return chkValue;
  }




  
  public StatModifierPriority getPriority() {
    return StatModifierPriority.LOW;
  }

  
  public static RateModifier newInstance(StatEnum stat, int value, boolean isBonus) {
    RateModifier m = new RateModifier();
    m.setStat(stat);
    m.setValue(value);
    m.setBonus(isBonus);
    m.nextId();
    return m;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\RateModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

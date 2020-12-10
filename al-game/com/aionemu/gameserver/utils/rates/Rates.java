package com.aionemu.gameserver.utils.rates;

























public abstract class Rates
{
  public abstract int getGroupXpRate();
  
  public abstract int getXpRate();
  
  public abstract float getApNpcRate();
  
  public abstract float getApPlayerRate();
  
  public abstract float getGatheringXPRate();
  
  public abstract float getCraftingXPRate();
  
  public abstract int getDropRate();
  
  public abstract int getQuestXpRate();
  
  public abstract int getQuestKinahRate();
  
  public static Rates getRatesFor(byte membership) {
    switch (membership) {
      
      case 0:
        return new RegularRates();
      case 1:
        return new PremiumRates();
    } 
    return new RegularRates();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\rates\Rates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

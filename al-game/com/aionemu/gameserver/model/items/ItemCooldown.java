package com.aionemu.gameserver.model.items;

































public class ItemCooldown
{
  private long time;
  private int useDelay;
  
  public ItemCooldown(long time, int useDelay) {
    this.time = time;
    this.useDelay = useDelay;
  }




  
  public long getReuseTime() {
    return this.time;
  }




  
  public int getUseDelay() {
    return this.useDelay;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\ItemCooldown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

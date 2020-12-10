package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;


























public class BrokerPlayerClassFilter
  extends BrokerFilter
{
  private PlayerClass playerClass;
  
  public BrokerPlayerClassFilter(PlayerClass playerClass) {
    this.playerClass = playerClass;
  }



  
  public boolean accept(ItemTemplate template) {
    return template.isClassSpecific(this.playerClass);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\filter\BrokerPlayerClassFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

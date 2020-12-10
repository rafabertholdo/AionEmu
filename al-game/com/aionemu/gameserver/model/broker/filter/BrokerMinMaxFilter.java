package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;


























public class BrokerMinMaxFilter
  extends BrokerFilter
{
  private int min;
  private int max;
  
  public BrokerMinMaxFilter(int min, int max) {
    this.min = min * 100000;
    this.max = max * 100000;
  }


  
  public boolean accept(ItemTemplate template) {
    return (template.getTemplateId() >= this.min && template.getTemplateId() < this.max);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\filter\BrokerMinMaxFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

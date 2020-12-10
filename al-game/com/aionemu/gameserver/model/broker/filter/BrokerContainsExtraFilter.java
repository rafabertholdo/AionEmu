package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import org.apache.commons.lang.ArrayUtils;


























public class BrokerContainsExtraFilter
  extends BrokerFilter
{
  private int[] masks;
  
  public BrokerContainsExtraFilter(int... masks) {
    this.masks = masks;
  }


  
  public boolean accept(ItemTemplate template) {
    return ArrayUtils.contains(this.masks, template.getTemplateId() / 10000);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\filter\BrokerContainsExtraFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

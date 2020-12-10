package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import org.apache.commons.lang.ArrayUtils;

public class BrokerContainsExtraFilter extends BrokerFilter {
  private int[] masks;

  public BrokerContainsExtraFilter(int... masks) {
    this.masks = masks;
  }

  public boolean accept(ItemTemplate template) {
    return ArrayUtils.contains(this.masks, template.getTemplateId() / 10000);
  }
}

package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import org.apache.commons.lang.ArrayUtils;

public class BrokerContainsFilter extends BrokerFilter {
  private int[] masks;

  public BrokerContainsFilter(int... masks) {
    this.masks = masks;
  }

  public boolean accept(ItemTemplate template) {
    return ArrayUtils.contains(this.masks, template.getTemplateId() / 100000);
  }
}

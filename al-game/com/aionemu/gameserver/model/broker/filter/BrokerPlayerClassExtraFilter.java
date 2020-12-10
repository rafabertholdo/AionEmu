package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

public class BrokerPlayerClassExtraFilter extends BrokerPlayerClassFilter {
  private int mask;

  public BrokerPlayerClassExtraFilter(int mask, PlayerClass playerClass) {
    super(playerClass);
    this.mask = mask;
  }

  public boolean accept(ItemTemplate template) {
    return (super.accept(template) && this.mask == template.getTemplateId() / 100000);
  }
}

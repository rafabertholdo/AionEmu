package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

public class BrokerPlayerClassFilter extends BrokerFilter {
  private PlayerClass playerClass;

  public BrokerPlayerClassFilter(PlayerClass playerClass) {
    this.playerClass = playerClass;
  }

  public boolean accept(ItemTemplate template) {
    return template.isClassSpecific(this.playerClass);
  }
}

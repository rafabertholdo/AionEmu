package com.aionemu.gameserver.model.broker.filter;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;

public abstract class BrokerFilter {
  public abstract boolean accept(ItemTemplate paramItemTemplate);
}

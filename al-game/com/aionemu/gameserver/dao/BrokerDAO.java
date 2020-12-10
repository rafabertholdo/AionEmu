package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import java.util.List;

public abstract class BrokerDAO implements IDFactoryAwareDAO {
  public abstract List<BrokerItem> loadBroker();

  public abstract boolean store(BrokerItem paramBrokerItem);

  public final String getClassName() {
    return BrokerDAO.class.getName();
  }
}

package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;

public interface IDFactoryAwareDAO extends DAO {
  int[] getUsedIDs();
}

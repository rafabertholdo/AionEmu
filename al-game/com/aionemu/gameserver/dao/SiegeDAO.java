package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import javolution.util.FastMap;

public abstract class SiegeDAO implements DAO {
  public final String getClassName() {
    return SiegeDAO.class.getName();
  }

  public abstract boolean loadSiegeLocations(FastMap<Integer, SiegeLocation> paramFastMap);

  public abstract boolean updateSiegeLocation(SiegeLocation paramSiegeLocation);
}

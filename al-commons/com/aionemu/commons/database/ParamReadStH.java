package com.aionemu.commons.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParamReadStH extends ReadStH {
  void setParams(PreparedStatement paramPreparedStatement) throws SQLException;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\ParamReadStH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

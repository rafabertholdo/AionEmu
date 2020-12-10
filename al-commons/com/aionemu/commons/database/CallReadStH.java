package com.aionemu.commons.database;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface CallReadStH extends ReadStH {
  void setParams(CallableStatement paramCallableStatement) throws SQLException;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\CallReadStH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

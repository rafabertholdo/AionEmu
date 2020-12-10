package com.aionemu.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ReadStH {
  void handleRead(ResultSet paramResultSet) throws SQLException;
}

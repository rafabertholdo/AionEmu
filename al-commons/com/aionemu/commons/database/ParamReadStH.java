package com.aionemu.commons.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParamReadStH extends ReadStH {
  void setParams(PreparedStatement paramPreparedStatement) throws SQLException;
}

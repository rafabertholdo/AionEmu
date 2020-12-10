package com.aionemu.commons.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IUStH {
  void handleInsertUpdate(PreparedStatement paramPreparedStatement) throws SQLException;
}

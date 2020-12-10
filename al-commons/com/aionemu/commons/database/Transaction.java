package com.aionemu.commons.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import org.apache.log4j.Logger;






























public class Transaction
{
  private static final Logger log = Logger.getLogger(Transaction.class);






  
  private Connection connection;







  
  Transaction(Connection con) throws SQLException {
    this.connection = con;
    this.connection.setAutoCommit(false);
  }









  
  public void insertUpdate(String sql) throws SQLException {
    insertUpdate(sql, null);
  }












  
  public void insertUpdate(String sql, IUStH iusth) throws SQLException {
    PreparedStatement statement = this.connection.prepareStatement(sql);
    if (iusth != null) {
      
      iusth.handleInsertUpdate(statement);
    }
    else {
      
      statement.executeUpdate();
    } 
  }










  
  public Savepoint setSavepoint(String name) throws SQLException {
    return this.connection.setSavepoint(name);
  }









  
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    this.connection.releaseSavepoint(savepoint);
  }







  
  public void commit() throws SQLException {
    commit(null);
  }











  
  public void commit(Savepoint rollBackToOnError) throws SQLException {
    try {
      this.connection.commit();
    }
    catch (SQLException e) {
      
      log.warn("Error while commiting transaction", e);

      
      try {
        if (rollBackToOnError != null)
        {
          this.connection.rollback(rollBackToOnError);
        }
        else
        {
          this.connection.rollback();
        }
      
      } catch (SQLException e1) {
        
        log.error("Can't rollback transaction", e1);
      } 
    } 
    
    this.connection.setAutoCommit(true);
    this.connection.close();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\Transaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

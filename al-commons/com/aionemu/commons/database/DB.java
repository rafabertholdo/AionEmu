package com.aionemu.commons.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;









































































































































public final class DB
{
  protected static final Logger log = Logger.getLogger(DB.class);
















  
  public static boolean select(String query, ReadStH reader) {
    return select(query, reader, null);
  }









  
  public static boolean select(String query, ReadStH reader, String errMsg) {
    Connection con = null;
    PreparedStatement stmt = null;


    
    try {
      con = DatabaseFactory.getConnection();
      stmt = con.prepareStatement(query);
      if (reader instanceof ParamReadStH)
        ((ParamReadStH)reader).setParams(stmt); 
      ResultSet rset = stmt.executeQuery();
      reader.handleRead(rset);
    }
    catch (Exception e) {
      
      if (errMsg == null) {
        log.warn("Error executing select query " + e, e);
      } else {
        log.warn(errMsg + " " + e, e);
      }  return false;
    } finally {

      
      try {
        
        if (con != null)
          con.close(); 
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        
        log.warn("Failed to close DB connection " + e, e);
      } 
    } 
    return true;
  }








  
  public static boolean call(String query, ReadStH reader) {
    return call(query, reader, null);
  }









  
  public static boolean call(String query, ReadStH reader, String errMsg) {
    Connection con = null;
    CallableStatement stmt = null;


    
    try {
      con = DatabaseFactory.getConnection();
      stmt = con.prepareCall(query);
      if (reader instanceof CallReadStH)
        ((CallReadStH)reader).setParams(stmt); 
      ResultSet rset = stmt.executeQuery();
      reader.handleRead(rset);
    }
    catch (Exception e) {
      
      if (errMsg == null) {
        log.warn("Error calling stored procedure " + e, e);
      } else {
        log.warn(errMsg + " " + e, e);
      }  return false;
    } finally {

      
      try {
        
        if (con != null)
          con.close(); 
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        
        log.warn("Failed to close DB connection " + e, e);
      } 
    } 
    return true;
  }








  
  public static boolean insertUpdate(String query) {
    return insertUpdate(query, null, null);
  }









  
  public static boolean insertUpdate(String query, String errMsg) {
    return insertUpdate(query, null, errMsg);
  }









  
  public static boolean insertUpdate(String query, IUStH batch) {
    return insertUpdate(query, batch, null);
  }











  
  public static boolean insertUpdate(String query, IUStH batch, String errMsg) {
    Connection con = null;
    PreparedStatement stmt = null;

    
    try {
      con = DatabaseFactory.getConnection();
      stmt = con.prepareStatement(query);
      if (batch != null) {
        batch.handleInsertUpdate(stmt);
      } else {
        stmt.executeUpdate();
      }
    
    } catch (Exception e) {
      
      if (errMsg == null) {
        log.warn("Failed to execute IU query " + e, e);
      } else {
        log.warn(errMsg + " " + e, e);
      } 
      return false;
    } finally {

      
      try {
        
        if (con != null)
          con.close(); 
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        
        log.warn("Failed to close DB connection " + e, e);
      } 
    } 
    return true;
  }








  
  public static Transaction beginTransaction() throws SQLException {
    Connection con = DatabaseFactory.getConnection();
    return new Transaction(con);
  }










  
  public static PreparedStatement prepareStatement(String sql) {
    return prepareStatement(sql, 1003, 1007);
  }

















  
  public static PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) {
    Connection c = null;
    PreparedStatement ps = null;
    
    try {
      c = DatabaseFactory.getConnection();
      ps = c.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    catch (Exception e) {
      
      log.error("Can't create PreparedStatement for querry: " + sql, e);
      if (c != null) {
        
        try {
          
          c.close();
        }
        catch (SQLException e1) {
          
          log.error("Can't close connection after exception", e1);
        } 
      }
    } 
    
    return ps;
  }









  
  public static int executeUpdate(PreparedStatement statement) {
    try {
      return statement.executeUpdate();
    }
    catch (Exception e) {
      
      log.error("Can't execute update for PreparedStatement", e);

      
      return -1;
    } 
  }






  
  public static void executeUpdateAndClose(PreparedStatement statement) {
    executeUpdate(statement);
    close(statement);
  }








  
  public static ResultSet executeQuerry(PreparedStatement statement) {
    ResultSet rs = null;
    
    try {
      rs = statement.executeQuery();
    }
    catch (Exception e) {
      
      log.error("Error while executing querry", e);
    } 
    return rs;
  }









  
  public static void close(PreparedStatement statement) {
    try {
      if (statement.isClosed()) {

        
        log.warn("Attempt to close PreparedStatement that is closes already", new Exception());
        
        return;
      } 
      Connection c = statement.getConnection();
      statement.close();
      c.close();
    }
    catch (Exception e) {
      
      log.error("Error while closing PreparedStatement", e);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\DB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

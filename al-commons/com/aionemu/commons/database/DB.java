/*     */ package com.aionemu.commons.database;
/*     */ 
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DB
/*     */ {
/* 148 */   protected static final Logger log = Logger.getLogger(DB.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean select(String query, ReadStH reader) {
/* 167 */     return select(query, reader, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean select(String query, ReadStH reader, String errMsg) {
/* 180 */     Connection con = null;
/* 181 */     PreparedStatement stmt = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 186 */       con = DatabaseFactory.getConnection();
/* 187 */       stmt = con.prepareStatement(query);
/* 188 */       if (reader instanceof ParamReadStH)
/* 189 */         ((ParamReadStH)reader).setParams(stmt); 
/* 190 */       ResultSet rset = stmt.executeQuery();
/* 191 */       reader.handleRead(rset);
/*     */     }
/* 193 */     catch (Exception e) {
/*     */       
/* 195 */       if (errMsg == null) {
/* 196 */         log.warn("Error executing select query " + e, e);
/*     */       } else {
/* 198 */         log.warn(errMsg + " " + e, e);
/* 199 */       }  return false;
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 205 */         if (con != null)
/* 206 */           con.close(); 
/* 207 */         if (stmt != null) {
/* 208 */           stmt.close();
/*     */         }
/* 210 */       } catch (Exception e) {
/*     */         
/* 212 */         log.warn("Failed to close DB connection " + e, e);
/*     */       } 
/*     */     } 
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean call(String query, ReadStH reader) {
/* 227 */     return call(query, reader, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean call(String query, ReadStH reader, String errMsg) {
/* 240 */     Connection con = null;
/* 241 */     CallableStatement stmt = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 246 */       con = DatabaseFactory.getConnection();
/* 247 */       stmt = con.prepareCall(query);
/* 248 */       if (reader instanceof CallReadStH)
/* 249 */         ((CallReadStH)reader).setParams(stmt); 
/* 250 */       ResultSet rset = stmt.executeQuery();
/* 251 */       reader.handleRead(rset);
/*     */     }
/* 253 */     catch (Exception e) {
/*     */       
/* 255 */       if (errMsg == null) {
/* 256 */         log.warn("Error calling stored procedure " + e, e);
/*     */       } else {
/* 258 */         log.warn(errMsg + " " + e, e);
/* 259 */       }  return false;
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 265 */         if (con != null)
/* 266 */           con.close(); 
/* 267 */         if (stmt != null) {
/* 268 */           stmt.close();
/*     */         }
/* 270 */       } catch (Exception e) {
/*     */         
/* 272 */         log.warn("Failed to close DB connection " + e, e);
/*     */       } 
/*     */     } 
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insertUpdate(String query) {
/* 287 */     return insertUpdate(query, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insertUpdate(String query, String errMsg) {
/* 300 */     return insertUpdate(query, null, errMsg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insertUpdate(String query, IUStH batch) {
/* 313 */     return insertUpdate(query, batch, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insertUpdate(String query, IUStH batch, String errMsg) {
/* 328 */     Connection con = null;
/* 329 */     PreparedStatement stmt = null;
/*     */ 
/*     */     
/*     */     try {
/* 333 */       con = DatabaseFactory.getConnection();
/* 334 */       stmt = con.prepareStatement(query);
/* 335 */       if (batch != null) {
/* 336 */         batch.handleInsertUpdate(stmt);
/*     */       } else {
/* 338 */         stmt.executeUpdate();
/*     */       }
/*     */     
/* 341 */     } catch (Exception e) {
/*     */       
/* 343 */       if (errMsg == null) {
/* 344 */         log.warn("Failed to execute IU query " + e, e);
/*     */       } else {
/* 346 */         log.warn(errMsg + " " + e, e);
/*     */       } 
/* 348 */       return false;
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 354 */         if (con != null)
/* 355 */           con.close(); 
/* 356 */         if (stmt != null) {
/* 357 */           stmt.close();
/*     */         }
/* 359 */       } catch (Exception e) {
/*     */         
/* 361 */         log.warn("Failed to close DB connection " + e, e);
/*     */       } 
/*     */     } 
/* 364 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transaction beginTransaction() throws SQLException {
/* 376 */     Connection con = DatabaseFactory.getConnection();
/* 377 */     return new Transaction(con);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PreparedStatement prepareStatement(String sql) {
/* 391 */     return prepareStatement(sql, 1003, 1007);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) {
/* 412 */     Connection c = null;
/* 413 */     PreparedStatement ps = null;
/*     */     
/*     */     try {
/* 416 */       c = DatabaseFactory.getConnection();
/* 417 */       ps = c.prepareStatement(sql, resultSetType, resultSetConcurrency);
/*     */     }
/* 419 */     catch (Exception e) {
/*     */       
/* 421 */       log.error("Can't create PreparedStatement for querry: " + sql, e);
/* 422 */       if (c != null) {
/*     */         
/*     */         try {
/*     */           
/* 426 */           c.close();
/*     */         }
/* 428 */         catch (SQLException e1) {
/*     */           
/* 430 */           log.error("Can't close connection after exception", e1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 435 */     return ps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int executeUpdate(PreparedStatement statement) {
/*     */     try {
/* 449 */       return statement.executeUpdate();
/*     */     }
/* 451 */     catch (Exception e) {
/*     */       
/* 453 */       log.error("Can't execute update for PreparedStatement", e);
/*     */ 
/*     */       
/* 456 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void executeUpdateAndClose(PreparedStatement statement) {
/* 467 */     executeUpdate(statement);
/* 468 */     close(statement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResultSet executeQuerry(PreparedStatement statement) {
/* 480 */     ResultSet rs = null;
/*     */     
/*     */     try {
/* 483 */       rs = statement.executeQuery();
/*     */     }
/* 485 */     catch (Exception e) {
/*     */       
/* 487 */       log.error("Error while executing querry", e);
/*     */     } 
/* 489 */     return rs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void close(PreparedStatement statement) {
/*     */     try {
/* 503 */       if (statement.isClosed()) {
/*     */ 
/*     */         
/* 506 */         log.warn("Attempt to close PreparedStatement that is closes already", new Exception());
/*     */         
/*     */         return;
/*     */       } 
/* 510 */       Connection c = statement.getConnection();
/* 511 */       statement.close();
/* 512 */       c.close();
/*     */     }
/* 514 */     catch (Exception e) {
/*     */       
/* 516 */       log.error("Error while closing PreparedStatement", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\DB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
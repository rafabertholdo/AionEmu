/*     */ package com.aionemu.commons.database;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Savepoint;
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
/*     */ public class Transaction
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(Transaction.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection connection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Transaction(Connection con) throws SQLException {
/*  58 */     this.connection = con;
/*  59 */     this.connection.setAutoCommit(false);
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
/*     */   public void insertUpdate(String sql) throws SQLException {
/*  72 */     insertUpdate(sql, null);
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
/*     */   public void insertUpdate(String sql, IUStH iusth) throws SQLException {
/*  88 */     PreparedStatement statement = this.connection.prepareStatement(sql);
/*  89 */     if (iusth != null) {
/*     */       
/*  91 */       iusth.handleInsertUpdate(statement);
/*     */     }
/*     */     else {
/*     */       
/*  95 */       statement.executeUpdate();
/*     */     } 
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
/*     */   public Savepoint setSavepoint(String name) throws SQLException {
/* 110 */     return this.connection.setSavepoint(name);
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
/*     */   public void releaseSavepoint(Savepoint savepoint) throws SQLException {
/* 123 */     this.connection.releaseSavepoint(savepoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit() throws SQLException {
/* 134 */     commit(null);
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
/*     */   public void commit(Savepoint rollBackToOnError) throws SQLException {
/*     */     try {
/* 150 */       this.connection.commit();
/*     */     }
/* 152 */     catch (SQLException e) {
/*     */       
/* 154 */       log.warn("Error while commiting transaction", e);
/*     */ 
/*     */       
/*     */       try {
/* 158 */         if (rollBackToOnError != null)
/*     */         {
/* 160 */           this.connection.rollback(rollBackToOnError);
/*     */         }
/*     */         else
/*     */         {
/* 164 */           this.connection.rollback();
/*     */         }
/*     */       
/* 167 */       } catch (SQLException e1) {
/*     */         
/* 169 */         log.error("Can't rollback transaction", e1);
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     this.connection.setAutoCommit(true);
/* 174 */     this.connection.close();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\Transaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
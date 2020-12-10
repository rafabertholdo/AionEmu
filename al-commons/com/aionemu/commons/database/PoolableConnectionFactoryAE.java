/*    */ package com.aionemu.commons.database;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import org.apache.commons.dbcp.ConnectionFactory;
/*    */ import org.apache.commons.dbcp.PoolableConnectionFactory;
/*    */ import org.apache.commons.pool.KeyedObjectPoolFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PoolableConnectionFactoryAE
/*    */   extends PoolableConnectionFactory
/*    */ {
/*    */   private final int validationTimeout;
/*    */   
/*    */   public PoolableConnectionFactoryAE(ConnectionFactory connFactory, ObjectPool pool, KeyedObjectPoolFactory stmtPoolFactory, int validationTimeout, boolean defaultReadOnly, boolean defaultAutoCommit) {
/* 68 */     super(connFactory, pool, stmtPoolFactory, null, defaultReadOnly, defaultAutoCommit);
/* 69 */     this.validationTimeout = validationTimeout;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validateConnection(Connection conn) throws SQLException {
/* 79 */     if (conn.isClosed())
/* 80 */       throw new SQLException("validateConnection: connection closed"); 
/* 81 */     if (this.validationTimeout >= 0 && !conn.isValid(this.validationTimeout))
/* 82 */       throw new SQLException("validateConnection: connection invalid"); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\PoolableConnectionFactoryAE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
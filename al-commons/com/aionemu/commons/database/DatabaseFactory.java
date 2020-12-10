/*     */ package com.aionemu.commons.database;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.commons.dbcp.ConnectionFactory;
/*     */ import org.apache.commons.dbcp.DriverManagerConnectionFactory;
/*     */ import org.apache.commons.dbcp.PoolingDataSource;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.impl.GenericObjectPool;
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
/*     */ public class DatabaseFactory
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(DatabaseFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DataSource dataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GenericObjectPool connectionPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String databaseName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int databaseMajorVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int databaseMinorVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void init() {
/*  86 */     if (dataSource != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  93 */       DatabaseConfig.DATABASE_DRIVER.newInstance();
/*     */     }
/*  95 */     catch (Exception e) {
/*     */       
/*  97 */       log.fatal("Error obtaining DB driver", e);
/*  98 */       throw new Error("DB Driver doesnt exist!");
/*     */     } 
/*     */     
/* 101 */     connectionPool = new GenericObjectPool();
/*     */     
/* 103 */     if (DatabaseConfig.DATABASE_CONNECTIONS_MIN > DatabaseConfig.DATABASE_CONNECTIONS_MAX) {
/*     */       
/* 105 */       log.error("Please check your database configuration. Minimum amount of connections is > maximum");
/* 106 */       DatabaseConfig.DATABASE_CONNECTIONS_MAX = DatabaseConfig.DATABASE_CONNECTIONS_MIN;
/*     */     } 
/*     */     
/* 109 */     connectionPool.setMaxIdle(DatabaseConfig.DATABASE_CONNECTIONS_MIN);
/* 110 */     connectionPool.setMaxActive(DatabaseConfig.DATABASE_CONNECTIONS_MAX);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 116 */       dataSource = setupDataSource();
/* 117 */       Connection c = getConnection();
/* 118 */       DatabaseMetaData dmd = c.getMetaData();
/* 119 */       databaseName = dmd.getDatabaseProductName();
/* 120 */       databaseMajorVersion = dmd.getDatabaseMajorVersion();
/* 121 */       databaseMinorVersion = dmd.getDatabaseMinorVersion();
/* 122 */       c.close();
/*     */     }
/* 124 */     catch (Exception e) {
/*     */       
/* 126 */       log.fatal("Error with connection string: " + DatabaseConfig.DATABASE_URL, e);
/* 127 */       throw new Error("DatabaseFactory not initialized!");
/*     */     } 
/*     */     
/* 130 */     log.info("Successfully connected to database");
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
/*     */   private static DataSource setupDataSource() throws Exception {
/* 143 */     DriverManagerConnectionFactory driverManagerConnectionFactory = new DriverManagerConnectionFactory(DatabaseConfig.DATABASE_URL, DatabaseConfig.DATABASE_USER, DatabaseConfig.DATABASE_PASSWORD);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     new PoolableConnectionFactoryAE((ConnectionFactory)driverManagerConnectionFactory, (ObjectPool)connectionPool, null, 1, false, true);
/*     */ 
/*     */     
/* 152 */     return (DataSource)new PoolingDataSource((ObjectPool)connectionPool);
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
/*     */   public static Connection getConnection() throws SQLException {
/* 167 */     return dataSource.getConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActiveConnections() {
/* 177 */     return connectionPool.getNumActive();
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
/*     */   public int getIdleConnections() {
/* 189 */     return connectionPool.getNumIdle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void shutdown() {
/*     */     try {
/* 199 */       connectionPool.close();
/*     */     }
/* 201 */     catch (Exception e) {
/*     */       
/* 203 */       log.warn("Failed to shutdown DatabaseFactory", e);
/*     */     } 
/*     */ 
/*     */     
/* 207 */     dataSource = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void close(Connection con) {
/* 212 */     if (con == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 217 */       con.close();
/*     */     }
/* 219 */     catch (SQLException e) {
/*     */       
/* 221 */       log.warn("DatabaseFactory: Failed to close database connection!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDatabaseName() {
/* 232 */     return databaseName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDatabaseMajorVersion() {
/* 242 */     return databaseMajorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDatabaseMinorVersion() {
/* 252 */     return databaseMinorVersion;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\DatabaseFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
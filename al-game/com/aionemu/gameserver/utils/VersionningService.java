/*     */ package com.aionemu.gameserver.utils;
/*     */ 
/*     */ import com.aionemu.commons.utils.AEInfos;
/*     */ import com.aionemu.commons.versionning.Version;
/*     */ import com.aionemu.gameserver.GameServer;
/*     */ import java.util.Date;
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
/*     */ public class VersionningService
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(VersionningService.class);
/*     */   
/*  35 */   private static final VersionInfo commons = new VersionInfo(AEInfos.class);
/*  36 */   private static final VersionInfo game = new VersionInfo(GameServer.class);
/*     */ 
/*     */   
/*     */   public static String getCommonsVersion() {
/*  40 */     return commons.getBuildVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getGameVersion() {
/*  45 */     return game.getBuildVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCommonsRevision() {
/*  50 */     return commons.getBuildRevision();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getGameRevision() {
/*  55 */     return game.getBuildRevision();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Date getCommonsDate() {
/*  60 */     return commons.getBuildDate();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Date getGameDate() {
/*  65 */     return game.getBuildDate();
/*     */   }
/*     */   
/*     */   private static final class VersionInfo
/*     */     extends Version
/*     */   {
/*     */     private final String version;
/*     */     private final String revision;
/*     */     private final Date buildDate;
/*     */     
/*     */     public VersionInfo(Class<?> c) {
/*  76 */       super(c);
/*     */       
/*  78 */       this.version = String.format("%-6s", new Object[] { getVersion() });
/*  79 */       this.revision = String.format("%-6s", new Object[] { getRevision() });
/*  80 */       this.buildDate = new Date(getDate());
/*     */     }
/*     */ 
/*     */     
/*     */     public String getBuildVersion() {
/*  85 */       return this.version;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getBuildRevision() {
/*  90 */       return this.revision;
/*     */     }
/*     */ 
/*     */     
/*     */     public Date getBuildDate() {
/*  95 */       return this.buildDate;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getFullVersionInfo() {
/* 101 */     return new String[] { "Commons Version: " + getCommonsVersion(), "Commons Revision: " + getCommonsRevision(), "Commons Build Date: " + getCommonsDate(), "GS Version: " + getGameVersion(), "GS Revision: " + getGameRevision(), "GS Build Date: " + getGameDate() };
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
/*     */   public static void printFullVersionInfo() {
/* 113 */     for (String line : getFullVersionInfo())
/* 114 */       log.info(line); 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\VersionningService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.configs;
/*     */ 
/*     */ import com.aionemu.commons.configuration.ConfigurableProcessor;
/*     */ import com.aionemu.commons.database.DatabaseConfig;
/*     */ import com.aionemu.commons.utils.AEInfos;
/*     */ import com.aionemu.commons.utils.PropertiesUtils;
/*     */ import com.aionemu.gameserver.configs.administration.AdminConfig;
/*     */ import com.aionemu.gameserver.configs.main.CacheConfig;
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.configs.main.EnchantsConfig;
/*     */ import com.aionemu.gameserver.configs.main.FallDamageConfig;
/*     */ import com.aionemu.gameserver.configs.main.GSConfig;
/*     */ import com.aionemu.gameserver.configs.main.GroupConfig;
/*     */ import com.aionemu.gameserver.configs.main.HTMLConfig;
/*     */ import com.aionemu.gameserver.configs.main.LegionConfig;
/*     */ import com.aionemu.gameserver.configs.main.NpcMovementConfig;
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.configs.main.PricesConfig;
/*     */ import com.aionemu.gameserver.configs.main.RateConfig;
/*     */ import com.aionemu.gameserver.configs.main.ShutdownConfig;
/*     */ import com.aionemu.gameserver.configs.main.SiegeConfig;
/*     */ import com.aionemu.gameserver.configs.main.ThreadConfig;
/*     */ import com.aionemu.gameserver.configs.network.IPConfig;
/*     */ import com.aionemu.gameserver.configs.network.NetworkConfig;
/*     */ import java.util.Properties;
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
/*     */ public class Config
/*     */ {
/*  52 */   protected static final Logger log = Logger.getLogger(Config.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void load() {
/*     */     try {
/*  61 */       Properties[] props = PropertiesUtils.loadAllFromDirectory("./config");
/*     */       
/*  63 */       ConfigurableProcessor.process(Config.class, props);
/*     */ 
/*     */       
/*  66 */       AEInfos.printSection("Administration");
/*  67 */       Properties[] admin = PropertiesUtils.loadAllFromDirectory("./config/administration");
/*     */       
/*  69 */       ConfigurableProcessor.process(AdminConfig.class, admin);
/*     */ 
/*     */       
/*  72 */       AEInfos.printSection("Main");
/*  73 */       Properties[] main = PropertiesUtils.loadAllFromDirectory("./config/main");
/*     */       
/*  75 */       ConfigurableProcessor.process(LegionConfig.class, main);
/*  76 */       ConfigurableProcessor.process(RateConfig.class, main);
/*  77 */       ConfigurableProcessor.process(CacheConfig.class, main);
/*  78 */       ConfigurableProcessor.process(ShutdownConfig.class, main);
/*  79 */       ConfigurableProcessor.process(OptionsConfig.class, main);
/*  80 */       ConfigurableProcessor.process(GroupConfig.class, main);
/*  81 */       ConfigurableProcessor.process(CustomConfig.class, main);
/*  82 */       ConfigurableProcessor.process(EnchantsConfig.class, main);
/*  83 */       ConfigurableProcessor.process(FallDamageConfig.class, main);
/*  84 */       ConfigurableProcessor.process(GSConfig.class, main);
/*  85 */       ConfigurableProcessor.process(NpcMovementConfig.class, main);
/*  86 */       ConfigurableProcessor.process(PricesConfig.class, main);
/*  87 */       ConfigurableProcessor.process(SiegeConfig.class, main);
/*  88 */       ConfigurableProcessor.process(ThreadConfig.class, main);
/*  89 */       ConfigurableProcessor.process(HTMLConfig.class, main);
/*     */ 
/*     */       
/*  92 */       AEInfos.printSection("Network");
/*  93 */       Properties[] network = PropertiesUtils.loadAllFromDirectory("./config/network");
/*     */       
/*  95 */       ConfigurableProcessor.process(NetworkConfig.class, network);
/*  96 */       ConfigurableProcessor.process(DatabaseConfig.class, network);
/*     */     }
/*  98 */     catch (Exception e) {
/*     */       
/* 100 */       log.fatal("Can't load gameserver configuration: ", e);
/* 101 */       throw new Error("Can't load gameserver configuration: ", e);
/*     */     } 
/*     */     
/* 104 */     IPConfig.load();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
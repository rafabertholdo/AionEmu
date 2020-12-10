/*    */ package com.aionemu.gameserver.configs.main;
/*    */ 
/*    */ import com.aionemu.commons.configuration.Property;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class LegionConfig
/*    */ {
/* 34 */   protected static final Logger log = Logger.getLogger(LegionConfig.class);
/*    */   @Property(key = "legion.pattern", defaultValue = "[a-zA-Z ]{2,16}")
/*    */   public static Pattern LEGION_NAME_PATTERN;
/*    */   @Property(key = "legion.selfintropattern", defaultValue = "[a-zA-Z]{2,25}")
/*    */   public static Pattern SELF_INTRO_PATTERN;
/*    */   @Property(key = "legion.nicknamepattern", defaultValue = "[a-zA-Z]{2,10}")
/*    */   public static Pattern NICKNAME_PATTERN;
/*    */   @Property(key = "legion.announcementpattern", defaultValue = "[a-zA-Z .,]{2,120}")
/*    */   public static Pattern ANNOUNCEMENT_PATTERN;
/*    */   @Property(key = "legion.disbandtime", defaultValue = "86400")
/*    */   public static int LEGION_DISBAND_TIME;
/*    */   @Property(key = "legion.disbanddifference", defaultValue = "604800")
/*    */   public static int LEGION_DISBAND_DIFFERENCE;
/*    */   @Property(key = "legion.creationrequiredkinah", defaultValue = "10000")
/*    */   public static int LEGION_CREATE_REQUIRED_KINAH;
/*    */   @Property(key = "legion.emblemrequiredkinah", defaultValue = "10000")
/*    */   public static int LEGION_EMBLEM_REQUIRED_KINAH;
/*    */   @Property(key = "legion.level2requiredkinah", defaultValue = "100000")
/*    */   public static int LEGION_LEVEL2_REQUIRED_KINAH;
/*    */   @Property(key = "legion.level3requiredkinah", defaultValue = "1000000")
/*    */   public static int LEGION_LEVEL3_REQUIRED_KINAH;
/*    */   @Property(key = "legion.level4requiredkinah", defaultValue = "2000000")
/*    */   public static int LEGION_LEVEL4_REQUIRED_KINAH;
/*    */   @Property(key = "legion.level5requiredkinah", defaultValue = "6000000")
/*    */   public static int LEGION_LEVEL5_REQUIRED_KINAH;
/*    */   @Property(key = "legion.level2requiredmembers", defaultValue = "10")
/*    */   public static int LEGION_LEVEL2_REQUIRED_MEMBERS;
/*    */   @Property(key = "legion.level3requiredmembers", defaultValue = "20")
/*    */   public static int LEGION_LEVEL3_REQUIRED_MEMBERS;
/*    */   @Property(key = "legion.level4requiredmembers", defaultValue = "30")
/*    */   public static int LEGION_LEVEL4_REQUIRED_MEMBERS;
/*    */   @Property(key = "legion.level5requiredmembers", defaultValue = "40")
/*    */   public static int LEGION_LEVEL5_REQUIRED_MEMBERS;
/*    */   @Property(key = "legion.level2requiredcontribution", defaultValue = "0")
/*    */   public static int LEGION_LEVEL2_REQUIRED_CONTRIBUTION;
/*    */   @Property(key = "legion.level3requiredcontribution", defaultValue = "20000")
/*    */   public static int LEGION_LEVEL3_REQUIRED_CONTRIBUTION;
/*    */   @Property(key = "legion.level4requiredcontribution", defaultValue = "100000")
/*    */   public static int LEGION_LEVEL4_REQUIRED_CONTRIBUTION;
/*    */   @Property(key = "legion.level5requiredcontribution", defaultValue = "500000")
/*    */   public static int LEGION_LEVEL5_REQUIRED_CONTRIBUTION;
/*    */   @Property(key = "legion.level1maxmembers", defaultValue = "30")
/*    */   public static int LEGION_LEVEL1_MAX_MEMBERS;
/*    */   @Property(key = "legion.level2maxmembers", defaultValue = "60")
/*    */   public static int LEGION_LEVEL2_MAX_MEMBERS;
/*    */   @Property(key = "legion.level3maxmembers", defaultValue = "90")
/*    */   public static int LEGION_LEVEL3_MAX_MEMBERS;
/*    */   @Property(key = "legion.level4maxmembers", defaultValue = "120")
/*    */   public static int LEGION_LEVEL4_MAX_MEMBERS;
/*    */   @Property(key = "legion.level5maxmembers", defaultValue = "150")
/*    */   public static int LEGION_LEVEL5_MAX_MEMBERS;
/*    */   @Property(key = "legion.warehouse", defaultValue = "false")
/*    */   public static boolean LEGION_WAREHOUSE;
/*    */   @Property(key = "legion.inviteotherfaction", defaultValue = "false")
/*    */   public static boolean LEGION_INVITEOTHERFACTION;
/*    */   @Property(key = "legion.ranking.periodicupdate", defaultValue = "1200")
/*    */   public static int LEGION_RANKING_PERIODICUPDATE;
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\main\LegionConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
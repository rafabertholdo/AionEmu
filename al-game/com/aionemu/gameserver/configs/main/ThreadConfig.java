/*    */ package com.aionemu.gameserver.configs.main;
/*    */ 
/*    */ import com.aionemu.commons.configuration.Property;
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
/*    */ public class ThreadConfig
/*    */ {
/*    */   @Property(key = "thread.basepoolsize", defaultValue = "2")
/*    */   public static int BASE_THREAD_POOL_SIZE;
/*    */   @Property(key = "thread.threadpercore", defaultValue = "4")
/*    */   public static int EXTRA_THREAD_PER_CORE;
/*    */   public static int THREAD_POOL_SIZE;
/*    */   
/*    */   public static void load() {
/* 37 */     int baseThreadPoolSize = BASE_THREAD_POOL_SIZE;
/* 38 */     int extraThreadPerCore = EXTRA_THREAD_PER_CORE;
/*    */     
/* 40 */     THREAD_POOL_SIZE = baseThreadPoolSize + Runtime.getRuntime().availableProcessors() * extraThreadPerCore;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\main\ThreadConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
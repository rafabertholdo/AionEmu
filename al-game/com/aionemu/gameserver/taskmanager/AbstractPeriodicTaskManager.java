/*    */ package com.aionemu.gameserver.taskmanager;
/*    */ 
/*    */ import com.aionemu.commons.taskmanager.AbstractLockManager;
/*    */ import com.aionemu.commons.utils.Rnd;
/*    */ import com.aionemu.gameserver.GameServer;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*    */ public abstract class AbstractPeriodicTaskManager
/*    */   extends AbstractLockManager
/*    */   implements Runnable, GameServer.StartupHook
/*    */ {
/* 35 */   protected static final Logger log = Logger.getLogger(AbstractPeriodicTaskManager.class);
/*    */   
/*    */   private final int period;
/*    */ 
/*    */   
/*    */   public AbstractPeriodicTaskManager(int period) {
/* 41 */     this.period = period;
/*    */     
/* 43 */     GameServer.addStartupHook(this);
/*    */     
/* 45 */     log.info(getClass().getSimpleName() + ": Initialized.");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final void onStartup() {
/* 51 */     ThreadPoolManager.getInstance().scheduleAtFixedRate(this, (1000 + Rnd.get(this.period)), Rnd.get(this.period - 5, this.period + 5));
/*    */   }
/*    */   
/*    */   public abstract void run();
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\AbstractPeriodicTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
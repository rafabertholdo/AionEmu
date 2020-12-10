/*    */ package com.aionemu.gameserver.taskmanager.tasks;
/*    */ 
/*    */ import com.aionemu.gameserver.ShutdownHook;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.tasks.TaskFromDBHandler;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ public class RestartTask
/*    */   extends TaskFromDBHandler
/*    */ {
/* 35 */   private static final Logger log = Logger.getLogger(RestartTask.class);
/*    */   
/*    */   private int countDown;
/*    */   
/*    */   private int announceInterval;
/*    */   
/*    */   private int warnCountDown;
/*    */   
/*    */   public String getTaskName() {
/* 44 */     return "restart";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 50 */     if (this.params.length == 3) {
/* 51 */       return true;
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 59 */     log.info("Task[" + this.id + "] launched : restarting the server !");
/* 60 */     setLastActivation();
/*    */     
/* 62 */     this.countDown = Integer.parseInt(this.params[0]);
/* 63 */     this.announceInterval = Integer.parseInt(this.params[1]);
/* 64 */     this.warnCountDown = Integer.parseInt(this.params[2]);
/*    */     
/* 66 */     for (Player player : World.getInstance().getAllPlayers()) {
/* 67 */       PacketSendUtility.sendSysMessage(player, "Automatic Task: The server will restart in " + this.warnCountDown + " seconds ! Please find a safe place and disconnect your character.");
/*    */     }
/* 69 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 73 */             ShutdownHook.getInstance().doShutdown(RestartTask.this.countDown, RestartTask.this.announceInterval, ShutdownHook.ShutdownMode.RESTART);
/*    */           }
/*    */         }(this.warnCountDown * 1000));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\tasks\RestartTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
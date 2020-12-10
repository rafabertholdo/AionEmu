/*    */ package com.aionemu.gameserver.taskmanager.tasks;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.tasks.TaskFromDBHandler;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ 
/*    */ 
/*    */ public class UpdateAbyssRank
/*    */   extends TaskFromDBHandler
/*    */ {
/* 37 */   private static final Logger log = Logger.getLogger(UpdateAbyssRank.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTaskName() {
/* 42 */     return "update_abyss_rank";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 48 */     if (this.params.length > 0) {
/* 49 */       return false;
/*    */     }
/* 51 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 57 */     log.info("Task[" + this.id + "] launched : updating abyss ranks for all online players !");
/* 58 */     setLastActivation();
/*    */     
/* 60 */     for (Player player : World.getInstance().getAllPlayers()) {
/*    */       
/* 62 */       player.getAbyssRank().doUpdate();
/* 63 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ABYSS_RANK(player.getAbyssRank()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\tasks\UpdateAbyssRank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
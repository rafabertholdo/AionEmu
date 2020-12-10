/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.SocialService;
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
/*    */ public class CM_BLOCK_DEL
/*    */   extends AionClientPacket
/*    */ {
/* 33 */   private static Logger log = Logger.getLogger(CM_BLOCK_DEL.class);
/*    */ 
/*    */ 
/*    */   
/*    */   private String targetName;
/*    */ 
/*    */ 
/*    */   
/*    */   public CM_BLOCK_DEL(int opcode) {
/* 42 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 51 */     this.targetName = readS();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 60 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 62 */     BlockedPlayer target = activePlayer.getBlockList().getBlockedPlayer(this.targetName);
/* 63 */     if (target == null) {
/*    */       
/* 65 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BUDDYLIST_NOT_IN_LIST);
/*    */ 
/*    */     
/*    */     }
/* 69 */     else if (!SocialService.deleteBlockedUser(activePlayer, target.getObjId())) {
/*    */       
/* 71 */       log.debug("Could not unblock " + this.targetName + " from " + activePlayer.getName() + " blocklist. Check database setup.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BLOCK_DEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
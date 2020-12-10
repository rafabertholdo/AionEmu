/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_BLOCK_RESPONSE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.SocialService;
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
/*    */ public class CM_BLOCK_ADD
/*    */   extends AionClientPacket
/*    */ {
/* 34 */   private static Logger log = Logger.getLogger(CM_BLOCK_ADD.class);
/*    */   
/*    */   private String targetName;
/*    */   
/*    */   private String reason;
/*    */   
/*    */   public CM_BLOCK_ADD(int opcode) {
/* 41 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 49 */     this.targetName = readS();
/* 50 */     this.reason = readS();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 60 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 62 */     Player targetPlayer = World.getInstance().findPlayer(this.targetName);
/*    */ 
/*    */     
/* 65 */     if (activePlayer.getName().equalsIgnoreCase(this.targetName)) {
/*    */       
/* 67 */       sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(4, this.targetName));
/*    */ 
/*    */     
/*    */     }
/* 71 */     else if (activePlayer.getBlockList().isFull()) {
/*    */       
/* 73 */       sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(3, this.targetName));
/*    */ 
/*    */     
/*    */     }
/* 77 */     else if (targetPlayer == null) {
/*    */       
/* 79 */       sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(2, this.targetName));
/*    */ 
/*    */     
/*    */     }
/* 83 */     else if (activePlayer.getFriendList().getFriend(targetPlayer.getObjectId()) != null) {
/*    */       
/* 85 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BLOCKLIST_NO_BUDDY);
/*    */ 
/*    */     
/*    */     }
/* 89 */     else if (activePlayer.getBlockList().contains(targetPlayer.getObjectId())) {
/*    */       
/* 91 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BLOCKLIST_ALREADY_BLOCKED);
/*    */ 
/*    */     
/*    */     }
/* 95 */     else if (!SocialService.addBlockedUser(activePlayer, targetPlayer, this.reason)) {
/*    */       
/* 97 */       log.error("Failed to add " + targetPlayer.getName() + " to the block list for " + activePlayer.getName() + " - check database setup.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BLOCK_ADD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Friend;
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
/*    */ 
/*    */ public class CM_FRIEND_DEL
/*    */   extends AionClientPacket
/*    */ {
/*    */   private String targetName;
/* 35 */   private static Logger log = Logger.getLogger(CM_FRIEND_DEL.class);
/*    */ 
/*    */   
/*    */   public CM_FRIEND_DEL(int opcode) {
/* 39 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 48 */     this.targetName = readS();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 59 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 60 */     Friend target = activePlayer.getFriendList().getFriend(this.targetName);
/* 61 */     if (target == null) {
/*    */       
/* 63 */       log.warn(activePlayer.getName() + " tried to delete friend " + this.targetName + " who is not his friend");
/* 64 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BUDDYLIST_NOT_IN_LIST);
/*    */     }
/*    */     else {
/*    */       
/* 68 */       SocialService.deleteFriend(activePlayer, target.getOid());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_FRIEND_DEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
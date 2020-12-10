/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
/*    */ import com.aionemu.gameserver.network.loginserver.LoginServer;
/*    */ import com.aionemu.gameserver.services.PlayerService;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CM_QUIT
/*    */   extends AionClientPacket
/*    */ {
/*    */   private boolean logout;
/*    */   
/*    */   public CM_QUIT(int opcode) {
/* 47 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 56 */     this.logout = (readC() == 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 65 */     AionConnection client = (AionConnection)getConnection();
/*    */     
/* 67 */     if (client.getState() == AionConnection.State.IN_GAME) {
/*    */       
/* 69 */       Player player = client.getActivePlayer();
/*    */       
/* 71 */       if (!this.logout) {
/* 72 */         LoginServer.getInstance().aionClientDisconnected(client.getAccount().getId());
/*    */       }
/* 74 */       PlayerService.playerLoggedOut(player);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 79 */       client.setActivePlayer(null);
/*    */     } 
/*    */     
/* 82 */     if (this.logout) {
/*    */       
/* 84 */       sendPacket((AionServerPacket)new SM_QUIT_RESPONSE());
/*    */     }
/*    */     else {
/*    */       
/* 88 */       client.close((AionServerPacket)new SM_QUIT_RESPONSE(), true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_QUIT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
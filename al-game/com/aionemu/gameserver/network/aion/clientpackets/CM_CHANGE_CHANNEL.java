/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.TeleportService;
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
/*    */ public class CM_CHANGE_CHANNEL
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int channel;
/*    */   
/*    */   public CM_CHANGE_CHANNEL(int opcode) {
/* 34 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 40 */     this.channel = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 46 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 47 */     TeleportService.changeChannel(activePlayer, this.channel);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CHANGE_CHANNEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
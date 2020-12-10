/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.BrokerService;
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
/*    */ public class CM_BROKER_LIST
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int brokerId;
/*    */   private int sortType;
/*    */   private int page;
/*    */   private int listMask;
/*    */   
/*    */   public CM_BROKER_LIST(int opcode) {
/* 37 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 43 */     this.brokerId = readD();
/* 44 */     this.sortType = readC();
/* 45 */     this.page = readH();
/* 46 */     this.listMask = readH();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 52 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 54 */     BrokerService.getInstance().showRequestedItems(player, this.listMask, this.sortType, this.page);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BROKER_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
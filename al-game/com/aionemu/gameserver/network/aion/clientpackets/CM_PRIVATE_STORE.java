/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.trade.TradePSItem;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.PrivateStoreService;
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
/*    */ public class CM_PRIVATE_STORE
/*    */   extends AionClientPacket
/*    */ {
/*    */   private Player activePlayer;
/*    */   private TradePSItem[] tradePSItems;
/*    */   private int itemCount;
/*    */   
/*    */   public CM_PRIVATE_STORE(int opcode) {
/* 44 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 56 */     this.activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     this.itemCount = readH();
/* 62 */     this.tradePSItems = new TradePSItem[this.itemCount];
/* 63 */     for (int i = 0; i < this.itemCount; i++)
/*    */     {
/* 65 */       this.tradePSItems[i] = new TradePSItem(readD(), readD(), readH(), readD());
/*    */     }
/*    */   }
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
/*    */   protected void runImpl() {
/* 82 */     if (this.itemCount > 0) {
/*    */       
/* 84 */       PrivateStoreService.addItem(this.activePlayer, this.tradePSItems);
/*    */     }
/*    */     else {
/*    */       
/* 88 */       PrivateStoreService.closePrivateStore(this.activePlayer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_PRIVATE_STORE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
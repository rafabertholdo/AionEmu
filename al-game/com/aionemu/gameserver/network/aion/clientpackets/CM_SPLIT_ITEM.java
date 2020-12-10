/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.ItemService;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CM_SPLIT_ITEM
/*    */   extends AionClientPacket
/*    */ {
/*    */   int sourceItemObjId;
/*    */   int sourceStorageType;
/*    */   long itemAmount;
/*    */   int destinationItemObjId;
/*    */   int destinationStorageType;
/*    */   int slotNum;
/*    */   
/*    */   public CM_SPLIT_ITEM(int opcode) {
/* 27 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 33 */     this.sourceItemObjId = readD();
/* 34 */     this.itemAmount = readD();
/*    */     
/* 36 */     byte[] zeros = readB(4);
/* 37 */     this.sourceStorageType = readC();
/* 38 */     this.destinationItemObjId = readD();
/* 39 */     this.destinationStorageType = readC();
/* 40 */     this.slotNum = readH();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 47 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 49 */     if (this.destinationItemObjId == 0) {
/* 50 */       ItemService.splitItem(player, this.sourceItemObjId, this.itemAmount, this.slotNum, this.sourceStorageType, this.destinationStorageType);
/*    */     } else {
/* 52 */       ItemService.mergeItems(player, this.sourceItemObjId, this.itemAmount, this.destinationItemObjId, this.sourceStorageType, this.destinationStorageType);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SPLIT_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.DropService;
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
/*    */ public class CM_GROUP_LOOT
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int groupId;
/*    */   private int unk1;
/*    */   private int unk2;
/*    */   private int itemId;
/*    */   private int unk3;
/*    */   private int npcId;
/*    */   private int distributionId;
/*    */   private int roll;
/*    */   private long bid;
/*    */   
/*    */   public CM_GROUP_LOOT(int opcode) {
/* 44 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 50 */     this.groupId = readD();
/* 51 */     this.unk1 = readD();
/* 52 */     this.unk2 = readD();
/* 53 */     this.itemId = readD();
/* 54 */     this.unk3 = readC();
/* 55 */     this.npcId = readD();
/* 56 */     this.distributionId = readC();
/* 57 */     this.roll = readD();
/* 58 */     this.bid = readQ();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 67 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 68 */     if (player == null) {
/*    */       return;
/*    */     }
/* 71 */     switch (this.distributionId) {
/*    */       
/*    */       case 2:
/* 74 */         DropService.getInstance().handleRoll(player, this.roll, this.itemId, this.npcId);
/*    */         break;
/*    */       case 3:
/* 77 */         DropService.getInstance().handleBid(player, this.bid, this.itemId, this.npcId);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GROUP_LOOT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
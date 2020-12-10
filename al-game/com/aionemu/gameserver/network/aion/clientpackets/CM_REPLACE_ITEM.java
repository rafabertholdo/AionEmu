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
/*    */ public class CM_REPLACE_ITEM
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int sourceStorageType;
/*    */   private int sourceItemObjId;
/*    */   private int replaceStorageType;
/*    */   private int replaceItemObjId;
/*    */   
/*    */   public CM_REPLACE_ITEM(int opcode) {
/* 37 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 43 */     this.sourceStorageType = readC();
/* 44 */     this.sourceItemObjId = readD();
/* 45 */     this.replaceStorageType = readC();
/* 46 */     this.replaceItemObjId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 52 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 53 */     ItemService.switchStoragesItems(player, this.sourceStorageType, this.sourceItemObjId, this.replaceStorageType, this.replaceItemObjId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_REPLACE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
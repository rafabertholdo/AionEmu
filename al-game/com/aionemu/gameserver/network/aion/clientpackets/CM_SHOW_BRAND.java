/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.AllianceService;
/*    */ import com.aionemu.gameserver.services.GroupService;
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
/*    */ public class CM_SHOW_BRAND
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int brandId;
/*    */   private int targetObjectId;
/*    */   
/*    */   public CM_SHOW_BRAND(int opcode) {
/* 39 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 48 */     this.brandId = readD();
/* 49 */     this.targetObjectId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 58 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 60 */     if (player == null) {
/*    */       return;
/*    */     }
/* 63 */     if (player.isInGroup())
/* 64 */       GroupService.getInstance().showBrand(player.getPlayerGroup(), this.brandId, this.targetObjectId); 
/* 65 */     if (player.isInAlliance())
/* 66 */       AllianceService.getInstance().showBrand(player.getPlayerAlliance(), this.brandId, this.targetObjectId); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SHOW_BRAND.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
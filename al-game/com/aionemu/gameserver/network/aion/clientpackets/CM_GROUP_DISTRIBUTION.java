/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
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
/*    */ public class CM_GROUP_DISTRIBUTION
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int amount;
/*    */   
/*    */   public CM_GROUP_DISTRIBUTION(int opcode) {
/* 35 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 44 */     this.amount = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 53 */     if (this.amount < 1) {
/*    */       return;
/*    */     }
/* 56 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 58 */     if (!RestrictionsManager.canTrade(player)) {
/*    */       return;
/*    */     }
/* 61 */     GroupService.getInstance().groupDistribution(player, this.amount);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GROUP_DISTRIBUTION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
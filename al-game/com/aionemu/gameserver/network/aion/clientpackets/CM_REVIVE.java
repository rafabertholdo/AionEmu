/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.ReviveType;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
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
/*    */ public class CM_REVIVE
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int reviveId;
/*    */   
/*    */   public CM_REVIVE(int opcode) {
/* 37 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 46 */     this.reviveId = readC();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 55 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 57 */     ReviveType reviveType = ReviveType.getReviveTypeById(this.reviveId);
/*    */     
/* 59 */     switch (reviveType) {
/*    */       
/*    */       case BIND_REVIVE:
/* 62 */         activePlayer.getReviveController().bindRevive();
/*    */         break;
/*    */       case REBIRTH_REVIVE:
/* 65 */         activePlayer.getReviveController().rebirthRevive();
/*    */         break;
/*    */       case ITEM_SELF_REVIVE:
/* 68 */         activePlayer.getReviveController().itemSelfRevive();
/*    */         break;
/*    */       case SKILL_REVIVE:
/* 71 */         activePlayer.getReviveController().skillRevive();
/*    */         break;
/*    */       case KISK_REVIVE:
/* 74 */         activePlayer.getReviveController().kiskRevive();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_REVIVE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
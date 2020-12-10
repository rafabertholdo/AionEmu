/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.AllianceService;
/*    */ import com.aionemu.gameserver.services.GroupService;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ public class CM_PLAYER_STATUS_INFO
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int status;
/*    */   private int playerObjId;
/*    */   
/*    */   public CM_PLAYER_STATUS_INFO(int opcode) {
/* 44 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 50 */     this.status = readC();
/* 51 */     this.playerObjId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 57 */     Player player, myActivePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 59 */     switch (this.status) {
/*    */ 
/*    */ 
/*    */       
/*    */       case 9:
/* 64 */         ((AionConnection)getConnection()).getActivePlayer().setLookingForGroup((this.playerObjId == 2));
/*    */         break;
/*    */ 
/*    */       
/*    */       case 12:
/*    */       case 14:
/*    */       case 15:
/*    */       case 19:
/*    */       case 23:
/*    */       case 24:
/* 74 */         AllianceService.getInstance().playerStatusInfo(myActivePlayer, this.status, this.playerObjId);
/*    */         break;
/*    */ 
/*    */       
/*    */       case 2:
/*    */       case 3:
/*    */       case 6:
/* 81 */         player = null;
/*    */         
/* 83 */         if (this.playerObjId == 0) {
/* 84 */           player = ((AionConnection)getConnection()).getActivePlayer();
/*    */         } else {
/* 86 */           player = World.getInstance().findPlayer(this.playerObjId);
/*    */         } 
/* 88 */         if (player == null || player.getPlayerGroup() == null) {
/*    */           return;
/*    */         }
/* 91 */         GroupService.getInstance().playerStatusInfo(this.status, player);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_PLAYER_STATUS_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.DuelService;
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
/*    */ public class CM_DUEL_REQUEST
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int objectId;
/*    */   
/*    */   public CM_DUEL_REQUEST(int opcode) {
/* 46 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 55 */     this.objectId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 61 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 62 */     AionObject target = World.getInstance().findAionObject(this.objectId);
/*    */     
/* 64 */     if (target == null) {
/*    */       return;
/*    */     }
/* 67 */     if (target instanceof Player) {
/*    */       
/* 69 */       Player targetPlayer = (Player)target;
/*    */       
/* 71 */       if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.DUEL)) {
/*    */         
/* 73 */         sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_DUEL(targetPlayer.getName()));
/*    */         return;
/*    */       } 
/* 76 */       DuelService duelService = DuelService.getInstance();
/* 77 */       duelService.onDuelRequest(activePlayer, targetPlayer);
/* 78 */       duelService.confirmDuelWith(activePlayer, targetPlayer);
/*    */     }
/*    */     else {
/*    */       
/* 82 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_PARTNER_INVALID(target.getName()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DUEL_REQUEST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
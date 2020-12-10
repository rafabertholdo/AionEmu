/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.SystemMessageId;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.ExchangeService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class CM_EXCHANGE_REQUEST
/*    */   extends AionClientPacket
/*    */ {
/*    */   public Integer targetObjectId;
/*    */   
/*    */   public CM_EXCHANGE_REQUEST(int opcode) {
/* 41 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 47 */     this.targetObjectId = Integer.valueOf(readD());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 54 */     final Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 55 */     final Player targetPlayer = World.getInstance().findPlayer(this.targetObjectId.intValue());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     if (activePlayer != targetPlayer)
/*    */     {
/*    */ 
/*    */ 
/*    */       
/* 65 */       if (targetPlayer != null) {
/*    */         
/* 67 */         if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.TRADE)) {
/*    */           
/* 69 */           sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_TRADE(targetPlayer.getName()));
/*    */           return;
/*    */         } 
/* 72 */         sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.REQUEST_TRADE(targetPlayer.getName()));
/*    */         
/* 74 */         RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)activePlayer)
/*    */           {
/*    */             public void acceptRequest(Creature requester, Player responder)
/*    */             {
/* 78 */               ExchangeService.getInstance().registerExchange(activePlayer, targetPlayer);
/*    */             }
/*    */ 
/*    */ 
/*    */             
/*    */             public void denyRequest(Creature requester, Player responder) {
/* 84 */               PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.EXCHANGE_HE_REJECTED_EXCHANGE, new Object[] { this.val$targetPlayer.getName() }));
/*    */             }
/*    */           };
/*    */         
/* 88 */         boolean requested = targetPlayer.getResponseRequester().putRequest(90001, responseHandler);
/* 89 */         if (requested)
/*    */         {
/* 91 */           PacketSendUtility.sendPacket(targetPlayer, (AionServerPacket)new SM_QUESTION_WINDOW(90001, 0, new Object[] { activePlayer.getName() }));
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_EXCHANGE_REQUEST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
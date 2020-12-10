/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_RESPONSE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.services.SocialService;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CM_FRIEND_ADD
/*     */   extends AionClientPacket
/*     */ {
/*     */   private String targetName;
/*     */   
/*     */   public CM_FRIEND_ADD(int opcode) {
/*  41 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  50 */     this.targetName = readS();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*  61 */     final Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*  62 */     final Player targetPlayer = World.getInstance().findPlayer(this.targetName);
/*     */ 
/*     */     
/*  65 */     if (!this.targetName.equalsIgnoreCase(activePlayer.getName()))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  70 */       if (targetPlayer == null) {
/*     */         
/*  72 */         sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(this.targetName, 1));
/*     */       }
/*  74 */       else if (activePlayer.getFriendList().getFriend(targetPlayer.getObjectId()) != null) {
/*     */         
/*  76 */         sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(targetPlayer.getName(), 2));
/*     */       }
/*  78 */       else if (activePlayer.getFriendList().isFull()) {
/*     */         
/*  80 */         sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BUDDYLIST_LIST_FULL);
/*     */       }
/*  82 */       else if (targetPlayer.getFriendList().isFull()) {
/*     */         
/*  84 */         sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(targetPlayer.getName(), 5));
/*     */       }
/*  86 */       else if (activePlayer.getBlockList().contains(targetPlayer.getObjectId())) {
/*     */         
/*  88 */         sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(targetPlayer.getName(), 8));
/*     */       }
/*  90 */       else if (targetPlayer.getBlockList().contains(activePlayer.getObjectId())) {
/*     */         
/*  92 */         sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.YOU_ARE_BLOCKED_BY(this.targetName));
/*     */       }
/*     */       else {
/*     */         
/*  96 */         RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)activePlayer)
/*     */           {
/*     */             
/*     */             public void acceptRequest(Creature requester, Player responder)
/*     */             {
/* 101 */               if (!targetPlayer.getCommonData().isOnline()) {
/*     */                 
/* 103 */                 CM_FRIEND_ADD.this.sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(CM_FRIEND_ADD.this.targetName, 1));
/*     */               } else {
/* 105 */                 if (activePlayer.getFriendList().isFull() || responder.getFriendList().isFull()) {
/*     */                   return;
/*     */                 }
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 112 */                 SocialService.makeFriends((Player)requester, responder);
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void denyRequest(Creature requester, Player responder) {
/* 120 */               CM_FRIEND_ADD.this.sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(CM_FRIEND_ADD.this.targetName, 4));
/*     */             }
/*     */           };
/*     */ 
/*     */         
/* 125 */         boolean requested = targetPlayer.getResponseRequester().putRequest(900841, responseHandler);
/*     */         
/* 127 */         if (!requested) {
/*     */           
/* 129 */           sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BUDDYLIST_BUSY);
/*     */         }
/*     */         else {
/*     */           
/* 133 */           if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.FRIEND)) {
/*     */             
/* 135 */             sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_FRIEND(targetPlayer.getName()));
/*     */             
/*     */             return;
/*     */           } 
/* 139 */           targetPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_QUESTION_WINDOW(900841, activePlayer.getObjectId(), new Object[] { activePlayer.getName() }));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_FRIEND_ADD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.AllianceService;
/*    */ import com.aionemu.gameserver.services.GroupService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.Util;
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
/*    */ public class CM_INVITE_TO_GROUP
/*    */   extends AionClientPacket
/*    */ {
/*    */   private String name;
/*    */   private int inviteType;
/*    */   
/*    */   public CM_INVITE_TO_GROUP(int opcode) {
/* 41 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 50 */     this.inviteType = readC();
/* 51 */     this.name = readS();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 60 */     String playerName = Util.convertName(this.name);
/*    */     
/* 62 */     Player inviter = ((AionConnection)getConnection()).getActivePlayer();
/* 63 */     Player invited = World.getInstance().findPlayer(playerName);
/*    */     
/* 65 */     if (invited != null) {
/*    */       
/* 67 */       if (invited.getPlayerSettings().isInDeniedStatus(DeniedStatus.GROUP)) {
/*    */         
/* 69 */         sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_INVITE_PARTY(invited.getName()));
/*    */         return;
/*    */       } 
/* 72 */       if (this.inviteType == 0) {
/* 73 */         GroupService.getInstance().invitePlayerToGroup(inviter, invited);
/* 74 */       } else if (this.inviteType == 10) {
/* 75 */         AllianceService.getInstance().invitePlayerToAlliance(inviter, invited);
/*    */       } else {
/* 77 */         PacketSendUtility.sendMessage(inviter, "You used an unknown invite type: " + this.inviteType);
/*    */       } 
/*    */     } else {
/* 80 */       inviter.getClientConnection().sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.PLAYER_IS_OFFLINE(this.name));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_INVITE_TO_GROUP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
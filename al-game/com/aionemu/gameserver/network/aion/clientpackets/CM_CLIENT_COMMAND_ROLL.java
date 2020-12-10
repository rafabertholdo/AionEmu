/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.commons.utils.Rnd;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class CM_CLIENT_COMMAND_ROLL
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int maxRoll;
/*    */   private int roll;
/*    */   
/*    */   public CM_CLIENT_COMMAND_ROLL(int opcode) {
/* 38 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 44 */     this.maxRoll = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 50 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 51 */     if (player == null) {
/*    */       return;
/*    */     }
/* 54 */     this.roll = Rnd.get(1, this.maxRoll);
/* 55 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1400126, new Object[] { Integer.valueOf(this.roll), Integer.valueOf(this.maxRoll) }));
/* 56 */     PacketSendUtility.broadcastPacket((VisibleObject)player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1400127, new Object[] { player.getName(), Integer.valueOf(this.roll), Integer.valueOf(this.maxRoll) }));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CLIENT_COMMAND_ROLL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
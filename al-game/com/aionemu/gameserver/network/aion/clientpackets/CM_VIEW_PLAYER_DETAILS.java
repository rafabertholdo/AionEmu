/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_VIEW_PLAYER_DETAILS;
/*    */ import com.aionemu.gameserver.world.World;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class CM_VIEW_PLAYER_DETAILS
/*    */   extends AionClientPacket
/*    */ {
/* 34 */   private static final Logger log = Logger.getLogger(CM_VIEW_PLAYER_DETAILS.class);
/*    */   
/*    */   private int targetObjectId;
/*    */ 
/*    */   
/*    */   public CM_VIEW_PLAYER_DETAILS(int opcode) {
/* 40 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 49 */     this.targetObjectId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 58 */     Player player = World.getInstance().findPlayer(this.targetObjectId);
/* 59 */     if (player == null) {
/*    */ 
/*    */       
/* 62 */       log.warn("CHECKPOINT: can't show player details for " + this.targetObjectId);
/*    */       
/*    */       return;
/*    */     } 
/* 66 */     if (player.getPlayerSettings().isInDeniedStatus(DeniedStatus.VEIW_DETAIL)) {
/*    */       
/* 68 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_WATCH(player.getName()));
/*    */       return;
/*    */     } 
/* 71 */     sendPacket((AionServerPacket)new SM_VIEW_PLAYER_DETAILS(this.targetObjectId, player.getEquipment().getEquippedItemsWithoutStigma()));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_VIEW_PLAYER_DETAILS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
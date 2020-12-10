/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Equipment;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
/*    */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
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
/*    */ public class CM_EQUIP_ITEM
/*    */   extends AionClientPacket
/*    */ {
/*    */   public int slotRead;
/*    */   public int itemUniqueId;
/*    */   public int action;
/*    */   
/*    */   public CM_EQUIP_ITEM(int opcode) {
/* 39 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 45 */     this.action = readC();
/* 46 */     this.slotRead = readD();
/* 47 */     this.itemUniqueId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 53 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 54 */     Equipment equipment = activePlayer.getEquipment();
/* 55 */     Item resultItem = null;
/*    */     
/* 57 */     if (!RestrictionsManager.canChangeEquip(activePlayer)) {
/*    */       return;
/*    */     }
/* 60 */     switch (this.action) {
/*    */       
/*    */       case 0:
/* 63 */         resultItem = equipment.equipItem(this.itemUniqueId, this.slotRead);
/*    */         break;
/*    */       case 1:
/* 66 */         resultItem = equipment.unEquipItem(this.itemUniqueId, this.slotRead);
/*    */         break;
/*    */       case 2:
/* 69 */         equipment.switchHands();
/*    */         break;
/*    */     } 
/*    */     
/* 73 */     if (resultItem != null || this.action == 2)
/*    */     {
/* 75 */       PacketSendUtility.broadcastPacket(activePlayer, (AionServerPacket)new SM_UPDATE_PLAYER_APPEARANCE(activePlayer.getObjectId(), equipment.getEquippedItemsWithoutStigma()), true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_EQUIP_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
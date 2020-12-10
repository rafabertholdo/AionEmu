/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
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
/*    */ public class CM_UI_SETTINGS
/*    */   extends AionClientPacket
/*    */ {
/*    */   int settingsType;
/*    */   byte[] data;
/*    */   int size;
/*    */   
/*    */   public CM_UI_SETTINGS(int opcode) {
/* 33 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 39 */     this.settingsType = readC();
/* 40 */     readH();
/* 41 */     this.size = readH();
/* 42 */     this.data = readB(getRemainingBytes());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 51 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 52 */     if (player == null) {
/*    */       return;
/*    */     }
/* 55 */     if (this.settingsType == 0) {
/*    */       
/* 57 */       player.getPlayerSettings().setUiSettings(this.data);
/*    */     }
/* 59 */     else if (this.settingsType == 1) {
/*    */       
/* 61 */       player.getPlayerSettings().setShortcuts(this.data);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_UI_SETTINGS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
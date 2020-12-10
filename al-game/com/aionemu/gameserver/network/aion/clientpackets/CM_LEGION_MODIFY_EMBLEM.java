/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.LegionService;
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
/*    */ public class CM_LEGION_MODIFY_EMBLEM
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int legionId;
/*    */   private int emblemId;
/*    */   private int red;
/*    */   private int green;
/*    */   private int blue;
/*    */   
/*    */   public CM_LEGION_MODIFY_EMBLEM(int opcode) {
/* 42 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 48 */     this.legionId = readD();
/* 49 */     this.emblemId = readH();
/* 50 */     readC();
/* 51 */     this.red = readC();
/* 52 */     this.green = readC();
/* 53 */     this.blue = readC();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 59 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 61 */     if (activePlayer.isLegionMember())
/* 62 */       LegionService.getInstance().storeLegionEmblem(activePlayer, this.legionId, this.emblemId, this.red, this.green, this.blue); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_LEGION_MODIFY_EMBLEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
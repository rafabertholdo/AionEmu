/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.CraftService;
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
/*    */ public class CM_CRAFT
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int unk;
/*    */   private int targetTemplateId;
/*    */   private int recipeId;
/*    */   private int targetObjId;
/*    */   
/*    */   public CM_CRAFT(int opcode) {
/* 40 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 49 */     this.unk = readC();
/* 50 */     this.targetTemplateId = readD();
/* 51 */     this.recipeId = readD();
/* 52 */     this.targetObjId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 58 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 60 */     if (player == null || !player.isSpawned()) {
/*    */       return;
/*    */     }
/*    */     
/* 64 */     if (player.getController().isInShutdownProgress()) {
/*    */       return;
/*    */     }
/* 67 */     CraftService.startCrafting(player, this.targetTemplateId, this.recipeId, this.targetObjId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CRAFT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.ItemService;
/*    */ import com.aionemu.gameserver.utils.MathUtil;
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
/*    */ public class CM_GODSTONE_SOCKET
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int npcId;
/*    */   private int weaponId;
/*    */   private int stoneId;
/*    */   
/*    */   public CM_GODSTONE_SOCKET(int opcode) {
/* 39 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 45 */     this.npcId = readD();
/* 46 */     this.weaponId = readD();
/* 47 */     this.stoneId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 53 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 55 */     Npc npc = (Npc)World.getInstance().findAionObject(this.npcId);
/* 56 */     if (npc == null) {
/*    */       return;
/*    */     }
/* 59 */     if (!MathUtil.isInRange((VisibleObject)activePlayer, (VisibleObject)npc, 15.0F)) {
/*    */       return;
/*    */     }
/* 62 */     ItemService.socketGodstone(activePlayer, this.weaponId, this.stoneId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GODSTONE_SOCKET.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
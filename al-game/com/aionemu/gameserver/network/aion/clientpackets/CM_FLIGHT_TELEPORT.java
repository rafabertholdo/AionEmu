/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CM_FLIGHT_TELEPORT
/*    */   extends AionClientPacket
/*    */ {
/*    */   float x;
/*    */   float y;
/*    */   float z;
/*    */   int distance;
/*    */   
/*    */   public CM_FLIGHT_TELEPORT(int opcode) {
/* 42 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 51 */     readD();
/* 52 */     this.x = readF();
/* 53 */     this.y = readF();
/* 54 */     this.z = readF();
/* 55 */     readC();
/* 56 */     this.distance = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 65 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 67 */     if (player != null && player.isInState(CreatureState.FLIGHT_TELEPORT)) {
/*    */       
/* 69 */       player.setFlightDistance(this.distance);
/* 70 */       World.getInstance().updatePosition((VisibleObject)player, this.x, this.y, this.z, (byte)0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_FLIGHT_TELEPORT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
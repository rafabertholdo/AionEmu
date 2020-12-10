/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Gatherable;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
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
/*    */ 
/*    */ public class CM_GATHER
/*    */   extends AionClientPacket
/*    */ {
/*    */   boolean isStartGather = false;
/*    */   
/*    */   public CM_GATHER(int opcode) {
/* 34 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 41 */     int action = readD();
/* 42 */     if (action == 0) {
/* 43 */       this.isStartGather = true;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 51 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 52 */     if (player != null) {
/*    */       
/* 54 */       VisibleObject target = player.getTarget();
/* 55 */       if (target != null && target instanceof Gatherable)
/*    */       {
/* 57 */         if (this.isStartGather) {
/*    */           
/* 59 */           ((Gatherable)target).getController().onStartUse(player);
/*    */         }
/*    */         else {
/*    */           
/* 63 */           ((Gatherable)target).getController().finishGathering(player);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GATHER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
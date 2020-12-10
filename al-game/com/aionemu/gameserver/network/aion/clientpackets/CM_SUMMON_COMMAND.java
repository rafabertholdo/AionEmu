/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.SummonController;
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ public class CM_SUMMON_COMMAND
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int mode;
/*    */   private int targetObjId;
/*    */   
/*    */   public CM_SUMMON_COMMAND(int opcode) {
/* 39 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 45 */     this.mode = readC();
/* 46 */     readD();
/* 47 */     readD();
/* 48 */     this.targetObjId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 54 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 55 */     Summon summon = activePlayer.getSummon();
/* 56 */     if (summon != null) {
/*    */       AionObject target;
/* 58 */       switch (this.mode) {
/*    */         
/*    */         case 0:
/* 61 */           target = World.getInstance().findAionObject(this.targetObjId);
/* 62 */           if (target != null && target instanceof com.aionemu.gameserver.model.gameobjects.Creature)
/*    */           {
/* 64 */             summon.getController().attackMode();
/*    */           }
/*    */           break;
/*    */         case 1:
/* 68 */           summon.getController().guardMode();
/*    */           break;
/*    */         case 2:
/* 71 */           summon.getController().restMode();
/*    */           break;
/*    */         case 3:
/* 74 */           summon.getController().release(SummonController.UnsummonType.COMMAND);
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SUMMON_COMMAND.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
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
/*    */ public class CM_SUMMON_ATTACK
/*    */   extends AionClientPacket
/*    */ {
/* 33 */   private static final Logger log = Logger.getLogger(CM_SUMMON_ATTACK.class);
/*    */   
/*    */   private int summonObjId;
/*    */   
/*    */   private int targetObjId;
/*    */   
/*    */   private int unk1;
/*    */   
/*    */   private int unk2;
/*    */   
/*    */   private int unk3;
/*    */ 
/*    */   
/*    */   public CM_SUMMON_ATTACK(int opcode) {
/* 47 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 53 */     this.summonObjId = readD();
/* 54 */     this.targetObjId = readD();
/* 55 */     this.unk1 = readC();
/* 56 */     this.unk2 = readH();
/* 57 */     this.unk3 = readC();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 64 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 65 */     if (activePlayer == null) {
/*    */       
/* 67 */       log.error("CM_SUMMON_ATTACK packet received but cannot get master player.");
/*    */       
/*    */       return;
/*    */     } 
/* 71 */     Summon summon = activePlayer.getSummon();
/*    */     
/* 73 */     if (summon == null) {
/*    */       
/* 75 */       log.error("CM_SUMMON_ATTACK packet received but cannot get summon.");
/*    */       
/*    */       return;
/*    */     } 
/* 79 */     Creature creature = (Creature)World.getInstance().findAionObject(this.targetObjId);
/* 80 */     summon.getController().attackTarget(creature);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SUMMON_ATTACK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
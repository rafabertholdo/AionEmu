/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
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
/*    */ 
/*    */ public class CM_SUMMON_CASTSPELL
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int summonObjId;
/*    */   private int targetObjId;
/*    */   private int skillId;
/*    */   private int skillLvl;
/*    */   private float unk;
/*    */   
/*    */   public CM_SUMMON_CASTSPELL(int opcode) {
/* 43 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 49 */     this.summonObjId = readD();
/* 50 */     this.skillId = readH();
/* 51 */     this.skillLvl = readC();
/* 52 */     this.targetObjId = readD();
/* 53 */     this.unk = readF();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 59 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 60 */     Summon summon = activePlayer.getSummon();
/*    */     
/* 62 */     if (summon == null) {
/*    */       return;
/*    */     }
/* 65 */     AionObject targetObject = World.getInstance().findAionObject(this.targetObjId);
/* 66 */     if (targetObject instanceof Creature)
/*    */     {
/* 68 */       summon.getController().useSkill(this.skillId, (Creature)targetObject);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SUMMON_CASTSPELL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
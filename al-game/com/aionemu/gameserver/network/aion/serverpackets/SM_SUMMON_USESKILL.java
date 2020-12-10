/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_SUMMON_USESKILL
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int summonId;
/*    */   private int skillId;
/*    */   private int skillLvl;
/*    */   private int targetId;
/*    */   
/*    */   public SM_SUMMON_USESKILL(int summonId, int skillId, int skillLvl, int targetId) {
/* 44 */     this.summonId = summonId;
/* 45 */     this.skillId = skillId;
/* 46 */     this.skillLvl = skillLvl;
/* 47 */     this.targetId = targetId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 53 */     writeD(buf, this.summonId);
/* 54 */     writeH(buf, this.skillId);
/* 55 */     writeC(buf, this.skillLvl);
/* 56 */     writeD(buf, this.targetId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SUMMON_USESKILL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
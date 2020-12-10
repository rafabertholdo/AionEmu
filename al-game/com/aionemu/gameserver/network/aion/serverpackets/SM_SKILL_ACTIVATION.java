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
/*    */ public class SM_SKILL_ACTIVATION
/*    */   extends AionServerPacket
/*    */ {
/*    */   private boolean isActive;
/*    */   private int unk;
/*    */   private int skillId;
/*    */   
/*    */   public SM_SKILL_ACTIVATION(int skillId, boolean isActive) {
/* 42 */     this.skillId = skillId;
/* 43 */     this.isActive = isActive;
/* 44 */     this.unk = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SM_SKILL_ACTIVATION(int skillId) {
/* 53 */     this.skillId = skillId;
/* 54 */     this.isActive = true;
/* 55 */     this.unk = 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 64 */     writeH(buf, this.skillId);
/* 65 */     writeD(buf, this.unk);
/* 66 */     writeC(buf, this.isActive ? 1 : 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SKILL_ACTIVATION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
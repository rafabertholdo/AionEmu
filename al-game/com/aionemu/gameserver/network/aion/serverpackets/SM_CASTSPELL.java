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
/*    */ public class SM_CASTSPELL
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int attackerObjectId;
/*    */   private int spellId;
/*    */   private int level;
/*    */   private int targetType;
/*    */   private int duration;
/*    */   private int targetObjectId;
/*    */   private float x;
/*    */   private float y;
/*    */   private float z;
/*    */   
/*    */   public SM_CASTSPELL(int attackerObjectId, int spellId, int level, int targetType, int targetObjectId, int duration) {
/* 44 */     this.attackerObjectId = attackerObjectId;
/* 45 */     this.spellId = spellId;
/* 46 */     this.level = level;
/* 47 */     this.targetType = targetType;
/* 48 */     this.targetObjectId = targetObjectId;
/* 49 */     this.duration = duration;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_CASTSPELL(int attackerObjectId, int spellId, int level, int targetType, float x, float y, float z, int duration) {
/* 54 */     this(attackerObjectId, spellId, level, targetType, 0, duration);
/* 55 */     this.x = x;
/* 56 */     this.y = y;
/* 57 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 66 */     writeD(buf, this.attackerObjectId);
/* 67 */     writeH(buf, this.spellId);
/* 68 */     writeC(buf, this.level);
/*    */     
/* 70 */     writeC(buf, this.targetType);
/* 71 */     switch (this.targetType) {
/*    */       
/*    */       case 0:
/* 74 */         writeD(buf, this.targetObjectId);
/*    */         break;
/*    */       case 1:
/* 77 */         writeF(buf, this.x);
/* 78 */         writeF(buf, this.y);
/* 79 */         writeF(buf, this.z);
/*    */         break;
/*    */     } 
/*    */     
/* 83 */     writeH(buf, this.duration);
/* 84 */     writeD(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CASTSPELL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
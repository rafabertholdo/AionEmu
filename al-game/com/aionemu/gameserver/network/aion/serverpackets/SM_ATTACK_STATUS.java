/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
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
/*    */ public class SM_ATTACK_STATUS
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Creature creature;
/*    */   private TYPE type;
/*    */   private int skillId;
/*    */   private int value;
/*    */   
/*    */   public enum TYPE
/*    */   {
/* 41 */     NATURAL_HP(3),
/* 42 */     REGULAR(5),
/* 43 */     DAMAGE(7),
/* 44 */     HP(7),
/* 45 */     MP(21),
/* 46 */     NATURAL_MP(22),
/* 47 */     FP_RINGS(23),
/* 48 */     FP(25),
/* 49 */     NATURAL_FP(26);
/*    */     
/*    */     private int value;
/*    */ 
/*    */     
/*    */     TYPE(int value) {
/* 55 */       this.value = value;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getValue() {
/* 60 */       return this.value;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_ATTACK_STATUS(Creature creature, TYPE type, int skillId, int value) {
/* 66 */     this.creature = creature;
/* 67 */     this.type = type;
/* 68 */     this.skillId = skillId;
/* 69 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_ATTACK_STATUS(Creature creature, int value) {
/* 74 */     this.creature = creature;
/* 75 */     this.type = TYPE.REGULAR;
/* 76 */     this.skillId = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 86 */     writeD(buf, this.creature.getObjectId());
/* 87 */     switch (this.type) {
/*    */       
/*    */       case DAMAGE:
/* 90 */         writeD(buf, -this.value);
/*    */         break;
/*    */       default:
/* 93 */         writeD(buf, this.value); break;
/*    */     } 
/* 95 */     writeC(buf, this.type.getValue());
/* 96 */     writeC(buf, this.creature.getLifeStats().getHpPercentage());
/* 97 */     writeH(buf, this.skillId);
/* 98 */     writeH(buf, 166);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ATTACK_STATUS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ public class SM_GROUP_LOOT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int groupId;
/*    */   private int unk1;
/*    */   private int unk2;
/*    */   private int itemId;
/*    */   private int unk3;
/*    */   private int lootCorpseId;
/*    */   private int distributionId;
/*    */   private int playerId;
/*    */   private int luck;
/*    */   
/*    */   public SM_GROUP_LOOT(int groupId, int itemId, int lootCorpseId, int distributionId) {
/* 44 */     this.groupId = groupId;
/* 45 */     this.unk1 = 1;
/* 46 */     this.unk2 = 1;
/* 47 */     this.itemId = itemId;
/* 48 */     this.unk3 = 0;
/* 49 */     this.lootCorpseId = lootCorpseId;
/* 50 */     this.distributionId = distributionId;
/* 51 */     this.playerId = 0;
/* 52 */     this.luck = 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 61 */     writeD(buf, this.groupId);
/* 62 */     writeD(buf, this.unk1);
/* 63 */     writeD(buf, this.unk2);
/* 64 */     writeD(buf, this.itemId);
/* 65 */     writeC(buf, this.unk3);
/* 66 */     writeD(buf, this.lootCorpseId);
/* 67 */     writeC(buf, this.distributionId);
/* 68 */     writeD(buf, this.playerId);
/* 69 */     writeD(buf, this.luck);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_GROUP_LOOT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
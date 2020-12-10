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
/*    */ public class SM_SHOW_NPC_ON_MAP
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int npcid;
/*    */   private int worldid;
/*    */   private float x;
/*    */   private float y;
/*    */   private float z;
/*    */   
/*    */   public SM_SHOW_NPC_ON_MAP(int npcid, int worldid, float x, float y, float z) {
/* 34 */     this.npcid = npcid;
/* 35 */     this.worldid = worldid;
/* 36 */     this.x = x;
/* 37 */     this.y = y;
/* 38 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 44 */     writeD(buf, this.npcid);
/* 45 */     writeD(buf, this.worldid);
/* 46 */     writeD(buf, this.worldid);
/* 47 */     writeF(buf, this.x);
/* 48 */     writeF(buf, this.y);
/* 49 */     writeF(buf, this.z);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SHOW_NPC_ON_MAP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
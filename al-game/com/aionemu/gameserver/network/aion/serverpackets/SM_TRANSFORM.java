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
/*    */ public class SM_TRANSFORM
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Creature creature;
/*    */   private int state;
/*    */   
/*    */   public SM_TRANSFORM(Creature creature) {
/* 35 */     this.creature = creature;
/* 36 */     this.state = creature.getState();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 45 */     writeD(buf, this.creature.getObjectId());
/* 46 */     writeD(buf, this.creature.getTransformedModelId());
/* 47 */     writeH(buf, this.state);
/* 48 */     writeF(buf, 0.55F);
/* 49 */     writeF(buf, 1.5F);
/* 50 */     writeC(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TRANSFORM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ public class SM_LEGION_UPDATE_SELF_INTRO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private String selfintro;
/*    */   private int playerObjId;
/*    */   
/*    */   public SM_LEGION_UPDATE_SELF_INTRO(int playerObjId, String selfintro) {
/* 36 */     this.selfintro = selfintro;
/* 37 */     this.playerObjId = playerObjId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 43 */     writeD(buf, this.playerObjId);
/* 44 */     writeS(buf, this.selfintro);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_UPDATE_SELF_INTRO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
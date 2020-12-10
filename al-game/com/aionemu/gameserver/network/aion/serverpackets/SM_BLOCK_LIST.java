/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.BlockList;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
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
/*    */ public class SM_BLOCK_LIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 37 */     BlockList list = con.getActivePlayer().getBlockList();
/* 38 */     writeH(buf, list.getSize());
/* 39 */     writeC(buf, 0);
/* 40 */     for (BlockedPlayer player : list.getBlockedList()) {
/*    */       
/* 42 */       writeS(buf, player.getName());
/* 43 */       writeS(buf, player.getReason());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BLOCK_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
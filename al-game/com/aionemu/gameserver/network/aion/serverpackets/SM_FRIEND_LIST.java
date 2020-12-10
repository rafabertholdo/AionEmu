/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Friend;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.FriendList;
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
/*    */ public class SM_FRIEND_LIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 37 */     FriendList list = con.getActivePlayer().getFriendList();
/*    */     
/* 39 */     writeH(buf, 0 - list.getSize());
/* 40 */     writeC(buf, 0);
/*    */     
/* 42 */     for (Friend friend : list) {
/*    */       
/* 44 */       writeS(buf, friend.getName());
/* 45 */       writeD(buf, friend.getLevel());
/* 46 */       writeD(buf, friend.getPlayerClass().getClassId());
/* 47 */       writeC(buf, 1);
/* 48 */       writeD(buf, friend.getMapId());
/* 49 */       writeD(buf, friend.getLastOnlineTime());
/* 50 */       writeS(buf, friend.getNote());
/* 51 */       writeC(buf, friend.getStatus().getIntValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_FRIEND_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
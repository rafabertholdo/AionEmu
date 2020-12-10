/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Friend;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class SM_FRIEND_UPDATE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int friendObjId;
/* 36 */   private static Logger log = Logger.getLogger(SM_FRIEND_UPDATE.class);
/*    */   
/*    */   public SM_FRIEND_UPDATE(int friendObjId) {
/* 39 */     this.friendObjId = friendObjId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 48 */     Friend f = con.getActivePlayer().getFriendList().getFriend(this.friendObjId);
/* 49 */     if (f == null) {
/* 50 */       log.debug("Attempted to update friend list status of " + this.friendObjId + " for " + con.getActivePlayer().getName() + " - object ID not found on friend list");
/*    */     } else {
/*    */       
/* 53 */       writeS(buf, f.getName());
/* 54 */       writeD(buf, f.getLevel());
/* 55 */       writeD(buf, f.getPlayerClass().getClassId());
/* 56 */       writeC(buf, f.isOnline() ? 1 : 0);
/* 57 */       writeD(buf, f.getMapId());
/* 58 */       writeD(buf, f.getLastOnlineTime());
/* 59 */       writeS(buf, f.getNote());
/* 60 */       writeC(buf, f.getStatus().getIntValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_FRIEND_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
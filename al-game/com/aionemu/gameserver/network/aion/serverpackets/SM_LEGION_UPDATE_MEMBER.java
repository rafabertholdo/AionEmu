/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ public class SM_LEGION_UPDATE_MEMBER
/*    */   extends AionServerPacket
/*    */ {
/*    */   private static final int OFFLINE = 0;
/*    */   private static final int ONLINE = 1;
/*    */   private Player player;
/*    */   private int msgId;
/*    */   private String text;
/*    */   
/*    */   public SM_LEGION_UPDATE_MEMBER(Player player, int msgId, String text) {
/* 40 */     this.player = player;
/* 41 */     this.msgId = msgId;
/* 42 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 48 */     writeD(buf, this.player.getObjectId());
/* 49 */     writeC(buf, this.player.getLegionMember().getRank().getRankId());
/* 50 */     writeC(buf, this.player.getCommonData().getPlayerClass().getClassId());
/* 51 */     writeC(buf, this.player.getLevel());
/* 52 */     writeD(buf, this.player.getPosition().getMapId());
/* 53 */     writeC(buf, this.player.isOnline() ? 1 : 0);
/* 54 */     writeD(buf, this.player.getLastOnline());
/* 55 */     writeD(buf, this.msgId);
/* 56 */     writeS(buf, this.text);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_UPDATE_MEMBER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
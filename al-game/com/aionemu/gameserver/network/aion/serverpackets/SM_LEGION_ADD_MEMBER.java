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
/*    */ public class SM_LEGION_ADD_MEMBER
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Player player;
/*    */   private boolean isMember;
/*    */   private int msgId;
/*    */   private String text;
/*    */   
/*    */   public SM_LEGION_ADD_MEMBER(Player player, boolean isMember, int msgId, String text) {
/* 39 */     this.player = player;
/* 40 */     this.isMember = isMember;
/* 41 */     this.msgId = msgId;
/* 42 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 48 */     writeD(buf, this.player.getObjectId());
/* 49 */     writeS(buf, this.player.getName());
/* 50 */     writeC(buf, this.player.getLegionMember().getRank().getRankId());
/* 51 */     writeC(buf, this.isMember ? 1 : 0);
/* 52 */     writeC(buf, this.player.getCommonData().getPlayerClass().getClassId());
/* 53 */     writeC(buf, this.player.getLevel());
/* 54 */     writeD(buf, this.player.getPosition().getMapId());
/* 55 */     writeD(buf, this.msgId);
/* 56 */     writeS(buf, this.text);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_ADD_MEMBER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
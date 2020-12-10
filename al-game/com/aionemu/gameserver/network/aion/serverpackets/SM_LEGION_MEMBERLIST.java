/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.legion.LegionMemberEx;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_LEGION_MEMBERLIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   private static final int OFFLINE = 0;
/*    */   private static final int ONLINE = 1;
/*    */   private ArrayList<LegionMemberEx> legionMembers;
/*    */   
/*    */   public SM_LEGION_MEMBERLIST(ArrayList<LegionMemberEx> legionMembers) {
/* 45 */     this.legionMembers = legionMembers;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 51 */     writeC(buf, 1);
/* 52 */     writeH(buf, 65536 - this.legionMembers.size());
/* 53 */     for (LegionMemberEx legionMember : this.legionMembers) {
/*    */       
/* 55 */       writeD(buf, legionMember.getObjectId());
/* 56 */       writeS(buf, legionMember.getName());
/* 57 */       writeC(buf, legionMember.getPlayerClass().getClassId());
/* 58 */       writeD(buf, legionMember.getLevel());
/* 59 */       writeC(buf, legionMember.getRank().getRankId());
/* 60 */       writeD(buf, legionMember.getWorldId());
/* 61 */       writeC(buf, legionMember.isOnline() ? 1 : 0);
/* 62 */       writeS(buf, legionMember.getSelfIntro());
/* 63 */       writeS(buf, legionMember.getNickname());
/* 64 */       writeD(buf, legionMember.getLastOnline());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_MEMBERLIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
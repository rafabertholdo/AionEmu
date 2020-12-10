/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_LIST;
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
/*    */ public class CM_SHOW_FRIENDLIST
/*    */   extends AionClientPacket
/*    */ {
/*    */   public CM_SHOW_FRIENDLIST(int opcode) {
/* 32 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 51 */     sendPacket((AionServerPacket)new SM_FRIEND_LIST());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SHOW_FRIENDLIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
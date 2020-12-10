/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.account.Account;
/*    */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.PlayerInfo;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_CHARACTER_LIST
/*    */   extends PlayerInfo
/*    */ {
/*    */   private final int playOk2;
/*    */   
/*    */   public SM_CHARACTER_LIST(int playOk2) {
/* 44 */     this.playOk2 = playOk2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 53 */     writeD(buf, this.playOk2);
/*    */     
/* 55 */     Account account = con.getAccount();
/* 56 */     writeC(buf, account.size());
/*    */     
/* 58 */     for (PlayerAccountData playerData : account.getSortedAccountsList()) {
/*    */       
/* 60 */       writePlayerInfo(buf, playerData);
/* 61 */       writeB(buf, new byte[14]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CHARACTER_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
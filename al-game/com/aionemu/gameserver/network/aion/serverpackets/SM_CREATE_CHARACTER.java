/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
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
/*    */ public class SM_CREATE_CHARACTER
/*    */   extends PlayerInfo
/*    */ {
/*    */   public static final int RESPONSE_OK = 0;
/*    */   public static final int FAILED_TO_CREATE_THE_CHARACTER = 1;
/*    */   public static final int RESPONSE_DB_ERROR = 2;
/*    */   public static final int RESPONSE_SERVER_LIMIT_EXCEEDED = 4;
/*    */   public static final int RESPONSE_INVALID_NAME = 5;
/*    */   public static final int RESPONSE_FORBIDDEN_CHAR_NAME = 9;
/*    */   public static final int RESPONSE_NAME_ALREADY_USED = 10;
/*    */   public static final int RESPONSE_NAME_RESERVED = 11;
/*    */   public static final int RESPONSE_OTHER_RACE = 12;
/*    */   private final int responseCode;
/*    */   private final PlayerAccountData player;
/*    */   
/*    */   public SM_CREATE_CHARACTER(PlayerAccountData accPlData, int responseCode) {
/* 75 */     this.player = accPlData;
/* 76 */     this.responseCode = responseCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 85 */     writeD(buf, this.responseCode);
/*    */     
/* 87 */     if (this.responseCode == 0) {
/*    */       
/* 89 */       writePlayerInfo(buf, this.player);
/*    */     }
/*    */     else {
/*    */       
/* 93 */       writeB(buf, new byte[448]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CREATE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
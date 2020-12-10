/*    */ package com.aionemu.gameserver.network.loginserver.serverpackets;
/*    */ 
/*    */ import com.aionemu.commons.network.IPRange;
/*    */ import com.aionemu.gameserver.configs.network.IPConfig;
/*    */ import com.aionemu.gameserver.configs.network.NetworkConfig;
/*    */ import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
/*    */ import com.aionemu.gameserver.network.loginserver.LsServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
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
/*    */ public class SM_GS_AUTH
/*    */   extends LsServerPacket
/*    */ {
/*    */   public SM_GS_AUTH() {
/* 42 */     super(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
/* 51 */     writeC(buf, getOpcode());
/* 52 */     writeC(buf, NetworkConfig.GAMESERVER_ID);
/* 53 */     writeC(buf, (IPConfig.getDefaultAddress()).length);
/* 54 */     writeB(buf, IPConfig.getDefaultAddress());
/*    */     
/* 56 */     List<IPRange> ranges = IPConfig.getRanges();
/* 57 */     int size = ranges.size();
/* 58 */     writeD(buf, size);
/* 59 */     for (int i = 0; i < size; i++) {
/*    */       
/* 61 */       IPRange ipRange = ranges.get(i);
/* 62 */       byte[] min = ipRange.getMinAsByteArray();
/* 63 */       byte[] max = ipRange.getMaxAsByteArray();
/* 64 */       writeC(buf, min.length);
/* 65 */       writeB(buf, min);
/* 66 */       writeC(buf, max.length);
/* 67 */       writeB(buf, max);
/* 68 */       writeC(buf, (ipRange.getAddress()).length);
/* 69 */       writeB(buf, ipRange.getAddress());
/*    */     } 
/*    */     
/* 72 */     writeH(buf, NetworkConfig.GAME_PORT);
/* 73 */     writeD(buf, NetworkConfig.MAX_ONLINE_PLAYERS);
/* 74 */     writeS(buf, NetworkConfig.LOGIN_PASSWORD);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\serverpackets\SM_GS_AUTH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
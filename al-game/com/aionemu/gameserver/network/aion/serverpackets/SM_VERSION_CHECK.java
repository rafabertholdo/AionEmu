/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.main.GSConfig;
/*    */ import com.aionemu.gameserver.configs.network.NetworkConfig;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.services.ChatService;
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
/*    */ public class SM_VERSION_CHECK
/*    */   extends AionServerPacket
/*    */ {
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 48 */     writeC(buf, 0);
/* 49 */     writeC(buf, NetworkConfig.GAMESERVER_ID);
/* 50 */     writeD(buf, 100525);
/* 51 */     writeD(buf, 100518);
/* 52 */     writeD(buf, 0);
/* 53 */     writeD(buf, 100504);
/* 54 */     writeD(buf, 1278504349);
/* 55 */     writeC(buf, 0);
/* 56 */     writeC(buf, GSConfig.SERVER_COUNTRY_CODE);
/* 57 */     writeC(buf, 0);
/* 58 */     writeC(buf, GSConfig.SERVER_MODE);
/* 59 */     writeD(buf, (int)(System.currentTimeMillis() / 1000L));
/* 60 */     writeH(buf, 350);
/* 61 */     writeH(buf, 2561);
/* 62 */     writeH(buf, 2561);
/* 63 */     writeH(buf, 522);
/* 64 */     writeC(buf, 0);
/* 65 */     writeC(buf, 1);
/* 66 */     writeC(buf, 0);
/* 67 */     writeC(buf, 0);
/* 68 */     writeB(buf, ChatService.getIp());
/* 69 */     writeH(buf, ChatService.getPort());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_VERSION_CHECK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
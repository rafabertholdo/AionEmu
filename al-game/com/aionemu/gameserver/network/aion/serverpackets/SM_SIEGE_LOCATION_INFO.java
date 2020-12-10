/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.main.SiegeConfig;
/*    */ import com.aionemu.gameserver.model.siege.SiegeLocation;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.services.SiegeService;
/*    */ import java.nio.ByteBuffer;
/*    */ import javolution.util.FastMap;
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
/*    */ public class SM_SIEGE_LOCATION_INFO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int infoType;
/*    */   private FastMap<Integer, SiegeLocation> locations;
/*    */   
/*    */   public SM_SIEGE_LOCATION_INFO() {
/* 48 */     this.infoType = 0;
/* 49 */     this.locations = SiegeService.getInstance().getSiegeLocations();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SM_SIEGE_LOCATION_INFO(SiegeLocation loc) {
/* 57 */     this.infoType = 1;
/* 58 */     this.locations = new FastMap();
/* 59 */     this.locations.put(Integer.valueOf(loc.getLocationId()), loc);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 65 */     if (!SiegeConfig.SIEGE_ENABLED) {
/*    */ 
/*    */       
/* 68 */       writeC(buf, 0);
/* 69 */       writeH(buf, 0);
/*    */       
/*    */       return;
/*    */     } 
/* 73 */     writeC(buf, this.infoType);
/* 74 */     writeH(buf, this.locations.size());
/* 75 */     FastMap.Entry<Integer, SiegeLocation> e = this.locations.head(), end = this.locations.tail();
/* 76 */     while ((e = e.getNext()) != end) {
/*    */       
/* 78 */       SiegeLocation sLoc = (SiegeLocation)e.getValue();
/*    */       
/* 80 */       writeD(buf, sLoc.getLocationId());
/* 81 */       writeD(buf, sLoc.getLegionId());
/*    */       
/* 83 */       writeD(buf, 0);
/* 84 */       writeD(buf, 0);
/*    */       
/* 86 */       writeC(buf, sLoc.getRace().getRaceId());
/*    */ 
/*    */       
/* 89 */       writeC(buf, sLoc.isVulnerable() ? 2 : 0);
/*    */ 
/*    */       
/* 92 */       writeC(buf, 1);
/*    */ 
/*    */       
/* 95 */       writeC(buf, sLoc.getNextState());
/*    */       
/* 97 */       writeD(buf, 0);
/* 98 */       writeD(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SIEGE_LOCATION_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class SM_PLAYER_SEARCH
/*    */   extends AionServerPacket
/*    */ {
/* 37 */   private static final Logger log = Logger.getLogger(SM_PLAYER_SEARCH.class);
/*    */ 
/*    */ 
/*    */   
/*    */   private List<Player> players;
/*    */ 
/*    */ 
/*    */   
/*    */   private int region;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SM_PLAYER_SEARCH(List<Player> players, int region) {
/* 51 */     this.players = new ArrayList<Player>(players);
/* 52 */     this.region = region;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 61 */     writeH(buf, this.players.size());
/* 62 */     for (Player player : this.players) {
/*    */       
/* 64 */       if (player.getActiveRegion() == null)
/*    */       {
/* 66 */         log.warn("CHECKPOINT: null active region for " + player.getObjectId() + "-" + player.getX() + "-" + player.getY() + "-" + player.getZ());
/*    */       }
/*    */       
/* 69 */       writeD(buf, (player.getActiveRegion() == null) ? this.region : player.getActiveRegion().getMapId().intValue());
/* 70 */       writeF(buf, player.getPosition().getX());
/* 71 */       writeF(buf, player.getPosition().getY());
/* 72 */       writeF(buf, player.getPosition().getZ());
/* 73 */       writeC(buf, player.getPlayerClass().getClassId());
/* 74 */       writeC(buf, player.getGender().getGenderId());
/* 75 */       writeC(buf, player.getLevel());
/*    */       
/* 77 */       writeC(buf, player.isLookingForGroup() ? 2 : 0);
/* 78 */       writeS(buf, player.getName());
/* 79 */       byte[] unknown = new byte[44 - player.getName().length() * 2 + 2];
/* 80 */       writeB(buf, unknown);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_SEARCH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
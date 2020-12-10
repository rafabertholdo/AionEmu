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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_CUBE_UPDATE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Player player;
/*    */   private int cubeType;
/*    */   private int advancedSlots;
/*    */   
/*    */   public SM_CUBE_UPDATE(Player player, int cubeType, int advancedSlots) {
/* 43 */     this.player = player;
/* 44 */     this.cubeType = cubeType;
/* 45 */     this.advancedSlots = advancedSlots;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_CUBE_UPDATE(Player player, int cubeType) {
/* 50 */     this.player = player;
/* 51 */     this.cubeType = cubeType;
/* 52 */     this.advancedSlots = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 61 */     writeC(buf, this.cubeType);
/* 62 */     writeC(buf, this.advancedSlots);
/* 63 */     switch (this.cubeType) {
/*    */       
/*    */       case 0:
/* 66 */         writeD(buf, this.player.getInventory().size());
/* 67 */         writeC(buf, this.player.getCubeSize());
/* 68 */         writeC(buf, 0);
/* 69 */         writeC(buf, 0);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CUBE_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
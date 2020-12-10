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
/*    */ public class SM_MANTRA_EFFECT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Player player;
/*    */   private int subEffectId;
/*    */   
/*    */   public SM_MANTRA_EFFECT(Player player, int subEffectId) {
/* 35 */     this.player = player;
/* 36 */     this.subEffectId = subEffectId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 45 */     writeD(buf, 0);
/* 46 */     writeD(buf, this.player.getObjectId());
/* 47 */     writeH(buf, this.subEffectId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_MANTRA_EFFECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
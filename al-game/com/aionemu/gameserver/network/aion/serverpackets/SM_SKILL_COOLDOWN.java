/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Map;
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
/*    */ public class SM_SKILL_COOLDOWN
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Map<Integer, Long> cooldowns;
/*    */   
/*    */   public SM_SKILL_COOLDOWN(Map<Integer, Long> cooldowns) {
/* 35 */     this.cooldowns = cooldowns;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 44 */     writeH(buf, this.cooldowns.size());
/* 45 */     long currentTime = System.currentTimeMillis();
/* 46 */     for (Map.Entry<Integer, Long> entry : this.cooldowns.entrySet()) {
/*    */       
/* 48 */       writeH(buf, ((Integer)entry.getKey()).intValue());
/* 49 */       int left = Math.round((float)((((Long)entry.getValue()).longValue() - currentTime) / 1000L));
/* 50 */       writeD(buf, (left > 0) ? left : 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SKILL_COOLDOWN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
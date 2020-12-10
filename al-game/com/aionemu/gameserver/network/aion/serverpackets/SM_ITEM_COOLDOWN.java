/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.items.ItemCooldown;
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
/*    */ public class SM_ITEM_COOLDOWN
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Map<Integer, ItemCooldown> cooldowns;
/*    */   
/*    */   public SM_ITEM_COOLDOWN(Map<Integer, ItemCooldown> cooldowns) {
/* 36 */     this.cooldowns = cooldowns;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 45 */     writeH(buf, this.cooldowns.size());
/* 46 */     long currentTime = System.currentTimeMillis();
/* 47 */     for (Map.Entry<Integer, ItemCooldown> entry : this.cooldowns.entrySet()) {
/*    */       
/* 49 */       writeH(buf, ((Integer)entry.getKey()).intValue());
/* 50 */       int left = Math.round((float)((((ItemCooldown)entry.getValue()).getReuseTime() - currentTime) / 1000L));
/* 51 */       writeD(buf, (left > 0) ? left : 0);
/* 52 */       writeD(buf, ((ItemCooldown)entry.getValue()).getUseDelay());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ITEM_COOLDOWN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Calendar;
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
/*    */ public class SM_BROKER_REGISTERED_LIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   List<BrokerItem> registeredItems;
/*    */   
/*    */   public SM_BROKER_REGISTERED_LIST(List<BrokerItem> items) {
/* 38 */     this.registeredItems = items;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 44 */     writeD(buf, 0);
/* 45 */     writeH(buf, this.registeredItems.size());
/* 46 */     for (BrokerItem item : this.registeredItems) {
/*    */       
/* 48 */       writeD(buf, item.getItemUniqueId());
/* 49 */       writeD(buf, item.getItemId());
/* 50 */       writeQ(buf, item.getPrice());
/* 51 */       writeQ(buf, item.getItem().getItemCount());
/* 52 */       writeQ(buf, item.getItem().getItemCount());
/* 53 */       Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
/* 54 */       int daysLeft = Math.round((float)((item.getExpireTime().getTime() - currentTime.getTime()) / 86400000L));
/* 55 */       writeH(buf, daysLeft);
/* 56 */       writeC(buf, 0);
/* 57 */       writeD(buf, item.getItemId());
/* 58 */       writeD(buf, 0);
/* 59 */       writeD(buf, 0);
/* 60 */       writeD(buf, 0);
/* 61 */       writeD(buf, 0);
/* 62 */       writeD(buf, 0);
/* 63 */       writeD(buf, 0);
/* 64 */       writeD(buf, 0);
/* 65 */       writeD(buf, 0);
/* 66 */       writeH(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BROKER_REGISTERED_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
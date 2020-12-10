/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
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
/*    */ public class SM_BROKER_REGISTRATION_SERVICE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private BrokerItem itemForRegistration;
/*    */   private int message;
/*    */   
/*    */   public SM_BROKER_REGISTRATION_SERVICE(BrokerItem item) {
/* 36 */     this.message = 0;
/* 37 */     this.itemForRegistration = item;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_BROKER_REGISTRATION_SERVICE(int message) {
/* 42 */     this.message = message;
/* 43 */     this.itemForRegistration = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 49 */     writeH(buf, this.message);
/* 50 */     if (this.message == 0) {
/*    */       
/* 52 */       writeD(buf, this.itemForRegistration.getItemUniqueId());
/* 53 */       writeD(buf, this.itemForRegistration.getItemId());
/* 54 */       writeQ(buf, this.itemForRegistration.getPrice());
/* 55 */       writeQ(buf, this.itemForRegistration.getItem().getItemCount());
/* 56 */       writeQ(buf, this.itemForRegistration.getItem().getItemCount());
/* 57 */       writeH(buf, 8);
/* 58 */       writeC(buf, 0);
/* 59 */       writeD(buf, this.itemForRegistration.getItemId());
/* 60 */       writeD(buf, 0);
/* 61 */       writeD(buf, 0);
/* 62 */       writeD(buf, 0);
/* 63 */       writeD(buf, 0);
/* 64 */       writeD(buf, 0);
/* 65 */       writeD(buf, 0);
/* 66 */       writeD(buf, 0);
/* 67 */       writeD(buf, 0);
/* 68 */       writeH(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BROKER_REGISTRATION_SERVICE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
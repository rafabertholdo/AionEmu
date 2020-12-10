/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.legion.LegionHistory;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Collection;
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
/*    */ public class SM_LEGION_TABS
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int page;
/*    */   private Collection<LegionHistory> legionHistory;
/*    */   
/*    */   public SM_LEGION_TABS(Collection<LegionHistory> legionHistory) {
/* 36 */     this.legionHistory = legionHistory;
/* 37 */     this.page = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_LEGION_TABS(Collection<LegionHistory> legionHistory, int page) {
/* 42 */     this.legionHistory = legionHistory;
/* 43 */     this.page = page;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 52 */     if (this.legionHistory.size() < this.page * 8) {
/*    */       return;
/*    */     }
/*    */     
/* 56 */     int hisSize = this.legionHistory.size() - this.page * 8;
/*    */     
/* 58 */     if (this.page == 0 && this.legionHistory.size() > 8)
/* 59 */       hisSize = 8; 
/* 60 */     if (this.page == 1 && this.legionHistory.size() > 16)
/* 61 */       hisSize = 8; 
/* 62 */     if (this.page == 2 && this.legionHistory.size() > 24) {
/* 63 */       hisSize = 8;
/*    */     }
/* 65 */     writeD(buf, 18);
/* 66 */     writeD(buf, this.page);
/* 67 */     writeD(buf, hisSize);
/*    */     
/* 69 */     int i = 0;
/* 70 */     for (LegionHistory history : this.legionHistory) {
/*    */       
/* 72 */       if (i >= this.page * 8 && i <= 8 + this.page * 8) {
/*    */         
/* 74 */         writeD(buf, (int)(history.getTime().getTime() / 1000L));
/* 75 */         writeC(buf, history.getLegionHistoryType().getHistoryId());
/* 76 */         writeC(buf, 0);
/* 77 */         if (history.getName().length() > 0) {
/*    */           
/* 79 */           writeS(buf, history.getName());
/* 80 */           int size = 134 - history.getName().length() * 2 + 2;
/* 81 */           writeB(buf, new byte[size]);
/*    */         } else {
/*    */           
/* 84 */           writeB(buf, new byte[134]);
/*    */         } 
/* 86 */       }  i++;
/* 87 */       if (i >= 8 + this.page * 8)
/*    */         break; 
/*    */     } 
/* 90 */     writeH(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_TABS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
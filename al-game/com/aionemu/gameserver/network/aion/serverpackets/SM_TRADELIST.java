/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.templates.TradeListTemplate;
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
/*    */ public class SM_TRADELIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int targetObjectId;
/*    */   private TradeListTemplate tlist;
/*    */   private int buyPriceModifier;
/*    */   
/*    */   public SM_TRADELIST(Npc npc, TradeListTemplate tlist, int buyPriceModifier) {
/* 41 */     this.targetObjectId = npc.getObjectId();
/* 42 */     this.tlist = tlist;
/* 43 */     this.buyPriceModifier = buyPriceModifier;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 49 */     if (this.tlist != null && this.tlist.getNpcId() != 0 && this.tlist.getCount() != 0) {
/*    */       
/* 51 */       writeD(buf, this.targetObjectId);
/* 52 */       writeC(buf, this.tlist.isAbyss() ? 2 : 1);
/* 53 */       writeD(buf, this.buyPriceModifier);
/* 54 */       writeH(buf, this.tlist.getCount());
/* 55 */       for (TradeListTemplate.TradeTab tradeTabl : this.tlist.getTradeTablist())
/*    */       {
/* 57 */         writeD(buf, tradeTabl.getId());
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TRADELIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
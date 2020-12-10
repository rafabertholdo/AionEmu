/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SM_VIEW_PLAYER_DETAILS
/*    */   extends AionServerPacket
/*    */ {
/*    */   private List<Item> items;
/*    */   private int size;
/*    */   private int targetObjId;
/*    */   
/*    */   public SM_VIEW_PLAYER_DETAILS(int targetObjId, List<Item> items) {
/* 38 */     this.items = items;
/* 39 */     this.size = items.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 47 */     writeD(buf, this.targetObjId);
/* 48 */     writeC(buf, 11);
/* 49 */     writeC(buf, this.size);
/* 50 */     writeC(buf, 0);
/* 51 */     writeD(buf, 0);
/* 52 */     for (Item item : this.items) {
/*    */ 
/*    */       
/* 55 */       writeD(buf, item.getItemTemplate().getTemplateId());
/* 56 */       writeH(buf, 36);
/* 57 */       writeD(buf, item.getItemTemplate().getNameId());
/* 58 */       writeH(buf, 0);
/*    */       
/* 60 */       writeH(buf, 36);
/* 61 */       writeC(buf, 4);
/* 62 */       writeC(buf, 1);
/* 63 */       writeH(buf, 0);
/* 64 */       writeH(buf, 0);
/* 65 */       writeC(buf, 0);
/*    */       
/* 67 */       writeH(buf, 0);
/* 68 */       writeC(buf, 6);
/* 69 */       writeH(buf, item.getEquipmentSlot());
/* 70 */       writeH(buf, 0);
/* 71 */       writeC(buf, 0);
/* 72 */       writeH(buf, 62);
/* 73 */       writeH(buf, (int)item.getItemCount());
/*    */ 
/*    */ 
/*    */       
/* 77 */       writeD(buf, 0);
/* 78 */       writeD(buf, 0);
/* 79 */       writeD(buf, 0);
/* 80 */       writeD(buf, 0);
/* 81 */       writeD(buf, 0);
/* 82 */       writeC(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_VIEW_PLAYER_DETAILS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
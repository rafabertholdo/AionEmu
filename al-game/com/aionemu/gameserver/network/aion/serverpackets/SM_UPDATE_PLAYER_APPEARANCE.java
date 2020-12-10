/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.items.GodStone;
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
/*    */ 
/*    */ public class SM_UPDATE_PLAYER_APPEARANCE
/*    */   extends AionServerPacket
/*    */ {
/*    */   public int playerId;
/*    */   public int size;
/*    */   public List<Item> items;
/*    */   
/*    */   public SM_UPDATE_PLAYER_APPEARANCE(int playerId, List<Item> items) {
/* 40 */     this.playerId = playerId;
/* 41 */     this.items = items;
/* 42 */     this.size = items.size();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 48 */     writeD(buf, this.playerId);
/*    */     
/* 50 */     short mask = 0;
/* 51 */     for (Item item : this.items)
/*    */     {
/* 53 */       mask = (short)(mask | item.getEquipmentSlot());
/*    */     }
/*    */     
/* 56 */     writeH(buf, mask);
/*    */     
/* 58 */     for (Item item : this.items) {
/*    */       
/* 60 */       writeD(buf, item.getItemSkinTemplate().getTemplateId());
/* 61 */       GodStone godStone = item.getGodStone();
/* 62 */       writeD(buf, (godStone != null) ? godStone.getItemId() : 0);
/* 63 */       writeD(buf, item.getItemColor());
/* 64 */       writeH(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_UPDATE_PLAYER_APPEARANCE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
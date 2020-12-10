/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.drop.DropItem;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public class SM_LOOT_ITEMLIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int targetObjectId;
/*    */   private DropItem[] dropItems;
/*    */   private int size;
/*    */   
/*    */   public SM_LOOT_ITEMLIST(int targetObjectId, Set<DropItem> dropItems, Player player) {
/* 43 */     this.targetObjectId = targetObjectId;
/* 44 */     Set<DropItem> tmp = new HashSet<DropItem>();
/* 45 */     for (DropItem item : dropItems) {
/*    */       
/* 47 */       if (item.getPlayerObjId() == 0 || player.getObjectId() == item.getPlayerObjId())
/* 48 */         tmp.add(item); 
/*    */     } 
/* 50 */     this.dropItems = tmp.<DropItem>toArray(new DropItem[tmp.size()]);
/* 51 */     this.size = this.dropItems.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 60 */     writeD(buf, this.targetObjectId);
/* 61 */     writeC(buf, this.size);
/*    */     
/* 63 */     for (DropItem dropItem : this.dropItems) {
/*    */       
/* 65 */       writeC(buf, dropItem.getIndex());
/* 66 */       writeD(buf, dropItem.getDropTemplate().getItemId());
/* 67 */       writeH(buf, (int)dropItem.getCount());
/* 68 */       writeD(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LOOT_ITEMLIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
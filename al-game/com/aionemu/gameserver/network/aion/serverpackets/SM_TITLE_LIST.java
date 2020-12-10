/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Title;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.TitleList;
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
/*    */ public class SM_TITLE_LIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   private TitleList titleList;
/*    */   
/*    */   public SM_TITLE_LIST(Player player) {
/* 40 */     this.titleList = player.getTitleList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 49 */     writeC(buf, 0);
/* 50 */     writeH(buf, this.titleList.size());
/* 51 */     for (Title title : this.titleList.getTitles()) {
/*    */       
/* 53 */       writeD(buf, title.getTemplate().getTitleId());
/* 54 */       writeD(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TITLE_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
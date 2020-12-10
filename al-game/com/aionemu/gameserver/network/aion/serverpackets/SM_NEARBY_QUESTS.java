/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.services.QuestService;
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
/*    */ public class SM_NEARBY_QUESTS
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Integer[] questIds;
/*    */   private int size;
/*    */   
/*    */   public SM_NEARBY_QUESTS(List<Integer> questIds) {
/* 37 */     this.questIds = questIds.<Integer>toArray(new Integer[questIds.size()]);
/* 38 */     this.size = questIds.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 45 */     int playerLevel = con.getActivePlayer().getLevel();
/* 46 */     writeD(buf, this.size); Integer[] arr$; int len$, i$;
/* 47 */     for (arr$ = this.questIds, len$ = arr$.length, i$ = 0; i$ < len$; ) { int id = arr$[i$].intValue();
/*    */       
/* 49 */       writeH(buf, id);
/* 50 */       if (QuestService.checkLevelRequirement(id, playerLevel)) {
/* 51 */         writeH(buf, 0);
/*    */       } else {
/* 53 */         writeH(buf, 2);
/*    */       } 
/*    */       i$++; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_NEARBY_QUESTS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
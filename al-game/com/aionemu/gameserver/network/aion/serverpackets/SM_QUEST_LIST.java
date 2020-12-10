/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.SortedMap;
/*    */ import java.util.TreeMap;
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
/*    */ public class SM_QUEST_LIST
/*    */   extends AionServerPacket
/*    */ {
/* 38 */   private SortedMap<Integer, QuestState> completeQuestList = new TreeMap<Integer, QuestState>();
/* 39 */   private List<QuestState> startedQuestList = new ArrayList<QuestState>();
/*    */ 
/*    */   
/*    */   public SM_QUEST_LIST(Player player) {
/* 43 */     for (QuestState qs : player.getQuestStateList().getAllQuestState()) {
/*    */       
/* 45 */       if (qs.getStatus() == QuestStatus.COMPLETE) {
/* 46 */         this.completeQuestList.put(Integer.valueOf(qs.getQuestId()), qs); continue;
/* 47 */       }  if (qs.getStatus() != QuestStatus.NONE) {
/* 48 */         this.startedQuestList.add(qs);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 58 */     writeH(buf, this.completeQuestList.size());
/* 59 */     for (QuestState qs : this.completeQuestList.values()) {
/*    */       
/* 61 */       writeH(buf, qs.getQuestId());
/* 62 */       writeH(buf, 0);
/* 63 */       writeC(buf, qs.getCompliteCount());
/*    */     } 
/* 65 */     writeC(buf, this.startedQuestList.size());
/* 66 */     for (QuestState qs : this.startedQuestList) {
/*    */       
/* 68 */       writeH(buf, qs.getQuestId());
/* 69 */       writeH(buf, 0);
/*    */     } 
/* 71 */     for (QuestState qs : this.startedQuestList) {
/*    */       
/* 73 */       writeC(buf, qs.getStatus().value());
/* 74 */       writeD(buf, qs.getQuestVars().getQuestVars());
/* 75 */       writeC(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_QUEST_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
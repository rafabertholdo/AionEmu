/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.dataholders.QuestsData;
/*    */ import com.aionemu.gameserver.model.TaskId;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
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
/*    */ public class CM_DELETE_QUEST
/*    */   extends AionClientPacket
/*    */ {
/* 30 */   static QuestsData questsData = DataManager.QUEST_DATA;
/*    */   
/*    */   public int questId;
/*    */   
/*    */   public CM_DELETE_QUEST(int opcode) {
/* 35 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 42 */     this.questId = readH();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 48 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 49 */     if (questsData.getQuestById(this.questId).isTimer()) {
/*    */       
/* 51 */       player.getController().cancelTask(TaskId.QUEST_TIMER);
/* 52 */       sendPacket((AionServerPacket)new SM_QUEST_ACCEPTED(this.questId, 0));
/*    */     } 
/* 54 */     if (!QuestEngine.getInstance().deleteQuest(player, this.questId))
/*    */       return; 
/* 56 */     sendPacket((AionServerPacket)new SM_QUEST_ACCEPTED(this.questId));
/* 57 */     player.getController().updateNearbyQuests();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DELETE_QUEST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
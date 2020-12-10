/*    */ package com.aionemu.gameserver.model.gameobjects.player;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import java.util.Collection;
/*    */ import java.util.SortedMap;
/*    */ import java.util.TreeMap;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class QuestStateList
/*    */ {
/* 33 */   private static final Logger log = Logger.getLogger(QuestStateList.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   private final SortedMap<Integer, QuestState> _quests = new TreeMap<Integer, QuestState>();
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized boolean addQuest(int questId, QuestState questState) {
/* 47 */     if (this._quests.containsKey(Integer.valueOf(questId))) {
/*    */       
/* 49 */       log.warn("Duplicate quest. ");
/* 50 */       return false;
/*    */     } 
/* 52 */     this._quests.put(Integer.valueOf(questId), questState);
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized boolean removeQuest(int questId) {
/* 58 */     if (this._quests.containsKey(Integer.valueOf(questId))) {
/*    */       
/* 60 */       this._quests.remove(Integer.valueOf(questId));
/* 61 */       return true;
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public QuestState getQuestState(int questId) {
/* 68 */     return this._quests.get(Integer.valueOf(questId));
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<QuestState> getAllQuestState() {
/* 73 */     return this._quests.values();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\QuestStateList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
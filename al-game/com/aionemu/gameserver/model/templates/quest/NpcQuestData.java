/*    */ package com.aionemu.gameserver.model.templates.quest;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NpcQuestData
/*    */ {
/* 35 */   private final List<Integer> onQuestStart = new ArrayList<Integer>();
/* 36 */   private final List<Integer> onKillEvent = new ArrayList<Integer>();
/* 37 */   private final List<Integer> onTalkEvent = new ArrayList<Integer>();
/* 38 */   private final List<Integer> onAttackEvent = new ArrayList<Integer>();
/*    */ 
/*    */ 
/*    */   
/*    */   public void addOnQuestStart(int questId) {
/* 43 */     if (!this.onQuestStart.contains(Integer.valueOf(questId)))
/*    */     {
/* 45 */       this.onQuestStart.add(Integer.valueOf(questId));
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Integer> getOnQuestStart() {
/* 50 */     return this.onQuestStart;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addOnAttackEvent(int questId) {
/* 55 */     if (!this.onAttackEvent.contains(Integer.valueOf(questId)))
/*    */     {
/* 57 */       this.onAttackEvent.add(Integer.valueOf(questId));
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Integer> getOnAttackEvent() {
/* 62 */     return this.onAttackEvent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addOnKillEvent(int questId) {
/* 67 */     if (!this.onKillEvent.contains(Integer.valueOf(questId)))
/*    */     {
/* 69 */       this.onKillEvent.add(Integer.valueOf(questId));
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Integer> getOnKillEvent() {
/* 74 */     return this.onKillEvent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addOnTalkEvent(int questId) {
/* 79 */     if (!this.onTalkEvent.contains(Integer.valueOf(questId)))
/*    */     {
/* 81 */       this.onTalkEvent.add(Integer.valueOf(questId));
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Integer> getOnTalkEvent() {
/* 86 */     return this.onTalkEvent;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\quest\NpcQuestData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
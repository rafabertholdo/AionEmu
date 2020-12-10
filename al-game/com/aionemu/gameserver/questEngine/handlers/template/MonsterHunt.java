/*     */ package com.aionemu.gameserver.questEngine.handlers.template;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.QuestTemplate;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.MonsterInfo;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import java.util.Iterator;
/*     */ import javolution.util.FastMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MonsterHunt
/*     */   extends QuestHandler
/*     */ {
/*     */   private final int questId;
/*     */   private final int startNpc;
/*     */   private final int endNpc;
/*     */   private final FastMap<Integer, MonsterInfo> monsterInfo;
/*     */   
/*     */   public MonsterHunt(int questId, int startNpc, int endNpc, FastMap<Integer, MonsterInfo> monsterInfo) {
/*  47 */     super(Integer.valueOf(questId));
/*  48 */     this.questId = questId;
/*  49 */     this.startNpc = startNpc;
/*  50 */     if (endNpc != 0) {
/*  51 */       this.endNpc = endNpc;
/*     */     } else {
/*  53 */       this.endNpc = startNpc;
/*  54 */     }  this.monsterInfo = monsterInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  60 */     this.qe.setNpcQuestData(this.startNpc).addOnQuestStart(this.questId);
/*  61 */     this.qe.setNpcQuestData(this.startNpc).addOnTalkEvent(this.questId);
/*  62 */     for (Iterator<Integer> i$ = this.monsterInfo.keySet().iterator(); i$.hasNext(); ) { int monsterId = ((Integer)i$.next()).intValue();
/*  63 */       this.qe.setNpcQuestData(monsterId).addOnKillEvent(this.questId); }
/*  64 */      if (this.endNpc != this.startNpc) {
/*  65 */       this.qe.setNpcQuestData(this.endNpc).addOnTalkEvent(this.questId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  71 */     Player player = env.getPlayer();
/*  72 */     int targetId = 0;
/*  73 */     if (env.getVisibleObject() instanceof Npc)
/*  74 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  75 */     QuestState qs = player.getQuestStateList().getQuestState(this.questId);
/*  76 */     QuestTemplate template = DataManager.QUEST_DATA.getQuestById(this.questId);
/*  77 */     if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue())) {
/*     */       
/*  79 */       if (targetId == this.startNpc)
/*     */       {
/*  81 */         if (env.getDialogId().intValue() == 25) {
/*  82 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  84 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  87 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  89 */       for (MonsterInfo mi : this.monsterInfo.values()) {
/*     */         
/*  91 */         if (mi.getMaxKill() < qs.getQuestVarById(mi.getVarId()))
/*  92 */           return false; 
/*     */       } 
/*  94 */       if (targetId == this.endNpc)
/*     */       {
/*  96 */         if (env.getDialogId().intValue() == 25)
/*  97 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  98 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 100 */           qs.setStatus(QuestStatus.REWARD);
/* 101 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 102 */           updateQuestStatus(player, qs);
/* 103 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         } 
/*     */         
/* 106 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/* 109 */     } else if (qs.getStatus() == QuestStatus.REWARD && targetId == this.endNpc) {
/*     */       
/* 111 */       return defaultQuestEndDialog(env);
/*     */     } 
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 120 */     Player player = env.getPlayer();
/* 121 */     QuestState qs = player.getQuestStateList().getQuestState(this.questId);
/* 122 */     if (qs == null) {
/* 123 */       return false;
/*     */     }
/* 125 */     int targetId = 0;
/* 126 */     if (env.getVisibleObject() instanceof Npc) {
/* 127 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 129 */     if (qs.getStatus() != QuestStatus.START)
/* 130 */       return false; 
/* 131 */     MonsterInfo mi = (MonsterInfo)this.monsterInfo.get(Integer.valueOf(targetId));
/* 132 */     if (mi == null)
/* 133 */       return false; 
/* 134 */     if (mi.getMaxKill() <= qs.getQuestVarById(mi.getVarId())) {
/* 135 */       return false;
/*     */     }
/* 137 */     qs.setQuestVarById(mi.getVarId(), qs.getQuestVarById(mi.getVarId()) + 1);
/* 138 */     updateQuestStatus(player, qs);
/* 139 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\template\MonsterHunt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
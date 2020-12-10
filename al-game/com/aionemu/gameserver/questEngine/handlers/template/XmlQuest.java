/*     */ package com.aionemu.gameserver.questEngine.handlers.template;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.QuestTemplate;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.MonsterInfo;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.XmlQuestData;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnKillEvent;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnTalkEvent;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import java.util.Iterator;
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
/*     */ public class XmlQuest
/*     */   extends QuestHandler
/*     */ {
/*     */   private final XmlQuestData xmlQuestData;
/*     */   
/*     */   public XmlQuest(XmlQuestData xmlQuestData) {
/*  43 */     super(Integer.valueOf(xmlQuestData.getId()));
/*  44 */     this.xmlQuestData = xmlQuestData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     if (this.xmlQuestData.getStartNpcId() != null) {
/*     */       
/*  52 */       this.qe.setNpcQuestData(this.xmlQuestData.getStartNpcId().intValue()).addOnQuestStart(getQuestId().intValue());
/*  53 */       this.qe.setNpcQuestData(this.xmlQuestData.getStartNpcId().intValue()).addOnTalkEvent(getQuestId().intValue());
/*     */     } 
/*  55 */     if (this.xmlQuestData.getEndNpcId() != null) {
/*  56 */       this.qe.setNpcQuestData(this.xmlQuestData.getEndNpcId().intValue()).addOnTalkEvent(getQuestId().intValue());
/*     */     }
/*  58 */     for (OnTalkEvent talkEvent : this.xmlQuestData.getOnTalkEvent()) {
/*  59 */       for (Iterator<Integer> i$ = talkEvent.getIds().iterator(); i$.hasNext(); ) { int npcId = ((Integer)i$.next()).intValue();
/*  60 */         this.qe.setNpcQuestData(npcId).addOnTalkEvent(getQuestId().intValue()); }
/*     */     
/*  62 */     }  for (OnKillEvent killEvent : this.xmlQuestData.getOnKillEvent()) {
/*  63 */       for (MonsterInfo monsterInfo : killEvent.getMonsterInfos()) {
/*  64 */         this.qe.setNpcQuestData(monsterInfo.getNpcId()).addOnKillEvent(getQuestId().intValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  70 */     env.setQuestId(getQuestId());
/*  71 */     for (OnTalkEvent talkEvent : this.xmlQuestData.getOnTalkEvent()) {
/*     */       
/*  73 */       if (talkEvent.operate(env)) {
/*  74 */         return true;
/*     */       }
/*     */     } 
/*  77 */     Player player = env.getPlayer();
/*  78 */     int targetId = 0;
/*  79 */     if (env.getVisibleObject() instanceof Npc)
/*  80 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  81 */     QuestState qs = player.getQuestStateList().getQuestState(getQuestId().intValue());
/*  82 */     QuestTemplate template = DataManager.QUEST_DATA.getQuestById(getQuestId().intValue());
/*  83 */     if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue())) {
/*     */       
/*  85 */       if (targetId == this.xmlQuestData.getStartNpcId().intValue())
/*     */       {
/*  87 */         if (env.getDialogId().intValue() == 25) {
/*  88 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  90 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  93 */     } else if (qs.getStatus() == QuestStatus.REWARD && targetId == this.xmlQuestData.getEndNpcId().intValue()) {
/*     */       
/*  95 */       return defaultQuestEndDialog(env);
/*     */     } 
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 103 */     env.setQuestId(getQuestId());
/* 104 */     for (OnKillEvent killEvent : this.xmlQuestData.getOnKillEvent()) {
/*     */       
/* 106 */       if (killEvent.operate(env))
/* 107 */         return true; 
/*     */     } 
/* 109 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\template\XmlQuest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
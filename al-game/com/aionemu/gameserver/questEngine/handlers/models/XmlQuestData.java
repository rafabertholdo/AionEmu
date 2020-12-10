/*     */ package com.aionemu.gameserver.questEngine.handlers.models;
/*     */ 
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnKillEvent;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events.OnTalkEvent;
/*     */ import com.aionemu.gameserver.questEngine.handlers.template.XmlQuest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "XmlQuest", propOrder = {"onTalkEvent", "onKillEvent"})
/*     */ public class XmlQuestData
/*     */   extends QuestScriptData
/*     */ {
/*     */   @XmlElement(name = "on_talk_event")
/*     */   protected List<OnTalkEvent> onTalkEvent;
/*     */   @XmlElement(name = "on_kill_event")
/*     */   protected List<OnKillEvent> onKillEvent;
/*     */   @XmlAttribute(name = "start_npc_id")
/*     */   protected Integer startNpcId;
/*     */   @XmlAttribute(name = "end_npc_id")
/*     */   protected Integer endNpcId;
/*     */   
/*     */   public List<OnTalkEvent> getOnTalkEvent() {
/*  73 */     if (this.onTalkEvent == null)
/*     */     {
/*  75 */       this.onTalkEvent = new ArrayList<OnTalkEvent>();
/*     */     }
/*  77 */     return this.onTalkEvent;
/*     */   }
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
/*     */   public List<OnKillEvent> getOnKillEvent() {
/* 103 */     if (this.onKillEvent == null)
/*     */     {
/* 105 */       this.onKillEvent = new ArrayList<OnKillEvent>();
/*     */     }
/* 107 */     return this.onKillEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getStartNpcId() {
/* 118 */     return this.startNpcId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getEndNpcId() {
/* 129 */     return this.endNpcId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(QuestEngine questEngine) {
/* 141 */     questEngine.addQuestHandler((QuestHandler)new XmlQuest(this));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\XmlQuestData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
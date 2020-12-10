/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.QuestVar;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "OnTalkEvent", propOrder = {"var"})
/*    */ public class OnTalkEvent
/*    */   extends QuestEvent
/*    */ {
/*    */   protected List<QuestVar> var;
/*    */   
/*    */   public boolean operate(QuestEnv env) {
/* 44 */     if (this.conditions == null || this.conditions.checkConditionOfSet(env)) {
/*    */       
/* 46 */       QuestState qs = env.getPlayer().getQuestStateList().getQuestState(env.getQuestId().intValue());
/* 47 */       for (QuestVar questVar : this.var) {
/*    */         
/* 49 */         if (questVar.operate(env, qs))
/* 50 */           return true; 
/*    */       } 
/*    */     } 
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\events\OnTalkEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
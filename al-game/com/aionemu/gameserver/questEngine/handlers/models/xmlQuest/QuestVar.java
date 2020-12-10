/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ @XmlType(name = "QuestVar", propOrder = {"npc"})
/*    */ public class QuestVar
/*    */ {
/*    */   protected List<QuestNpc> npc;
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */   
/*    */   public boolean operate(QuestEnv env, QuestState qs) {
/* 45 */     int var = -1;
/* 46 */     if (qs != null)
/* 47 */       var = qs.getQuestVars().getQuestVars(); 
/* 48 */     if (var != this.value)
/* 49 */       return false; 
/* 50 */     for (QuestNpc questNpc : this.npc) {
/*    */       
/* 52 */       if (questNpc.operate(env, qs))
/* 53 */         return true; 
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\QuestVar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
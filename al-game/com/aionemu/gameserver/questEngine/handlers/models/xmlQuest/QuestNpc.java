/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
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
/*    */ @XmlType(name = "QuestNpc", propOrder = {"dialog"})
/*    */ public class QuestNpc
/*    */ {
/*    */   protected List<QuestDialog> dialog;
/*    */   @XmlAttribute(required = true)
/*    */   protected int id;
/*    */   
/*    */   public boolean operate(QuestEnv env, QuestState qs) {
/* 46 */     int npcId = -1;
/* 47 */     if (env.getVisibleObject() instanceof Npc)
/* 48 */       npcId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 49 */     if (npcId != this.id)
/* 50 */       return false; 
/* 51 */     for (QuestDialog questDialog : this.dialog) {
/*    */       
/* 53 */       if (questDialog.operate(env, qs))
/* 54 */         return true; 
/*    */     } 
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\QuestNpc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
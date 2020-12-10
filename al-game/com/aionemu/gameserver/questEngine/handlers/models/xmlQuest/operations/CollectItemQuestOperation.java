/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.services.QuestService;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
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
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "CollectItemQuestOperation", propOrder = {"_true", "_false"})
/*    */ public class CollectItemQuestOperation
/*    */   extends QuestOperation
/*    */ {
/*    */   @XmlElement(name = "true", required = true)
/*    */   protected QuestOperations _true;
/*    */   @XmlElement(name = "false", required = true)
/*    */   protected QuestOperations _false;
/*    */   @XmlAttribute
/*    */   protected Boolean removeItems;
/*    */   
/*    */   public void doOperate(QuestEnv env) {
/* 51 */     if (QuestService.collectItemCheck(env, (this.removeItems == null))) {
/* 52 */       this._true.operate(env);
/*    */     } else {
/* 54 */       this._false.operate(env);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\CollectItemQuestOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
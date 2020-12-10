/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlElements;
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
/*    */ @XmlType(name = "QuestOperations", propOrder = {"operations"})
/*    */ public class QuestOperations
/*    */ {
/*    */   @XmlElements({@XmlElement(name = "take_item", type = TakeItemOperation.class), @XmlElement(name = "npc_dialog", type = NpcDialogOperation.class), @XmlElement(name = "set_quest_status", type = SetQuestStatusOperation.class), @XmlElement(name = "give_item", type = GiveItemOperation.class), @XmlElement(name = "start_quest", type = StartQuestOperation.class), @XmlElement(name = "npc_use", type = ActionItemUseOperation.class), @XmlElement(name = "set_quest_var", type = SetQuestVarOperation.class), @XmlElement(name = "collect_items", type = CollectItemQuestOperation.class)})
/*    */   protected List<QuestOperation> operations;
/*    */   @XmlAttribute
/*    */   protected Boolean override;
/*    */   
/*    */   public boolean isOverride() {
/* 58 */     if (this.override == null)
/*    */     {
/* 60 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 64 */     return this.override.booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean operate(QuestEnv env) {
/* 70 */     if (this.operations != null)
/*    */     {
/* 72 */       for (QuestOperation oper : this.operations)
/*    */       {
/* 74 */         oper.doOperate(env);
/*    */       }
/*    */     }
/* 77 */     return isOverride();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\QuestOperations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.model.ConditionUnionType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "QuestConditions", propOrder = {"conditions"})
/*    */ public class QuestConditions
/*    */ {
/*    */   @XmlElements({@XmlElement(name = "quest_status", type = QuestStatusCondition.class), @XmlElement(name = "npc_id", type = NpcIdCondition.class), @XmlElement(name = "pc_inventory", type = PcInventoryCondition.class), @XmlElement(name = "quest_var", type = QuestVarCondition.class), @XmlElement(name = "dialog_id", type = DialogIdCondition.class)})
/*    */   protected List<QuestCondition> conditions;
/*    */   @XmlAttribute(required = true)
/*    */   protected ConditionUnionType operate;
/*    */   
/*    */   public boolean checkConditionOfSet(QuestEnv env) {
/* 52 */     boolean inCondition = (this.operate == ConditionUnionType.AND);
/* 53 */     for (QuestCondition cond : this.conditions) {
/*    */       
/* 55 */       boolean bCond = cond.doCheck(env);
/* 56 */       switch (this.operate) {
/*    */         
/*    */         case AND:
/* 59 */           if (!bCond) return false; 
/* 60 */           inCondition = (inCondition && bCond);
/*    */         
/*    */         case OR:
/* 63 */           if (bCond) return true; 
/* 64 */           inCondition = (inCondition || bCond);
/*    */       } 
/*    */     
/*    */     } 
/* 68 */     return inCondition;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\QuestConditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
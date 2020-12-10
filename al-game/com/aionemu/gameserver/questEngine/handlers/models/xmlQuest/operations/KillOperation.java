/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "KillOperation")
/*    */ public class KillOperation
/*    */   extends QuestOperation
/*    */ {
/*    */   public void doOperate(QuestEnv env) {
/* 44 */     if (env.getVisibleObject() instanceof Npc)
/* 45 */       ((Npc)env.getVisibleObject()).getController().onDie((Creature)env.getPlayer()); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\KillOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
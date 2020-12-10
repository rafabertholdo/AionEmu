/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "SetQuestStatusOperation")
/*    */ public class SetQuestStatusOperation
/*    */   extends QuestOperation
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected QuestStatus status;
/*    */   
/*    */   public void doOperate(QuestEnv env) {
/* 51 */     Player player = env.getPlayer();
/* 52 */     int questId = env.getQuestId().intValue();
/* 53 */     QuestState qs = player.getQuestStateList().getQuestState(questId);
/* 54 */     if (qs != null) {
/*    */       
/* 56 */       qs.setStatus(this.status);
/* 57 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(questId, qs.getStatus(), qs.getQuestVars().getQuestVars()));
/* 58 */       if (qs.getStatus() == QuestStatus.COMPLETE)
/* 59 */         player.getController().updateNearbyQuests(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\SetQuestStatusOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "NpcDialogOperation")
/*    */ public class NpcDialogOperation
/*    */   extends QuestOperation
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int id;
/*    */   @XmlAttribute(name = "quest_id")
/*    */   protected Integer questId;
/*    */   
/*    */   public void doOperate(QuestEnv env) {
/* 51 */     Player player = env.getPlayer();
/* 52 */     VisibleObject obj = env.getVisibleObject();
/* 53 */     int qId = env.getQuestId().intValue();
/* 54 */     if (this.questId != null)
/* 55 */       qId = this.questId.intValue(); 
/* 56 */     if (qId == 0) {
/* 57 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(obj.getObjectId(), this.id));
/*    */     } else {
/* 59 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(obj.getObjectId(), this.id, qId));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\NpcDialogOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
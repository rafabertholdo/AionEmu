/*    */ package com.aionemu.gameserver.skillengine.task;
/*    */ 
/*    */ import com.aionemu.gameserver.model.DescriptionId;
/*    */ import com.aionemu.gameserver.model.gameobjects.Gatherable;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.GatherableTemplate;
/*    */ import com.aionemu.gameserver.model.templates.gather.Material;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GATHER_STATUS;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GATHER_UPDATE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.ItemService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class GatheringTask
/*    */   extends AbstractCraftTask
/*    */ {
/*    */   private GatherableTemplate template;
/*    */   private Material material;
/*    */   
/*    */   public GatheringTask(Player requestor, Gatherable gatherable, Material material, int skillLvlDiff) {
/* 42 */     super(requestor, (VisibleObject)gatherable, gatherable.getObjectTemplate().getSuccessAdj(), gatherable.getObjectTemplate().getFailureAdj(), skillLvlDiff);
/*    */     
/* 44 */     this.template = gatherable.getObjectTemplate();
/* 45 */     this.material = material;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onInteractionAbort() {
/* 51 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, 0, 0, 5));
/*    */     
/* 53 */     PacketSendUtility.broadcastPacket((VisibleObject)this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 2));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onInteractionFinish() {
/* 60 */     ((Gatherable)this.responder).getController().completeInteraction();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onInteractionStart() {
/* 66 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, 0, 0, 0));
/* 67 */     PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 0), true);
/* 68 */     PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 1), true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void sendInteractionUpdate() {
/* 74 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onFailureFinish() {
/* 80 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 1));
/* 81 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 7));
/* 82 */     PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 3), true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onSuccessFinish() {
/* 88 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 2));
/* 89 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 6));
/* 90 */     PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 2), true);
/* 91 */     PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)SM_SYSTEM_MESSAGE.EXTRACT_GATHER_SUCCESS_1_BASIC(new DescriptionId(this.material.getNameid().intValue())));
/* 92 */     ItemService.addItem(this.requestor, this.material.getItemid().intValue(), 1L);
/* 93 */     ((Gatherable)this.responder).getController().rewardPlayer(this.requestor);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\task\GatheringTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
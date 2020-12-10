package com.aionemu.gameserver.skillengine.task;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.GatherableTemplate;
import com.aionemu.gameserver.model.templates.gather.Material;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GATHER_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GATHER_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class GatheringTask
  extends AbstractCraftTask
{
  private GatherableTemplate template;
  private Material material;
  
  public GatheringTask(Player requestor, Gatherable gatherable, Material material, int skillLvlDiff) {
    super(requestor, (VisibleObject)gatherable, gatherable.getObjectTemplate().getSuccessAdj(), gatherable.getObjectTemplate().getFailureAdj(), skillLvlDiff);
    
    this.template = gatherable.getObjectTemplate();
    this.material = material;
  }


  
  protected void onInteractionAbort() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, 0, 0, 5));
    
    PacketSendUtility.broadcastPacket((VisibleObject)this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 2));
  }



  
  protected void onInteractionFinish() {
    ((Gatherable)this.responder).getController().completeInteraction();
  }


  
  protected void onInteractionStart() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, 0, 0, 0));
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 0), true);
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 1), true);
  }


  
  protected void sendInteractionUpdate() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 1));
  }


  
  protected void onFailureFinish() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 1));
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 7));
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 3), true);
  }


  
  protected void onSuccessFinish() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 2));
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_GATHER_UPDATE(this.template, this.material, this.currentSuccessValue, this.currentFailureValue, 6));
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_GATHER_STATUS(this.requestor.getObjectId(), this.responder.getObjectId(), 2), true);
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)SM_SYSTEM_MESSAGE.EXTRACT_GATHER_SUCCESS_1_BASIC(new DescriptionId(this.material.getNameid().intValue())));
    ItemService.addItem(this.requestor, this.material.getItemid().intValue(), 1L);
    ((Gatherable)this.responder).getController().rewardPlayer(this.requestor);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\task\GatheringTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

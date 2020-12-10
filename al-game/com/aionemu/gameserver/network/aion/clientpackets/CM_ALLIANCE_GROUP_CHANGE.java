package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class CM_ALLIANCE_GROUP_CHANGE
  extends AionClientPacket
{
  private int allianceGroupId;
  private int playerObjectId;
  private int secondObjectId;
  
  public CM_ALLIANCE_GROUP_CHANGE(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.playerObjectId = readD();
    this.allianceGroupId = readD();
    this.secondObjectId = readD();
  }


  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    PlayerAlliance alliance = player.getPlayerAlliance();
    
    if (alliance == null) {

      
      PacketSendUtility.sendMessage(player, "You are not in an alliance.");
      
      return;
    } 
    if (!alliance.hasAuthority(player.getObjectId())) {

      
      PacketSendUtility.sendMessage(player, "You do not have the authority for that.");
      
      return;
    } 
    AllianceService.getInstance().handleGroupChange(alliance, this.playerObjectId, this.allianceGroupId, this.secondObjectId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_ALLIANCE_GROUP_CHANGE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

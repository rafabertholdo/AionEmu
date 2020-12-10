package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.teleport.TelelocationTemplate;
import com.aionemu.gameserver.model.templates.teleport.TeleportType;
import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.world.World;
























public class CM_TELEPORT_SELECT
  extends AionClientPacket
{
  public int targetObjectId;
  public int locId;
  public TelelocationTemplate _tele;
  private TeleporterTemplate teleport;
  
  public CM_TELEPORT_SELECT(int opcode) {
    super(opcode);
  }






  
  protected void readImpl() {
    this.targetObjectId = readD();
    this.locId = readD();
  }





  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    
    Npc npc = (Npc)World.getInstance().findAionObject(this.targetObjectId);
    
    if (activePlayer == null || activePlayer.getLifeStats().isAlreadyDead()) {
      return;
    }
    this.teleport = DataManager.TELEPORTER_DATA.getTeleporterTemplate(npc.getNpcId());
    
    switch (this.teleport.getType()) {
      
      case FLIGHT:
        TeleportService.flightTeleport(this.teleport, this.locId, activePlayer);
        break;
      case REGULAR:
        TeleportService.regularTeleport(this.teleport, this.locId, activePlayer);
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_TELEPORT_SELECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

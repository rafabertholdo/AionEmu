package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_INFO;
import com.aionemu.gameserver.services.LegionService;
import org.apache.log4j.Logger;




















public class CM_LEGION
  extends AionClientPacket
{
  private static final Logger log = Logger.getLogger(CM_LEGION.class);
  
  private int exOpcode;
  
  private int legionarPermission2;
  
  private int centurionPermission1;
  
  private int centurionPermission2;
  
  private int rank;
  
  private String legionName;
  
  private String charName;
  
  private String newNickname;
  
  private String announcement;
  
  private String newSelfIntro;
  
  public CM_LEGION(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.exOpcode = readC();
    
    switch (this.exOpcode) {

      
      case 0:
        readD();
        this.legionName = readS();

      
      case 1:
        readD();
        this.charName = readS();

      
      case 2:
        readD();
        readH();

      
      case 4:
        readD();
        this.charName = readS();

      
      case 5:
        readD();
        this.charName = readS();

      
      case 6:
        this.rank = readD();
        this.charName = readS();

      
      case 7:
        readD();
        this.charName = readS();

      
      case 8:
        return;
      
      case 9:
        readD();
        this.announcement = readS();

      
      case 10:
        readD();
        this.newSelfIntro = readS();

      
      case 13:
        this.centurionPermission1 = readC();
        this.centurionPermission2 = readC();
        readC();
        this.legionarPermission2 = readC();

      
      case 14:
        readD();
        readH();
      
      case 15:
        this.charName = readS();
        this.newNickname = readS();
    } 
    
    log.info("Unknown Legion exOpcode? 0x" + Integer.toHexString(this.exOpcode).toUpperCase());
  }







  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    if (activePlayer.isLegionMember()) {
      
      Legion legion = activePlayer.getLegion();
      
      if (this.charName != null)
      {
        LegionService.getInstance().handleCharNameRequest(this.exOpcode, activePlayer, this.charName, this.newNickname, this.rank);
      }
      else
      {
        switch (this.exOpcode) {

          
          case 8:
            sendPacket((AionServerPacket)new SM_LEGION_INFO(legion));
            return;
          
          case 9:
            LegionService.getInstance().handleLegionRequest(this.exOpcode, activePlayer, this.announcement);
            return;
          
          case 10:
            LegionService.getInstance().handleLegionRequest(this.exOpcode, activePlayer, this.newSelfIntro);
            return;
          
          case 13:
            if (activePlayer.getLegionMember().isBrigadeGeneral()) {
              LegionService.getInstance().changePermissions(legion, this.legionarPermission2, this.centurionPermission1, this.centurionPermission2);
            }
            return;
        } 
        
        LegionService.getInstance().handleLegionRequest(this.exOpcode, activePlayer);
      
      }
    
    }
    else {
      
      switch (this.exOpcode) {

        
        case 0:
          LegionService.getInstance().createLegion(activePlayer, this.legionName);
          break;
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_LEGION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

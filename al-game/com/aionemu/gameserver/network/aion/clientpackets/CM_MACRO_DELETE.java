package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MACRO_RESULT;
import com.aionemu.gameserver.services.PlayerService;
import org.apache.log4j.Logger;






























public class CM_MACRO_DELETE
  extends AionClientPacket
{
  private static final Logger log = Logger.getLogger(CM_MACRO_DELETE.class);




  
  private int macroPosition;




  
  public CM_MACRO_DELETE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.macroPosition = readC();
  }





  
  protected void runImpl() {
    log.debug("Request to delete macro #" + this.macroPosition);
    
    PlayerService.removeMacro(((AionConnection)getConnection()).getActivePlayer(), this.macroPosition);
    
    sendPacket((AionServerPacket)SM_MACRO_RESULT.SM_MACRO_DELETED);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MACRO_DELETE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

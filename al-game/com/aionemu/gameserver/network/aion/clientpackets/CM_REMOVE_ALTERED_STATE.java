package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;








public class CM_REMOVE_ALTERED_STATE
  extends AionClientPacket
{
  private int skillid;
  
  public CM_REMOVE_ALTERED_STATE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.skillid = readH();
  }






  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    player.getEffectController().removeEffect(this.skillid);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_REMOVE_ALTERED_STATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

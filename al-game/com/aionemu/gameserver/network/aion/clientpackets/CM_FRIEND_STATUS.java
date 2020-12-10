package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;























public class CM_FRIEND_STATUS
  extends AionClientPacket
{
  private int status;
  
  public CM_FRIEND_STATUS(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.status = readC();
  }






  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    FriendList.Status statusEnum = FriendList.Status.getByValue(this.status);
    if (statusEnum == null)
      statusEnum = FriendList.Status.ONLINE; 
    activePlayer.getFriendList().setStatus(statusEnum);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_FRIEND_STATUS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

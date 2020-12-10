package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_LIST;




















public class CM_SET_NOTE
  extends AionClientPacket
{
  private String note;
  
  public CM_SET_NOTE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.note = readS();
  }






  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    
    if (!this.note.equals(activePlayer.getCommonData().getNote())) {

      
      activePlayer.getCommonData().setNote(this.note);
      
      for (Friend friend : activePlayer.getFriendList()) {

        
        if (friend.isOnline())
        {
          friend.getPlayer().getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
        }
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SET_NOTE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

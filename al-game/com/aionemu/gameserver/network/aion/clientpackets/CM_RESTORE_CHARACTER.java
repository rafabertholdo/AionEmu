package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RESTORE_CHARACTER;
import com.aionemu.gameserver.services.PlayerService;
































public class CM_RESTORE_CHARACTER
  extends AionClientPacket
{
  private int playOk2;
  private int chaOid;
  
  public CM_RESTORE_CHARACTER(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.playOk2 = readD();
    this.chaOid = readD();
  }





  
  protected void runImpl() {
    Account account = ((AionConnection)getConnection()).getAccount();
    PlayerAccountData pad = account.getPlayerAccountData(this.chaOid);
    
    boolean success = (pad != null && PlayerService.cancelPlayerDeletion(pad));
    sendPacket((AionServerPacket)new SM_RESTORE_CHARACTER(this.chaOid, success));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_RESTORE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

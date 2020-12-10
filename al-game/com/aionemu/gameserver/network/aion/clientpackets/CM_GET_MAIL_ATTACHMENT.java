package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.MailService;





















public class CM_GET_MAIL_ATTACHMENT
  extends AionClientPacket
{
  private int mailObjId;
  private int attachmentType;
  
  public CM_GET_MAIL_ATTACHMENT(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.mailObjId = readD();
    this.attachmentType = readC();
  }


  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    MailService.getInstance().getAttachments(player, this.mailObjId, this.attachmentType);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GET_MAIL_ATTACHMENT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

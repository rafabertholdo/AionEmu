package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.MailService;




















public class CM_SEND_MAIL
  extends AionClientPacket
{
  private String recipientName;
  private String title;
  private String message;
  private int itemObjId;
  private int itemCount;
  private int kinahCount;
  private int express;
  
  public CM_SEND_MAIL(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.recipientName = readS();
    this.title = readS();
    this.message = readS();
    this.itemObjId = readD();
    this.itemCount = readD();
    readD();
    this.kinahCount = readD();
    readD();
    this.express = readC();
  }


  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    if (!player.isTrading()) {

      
      if (this.express == 0)
        MailService.getInstance().sendMail(player, this.recipientName, this.title, this.message, this.itemObjId, this.itemCount, this.kinahCount, false); 
      if (this.express == 1)
        MailService.getInstance().sendMail(player, this.recipientName, this.title, this.message, this.itemObjId, this.itemCount, this.kinahCount, true); 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SEND_MAIL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

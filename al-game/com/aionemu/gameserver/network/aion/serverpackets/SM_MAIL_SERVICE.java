package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Letter;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.mail.MailMessage;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.MailServicePacket;
import java.nio.ByteBuffer;
import java.util.Collection;


























public class SM_MAIL_SERVICE
  extends MailServicePacket
{
  private int serviceId;
  private Player player;
  private Collection<Letter> letters;
  private int haveNewMail;
  private int haveUnread;
  private int mailMessage;
  private Letter letter;
  private long time;
  private int letterId;
  private int attachmentType;
  
  public SM_MAIL_SERVICE(boolean isNewMail, boolean haveUnread) {
    this.serviceId = 0;
    
    if (isNewMail) {
      this.haveNewMail = 1;
    } else {
      this.haveNewMail = 0;
    } 
    if (haveUnread) {
      this.haveUnread = 1;
    } else {
      this.haveUnread = 0;
    } 
  }




  
  public SM_MAIL_SERVICE(MailMessage mailMessage) {
    this.serviceId = 1;
    this.mailMessage = mailMessage.getId();
  }






  
  public SM_MAIL_SERVICE(Player player, Collection<Letter> letters) {
    this.serviceId = 2;
    this.player = player;
    this.letters = letters;
  }







  
  public SM_MAIL_SERVICE(Player player, Letter letter, long time) {
    this.serviceId = 3;
    this.player = player;
    this.letter = letter;
    this.time = time;
  }






  
  public SM_MAIL_SERVICE(int letterId, int attachmentType) {
    this.serviceId = 5;
    this.letterId = letterId;
    this.attachmentType = attachmentType;
  }





  
  public SM_MAIL_SERVICE(int letterId) {
    this.serviceId = 6;
    this.letterId = letterId;
  }


  
  public void writeImpl(AionConnection con, ByteBuffer buf) {
    switch (this.serviceId) {
      
      case 0:
        writeMailboxState(buf, this.haveNewMail, this.haveUnread);
        break;
      
      case 1:
        writeMailMessage(buf, this.mailMessage);
        break;
      
      case 2:
        if (this.letters.size() > 0) {
          writeLettersList(buf, this.letters, this.player); break;
        } 
        writeEmptyLettersList(buf, this.player);
        break;
      
      case 3:
        writeLetterRead(buf, this.letter, this.time);
        break;
      
      case 5:
        writeLetterState(buf, this.letterId, this.attachmentType);
        break;
      
      case 6:
        writeLetterDelete(buf, this.letterId);
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_MAIL_SERVICE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

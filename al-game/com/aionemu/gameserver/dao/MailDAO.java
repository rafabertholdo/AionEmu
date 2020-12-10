package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.gameobjects.Letter;
import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import java.sql.Timestamp;























public abstract class MailDAO
  implements IDFactoryAwareDAO
{
  public String getClassName() {
    return MailDAO.class.getName();
  }
  
  public abstract boolean storeLetter(Timestamp paramTimestamp, Letter paramLetter);
  
  public abstract Mailbox loadPlayerMailbox(Player paramPlayer);
  
  public abstract void storeMailbox(Player paramPlayer);
  
  public abstract boolean deleteLetter(int paramInt);
  
  public abstract void updateOfflineMailCounter(PlayerCommonData paramPlayerCommonData);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\MailDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

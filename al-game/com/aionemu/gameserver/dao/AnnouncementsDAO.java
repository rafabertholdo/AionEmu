package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.Announcement;
import java.util.Set;






























public abstract class AnnouncementsDAO
  implements DAO
{
  public abstract Set<Announcement> getAnnouncements();
  
  public abstract void addAnnouncement(Announcement paramAnnouncement);
  
  public abstract boolean delAnnouncement(int paramInt);
  
  public final String getClassName() {
    return AnnouncementsDAO.class.getName();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\AnnouncementsDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

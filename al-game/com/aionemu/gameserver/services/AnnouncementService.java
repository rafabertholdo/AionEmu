package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.AnnouncementsDAO;
import com.aionemu.gameserver.model.Announcement;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import javolution.util.FastSet;
import org.apache.log4j.Logger;



























public class AnnouncementService
{
  private static final Logger log = Logger.getLogger(AnnouncementService.class);
  
  private Collection<Announcement> announcements;
  private List<Future<?>> delays = new ArrayList<Future<?>>();

  
  private AnnouncementService() {
    load();
  }

  
  public static final AnnouncementService getInstance() {
    return SingletonHolder.instance;
  }





  
  public void reload() {
    if (this.delays != null && this.delays.size() > 0) {
      for (Future<?> delay : this.delays) {
        delay.cancel(false);
      }
    }
    this.announcements.clear();

    
    load();
  }




  
  private void load() {
    this.announcements = (new FastSet(getDAO().getAnnouncements())).shared();
    
    for (Announcement announce : this.announcements) {
      
      this.delays.add(ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
            {
              
              public void run()
              {
                for (Player player : World.getInstance().getAllPlayers()) {
                  
                  if (announce.getFaction().equalsIgnoreCase("ALL")) {
                    if (announce.getChatType() == ChatType.SHOUT || announce.getChatType() == ChatType.GROUP_LEADER) {
                      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, "Automatic Announce", announce.getAnnounce(), announce.getChatType())); continue;
                    } 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, "Automatic Announce", "Automatic Announce: " + announce.getAnnounce(), announce.getChatType())); continue;
                  }  if (announce.getFactionEnum() == player.getCommonData().getRace()) {
                    if (announce.getChatType() == ChatType.SHOUT || announce.getChatType() == ChatType.GROUP_LEADER) {
                      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, (announce.getFaction().equalsIgnoreCase("ELYOS") ? "Elyos" : "Asmodian") + " Automatic Announce", announce.getAnnounce(), announce.getChatType())); continue;
                    } 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, (announce.getFaction().equalsIgnoreCase("ELYOS") ? "Elyos" : "Asmodian") + " Automatic Announce", (announce.getFaction().equalsIgnoreCase("ELYOS") ? "Elyos" : "Asmodian") + " Automatic Announce: " + announce.getAnnounce(), announce.getChatType()));
                  } 
                } 
              }
            }(announce.getDelay() * 1000), (announce.getDelay() * 1000)));
    } 
    log.info("Loaded " + this.announcements.size() + " announcements");
  }

  
  public void addAnnouncement(Announcement announce) {
    getDAO().addAnnouncement(announce);
  }

  
  public boolean delAnnouncement(int idAnnounce) {
    return getDAO().delAnnouncement(idAnnounce);
  }

  
  public Set<Announcement> getAnnouncements() {
    return getDAO().getAnnouncements();
  }






  
  private AnnouncementsDAO getDAO() {
    return (AnnouncementsDAO)DAOManager.getDAO(AnnouncementsDAO.class);
  }

  
  private static class SingletonHolder
  {
    protected static final AnnouncementService instance = new AnnouncementService();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\AnnouncementService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

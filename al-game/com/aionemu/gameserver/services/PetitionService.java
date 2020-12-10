package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PetitionDAO;
import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PETITION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class PetitionService {
  private static Logger log = Logger.getLogger(PetitionService.class);

  private static SortedMap<Integer, Petition> registeredPetitions = new TreeMap<Integer, Petition>();

  public static final PetitionService getInstance() {
    return SingletonHolder.instance;
  }

  public PetitionService() {
    log.info("Loading PetitionService ...");
    Set<Petition> petitions = ((PetitionDAO) DAOManager.getDAO(PetitionDAO.class)).getPetitions();
    for (Petition p : petitions) {
      registeredPetitions.put(Integer.valueOf(p.getPetitionId()), p);
    }
    log.info("Successfully loaded " + registeredPetitions.size() + " database petitions");
  }

  public Collection<Petition> getRegisteredPetitions() {
    return registeredPetitions.values();
  }

  public void deletePetition(int playerObjId) {
    for (Petition p : registeredPetitions.values()) {

      if (p.getPlayerObjId() == playerObjId)
        registeredPetitions.remove(Integer.valueOf(p.getPetitionId()));
    }
    ((PetitionDAO) DAOManager.getDAO(PetitionDAO.class)).deletePetition(playerObjId);
    if (playerObjId > 0 && World.getInstance().findPlayer(playerObjId) != null) {

      Player p = World.getInstance().findPlayer(playerObjId);
      PacketSendUtility.sendPacket(p, (AionServerPacket) new SM_PETITION());
    }
    rebroadcastPlayerData();
  }

  public void setPetitionReplied(int petitionId) {
    int playerObjId = ((Petition) registeredPetitions.get(Integer.valueOf(petitionId))).getPlayerObjId();
    ((PetitionDAO) DAOManager.getDAO(PetitionDAO.class)).setReplied(petitionId);
    registeredPetitions.remove(Integer.valueOf(petitionId));
    rebroadcastPlayerData();
    if (playerObjId > 0 && World.getInstance().findPlayer(playerObjId) != null) {

      Player p = World.getInstance().findPlayer(playerObjId);
      PacketSendUtility.sendPacket(p, (AionServerPacket) new SM_PETITION());
    }
  }

  public synchronized Petition registerPetition(Player sender, int typeId, String title, String contentText,
      String additionalData) {
    int id = ((PetitionDAO) DAOManager.getDAO(PetitionDAO.class)).getNextAvailableId();
    Petition ptt = new Petition(id, sender.getObjectId(), typeId, title, contentText, additionalData, 0);
    ((PetitionDAO) DAOManager.getDAO(PetitionDAO.class)).insertPetition(ptt);
    registeredPetitions.put(Integer.valueOf(ptt.getPetitionId()), ptt);
    broadcastMessageToGM(sender, ptt.getPetitionId());
    return ptt;
  }

  private void rebroadcastPlayerData() {
    for (Petition p : registeredPetitions.values()) {

      Player player = World.getInstance().findPlayer(p.getPlayerObjId());
      if (player != null) {
        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PETITION(p));
      }
    }
  }

  private void broadcastMessageToGM(Player sender, int petitionId) {
    for (Player player : World.getInstance().getAllPlayers()) {

      if (player.getAccessLevel() > 0) {
        PacketSendUtility.sendSysMessage(player,
            "New Support Petition from: " + sender.getName() + " (#" + petitionId + ")");
      }
    }
  }

  public boolean hasRegisteredPetition(Player player) {
    return hasRegisteredPetition(player.getObjectId());
  }

  public boolean hasRegisteredPetition(int playerObjId) {
    boolean result = false;
    for (Petition p : registeredPetitions.values()) {

      if (p.getPlayerObjId() == playerObjId)
        result = true;
    }
    return result;
  }

  public Petition getPetition(int playerObjId) {
    for (Petition p : registeredPetitions.values()) {

      if (p.getPlayerObjId() == playerObjId)
        return p;
    }
    return null;
  }

  public synchronized int getNextAvailablePetitionId() {
    return 0;
  }

  public int getWaitingPlayers(int playerObjId) {
    int counter = 0;
    for (Petition p : registeredPetitions.values()) {

      if (p.getPlayerObjId() == playerObjId)
        break;
      counter++;
    }
    return counter;
  }

  public int calculateWaitTime(int playerObjId) {
    int timePerPetition = 15;
    int timeBetweenPetition = 30;
    int result = timeBetweenPetition;
    for (Petition p : registeredPetitions.values()) {

      if (p.getPlayerObjId() == playerObjId)
        break;
      result += timePerPetition;
      result += timeBetweenPetition;
    }
    return result;
  }

  public void onPlayerLogin(Player player) {
    if (hasRegisteredPetition(player)) {
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PETITION(getPetition(player.getObjectId())));
    }
  }

  private static class SingletonHolder {
    protected static final PetitionService instance = new PetitionService();
  }
}

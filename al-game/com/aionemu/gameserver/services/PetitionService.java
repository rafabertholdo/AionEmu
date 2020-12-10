/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.PetitionDAO;
/*     */ import com.aionemu.gameserver.model.Petition;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PETITION;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PetitionService
/*     */ {
/*  39 */   private static Logger log = Logger.getLogger(PetitionService.class);
/*     */   
/*  41 */   private static SortedMap<Integer, Petition> registeredPetitions = new TreeMap<Integer, Petition>();
/*     */ 
/*     */   
/*     */   public static final PetitionService getInstance() {
/*  45 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public PetitionService() {
/*  50 */     log.info("Loading PetitionService ...");
/*  51 */     Set<Petition> petitions = ((PetitionDAO)DAOManager.getDAO(PetitionDAO.class)).getPetitions();
/*  52 */     for (Petition p : petitions)
/*     */     {
/*  54 */       registeredPetitions.put(Integer.valueOf(p.getPetitionId()), p);
/*     */     }
/*  56 */     log.info("Successfully loaded " + registeredPetitions.size() + " database petitions");
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Petition> getRegisteredPetitions() {
/*  61 */     return registeredPetitions.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deletePetition(int playerObjId) {
/*  66 */     for (Petition p : registeredPetitions.values()) {
/*     */       
/*  68 */       if (p.getPlayerObjId() == playerObjId)
/*  69 */         registeredPetitions.remove(Integer.valueOf(p.getPetitionId())); 
/*     */     } 
/*  71 */     ((PetitionDAO)DAOManager.getDAO(PetitionDAO.class)).deletePetition(playerObjId);
/*  72 */     if (playerObjId > 0 && World.getInstance().findPlayer(playerObjId) != null) {
/*     */       
/*  74 */       Player p = World.getInstance().findPlayer(playerObjId);
/*  75 */       PacketSendUtility.sendPacket(p, (AionServerPacket)new SM_PETITION());
/*     */     } 
/*  77 */     rebroadcastPlayerData();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPetitionReplied(int petitionId) {
/*  82 */     int playerObjId = ((Petition)registeredPetitions.get(Integer.valueOf(petitionId))).getPlayerObjId();
/*  83 */     ((PetitionDAO)DAOManager.getDAO(PetitionDAO.class)).setReplied(petitionId);
/*  84 */     registeredPetitions.remove(Integer.valueOf(petitionId));
/*  85 */     rebroadcastPlayerData();
/*  86 */     if (playerObjId > 0 && World.getInstance().findPlayer(playerObjId) != null) {
/*     */       
/*  88 */       Player p = World.getInstance().findPlayer(playerObjId);
/*  89 */       PacketSendUtility.sendPacket(p, (AionServerPacket)new SM_PETITION());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Petition registerPetition(Player sender, int typeId, String title, String contentText, String additionalData) {
/*  95 */     int id = ((PetitionDAO)DAOManager.getDAO(PetitionDAO.class)).getNextAvailableId();
/*  96 */     Petition ptt = new Petition(id, sender.getObjectId(), typeId, title, contentText, additionalData, 0);
/*  97 */     ((PetitionDAO)DAOManager.getDAO(PetitionDAO.class)).insertPetition(ptt);
/*  98 */     registeredPetitions.put(Integer.valueOf(ptt.getPetitionId()), ptt);
/*  99 */     broadcastMessageToGM(sender, ptt.getPetitionId());
/* 100 */     return ptt;
/*     */   }
/*     */ 
/*     */   
/*     */   private void rebroadcastPlayerData() {
/* 105 */     for (Petition p : registeredPetitions.values()) {
/*     */       
/* 107 */       Player player = World.getInstance().findPlayer(p.getPlayerObjId());
/* 108 */       if (player != null) {
/* 109 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PETITION(p));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void broadcastMessageToGM(Player sender, int petitionId) {
/* 115 */     for (Player player : World.getInstance().getAllPlayers()) {
/*     */       
/* 117 */       if (player.getAccessLevel() > 0)
/*     */       {
/* 119 */         PacketSendUtility.sendSysMessage(player, "New Support Petition from: " + sender.getName() + " (#" + petitionId + ")");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRegisteredPetition(Player player) {
/* 126 */     return hasRegisteredPetition(player.getObjectId());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRegisteredPetition(int playerObjId) {
/* 131 */     boolean result = false;
/* 132 */     for (Petition p : registeredPetitions.values()) {
/*     */       
/* 134 */       if (p.getPlayerObjId() == playerObjId)
/* 135 */         result = true; 
/*     */     } 
/* 137 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Petition getPetition(int playerObjId) {
/* 142 */     for (Petition p : registeredPetitions.values()) {
/*     */       
/* 144 */       if (p.getPlayerObjId() == playerObjId)
/* 145 */         return p; 
/*     */     } 
/* 147 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int getNextAvailablePetitionId() {
/* 152 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWaitingPlayers(int playerObjId) {
/* 157 */     int counter = 0;
/* 158 */     for (Petition p : registeredPetitions.values()) {
/*     */       
/* 160 */       if (p.getPlayerObjId() == playerObjId)
/*     */         break; 
/* 162 */       counter++;
/*     */     } 
/* 164 */     return counter;
/*     */   }
/*     */ 
/*     */   
/*     */   public int calculateWaitTime(int playerObjId) {
/* 169 */     int timePerPetition = 15;
/* 170 */     int timeBetweenPetition = 30;
/* 171 */     int result = timeBetweenPetition;
/* 172 */     for (Petition p : registeredPetitions.values()) {
/*     */       
/* 174 */       if (p.getPlayerObjId() == playerObjId)
/*     */         break; 
/* 176 */       result += timePerPetition;
/* 177 */       result += timeBetweenPetition;
/*     */     } 
/* 179 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlayerLogin(Player player) {
/* 184 */     if (hasRegisteredPetition(player)) {
/* 185 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PETITION(getPetition(player.getObjectId())));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 191 */     protected static final PetitionService instance = new PetitionService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\PetitionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
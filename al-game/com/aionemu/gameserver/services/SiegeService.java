/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.configs.main.SiegeConfig;
/*     */ import com.aionemu.gameserver.dao.SiegeDAO;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.siege.Influence;
/*     */ import com.aionemu.gameserver.model.siege.SiegeLocation;
/*     */ import com.aionemu.gameserver.model.siege.SiegeRace;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SIEGE_LOCATION_INFO;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import javolution.util.FastMap;
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
/*     */ 
/*     */ public class SiegeService
/*     */ {
/*  41 */   private static Logger log = Logger.getLogger(SiegeService.class);
/*     */   private FastMap<Integer, SiegeLocation> locations;
/*     */   
/*     */   public static final SiegeService getInstance() {
/*  45 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SiegeService() {
/*  52 */     if (SiegeConfig.SIEGE_ENABLED) {
/*     */       
/*  54 */       log.info("Loading Siege Location Data...");
/*     */       
/*  56 */       this.locations = DataManager.SIEGE_LOCATION_DATA.getSiegeLocations();
/*     */       
/*  58 */       ((SiegeDAO)DAOManager.getDAO(SiegeDAO.class)).loadSiegeLocations(this.locations);
/*     */     }
/*     */     else {
/*     */       
/*  62 */       log.info("Siege Disabled by Config.");
/*     */       
/*  64 */       this.locations = new FastMap();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FastMap<Integer, SiegeLocation> getSiegeLocations() {
/*  70 */     return this.locations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSiegeTime() {
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SiegeLocation getSiegeLocation(int locationId) {
/*  89 */     return (SiegeLocation)this.locations.get(Integer.valueOf(locationId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void capture(int locationId, SiegeRace race) {
/*  98 */     capture(locationId, race, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void capture(int locationId, SiegeRace race, int legionId) {
/* 110 */     SiegeLocation sLoc = (SiegeLocation)this.locations.get(Integer.valueOf(locationId));
/* 111 */     sLoc.setRace(race);
/* 112 */     sLoc.setLegionId(legionId);
/*     */     
/* 114 */     if (sLoc instanceof com.aionemu.gameserver.model.siege.Fortress) {
/* 115 */       sLoc.setVulnerable(false);
/*     */     }
/* 117 */     ((SiegeDAO)DAOManager.getDAO(SiegeDAO.class)).updateSiegeLocation(sLoc);
/*     */     
/* 119 */     broadcastUpdate(sLoc);
/* 120 */     Influence.getInstance().recalculateInfluence();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastUpdate(SiegeLocation loc) {
/* 128 */     SM_SIEGE_LOCATION_INFO pkt = new SM_SIEGE_LOCATION_INFO(loc);
/* 129 */     broadcast(pkt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastUpdate() {
/* 134 */     SM_SIEGE_LOCATION_INFO pkt = new SM_SIEGE_LOCATION_INFO();
/* 135 */     broadcast(pkt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void broadcast(SM_SIEGE_LOCATION_INFO pkt) {
/* 140 */     for (Player player : World.getInstance().getAllPlayers())
/*     */     {
/* 142 */       PacketSendUtility.sendPacket(player, (AionServerPacket)pkt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 150 */     protected static final SiegeService instance = new SiegeService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\SiegeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
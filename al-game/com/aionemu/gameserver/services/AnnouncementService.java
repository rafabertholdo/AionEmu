/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.AnnouncementsDAO;
/*     */ import com.aionemu.gameserver.model.Announcement;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
/*     */ import javolution.util.FastSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnouncementService
/*     */ {
/*  49 */   private static final Logger log = Logger.getLogger(AnnouncementService.class);
/*     */   
/*     */   private Collection<Announcement> announcements;
/*  52 */   private List<Future<?>> delays = new ArrayList<Future<?>>();
/*     */ 
/*     */   
/*     */   private AnnouncementService() {
/*  56 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final AnnouncementService getInstance() {
/*  61 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reload() {
/*  70 */     if (this.delays != null && this.delays.size() > 0) {
/*  71 */       for (Future<?> delay : this.delays) {
/*  72 */         delay.cancel(false);
/*     */       }
/*     */     }
/*  75 */     this.announcements.clear();
/*     */ 
/*     */     
/*  78 */     load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void load() {
/*  86 */     this.announcements = (new FastSet(getDAO().getAnnouncements())).shared();
/*     */     
/*  88 */     for (Announcement announce : this.announcements) {
/*     */       
/*  90 */       this.delays.add(ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*     */             {
/*     */               
/*     */               public void run()
/*     */               {
/*  95 */                 for (Player player : World.getInstance().getAllPlayers()) {
/*     */                   
/*  97 */                   if (announce.getFaction().equalsIgnoreCase("ALL")) {
/*  98 */                     if (announce.getChatType() == ChatType.SHOUT || announce.getChatType() == ChatType.GROUP_LEADER) {
/*  99 */                       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, "Automatic Announce", announce.getAnnounce(), announce.getChatType())); continue;
/*     */                     } 
/* 101 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, "Automatic Announce", "Automatic Announce: " + announce.getAnnounce(), announce.getChatType())); continue;
/* 102 */                   }  if (announce.getFactionEnum() == player.getCommonData().getRace()) {
/* 103 */                     if (announce.getChatType() == ChatType.SHOUT || announce.getChatType() == ChatType.GROUP_LEADER) {
/* 104 */                       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, (announce.getFaction().equalsIgnoreCase("ELYOS") ? "Elyos" : "Asmodian") + " Automatic Announce", announce.getAnnounce(), announce.getChatType())); continue;
/*     */                     } 
/* 106 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(1, (announce.getFaction().equalsIgnoreCase("ELYOS") ? "Elyos" : "Asmodian") + " Automatic Announce", (announce.getFaction().equalsIgnoreCase("ELYOS") ? "Elyos" : "Asmodian") + " Automatic Announce: " + announce.getAnnounce(), announce.getChatType()));
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             }(announce.getDelay() * 1000), (announce.getDelay() * 1000)));
/*     */     } 
/* 112 */     log.info("Loaded " + this.announcements.size() + " announcements");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAnnouncement(Announcement announce) {
/* 117 */     getDAO().addAnnouncement(announce);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean delAnnouncement(int idAnnounce) {
/* 122 */     return getDAO().delAnnouncement(idAnnounce);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Announcement> getAnnouncements() {
/* 127 */     return getDAO().getAnnouncements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnouncementsDAO getDAO() {
/* 137 */     return (AnnouncementsDAO)DAOManager.getDAO(AnnouncementsDAO.class);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 143 */     protected static final AnnouncementService instance = new AnnouncementService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\AnnouncementService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
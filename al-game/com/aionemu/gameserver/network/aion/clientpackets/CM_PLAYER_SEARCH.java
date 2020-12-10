/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.FriendList;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SEARCH;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.Util;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class CM_PLAYER_SEARCH
/*     */   extends AionClientPacket
/*     */ {
/*     */   public static final int MAX_RESULTS = 124;
/*     */   private String name;
/*     */   private int region;
/*     */   private int classMask;
/*     */   private int minLevel;
/*     */   private int maxLevel;
/*     */   private int lfgOnly;
/*     */   
/*     */   public CM_PLAYER_SEARCH(int opcode) {
/*  53 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  62 */     if (!(this.name = readS()).isEmpty()) {
/*     */       
/*  64 */       this.name = Util.convertName(this.name);
/*  65 */       readB(44 - this.name.length() * 2 + 2);
/*     */     }
/*     */     else {
/*     */       
/*  69 */       readB(42);
/*     */     } 
/*  71 */     this.region = readD();
/*  72 */     this.classMask = readD();
/*  73 */     this.minLevel = readC();
/*  74 */     this.maxLevel = readC();
/*  75 */     this.lfgOnly = readC();
/*  76 */     readC();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*  85 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*     */     
/*  87 */     List<Player> matches = new ArrayList<Player>(124);
/*     */     
/*  89 */     if (activePlayer != null && activePlayer.getLevel() < 10) {
/*     */       
/*  91 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.LEVEL_NOT_ENOUGH_FOR_SEARCH("10"));
/*     */       return;
/*     */     } 
/*  94 */     for (Player player : World.getInstance().getAllPlayers()) {
/*     */       
/*  96 */       if (matches.size() > 124)
/*     */         return; 
/*  98 */       if (!player.isSpawned())
/*     */         continue; 
/* 100 */       if (player.getFriendList().getStatus() == FriendList.Status.OFFLINE)
/*     */         continue; 
/* 102 */       if (this.lfgOnly == 1 && !player.isLookingForGroup())
/*     */         continue; 
/* 104 */       if (!this.name.isEmpty() && !player.getName().toLowerCase().contains(this.name.toLowerCase()))
/*     */         continue; 
/* 106 */       if (this.minLevel != 255 && player.getLevel() < this.minLevel)
/*     */         continue; 
/* 108 */       if (this.maxLevel != 255 && player.getLevel() > this.maxLevel)
/*     */         continue; 
/* 110 */       if (this.classMask > 0 && (player.getPlayerClass().getMask() & this.classMask) == 0)
/*     */         continue; 
/* 112 */       if (this.region > 0 && player.getActiveRegion().getMapId().intValue() != this.region)
/*     */         continue; 
/* 114 */       if (player.getCommonData().getRace() != activePlayer.getCommonData().getRace() && !CustomConfig.FACTIONS_SEARCH_MODE) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 119 */       matches.add(player);
/*     */     } 
/*     */ 
/*     */     
/* 123 */     sendPacket((AionServerPacket)new SM_PLAYER_SEARCH(matches, this.region));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_PLAYER_SEARCH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
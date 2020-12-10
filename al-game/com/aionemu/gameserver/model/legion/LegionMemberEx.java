/*     */ package com.aionemu.gameserver.model.legion;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import java.sql.Timestamp;
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
/*     */ public class LegionMemberEx
/*     */   extends LegionMember
/*     */ {
/*  33 */   private static Logger log = Logger.getLogger(LegionMemberEx.class);
/*     */   
/*     */   private String name;
/*     */   
/*     */   private PlayerClass playerClass;
/*     */   
/*     */   private int level;
/*     */   
/*     */   private Timestamp lastOnline;
/*     */   
/*     */   private int worldId;
/*     */   private boolean online = false;
/*     */   
/*     */   public LegionMemberEx(Player player, LegionMember legionMember, boolean online) {
/*  47 */     super(player.getObjectId(), legionMember.getLegion(), legionMember.getRank());
/*  48 */     this.nickname = legionMember.getNickname();
/*  49 */     this.selfIntro = legionMember.getSelfIntro();
/*  50 */     this.name = player.getName();
/*  51 */     this.playerClass = player.getPlayerClass();
/*  52 */     this.level = player.getLevel();
/*  53 */     this.lastOnline = player.getCommonData().getLastOnline();
/*  54 */     this.worldId = player.getPosition().getMapId();
/*  55 */     this.online = online;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMemberEx(int playerObjId) {
/*  63 */     super(playerObjId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMemberEx(String name) {
/*  72 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  82 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerClass getPlayerClass() {
/*  87 */     return this.playerClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerClass(PlayerClass playerClass) {
/*  92 */     this.playerClass = playerClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastOnline() {
/*  97 */     if (this.lastOnline == null || isOnline())
/*  98 */       return 0; 
/*  99 */     return (int)(this.lastOnline.getTime() / 1000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastOnline(Timestamp timestamp) {
/* 104 */     this.lastOnline = timestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 109 */     return this.level;
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
/*     */   public void setExp(long exp) {
/* 121 */     int maxLevel = DataManager.PLAYER_EXPERIENCE_TABLE.getMaxLevel();
/*     */     
/* 123 */     if (getPlayerClass() != null && getPlayerClass().isStartingClass()) {
/* 124 */       maxLevel = 10;
/*     */     }
/* 126 */     long maxExp = DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(maxLevel);
/* 127 */     int level = 1;
/*     */     
/* 129 */     if (exp > maxExp)
/*     */     {
/* 131 */       exp = maxExp;
/*     */     }
/*     */ 
/*     */     
/* 135 */     while (level + 1 != maxLevel && exp >= DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(level + 1))
/*     */     {
/* 137 */       level++;
/*     */     }
/*     */     
/* 140 */     this.level = level;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWorldId() {
/* 145 */     return this.worldId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldId(int worldId) {
/* 150 */     this.worldId = worldId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnline(boolean online) {
/* 159 */     this.online = online;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnline() {
/* 167 */     return this.online;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sameObjectId(int objectId) {
/* 172 */     return (getObjectId() == objectId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidLegionMemberEx() {
/* 182 */     if (getObjectId() < 1) {
/*     */       
/* 184 */       log.error("[LegionMemberEx] Player Object ID is empty.");
/*     */     }
/* 186 */     else if (getName() == null) {
/*     */       
/* 188 */       log.error("[LegionMemberEx] Player Name is empty." + getObjectId());
/*     */     }
/* 190 */     else if (getPlayerClass() == null) {
/*     */       
/* 192 */       log.error("[LegionMemberEx] Player Class is empty." + getObjectId());
/*     */     }
/* 194 */     else if (getLevel() < 1) {
/*     */       
/* 196 */       log.error("[LegionMemberEx] Player Level is empty." + getObjectId());
/*     */     }
/* 198 */     else if (getLastOnline() == 0) {
/*     */       
/* 200 */       log.error("[LegionMemberEx] Last Online is empty." + getObjectId());
/*     */     }
/* 202 */     else if (getWorldId() < 1) {
/*     */       
/* 204 */       log.error("[LegionMemberEx] World Id is empty." + getObjectId());
/*     */     }
/* 206 */     else if (getLegion() == null) {
/*     */       
/* 208 */       log.error("[LegionMemberEx] Legion is empty." + getObjectId());
/*     */     }
/* 210 */     else if (getRank() == null) {
/*     */       
/* 212 */       log.error("[LegionMemberEx] Rank is empty." + getObjectId());
/*     */     }
/* 214 */     else if (getNickname() == null) {
/*     */       
/* 216 */       log.error("[LegionMemberEx] Nickname is empty." + getObjectId());
/*     */     }
/* 218 */     else if (getSelfIntro() == null) {
/*     */       
/* 220 */       log.error("[LegionMemberEx] Self Intro is empty." + getObjectId());
/*     */     }
/*     */     else {
/*     */       
/* 224 */       return true;
/*     */     } 
/* 226 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionMemberEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
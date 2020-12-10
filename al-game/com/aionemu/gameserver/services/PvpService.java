/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.configs.main.GroupConfig;
/*     */ import com.aionemu.gameserver.controllers.attack.AggroInfo;
/*     */ import com.aionemu.gameserver.controllers.attack.KillList;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
/*     */ import com.aionemu.gameserver.utils.stats.StatFunctions;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javolution.util.FastMap;
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
/*     */ public class PvpService
/*     */ {
/*     */   private FastMap<Integer, KillList> pvpKillLists;
/*     */   
/*     */   public static final PvpService getInstance() {
/*  46 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PvpService() {
/*  53 */     this.pvpKillLists = new FastMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getKillsFor(int winnerId, int victimId) {
/*  63 */     KillList winnerKillList = (KillList)this.pvpKillLists.get(Integer.valueOf(winnerId));
/*     */     
/*  65 */     if (winnerKillList == null)
/*  66 */       return 0; 
/*  67 */     return winnerKillList.getKillsFor(victimId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addKillFor(int winnerId, int victimId) {
/*  76 */     KillList winnerKillList = (KillList)this.pvpKillLists.get(Integer.valueOf(winnerId));
/*  77 */     if (winnerKillList == null) {
/*     */       
/*  79 */       winnerKillList = new KillList();
/*  80 */       this.pvpKillLists.put(Integer.valueOf(winnerId), winnerKillList);
/*     */     } 
/*  82 */     winnerKillList.addKillFor(victimId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doReward(Player victim) {
/*  91 */     Player winner = victim.getAggroList().getMostPlayerDamage();
/*     */     
/*  93 */     int totalDamage = victim.getAggroList().getTotalDamage();
/*     */     
/*  95 */     if (totalDamage == 0 || winner == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (getKillsFor(winner.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
/* 102 */       winner.getAbyssRank().setAllKill();
/*     */     }
/*     */     
/* 105 */     PacketSendUtility.broadcastPacketAndReceive((VisibleObject)victim, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_COMBAT_FRIENDLY_DEATH_TO_B(victim.getName(), winner.getName()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     int playerDamage = 0;
/* 111 */     boolean success = false;
/*     */ 
/*     */     
/* 114 */     for (AggroInfo aggro : victim.getAggroList().getFinalDamageList(true)) {
/*     */       
/* 116 */       if (aggro.getAttacker() instanceof Player) {
/*     */         
/* 118 */         success = rewardPlayer(victim, totalDamage, aggro);
/*     */       }
/* 120 */       else if (aggro.getAttacker() instanceof PlayerGroup) {
/*     */         
/* 122 */         success = rewardPlayerGroup(victim, totalDamage, aggro);
/*     */       }
/* 124 */       else if (aggro.getAttacker() instanceof PlayerAlliance) {
/*     */         
/* 126 */         success = rewardPlayerAlliance(victim, totalDamage, aggro);
/*     */       } 
/*     */ 
/*     */       
/* 130 */       if (success) {
/* 131 */         playerDamage += aggro.getDamage();
/*     */       }
/*     */     } 
/*     */     
/* 135 */     int apLost = StatFunctions.calculatePvPApLost(victim, winner);
/* 136 */     int apActuallyLost = apLost * playerDamage / totalDamage;
/*     */     
/* 138 */     if (apActuallyLost > 0) {
/* 139 */       victim.getCommonData().addAp(-apActuallyLost);
/*     */     }
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
/*     */   
/*     */   private boolean rewardPlayerGroup(Player victim, int totalDamage, AggroInfo aggro) {
/* 153 */     PlayerGroup group = (PlayerGroup)aggro.getAttacker();
/*     */ 
/*     */ 
/*     */     
/* 157 */     if (group.getGroupLeader().getCommonData().getRace() == victim.getCommonData().getRace()) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     List<Player> players = new ArrayList<Player>();
/*     */ 
/*     */     
/* 164 */     int maxRank = AbyssRankEnum.GRADE9_SOLDIER.getId();
/* 165 */     int maxLevel = 0;
/*     */     
/* 167 */     for (Player member : group.getMembers()) {
/*     */       
/* 169 */       if (MathUtil.isIn3dRange((VisibleObject)member, (VisibleObject)victim, GroupConfig.GROUP_MAX_DISTANCE))
/*     */       {
/*     */         
/* 172 */         if (!member.getLifeStats().isAlreadyDead()) {
/*     */           
/* 174 */           players.add(member);
/* 175 */           if (member.getLevel() > maxLevel)
/* 176 */             maxLevel = member.getLevel(); 
/* 177 */           if (member.getAbyssRank().getRank().getId() > maxRank) {
/* 178 */             maxRank = member.getAbyssRank().getRank().getId();
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 184 */     if (players.size() == 0) {
/* 185 */       return false;
/*     */     }
/* 187 */     int baseApReward = StatFunctions.calculatePvpApGained(victim, maxRank, maxLevel);
/* 188 */     float groupApPercentage = aggro.getDamage() / totalDamage;
/* 189 */     int apRewardPerMember = Math.round(baseApReward * groupApPercentage / players.size());
/*     */     
/* 191 */     if (apRewardPerMember > 0)
/*     */     {
/* 193 */       for (Player member : players) {
/*     */         
/* 195 */         int memberApGain = 1;
/* 196 */         if (getKillsFor(member.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS)
/* 197 */           memberApGain = Math.round(apRewardPerMember * member.getRates().getApPlayerRate()); 
/* 198 */         member.getCommonData().addAp(memberApGain);
/* 199 */         addKillFor(member.getObjectId(), victim.getObjectId());
/*     */       } 
/*     */     }
/*     */     
/* 203 */     return true;
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
/*     */   
/*     */   private boolean rewardPlayerAlliance(Player victim, int totalDamage, AggroInfo aggro) {
/* 216 */     PlayerAlliance alliance = (PlayerAlliance)aggro.getAttacker();
/*     */ 
/*     */     
/* 219 */     if (alliance.getCaptain().getCommonData().getRace() == victim.getCommonData().getRace()) {
/* 220 */       return false;
/*     */     }
/*     */     
/* 223 */     List<Player> players = new ArrayList<Player>();
/*     */ 
/*     */     
/* 226 */     int maxRank = AbyssRankEnum.GRADE9_SOLDIER.getId();
/* 227 */     int maxLevel = 0;
/*     */     
/* 229 */     for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */       
/* 231 */       if (!allianceMember.isOnline())
/* 232 */         continue;  Player member = allianceMember.getPlayer();
/* 233 */       if (MathUtil.isIn3dRange((VisibleObject)member, (VisibleObject)victim, GroupConfig.GROUP_MAX_DISTANCE))
/*     */       {
/*     */         
/* 236 */         if (!member.getLifeStats().isAlreadyDead()) {
/*     */           
/* 238 */           players.add(member);
/* 239 */           if (member.getLevel() > maxLevel)
/* 240 */             maxLevel = member.getLevel(); 
/* 241 */           if (member.getAbyssRank().getRank().getId() > maxRank) {
/* 242 */             maxRank = member.getAbyssRank().getRank().getId();
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 248 */     if (players.size() == 0) {
/* 249 */       return false;
/*     */     }
/* 251 */     int baseApReward = StatFunctions.calculatePvpApGained(victim, maxRank, maxLevel);
/* 252 */     float groupApPercentage = aggro.getDamage() / totalDamage;
/* 253 */     int apRewardPerMember = Math.round(baseApReward * groupApPercentage / players.size());
/*     */     
/* 255 */     if (apRewardPerMember > 0)
/*     */     {
/* 257 */       for (Player member : players) {
/*     */         
/* 259 */         int memberApGain = 1;
/* 260 */         if (getKillsFor(member.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS)
/* 261 */           memberApGain = Math.round(apRewardPerMember * member.getRates().getApPlayerRate()); 
/* 262 */         member.getCommonData().addAp(memberApGain);
/* 263 */         addKillFor(member.getObjectId(), victim.getObjectId());
/*     */       } 
/*     */     }
/*     */     
/* 267 */     return true;
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
/*     */   
/*     */   private boolean rewardPlayer(Player victim, int totalDamage, AggroInfo aggro) {
/* 280 */     Player winner = (Player)aggro.getAttacker();
/*     */ 
/*     */     
/* 283 */     if (winner.getCommonData().getRace() == victim.getCommonData().getRace()) {
/* 284 */       return false;
/*     */     }
/* 286 */     int baseApReward = 1;
/*     */     
/* 288 */     if (getKillsFor(winner.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
/* 289 */       baseApReward = StatFunctions.calculatePvpApGained(victim, winner.getAbyssRank().getRank().getId(), winner.getLevel());
/*     */     }
/* 291 */     int apPlayerReward = Math.round(baseApReward * winner.getRates().getApPlayerRate() * aggro.getDamage() / totalDamage);
/*     */     
/* 293 */     winner.getCommonData().addAp(apPlayerReward);
/* 294 */     addKillFor(winner.getObjectId(), victim.getObjectId());
/*     */     
/* 296 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 302 */     protected static final PvpService instance = new PvpService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\PvpService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
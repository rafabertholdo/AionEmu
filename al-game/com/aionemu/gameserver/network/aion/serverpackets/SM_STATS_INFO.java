/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.utils.gametime.GameTimeManager;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SM_STATS_INFO
/*     */   extends AionServerPacket
/*     */ {
/*     */   private Player player;
/*     */   private PlayerGameStats pgs;
/*     */   private PlayerLifeStats pls;
/*     */   private PlayerCommonData pcd;
/*     */   
/*     */   public SM_STATS_INFO(Player player) {
/*  53 */     this.player = player;
/*  54 */     this.pcd = player.getCommonData();
/*  55 */     this.pgs = player.getGameStats();
/*  56 */     this.pls = player.getLifeStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  65 */     writeD(buf, this.player.getObjectId());
/*  66 */     writeD(buf, GameTimeManager.getGameTime().getTime());
/*     */     
/*  68 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.POWER));
/*  69 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.HEALTH));
/*  70 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.ACCURACY));
/*  71 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.AGILITY));
/*  72 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.KNOWLEDGE));
/*  73 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.WILL));
/*     */     
/*  75 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.WATER_RESISTANCE));
/*  76 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.WIND_RESISTANCE));
/*  77 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.EARTH_RESISTANCE));
/*  78 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.FIRE_RESISTANCE));
/*  79 */     writeH(buf, 0);
/*  80 */     writeH(buf, 0);
/*     */     
/*  82 */     writeH(buf, this.player.getLevel());
/*     */ 
/*     */     
/*  85 */     writeH(buf, 0);
/*  86 */     writeH(buf, 0);
/*  87 */     writeH(buf, 0);
/*     */     
/*  89 */     writeQ(buf, this.pcd.getExpNeed());
/*  90 */     writeQ(buf, this.pcd.getExpRecoverable());
/*  91 */     writeQ(buf, this.pcd.getExpShown());
/*     */     
/*  93 */     writeD(buf, 0);
/*  94 */     writeD(buf, this.pgs.getCurrentStat(StatEnum.MAXHP));
/*  95 */     writeD(buf, this.pls.getCurrentHp());
/*     */     
/*  97 */     writeD(buf, this.pgs.getCurrentStat(StatEnum.MAXMP));
/*  98 */     writeD(buf, this.pls.getCurrentMp());
/*     */     
/* 100 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAXDP));
/* 101 */     writeH(buf, this.pcd.getDp());
/*     */     
/* 103 */     writeD(buf, this.pgs.getCurrentStat(StatEnum.FLY_TIME));
/*     */     
/* 105 */     writeD(buf, this.pls.getCurrentFp());
/*     */     
/* 107 */     writeC(buf, this.player.getFlyState());
/* 108 */     writeC(buf, 0);
/*     */     
/* 110 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_POWER));
/*     */     
/* 112 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.OFF_HAND_POWER));
/*     */     
/* 114 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.PHYSICAL_DEFENSE));
/*     */     
/* 116 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_POWER));
/*     */     
/* 118 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_RESIST));
/*     */     
/* 120 */     writeF(buf, this.pgs.getCurrentStat(StatEnum.ATTACK_RANGE) / 1000.0F);
/* 121 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.ATTACK_SPEED));
/* 122 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.EVASION));
/* 123 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.PARRY));
/* 124 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.BLOCK));
/*     */     
/* 126 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_CRITICAL));
/* 127 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.OFF_HAND_CRITICAL));
/*     */     
/* 129 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_ACCURACY));
/* 130 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.OFF_HAND_ACCURACY));
/*     */     
/* 132 */     writeH(buf, 0);
/* 133 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_ACCURACY));
/* 134 */     writeH(buf, 0);
/* 135 */     writeH(buf, 0);
/*     */     
/* 137 */     writeH(buf, 0);
/* 138 */     writeH(buf, 16256);
/* 139 */     writeH(buf, 40);
/* 140 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_ATTACK) + this.pgs.getCurrentStat(StatEnum.BOOST_MAGICAL_SKILL));
/* 141 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.BOOST_HEAL) - 100);
/* 142 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.CRITICAL_RESIST));
/* 143 */     writeH(buf, 0);
/* 144 */     writeH(buf, 0);
/* 145 */     writeH(buf, 0);
/* 146 */     writeH(buf, 20511);
/*     */     
/* 148 */     writeD(buf, 27 + this.player.getCubeSize() * 9);
/*     */     
/* 150 */     writeD(buf, this.player.getInventory().size());
/* 151 */     writeD(buf, 0);
/* 152 */     writeD(buf, 0);
/* 153 */     writeD(buf, this.pcd.getPlayerClass().getClassId());
/*     */     
/* 155 */     writeQ(buf, 0L);
/* 156 */     writeQ(buf, 0L);
/* 157 */     writeQ(buf, 251141L);
/* 158 */     writeQ(buf, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     writeH(buf, this.pgs.getBaseStat(StatEnum.POWER));
/* 164 */     writeH(buf, this.pgs.getBaseStat(StatEnum.HEALTH));
/*     */     
/* 166 */     writeH(buf, this.pgs.getBaseStat(StatEnum.ACCURACY));
/* 167 */     writeH(buf, this.pgs.getBaseStat(StatEnum.AGILITY));
/*     */     
/* 169 */     writeH(buf, this.pgs.getBaseStat(StatEnum.KNOWLEDGE));
/* 170 */     writeH(buf, this.pgs.getBaseStat(StatEnum.WILL));
/*     */     
/* 172 */     writeH(buf, this.pgs.getBaseStat(StatEnum.WATER_RESISTANCE));
/* 173 */     writeH(buf, this.pgs.getBaseStat(StatEnum.WIND_RESISTANCE));
/*     */     
/* 175 */     writeH(buf, this.pgs.getBaseStat(StatEnum.EARTH_RESISTANCE));
/* 176 */     writeH(buf, this.pgs.getBaseStat(StatEnum.FIRE_RESISTANCE));
/*     */     
/* 178 */     writeD(buf, 0);
/*     */     
/* 180 */     writeD(buf, this.pgs.getBaseStat(StatEnum.MAXHP));
/*     */     
/* 182 */     writeD(buf, this.pgs.getBaseStat(StatEnum.MAXMP));
/*     */     
/* 184 */     writeD(buf, this.pgs.getBaseStat(StatEnum.MAXDP));
/* 185 */     writeD(buf, this.pgs.getBaseStat(StatEnum.FLY_TIME));
/*     */     
/* 187 */     writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_POWER));
/* 188 */     writeH(buf, this.pgs.getBaseStat(StatEnum.OFF_HAND_POWER));
/*     */     
/* 190 */     writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_POWER));
/* 191 */     writeH(buf, this.pgs.getBaseStat(StatEnum.PHYSICAL_DEFENSE));
/*     */     
/* 193 */     writeH(buf, this.pgs.getBaseStat(StatEnum.MAGICAL_RESIST));
/*     */     
/* 195 */     writeH(buf, 0);
/*     */     
/* 197 */     writeF(buf, this.pgs.getCurrentStat(StatEnum.ATTACK_RANGE) / 1000.0F);
/*     */     
/* 199 */     writeH(buf, this.pgs.getBaseStat(StatEnum.EVASION));
/*     */     
/* 201 */     writeH(buf, this.pgs.getBaseStat(StatEnum.PARRY));
/*     */     
/* 203 */     writeH(buf, this.pgs.getBaseStat(StatEnum.BLOCK));
/*     */     
/* 205 */     writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_CRITICAL));
/* 206 */     writeH(buf, this.pgs.getBaseStat(StatEnum.OFF_HAND_CRITICAL));
/*     */     
/* 208 */     writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_CRITICAL));
/* 209 */     writeH(buf, 0);
/*     */     
/* 211 */     writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_ACCURACY));
/* 212 */     writeH(buf, this.pgs.getBaseStat(StatEnum.OFF_HAND_ACCURACY));
/*     */     
/* 214 */     writeH(buf, 0);
/*     */     
/* 216 */     writeH(buf, this.pgs.getBaseStat(StatEnum.MAGICAL_ACCURACY));
/*     */     
/* 218 */     writeH(buf, 0);
/* 219 */     writeH(buf, this.pgs.getBaseStat(StatEnum.MAGICAL_ATTACK) + this.pgs.getBaseStat(StatEnum.BOOST_MAGICAL_SKILL));
/*     */     
/* 221 */     writeH(buf, this.pgs.getBaseStat(StatEnum.BOOST_HEAL) - 100);
/* 222 */     writeH(buf, this.pgs.getBaseStat(StatEnum.CRITICAL_RESIST));
/* 223 */     writeH(buf, 0);
/* 224 */     writeH(buf, 0);
/* 225 */     writeH(buf, 0);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_STATS_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
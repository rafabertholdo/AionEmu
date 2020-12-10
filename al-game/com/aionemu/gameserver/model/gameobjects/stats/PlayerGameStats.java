/*     */ package com.aionemu.gameserver.model.gameobjects.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.dataholders.PlayerStatsData;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class PlayerGameStats
/*     */   extends CreatureGameStats<Player>
/*     */ {
/*  34 */   private int currentRunSpeed = 0;
/*  35 */   private int currentFlySpeed = 0;
/*  36 */   private int currentAttackSpeed = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerGameStats(Player owner) {
/*  43 */     super(owner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerGameStats(PlayerStatsData playerStatsData, Player owner) {
/*  53 */     super(owner);
/*  54 */     PlayerStatsTemplate pst = playerStatsData.getTemplate(owner.getPlayerClass(), owner.getLevel());
/*  55 */     initStats(pst, owner.getLevel());
/*  56 */     log.debug("loading base game stats for player " + owner.getName() + " (id " + owner.getObjectId() + "): " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recomputeStats() {
/*  64 */     super.recomputeStats();
/*  65 */     int newRunSpeed = getCurrentStat(StatEnum.SPEED);
/*  66 */     int newFlySpeed = getCurrentStat(StatEnum.FLY_SPEED);
/*  67 */     int newAttackSpeed = getCurrentStat(StatEnum.ATTACK_SPEED);
/*     */     
/*  69 */     if (newRunSpeed != this.currentRunSpeed || this.currentFlySpeed != newFlySpeed || newAttackSpeed != this.currentAttackSpeed)
/*     */     {
/*  71 */       PacketSendUtility.broadcastPacket(this.owner, (AionServerPacket)new SM_EMOTION((Creature)this.owner, EmotionType.START_EMOTE2, 0, 0), true);
/*     */     }
/*     */     
/*  74 */     PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_STATS_INFO(this.owner));
/*     */     
/*  76 */     this.currentRunSpeed = newRunSpeed;
/*  77 */     this.currentFlySpeed = newFlySpeed;
/*  78 */     this.currentAttackSpeed = newAttackSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initStats(PlayerStatsTemplate pst, int level) {
/*  88 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/*  91 */       initStats(pst.getMaxHp(), pst.getMaxMp(), pst.getPower(), pst.getHealth(), pst.getAgility(), pst.getAccuracy(), pst.getKnowledge(), pst.getWill(), pst.getMainHandAttack(), pst.getMainHandCritRate(), Math.round(pst.getAttackSpeed() * 1000.0F), 1500, Math.round(pst.getRunSpeed() * 1000.0F), Math.round(pst.getFlySpeed() * 1000.0F));
/*     */ 
/*     */       
/*  94 */       setAttackCounter(1);
/*  95 */       initStat(StatEnum.PARRY, pst.getParry());
/*  96 */       initStat(StatEnum.BLOCK, pst.getBlock());
/*  97 */       initStat(StatEnum.EVASION, pst.getEvasion());
/*  98 */       initStat(StatEnum.MAGICAL_ACCURACY, pst.getMagicAccuracy());
/*  99 */       initStat(StatEnum.MAIN_HAND_ACCURACY, pst.getMainHandAccuracy());
/* 100 */       initStat(StatEnum.FLY_TIME, CustomConfig.BASE_FLYTIME);
/* 101 */       initStat(StatEnum.REGEN_HP, level + 3);
/* 102 */       initStat(StatEnum.REGEN_MP, level + 8);
/* 103 */       initStat(StatEnum.MAXDP, 4000);
/*     */     }
/*     */     finally {
/*     */       
/* 107 */       this.lock.writeLock().unlock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initStats(int maxHp, int maxMp, int power, int health, int agility, int accuracy, int knowledge, int will, int mainHandAttack, int mainHandCritRate, int attackSpeed, int attackRange, int runSpeed, int flySpeed) {
/* 130 */     this.stats.clear();
/* 131 */     initStat(StatEnum.MAXHP, maxHp);
/* 132 */     initStat(StatEnum.MAXMP, maxMp);
/* 133 */     initStat(StatEnum.POWER, power);
/* 134 */     initStat(StatEnum.ACCURACY, accuracy);
/* 135 */     initStat(StatEnum.HEALTH, health);
/* 136 */     initStat(StatEnum.AGILITY, agility);
/* 137 */     initStat(StatEnum.KNOWLEDGE, knowledge);
/* 138 */     initStat(StatEnum.WILL, will);
/* 139 */     initStat(StatEnum.MAIN_HAND_POWER, Math.round(18.0F * power * 0.01F));
/* 140 */     initStat(StatEnum.MAIN_HAND_CRITICAL, mainHandCritRate);
/* 141 */     initStat(StatEnum.OFF_HAND_POWER, 0);
/* 142 */     initStat(StatEnum.OFF_HAND_CRITICAL, 0);
/* 143 */     initStat(StatEnum.ATTACK_SPEED, attackSpeed);
/* 144 */     initStat(StatEnum.MAIN_HAND_ATTACK_SPEED, attackSpeed);
/* 145 */     initStat(StatEnum.OFF_HAND_ATTACK_SPEED, 0);
/* 146 */     initStat(StatEnum.ATTACK_RANGE, attackRange);
/* 147 */     initStat(StatEnum.PHYSICAL_DEFENSE, 0);
/* 148 */     initStat(StatEnum.PARRY, Math.round(agility * 3.1F - 248.5F + 12.4F * this.owner.getLevel()));
/* 149 */     initStat(StatEnum.EVASION, Math.round(agility * 3.1F - 248.5F + 12.4F * this.owner.getLevel()));
/* 150 */     initStat(StatEnum.BLOCK, Math.round(agility * 3.1F - 248.5F + 12.4F * this.owner.getLevel()));
/* 151 */     initStat(StatEnum.DAMAGE_REDUCE, 0);
/* 152 */     initStat(StatEnum.MAIN_HAND_ACCURACY, Math.round((accuracy * 2 - 10 + 8 * this.owner.getLevel())));
/* 153 */     initStat(StatEnum.OFF_HAND_ACCURACY, Math.round((accuracy * 2 - 10 + 8 * this.owner.getLevel())));
/* 154 */     initStat(StatEnum.MAGICAL_RESIST, 0);
/* 155 */     initStat(StatEnum.WIND_RESISTANCE, 0);
/* 156 */     initStat(StatEnum.FIRE_RESISTANCE, 0);
/* 157 */     initStat(StatEnum.WATER_RESISTANCE, 0);
/* 158 */     initStat(StatEnum.EARTH_RESISTANCE, 0);
/* 159 */     initStat(StatEnum.MAGICAL_ACCURACY, Math.round(14.26F * this.owner.getLevel()));
/* 160 */     initStat(StatEnum.BOOST_MAGICAL_SKILL, 0);
/* 161 */     initStat(StatEnum.SPEED, runSpeed);
/* 162 */     initStat(StatEnum.FLY_SPEED, flySpeed);
/* 163 */     initStat(StatEnum.PVP_ATTACK_RATIO, 0);
/* 164 */     initStat(StatEnum.PVP_DEFEND_RATIO, 0);
/* 165 */     initStat(StatEnum.BOOST_CASTING_TIME, 100);
/* 166 */     initStat(StatEnum.BOOST_HATE, 100);
/* 167 */     initStat(StatEnum.BOOST_HEAL, 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLevelUpgrade() {
/* 177 */     initStats(this.owner.getPlayerStatsTemplate(), this.owner.getLevel());
/* 178 */     recomputeStats();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\PlayerGameStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.utils.stats;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.configs.main.FallDamageConfig;
/*     */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*     */ import com.aionemu.gameserver.model.SkillElement;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Equipment;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SimpleModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*     */ import com.aionemu.gameserver.model.templates.stats.NpcRank;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.TreeSet;
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
/*     */ public class StatFunctions
/*     */ {
/*  50 */   private static Logger log = Logger.getLogger(StatFunctions.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long calculateSoloExperienceReward(Player player, Creature target) {
/*  60 */     int playerLevel = player.getCommonData().getLevel();
/*  61 */     int targetLevel = target.getLevel();
/*     */     
/*  63 */     int baseXP = ((Npc)target).getObjectTemplate().getStatsTemplate().getMaxXp();
/*  64 */     int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
/*     */     
/*  66 */     return (int)Math.floor((baseXP * xpPercentage * player.getRates().getXpRate() / 100));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long calculateGroupExperienceReward(int maxLevelInRange, Creature target) {
/*  77 */     int targetLevel = target.getLevel();
/*     */     
/*  79 */     int baseXP = ((Npc)target).getObjectTemplate().getStatsTemplate().getMaxXp();
/*  80 */     int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - maxLevelInRange);
/*     */     
/*  82 */     return (int)Math.floor((baseXP * xpPercentage / 100));
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
/*     */   public static int calculateSoloDPReward(Player player, Creature target) {
/*  95 */     int playerLevel = player.getCommonData().getLevel();
/*  96 */     int targetLevel = target.getLevel();
/*  97 */     NpcRank npcRank = ((Npc)target).getObjectTemplate().getRank();
/*     */ 
/*     */ 
/*     */     
/* 101 */     int baseDP = targetLevel * calculateRankMultipler(npcRank);
/*     */     
/* 103 */     int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
/* 104 */     return (int)Math.floor((baseDP * xpPercentage * player.getRates().getXpRate() / 100));
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
/*     */   public static int calculateSoloAPReward(Player player, Creature target) {
/* 116 */     int playerLevel = player.getCommonData().getLevel();
/* 117 */     int targetLevel = target.getLevel();
/* 118 */     int percentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
/* 119 */     return (int)Math.floor(((10 * percentage) * player.getRates().getApNpcRate() / 100.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateGroupAPReward(int maxLevelInRange, Creature target) {
/* 130 */     int targetLevel = target.getLevel();
/* 131 */     NpcRank npcRank = ((Npc)target).getObjectTemplate().getRank();
/*     */ 
/*     */     
/* 134 */     int baseAP = 10 + calculateRankMultipler(npcRank) - 1;
/*     */     
/* 136 */     int apPercentage = XPRewardEnum.xpRewardFrom(targetLevel - maxLevelInRange);
/*     */     
/* 138 */     return (int)Math.floor((baseAP * apPercentage / 100));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculatePvPApLost(Player defeated, Player winner) {
/* 149 */     int pointsLost = Math.round(defeated.getAbyssRank().getRank().getPointsLost() * defeated.getRates().getApPlayerRate());
/*     */ 
/*     */     
/* 152 */     int difference = winner.getLevel() - defeated.getLevel();
/*     */     
/* 154 */     if (difference > 4) {
/*     */       
/* 156 */       pointsLost = Math.round(pointsLost * 0.1F);
/*     */     }
/*     */     else {
/*     */       
/* 160 */       switch (difference) {
/*     */         
/*     */         case 3:
/* 163 */           pointsLost = Math.round(pointsLost * 0.85F);
/*     */           break;
/*     */         case 4:
/* 166 */           pointsLost = Math.round(pointsLost * 0.65F);
/*     */           break;
/*     */       } 
/*     */     } 
/* 170 */     return pointsLost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculatePvpApGained(Player defeated, int maxRank, int maxLevel) {
/* 181 */     int pointsGained = Math.round(defeated.getAbyssRank().getRank().getPointsGained());
/*     */ 
/*     */     
/* 184 */     int difference = maxLevel - defeated.getLevel();
/*     */     
/* 186 */     if (difference > 4) {
/*     */       
/* 188 */       pointsGained = Math.round(pointsGained * 0.1F);
/*     */     }
/* 190 */     else if (difference < -3) {
/*     */       
/* 192 */       pointsGained = Math.round(pointsGained * 1.3F);
/*     */     }
/*     */     else {
/*     */       
/* 196 */       switch (difference) {
/*     */         
/*     */         case 3:
/* 199 */           pointsGained = Math.round(pointsGained * 0.85F);
/*     */           break;
/*     */         case 4:
/* 202 */           pointsGained = Math.round(pointsGained * 0.65F);
/*     */           break;
/*     */         case -2:
/* 205 */           pointsGained = Math.round(pointsGained * 1.1F);
/*     */           break;
/*     */         case -3:
/* 208 */           pointsGained = Math.round(pointsGained * 1.2F);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 214 */     int winnerAbyssRank = maxRank;
/* 215 */     int defeatedAbyssRank = defeated.getAbyssRank().getRank().getId();
/* 216 */     int abyssRankDifference = winnerAbyssRank - defeatedAbyssRank;
/*     */     
/* 218 */     if (winnerAbyssRank <= 7 && abyssRankDifference > 0) {
/*     */       
/* 220 */       float penaltyPercent = abyssRankDifference * 0.05F;
/*     */       
/* 222 */       pointsGained -= Math.round(pointsGained * penaltyPercent);
/*     */     } 
/*     */     
/* 225 */     return pointsGained;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateGroupDPReward(Player player, Creature target) {
/* 236 */     int playerLevel = player.getCommonData().getLevel();
/* 237 */     int targetLevel = target.getLevel();
/* 238 */     NpcRank npcRank = ((Npc)target).getObjectTemplate().getRank();
/*     */ 
/*     */     
/* 241 */     int baseDP = targetLevel * calculateRankMultipler(npcRank);
/*     */     
/* 243 */     int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
/*     */     
/* 245 */     return (int)Math.floor((baseDP * xpPercentage * player.getRates().getGroupXpRate() / 100));
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
/*     */   public static int calculateHate(Creature creature, int value) {
/* 258 */     return Math.round((value * creature.getGameStats().getCurrentStat(StatEnum.BOOST_HATE)) / 100.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateBaseDamageToTarget(Creature attacker, Creature target) {
/* 269 */     return calculatePhysicDamageToTarget(attacker, target, 0);
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
/*     */   public static int calculatePhysicDamageToTarget(Creature attacker, Creature target, int skillDamages) {
/* 281 */     CreatureGameStats<?> ags = attacker.getGameStats();
/* 282 */     CreatureGameStats<?> tgs = target.getGameStats();
/*     */     
/* 284 */     int resultDamage = 0;
/*     */     
/* 286 */     if (attacker instanceof Player) {
/*     */       
/* 288 */       int totalMin = ags.getCurrentStat(StatEnum.MIN_DAMAGES);
/* 289 */       int totalMax = ags.getCurrentStat(StatEnum.MAX_DAMAGES);
/* 290 */       int average = Math.round(((totalMin + totalMax) / 2));
/* 291 */       int mainHandAttack = ags.getBaseStat(StatEnum.MAIN_HAND_POWER);
/*     */       
/* 293 */       Equipment equipment = ((Player)attacker).getEquipment();
/*     */       
/* 295 */       WeaponType weaponType = equipment.getMainHandWeaponType();
/*     */       
/* 297 */       if (weaponType != null) {
/*     */         
/* 299 */         if (average < 1) {
/*     */           
/* 301 */           average = 1;
/* 302 */           log.warn("Weapon stat MIN_MAX_DAMAGE resulted average zero in main-hand calculation");
/* 303 */           log.warn("Weapon ID: " + String.valueOf(equipment.getMainHandWeapon().getItemTemplate().getTemplateId()));
/* 304 */           log.warn("MIN_DAMAGE = " + String.valueOf(totalMin));
/* 305 */           log.warn("MAX_DAMAGE = " + String.valueOf(totalMax));
/*     */         } 
/*     */ 
/*     */         
/* 309 */         if (weaponType == WeaponType.BOW) {
/* 310 */           equipment.useArrow();
/*     */         }
/* 312 */         if (equipment.getMainHandWeapon().hasFusionedItem()) {
/*     */           
/* 314 */           Item fusionedItem = ItemService.newItem(equipment.getMainHandWeapon().getFusionedItem(), 1L);
/* 315 */           if (fusionedItem != null) {
/*     */             
/* 317 */             TreeSet<StatModifier> fusionedModifiers = fusionedItem.getItemTemplate().getModifiers();
/* 318 */             for (StatModifier sm : fusionedModifiers) {
/*     */               
/* 320 */               if (sm.getStat() == StatEnum.MAIN_HAND_POWER && sm instanceof SimpleModifier) {
/*     */                 
/* 322 */                 SimpleModifier mod = (SimpleModifier)sm;
/* 323 */                 mainHandAttack += Math.round((mod.getValue() / 10));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 329 */         int min = Math.round((mainHandAttack * 100 / average * totalMin / 100));
/* 330 */         int max = Math.round((mainHandAttack * 100 / average * totalMax / 100));
/*     */         
/* 332 */         int base = Rnd.get(min, max);
/*     */ 
/*     */ 
/*     */         
/* 336 */         resultDamage = Math.round(base * (ags.getCurrentStat(StatEnum.POWER) * 0.01F + ags.getBaseStat(StatEnum.MAIN_HAND_POWER) * 0.2F * 0.01F) + ags.getStatBonus(StatEnum.MAIN_HAND_POWER) + skillDamages);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 342 */         int base = Rnd.get(16, 20);
/* 343 */         resultDamage = Math.round(base * ags.getCurrentStat(StatEnum.POWER) * 0.01F);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 348 */       resultDamage = adjustDamages(attacker, target, resultDamage);
/*     */       
/* 350 */       if (attacker.isInState(CreatureState.POWERSHARD)) {
/*     */         
/* 352 */         Item mainHandPowerShard = equipment.getMainHandPowerShard();
/* 353 */         if (mainHandPowerShard != null)
/*     */         {
/* 355 */           resultDamage += mainHandPowerShard.getItemTemplate().getWeaponBoost();
/*     */           
/* 357 */           equipment.usePowerShard(mainHandPowerShard, 1);
/*     */         }
/*     */       
/*     */       } 
/* 361 */     } else if (attacker instanceof com.aionemu.gameserver.model.gameobjects.Summon) {
/*     */       
/* 363 */       int baseDamage = ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
/* 364 */       int max = baseDamage + baseDamage * attacker.getLevel() / 10;
/* 365 */       int min = max - ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
/* 366 */       resultDamage += Rnd.get(min, max);
/*     */     }
/*     */     else {
/*     */       
/* 370 */       NpcRank npcRank = ((Npc)attacker).getObjectTemplate().getRank();
/* 371 */       double multipler = calculateRankMultipler(npcRank);
/* 372 */       double hpGaugeMod = (1 + ((Npc)attacker).getObjectTemplate().getHpGauge() / 10);
/* 373 */       int baseDamage = ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
/* 374 */       int max = (int)(baseDamage * multipler * hpGaugeMod + (baseDamage * attacker.getLevel() / 10));
/* 375 */       int min = max - ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
/* 376 */       resultDamage += Rnd.get(min, max);
/*     */     } 
/*     */     
/* 379 */     resultDamage -= Math.round(tgs.getCurrentStat(StatEnum.PHYSICAL_DEFENSE) * 0.1F);
/*     */     
/* 381 */     if (resultDamage <= 0) {
/* 382 */       resultDamage = 1;
/*     */     }
/* 384 */     return resultDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateOffHandPhysicDamageToTarget(Creature attacker, Creature target) {
/* 395 */     CreatureGameStats<?> ags = attacker.getGameStats();
/* 396 */     CreatureGameStats<?> tgs = target.getGameStats();
/*     */     
/* 398 */     int totalMin = ags.getCurrentStat(StatEnum.MIN_DAMAGES);
/* 399 */     int totalMax = ags.getCurrentStat(StatEnum.MAX_DAMAGES);
/* 400 */     int average = Math.round(((totalMin + totalMax) / 2));
/* 401 */     int offHandAttack = ags.getBaseStat(StatEnum.OFF_HAND_POWER);
/*     */     
/* 403 */     Equipment equipment = ((Player)attacker).getEquipment();
/*     */     
/* 405 */     if (average < 1) {
/*     */       
/* 407 */       average = 1;
/* 408 */       log.warn("Weapon stat MIN_MAX_DAMAGE resulted average zero in off-hand calculation");
/* 409 */       log.warn("Weapon ID: " + String.valueOf(equipment.getOffHandWeapon().getItemTemplate().getTemplateId()));
/* 410 */       log.warn("MIN_DAMAGE = " + String.valueOf(totalMin));
/* 411 */       log.warn("MAX_DAMAGE = " + String.valueOf(totalMax));
/*     */     } 
/*     */     
/* 414 */     int Damage = 0;
/* 415 */     int min = Math.round((offHandAttack * 100 / average * totalMin / 100));
/* 416 */     int max = Math.round((offHandAttack * 100 / average * totalMax / 100));
/*     */     
/* 418 */     int base = Rnd.get(min, max);
/* 419 */     Damage = Math.round(base * (ags.getCurrentStat(StatEnum.POWER) * 0.01F + ags.getBaseStat(StatEnum.OFF_HAND_POWER) * 0.2F * 0.01F) + ags.getStatBonus(StatEnum.OFF_HAND_POWER));
/*     */ 
/*     */     
/* 422 */     Damage = adjustDamages(attacker, target, Damage);
/*     */     
/* 424 */     if (attacker.isInState(CreatureState.POWERSHARD)) {
/*     */       
/* 426 */       Item offHandPowerShard = equipment.getOffHandPowerShard();
/* 427 */       if (offHandPowerShard != null) {
/*     */         
/* 429 */         Damage += offHandPowerShard.getItemTemplate().getWeaponBoost();
/* 430 */         equipment.usePowerShard(offHandPowerShard, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 434 */     Damage -= Math.round(tgs.getCurrentStat(StatEnum.PHYSICAL_DEFENSE) * 0.1F);
/*     */     float i;
/* 436 */     for (i = 0.25F; i <= 1.0F; i += 0.25F) {
/*     */       
/* 438 */       if (Rnd.get(0, 100) < 50) {
/*     */         
/* 440 */         Damage = (int)(Damage * i);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 445 */     if (Damage <= 0) {
/* 446 */       Damage = 1;
/*     */     }
/* 448 */     return Damage;
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
/*     */   public static int calculateMagicDamageToTarget(Creature speller, Creature target, int baseDamages, SkillElement element) {
/* 460 */     CreatureGameStats<?> sgs = speller.getGameStats();
/* 461 */     CreatureGameStats<?> tgs = target.getGameStats();
/*     */     
/* 463 */     int totalBoostMagicalSkill = 0;
/*     */     
/* 465 */     if (speller instanceof Player && ((Player)speller).getEquipment().getMainHandWeapon() != null && ((Player)speller).getEquipment().getMainHandWeapon().hasFusionedItem()) {
/*     */       
/* 467 */       Item fusionedItem = ItemService.newItem(((Player)speller).getEquipment().getMainHandWeapon().getFusionedItem(), 1L);
/* 468 */       if (fusionedItem != null) {
/*     */         
/* 470 */         TreeSet<StatModifier> fusionedModifiers = fusionedItem.getItemTemplate().getModifiers();
/* 471 */         for (StatModifier sm : fusionedModifiers) {
/*     */           
/* 473 */           if (sm.getStat() == StatEnum.BOOST_MAGICAL_SKILL && sm instanceof SimpleModifier) {
/*     */             
/* 475 */             SimpleModifier mod = (SimpleModifier)sm;
/* 476 */             totalBoostMagicalSkill += Math.round((mod.getValue() / 10));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 482 */     totalBoostMagicalSkill += sgs.getCurrentStat(StatEnum.BOOST_MAGICAL_SKILL);
/*     */     
/* 484 */     int damages = Math.round(baseDamages * (sgs.getCurrentStat(StatEnum.KNOWLEDGE) / 100.0F + totalBoostMagicalSkill / 1000.0F));
/*     */ 
/*     */ 
/*     */     
/* 488 */     damages = adjustDamages(speller, target, damages);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     damages = Math.round(damages * (1.0F - tgs.getMagicalDefenseFor(element) / 1000.0F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 504 */     if (damages <= 0) {
/* 505 */       damages = 1;
/*     */     }
/*     */     
/* 508 */     return damages;
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
/*     */   public static int calculateRankMultipler(NpcRank npcRank) {
/* 520 */     switch (npcRank)
/*     */     
/*     */     { case JUNK:
/* 523 */         multipler = 2;
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
/* 541 */         return multipler;case NORMAL: multipler = 2; return multipler;case ELITE: multipler = 3; return multipler;case HERO: multipler = 4; return multipler;case LEGENDARY: multipler = 5; return multipler; }  int multipler = 1; return multipler;
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
/*     */   public static int adjustDamages(Creature attacker, Creature target, int Damages) {
/* 556 */     int attackerLevel = attacker.getLevel();
/* 557 */     int targetLevel = target.getLevel();
/* 558 */     int baseDamages = Damages;
/*     */ 
/*     */     
/* 561 */     if (attacker instanceof Player && !(target instanceof Player)) {
/*     */       
/* 563 */       if (targetLevel > attackerLevel)
/*     */       {
/* 565 */         float multipler = 0.0F;
/* 566 */         int differ = targetLevel - attackerLevel;
/*     */         
/* 568 */         if (differ <= 2) {
/* 569 */           return baseDamages;
/*     */         }
/* 571 */         if (differ > 2 && differ < 10) {
/* 572 */           multipler = (differ - 2.0F) / 10.0F;
/* 573 */           baseDamages -= Math.round(baseDamages * multipler);
/*     */         }
/*     */         else {
/*     */           
/* 577 */           baseDamages -= Math.round(baseDamages * 0.8F);
/*     */         } 
/*     */         
/* 580 */         return baseDamages;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 585 */     else if (attacker instanceof Player && target instanceof Player) {
/* 586 */       baseDamages = Math.round(baseDamages * 0.6F);
/* 587 */       float pvpAttackBonus = attacker.getGameStats().getCurrentStat(StatEnum.PVP_ATTACK_RATIO) * 0.001F;
/* 588 */       float pvpDefenceBonus = target.getGameStats().getCurrentStat(StatEnum.PVP_DEFEND_RATIO) * 0.001F;
/* 589 */       baseDamages = Math.round(baseDamages + baseDamages * pvpAttackBonus - baseDamages * pvpDefenceBonus);
/* 590 */       return baseDamages;
/*     */     } 
/*     */     
/* 593 */     return baseDamages;
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
/*     */   public static int calculatePhysicalDodgeRate(Creature attacker, Creature attacked) {
/*     */     int accuracy;
/* 607 */     if (attacked.getObserveController().checkAttackStatus(AttackStatus.DODGE)) {
/* 608 */       return 100;
/*     */     }
/*     */ 
/*     */     
/* 612 */     if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
/* 613 */       accuracy = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_ACCURACY)) / 2));
/*     */     } else {
/*     */       
/* 616 */       accuracy = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY);
/*     */     } 
/* 618 */     int dodgeRate = (attacked.getGameStats().getCurrentStat(StatEnum.EVASION) - accuracy) / 10;
/*     */     
/* 620 */     if (dodgeRate > 30) {
/* 621 */       dodgeRate = 30;
/*     */     }
/* 623 */     if (dodgeRate <= 0) {
/* 624 */       return 1;
/*     */     }
/* 626 */     return dodgeRate;
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
/*     */   public static int calculatePhysicalParryRate(Creature attacker, Creature attacked) {
/*     */     int accuracy;
/* 639 */     if (attacked.getObserveController().checkAttackStatus(AttackStatus.PARRY)) {
/* 640 */       return 100;
/*     */     }
/*     */ 
/*     */     
/* 644 */     if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
/* 645 */       accuracy = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_ACCURACY)) / 2));
/*     */     } else {
/*     */       
/* 648 */       accuracy = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY);
/*     */     } 
/* 650 */     int parryRate = (attacked.getGameStats().getCurrentStat(StatEnum.PARRY) - accuracy) / 10;
/*     */     
/* 652 */     if (parryRate > 40) {
/* 653 */       parryRate = 40;
/*     */     }
/* 655 */     if (parryRate <= 0) {
/* 656 */       return 1;
/*     */     }
/* 658 */     return parryRate;
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
/*     */   public static int calculatePhysicalBlockRate(Creature attacker, Creature attacked) {
/*     */     int accuracy;
/* 671 */     if (attacked.getObserveController().checkAttackStatus(AttackStatus.BLOCK)) {
/* 672 */       return 100;
/*     */     }
/*     */ 
/*     */     
/* 676 */     if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
/* 677 */       accuracy = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_ACCURACY)) / 2));
/*     */     } else {
/*     */       
/* 680 */       accuracy = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY);
/*     */     } 
/* 682 */     int blockRate = (attacked.getGameStats().getCurrentStat(StatEnum.BLOCK) - accuracy) / 10;
/*     */     
/* 684 */     if (blockRate > 50) {
/* 685 */       blockRate = 50;
/*     */     }
/* 687 */     if (blockRate <= 0) {
/* 688 */       return 1;
/*     */     }
/* 690 */     return blockRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double calculatePhysicalCriticalRate(Creature attacker, Creature attacked) {
/*     */     int critical;
/*     */     double criticalRate;
/* 703 */     if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
/* 704 */       critical = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_CRITICAL) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_CRITICAL)) / 2 - attacked.getGameStats().getCurrentStat(StatEnum.CRITICAL_RESIST)));
/*     */     } else {
/*     */       
/* 707 */       critical = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_CRITICAL) - attacked.getGameStats().getCurrentStat(StatEnum.CRITICAL_RESIST);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 712 */     if (critical <= 440) {
/* 713 */       criticalRate = (critical * 0.1F);
/* 714 */     } else if (critical <= 600) {
/* 715 */       criticalRate = (44.0F + (critical - 440) * 0.05F);
/*     */     } else {
/* 717 */       criticalRate = (52.0F + (critical - 600) * 0.02F);
/*     */     } 
/* 719 */     if (criticalRate < 1.0D) {
/* 720 */       criticalRate = 1.0D;
/*     */     }
/* 722 */     return criticalRate;
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
/*     */   public static int calculateMagicalResistRate(Creature attacker, Creature attacked) {
/* 734 */     if (attacked.getObserveController().checkAttackStatus(AttackStatus.RESIST)) {
/* 735 */       return 100;
/*     */     }
/* 737 */     int resistRate = Math.round(((attacked.getGameStats().getCurrentStat(StatEnum.MAGICAL_RESIST) - attacker.getGameStats().getCurrentStat(StatEnum.MAGICAL_ACCURACY)) / 10));
/*     */ 
/*     */     
/* 740 */     return resistRate;
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
/*     */   public static boolean calculateFallDamage(Player player, float distance) {
/* 752 */     if (player.isInvul())
/*     */     {
/* 754 */       return false;
/*     */     }
/*     */     
/* 757 */     if (distance >= FallDamageConfig.MAXIMUM_DISTANCE_DAMAGE) {
/*     */       
/* 759 */       player.getController().onStopMove();
/* 760 */       player.getFlyController().onStopGliding();
/* 761 */       player.getController().onDie((Creature)player);
/*     */       
/* 763 */       player.getReviveController().bindRevive();
/* 764 */       return true;
/*     */     } 
/* 766 */     if (distance >= FallDamageConfig.MINIMUM_DISTANCE_DAMAGE) {
/*     */       
/* 768 */       float dmgPerMeter = player.getLifeStats().getMaxHp() * FallDamageConfig.FALL_DAMAGE_PERCENTAGE / 100.0F;
/* 769 */       int damage = (int)(distance * dmgPerMeter);
/*     */       
/* 771 */       player.getLifeStats().reduceHp(damage, (Creature)player);
/* 772 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ATTACK_STATUS((Creature)player, SM_ATTACK_STATUS.TYPE.DAMAGE, 0, damage));
/*     */     } 
/*     */     
/* 775 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\StatFunctions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
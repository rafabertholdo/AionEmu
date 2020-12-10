package com.aionemu.gameserver.utils.stats;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.FallDamageConfig;
import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.model.SkillElement;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SimpleModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.model.templates.stats.NpcRank;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.TreeSet;
import org.apache.log4j.Logger;























public class StatFunctions
{
  private static Logger log = Logger.getLogger(StatFunctions.class);







  
  public static long calculateSoloExperienceReward(Player player, Creature target) {
    int playerLevel = player.getCommonData().getLevel();
    int targetLevel = target.getLevel();
    
    int baseXP = ((Npc)target).getObjectTemplate().getStatsTemplate().getMaxXp();
    int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
    
    return (int)Math.floor((baseXP * xpPercentage * player.getRates().getXpRate() / 100));
  }







  
  public static long calculateGroupExperienceReward(int maxLevelInRange, Creature target) {
    int targetLevel = target.getLevel();
    
    int baseXP = ((Npc)target).getObjectTemplate().getStatsTemplate().getMaxXp();
    int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - maxLevelInRange);
    
    return (int)Math.floor((baseXP * xpPercentage / 100));
  }









  
  public static int calculateSoloDPReward(Player player, Creature target) {
    int playerLevel = player.getCommonData().getLevel();
    int targetLevel = target.getLevel();
    NpcRank npcRank = ((Npc)target).getObjectTemplate().getRank();


    
    int baseDP = targetLevel * calculateRankMultipler(npcRank);
    
    int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
    return (int)Math.floor((baseDP * xpPercentage * player.getRates().getXpRate() / 100));
  }








  
  public static int calculateSoloAPReward(Player player, Creature target) {
    int playerLevel = player.getCommonData().getLevel();
    int targetLevel = target.getLevel();
    int percentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
    return (int)Math.floor(((10 * percentage) * player.getRates().getApNpcRate() / 100.0F));
  }







  
  public static int calculateGroupAPReward(int maxLevelInRange, Creature target) {
    int targetLevel = target.getLevel();
    NpcRank npcRank = ((Npc)target).getObjectTemplate().getRank();

    
    int baseAP = 10 + calculateRankMultipler(npcRank) - 1;
    
    int apPercentage = XPRewardEnum.xpRewardFrom(targetLevel - maxLevelInRange);
    
    return (int)Math.floor((baseAP * apPercentage / 100));
  }







  
  public static int calculatePvPApLost(Player defeated, Player winner) {
    int pointsLost = Math.round(defeated.getAbyssRank().getRank().getPointsLost() * defeated.getRates().getApPlayerRate());

    
    int difference = winner.getLevel() - defeated.getLevel();
    
    if (difference > 4) {
      
      pointsLost = Math.round(pointsLost * 0.1F);
    }
    else {
      
      switch (difference) {
        
        case 3:
          pointsLost = Math.round(pointsLost * 0.85F);
          break;
        case 4:
          pointsLost = Math.round(pointsLost * 0.65F);
          break;
      } 
    } 
    return pointsLost;
  }







  
  public static int calculatePvpApGained(Player defeated, int maxRank, int maxLevel) {
    int pointsGained = Math.round(defeated.getAbyssRank().getRank().getPointsGained());

    
    int difference = maxLevel - defeated.getLevel();
    
    if (difference > 4) {
      
      pointsGained = Math.round(pointsGained * 0.1F);
    }
    else if (difference < -3) {
      
      pointsGained = Math.round(pointsGained * 1.3F);
    }
    else {
      
      switch (difference) {
        
        case 3:
          pointsGained = Math.round(pointsGained * 0.85F);
          break;
        case 4:
          pointsGained = Math.round(pointsGained * 0.65F);
          break;
        case -2:
          pointsGained = Math.round(pointsGained * 1.1F);
          break;
        case -3:
          pointsGained = Math.round(pointsGained * 1.2F);
          break;
      } 

    
    } 
    int winnerAbyssRank = maxRank;
    int defeatedAbyssRank = defeated.getAbyssRank().getRank().getId();
    int abyssRankDifference = winnerAbyssRank - defeatedAbyssRank;
    
    if (winnerAbyssRank <= 7 && abyssRankDifference > 0) {
      
      float penaltyPercent = abyssRankDifference * 0.05F;
      
      pointsGained -= Math.round(pointsGained * penaltyPercent);
    } 
    
    return pointsGained;
  }







  
  public static int calculateGroupDPReward(Player player, Creature target) {
    int playerLevel = player.getCommonData().getLevel();
    int targetLevel = target.getLevel();
    NpcRank npcRank = ((Npc)target).getObjectTemplate().getRank();

    
    int baseDP = targetLevel * calculateRankMultipler(npcRank);
    
    int xpPercentage = XPRewardEnum.xpRewardFrom(targetLevel - playerLevel);
    
    return (int)Math.floor((baseDP * xpPercentage * player.getRates().getGroupXpRate() / 100));
  }









  
  public static int calculateHate(Creature creature, int value) {
    return Math.round((value * creature.getGameStats().getCurrentStat(StatEnum.BOOST_HATE)) / 100.0F);
  }







  
  public static int calculateBaseDamageToTarget(Creature attacker, Creature target) {
    return calculatePhysicDamageToTarget(attacker, target, 0);
  }








  
  public static int calculatePhysicDamageToTarget(Creature attacker, Creature target, int skillDamages) {
    CreatureGameStats<?> ags = attacker.getGameStats();
    CreatureGameStats<?> tgs = target.getGameStats();
    
    int resultDamage = 0;
    
    if (attacker instanceof Player) {
      
      int totalMin = ags.getCurrentStat(StatEnum.MIN_DAMAGES);
      int totalMax = ags.getCurrentStat(StatEnum.MAX_DAMAGES);
      int average = Math.round(((totalMin + totalMax) / 2));
      int mainHandAttack = ags.getBaseStat(StatEnum.MAIN_HAND_POWER);
      
      Equipment equipment = ((Player)attacker).getEquipment();
      
      WeaponType weaponType = equipment.getMainHandWeaponType();
      
      if (weaponType != null) {
        
        if (average < 1) {
          
          average = 1;
          log.warn("Weapon stat MIN_MAX_DAMAGE resulted average zero in main-hand calculation");
          log.warn("Weapon ID: " + String.valueOf(equipment.getMainHandWeapon().getItemTemplate().getTemplateId()));
          log.warn("MIN_DAMAGE = " + String.valueOf(totalMin));
          log.warn("MAX_DAMAGE = " + String.valueOf(totalMax));
        } 

        
        if (weaponType == WeaponType.BOW) {
          equipment.useArrow();
        }
        if (equipment.getMainHandWeapon().hasFusionedItem()) {
          
          Item fusionedItem = ItemService.newItem(equipment.getMainHandWeapon().getFusionedItem(), 1L);
          if (fusionedItem != null) {
            
            TreeSet<StatModifier> fusionedModifiers = fusionedItem.getItemTemplate().getModifiers();
            for (StatModifier sm : fusionedModifiers) {
              
              if (sm.getStat() == StatEnum.MAIN_HAND_POWER && sm instanceof SimpleModifier) {
                
                SimpleModifier mod = (SimpleModifier)sm;
                mainHandAttack += Math.round((mod.getValue() / 10));
              } 
            } 
          } 
        } 
        
        int min = Math.round((mainHandAttack * 100 / average * totalMin / 100));
        int max = Math.round((mainHandAttack * 100 / average * totalMax / 100));
        
        int base = Rnd.get(min, max);


        
        resultDamage = Math.round(base * (ags.getCurrentStat(StatEnum.POWER) * 0.01F + ags.getBaseStat(StatEnum.MAIN_HAND_POWER) * 0.2F * 0.01F) + ags.getStatBonus(StatEnum.MAIN_HAND_POWER) + skillDamages);
      
      }
      else {

        
        int base = Rnd.get(16, 20);
        resultDamage = Math.round(base * ags.getCurrentStat(StatEnum.POWER) * 0.01F);
      } 


      
      resultDamage = adjustDamages(attacker, target, resultDamage);
      
      if (attacker.isInState(CreatureState.POWERSHARD)) {
        
        Item mainHandPowerShard = equipment.getMainHandPowerShard();
        if (mainHandPowerShard != null)
        {
          resultDamage += mainHandPowerShard.getItemTemplate().getWeaponBoost();
          
          equipment.usePowerShard(mainHandPowerShard, 1);
        }
      
      } 
    } else if (attacker instanceof com.aionemu.gameserver.model.gameobjects.Summon) {
      
      int baseDamage = ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
      int max = baseDamage + baseDamage * attacker.getLevel() / 10;
      int min = max - ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
      resultDamage += Rnd.get(min, max);
    }
    else {
      
      NpcRank npcRank = ((Npc)attacker).getObjectTemplate().getRank();
      double multipler = calculateRankMultipler(npcRank);
      double hpGaugeMod = (1 + ((Npc)attacker).getObjectTemplate().getHpGauge() / 10);
      int baseDamage = ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
      int max = (int)(baseDamage * multipler * hpGaugeMod + (baseDamage * attacker.getLevel() / 10));
      int min = max - ags.getCurrentStat(StatEnum.MAIN_HAND_POWER);
      resultDamage += Rnd.get(min, max);
    } 
    
    resultDamage -= Math.round(tgs.getCurrentStat(StatEnum.PHYSICAL_DEFENSE) * 0.1F);
    
    if (resultDamage <= 0) {
      resultDamage = 1;
    }
    return resultDamage;
  }







  
  public static int calculateOffHandPhysicDamageToTarget(Creature attacker, Creature target) {
    CreatureGameStats<?> ags = attacker.getGameStats();
    CreatureGameStats<?> tgs = target.getGameStats();
    
    int totalMin = ags.getCurrentStat(StatEnum.MIN_DAMAGES);
    int totalMax = ags.getCurrentStat(StatEnum.MAX_DAMAGES);
    int average = Math.round(((totalMin + totalMax) / 2));
    int offHandAttack = ags.getBaseStat(StatEnum.OFF_HAND_POWER);
    
    Equipment equipment = ((Player)attacker).getEquipment();
    
    if (average < 1) {
      
      average = 1;
      log.warn("Weapon stat MIN_MAX_DAMAGE resulted average zero in off-hand calculation");
      log.warn("Weapon ID: " + String.valueOf(equipment.getOffHandWeapon().getItemTemplate().getTemplateId()));
      log.warn("MIN_DAMAGE = " + String.valueOf(totalMin));
      log.warn("MAX_DAMAGE = " + String.valueOf(totalMax));
    } 
    
    int Damage = 0;
    int min = Math.round((offHandAttack * 100 / average * totalMin / 100));
    int max = Math.round((offHandAttack * 100 / average * totalMax / 100));
    
    int base = Rnd.get(min, max);
    Damage = Math.round(base * (ags.getCurrentStat(StatEnum.POWER) * 0.01F + ags.getBaseStat(StatEnum.OFF_HAND_POWER) * 0.2F * 0.01F) + ags.getStatBonus(StatEnum.OFF_HAND_POWER));

    
    Damage = adjustDamages(attacker, target, Damage);
    
    if (attacker.isInState(CreatureState.POWERSHARD)) {
      
      Item offHandPowerShard = equipment.getOffHandPowerShard();
      if (offHandPowerShard != null) {
        
        Damage += offHandPowerShard.getItemTemplate().getWeaponBoost();
        equipment.usePowerShard(offHandPowerShard, 1);
      } 
    } 
    
    Damage -= Math.round(tgs.getCurrentStat(StatEnum.PHYSICAL_DEFENSE) * 0.1F);
    float i;
    for (i = 0.25F; i <= 1.0F; i += 0.25F) {
      
      if (Rnd.get(0, 100) < 50) {
        
        Damage = (int)(Damage * i);
        
        break;
      } 
    } 
    if (Damage <= 0) {
      Damage = 1;
    }
    return Damage;
  }








  
  public static int calculateMagicDamageToTarget(Creature speller, Creature target, int baseDamages, SkillElement element) {
    CreatureGameStats<?> sgs = speller.getGameStats();
    CreatureGameStats<?> tgs = target.getGameStats();
    
    int totalBoostMagicalSkill = 0;
    
    if (speller instanceof Player && ((Player)speller).getEquipment().getMainHandWeapon() != null && ((Player)speller).getEquipment().getMainHandWeapon().hasFusionedItem()) {
      
      Item fusionedItem = ItemService.newItem(((Player)speller).getEquipment().getMainHandWeapon().getFusionedItem(), 1L);
      if (fusionedItem != null) {
        
        TreeSet<StatModifier> fusionedModifiers = fusionedItem.getItemTemplate().getModifiers();
        for (StatModifier sm : fusionedModifiers) {
          
          if (sm.getStat() == StatEnum.BOOST_MAGICAL_SKILL && sm instanceof SimpleModifier) {
            
            SimpleModifier mod = (SimpleModifier)sm;
            totalBoostMagicalSkill += Math.round((mod.getValue() / 10));
          } 
        } 
      } 
    } 
    
    totalBoostMagicalSkill += sgs.getCurrentStat(StatEnum.BOOST_MAGICAL_SKILL);
    
    int damages = Math.round(baseDamages * (sgs.getCurrentStat(StatEnum.KNOWLEDGE) / 100.0F + totalBoostMagicalSkill / 1000.0F));


    
    damages = adjustDamages(speller, target, damages);




    
    damages = Math.round(damages * (1.0F - tgs.getMagicalDefenseFor(element) / 1000.0F));








    
    if (damages <= 0) {
      damages = 1;
    }
    
    return damages;
  }








  
  public static int calculateRankMultipler(NpcRank npcRank) {
    switch (npcRank)
    
    { case JUNK:
        multipler = 2;
















        
        return multipler;case NORMAL: multipler = 2; return multipler;case ELITE: multipler = 3; return multipler;case HERO: multipler = 4; return multipler;case LEGENDARY: multipler = 5; return multipler; }  int multipler = 1; return multipler;
  }











  
  public static int adjustDamages(Creature attacker, Creature target, int Damages) {
    int attackerLevel = attacker.getLevel();
    int targetLevel = target.getLevel();
    int baseDamages = Damages;

    
    if (attacker instanceof Player && !(target instanceof Player)) {
      
      if (targetLevel > attackerLevel)
      {
        float multipler = 0.0F;
        int differ = targetLevel - attackerLevel;
        
        if (differ <= 2) {
          return baseDamages;
        }
        if (differ > 2 && differ < 10) {
          multipler = (differ - 2.0F) / 10.0F;
          baseDamages -= Math.round(baseDamages * multipler);
        }
        else {
          
          baseDamages -= Math.round(baseDamages * 0.8F);
        } 
        
        return baseDamages;
      
      }
    
    }
    else if (attacker instanceof Player && target instanceof Player) {
      baseDamages = Math.round(baseDamages * 0.6F);
      float pvpAttackBonus = attacker.getGameStats().getCurrentStat(StatEnum.PVP_ATTACK_RATIO) * 0.001F;
      float pvpDefenceBonus = target.getGameStats().getCurrentStat(StatEnum.PVP_DEFEND_RATIO) * 0.001F;
      baseDamages = Math.round(baseDamages + baseDamages * pvpAttackBonus - baseDamages * pvpDefenceBonus);
      return baseDamages;
    } 
    
    return baseDamages;
  }









  
  public static int calculatePhysicalDodgeRate(Creature attacker, Creature attacked) {
    int accuracy;
    if (attacked.getObserveController().checkAttackStatus(AttackStatus.DODGE)) {
      return 100;
    }

    
    if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
      accuracy = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_ACCURACY)) / 2));
    } else {
      
      accuracy = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY);
    } 
    int dodgeRate = (attacked.getGameStats().getCurrentStat(StatEnum.EVASION) - accuracy) / 10;
    
    if (dodgeRate > 30) {
      dodgeRate = 30;
    }
    if (dodgeRate <= 0) {
      return 1;
    }
    return dodgeRate;
  }








  
  public static int calculatePhysicalParryRate(Creature attacker, Creature attacked) {
    int accuracy;
    if (attacked.getObserveController().checkAttackStatus(AttackStatus.PARRY)) {
      return 100;
    }

    
    if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
      accuracy = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_ACCURACY)) / 2));
    } else {
      
      accuracy = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY);
    } 
    int parryRate = (attacked.getGameStats().getCurrentStat(StatEnum.PARRY) - accuracy) / 10;
    
    if (parryRate > 40) {
      parryRate = 40;
    }
    if (parryRate <= 0) {
      return 1;
    }
    return parryRate;
  }








  
  public static int calculatePhysicalBlockRate(Creature attacker, Creature attacked) {
    int accuracy;
    if (attacked.getObserveController().checkAttackStatus(AttackStatus.BLOCK)) {
      return 100;
    }

    
    if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
      accuracy = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_ACCURACY)) / 2));
    } else {
      
      accuracy = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_ACCURACY);
    } 
    int blockRate = (attacked.getGameStats().getCurrentStat(StatEnum.BLOCK) - accuracy) / 10;
    
    if (blockRate > 50) {
      blockRate = 50;
    }
    if (blockRate <= 0) {
      return 1;
    }
    return blockRate;
  }







  
  public static double calculatePhysicalCriticalRate(Creature attacker, Creature attacked) {
    int critical;
    double criticalRate;
    if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
      critical = Math.round(((attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_CRITICAL) + attacker.getGameStats().getCurrentStat(StatEnum.OFF_HAND_CRITICAL)) / 2 - attacked.getGameStats().getCurrentStat(StatEnum.CRITICAL_RESIST)));
    } else {
      
      critical = attacker.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_CRITICAL) - attacked.getGameStats().getCurrentStat(StatEnum.CRITICAL_RESIST);
    } 


    
    if (critical <= 440) {
      criticalRate = (critical * 0.1F);
    } else if (critical <= 600) {
      criticalRate = (44.0F + (critical - 440) * 0.05F);
    } else {
      criticalRate = (52.0F + (critical - 600) * 0.02F);
    } 
    if (criticalRate < 1.0D) {
      criticalRate = 1.0D;
    }
    return criticalRate;
  }








  
  public static int calculateMagicalResistRate(Creature attacker, Creature attacked) {
    if (attacked.getObserveController().checkAttackStatus(AttackStatus.RESIST)) {
      return 100;
    }
    int resistRate = Math.round(((attacked.getGameStats().getCurrentStat(StatEnum.MAGICAL_RESIST) - attacker.getGameStats().getCurrentStat(StatEnum.MAGICAL_ACCURACY)) / 10));

    
    return resistRate;
  }








  
  public static boolean calculateFallDamage(Player player, float distance) {
    if (player.isInvul())
    {
      return false;
    }
    
    if (distance >= FallDamageConfig.MAXIMUM_DISTANCE_DAMAGE) {
      
      player.getController().onStopMove();
      player.getFlyController().onStopGliding();
      player.getController().onDie((Creature)player);
      
      player.getReviveController().bindRevive();
      return true;
    } 
    if (distance >= FallDamageConfig.MINIMUM_DISTANCE_DAMAGE) {
      
      float dmgPerMeter = player.getLifeStats().getMaxHp() * FallDamageConfig.FALL_DAMAGE_PERCENTAGE / 100.0F;
      int damage = (int)(distance * dmgPerMeter);
      
      player.getLifeStats().reduceHp(damage, (Creature)player);
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ATTACK_STATUS((Creature)player, SM_ATTACK_STATUS.TYPE.DAMAGE, 0, damage));
    } 
    
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\StatFunctions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

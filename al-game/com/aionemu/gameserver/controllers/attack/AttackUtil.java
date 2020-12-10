package com.aionemu.gameserver.controllers.attack;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.SkillElement;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;































public class AttackUtil
{
  public static List<AttackResult> calculateAttackResult(Creature attacker, Creature attacked) {
    int damage = StatFunctions.calculateBaseDamageToTarget(attacker, attacked);
    
    AttackStatus status = calculateAttackerPhysicalStatus(attacker);
    
    if (status == null) {
      status = calculatePhysicalStatus(attacker, attacked);
    }
    CreatureGameStats<?> gameStats = attacker.getGameStats();
    
    if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {


      
      switch (status)
      
      { case DAGGER_1H:
          offHandStatus = AttackStatus.OFFHAND_BLOCK;














          
          offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked);
          
          mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS));
          offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS));
          
          list = new ArrayList<AttackResult>();
          list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status));
          list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus));
          attacked.getObserveController().checkShieldStatus(list);
          
          return list;case SWORD_1H: offHandStatus = AttackStatus.OFFHAND_DODGE; offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list;case MACE_1H: offHandStatus = AttackStatus.OFFHAND_CRITICAL; offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list;case SWORD_2H: offHandStatus = AttackStatus.OFFHAND_PARRY; offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list; }  AttackStatus offHandStatus = AttackStatus.OFFHAND_NORMALHIT; int offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); int mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); int offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); List<AttackResult> list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list;
    } 
    
    int hitCount = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS));
    List<AttackResult> attackList = splitPhysicalDamage(attacker, attacked, hitCount, damage, status);
    attacked.getObserveController().checkShieldStatus(attackList);
    return attackList;
  }


  
  public static List<AttackResult> splitPhysicalDamage(Creature attacker, Creature attacked, int hitCount, int damage, AttackStatus status) {
    List<AttackResult> attackList = new ArrayList<AttackResult>();
    
    for (int i = 0; i < hitCount; i++) {
      WeaponType weaponType;
      int shieldDamageReduce, damages = damage;
      
      if (i != 0)
      {
        damages = Math.round(damage * 0.1F);
      }



      
      switch (status) {
        
        case DAGGER_1H:
        case POLEARM_2H:
          shieldDamageReduce = ((Player)attacked).getGameStats().getCurrentStat(StatEnum.DAMAGE_REDUCE);
          damages -= Math.round((damages * shieldDamageReduce / 100));
          break;
        
        case SWORD_1H:
        case STAFF_2H:
          damages = 0;
          break;
        
        case MACE_1H:
          weaponType = ((Player)attacker).getEquipment().getMainHandWeaponType();
          damages = calculateWeaponCritical(damages, weaponType);
          break;
        
        case BOW:
          weaponType = ((Player)attacker).getEquipment().getOffHandWeaponType();
          damages = calculateWeaponCritical(damages, weaponType);
          break;
        
        case SWORD_2H:
        case null:
          damages = (int)(damages * 0.5D);
          break;
      } 


      
      attackList.add(new AttackResult(damages, status));
    } 
    return attackList;
  }

















  
  private static int calculateWeaponCritical(int damages, WeaponType weaponType) {
    switch (weaponType)
    
    { case DAGGER_1H:
        damages = Math.round(damages * 2.3F);


















        
        return damages;case SWORD_1H: damages = Math.round(damages * 2.2F); return damages;case MACE_1H: damages *= 2; return damages;case SWORD_2H: case POLEARM_2H: damages = Math.round(damages * 1.8F); return damages;case STAFF_2H: case BOW: damages = Math.round(damages * 1.8F); return damages; }  damages = Math.round(damages * 1.5F); return damages;
  }





  
  public static void calculatePhysicalSkillAttackResult(Effect effect, int skillDamage) {
    int shieldDamageReduce;
    Creature effector = effect.getEffector();
    Creature effected = effect.getEffected();
    int damage = StatFunctions.calculatePhysicDamageToTarget(effector, effected, skillDamage);
    
    AttackStatus status = calculateAttackerPhysicalStatus(effector);
    
    if (status == null) {
      status = calculatePhysicalStatus(effector, effected);
    }
    switch (status) {
      
      case DAGGER_1H:
        shieldDamageReduce = ((Player)effected).getGameStats().getCurrentStat(StatEnum.DAMAGE_REDUCE);
        damage -= Math.round((damage * shieldDamageReduce / 100));
        break;
      case SWORD_1H:
        damage = 0;
        break;
      case MACE_1H:
        damage *= 2;
        break;
      case SWORD_2H:
        damage = (int)(damage * 0.5D);
        break;
    } 


    
    calculateEffectResult(effect, effected, damage, status);
  }







  
  private static AttackStatus calculateAttackerPhysicalStatus(Creature effector) {
    if (effector.getObserveController().checkAttackerStatus(AttackStatus.DODGE))
      return AttackStatus.DODGE; 
    return null;
  }









  
  private static void calculateEffectResult(Effect effect, Creature effected, int damage, AttackStatus status) {
    AttackResult attackResult = new AttackResult(damage, status);
    effected.getObserveController().checkShieldStatus(Collections.singletonList(attackResult));
    effect.setReserved1(attackResult.getDamage());
    effect.setAttackStatus(attackResult.getAttackStatus());
    effect.setShieldDefense(attackResult.getShieldType());
  }







  
  public static void calculateMagicalSkillAttackResult(Effect effect, int skillDamage, SkillElement element) {
    Creature effector = effect.getEffector();
    Creature effected = effect.getEffected();
    
    int damage = StatFunctions.calculateMagicDamageToTarget(effector, effected, skillDamage, element);
    
    AttackStatus status = calculateMagicalStatus(effector, effected);
    switch (status) {
      
      case null:
        damage = 0;
        break;
    } 


    
    calculateEffectResult(effect, effected, damage, status);
  }






  
  public static AttackStatus calculatePhysicalStatus(Creature attacker, Creature attacked) {
    if (Rnd.get(0, 100) < StatFunctions.calculatePhysicalDodgeRate(attacker, attacked)) {
      return AttackStatus.DODGE;
    }
    if (attacked instanceof Player && ((Player)attacked).getEquipment().getMainHandWeaponType() != null && Rnd.get(0, 100) < StatFunctions.calculatePhysicalParryRate(attacker, attacked))
    {
      return AttackStatus.PARRY;
    }
    if (attacked instanceof Player && ((Player)attacked).getEquipment().isShieldEquipped() && Rnd.get(0, 100) < StatFunctions.calculatePhysicalBlockRate(attacker, attacked))
    {
      return AttackStatus.BLOCK;
    }
    
    if (attacker instanceof Player && ((Player)attacker).getEquipment().getMainHandWeaponType() != null && Rnd.get(0, 100) < StatFunctions.calculatePhysicalCriticalRate(attacker, attacked))
    {
      return AttackStatus.CRITICAL;
    }
    return AttackStatus.NORMALHIT;
  }


  
  public static AttackStatus calculateMagicalStatus(Creature attacker, Creature attacked) {
    if (Rnd.get(0, 100) < StatFunctions.calculateMagicalResistRate(attacker, attacked)) {
      return AttackStatus.RESIST;
    }
    return AttackStatus.NORMALHIT;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AttackUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

/*     */ package com.aionemu.gameserver.controllers.attack;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.model.SkillElement;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.utils.stats.StatFunctions;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttackUtil
/*     */ {
/*     */   public static List<AttackResult> calculateAttackResult(Creature attacker, Creature attacked) {
/*  49 */     int damage = StatFunctions.calculateBaseDamageToTarget(attacker, attacked);
/*     */     
/*  51 */     AttackStatus status = calculateAttackerPhysicalStatus(attacker);
/*     */     
/*  53 */     if (status == null) {
/*  54 */       status = calculatePhysicalStatus(attacker, attacked);
/*     */     }
/*  56 */     CreatureGameStats<?> gameStats = attacker.getGameStats();
/*     */     
/*  58 */     if (attacker instanceof Player && ((Player)attacker).getEquipment().getOffHandWeaponType() != null) {
/*     */ 
/*     */ 
/*     */       
/*  62 */       switch (status)
/*     */       
/*     */       { case DAGGER_1H:
/*  65 */           offHandStatus = AttackStatus.OFFHAND_BLOCK;
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
/*  81 */           offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked);
/*     */           
/*  83 */           mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS));
/*  84 */           offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS));
/*     */           
/*  86 */           list = new ArrayList<AttackResult>();
/*  87 */           list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status));
/*  88 */           list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus));
/*  89 */           attacked.getObserveController().checkShieldStatus(list);
/*     */           
/*  91 */           return list;case SWORD_1H: offHandStatus = AttackStatus.OFFHAND_DODGE; offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list;case MACE_1H: offHandStatus = AttackStatus.OFFHAND_CRITICAL; offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list;case SWORD_2H: offHandStatus = AttackStatus.OFFHAND_PARRY; offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list; }  AttackStatus offHandStatus = AttackStatus.OFFHAND_NORMALHIT; int offHandDamage = StatFunctions.calculateOffHandPhysicDamageToTarget(attacker, attacked); int mainHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS)); int offHandHits = Rnd.get(1, gameStats.getCurrentStat(StatEnum.OFF_HAND_HITS)); List<AttackResult> list = new ArrayList<AttackResult>(); list.addAll(splitPhysicalDamage(attacker, attacked, mainHandHits, damage, status)); list.addAll(splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus)); attacked.getObserveController().checkShieldStatus(list); return list;
/*     */     } 
/*     */     
/*  94 */     int hitCount = Rnd.get(1, gameStats.getCurrentStat(StatEnum.MAIN_HAND_HITS));
/*  95 */     List<AttackResult> attackList = splitPhysicalDamage(attacker, attacked, hitCount, damage, status);
/*  96 */     attacked.getObserveController().checkShieldStatus(attackList);
/*  97 */     return attackList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<AttackResult> splitPhysicalDamage(Creature attacker, Creature attacked, int hitCount, int damage, AttackStatus status) {
/* 103 */     List<AttackResult> attackList = new ArrayList<AttackResult>();
/*     */     
/* 105 */     for (int i = 0; i < hitCount; i++) {
/*     */       WeaponType weaponType;
/* 107 */       int shieldDamageReduce, damages = damage;
/*     */       
/* 109 */       if (i != 0)
/*     */       {
/* 111 */         damages = Math.round(damage * 0.1F);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       switch (status) {
/*     */         
/*     */         case DAGGER_1H:
/*     */         case POLEARM_2H:
/* 121 */           shieldDamageReduce = ((Player)attacked).getGameStats().getCurrentStat(StatEnum.DAMAGE_REDUCE);
/* 122 */           damages -= Math.round((damages * shieldDamageReduce / 100));
/*     */           break;
/*     */         
/*     */         case SWORD_1H:
/*     */         case STAFF_2H:
/* 127 */           damages = 0;
/*     */           break;
/*     */         
/*     */         case MACE_1H:
/* 131 */           weaponType = ((Player)attacker).getEquipment().getMainHandWeaponType();
/* 132 */           damages = calculateWeaponCritical(damages, weaponType);
/*     */           break;
/*     */         
/*     */         case BOW:
/* 136 */           weaponType = ((Player)attacker).getEquipment().getOffHandWeaponType();
/* 137 */           damages = calculateWeaponCritical(damages, weaponType);
/*     */           break;
/*     */         
/*     */         case SWORD_2H:
/*     */         case null:
/* 142 */           damages = (int)(damages * 0.5D);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 148 */       attackList.add(new AttackResult(damages, status));
/*     */     } 
/* 150 */     return attackList;
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
/*     */   private static int calculateWeaponCritical(int damages, WeaponType weaponType) {
/* 171 */     switch (weaponType)
/*     */     
/*     */     { case DAGGER_1H:
/* 174 */         damages = Math.round(damages * 2.3F);
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
/* 194 */         return damages;case SWORD_1H: damages = Math.round(damages * 2.2F); return damages;case MACE_1H: damages *= 2; return damages;case SWORD_2H: case POLEARM_2H: damages = Math.round(damages * 1.8F); return damages;case STAFF_2H: case BOW: damages = Math.round(damages * 1.8F); return damages; }  damages = Math.round(damages * 1.5F); return damages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void calculatePhysicalSkillAttackResult(Effect effect, int skillDamage) {
/*     */     int shieldDamageReduce;
/* 204 */     Creature effector = effect.getEffector();
/* 205 */     Creature effected = effect.getEffected();
/* 206 */     int damage = StatFunctions.calculatePhysicDamageToTarget(effector, effected, skillDamage);
/*     */     
/* 208 */     AttackStatus status = calculateAttackerPhysicalStatus(effector);
/*     */     
/* 210 */     if (status == null) {
/* 211 */       status = calculatePhysicalStatus(effector, effected);
/*     */     }
/* 213 */     switch (status) {
/*     */       
/*     */       case DAGGER_1H:
/* 216 */         shieldDamageReduce = ((Player)effected).getGameStats().getCurrentStat(StatEnum.DAMAGE_REDUCE);
/* 217 */         damage -= Math.round((damage * shieldDamageReduce / 100));
/*     */         break;
/*     */       case SWORD_1H:
/* 220 */         damage = 0;
/*     */         break;
/*     */       case MACE_1H:
/* 223 */         damage *= 2;
/*     */         break;
/*     */       case SWORD_2H:
/* 226 */         damage = (int)(damage * 0.5D);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 232 */     calculateEffectResult(effect, effected, damage, status);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AttackStatus calculateAttackerPhysicalStatus(Creature effector) {
/* 243 */     if (effector.getObserveController().checkAttackerStatus(AttackStatus.DODGE))
/* 244 */       return AttackStatus.DODGE; 
/* 245 */     return null;
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
/*     */   private static void calculateEffectResult(Effect effect, Creature effected, int damage, AttackStatus status) {
/* 258 */     AttackResult attackResult = new AttackResult(damage, status);
/* 259 */     effected.getObserveController().checkShieldStatus(Collections.singletonList(attackResult));
/* 260 */     effect.setReserved1(attackResult.getDamage());
/* 261 */     effect.setAttackStatus(attackResult.getAttackStatus());
/* 262 */     effect.setShieldDefense(attackResult.getShieldType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void calculateMagicalSkillAttackResult(Effect effect, int skillDamage, SkillElement element) {
/* 273 */     Creature effector = effect.getEffector();
/* 274 */     Creature effected = effect.getEffected();
/*     */     
/* 276 */     int damage = StatFunctions.calculateMagicDamageToTarget(effector, effected, skillDamage, element);
/*     */     
/* 278 */     AttackStatus status = calculateMagicalStatus(effector, effected);
/* 279 */     switch (status) {
/*     */       
/*     */       case null:
/* 282 */         damage = 0;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 288 */     calculateEffectResult(effect, effected, damage, status);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttackStatus calculatePhysicalStatus(Creature attacker, Creature attacked) {
/* 298 */     if (Rnd.get(0, 100) < StatFunctions.calculatePhysicalDodgeRate(attacker, attacked)) {
/* 299 */       return AttackStatus.DODGE;
/*     */     }
/* 301 */     if (attacked instanceof Player && ((Player)attacked).getEquipment().getMainHandWeaponType() != null && Rnd.get(0, 100) < StatFunctions.calculatePhysicalParryRate(attacker, attacked))
/*     */     {
/* 303 */       return AttackStatus.PARRY;
/*     */     }
/* 305 */     if (attacked instanceof Player && ((Player)attacked).getEquipment().isShieldEquipped() && Rnd.get(0, 100) < StatFunctions.calculatePhysicalBlockRate(attacker, attacked))
/*     */     {
/* 307 */       return AttackStatus.BLOCK;
/*     */     }
/*     */     
/* 310 */     if (attacker instanceof Player && ((Player)attacker).getEquipment().getMainHandWeaponType() != null && Rnd.get(0, 100) < StatFunctions.calculatePhysicalCriticalRate(attacker, attacked))
/*     */     {
/* 312 */       return AttackStatus.CRITICAL;
/*     */     }
/* 314 */     return AttackStatus.NORMALHIT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttackStatus calculateMagicalStatus(Creature attacker, Creature attacked) {
/* 320 */     if (Rnd.get(0, 100) < StatFunctions.calculateMagicalResistRate(attacker, attacked)) {
/* 321 */       return AttackStatus.RESIST;
/*     */     }
/* 323 */     return AttackStatus.NORMALHIT;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AttackUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
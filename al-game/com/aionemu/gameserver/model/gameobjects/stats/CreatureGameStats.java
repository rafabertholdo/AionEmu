/*     */ package com.aionemu.gameserver.model.gameobjects.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.model.SkillElement;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.ItemStatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javolution.text.TextBuilder;
/*     */ import javolution.util.FastList;
/*     */ import javolution.util.FastMap;
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
/*     */ public class CreatureGameStats<T extends Creature>
/*     */ {
/*  45 */   protected static final Logger log = Logger.getLogger(CreatureGameStats.class);
/*     */   
/*     */   private static final int ATTACK_MAX_COUNTER = 2147483647;
/*     */   
/*     */   protected FastMap<StatEnum, Stat> stats;
/*     */   
/*     */   protected FastMap<StatEffectId, TreeSet<StatModifier>> statsModifiers;
/*     */   
/*  53 */   private int attackCounter = 0;
/*  54 */   protected T owner = null;
/*  55 */   protected final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CreatureGameStats(T owner) {
/*  63 */     this.owner = owner;
/*  64 */     this.stats = new FastMap();
/*  65 */     this.statsModifiers = (new FastMap()).shared();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttackCounter() {
/*  73 */     return this.attackCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAttackCounter(int attackCounter) {
/*  82 */     if (attackCounter <= 0) {
/*     */       
/*  84 */       this.attackCounter = 1;
/*     */     }
/*     */     else {
/*     */       
/*  88 */       this.attackCounter = attackCounter;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void increaseAttackCounter() {
/*  94 */     if (this.attackCounter == Integer.MAX_VALUE) {
/*     */       
/*  96 */       this.attackCounter = 1;
/*     */     }
/*     */     else {
/*     */       
/* 100 */       this.attackCounter++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStat(StatEnum stat, int value) {
/* 111 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/* 114 */       setStat(stat, value, false);
/*     */     }
/*     */     finally {
/*     */       
/* 118 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseStat(StatEnum stat) {
/* 128 */     int value = 0;
/* 129 */     this.lock.readLock().lock();
/*     */     
/*     */     try {
/* 132 */       if (this.stats.containsKey(stat))
/*     */       {
/* 134 */         value = ((Stat)this.stats.get(stat)).getBase();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 139 */       this.lock.readLock().unlock();
/*     */     } 
/* 141 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatBonus(StatEnum stat) {
/* 151 */     int value = 0;
/* 152 */     this.lock.readLock().lock();
/*     */     
/*     */     try {
/* 155 */       if (this.stats.containsKey(stat))
/*     */       {
/* 157 */         value = ((Stat)this.stats.get(stat)).getBonus();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 162 */       this.lock.readLock().unlock();
/*     */     } 
/* 164 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentStat(StatEnum stat) {
/* 174 */     int value = 0;
/*     */     
/* 176 */     this.lock.readLock().lock();
/*     */     
/*     */     try {
/* 179 */       if (this.stats.containsKey(stat))
/*     */       {
/* 181 */         value = ((Stat)this.stats.get(stat)).getCurrent();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 186 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/* 189 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOldStat(StatEnum stat) {
/* 199 */     int value = 0;
/*     */     
/* 201 */     this.lock.readLock().lock();
/*     */     
/*     */     try {
/* 204 */       if (this.stats.containsKey(stat))
/*     */       {
/* 206 */         value = ((Stat)this.stats.get(stat)).getOld();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 211 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/* 214 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addModifiers(StatEffectId id, TreeSet<StatModifier> modifiers) {
/* 224 */     if (modifiers == null) {
/*     */       return;
/*     */     }
/* 227 */     if (this.statsModifiers.containsKey(id)) {
/* 228 */       throw new IllegalArgumentException("Effect " + id + " already active");
/*     */     }
/* 230 */     this.statsModifiers.put(id, modifiers);
/* 231 */     recomputeStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean effectAlreadyAdded(StatEffectId id) {
/* 239 */     return this.statsModifiers.containsKey(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recomputeStats() {
/* 248 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/* 251 */       resetStats();
/* 252 */       FastMap<StatEnum, StatModifiers> orderedModifiers = new FastMap();
/*     */       
/* 254 */       for (Map.Entry<StatEffectId, TreeSet<StatModifier>> modifiers : (Iterable<Map.Entry<StatEffectId, TreeSet<StatModifier>>>)this.statsModifiers.entrySet()) {
/*     */         
/* 256 */         StatEffectId eid = modifiers.getKey();
/* 257 */         int slots = 0;
/*     */         
/* 259 */         if (modifiers.getValue() == null) {
/*     */           continue;
/*     */         }
/* 262 */         for (StatModifier modifier : modifiers.getValue()) {
/*     */           
/* 264 */           if (eid instanceof ItemStatEffectId)
/*     */           {
/* 266 */             slots = ((ItemStatEffectId)eid).getSlot();
/*     */           }
/* 268 */           if (slots == 0)
/* 269 */             slots = ItemSlot.NONE.getSlotIdMask(); 
/* 270 */           if (modifier.getStat().isMainOrSubHandStat() && this.owner instanceof Player)
/*     */           {
/* 272 */             if (slots != ItemSlot.MAIN_HAND.getSlotIdMask() && slots != ItemSlot.SUB_HAND.getSlotIdMask()) {
/*     */               
/* 274 */               if (((Player)this.owner).getEquipment().getOffHandWeaponType() != null) {
/* 275 */                 slots = ItemSlot.MAIN_OR_SUB.getSlotIdMask();
/*     */               } else {
/*     */                 
/* 278 */                 slots = ItemSlot.MAIN_HAND.getSlotIdMask();
/* 279 */                 setStat(StatEnum.OFF_HAND_ACCURACY, 0, false);
/*     */               }
/*     */             
/* 282 */             } else if (slots == ItemSlot.MAIN_HAND.getSlotIdMask()) {
/* 283 */               setStat(StatEnum.MAIN_HAND_POWER, 0);
/*     */             } 
/*     */           }
/* 286 */           FastList fastList = ItemSlot.getSlotsFor(slots);
/* 287 */           for (ItemSlot slot : fastList) {
/*     */             
/* 289 */             StatEnum statToModify = modifier.getStat().getMainOrSubHandStat(slot);
/* 290 */             if (!orderedModifiers.containsKey(statToModify))
/*     */             {
/* 292 */               orderedModifiers.put(statToModify, new StatModifiers());
/*     */             }
/* 294 */             ((StatModifiers)orderedModifiers.get(statToModify)).add(modifier);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 299 */       for (Map.Entry<StatEnum, StatModifiers> entry : (Iterable<Map.Entry<StatEnum, StatModifiers>>)orderedModifiers.entrySet())
/*     */       {
/* 301 */         applyModifiers(entry.getKey(), entry.getValue());
/*     */       }
/*     */       
/* 304 */       setStat(StatEnum.ATTACK_SPEED, Math.round(getBaseStat(StatEnum.MAIN_HAND_ATTACK_SPEED) + getBaseStat(StatEnum.OFF_HAND_ATTACK_SPEED) * 0.25F), false);
/*     */ 
/*     */       
/* 307 */       setStat(StatEnum.ATTACK_SPEED, getStatBonus(StatEnum.MAIN_HAND_ATTACK_SPEED) + getStatBonus(StatEnum.OFF_HAND_ATTACK_SPEED), true);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 312 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEffect(StatEffectId id) {
/* 322 */     this.statsModifiers.remove(id);
/* 323 */     recomputeStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMagicalDefenseFor(SkillElement element) {
/* 333 */     switch (element) {
/*     */       
/*     */       case MAXHP:
/* 336 */         return getCurrentStat(StatEnum.EARTH_RESISTANCE);
/*     */       case MAXMP:
/* 338 */         return getCurrentStat(StatEnum.FIRE_RESISTANCE);
/*     */       case null:
/* 340 */         return getCurrentStat(StatEnum.WATER_RESISTANCE);
/*     */       case null:
/* 342 */         return getCurrentStat(StatEnum.WIND_RESISTANCE);
/*     */     } 
/* 344 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetStats() {
/* 354 */     for (Stat stat : this.stats.values())
/*     */     {
/* 356 */       stat.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyModifiers(final StatEnum stat, StatModifiers modifiers) {
/* 367 */     if (modifiers == null) {
/*     */       return;
/*     */     }
/* 370 */     if (!this.stats.containsKey(stat))
/*     */     {
/* 372 */       initStat(stat, 0);
/*     */     }
/*     */     
/* 375 */     Stat oStat = (Stat)this.stats.get(stat);
/* 376 */     for (StatModifierPriority priority : StatModifierPriority.values()) {
/*     */       
/* 378 */       FastList<StatModifier> mod = modifiers.getModifiers(priority);
/* 379 */       for (FastList.Node<StatModifier> n = mod.head(), listEnd = mod.tail(); (n = n.getNext()) != listEnd; ) {
/*     */         
/* 381 */         StatModifier modifier = (StatModifier)n.getValue();
/* 382 */         final int newValue = modifier.apply(oStat.getBase(), oStat.getCurrent());
/* 383 */         oStat.increase(newValue, modifier.isBonus());
/*     */       } 
/*     */     } 
/*     */     
/* 387 */     if (stat == StatEnum.MAXHP || stat == StatEnum.MAXMP) {
/*     */       
/* 389 */       final int oldValue = getOldStat(stat);
/* 390 */       final int newValue = getCurrentStat(stat);
/* 391 */       if (oldValue == newValue) {
/*     */         return;
/*     */       }
/*     */       
/* 395 */       final CreatureLifeStats<? extends Creature> lifeStats = this.owner.getLifeStats();
/* 396 */       ThreadPoolManager.getInstance().schedule(new Runnable() {
/*     */             public void run() {
/*     */               int hp;
/*     */               int mp;
/* 400 */               switch (stat) {
/*     */                 
/*     */                 case MAXHP:
/* 403 */                   hp = lifeStats.getCurrentHp();
/* 404 */                   hp = hp * newValue / oldValue;
/* 405 */                   lifeStats.setCurrentHp(hp);
/*     */                   break;
/*     */                 case MAXMP:
/* 408 */                   mp = lifeStats.getCurrentMp();
/* 409 */                   mp = mp * newValue / oldValue;
/* 410 */                   lifeStats.setCurrentMp(mp);
/*     */                   break;
/*     */               } 
/*     */             }
/*     */           },  0L);
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
/*     */   protected void initStat(StatEnum stat, int value) {
/* 426 */     if (!this.stats.containsKey(stat)) {
/*     */       
/* 428 */       this.stats.put(stat, new Stat(stat, value));
/*     */     }
/*     */     else {
/*     */       
/* 432 */       ((Stat)this.stats.get(stat)).reset();
/* 433 */       ((Stat)this.stats.get(stat)).set(value, false);
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
/*     */   protected void setStat(StatEnum stat, int value, boolean bonus) {
/* 445 */     if (!this.stats.containsKey(stat))
/*     */     {
/* 447 */       this.stats.put(stat, new Stat(stat, 0));
/*     */     }
/* 449 */     ((Stat)this.stats.get(stat)).set(value, bonus);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 455 */     TextBuilder tb = TextBuilder.newInstance();
/*     */     
/* 457 */     tb.append('{');
/* 458 */     tb.append("owner:" + this.owner.getObjectId());
/* 459 */     for (Stat stat : this.stats.values())
/*     */     {
/* 461 */       tb.append(stat);
/*     */     }
/* 463 */     tb.append('}');
/* 464 */     String toString = tb.toString();
/*     */     
/* 466 */     TextBuilder.recycle(tb);
/*     */     
/* 468 */     return toString;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\CreatureGameStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
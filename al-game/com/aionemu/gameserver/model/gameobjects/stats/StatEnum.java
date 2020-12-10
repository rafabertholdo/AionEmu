/*     */ package com.aionemu.gameserver.model.gameobjects.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
/*     */ import javax.xml.bind.annotation.XmlEnum;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlType(name = "StatEnum")
/*     */ @XmlEnum
/*     */ public enum StatEnum
/*     */ {
/*  57 */   MAXDP(0, "maxdp"),
/*  58 */   MAXHP(18, "maxhp"),
/*  59 */   MAXMP(20, "maxmp"),
/*     */   
/*  61 */   AGILITY(107, "agility", true),
/*  62 */   BLOCK(33, "block"),
/*  63 */   EVASION(31, "dodge"),
/*  64 */   CONCENTRATION(41, "concentration"),
/*  65 */   WILL(0, "will", true),
/*  66 */   HEALTH(0, "health", true),
/*  67 */   ACCURACY(0, "accuracy", true),
/*  68 */   KNOWLEDGE(106, "knowledge", true),
/*  69 */   PARRY(32, "parry"),
/*  70 */   POWER(0, "strength", true),
/*  71 */   SPEED(36, "speed", true),
/*  72 */   HIT_COUNT(0, "hitcount", true),
/*     */   
/*  74 */   ATTACK_RANGE(0, "attackrange", true),
/*  75 */   ATTACK_SPEED(29, "attackdelay", -1, true),
/*  76 */   PHYSICAL_ATTACK(25, "phyattack"),
/*  77 */   PHYSICAL_ACCURACY(30, "hitaccuracy"),
/*  78 */   PHYSICAL_CRITICAL(34, "critical"),
/*  79 */   PHYSICAL_DEFENSE(26, "physicaldefend"),
/*  80 */   MAIN_HAND_HITS(0, "mainhandhits"),
/*  81 */   MAIN_HAND_ACCURACY(0, "mainhandaccuracy"),
/*  82 */   MAIN_HAND_CRITICAL(0, "mainhandcritical"),
/*  83 */   MAIN_HAND_POWER(0, "mainhandpower"),
/*  84 */   MAIN_HAND_ATTACK_SPEED(0, "mainhandattackspeed"),
/*  85 */   OFF_HAND_HITS(0, "offhandhits"),
/*  86 */   OFF_HAND_ACCURACY(0, "offhandaccuracy"),
/*  87 */   OFF_HAND_CRITICAL(0, "offhandcritical"),
/*  88 */   OFF_HAND_POWER(0, "offhandpower"),
/*  89 */   OFF_HAND_ATTACK_SPEED(0, "offhandattackspeed"),
/*  90 */   CRITICAL_RESIST(0, "physicalcriticalreducerate"),
/*     */   
/*  92 */   MAGICAL_ATTACK(27, "magicalattack"),
/*  93 */   MAGICAL_ACCURACY(105, "magicalhitaccuracy"),
/*  94 */   MAGICAL_CRITICAL(40, "magicalcritical"),
/*  95 */   MAGICAL_RESIST(28, "magicalresist"),
/*  96 */   MAX_DAMAGES(0, "maxdamages"),
/*  97 */   MIN_DAMAGES(0, "mindamages"),
/*  98 */   IS_MAGICAL_ATTACK(0, "ismagicalattack", true),
/*     */   
/* 100 */   EARTH_RESISTANCE(0, "elementaldefendearth"),
/* 101 */   FIRE_RESISTANCE(15, "elementaldefendfire"),
/* 102 */   WIND_RESISTANCE(0, "elementaldefendair"),
/* 103 */   WATER_RESISTANCE(0, "elementaldefendwater"),
/*     */   
/* 105 */   BOOST_MAGICAL_SKILL(104, "magicalskillboost"),
/* 106 */   BOOST_CASTING_TIME(0, "boostcastingtime"),
/* 107 */   BOOST_HATE(109, "boosthate"),
/* 108 */   BOOST_HEAL(0, "boostheal"),
/*     */   
/* 110 */   FLY_TIME(23, "maxfp"),
/* 111 */   FLY_SPEED(37, "flyspeed"),
/*     */   
/* 113 */   PVP_ATTACK_RATIO(0, "pvpattackratio"),
/* 114 */   PVP_DEFEND_RATIO(0, "pvpdefendratio"),
/*     */   
/* 116 */   DAMAGE_REDUCE(0, "damagereduce"),
/*     */   
/* 118 */   BLEED_RESISTANCE(0, "arbleed"),
/* 119 */   BLIND_RESISTANCE(0, "arblind"),
/* 120 */   CHARM_RESISTANCE(0, "archarm"),
/* 121 */   CONFUSE_RESISTANCE(0, "arconfuse"),
/* 122 */   CURSE_RESISTANCE(0, "arcurse"),
/* 123 */   DISEASE_RESISTANCE(0, "ardisease"),
/* 124 */   FEAR_RESISTANCE(0, "arfear"),
/* 125 */   OPENAREIAL_RESISTANCE(0, "aropenareial"),
/* 126 */   PARALYZE_RESISTANCE(0, "arparalyze"),
/* 127 */   PERIFICATION_RESISTANCE(0, "arperification"),
/* 128 */   POISON_RESISTANCE(0, "arpoison"),
/* 129 */   ROOT_RESISTANCE(0, "arroot"),
/* 130 */   SILENCE_RESISTANCE(0, "arsilence"),
/* 131 */   SLEEP_RESISTANCE(0, "arsleep"),
/* 132 */   SLOW_RESISTANCE(0, "arslow"),
/* 133 */   SNARE_RESISTANCE(0, "arsnare"),
/* 134 */   SPIN_RESISTANCE(0, "arspin"),
/* 135 */   STAGGER_RESISTANCE(0, "arstagger"),
/* 136 */   STUMBLE_RESISTANCE(0, "arstumble"),
/* 137 */   STUN_RESISTANCE(0, "arstun"),
/*     */   
/* 139 */   REGEN_MP(0, "mpregen"),
/* 140 */   REGEN_HP(0, "hpregen"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   REGEN_FP(0, "fpregen"),
/* 146 */   STAGGER_BOOST(0, "stagger_arp"),
/* 147 */   STUMBLE_BOOST(0, "stumble_arp"),
/* 148 */   STUN_BOOST(0, "stun_arp"),
/* 149 */   HEAL_BOOST(0, "healskillboost"),
/* 150 */   ALLRESIST(0, "allresist"),
/* 151 */   STUNLIKE_RESISTANCE(0, "arstunlike"),
/* 152 */   ELEMENTAL_RESISTANCE_DARK(0, "elemental_resistance_dark"),
/* 153 */   ELEMENTAL_RESISTANCE_LIGHT(0, "elemental_resistance_light"),
/* 154 */   MAGICAL_CRITICAL_RESIST(0, "magicalcriticalresist"),
/* 155 */   MAGICAL_CRITICAL_DAMAGE_REDUCE(0, "magicalcriticaldamagereduce"),
/* 156 */   PHYSICAL_CRITICAL_RESIST(0, "physicalcriticalresist"),
/* 157 */   PHYSICAL_CRITICAL_DAMAGE_REDUCE(0, "physicalcriticalreducerate"),
/* 158 */   ERFIRE(0, "erfire"),
/* 159 */   ERAIR(0, "erair"),
/* 160 */   EREARTH(0, "erearth"),
/* 161 */   ERWATER(0, "erwater"),
/* 162 */   ABNORMAL_RESISTANCE_ALL(0, "abnormal_resistance_all"),
/* 163 */   MAGICAL_DEFEND(0, "magical_defend"),
/* 164 */   ALLPARA(0, "allpara"),
/* 165 */   KNOWIL(0, "knowil"),
/* 166 */   AGIDEX(0, "agidex"),
/* 167 */   STRVIT(0, "strvit");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean replace;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int sign;
/*     */ 
/*     */ 
/*     */   
/*     */   private int itemStoneMask;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StatEnum(int stoneMask, String name, int sign, boolean replace) {
/* 192 */     this.itemStoneMask = stoneMask;
/* 193 */     this.name = name;
/* 194 */     this.replace = replace;
/* 195 */     this.sign = sign;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 200 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSign() {
/* 205 */     return this.sign;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemStoneMask() {
/* 213 */     return this.itemStoneMask;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StatEnum find(String name) {
/* 218 */     for (StatEnum sEnum : values()) {
/*     */       
/* 220 */       if (sEnum.getName().toLowerCase().equals(name.toLowerCase()))
/*     */       {
/* 222 */         return sEnum;
/*     */       }
/*     */     } 
/* 225 */     throw new IllegalArgumentException("Cannot find StatEnum for: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StatEnum findByItemStoneMask(int mask) {
/* 236 */     for (StatEnum sEnum : values()) {
/*     */       
/* 238 */       if (sEnum.getItemStoneMask() == mask)
/*     */       {
/* 240 */         return sEnum;
/*     */       }
/*     */     } 
/* 243 */     throw new IllegalArgumentException("Cannot find StatEnum for stone mask: " + mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public StatEnum getMainOrSubHandStat(ItemSlot slot) {
/* 248 */     if (slot == null)
/* 249 */       return this; 
/* 250 */     switch (this) {
/*     */       
/*     */       case PHYSICAL_ATTACK:
/*     */       case POWER:
/* 254 */         switch (slot) {
/*     */           
/*     */           case PHYSICAL_ATTACK:
/* 257 */             return OFF_HAND_POWER;
/*     */           case POWER:
/* 259 */             return MAIN_HAND_POWER;
/*     */         } 
/*     */       case PHYSICAL_ACCURACY:
/* 262 */         switch (slot) {
/*     */           
/*     */           case PHYSICAL_ATTACK:
/* 265 */             return OFF_HAND_ACCURACY;
/*     */           case POWER:
/* 267 */             return MAIN_HAND_ACCURACY;
/*     */         } 
/*     */       case PHYSICAL_CRITICAL:
/* 270 */         switch (slot) {
/*     */           
/*     */           case PHYSICAL_ATTACK:
/* 273 */             return OFF_HAND_CRITICAL;
/*     */           case POWER:
/* 275 */             return MAIN_HAND_CRITICAL;
/*     */         } 
/*     */       case HIT_COUNT:
/* 278 */         switch (slot) {
/*     */           
/*     */           case PHYSICAL_ATTACK:
/* 281 */             return OFF_HAND_HITS;
/*     */           case POWER:
/* 283 */             return MAIN_HAND_HITS;
/*     */         } 
/*     */       case ATTACK_SPEED:
/* 286 */         switch (slot) {
/*     */           
/*     */           case PHYSICAL_ATTACK:
/* 289 */             return OFF_HAND_ATTACK_SPEED;
/*     */         } 
/* 291 */         return MAIN_HAND_ATTACK_SPEED;
/*     */     } 
/*     */     
/* 294 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMainOrSubHandStat() {
/* 300 */     switch (this) {
/*     */       
/*     */       case PHYSICAL_ATTACK:
/*     */       case POWER:
/*     */       case PHYSICAL_ACCURACY:
/*     */       case PHYSICAL_CRITICAL:
/* 306 */         return true;
/*     */     } 
/*     */     
/* 309 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReplace() {
/* 314 */     return this.replace;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\StatEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
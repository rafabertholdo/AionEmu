/*     */ package com.aionemu.gameserver.controllers.attack;
/*     */ 
/*     */ import com.aionemu.commons.utils.SingletonMap;
/*     */ import com.aionemu.gameserver.ai.events.Event;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ public class AggroList
/*     */ {
/*     */   private Creature owner;
/*  36 */   private final Map<Creature, AggroInfo> aggroList = (Map<Creature, AggroInfo>)(new SingletonMap()).setShared();
/*     */ 
/*     */   
/*     */   public AggroList(Creature owner) {
/*  40 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDamage(Creature creature, int damage) {
/*  51 */     if (creature == null || !this.owner.isEnemy((VisibleObject)creature)) {
/*     */       return;
/*     */     }
/*     */     
/*  55 */     AggroInfo ai = getAggroInfo(creature);
/*  56 */     ai.addDamage(damage);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     ai.addHate(damage);
/*     */     
/*  63 */     this.owner.getAi().handleEvent(Event.ATTACKED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHate(Creature creature, int hate) {
/*  74 */     if (creature == null || creature == this.owner || !this.owner.isEnemy((VisibleObject)creature)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  79 */     AggroInfo ai = getAggroInfo(creature);
/*  80 */     ai.addHate(hate);
/*     */     
/*  82 */     this.owner.getAi().handleEvent(Event.ATTACKED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AionObject getMostDamage() {
/*  90 */     AionObject mostDamage = null;
/*  91 */     int maxDamage = 0;
/*     */     
/*  93 */     for (AggroInfo ai : getFinalDamageList(true)) {
/*     */       
/*  95 */       if (ai.getAttacker() == null) {
/*     */         continue;
/*     */       }
/*  98 */       if (ai.getDamage() > maxDamage) {
/*     */         
/* 100 */         mostDamage = ai.getAttacker();
/* 101 */         maxDamage = ai.getDamage();
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return mostDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getMostPlayerDamage() {
/* 113 */     if (this.aggroList.isEmpty()) {
/* 114 */       return null;
/*     */     }
/* 116 */     Player mostDamage = null;
/* 117 */     int maxDamage = 0;
/*     */ 
/*     */     
/* 120 */     for (AggroInfo ai : getFinalDamageList(false)) {
/*     */       
/* 122 */       if (ai.getDamage() > maxDamage) {
/*     */         
/* 124 */         mostDamage = (Player)ai.getAttacker();
/* 125 */         maxDamage = ai.getDamage();
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return mostDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getMostHated() {
/* 138 */     if (this.aggroList.isEmpty()) {
/* 139 */       return null;
/*     */     }
/* 141 */     Creature mostHated = null;
/* 142 */     int maxHate = 0;
/*     */     
/* 144 */     for (AggroInfo ai : this.aggroList.values()) {
/*     */       
/* 146 */       if (ai == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 150 */       Creature attacker = (Creature)ai.getAttacker();
/*     */       
/* 152 */       if (attacker.getLifeStats().isAlreadyDead() || !this.owner.getKnownList().knowns(ai.getAttacker()))
/*     */       {
/* 154 */         ai.setHate(0);
/*     */       }
/* 156 */       if (ai.getHate() > maxHate) {
/*     */         
/* 158 */         mostHated = attacker;
/* 159 */         maxHate = ai.getHate();
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     return mostHated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMostHated(Creature creature) {
/* 173 */     if (creature == null || creature.getLifeStats().isAlreadyDead()) {
/* 174 */       return false;
/*     */     }
/* 176 */     Creature mostHated = getMostHated();
/* 177 */     if (mostHated == null) {
/* 178 */       return false;
/*     */     }
/* 180 */     return mostHated.equals(creature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyHate(Creature creature, int value) {
/* 189 */     if (isHating(creature)) {
/* 190 */       addHate(creature, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopHating(Creature creature) {
/* 199 */     AggroInfo aggroInfo = this.aggroList.get(creature);
/* 200 */     if (aggroInfo != null) {
/* 201 */       aggroInfo.setHate(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Creature creature) {
/* 211 */     this.aggroList.remove(creature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 219 */     this.aggroList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AggroInfo getAggroInfo(Creature creature) {
/* 229 */     AggroInfo ai = this.aggroList.get(creature);
/* 230 */     if (ai == null) {
/*     */       
/* 232 */       ai = new AggroInfo((AionObject)creature);
/* 233 */       this.aggroList.put(creature, ai);
/*     */     } 
/* 235 */     return ai;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isHating(Creature creature) {
/* 245 */     return this.aggroList.containsKey(creature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<AggroInfo> getList() {
/* 253 */     return this.aggroList.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalDamage() {
/* 261 */     int totalDamage = 0;
/* 262 */     for (AggroInfo ai : this.aggroList.values())
/*     */     {
/* 264 */       totalDamage += ai.getDamage();
/*     */     }
/* 266 */     return totalDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<AggroInfo> getFinalDamageList(boolean mergeGroupDamage) {
/* 276 */     SingletonMap<Player, AggroInfo> singletonMap = (new SingletonMap()).setShared();
/*     */     
/* 278 */     for (AggroInfo ai : this.aggroList.values()) {
/*     */       
/* 280 */       if (!(ai.getAttacker() instanceof Creature)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 285 */       Creature master = ((Creature)ai.getAttacker()).getMaster();
/*     */       
/* 287 */       if (!(master instanceof Player)) {
/*     */         continue;
/*     */       }
/* 290 */       Player player = (Player)master;
/*     */ 
/*     */ 
/*     */       
/* 294 */       if (!this.owner.getKnownList().knowns((AionObject)player)) {
/*     */         continue;
/*     */       }
/* 297 */       if (mergeGroupDamage) {
/*     */         Player player1;
/*     */ 
/*     */         
/* 301 */         if (player.isInAlliance()) {
/*     */           
/* 303 */           PlayerAlliance playerAlliance = player.getPlayerAlliance();
/*     */         }
/* 305 */         else if (player.isInGroup()) {
/*     */           
/* 307 */           PlayerGroup playerGroup = player.getPlayerGroup();
/*     */         }
/*     */         else {
/*     */           
/* 311 */           player1 = player;
/*     */         } 
/*     */         
/* 314 */         if (singletonMap.containsKey(player1)) {
/*     */           
/* 316 */           ((AggroInfo)singletonMap.get(player1)).addDamage(ai.getDamage());
/*     */           
/*     */           continue;
/*     */         } 
/* 320 */         AggroInfo aggroInfo = new AggroInfo((AionObject)player1);
/* 321 */         aggroInfo.setDamage(ai.getDamage());
/* 322 */         singletonMap.put(player1, aggroInfo);
/*     */         continue;
/*     */       } 
/* 325 */       if (singletonMap.containsKey(player)) {
/*     */ 
/*     */         
/* 328 */         ((AggroInfo)singletonMap.get(player)).addDamage(ai.getDamage());
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 333 */       AggroInfo aggro = new AggroInfo((AionObject)player);
/* 334 */       aggro.addDamage(ai.getDamage());
/* 335 */       singletonMap.put(player, aggro);
/*     */     } 
/*     */ 
/*     */     
/* 339 */     return singletonMap.values();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AggroList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
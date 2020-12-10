/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.attack.AttackResult;
/*     */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*     */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*     */ import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.commons.lang.ArrayUtils;
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
/*     */ public class ObserveController
/*     */ {
/*  41 */   protected Queue<ActionObserver> moveObservers = new ConcurrentLinkedQueue<ActionObserver>();
/*  42 */   protected Queue<ActionObserver> attackObservers = new ConcurrentLinkedQueue<ActionObserver>();
/*  43 */   protected Queue<ActionObserver> attackedObservers = new ConcurrentLinkedQueue<ActionObserver>();
/*  44 */   protected Queue<ActionObserver> skilluseObservers = new ConcurrentLinkedQueue<ActionObserver>();
/*     */   
/*  46 */   protected ActionObserver[] observers = new ActionObserver[0];
/*  47 */   protected ActionObserver[] equipObservers = new ActionObserver[0];
/*  48 */   protected AttackCalcObserver[] attackCalcObservers = new AttackCalcObserver[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attach(ActionObserver observer) {
/*  56 */     switch (observer.getObserverType()) {
/*     */       
/*     */       case ATTACK:
/*  59 */         this.attackObservers.add(observer);
/*     */         break;
/*     */       case ATTACKED:
/*  62 */         this.attackedObservers.add(observer);
/*     */         break;
/*     */       case MOVE:
/*  65 */         this.moveObservers.add(observer);
/*     */         break;
/*     */       case SKILLUSE:
/*  68 */         this.skilluseObservers.add(observer);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyMoveObservers() {
/*  78 */     while (!this.moveObservers.isEmpty()) {
/*     */       
/*  80 */       ActionObserver observer = this.moveObservers.poll();
/*  81 */       observer.moved();
/*     */     } 
/*  83 */     synchronized (this.observers) {
/*     */       
/*  85 */       for (ActionObserver observer : this.observers)
/*     */       {
/*  87 */         observer.moved();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyAttackObservers(Creature creature) {
/*  97 */     while (!this.attackObservers.isEmpty()) {
/*     */       
/*  99 */       ActionObserver observer = this.attackObservers.poll();
/* 100 */       observer.attack(creature);
/*     */     } 
/* 102 */     synchronized (this.observers) {
/*     */       
/* 104 */       for (ActionObserver observer : this.observers)
/*     */       {
/* 106 */         observer.attack(creature);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyAttackedObservers(Creature creature) {
/* 116 */     while (!this.attackedObservers.isEmpty()) {
/*     */       
/* 118 */       ActionObserver observer = this.attackedObservers.poll();
/* 119 */       observer.attacked(creature);
/*     */     } 
/* 121 */     synchronized (this.observers) {
/*     */       
/* 123 */       for (ActionObserver observer : this.observers)
/*     */       {
/* 125 */         observer.attacked(creature);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySkilluseObservers(Skill skill) {
/* 135 */     while (!this.skilluseObservers.isEmpty()) {
/*     */       
/* 137 */       ActionObserver observer = this.skilluseObservers.poll();
/* 138 */       observer.skilluse(skill);
/*     */     } 
/* 140 */     synchronized (this.observers) {
/*     */       
/* 142 */       for (ActionObserver observer : this.observers)
/*     */       {
/* 144 */         observer.skilluse(skill);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyItemEquip(Item item, Player owner) {
/* 156 */     synchronized (this.equipObservers) {
/*     */       
/* 158 */       for (ActionObserver observer : this.equipObservers)
/*     */       {
/* 160 */         observer.equip(item, owner);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyItemUnEquip(Item item, Player owner) {
/* 172 */     synchronized (this.equipObservers) {
/*     */       
/* 174 */       for (ActionObserver observer : this.equipObservers)
/*     */       {
/* 176 */         observer.unequip(item, owner);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObserver(ActionObserver observer) {
/* 187 */     synchronized (this.observers) {
/*     */       
/* 189 */       this.observers = (ActionObserver[])ArrayUtils.add((Object[])this.observers, observer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEquipObserver(ActionObserver observer) {
/* 199 */     synchronized (this.equipObservers) {
/*     */       
/* 201 */       this.equipObservers = (ActionObserver[])ArrayUtils.add((Object[])this.equipObservers, observer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttackCalcObserver(AttackCalcObserver observer) {
/* 211 */     synchronized (this.attackCalcObservers) {
/*     */       
/* 213 */       this.attackCalcObservers = (AttackCalcObserver[])ArrayUtils.add((Object[])this.attackCalcObservers, observer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObserver(ActionObserver observer) {
/* 223 */     synchronized (this.observers) {
/*     */       
/* 225 */       this.observers = (ActionObserver[])ArrayUtils.removeElement((Object[])this.observers, observer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEquipObserver(ActionObserver observer) {
/* 235 */     synchronized (this.equipObservers) {
/*     */       
/* 237 */       this.equipObservers = (ActionObserver[])ArrayUtils.removeElement((Object[])this.equipObservers, observer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAttackCalcObserver(AttackCalcObserver observer) {
/* 247 */     synchronized (this.attackCalcObservers) {
/*     */       
/* 249 */       this.attackCalcObservers = (AttackCalcObserver[])ArrayUtils.removeElement((Object[])this.attackCalcObservers, observer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkAttackStatus(AttackStatus status) {
/* 260 */     synchronized (this.attackCalcObservers) {
/*     */       
/* 262 */       for (AttackCalcObserver observer : this.attackCalcObservers) {
/*     */         
/* 264 */         if (observer.checkStatus(status))
/* 265 */           return true; 
/*     */       } 
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkAttackerStatus(AttackStatus status) {
/* 278 */     synchronized (this.attackCalcObservers) {
/*     */       
/* 280 */       for (AttackCalcObserver observer : this.attackCalcObservers) {
/*     */         
/* 282 */         if (observer.checkAttackerStatus(status))
/* 283 */           return true; 
/*     */       } 
/*     */     } 
/* 286 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkShieldStatus(List<AttackResult> attackList) {
/* 294 */     synchronized (this.attackCalcObservers) {
/*     */       
/* 296 */       for (AttackCalcObserver observer : this.attackCalcObservers)
/*     */       {
/* 298 */         observer.checkShield(attackList);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\ObserveController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.lang.ArrayUtils;
























public class ObserveController
{
  protected Queue<ActionObserver> moveObservers = new ConcurrentLinkedQueue<ActionObserver>();
  protected Queue<ActionObserver> attackObservers = new ConcurrentLinkedQueue<ActionObserver>();
  protected Queue<ActionObserver> attackedObservers = new ConcurrentLinkedQueue<ActionObserver>();
  protected Queue<ActionObserver> skilluseObservers = new ConcurrentLinkedQueue<ActionObserver>();
  
  protected ActionObserver[] observers = new ActionObserver[0];
  protected ActionObserver[] equipObservers = new ActionObserver[0];
  protected AttackCalcObserver[] attackCalcObservers = new AttackCalcObserver[0];





  
  public void attach(ActionObserver observer) {
    switch (observer.getObserverType()) {
      
      case ATTACK:
        this.attackObservers.add(observer);
        break;
      case ATTACKED:
        this.attackedObservers.add(observer);
        break;
      case MOVE:
        this.moveObservers.add(observer);
        break;
      case SKILLUSE:
        this.skilluseObservers.add(observer);
        break;
    } 
  }




  
  protected void notifyMoveObservers() {
    while (!this.moveObservers.isEmpty()) {
      
      ActionObserver observer = this.moveObservers.poll();
      observer.moved();
    } 
    synchronized (this.observers) {
      
      for (ActionObserver observer : this.observers)
      {
        observer.moved();
      }
    } 
  }




  
  public void notifyAttackObservers(Creature creature) {
    while (!this.attackObservers.isEmpty()) {
      
      ActionObserver observer = this.attackObservers.poll();
      observer.attack(creature);
    } 
    synchronized (this.observers) {
      
      for (ActionObserver observer : this.observers)
      {
        observer.attack(creature);
      }
    } 
  }




  
  protected void notifyAttackedObservers(Creature creature) {
    while (!this.attackedObservers.isEmpty()) {
      
      ActionObserver observer = this.attackedObservers.poll();
      observer.attacked(creature);
    } 
    synchronized (this.observers) {
      
      for (ActionObserver observer : this.observers)
      {
        observer.attacked(creature);
      }
    } 
  }




  
  public void notifySkilluseObservers(Skill skill) {
    while (!this.skilluseObservers.isEmpty()) {
      
      ActionObserver observer = this.skilluseObservers.poll();
      observer.skilluse(skill);
    } 
    synchronized (this.observers) {
      
      for (ActionObserver observer : this.observers)
      {
        observer.skilluse(skill);
      }
    } 
  }






  
  public void notifyItemEquip(Item item, Player owner) {
    synchronized (this.equipObservers) {
      
      for (ActionObserver observer : this.equipObservers)
      {
        observer.equip(item, owner);
      }
    } 
  }






  
  public void notifyItemUnEquip(Item item, Player owner) {
    synchronized (this.equipObservers) {
      
      for (ActionObserver observer : this.equipObservers)
      {
        observer.unequip(item, owner);
      }
    } 
  }





  
  public void addObserver(ActionObserver observer) {
    synchronized (this.observers) {
      
      this.observers = (ActionObserver[])ArrayUtils.add((Object[])this.observers, observer);
    } 
  }





  
  public void addEquipObserver(ActionObserver observer) {
    synchronized (this.equipObservers) {
      
      this.equipObservers = (ActionObserver[])ArrayUtils.add((Object[])this.equipObservers, observer);
    } 
  }





  
  public void addAttackCalcObserver(AttackCalcObserver observer) {
    synchronized (this.attackCalcObservers) {
      
      this.attackCalcObservers = (AttackCalcObserver[])ArrayUtils.add((Object[])this.attackCalcObservers, observer);
    } 
  }





  
  public void removeObserver(ActionObserver observer) {
    synchronized (this.observers) {
      
      this.observers = (ActionObserver[])ArrayUtils.removeElement((Object[])this.observers, observer);
    } 
  }





  
  public void removeEquipObserver(ActionObserver observer) {
    synchronized (this.equipObservers) {
      
      this.equipObservers = (ActionObserver[])ArrayUtils.removeElement((Object[])this.equipObservers, observer);
    } 
  }





  
  public void removeAttackCalcObserver(AttackCalcObserver observer) {
    synchronized (this.attackCalcObservers) {
      
      this.attackCalcObservers = (AttackCalcObserver[])ArrayUtils.removeElement((Object[])this.attackCalcObservers, observer);
    } 
  }






  
  public boolean checkAttackStatus(AttackStatus status) {
    synchronized (this.attackCalcObservers) {
      
      for (AttackCalcObserver observer : this.attackCalcObservers) {
        
        if (observer.checkStatus(status))
          return true; 
      } 
    } 
    return false;
  }






  
  public boolean checkAttackerStatus(AttackStatus status) {
    synchronized (this.attackCalcObservers) {
      
      for (AttackCalcObserver observer : this.attackCalcObservers) {
        
        if (observer.checkAttackerStatus(status))
          return true; 
      } 
    } 
    return false;
  }




  
  public void checkShieldStatus(List<AttackResult> attackList) {
    synchronized (this.attackCalcObservers) {
      
      for (AttackCalcObserver observer : this.attackCalcObservers)
      {
        observer.checkShield(attackList);
      }
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\ObserveController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

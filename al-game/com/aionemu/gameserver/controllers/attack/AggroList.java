package com.aionemu.gameserver.controllers.attack;

import com.aionemu.commons.utils.SingletonMap;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.PlayerGroup;
import java.util.Collection;
import java.util.Map;




















public class AggroList
{
  private Creature owner;
  private final Map<Creature, AggroInfo> aggroList = (Map<Creature, AggroInfo>)(new SingletonMap()).setShared();

  
  public AggroList(Creature owner) {
    this.owner = owner;
  }







  
  public void addDamage(Creature creature, int damage) {
    if (creature == null || !this.owner.isEnemy((VisibleObject)creature)) {
      return;
    }
    
    AggroInfo ai = getAggroInfo(creature);
    ai.addDamage(damage);



    
    ai.addHate(damage);
    
    this.owner.getAi().handleEvent(Event.ATTACKED);
  }







  
  public void addHate(Creature creature, int hate) {
    if (creature == null || creature == this.owner || !this.owner.isEnemy((VisibleObject)creature)) {
      return;
    }

    
    AggroInfo ai = getAggroInfo(creature);
    ai.addHate(hate);
    
    this.owner.getAi().handleEvent(Event.ATTACKED);
  }




  
  public AionObject getMostDamage() {
    AionObject mostDamage = null;
    int maxDamage = 0;
    
    for (AggroInfo ai : getFinalDamageList(true)) {
      
      if (ai.getAttacker() == null) {
        continue;
      }
      if (ai.getDamage() > maxDamage) {
        
        mostDamage = ai.getAttacker();
        maxDamage = ai.getDamage();
      } 
    } 
    
    return mostDamage;
  }




  
  public Player getMostPlayerDamage() {
    if (this.aggroList.isEmpty()) {
      return null;
    }
    Player mostDamage = null;
    int maxDamage = 0;

    
    for (AggroInfo ai : getFinalDamageList(false)) {
      
      if (ai.getDamage() > maxDamage) {
        
        mostDamage = (Player)ai.getAttacker();
        maxDamage = ai.getDamage();
      } 
    } 
    
    return mostDamage;
  }





  
  public Creature getMostHated() {
    if (this.aggroList.isEmpty()) {
      return null;
    }
    Creature mostHated = null;
    int maxHate = 0;
    
    for (AggroInfo ai : this.aggroList.values()) {
      
      if (ai == null) {
        continue;
      }
      
      Creature attacker = (Creature)ai.getAttacker();
      
      if (attacker.getLifeStats().isAlreadyDead() || !this.owner.getKnownList().knowns(ai.getAttacker()))
      {
        ai.setHate(0);
      }
      if (ai.getHate() > maxHate) {
        
        mostHated = attacker;
        maxHate = ai.getHate();
      } 
    } 
    
    return mostHated;
  }






  
  public boolean isMostHated(Creature creature) {
    if (creature == null || creature.getLifeStats().isAlreadyDead()) {
      return false;
    }
    Creature mostHated = getMostHated();
    if (mostHated == null) {
      return false;
    }
    return mostHated.equals(creature);
  }





  
  public void notifyHate(Creature creature, int value) {
    if (isHating(creature)) {
      addHate(creature, value);
    }
  }




  
  public void stopHating(Creature creature) {
    AggroInfo aggroInfo = this.aggroList.get(creature);
    if (aggroInfo != null) {
      aggroInfo.setHate(0);
    }
  }





  
  public void remove(Creature creature) {
    this.aggroList.remove(creature);
  }




  
  public void clear() {
    this.aggroList.clear();
  }






  
  private AggroInfo getAggroInfo(Creature creature) {
    AggroInfo ai = this.aggroList.get(creature);
    if (ai == null) {
      
      ai = new AggroInfo((AionObject)creature);
      this.aggroList.put(creature, ai);
    } 
    return ai;
  }






  
  private boolean isHating(Creature creature) {
    return this.aggroList.containsKey(creature);
  }




  
  public Collection<AggroInfo> getList() {
    return this.aggroList.values();
  }




  
  public int getTotalDamage() {
    int totalDamage = 0;
    for (AggroInfo ai : this.aggroList.values())
    {
      totalDamage += ai.getDamage();
    }
    return totalDamage;
  }






  
  public Collection<AggroInfo> getFinalDamageList(boolean mergeGroupDamage) {
    SingletonMap<Player, AggroInfo> singletonMap = (new SingletonMap()).setShared();
    
    for (AggroInfo ai : this.aggroList.values()) {
      
      if (!(ai.getAttacker() instanceof Creature)) {
        continue;
      }

      
      Creature master = ((Creature)ai.getAttacker()).getMaster();
      
      if (!(master instanceof Player)) {
        continue;
      }
      Player player = (Player)master;


      
      if (!this.owner.getKnownList().knowns((AionObject)player)) {
        continue;
      }
      if (mergeGroupDamage) {
        Player player1;

        
        if (player.isInAlliance()) {
          
          PlayerAlliance playerAlliance = player.getPlayerAlliance();
        }
        else if (player.isInGroup()) {
          
          PlayerGroup playerGroup = player.getPlayerGroup();
        }
        else {
          
          player1 = player;
        } 
        
        if (singletonMap.containsKey(player1)) {
          
          ((AggroInfo)singletonMap.get(player1)).addDamage(ai.getDamage());
          
          continue;
        } 
        AggroInfo aggroInfo = new AggroInfo((AionObject)player1);
        aggroInfo.setDamage(ai.getDamage());
        singletonMap.put(player1, aggroInfo);
        continue;
      } 
      if (singletonMap.containsKey(player)) {

        
        ((AggroInfo)singletonMap.get(player)).addDamage(ai.getDamage());
        
        continue;
      } 
      
      AggroInfo aggro = new AggroInfo((AionObject)player);
      aggro.addDamage(ai.getDamage());
      singletonMap.put(player, aggro);
    } 

    
    return singletonMap.values();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AggroList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

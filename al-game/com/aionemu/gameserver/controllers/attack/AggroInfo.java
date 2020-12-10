package com.aionemu.gameserver.controllers.attack;

import com.aionemu.gameserver.model.gameobjects.AionObject;



























public class AggroInfo
{
  private AionObject attacker;
  private int hate;
  private int damage;
  
  AggroInfo(AionObject attacker) {
    this.attacker = attacker;
  }




  
  public AionObject getAttacker() {
    return this.attacker;
  }




  
  public void addDamage(int damage) {
    this.damage += damage;
    if (this.damage < 0) {
      this.damage = 0;
    }
  }



  
  public void addHate(int damage) {
    this.hate += damage;
    if (this.hate < 1) {
      this.hate = 1;
    }
  }



  
  public int getHate() {
    return this.hate;
  }




  
  public void setHate(int hate) {
    this.hate = hate;
  }




  
  public int getDamage() {
    return this.damage;
  }




  
  public void setDamage(int damage) {
    this.damage = damage;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AggroInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

package com.aionemu.gameserver.controllers.attack;
























public class AttackResult
{
  private int damage;
  private AttackStatus attackStatus;
  private int shieldType;
  
  public AttackResult(int damage, AttackStatus attackStatus) {
    this.damage = damage;
    this.attackStatus = attackStatus;
  }




  
  public int getDamage() {
    return this.damage;
  }




  
  public void setDamage(int damage) {
    this.damage = damage;
  }




  
  public AttackStatus getAttackStatus() {
    return this.attackStatus;
  }




  
  public int getShieldType() {
    return this.shieldType;
  }




  
  public void setShieldType(int shieldType) {
    this.shieldType = shieldType;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AttackResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

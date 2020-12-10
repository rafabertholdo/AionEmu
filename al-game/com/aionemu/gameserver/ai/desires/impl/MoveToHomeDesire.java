package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.desires.MoveDesire;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.utils.MathUtil;




















public class MoveToHomeDesire
  extends AbstractDesire
  implements MoveDesire
{
  private Npc owner;
  private float x;
  private float y;
  private float z;
  
  public MoveToHomeDesire(Npc owner, int desirePower) {
    super(desirePower);
    this.owner = owner;
    SpawnTemplate template = owner.getSpawn();
    this.x = template.getX();
    this.y = template.getY();
    this.z = template.getZ();
  }



  
  public boolean handleDesire(AI ai) {
    if (this.owner == null || this.owner.getLifeStats().isAlreadyDead()) {
      return false;
    }
    this.owner.getMoveController().setNewDirection(this.x, this.y, this.z);
    this.owner.getMoveController().setFollowTarget(false);
    this.owner.getMoveController().setDistance(0.0F);
    
    if (!this.owner.getMoveController().isScheduled()) {
      this.owner.getMoveController().schedule();
    }
    double dist = MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), this.x, this.y, this.z);
    if (dist < 2.0D) {
      
      ai.handleEvent(Event.BACK_HOME);
      return false;
    } 
    return true;
  }


  
  public int getExecutionInterval() {
    return 1;
  }


  
  public void onClear() {
    this.owner.getMoveController().stop();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\MoveToHomeDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

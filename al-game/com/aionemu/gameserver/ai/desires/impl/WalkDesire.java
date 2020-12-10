package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.desires.MoveDesire;
import com.aionemu.gameserver.configs.main.NpcMovementConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.walker.RouteData;
import com.aionemu.gameserver.model.templates.walker.RouteStep;
import com.aionemu.gameserver.model.templates.walker.WalkerTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;

















public class WalkDesire
  extends AbstractDesire
  implements MoveDesire
{
  private Npc owner;
  private RouteData route;
  private boolean isWalkingToNextPoint = false;
  private int targetPosition;
  private long nextMoveTime;
  private boolean isRandomWalk = false;
  private RouteStep randomPoint = null;
  private float walkArea = 10.0F;
  private float halfWalkArea = 5.0F;
  private float minRandomDistance = 2.0F;

  
  public WalkDesire(Npc npc, int power) {
    super(power);
    this.owner = npc;
    
    WalkerTemplate template = DataManager.WALKER_DATA.getWalkerTemplate(this.owner.getSpawn().getWalkerId());
    this.isRandomWalk = this.owner.getSpawn().hasRandomWalk();
    if (template != null) {
      
      this.route = template.getRouteData();
      
      this.owner.getMoveController().setSpeed(this.owner.getObjectTemplate().getStatsTemplate().getWalkSpeed());
      this.owner.getMoveController().setWalking(true);
      PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_EMOTION((Creature)this.owner, EmotionType.WALK));
    }
    else if (this.isRandomWalk && NpcMovementConfig.ACTIVE_NPC_MOVEMENT) {
      
      this.walkArea = Math.max(5, this.owner.getSpawn().getRandomWalkNr());
      this.halfWalkArea = this.walkArea / 2.0F;
      this.minRandomDistance = Math.min(this.walkArea / 5.0F, 2.0F);
      
      this.route = null;
      this.randomPoint = new RouteStep(this.owner.getX(), this.owner.getY(), this.owner.getZ());
      
      this.owner.getMoveController().setSpeed(this.owner.getObjectTemplate().getStatsTemplate().getWalkSpeed());
      this.owner.getMoveController().setWalking(true);
      this.owner.getMoveController().setDistance(this.minRandomDistance);
      PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_EMOTION((Creature)this.owner, EmotionType.WALK));
    } 
  }



  
  public boolean handleDesire(AI ai) {
    if (this.owner == null) {
      return false;
    }
    if (this.route == null && !this.isRandomWalk) {
      return false;
    }
    if (isWalkingToNextPoint()) {
      checkArrivedToPoint();
    }
    walkToLocation();
    return true;
  }




  
  private void checkArrivedToPoint() {
    RouteStep step = this.randomPoint;
    if (this.route != null)
    {
      step = this.route.getRouteSteps().get(this.targetPosition);
    }
    
    float x = step.getX();
    float y = step.getY();
    float z = step.getZ();
    
    double dist = MathUtil.getDistance((VisibleObject)this.owner, x, y, z);
    float minDist = (this.route == null) ? this.minRandomDistance : 2.0F;
    if (dist <= minDist) {
      
      setWalkingToNextPoint(false);
      getNextTime();
    } 
  }




  
  private void walkToLocation() {
    if (!isWalkingToNextPoint() && this.nextMoveTime <= System.currentTimeMillis()) {
      
      setNextPosition();
      setWalkingToNextPoint(true);
      
      RouteStep step = this.randomPoint;
      if (this.route != null)
      {
        step = this.route.getRouteSteps().get(this.targetPosition);
      }
      
      float x = step.getX();
      float y = step.getY();
      float z = step.getZ();
      this.owner.getMoveController().setNewDirection(x, y, z);
      if (!this.owner.getMoveController().isScheduled()) {
        this.owner.getMoveController().schedule();
      }
    } 
  }
  
  private boolean isWalkingToNextPoint() {
    return this.isWalkingToNextPoint;
  }

  
  private void setWalkingToNextPoint(boolean value) {
    this.isWalkingToNextPoint = value;
  }

  
  private void setNextPosition() {
    if (this.route == null) {
      
      getNextRandomPoint();
      
      return;
    } 
    if (this.isRandomWalk) {
      
      this.targetPosition = Rnd.get(0, this.route.getRouteSteps().size() - 1);

    
    }
    else if (this.targetPosition < this.route.getRouteSteps().size() - 1) {
      this.targetPosition++;
    } else {
      this.targetPosition = 0;
    } 
  }

  
  private void getNextTime() {
    int nextDelay;
    if (this.route == null) {
      
      nextDelay = Rnd.get(NpcMovementConfig.MINIMIMUM_DELAY, NpcMovementConfig.MAXIMUM_DELAY);
    }
    else {
      
      nextDelay = this.isRandomWalk ? Rnd.get(5, 60) : ((RouteStep)this.route.getRouteSteps().get(this.targetPosition)).getRestTime();
    } 
    this.nextMoveTime = System.currentTimeMillis() + (nextDelay * 1000);
  }

  
  private void getNextRandomPoint() {
    float x = this.owner.getSpawn().getX() - this.halfWalkArea + Rnd.get() * this.walkArea;
    float y = this.owner.getSpawn().getY() - this.halfWalkArea + Rnd.get() * this.walkArea;
    this.randomPoint = new RouteStep(x, y, this.owner.getSpawn().getZ());
  }


  
  public int getExecutionInterval() {
    return 1;
  }


  
  public void onClear() {
    this.owner.getMoveController().stop();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\WalkDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

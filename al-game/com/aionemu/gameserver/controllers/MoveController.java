package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.controllers.movement.MovementType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

public class MoveController {
  private static final Logger log = Logger.getLogger(MoveController.class);

  private Future<?> moveTask;

  private Creature owner;

  private boolean directionChanged = true;

  private float targetX;
  private float targetY;
  private float targetZ;
  private boolean isFollowTarget;
  private boolean isStopped = false;
  private int moveCounter;
  private float speed = 0.0F;
  private float distance = 2.0F;

  private boolean walking;

  public MoveController(Creature owner) {
    this.owner = owner;
  }

  public void setFollowTarget(boolean isFollowTarget) {
    this.isFollowTarget = isFollowTarget;
  }

  public void setDirectionChanged(boolean directionChanged) {
    this.directionChanged = directionChanged;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public float getSpeed() {
    return this.speed;
  }

  public void setDistance(float distance) {
    this.distance = distance;
  }

  public boolean isWalking() {
    return this.walking;
  }

  public void setWalking(boolean walking) {
    this.walking = walking;
  }

  public void setNewDirection(float x, float y, float z) {
    if (x != this.targetX || y != this.targetY || z != this.targetZ)
      this.directionChanged = true;
    this.targetX = x;
    this.targetY = y;
    this.targetZ = z;
  }

  public float getTargetX() {
    return this.targetX;
  }

  public float getTargetY() {
    return this.targetY;
  }

  public float getTargetZ() {
    return this.targetZ;
  }

  public boolean isScheduled() {
    return (this.moveTask != null && !this.moveTask.isCancelled());
  }

  public void schedule() {
    if (isScheduled()) {
      return;
    }
    this.moveTask = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new Runnable() {

      public void run() {
        MoveController.this.move();
      }
    }, 0L, 500L);
  }

  private void move() {
    if (this.speed == 0.0F) {
      this.speed = (this.owner.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000);
    }

    if (!this.owner.canPerformMove() || this.owner.isCasting() || this.speed <= 0.0F) {

      if (!this.isStopped) {

        this.isStopped = true;
        this.owner.getController().stopMoving();
      }

      return;
    }
    VisibleObject target = this.owner.getTarget();

    if (this.isFollowTarget && target != null) {
      setNewDirection(target.getX(), target.getY(), target.getZ());
    }

    float ownerX = this.owner.getX();
    float ownerY = this.owner.getY();
    float ownerZ = this.owner.getZ();
    byte heading = this.owner.getHeading();

    float distX = this.targetX - ownerX;
    float distY = this.targetY - ownerY;
    float distZ = this.targetZ - ownerZ;

    double dist = MathUtil.getDistance(ownerX, ownerY, ownerZ, this.targetX, this.targetY, this.targetZ);

    if (dist > this.distance) {

      this.isStopped = false;

      float x2 = (float) (distX / dist);
      float y2 = (float) (distY / dist);
      float z2 = (float) (distZ / dist);

      dist -= (this.distance - 0.02F);

      if (this.directionChanged) {

        heading = (byte) (int) (Math.toDegrees(Math.atan2(distY, distX)) / 3.0D);
        PacketSendUtility.broadcastPacket((VisibleObject) this.owner,
            (AionServerPacket) new SM_MOVE(this.owner.getObjectId(), ownerX, ownerY, ownerZ,
                (float) (x2 * dist) + ownerX, (float) (y2 * dist) + ownerY, (float) (z2 * dist) + ownerZ, heading,
                MovementType.UNKNOWN_NPC_MOVEMENT));

        this.directionChanged = false;
      }
      double maxDist = this.speed * 0.5D;
      if (dist > maxDist) {
        dist = maxDist;
      }
      x2 = (float) (x2 * dist) + ownerX;
      y2 = (float) (y2 * dist) + ownerY;
      z2 = (float) (z2 * dist) + ownerZ;

      this.moveCounter++;
      World.getInstance().updatePosition((VisibleObject) this.owner, x2, y2, z2, heading, (this.moveCounter % 5 == 0));

    } else if (!this.isStopped) {

      this.isStopped = true;
      this.owner.getController().stopMoving();
    }
  }

  public double getDistanceToTarget() {
    if (this.isFollowTarget) {

      VisibleObject target = this.owner.getTarget();
      if (target != null) {
        return MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), target.getX(),
            target.getY(), target.getZ());
      }
    }

    return MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), this.targetX, this.targetY,
        this.targetZ);
  }

  public void stop() {
    this.walking = false;

    if (this.moveTask != null) {

      if (!this.moveTask.isCancelled())
        this.moveTask.cancel(true);
      this.moveTask = null;
    }
  }
}

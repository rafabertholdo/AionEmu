package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.world.World;

public abstract class VisibleObjectController<T extends VisibleObject> {
  private T owner;

  public void setOwner(T owner) {
    this.owner = owner;
  }

  public T getOwner() {
    return this.owner;
  }

  public void see(VisibleObject object) {
  }

  public void notSee(VisibleObject object, boolean isOutOfRange) {
  }

  public void delete() {
    if (getOwner().isSpawned()) {
      World.getInstance().despawn((VisibleObject) getOwner());
    }

    World.getInstance().removeObject((AionObject) getOwner());
  }

  public void onRespawn() {
  }
}

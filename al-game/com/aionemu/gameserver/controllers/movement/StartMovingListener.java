package com.aionemu.gameserver.controllers.movement;

public class StartMovingListener extends ActionObserver {
  private boolean effectorMoved = false;

  public StartMovingListener() {
    super(ActionObserver.ObserverType.MOVE);
  }

  public boolean isEffectorMoved() {
    return this.effectorMoved;
  }

  public void moved() {
    this.effectorMoved = true;
  }
}

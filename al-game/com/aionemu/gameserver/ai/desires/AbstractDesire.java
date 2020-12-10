package com.aionemu.gameserver.ai.desires;

import com.aionemu.gameserver.ai.AI;

public abstract class AbstractDesire implements Desire {
  protected int executionCounter;
  protected int desirePower;

  protected AbstractDesire(int desirePower) {
    this.desirePower = desirePower;
  }

  public int compareTo(Desire o) {
    return o.getDesirePower() - getDesirePower();
  }

  public int getDesirePower() {
    return this.desirePower;
  }

  public synchronized void increaseDesirePower(int desirePower) {
    this.desirePower += desirePower;
  }

  public boolean handleDesire(AI<?> ai) {
    return false;
  }

  public boolean isReadyToRun() {
    boolean isReady = (this.executionCounter % getExecutionInterval() == 0);
    this.executionCounter++;
    return isReady;
  }

  public synchronized void reduceDesirePower(int desirePower) {
    this.desirePower -= desirePower;
  }

  public abstract int getExecutionInterval();
}

package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.DesireIteratorHandler;
import java.util.Iterator;

public class GeneralDesireIteratorHandler implements DesireIteratorHandler {
  private AI ai;

  public GeneralDesireIteratorHandler(AI ai) {
    this.ai = ai;
  }

  public void next(Desire desire, Iterator<Desire> iterator) {
    boolean isOk = desire.handleDesire(this.ai);
    if (!isOk) {

      desire.onClear();
      iterator.remove();
    }
  }
}

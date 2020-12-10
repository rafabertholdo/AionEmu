package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.DesireIteratorFilter;

public class CounterBasedDesireFilter implements DesireIteratorFilter {
  public boolean isOk(Desire desire) {
    return desire.isReadyToRun();
  }
}

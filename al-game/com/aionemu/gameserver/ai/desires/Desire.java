package com.aionemu.gameserver.ai.desires;

import com.aionemu.gameserver.ai.AI;

public interface Desire extends Comparable<Desire> {
  boolean handleDesire(AI<?> paramAI);

  int hashCode();

  boolean equals(Object paramObject);

  int getDesirePower();

  void increaseDesirePower(int paramInt);

  void reduceDesirePower(int paramInt);

  boolean isReadyToRun();

  void onClear();
}

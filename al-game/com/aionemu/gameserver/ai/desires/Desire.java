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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\Desire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
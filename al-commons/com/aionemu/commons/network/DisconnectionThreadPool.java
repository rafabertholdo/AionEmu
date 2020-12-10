package com.aionemu.commons.network;

public interface DisconnectionThreadPool {
  void scheduleDisconnection(DisconnectionTask paramDisconnectionTask, long paramLong);
  
  void waitForDisconnectionTasks();
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\DisconnectionThreadPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
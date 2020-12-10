package com.aionemu.commons.network;

public interface DisconnectionThreadPool {
  void scheduleDisconnection(DisconnectionTask paramDisconnectionTask, long paramLong);

  void waitForDisconnectionTasks();
}

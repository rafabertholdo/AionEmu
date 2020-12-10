package com.aionemu.gameserver.world.exceptions;

public class AlreadySpawnedException extends RuntimeException {
  public AlreadySpawnedException() {
  }

  public AlreadySpawnedException(String s) {
    super(s);
  }

  public AlreadySpawnedException(String message, Throwable cause) {
    super(message, cause);
  }

  public AlreadySpawnedException(Throwable cause) {
    super(cause);
  }
}

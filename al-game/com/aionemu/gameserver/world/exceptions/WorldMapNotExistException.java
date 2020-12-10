package com.aionemu.gameserver.world.exceptions;

public class WorldMapNotExistException extends RuntimeException {
  public WorldMapNotExistException() {
  }

  public WorldMapNotExistException(String s) {
    super(s);
  }

  public WorldMapNotExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public WorldMapNotExistException(Throwable cause) {
    super(cause);
  }
}

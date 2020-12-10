package com.aionemu.gameserver.world.exceptions;

public class NotSetPositionException extends RuntimeException {
  public NotSetPositionException() {
  }

  public NotSetPositionException(String s) {
    super(s);
  }

  public NotSetPositionException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotSetPositionException(Throwable cause) {
    super(cause);
  }
}

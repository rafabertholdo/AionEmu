package com.aionemu.gameserver;

public class GameServerError extends Error {
  private static final long serialVersionUID = -7445873741878754767L;

  public GameServerError() {
  }

  public GameServerError(Throwable cause) {
    super(cause);
  }

  public GameServerError(String message) {
    super(message);
  }

  public GameServerError(String message, Throwable cause) {
    super(message, cause);
  }
}

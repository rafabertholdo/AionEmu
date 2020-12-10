package com.aionemu.gameserver.utils.idfactory;

public class IDFactoryError extends Error {
  public IDFactoryError() {
  }

  public IDFactoryError(String message) {
    super(message);
  }

  public IDFactoryError(String message, Throwable cause) {
    super(message, cause);
  }

  public IDFactoryError(Throwable cause) {
    super(cause);
  }
}

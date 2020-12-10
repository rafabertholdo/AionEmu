package com.aionemu.gameserver.network;

public class KeyAlreadySetException extends RuntimeException {
  public KeyAlreadySetException() {
  }

  public KeyAlreadySetException(String s) {
    super(s);
  }

  public KeyAlreadySetException(String message, Throwable cause) {
    super(message, cause);
  }

  public KeyAlreadySetException(Throwable cause) {
    super(cause);
  }
}

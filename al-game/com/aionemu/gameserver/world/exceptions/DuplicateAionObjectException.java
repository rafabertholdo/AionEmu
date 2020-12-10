package com.aionemu.gameserver.world.exceptions;

public class DuplicateAionObjectException extends RuntimeException {
  public DuplicateAionObjectException() {
  }

  public DuplicateAionObjectException(String s) {
    super(s);
  }

  public DuplicateAionObjectException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateAionObjectException(Throwable cause) {
    super(cause);
  }
}

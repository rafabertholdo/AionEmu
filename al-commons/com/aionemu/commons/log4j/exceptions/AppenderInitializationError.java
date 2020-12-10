package com.aionemu.commons.log4j.exceptions;

public class AppenderInitializationError extends Error {
  private static final long serialVersionUID = -6090251689433934051L;

  public AppenderInitializationError() {
  }

  public AppenderInitializationError(String message) {
    super(message);
  }

  public AppenderInitializationError(String message, Throwable cause) {
    super(message, cause);
  }

  public AppenderInitializationError(Throwable cause) {
    super(cause);
  }
}

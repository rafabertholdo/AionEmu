package com.aionemu.commons.log4j.exceptions;

public class Log4jInitializationError extends Error {
  private static final long serialVersionUID = -628697707807736993L;

  public Log4jInitializationError() {
  }

  public Log4jInitializationError(String message) {
    super(message);
  }

  public Log4jInitializationError(String message, Throwable cause) {
    super(message, cause);
  }

  public Log4jInitializationError(Throwable cause) {
    super(cause);
  }
}

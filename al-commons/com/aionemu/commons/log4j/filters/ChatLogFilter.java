package com.aionemu.commons.log4j.filters;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public final class ChatLogFilter extends Filter {
  public final int decide(LoggingEvent loggingEvent) {
    Object message = loggingEvent.getMessage();

    if (((String) message).startsWith("[MESSAGE]")) {
      return 1;
    }

    return -1;
  }
}

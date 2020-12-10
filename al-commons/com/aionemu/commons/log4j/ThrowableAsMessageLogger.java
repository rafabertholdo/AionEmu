package com.aionemu.commons.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class ThrowableAsMessageLogger extends Logger {
  protected ThrowableAsMessageLogger(String name) {
    super(name);
  }

  protected void forcedLog(String fqcn, Priority level, Object message, Throwable t) {
    if (message instanceof Throwable && t == null) {

      t = (Throwable) message;
      message = t.getLocalizedMessage();
    }

    super.forcedLog(fqcn, level, message, t);
  }
}

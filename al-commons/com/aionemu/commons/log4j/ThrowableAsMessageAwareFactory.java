package com.aionemu.commons.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class ThrowableAsMessageAwareFactory implements LoggerFactory {
  public Logger makeNewLoggerInstance(String name) {
    return new ThrowableAsMessageLogger(name);
  }
}

package com.aionemu.commons.log4j;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.LogLog;

public class JuliToLog4JHandler extends Handler {
  public void publish(LogRecord record) {
    String loggerName = record.getLoggerName();
    if (loggerName == null) {
      loggerName = "";
    }

    Logger log = Logger.getLogger(loggerName);
    Level level = toLog4jLevel(record.getLevel());

    log.log(Logger.class.getName(), (Priority) level, record.getMessage(), record.getThrown());
  }

  protected Level toLog4jLevel(Level juliLevel) {
    if (Level.OFF.equals(juliLevel)) {
      return Level.OFF;
    }
    if (Level.SEVERE.equals(juliLevel)) {
      return Level.ERROR;
    }
    if (Level.WARNING.equals(juliLevel)) {
      return Level.WARN;
    }
    if (Level.INFO.equals(juliLevel)) {
      return Level.INFO;
    }
    if (Level.CONFIG.equals(juliLevel) || Level.FINE.equals(juliLevel)) {
      return Level.DEBUG;
    }
    if (Level.FINER.equals(juliLevel) || Level.FINEST.equals(juliLevel)) {
      return Level.TRACE;
    }
    if (Level.ALL.equals(juliLevel)) {
      return Level.ALL;
    }

    LogLog.warn("Warning: usage of custom JULI level: " + juliLevel.getName(), new Exception());

    return new CustomLog4jLevel(juliLevel.intValue(), juliLevel.getName(), juliLevel.intValue());
  }

  public void flush() {
  }

  public void close() throws SecurityException {
  }

  protected static class CustomLog4jLevel extends Level {
    private static final long serialVersionUID = 4014557380173323844L;

    protected CustomLog4jLevel(int level, String levelStr, int syslogEquivalent) {
      super(level, levelStr, syslogEquivalent);
    }
  }
}

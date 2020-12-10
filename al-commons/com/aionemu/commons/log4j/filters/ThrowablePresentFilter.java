package com.aionemu.commons.log4j.filters;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

































public class ThrowablePresentFilter
  extends Filter
{
  public int decide(LoggingEvent loggingEvent) {
    Object message = loggingEvent.getMessage();
    
    if (message instanceof Throwable)
    {
      return 1;
    }
    
    ThrowableInformation information = loggingEvent.getThrowableInformation();

    
    if (information != null && information.getThrowable() != null)
    {
      return 1;
    }
    
    return -1;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\log4j\filters\ThrowablePresentFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

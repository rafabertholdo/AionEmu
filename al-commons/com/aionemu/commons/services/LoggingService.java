package com.aionemu.commons.services;

import com.aionemu.commons.log4j.JuliToLog4JHandler;
import com.aionemu.commons.log4j.ThrowableAsMessageAwareFactory;
import com.aionemu.commons.log4j.exceptions.Log4jInitializationError;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;
















































public class LoggingService
{
  public static final String LOGGER_FACTORY_CLASS_PROPERTY = "log4j.loggerfactory";
  public static final String LOGGER_CONFIG_FILE = "config/log4j.xml";
  private static boolean initialized;
  
  public static void init() throws Log4jInitializationError {
    File f = new File("config/log4j.xml");
    
    if (!f.exists())
    {
      throw new Log4jInitializationError("Missing file " + f.getPath());
    }

    
    try {
      init(f.toURI().toURL());
    }
    catch (MalformedURLException e) {
      
      throw new Log4jInitializationError("Can't initalize logging", e);
    } 
  }









  
  public static void init(URL url) throws Log4jInitializationError {
    synchronized (LoggingService.class) {
      
      if (initialized) {
        return;
      }


      
      initialized = true;
    } 


    
    try {
      DOMConfigurator.configure(url);
    }
    catch (Exception e) {
      
      throw new Log4jInitializationError("Can't initialize logging", e);
    } 
    
    overrideDefaultLoggerFactory();

    
    Logger logger = LogManager.getLogManager().getLogger("");
    for (Handler h : logger.getHandlers())
    {
      logger.removeHandler(h);
    }
    
    logger.addHandler((Handler)new JuliToLog4JHandler());
  }












  
  private static void overrideDefaultLoggerFactory() {
    Hierarchy lr = (Hierarchy)LogManager.getLoggerRepository();
    
    try {
      Field field = lr.getClass().getDeclaredField("defaultFactory");
      field.setAccessible(true);
      String cn = System.getProperty("log4j.loggerfactory", ThrowableAsMessageAwareFactory.class.getName());
      
      Class<?> c = Class.forName(cn);
      field.set(lr, c.newInstance());
      field.setAccessible(false);
    }
    catch (NoSuchFieldException e) {

      
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {

      
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      
      throw new Log4jInitializationError("Can't found log4j logger factory class", e);
    }
    catch (InstantiationException e) {
      
      throw new Log4jInitializationError("Can't instantiate log4j logger factory", e);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\services\LoggingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

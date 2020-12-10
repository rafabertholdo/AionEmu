package com.aionemu.commons.database.dao;

import com.aionemu.commons.database.DatabaseConfig;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;






























public class DAOManager
{
  private static final Logger log = Logger.getLogger(DAOManager.class);



  
  private static final Map<String, DAO> daoMap = new HashMap<String, DAO>();




  
  private static ScriptManager scriptManager;




  
  public static void init() {
    try {
      scriptManager = new ScriptManager();
      scriptManager.setGlobalClassListener(new DAOLoader());
      scriptManager.load(DatabaseConfig.DATABASE_SCRIPTCONTEXT_DESCRIPTOR);
    }
    catch (Exception e) {
      
      throw new Error("Can't load database script context: " + DatabaseConfig.DATABASE_SCRIPTCONTEXT_DESCRIPTOR, e);
    } 

    
    log.info("Loaded " + daoMap.size() + " DAO implementations.");
  }




  
  public static void shutdown() {
    scriptManager.shutdown();
    daoMap.clear();
    scriptManager = null;
  }


















  
  public static <T extends DAO> T getDAO(Class<T> clazz) throws DAONotFoundException {
    DAO result = daoMap.get(clazz.getName());
    
    if (result == null) {
      
      String s = "DAO for class " + clazz.getName() + " not implemented";
      log.error(s);
      throw new DAONotFoundException(s);
    } 
    
    return (T)result;
  }



















  
  public static void registerDAO(Class<? extends DAO> daoClass) throws DAOAlreadyRegisteredException, IllegalAccessException, InstantiationException {
    DAO dao = daoClass.newInstance();
    
    if (!dao.supports(DatabaseFactory.getDatabaseName(), DatabaseFactory.getDatabaseMajorVersion(), DatabaseFactory.getDatabaseMinorVersion())) {
      return;
    }

    
    synchronized (DAOManager.class) {
      
      DAO oldDao = daoMap.get(dao.getClassName());
      if (oldDao != null) {
        
        StringBuilder sb = new StringBuilder();
        sb.append("DAO with className ").append(dao.getClassName()).append(" is used by ");
        sb.append(oldDao.getClass().getName()).append(". Can't override with ");
        sb.append(daoClass.getName()).append(".");
        String s = sb.toString();
        log.error(s);
        throw new DAOAlreadyRegisteredException(s);
      } 

      
      daoMap.put(dao.getClassName(), dao);
    } 

    
    if (log.isDebugEnabled()) {
      log.debug("DAO " + dao.getClassName() + " was successfuly registered.");
    }
  }






  
  public static void unregisterDAO(Class<? extends DAO> daoClass) {
    synchronized (DAOManager.class) {
      
      for (DAO dao : daoMap.values()) {
        
        if (dao.getClass() == daoClass) {
          
          daoMap.remove(dao.getClassName());
          
          if (log.isDebugEnabled())
            log.debug("DAO " + dao.getClassName() + " was successfuly unregistered."); 
          break;
        } 
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\dao\DAOManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

package com.aionemu.gameserver.questEngine.handlers;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
import com.aionemu.commons.utils.ClassUtils;
import com.aionemu.gameserver.questEngine.QuestEngine;
import java.lang.reflect.Modifier;
import org.apache.log4j.Logger;





















public class QuestHandlerLoader
  extends DefaultClassListener
  implements ClassListener
{
  private static final Logger logger = Logger.getLogger(QuestHandlerLoader.class);








  
  public void postLoad(Class<?>[] classes) {
    for (Class<?> c : classes) {
      
      if (logger.isDebugEnabled()) {
        logger.debug("Load class " + c.getName());
      }
      if (isValidClass(c))
      {
        
        if (ClassUtils.isSubclass(c, QuestHandler.class)) {
          
          try {
            
            Class<? extends QuestHandler> tmp = (Class)c;
            if (tmp != null) {
              QuestEngine.getInstance().addQuestHandler(tmp.newInstance());
            }
          } catch (InstantiationException e) {

            
            e.printStackTrace();
          }
          catch (IllegalAccessException e) {

            
            e.printStackTrace();
          } 
        }
      }
    } 
    
    super.postLoad(classes);
  }



  
  public void preUnload(Class<?>[] classes) {
    if (logger.isDebugEnabled()) {
      for (Class<?> c : classes) {
        logger.debug("Unload class " + c.getName());
      }
    }
    super.preUnload(classes);
    
    QuestEngine.getInstance().clear();
  }

  
  public boolean isValidClass(Class<?> clazz) {
    int modifiers = clazz.getModifiers();
    
    if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
      return false;
    }
    if (!Modifier.isPublic(modifiers)) {
      return false;
    }
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\QuestHandlerLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

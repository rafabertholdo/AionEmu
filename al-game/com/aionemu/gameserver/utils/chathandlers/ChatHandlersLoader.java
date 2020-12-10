package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
import com.aionemu.commons.utils.ClassUtils;
import java.lang.reflect.Modifier;
import org.apache.log4j.Logger;








class ChatHandlersLoader
  extends DefaultClassListener
  implements ClassListener
{
  private static final Logger logger = Logger.getLogger(ChatHandlersLoader.class);
  
  private final AdminCommandChatHandler adminCCH;

  
  public ChatHandlersLoader(AdminCommandChatHandler handler) {
    this.adminCCH = handler;
  }



  
  public void postLoad(Class<?>[] classes) {
    for (Class<?> c : classes) {
      
      if (logger.isDebugEnabled()) {
        logger.debug("Load class " + c.getName());
      }
      if (isValidClass(c))
      {
        
        if (ClassUtils.isSubclass(c, AdminCommand.class)) {
          
          Class<? extends AdminCommand> tmp = (Class)c;
          if (tmp != null) {
            
            try {
              this.adminCCH.registerAdminCommand(tmp.newInstance());
            }
            catch (InstantiationException e) {

              
              e.printStackTrace();
            }
            catch (IllegalAccessException e) {

              
              e.printStackTrace();
            } 
          }
        } 
      }
    } 
    super.postLoad(classes);
    
    logger.info("Loaded " + this.adminCCH.getSize() + " admin command handlers.");
  }


  
  public void preUnload(Class<?>[] classes) {
    if (logger.isDebugEnabled()) {
      for (Class<?> c : classes) {
        logger.debug("Unload class " + c.getName());
      }
    }
    super.preUnload(classes);
    
    this.adminCCH.clearHandlers();
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\ChatHandlersLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

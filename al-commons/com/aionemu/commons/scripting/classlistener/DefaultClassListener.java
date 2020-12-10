package com.aionemu.commons.scripting.classlistener;

import com.aionemu.commons.scripting.metadata.OnClassLoad;
import com.aionemu.commons.scripting.metadata.OnClassUnload;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.log4j.Logger;
























public class DefaultClassListener
  implements ClassListener
{
  private static final Logger log = Logger.getLogger(DefaultClassListener.class);


  
  public void postLoad(Class<?>[] classes) {
    for (Class<?> c : classes)
    {
      doMethodInvoke(c.getDeclaredMethods(), (Class)OnClassLoad.class);
    }
  }


  
  public void preUnload(Class<?>[] classes) {
    for (Class<?> c : classes)
    {
      doMethodInvoke(c.getDeclaredMethods(), (Class)OnClassUnload.class);
    }
  }









  
  protected final void doMethodInvoke(Method[] methods, Class<? extends Annotation> annotationClass) {
    for (Method m : methods) {
      
      if (Modifier.isStatic(m.getModifiers())) {

        
        boolean accessible = m.isAccessible();
        m.setAccessible(true);
        
        if (m.getAnnotation(annotationClass) != null) {
          
          try {
            
            m.invoke((Object)null, new Object[0]);
          }
          catch (IllegalAccessException e) {
            
            log.error("Can't access method " + m.getName() + " of class " + m.getDeclaringClass().getName(), e);
          }
          catch (InvocationTargetException e) {
            
            log.error("Can't invoke method " + m.getName() + " of class " + m.getDeclaringClass().getName(), e);
          } 
        }
        
        m.setAccessible(accessible);
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\classlistener\DefaultClassListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

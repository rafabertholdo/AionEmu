package com.aionemu.commons.scripting.scriptmanager;

import com.aionemu.commons.scripting.ScriptContext;
import com.aionemu.commons.scripting.ScriptContextFactory;
import com.aionemu.commons.scripting.classlistener.ClassListener;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;











































public class ScriptManager
{
  private static final Logger log = Logger.getLogger(ScriptManager.class);



  
  private Set<ScriptContext> contexts = new HashSet<ScriptContext>();






  
  private ClassListener globalClassListener;







  
  public synchronized void load(File scriptDescriptor) throws Exception {
    JAXBContext c = JAXBContext.newInstance(new Class[] { ScriptInfo.class, ScriptList.class });
    Unmarshaller u = c.createUnmarshaller();
    
    ScriptList list = (ScriptList)u.unmarshal(scriptDescriptor);
    
    for (ScriptInfo si : list.getScriptInfos()) {
      
      ScriptContext context = createContext(si, null);
      if (context != null) {
        
        this.contexts.add(context);
        context.init();
      } 
    } 
  }












  
  public ScriptContext createContext(ScriptInfo si, ScriptContext parent) throws Exception {
    ScriptContext context = ScriptContextFactory.getScriptContext(si.getRoot(), parent);
    context.setLibraries(si.getLibraries());
    context.setCompilerClassName(si.getCompilerClass());
    
    if (parent == null && this.contexts.contains(context)) {
      
      log.warn("Double root script context definition: " + si.getRoot().getAbsolutePath());
      return null;
    } 
    
    if (si.getScriptInfos() != null && !si.getScriptInfos().isEmpty())
    {
      for (ScriptInfo child : si.getScriptInfos())
      {
        createContext(child, context);
      }
    }
    
    if (parent == null && this.globalClassListener != null) {
      context.setClassListener(this.globalClassListener);
    }
    return context;
  }




  
  public synchronized void shutdown() {
    for (ScriptContext context : this.contexts)
    {
      context.shutdown();
    }
    
    this.contexts.clear();
  }




  
  public synchronized void reload() {
    for (ScriptContext context : this.contexts)
    {
      reloadContext(context);
    }
  }







  
  public void reloadContext(ScriptContext ctx) {
    ctx.reload();
  }






  
  public synchronized Collection<ScriptContext> getScriptContexts() {
    return Collections.unmodifiableSet(this.contexts);
  }







  
  public void setGlobalClassListener(ClassListener instance) {
    this.globalClassListener = instance;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\scriptmanager\ScriptManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

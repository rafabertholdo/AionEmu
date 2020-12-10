package com.aionemu.commons.scripting;

import com.aionemu.commons.scripting.impl.ScriptContextImpl;
import java.io.File;




































public final class ScriptContextFactory
{
  public static ScriptContext getScriptContext(File root, ScriptContext parent) throws InstantiationException {
    ScriptContextImpl ctx;
    if (parent == null) {
      
      ctx = new ScriptContextImpl(root);
    }
    else {
      
      ctx = new ScriptContextImpl(root, parent);
      parent.addChildScriptContext((ScriptContext)ctx);
    } 
    return (ScriptContext)ctx;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\ScriptContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

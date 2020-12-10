package com.aionemu.commons.scripting;

import java.util.Arrays;
import javolution.text.TextBuilder;










































public class CompilationResult
{
  private final Class[] compiledClasses;
  private final ScriptClassLoader classLoader;
  
  public CompilationResult(Class[] compiledClasses, ScriptClassLoader classLoader) {
    this.compiledClasses = compiledClasses;
    this.classLoader = classLoader;
  }






  
  public ScriptClassLoader getClassLoader() {
    return this.classLoader;
  }







  
  public Class[] getCompiledClasses() {
    return this.compiledClasses;
  }



  
  public String toString() {
    TextBuilder tb = TextBuilder.newInstance();
    tb.append("CompilationResult");
    tb.append("{classLoader=").append(this.classLoader);
    tb.append(", compiledClasses=").append((this.compiledClasses == null) ? "null" : Arrays.<Class<?>[]>asList((Class<?>[][])this.compiledClasses).toString());
    
    tb.append('}');
    
    String toString = tb.toString();
    
    TextBuilder.recycle(tb);
    
    return toString;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\CompilationResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

package com.aionemu.commons.scripting.impl;

import com.aionemu.commons.scripting.CompilationResult;
import com.aionemu.commons.scripting.ScriptClassLoader;
import com.aionemu.commons.scripting.ScriptCompiler;
import com.aionemu.commons.scripting.ScriptContext;
import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

























public class ScriptContextImpl
  implements ScriptContext
{
  private static final Logger log = Logger.getLogger(ScriptContextImpl.class);




  
  private final ScriptContext parentScriptContext;




  
  private Iterable<File> libraries;




  
  private final File root;




  
  private CompilationResult compilationResult;




  
  private Set<ScriptContext> childScriptContexts;




  
  private ClassListener classListener;




  
  private String compilerClassName;




  
  public ScriptContextImpl(File root) {
    this(root, null);
  }













  
  public ScriptContextImpl(File root, ScriptContext parent) {
    if (root == null)
    {
      throw new NullPointerException("Root file must be specified");
    }
    
    if (!root.exists() || !root.isDirectory())
    {
      throw new IllegalArgumentException("Root directory not exists or is not a directory");
    }
    
    this.root = root;
    this.parentScriptContext = parent;
  }






  
  public synchronized void init() {
    if (this.compilationResult != null) {
      
      log.error(new Exception("Init request on initialized ScriptContext"));
      
      return;
    } 
    ScriptCompiler scriptCompiler = instantiateCompiler();

    
    Collection<File> files = FileUtils.listFiles(this.root, scriptCompiler.getSupportedFileTypes(), true);
    
    if (this.parentScriptContext != null)
    {
      scriptCompiler.setParentClassLoader(this.parentScriptContext.getCompilationResult().getClassLoader());
    }
    
    scriptCompiler.setLibraires(this.libraries);
    this.compilationResult = scriptCompiler.compile(files);
    
    getClassListener().postLoad(this.compilationResult.getCompiledClasses());
    
    if (this.childScriptContexts != null)
    {
      for (ScriptContext context : this.childScriptContexts)
      {
        context.init();
      }
    }
  }






  
  public synchronized void shutdown() {
    if (this.compilationResult == null) {
      
      log.error("Shutdown of not initialized stript context", new Exception());
      
      return;
    } 
    if (this.childScriptContexts != null)
    {
      for (ScriptContext child : this.childScriptContexts)
      {
        child.shutdown();
      }
    }
    
    getClassListener().preUnload(this.compilationResult.getCompiledClasses());
    this.compilationResult = null;
  }





  
  public void reload() {
    shutdown();
    init();
  }





  
  public File getRoot() {
    return this.root;
  }





  
  public CompilationResult getCompilationResult() {
    return this.compilationResult;
  }





  
  public synchronized boolean isInitialized() {
    return (this.compilationResult != null);
  }





  
  public void setLibraries(Iterable<File> files) {
    this.libraries = files;
  }





  
  public Iterable<File> getLibraries() {
    return this.libraries;
  }





  
  public ScriptContext getParentScriptContext() {
    return this.parentScriptContext;
  }





  
  public Collection<ScriptContext> getChildScriptContexts() {
    return this.childScriptContexts;
  }






  
  public void addChildScriptContext(ScriptContext context) {
    synchronized (this) {
      
      if (this.childScriptContexts == null)
      {
        this.childScriptContexts = new HashSet<ScriptContext>();
      }
      
      if (this.childScriptContexts.contains(context)) {
        
        log.error("Double child definition, root: " + this.root.getAbsolutePath() + ", child: " + context.getRoot().getAbsolutePath());
        
        return;
      } 
      
      if (isInitialized())
      {
        context.init();
      }
    } 
    
    this.childScriptContexts.add(context);
  }





  
  public void setClassListener(ClassListener cl) {
    this.classListener = cl;
  }





  
  public ClassListener getClassListener() {
    if (this.classListener == null) {
      
      if (getParentScriptContext() == null) {
        
        setClassListener((ClassListener)new DefaultClassListener());
        return this.classListener;
      } 

      
      return getParentScriptContext().getClassListener();
    } 


    
    return this.classListener;
  }






  
  public void setCompilerClassName(String className) {
    this.compilerClassName = className;
  }





  
  public String getCompilerClassName() {
    return this.compilerClassName;
  }






  
  protected ScriptCompiler instantiateCompiler() throws RuntimeException {
    ScriptClassLoader scriptClassLoader;
    ScriptCompiler sc;
    ClassLoader cl = getClass().getClassLoader();
    if (getParentScriptContext() != null)
    {
      scriptClassLoader = getParentScriptContext().getCompilationResult().getClassLoader();
    }


    
    try {
      sc = (ScriptCompiler)Class.forName(getCompilerClassName(), true, (ClassLoader)scriptClassLoader).newInstance();
    }
    catch (Exception e) {
      
      RuntimeException e1 = new RuntimeException("Can't create instance of compiler", e);
      log.error(e1);
      throw e1;
    } 
    
    return sc;
  }





  
  public boolean equals(Object obj) {
    if (!(obj instanceof ScriptContextImpl))
    {
      return false;
    }
    
    ScriptContextImpl another = (ScriptContextImpl)obj;
    
    if (this.parentScriptContext == null)
    {
      return another.getRoot().equals(this.root);
    }

    
    return (another.getRoot().equals(this.root) && this.parentScriptContext.equals(another.parentScriptContext));
  }






  
  public int hashCode() {
    int result = (this.parentScriptContext != null) ? this.parentScriptContext.hashCode() : 0;
    result = 31 * result + this.root.hashCode();
    return result;
  }





  
  public void finalize() throws Throwable {
    if (this.compilationResult != null) {
      
      log.error("Finalization of initialized ScriptContext. Forcing context shutdown.");
      shutdown();
    } 
    super.finalize();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\ScriptContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

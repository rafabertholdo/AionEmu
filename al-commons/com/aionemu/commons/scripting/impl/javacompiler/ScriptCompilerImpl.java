package com.aionemu.commons.scripting.impl.javacompiler;

import com.aionemu.commons.scripting.CompilationResult;
import com.aionemu.commons.scripting.ScriptClassLoader;
import com.aionemu.commons.scripting.ScriptCompiler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import org.apache.log4j.Logger;


























public class ScriptCompilerImpl
  implements ScriptCompiler
{
  private static final Logger log = Logger.getLogger(ScriptCompilerImpl.class);























  
  protected final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
  public ScriptCompilerImpl() {
    if (this.javaCompiler == null)
    {
      if (ToolProvider.getSystemJavaCompiler() != null)
      {
        throw new RuntimeException(new InstantiationException("JavaCompiler is not aviable."));
      }
    }
  }


  
  protected Iterable<File> libraries;

  
  protected ScriptClassLoader parentClassLoader;

  
  public void setParentClassLoader(ScriptClassLoader classLoader) {
    this.parentClassLoader = classLoader;
  }








  
  public void setLibraires(Iterable<File> files) {
    this.libraries = files;
  }













  
  public CompilationResult compile(String className, String sourceCode) {
    return compile(new String[] { className }, new String[] { sourceCode });
  }
















  
  public CompilationResult compile(String[] classNames, String[] sourceCode) throws IllegalArgumentException {
    if (classNames.length != sourceCode.length)
    {
      throw new IllegalArgumentException("Amount of classes is not equal to amount of sources");
    }
    
    List<JavaFileObject> compilationUnits = new ArrayList<JavaFileObject>();
    
    for (int i = 0; i < classNames.length; i++) {
      
      JavaFileObject compilationUnit = new JavaSourceFromString(classNames[i], sourceCode[i]);
      compilationUnits.add(compilationUnit);
    } 
    
    return doCompilation(compilationUnits);
  }











  
  public CompilationResult compile(Iterable<File> compilationUnits) {
    List<JavaFileObject> list = new ArrayList<JavaFileObject>();
    
    for (File f : compilationUnits)
    {
      list.add(new JavaSourceFromFile(f, JavaFileObject.Kind.SOURCE));
    }
    
    return doCompilation(list);
  }












  
  protected CompilationResult doCompilation(Iterable<JavaFileObject> compilationUnits) {
    List<String> options = Arrays.asList(new String[] { "-encoding", "UTF-8", "-g" });
    DiagnosticListener<JavaFileObject> listener = new ErrorListener();
    ClassFileManager manager = new ClassFileManager(this.javaCompiler, listener);
    manager.setParentClassLoader(this.parentClassLoader);
    
    if (this.libraries != null) {
      
      try {
        
        manager.addLibraries(this.libraries);
      }
      catch (IOException e) {
        
        log.error("Can't set libraries for compiler.", e);
      } 
    }
    
    JavaCompiler.CompilationTask task = this.javaCompiler.getTask(null, manager, listener, options, null, compilationUnits);

    
    if (!task.call().booleanValue())
    {
      throw new RuntimeException("Error while compiling classes");
    }
    
    ScriptClassLoader cl = manager.getClassLoader((JavaFileManager.Location)null);
    Class[] compiledClasses = classNamesToClasses(manager.getCompiledClasses().keySet(), cl);
    return new CompilationResult(compiledClasses, cl);
  }













  
  protected Class[] classNamesToClasses(Collection<String> classNames, ScriptClassLoader cl) {
    Class<?>[] classes = new Class[classNames.size()];
    
    int i = 0;
    for (String className : classNames) {

      
      try {
        Class<?> clazz = cl.loadClass(className);
        classes[i] = clazz;
      }
      catch (ClassNotFoundException e) {
        
        throw new RuntimeException(e);
      } 
      i++;
    } 
    
    return classes;
  }







  
  public String[] getSupportedFileTypes() {
    return new String[] { "java" };
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\ScriptCompilerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

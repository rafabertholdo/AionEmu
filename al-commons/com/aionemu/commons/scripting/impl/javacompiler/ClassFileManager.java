package com.aionemu.commons.scripting.impl.javacompiler;

import com.aionemu.commons.scripting.ScriptClassLoader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;




























public class ClassFileManager
  extends ForwardingJavaFileManager<JavaFileManager>
{
  private final Map<String, BinaryClass> compiledClasses = new HashMap<String, BinaryClass>();





  
  protected ScriptClassLoaderImpl loader;





  
  protected ScriptClassLoader parentClassLoader;





  
  public ClassFileManager(JavaCompiler compiler, DiagnosticListener<? super JavaFileObject> listener) {
    super(compiler.getStandardFileManager(listener, null, null));
  }


















  
  public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
    BinaryClass co = new BinaryClass(className);
    this.compiledClasses.put(className, co);
    return (JavaFileObject)co;
  }









  
  public synchronized ScriptClassLoaderImpl getClassLoader(JavaFileManager.Location location) {
    if (this.loader == null)
    {
      if (this.parentClassLoader != null) {
        
        this.loader = new ScriptClassLoaderImpl(this, (ClassLoader)this.parentClassLoader);
      }
      else {
        
        this.loader = new ScriptClassLoaderImpl(this);
      } 
    }
    return this.loader;
  }







  
  public void setParentClassLoader(ScriptClassLoader classLoader) {
    this.parentClassLoader = classLoader;
  }









  
  public void addLibrary(File file) throws IOException {
    ScriptClassLoaderImpl classLoader = getClassLoader((JavaFileManager.Location)null);
    classLoader.addLibrary(file);
  }









  
  public void addLibraries(Iterable<File> files) throws IOException {
    for (File f : files)
    {
      addLibrary(f);
    }
  }






  
  public Map<String, BinaryClass> getCompiledClasses() {
    return this.compiledClasses;
  }























  
  public Iterable<JavaFileObject> list(JavaFileManager.Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
    Iterable<JavaFileObject> objects = super.list(location, packageName, kinds, recurse);
    
    if (StandardLocation.CLASS_PATH.equals(location) && kinds.contains(JavaFileObject.Kind.CLASS)) {
      
      List<JavaFileObject> temp = new ArrayList<JavaFileObject>();
      for (JavaFileObject object : objects)
      {
        temp.add(object);
      }
      
      temp.addAll(this.loader.getClassesForPackage(packageName));
      objects = temp;
    } 
    
    return objects;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\ClassFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

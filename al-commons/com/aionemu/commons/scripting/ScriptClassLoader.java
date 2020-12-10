package com.aionemu.commons.scripting;

import com.aionemu.commons.scripting.url.VirtualClassURLStreamHandler;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.log4j.Logger;



























public abstract class ScriptClassLoader
  extends URLClassLoader
{
  private static final Logger log = Logger.getLogger(ScriptClassLoader.class);



  
  private final VirtualClassURLStreamHandler urlStreamHandler = new VirtualClassURLStreamHandler(this);




  
  protected Set<String> libraryClasses = new HashSet<String>();









  
  public ScriptClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }







  
  public ScriptClassLoader(URL[] urls) {
    super(urls);
  }











  
  public ScriptClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
    super(urls, parent, factory);
  }









  
  public void addLibrary(File file) throws IOException {
    URL fileURL = file.toURI().toURL();
    addURL(fileURL);
    
    JarFile jarFile = new JarFile(file);
    
    Enumeration<JarEntry> entries = jarFile.entries();
    while (entries.hasMoreElements()) {
      
      JarEntry entry = entries.nextElement();
      
      String name = entry.getName();
      if (name.endsWith(".class")) {
        
        name = name.substring(0, name.length() - 6);
        name = name.replace('/', '.');
        this.libraryClasses.add(name);
      } 
    } 
    
    jarFile.close();
  }





  
  public URL getResource(String name) {
    if (!name.endsWith(".class"))
    {
      return super.getResource(name);
    }

    
    String newName = name.substring(0, name.length() - 6);
    newName = newName.replace('/', '.');
    if (getCompiledClasses().contains(newName)) {
      
      try {
        
        return new URL(null, "aescript://" + newName, (URLStreamHandler)this.urlStreamHandler);
      }
      catch (MalformedURLException e) {
        
        log.error("Can't create url for compiled class", e);
      } 
    }

    
    return super.getResource(name);
  }











  
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    boolean isCompiled = getCompiledClasses().contains(name);
    if (!isCompiled)
    {
      return loadClass(name, true);
    }
    
    Class<?> c = getDefinedClass(name);
    if (c == null) {
      
      byte[] b = getByteCode(name);
      c = defineClass(name, b, 0, b.length);
      setDefinedClass(name, c);
    } 
    return c;
  }






  
  public Set<String> getLibraryClasses() {
    return Collections.unmodifiableSet(this.libraryClasses);
  }
  
  public abstract Set<String> getCompiledClasses();
  
  public abstract byte[] getByteCode(String paramString);
  
  public abstract Class<?> getDefinedClass(String paramString);
  
  public abstract void setDefinedClass(String paramString, Class<?> paramClass) throws IllegalArgumentException;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\ScriptClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

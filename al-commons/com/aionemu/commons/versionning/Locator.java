package com.aionemu.commons.versionning;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;







































public final class Locator
{
  public static File getClassSource(Class<?> c) {
    String classResource = c.getName().replace('.', '/') + ".class";
    return getResourceSource(c.getClassLoader(), classResource);
  }













  
  public static File getResourceSource(ClassLoader c, String resource) {
    if (c == null)
    {
      c = Locator.class.getClassLoader();
    }
    URL url = null;
    if (c == null) {
      
      url = ClassLoader.getSystemResource(resource);
    }
    else {
      
      url = c.getResource(resource);
    } 
    if (url != null) {
      
      String u = url.toString();
      if (u.startsWith("jar:file:")) {
        
        int pling = u.indexOf("!");
        String jarName = u.substring(4, pling);
        return new File(fromURI(jarName));
      } 
      if (u.startsWith("file:")) {
        
        int tail = u.indexOf(resource);
        String dirName = u.substring(0, tail);
        return new File(fromURI(dirName));
      } 
    } 
    return null;
  }

















  
  public static String fromURI(String uri) {
    URL url = null;
    
    try {
      url = new URL(uri);
    }
    catch (MalformedURLException emYouEarlEx) {}


    
    if (url == null || !"file".equals(url.getProtocol()))
    {
      throw new IllegalArgumentException("Can only handle valid file: URIs");
    }
    StringBuffer buf = new StringBuffer(url.getHost());
    if (buf.length() > 0)
    {
      buf.insert(0, File.separatorChar).insert(0, File.separatorChar);
    }
    String file = url.getFile();
    int queryPos = file.indexOf('?');
    buf.append((queryPos < 0) ? file : file.substring(0, queryPos));
    
    uri = buf.toString().replace('/', File.separatorChar);
    
    if (File.pathSeparatorChar == ';' && uri.startsWith("\\") && uri.length() > 2 && Character.isLetter(uri.charAt(1)) && uri.lastIndexOf(':') > -1)
    {
      
      uri = uri.substring(1);
    }
    String path = decodeUri(uri);
    return path;
  }








  
  private static String decodeUri(String uri) {
    if (uri.indexOf('%') == -1)
    {
      return uri;
    }
    StringBuffer sb = new StringBuffer();
    CharacterIterator iter = new StringCharacterIterator(uri);
    for (char c = iter.first(); c != Character.MAX_VALUE; c = iter.next()) {
      
      if (c == '%') {
        
        char c1 = iter.next();
        if (c1 != Character.MAX_VALUE) {
          
          int i1 = Character.digit(c1, 16);
          char c2 = iter.next();
          if (c2 != Character.MAX_VALUE)
          {
            int i2 = Character.digit(c2, 16);
            sb.append((char)((i1 << 4) + i2));
          }
        
        } 
      } else {
        
        sb.append(c);
      } 
    } 
    String path = sb.toString();
    return path;
  }









  
  public static File getToolsJar() {
    boolean toolsJarAvailable = false;

    
    try {
      Class.forName("com.sun.tools.javac.Main");
      toolsJarAvailable = true;
    }
    catch (Exception e) {

      
      try {
        Class.forName("sun.tools.javac.Main");
        toolsJarAvailable = true;
      }
      catch (Exception e2) {}
    } 


    
    if (toolsJarAvailable)
    {
      return null;
    }

    
    String javaHome = System.getProperty("java.home");
    if (javaHome.toLowerCase(Locale.US).endsWith("jre"))
    {
      javaHome = javaHome.substring(0, javaHome.length() - 4);
    }
    File toolsJar = new File(javaHome + "/lib/tools.jar");
    if (!toolsJar.exists()) {
      
      System.out.println("Unable to locate tools.jar. Expected to find it in " + toolsJar.getPath());
      return null;
    } 
    return toolsJar;
  }













  
  public static URL[] getLocationURLs(File location) throws MalformedURLException {
    return getLocationURLs(location, new String[] { ".jar" });
  }
















  
  public static URL[] getLocationURLs(File location, final String[] extensions) throws MalformedURLException {
    URL[] urls = new URL[0];
    
    if (!location.exists())
    {
      return urls;
    }
    if (!location.isDirectory()) {
      
      urls = new URL[1];
      String path = location.getPath();
      for (int j = 0; j < extensions.length; j++) {
        
        if (path.toLowerCase().endsWith(extensions[j])) {
          
          urls[0] = location.toURI().toURL();
          break;
        } 
      } 
      return urls;
    } 
    File[] matches = location.listFiles(new FilenameFilter()
        {
          public boolean accept(File dir, String name)
          {
            for (int i = 0; i < extensions.length; i++) {
              
              if (name.toLowerCase().endsWith(extensions[i]))
              {
                return true;
              }
            } 
            return false;
          }
        });
    urls = new URL[matches.length];
    for (int i = 0; i < matches.length; i++)
    {
      urls[i] = matches[i].toURI().toURL();
    }
    return urls;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\versionning\Locator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

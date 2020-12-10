/*     */ package com.aionemu.commons.versionning;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.text.CharacterIterator;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Locator
/*     */ {
/*     */   public static File getClassSource(Class<?> c) {
/*  52 */     String classResource = c.getName().replace('.', '/') + ".class";
/*  53 */     return getResourceSource(c.getClassLoader(), classResource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getResourceSource(ClassLoader c, String resource) {
/*  70 */     if (c == null)
/*     */     {
/*  72 */       c = Locator.class.getClassLoader();
/*     */     }
/*  74 */     URL url = null;
/*  75 */     if (c == null) {
/*     */       
/*  77 */       url = ClassLoader.getSystemResource(resource);
/*     */     }
/*     */     else {
/*     */       
/*  81 */       url = c.getResource(resource);
/*     */     } 
/*  83 */     if (url != null) {
/*     */       
/*  85 */       String u = url.toString();
/*  86 */       if (u.startsWith("jar:file:")) {
/*     */         
/*  88 */         int pling = u.indexOf("!");
/*  89 */         String jarName = u.substring(4, pling);
/*  90 */         return new File(fromURI(jarName));
/*     */       } 
/*  92 */       if (u.startsWith("file:")) {
/*     */         
/*  94 */         int tail = u.indexOf(resource);
/*  95 */         String dirName = u.substring(0, tail);
/*  96 */         return new File(fromURI(dirName));
/*     */       } 
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fromURI(String uri) {
/* 120 */     URL url = null;
/*     */     
/*     */     try {
/* 123 */       url = new URL(uri);
/*     */     }
/* 125 */     catch (MalformedURLException emYouEarlEx) {}
/*     */ 
/*     */ 
/*     */     
/* 129 */     if (url == null || !"file".equals(url.getProtocol()))
/*     */     {
/* 131 */       throw new IllegalArgumentException("Can only handle valid file: URIs");
/*     */     }
/* 133 */     StringBuffer buf = new StringBuffer(url.getHost());
/* 134 */     if (buf.length() > 0)
/*     */     {
/* 136 */       buf.insert(0, File.separatorChar).insert(0, File.separatorChar);
/*     */     }
/* 138 */     String file = url.getFile();
/* 139 */     int queryPos = file.indexOf('?');
/* 140 */     buf.append((queryPos < 0) ? file : file.substring(0, queryPos));
/*     */     
/* 142 */     uri = buf.toString().replace('/', File.separatorChar);
/*     */     
/* 144 */     if (File.pathSeparatorChar == ';' && uri.startsWith("\\") && uri.length() > 2 && Character.isLetter(uri.charAt(1)) && uri.lastIndexOf(':') > -1)
/*     */     {
/*     */       
/* 147 */       uri = uri.substring(1);
/*     */     }
/* 149 */     String path = decodeUri(uri);
/* 150 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String decodeUri(String uri) {
/* 162 */     if (uri.indexOf('%') == -1)
/*     */     {
/* 164 */       return uri;
/*     */     }
/* 166 */     StringBuffer sb = new StringBuffer();
/* 167 */     CharacterIterator iter = new StringCharacterIterator(uri);
/* 168 */     for (char c = iter.first(); c != Character.MAX_VALUE; c = iter.next()) {
/*     */       
/* 170 */       if (c == '%') {
/*     */         
/* 172 */         char c1 = iter.next();
/* 173 */         if (c1 != Character.MAX_VALUE) {
/*     */           
/* 175 */           int i1 = Character.digit(c1, 16);
/* 176 */           char c2 = iter.next();
/* 177 */           if (c2 != Character.MAX_VALUE)
/*     */           {
/* 179 */             int i2 = Character.digit(c2, 16);
/* 180 */             sb.append((char)((i1 << 4) + i2));
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 186 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 189 */     String path = sb.toString();
/* 190 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getToolsJar() {
/* 203 */     boolean toolsJarAvailable = false;
/*     */ 
/*     */     
/*     */     try {
/* 207 */       Class.forName("com.sun.tools.javac.Main");
/* 208 */       toolsJarAvailable = true;
/*     */     }
/* 210 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/* 214 */         Class.forName("sun.tools.javac.Main");
/* 215 */         toolsJarAvailable = true;
/*     */       }
/* 217 */       catch (Exception e2) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (toolsJarAvailable)
/*     */     {
/* 224 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 228 */     String javaHome = System.getProperty("java.home");
/* 229 */     if (javaHome.toLowerCase(Locale.US).endsWith("jre"))
/*     */     {
/* 231 */       javaHome = javaHome.substring(0, javaHome.length() - 4);
/*     */     }
/* 233 */     File toolsJar = new File(javaHome + "/lib/tools.jar");
/* 234 */     if (!toolsJar.exists()) {
/*     */       
/* 236 */       System.out.println("Unable to locate tools.jar. Expected to find it in " + toolsJar.getPath());
/* 237 */       return null;
/*     */     } 
/* 239 */     return toolsJar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL[] getLocationURLs(File location) throws MalformedURLException {
/* 256 */     return getLocationURLs(location, new String[] { ".jar" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL[] getLocationURLs(File location, final String[] extensions) throws MalformedURLException {
/* 276 */     URL[] urls = new URL[0];
/*     */     
/* 278 */     if (!location.exists())
/*     */     {
/* 280 */       return urls;
/*     */     }
/* 282 */     if (!location.isDirectory()) {
/*     */       
/* 284 */       urls = new URL[1];
/* 285 */       String path = location.getPath();
/* 286 */       for (int j = 0; j < extensions.length; j++) {
/*     */         
/* 288 */         if (path.toLowerCase().endsWith(extensions[j])) {
/*     */           
/* 290 */           urls[0] = location.toURI().toURL();
/*     */           break;
/*     */         } 
/*     */       } 
/* 294 */       return urls;
/*     */     } 
/* 296 */     File[] matches = location.listFiles(new FilenameFilter()
/*     */         {
/*     */           public boolean accept(File dir, String name)
/*     */           {
/* 300 */             for (int i = 0; i < extensions.length; i++) {
/*     */               
/* 302 */               if (name.toLowerCase().endsWith(extensions[i]))
/*     */               {
/* 304 */                 return true;
/*     */               }
/*     */             } 
/* 307 */             return false;
/*     */           }
/*     */         });
/* 310 */     urls = new URL[matches.length];
/* 311 */     for (int i = 0; i < matches.length; i++)
/*     */     {
/* 313 */       urls[i] = matches[i].toURI().toURL();
/*     */     }
/* 315 */     return urls;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\versionning\Locator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
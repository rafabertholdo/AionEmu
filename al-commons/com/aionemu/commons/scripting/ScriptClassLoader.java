/*     */ package com.aionemu.commons.scripting;
/*     */ 
/*     */ import com.aionemu.commons.scripting.url.VirtualClassURLStreamHandler;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.net.URLStreamHandlerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class ScriptClassLoader
/*     */   extends URLClassLoader
/*     */ {
/*  48 */   private static final Logger log = Logger.getLogger(ScriptClassLoader.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private final VirtualClassURLStreamHandler urlStreamHandler = new VirtualClassURLStreamHandler(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   protected Set<String> libraryClasses = new HashSet<String>();
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
/*     */   public ScriptClassLoader(URL[] urls, ClassLoader parent) {
/*  71 */     super(urls, parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptClassLoader(URL[] urls) {
/*  82 */     super(urls);
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
/*     */   public ScriptClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
/*  97 */     super(urls, parent, factory);
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
/*     */   public void addLibrary(File file) throws IOException {
/* 110 */     URL fileURL = file.toURI().toURL();
/* 111 */     addURL(fileURL);
/*     */     
/* 113 */     JarFile jarFile = new JarFile(file);
/*     */     
/* 115 */     Enumeration<JarEntry> entries = jarFile.entries();
/* 116 */     while (entries.hasMoreElements()) {
/*     */       
/* 118 */       JarEntry entry = entries.nextElement();
/*     */       
/* 120 */       String name = entry.getName();
/* 121 */       if (name.endsWith(".class")) {
/*     */         
/* 123 */         name = name.substring(0, name.length() - 6);
/* 124 */         name = name.replace('/', '.');
/* 125 */         this.libraryClasses.add(name);
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     jarFile.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getResource(String name) {
/* 138 */     if (!name.endsWith(".class"))
/*     */     {
/* 140 */       return super.getResource(name);
/*     */     }
/*     */ 
/*     */     
/* 144 */     String newName = name.substring(0, name.length() - 6);
/* 145 */     newName = newName.replace('/', '.');
/* 146 */     if (getCompiledClasses().contains(newName)) {
/*     */       
/*     */       try {
/*     */         
/* 150 */         return new URL(null, "aescript://" + newName, (URLStreamHandler)this.urlStreamHandler);
/*     */       }
/* 152 */       catch (MalformedURLException e) {
/*     */         
/* 154 */         log.error("Can't create url for compiled class", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 159 */     return super.getResource(name);
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
/*     */   public Class<?> loadClass(String name) throws ClassNotFoundException {
/* 174 */     boolean isCompiled = getCompiledClasses().contains(name);
/* 175 */     if (!isCompiled)
/*     */     {
/* 177 */       return loadClass(name, true);
/*     */     }
/*     */     
/* 180 */     Class<?> c = getDefinedClass(name);
/* 181 */     if (c == null) {
/*     */       
/* 183 */       byte[] b = getByteCode(name);
/* 184 */       c = defineClass(name, b, 0, b.length);
/* 185 */       setDefinedClass(name, c);
/*     */     } 
/* 187 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getLibraryClasses() {
/* 197 */     return Collections.unmodifiableSet(this.libraryClasses);
/*     */   }
/*     */   
/*     */   public abstract Set<String> getCompiledClasses();
/*     */   
/*     */   public abstract byte[] getByteCode(String paramString);
/*     */   
/*     */   public abstract Class<?> getDefinedClass(String paramString);
/*     */   
/*     */   public abstract void setDefinedClass(String paramString, Class<?> paramClass) throws IllegalArgumentException;
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\ScriptClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
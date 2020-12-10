/*     */ package com.aionemu.commons.scripting.impl.javacompiler;
/*     */ 
/*     */ import com.aionemu.commons.scripting.ScriptClassLoader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.tools.DiagnosticListener;
/*     */ import javax.tools.FileObject;
/*     */ import javax.tools.ForwardingJavaFileManager;
/*     */ import javax.tools.JavaCompiler;
/*     */ import javax.tools.JavaFileManager;
/*     */ import javax.tools.JavaFileObject;
/*     */ import javax.tools.StandardLocation;
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
/*     */ public class ClassFileManager
/*     */   extends ForwardingJavaFileManager<JavaFileManager>
/*     */ {
/*  49 */   private final Map<String, BinaryClass> compiledClasses = new HashMap<String, BinaryClass>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScriptClassLoaderImpl loader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScriptClassLoader parentClassLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassFileManager(JavaCompiler compiler, DiagnosticListener<? super JavaFileObject> listener) {
/*  71 */     super(compiler.getStandardFileManager(listener, null, null));
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
/*     */   
/*     */   public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
/*  93 */     BinaryClass co = new BinaryClass(className);
/*  94 */     this.compiledClasses.put(className, co);
/*  95 */     return (JavaFileObject)co;
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
/*     */   public synchronized ScriptClassLoaderImpl getClassLoader(JavaFileManager.Location location) {
/* 108 */     if (this.loader == null)
/*     */     {
/* 110 */       if (this.parentClassLoader != null) {
/*     */         
/* 112 */         this.loader = new ScriptClassLoaderImpl(this, (ClassLoader)this.parentClassLoader);
/*     */       }
/*     */       else {
/*     */         
/* 116 */         this.loader = new ScriptClassLoaderImpl(this);
/*     */       } 
/*     */     }
/* 119 */     return this.loader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParentClassLoader(ScriptClassLoader classLoader) {
/* 130 */     this.parentClassLoader = classLoader;
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
/* 143 */     ScriptClassLoaderImpl classLoader = getClassLoader((JavaFileManager.Location)null);
/* 144 */     classLoader.addLibrary(file);
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
/*     */   public void addLibraries(Iterable<File> files) throws IOException {
/* 157 */     for (File f : files)
/*     */     {
/* 159 */       addLibrary(f);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, BinaryClass> getCompiledClasses() {
/* 170 */     return this.compiledClasses;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<JavaFileObject> list(JavaFileManager.Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
/* 197 */     Iterable<JavaFileObject> objects = super.list(location, packageName, kinds, recurse);
/*     */     
/* 199 */     if (StandardLocation.CLASS_PATH.equals(location) && kinds.contains(JavaFileObject.Kind.CLASS)) {
/*     */       
/* 201 */       List<JavaFileObject> temp = new ArrayList<JavaFileObject>();
/* 202 */       for (JavaFileObject object : objects)
/*     */       {
/* 204 */         temp.add(object);
/*     */       }
/*     */       
/* 207 */       temp.addAll(this.loader.getClassesForPackage(packageName));
/* 208 */       objects = temp;
/*     */     } 
/*     */     
/* 211 */     return objects;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\ClassFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
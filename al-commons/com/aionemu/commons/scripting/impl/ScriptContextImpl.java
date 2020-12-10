/*     */ package com.aionemu.commons.scripting.impl;
/*     */ 
/*     */ import com.aionemu.commons.scripting.CompilationResult;
/*     */ import com.aionemu.commons.scripting.ScriptClassLoader;
/*     */ import com.aionemu.commons.scripting.ScriptCompiler;
/*     */ import com.aionemu.commons.scripting.ScriptContext;
/*     */ import com.aionemu.commons.scripting.classlistener.ClassListener;
/*     */ import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.io.FileUtils;
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
/*     */ public class ScriptContextImpl
/*     */   implements ScriptContext
/*     */ {
/*  43 */   private static final Logger log = Logger.getLogger(ScriptContextImpl.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ScriptContext parentScriptContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Iterable<File> libraries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final File root;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CompilationResult compilationResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<ScriptContext> childScriptContexts;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassListener classListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String compilerClassName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptContextImpl(File root) {
/*  92 */     this(root, null);
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
/*     */   public ScriptContextImpl(File root, ScriptContext parent) {
/* 109 */     if (root == null)
/*     */     {
/* 111 */       throw new NullPointerException("Root file must be specified");
/*     */     }
/*     */     
/* 114 */     if (!root.exists() || !root.isDirectory())
/*     */     {
/* 116 */       throw new IllegalArgumentException("Root directory not exists or is not a directory");
/*     */     }
/*     */     
/* 119 */     this.root = root;
/* 120 */     this.parentScriptContext = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void init() {
/* 130 */     if (this.compilationResult != null) {
/*     */       
/* 132 */       log.error(new Exception("Init request on initialized ScriptContext"));
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     ScriptCompiler scriptCompiler = instantiateCompiler();
/*     */ 
/*     */     
/* 139 */     Collection<File> files = FileUtils.listFiles(this.root, scriptCompiler.getSupportedFileTypes(), true);
/*     */     
/* 141 */     if (this.parentScriptContext != null)
/*     */     {
/* 143 */       scriptCompiler.setParentClassLoader(this.parentScriptContext.getCompilationResult().getClassLoader());
/*     */     }
/*     */     
/* 146 */     scriptCompiler.setLibraires(this.libraries);
/* 147 */     this.compilationResult = scriptCompiler.compile(files);
/*     */     
/* 149 */     getClassListener().postLoad(this.compilationResult.getCompiledClasses());
/*     */     
/* 151 */     if (this.childScriptContexts != null)
/*     */     {
/* 153 */       for (ScriptContext context : this.childScriptContexts)
/*     */       {
/* 155 */         context.init();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void shutdown() {
/* 167 */     if (this.compilationResult == null) {
/*     */       
/* 169 */       log.error("Shutdown of not initialized stript context", new Exception());
/*     */       
/*     */       return;
/*     */     } 
/* 173 */     if (this.childScriptContexts != null)
/*     */     {
/* 175 */       for (ScriptContext child : this.childScriptContexts)
/*     */       {
/* 177 */         child.shutdown();
/*     */       }
/*     */     }
/*     */     
/* 181 */     getClassListener().preUnload(this.compilationResult.getCompiledClasses());
/* 182 */     this.compilationResult = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reload() {
/* 191 */     shutdown();
/* 192 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getRoot() {
/* 201 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompilationResult getCompilationResult() {
/* 210 */     return this.compilationResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isInitialized() {
/* 219 */     return (this.compilationResult != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLibraries(Iterable<File> files) {
/* 228 */     this.libraries = files;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<File> getLibraries() {
/* 237 */     return this.libraries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptContext getParentScriptContext() {
/* 246 */     return this.parentScriptContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ScriptContext> getChildScriptContexts() {
/* 255 */     return this.childScriptContexts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChildScriptContext(ScriptContext context) {
/* 265 */     synchronized (this) {
/*     */       
/* 267 */       if (this.childScriptContexts == null)
/*     */       {
/* 269 */         this.childScriptContexts = new HashSet<ScriptContext>();
/*     */       }
/*     */       
/* 272 */       if (this.childScriptContexts.contains(context)) {
/*     */         
/* 274 */         log.error("Double child definition, root: " + this.root.getAbsolutePath() + ", child: " + context.getRoot().getAbsolutePath());
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 279 */       if (isInitialized())
/*     */       {
/* 281 */         context.init();
/*     */       }
/*     */     } 
/*     */     
/* 285 */     this.childScriptContexts.add(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClassListener(ClassListener cl) {
/* 294 */     this.classListener = cl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassListener getClassListener() {
/* 303 */     if (this.classListener == null) {
/*     */       
/* 305 */       if (getParentScriptContext() == null) {
/*     */         
/* 307 */         setClassListener((ClassListener)new DefaultClassListener());
/* 308 */         return this.classListener;
/*     */       } 
/*     */ 
/*     */       
/* 312 */       return getParentScriptContext().getClassListener();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 317 */     return this.classListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompilerClassName(String className) {
/* 327 */     this.compilerClassName = className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompilerClassName() {
/* 336 */     return this.compilerClassName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScriptCompiler instantiateCompiler() throws RuntimeException {
/*     */     ScriptClassLoader scriptClassLoader;
/*     */     ScriptCompiler sc;
/* 348 */     ClassLoader cl = getClass().getClassLoader();
/* 349 */     if (getParentScriptContext() != null)
/*     */     {
/* 351 */       scriptClassLoader = getParentScriptContext().getCompilationResult().getClassLoader();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 357 */       sc = (ScriptCompiler)Class.forName(getCompilerClassName(), true, (ClassLoader)scriptClassLoader).newInstance();
/*     */     }
/* 359 */     catch (Exception e) {
/*     */       
/* 361 */       RuntimeException e1 = new RuntimeException("Can't create instance of compiler", e);
/* 362 */       log.error(e1);
/* 363 */       throw e1;
/*     */     } 
/*     */     
/* 366 */     return sc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 375 */     if (!(obj instanceof ScriptContextImpl))
/*     */     {
/* 377 */       return false;
/*     */     }
/*     */     
/* 380 */     ScriptContextImpl another = (ScriptContextImpl)obj;
/*     */     
/* 382 */     if (this.parentScriptContext == null)
/*     */     {
/* 384 */       return another.getRoot().equals(this.root);
/*     */     }
/*     */ 
/*     */     
/* 388 */     return (another.getRoot().equals(this.root) && this.parentScriptContext.equals(another.parentScriptContext));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 398 */     int result = (this.parentScriptContext != null) ? this.parentScriptContext.hashCode() : 0;
/* 399 */     result = 31 * result + this.root.hashCode();
/* 400 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finalize() throws Throwable {
/* 409 */     if (this.compilationResult != null) {
/*     */       
/* 411 */       log.error("Finalization of initialized ScriptContext. Forcing context shutdown.");
/* 412 */       shutdown();
/*     */     } 
/* 414 */     super.finalize();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\ScriptContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
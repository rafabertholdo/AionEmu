/*     */ package com.aionemu.commons.scripting.impl.javacompiler;
/*     */ 
/*     */ import com.aionemu.commons.scripting.CompilationResult;
/*     */ import com.aionemu.commons.scripting.ScriptClassLoader;
/*     */ import com.aionemu.commons.scripting.ScriptCompiler;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.tools.DiagnosticListener;
/*     */ import javax.tools.JavaCompiler;
/*     */ import javax.tools.JavaFileManager;
/*     */ import javax.tools.JavaFileObject;
/*     */ import javax.tools.ToolProvider;
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
/*     */ public class ScriptCompilerImpl
/*     */   implements ScriptCompiler
/*     */ {
/*  47 */   private static final Logger log = Logger.getLogger(ScriptCompilerImpl.class);
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
/*  72 */   protected final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
/*     */   public ScriptCompilerImpl() {
/*  74 */     if (this.javaCompiler == null)
/*     */     {
/*  76 */       if (ToolProvider.getSystemJavaCompiler() != null)
/*     */       {
/*  78 */         throw new RuntimeException(new InstantiationException("JavaCompiler is not aviable."));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Iterable<File> libraries;
/*     */ 
/*     */   
/*     */   protected ScriptClassLoader parentClassLoader;
/*     */ 
/*     */   
/*     */   public void setParentClassLoader(ScriptClassLoader classLoader) {
/*  92 */     this.parentClassLoader = classLoader;
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
/*     */   public void setLibraires(Iterable<File> files) {
/* 104 */     this.libraries = files;
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
/*     */   public CompilationResult compile(String className, String sourceCode) {
/* 121 */     return compile(new String[] { className }, new String[] { sourceCode });
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
/*     */   public CompilationResult compile(String[] classNames, String[] sourceCode) throws IllegalArgumentException {
/* 141 */     if (classNames.length != sourceCode.length)
/*     */     {
/* 143 */       throw new IllegalArgumentException("Amount of classes is not equal to amount of sources");
/*     */     }
/*     */     
/* 146 */     List<JavaFileObject> compilationUnits = new ArrayList<JavaFileObject>();
/*     */     
/* 148 */     for (int i = 0; i < classNames.length; i++) {
/*     */       
/* 150 */       JavaFileObject compilationUnit = new JavaSourceFromString(classNames[i], sourceCode[i]);
/* 151 */       compilationUnits.add(compilationUnit);
/*     */     } 
/*     */     
/* 154 */     return doCompilation(compilationUnits);
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
/*     */   public CompilationResult compile(Iterable<File> compilationUnits) {
/* 169 */     List<JavaFileObject> list = new ArrayList<JavaFileObject>();
/*     */     
/* 171 */     for (File f : compilationUnits)
/*     */     {
/* 173 */       list.add(new JavaSourceFromFile(f, JavaFileObject.Kind.SOURCE));
/*     */     }
/*     */     
/* 176 */     return doCompilation(list);
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
/*     */   protected CompilationResult doCompilation(Iterable<JavaFileObject> compilationUnits) {
/* 192 */     List<String> options = Arrays.asList(new String[] { "-encoding", "UTF-8", "-g" });
/* 193 */     DiagnosticListener<JavaFileObject> listener = new ErrorListener();
/* 194 */     ClassFileManager manager = new ClassFileManager(this.javaCompiler, listener);
/* 195 */     manager.setParentClassLoader(this.parentClassLoader);
/*     */     
/* 197 */     if (this.libraries != null) {
/*     */       
/*     */       try {
/*     */         
/* 201 */         manager.addLibraries(this.libraries);
/*     */       }
/* 203 */       catch (IOException e) {
/*     */         
/* 205 */         log.error("Can't set libraries for compiler.", e);
/*     */       } 
/*     */     }
/*     */     
/* 209 */     JavaCompiler.CompilationTask task = this.javaCompiler.getTask(null, manager, listener, options, null, compilationUnits);
/*     */ 
/*     */     
/* 212 */     if (!task.call().booleanValue())
/*     */     {
/* 214 */       throw new RuntimeException("Error while compiling classes");
/*     */     }
/*     */     
/* 217 */     ScriptClassLoader cl = manager.getClassLoader((JavaFileManager.Location)null);
/* 218 */     Class[] compiledClasses = classNamesToClasses(manager.getCompiledClasses().keySet(), cl);
/* 219 */     return new CompilationResult(compiledClasses, cl);
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
/*     */   protected Class[] classNamesToClasses(Collection<String> classNames, ScriptClassLoader cl) {
/* 236 */     Class<?>[] classes = new Class[classNames.size()];
/*     */     
/* 238 */     int i = 0;
/* 239 */     for (String className : classNames) {
/*     */ 
/*     */       
/*     */       try {
/* 243 */         Class<?> clazz = cl.loadClass(className);
/* 244 */         classes[i] = clazz;
/*     */       }
/* 246 */       catch (ClassNotFoundException e) {
/*     */         
/* 248 */         throw new RuntimeException(e);
/*     */       } 
/* 250 */       i++;
/*     */     } 
/*     */     
/* 253 */     return classes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getSupportedFileTypes() {
/* 264 */     return new String[] { "java" };
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\ScriptCompilerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
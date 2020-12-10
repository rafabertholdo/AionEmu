/*     */ package com.aionemu.commons.scripting.impl.javacompiler;
/*     */ 
/*     */ import com.aionemu.commons.scripting.ScriptClassLoader;
/*     */ import com.aionemu.commons.utils.ClassUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.tools.JavaFileObject;
/*     */ import org.apache.commons.io.IOUtils;
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
/*     */ 
/*     */ 
/*     */ public class ScriptClassLoaderImpl
/*     */   extends ScriptClassLoader
/*     */ {
/*  47 */   private static final Logger log = Logger.getLogger(ScriptClassLoaderImpl.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassFileManager classFileManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ScriptClassLoaderImpl(ClassFileManager classFileManager) {
/*  62 */     super(new URL[0]);
/*  63 */     this.classFileManager = classFileManager;
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
/*     */   ScriptClassLoaderImpl(ClassFileManager classFileManager, ClassLoader parent) {
/*  76 */     super(new URL[0], parent);
/*  77 */     this.classFileManager = classFileManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassFileManager getClassFileManager() {
/*  87 */     return this.classFileManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getCompiledClasses() {
/*  96 */     Set<String> compiledClasses = this.classFileManager.getCompiledClasses().keySet();
/*  97 */     return Collections.unmodifiableSet(compiledClasses);
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
/*     */   public Set<JavaFileObject> getClassesForPackage(String packageName) throws IOException {
/* 111 */     Set<JavaFileObject> result = new HashSet<JavaFileObject>();
/*     */ 
/*     */     
/* 114 */     ClassLoader parent = getParent();
/* 115 */     if (parent instanceof ScriptClassLoaderImpl) {
/*     */       
/* 117 */       ScriptClassLoaderImpl pscl = (ScriptClassLoaderImpl)parent;
/* 118 */       result.addAll(pscl.getClassesForPackage(packageName));
/*     */     } 
/*     */ 
/*     */     
/* 122 */     for (String cn : this.classFileManager.getCompiledClasses().keySet()) {
/*     */       
/* 124 */       if (ClassUtils.isPackageMember(cn, packageName)) {
/*     */         
/* 126 */         BinaryClass bc = this.classFileManager.getCompiledClasses().get(cn);
/* 127 */         result.add(bc);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 132 */     for (String cn : this.libraryClasses) {
/*     */       
/* 134 */       if (ClassUtils.isPackageMember(cn, packageName)) {
/*     */         
/* 136 */         BinaryClass bc = new BinaryClass(cn);
/*     */         
/*     */         try {
/* 139 */           byte[] data = getRawClassByName(cn);
/* 140 */           OutputStream os = bc.openOutputStream();
/* 141 */           os.write(data);
/*     */         }
/* 143 */         catch (IOException e) {
/*     */           
/* 145 */           log.error("Error while loading class from package " + packageName, e);
/* 146 */           throw e;
/*     */         } 
/* 148 */         result.add(bc);
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     return result;
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
/*     */   protected byte[] getRawClassByName(String name) throws IOException {
/* 167 */     URL resource = findResource(name.replace('.', '/').concat(".class"));
/* 168 */     InputStream is = null;
/* 169 */     byte[] clazz = null;
/*     */ 
/*     */     
/*     */     try {
/* 173 */       is = resource.openStream();
/* 174 */       clazz = IOUtils.toByteArray(is);
/*     */     }
/* 176 */     catch (IOException e) {
/*     */       
/* 178 */       log.error("Error while loading class data", e);
/* 179 */       throw e;
/*     */     }
/*     */     finally {
/*     */       
/* 183 */       if (is != null) {
/*     */         
/*     */         try {
/*     */           
/* 187 */           is.close();
/*     */         }
/* 189 */         catch (IOException e) {
/*     */           
/* 191 */           log.error("Error while closing stream", e);
/*     */         } 
/*     */       }
/*     */     } 
/* 195 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteCode(String className) {
/* 204 */     BinaryClass bc = getClassFileManager().getCompiledClasses().get(className);
/* 205 */     byte[] b = new byte[(bc.getBytes()).length];
/* 206 */     System.arraycopy(bc.getBytes(), 0, b, 0, b.length);
/* 207 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getDefinedClass(String name) {
/* 216 */     BinaryClass bc = this.classFileManager.getCompiledClasses().get(name);
/* 217 */     if (bc == null)
/*     */     {
/* 219 */       return null;
/*     */     }
/*     */     
/* 222 */     return bc.getDefinedClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefinedClass(String name, Class<?> clazz) {
/* 231 */     BinaryClass bc = this.classFileManager.getCompiledClasses().get(name);
/*     */     
/* 233 */     if (bc == null)
/*     */     {
/* 235 */       throw new IllegalArgumentException("Attempt to set defined class for class that was not compiled?");
/*     */     }
/*     */     
/* 238 */     bc.setDefinedClass(clazz);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\ScriptClassLoaderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.commons.scripting.scriptmanager;
/*     */ 
/*     */ import com.aionemu.commons.scripting.ScriptContext;
/*     */ import com.aionemu.commons.scripting.ScriptContextFactory;
/*     */ import com.aionemu.commons.scripting.classlistener.ClassListener;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public class ScriptManager
/*     */ {
/*  59 */   private static final Logger log = Logger.getLogger(ScriptManager.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private Set<ScriptContext> contexts = new HashSet<ScriptContext>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassListener globalClassListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void load(File scriptDescriptor) throws Exception {
/*  82 */     JAXBContext c = JAXBContext.newInstance(new Class[] { ScriptInfo.class, ScriptList.class });
/*  83 */     Unmarshaller u = c.createUnmarshaller();
/*     */     
/*  85 */     ScriptList list = (ScriptList)u.unmarshal(scriptDescriptor);
/*     */     
/*  87 */     for (ScriptInfo si : list.getScriptInfos()) {
/*     */       
/*  89 */       ScriptContext context = createContext(si, null);
/*  90 */       if (context != null) {
/*     */         
/*  92 */         this.contexts.add(context);
/*  93 */         context.init();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptContext createContext(ScriptInfo si, ScriptContext parent) throws Exception {
/* 111 */     ScriptContext context = ScriptContextFactory.getScriptContext(si.getRoot(), parent);
/* 112 */     context.setLibraries(si.getLibraries());
/* 113 */     context.setCompilerClassName(si.getCompilerClass());
/*     */     
/* 115 */     if (parent == null && this.contexts.contains(context)) {
/*     */       
/* 117 */       log.warn("Double root script context definition: " + si.getRoot().getAbsolutePath());
/* 118 */       return null;
/*     */     } 
/*     */     
/* 121 */     if (si.getScriptInfos() != null && !si.getScriptInfos().isEmpty())
/*     */     {
/* 123 */       for (ScriptInfo child : si.getScriptInfos())
/*     */       {
/* 125 */         createContext(child, context);
/*     */       }
/*     */     }
/*     */     
/* 129 */     if (parent == null && this.globalClassListener != null) {
/* 130 */       context.setClassListener(this.globalClassListener);
/*     */     }
/* 132 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void shutdown() {
/* 140 */     for (ScriptContext context : this.contexts)
/*     */     {
/* 142 */       context.shutdown();
/*     */     }
/*     */     
/* 145 */     this.contexts.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reload() {
/* 153 */     for (ScriptContext context : this.contexts)
/*     */     {
/* 155 */       reloadContext(context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadContext(ScriptContext ctx) {
/* 167 */     ctx.reload();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Collection<ScriptContext> getScriptContexts() {
/* 177 */     return Collections.unmodifiableSet(this.contexts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlobalClassListener(ClassListener instance) {
/* 188 */     this.globalClassListener = instance;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\scriptmanager\ScriptManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
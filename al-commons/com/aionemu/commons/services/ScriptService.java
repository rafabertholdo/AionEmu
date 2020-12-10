/*     */ package com.aionemu.commons.services;
/*     */ 
/*     */ import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
/*     */ import java.io.File;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptService
/*     */ {
/*  42 */   private static final Logger log = Logger.getLogger(ScriptService.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private final Map<File, ScriptManager> map = (Map<File, ScriptManager>)(new FastMap()).shared();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(String file) throws RuntimeException {
/*  59 */     load(new File(file));
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
/*     */   public void load(File file) throws RuntimeException {
/*  72 */     if (file.isFile()) {
/*     */       
/*  74 */       loadFile(file);
/*     */     }
/*  76 */     else if (file.isDirectory()) {
/*     */       
/*  78 */       loadDir(file);
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
/*     */   private void loadFile(File file) {
/*  90 */     if (this.map.containsKey(file))
/*     */     {
/*  92 */       throw new IllegalArgumentException("ScriptManager by file:" + file + " already loaded");
/*     */     }
/*     */     
/*  95 */     ScriptManager sm = new ScriptManager();
/*     */     
/*     */     try {
/*  98 */       sm.load(file);
/*     */     }
/* 100 */     catch (Exception e) {
/*     */       
/* 102 */       log.error(e);
/* 103 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 106 */     this.map.put(file, sm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadDir(File dir) {
/* 117 */     for (Object file : FileUtils.listFiles(dir, new String[] { "xml" }, false))
/*     */     {
/* 119 */       loadFile((File)file);
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
/*     */   public void unload(File file) throws IllegalArgumentException {
/* 133 */     ScriptManager sm = this.map.remove(file);
/* 134 */     if (sm == null)
/*     */     {
/* 136 */       throw new IllegalArgumentException("ScriptManager by file " + file + " is not loaded.");
/*     */     }
/*     */     
/* 139 */     sm.shutdown();
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
/*     */   public void reload(File file) throws IllegalArgumentException {
/* 152 */     ScriptManager sm = this.map.get(file);
/* 153 */     if (sm == null)
/*     */     {
/* 155 */       throw new IllegalArgumentException("ScriptManager by file " + file + " is not loaded.");
/*     */     }
/*     */     
/* 158 */     sm.reload();
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
/*     */   public void addScriptManager(ScriptManager scriptManager, File file) {
/* 172 */     if (this.map.containsKey(file))
/*     */     {
/* 174 */       throw new IllegalArgumentException("ScriptManager by file " + file + " is already loaded.");
/*     */     }
/*     */     
/* 177 */     this.map.put(file, scriptManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<File, ScriptManager> getLoadedScriptManagers() {
/* 187 */     return Collections.unmodifiableMap(this.map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 195 */     for (Iterator<Map.Entry<File, ScriptManager>> it = this.map.entrySet().iterator(); it.hasNext(); ) {
/*     */ 
/*     */       
/*     */       try {
/* 199 */         ((ScriptManager)((Map.Entry)it.next()).getValue()).shutdown();
/*     */       }
/* 201 */       catch (Exception e) {
/*     */         
/* 203 */         log.warn("An exception occured during shudown procedure.", e);
/*     */       } 
/*     */       
/* 206 */       it.remove();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\services\ScriptService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
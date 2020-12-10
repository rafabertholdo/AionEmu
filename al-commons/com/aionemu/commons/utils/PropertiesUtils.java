/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Properties;
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
/*     */ public class PropertiesUtils
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(PropertiesUtils.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Properties load(String file) throws IOException {
/*  48 */     return load(new File(file));
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
/*     */   public static Properties load(File file) throws IOException {
/*  62 */     log.info("Loading: " + file);
/*     */     
/*  64 */     FileInputStream fis = new FileInputStream(file);
/*  65 */     Properties p = new Properties();
/*  66 */     p.load(fis);
/*  67 */     fis.close();
/*  68 */     return p;
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
/*     */   public static Properties[] load(String... files) throws IOException {
/*  82 */     Properties[] result = new Properties[files.length];
/*  83 */     for (int i = 0; i < result.length; i++)
/*     */     {
/*  85 */       result[i] = load(files[i]);
/*     */     }
/*  87 */     return result;
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
/*     */   public static Properties[] load(File... files) throws IOException {
/* 101 */     Properties[] result = new Properties[files.length];
/* 102 */     for (int i = 0; i < result.length; i++)
/*     */     {
/* 104 */       result[i] = load(files[i]);
/*     */     }
/* 106 */     return result;
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
/*     */   public static Properties[] loadAllFromDirectory(String dir) throws IOException {
/* 120 */     return loadAllFromDirectory(new File(dir), false);
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
/*     */   public static Properties[] loadAllFromDirectory(File dir) throws IOException {
/* 134 */     return loadAllFromDirectory(dir, false);
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
/*     */   public static Properties[] loadAllFromDirectory(String dir, boolean recursive) throws IOException {
/* 150 */     return loadAllFromDirectory(new File(dir), recursive);
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
/*     */   public static Properties[] loadAllFromDirectory(File dir, boolean recursive) throws IOException {
/* 167 */     Collection<File> files = FileUtils.listFiles(dir, new String[] { "properties" }, recursive);
/* 168 */     return load(files.<File>toArray(new File[files.size()]));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\PropertiesUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.commons.versionning;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
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
/*     */ public class Version
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(Version.class);
/*     */   
/*     */   private String version;
/*     */   private String revision;
/*  39 */   private long date = -1L;
/*     */ 
/*     */ 
/*     */   
/*     */   public Version() {}
/*     */ 
/*     */   
/*     */   public Version(Class<?> c) {
/*  47 */     loadInformation(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadInformation(Class<?> c) {
/*  52 */     File jarName = null;
/*     */     
/*     */     try {
/*  55 */       jarName = Locator.getClassSource(c);
/*  56 */       JarFile jarFile = new JarFile(jarName);
/*     */       
/*  58 */       Attributes attrs = jarFile.getManifest().getMainAttributes();
/*     */       
/*  60 */       setVersion(attrs);
/*     */       
/*  62 */       setRevision(attrs);
/*     */       
/*  64 */       setDate(attrs);
/*     */     }
/*  66 */     catch (IOException e) {
/*     */       
/*  68 */       log.error("Unable to get Soft information\nFile name '" + ((jarName == null) ? "null" : jarName.getAbsolutePath()) + "' isn't a valid jar", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transferInfo(String jarName, String type, File fileToWrite) {
/*     */     try {
/*  78 */       if (!fileToWrite.exists()) {
/*     */         
/*  80 */         log.error("Unable to Find File :" + fileToWrite.getName() + " Please Update your " + type);
/*     */         
/*     */         return;
/*     */       } 
/*  84 */       JarFile jarFile = new JarFile("./" + jarName);
/*     */       
/*  86 */       Manifest manifest = jarFile.getManifest();
/*     */       
/*  88 */       OutputStream fos = new FileOutputStream(fileToWrite);
/*  89 */       manifest.write(fos);
/*  90 */       fos.close();
/*     */     }
/*  92 */     catch (IOException e) {
/*     */       
/*  94 */       log.error("Error, " + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVersion(Attributes attrs) {
/* 100 */     String version = attrs.getValue("Implementation-Version");
/*     */     
/* 102 */     if (version != null) {
/* 103 */       this.version = version;
/*     */     } else {
/* 105 */       this.version = "-1";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 110 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRevision(Attributes attrs) {
/* 115 */     String revision = attrs.getValue("Implementation-Build");
/*     */     
/* 117 */     if (revision != null) {
/* 118 */       this.revision = revision;
/*     */     } else {
/* 120 */       this.revision = "-1";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getRevision() {
/* 125 */     return this.revision;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDate(Attributes attrs) {
/* 130 */     String buildTime = attrs.getValue("Implementation-Time");
/*     */     
/* 132 */     if (buildTime != null) {
/* 133 */       this.date = Long.parseLong(buildTime);
/*     */     } else {
/* 135 */       this.date = -1L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getDate() {
/* 140 */     return this.date;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\versionning\Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
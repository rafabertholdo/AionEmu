/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.LineIterator;
/*     */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*     */ import org.apache.commons.io.filefilter.HiddenFileFilter;
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
/*     */ abstract class DataLoader
/*     */ {
/*  47 */   protected Logger log = Logger.getLogger(getClass().getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PATH = "./data/static_data/";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File dataFile;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DataLoader(String file) {
/*  63 */     this.dataFile = new File("./data/static_data/" + file);
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
/*     */   protected void loadData() {
/*  79 */     if (this.dataFile.isDirectory()) {
/*     */       
/*  81 */       Collection<?> files = FileUtils.listFiles(this.dataFile, FileFilterUtils.andFileFilter(FileFilterUtils.andFileFilter(FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter("new")), FileFilterUtils.suffixFileFilter(".txt")), HiddenFileFilter.VISIBLE), HiddenFileFilter.VISIBLE);
/*     */ 
/*     */ 
/*     */       
/*  85 */       for (Object file1 : files)
/*     */       {
/*  87 */         File f = (File)file1;
/*  88 */         loadFile(f);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  93 */       loadFile(this.dataFile);
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
/*     */   private void loadFile(File file) {
/* 109 */     LineIterator it = null;
/*     */     
/*     */     try {
/* 112 */       it = FileUtils.lineIterator(file);
/* 113 */       while (it.hasNext())
/*     */       {
/* 115 */         String line = it.nextLine();
/* 116 */         if (line.isEmpty() || line.startsWith("#")) {
/*     */           continue;
/*     */         }
/*     */         
/* 120 */         parse(line);
/*     */       }
/*     */     
/* 123 */     } catch (IOException e) {
/*     */       
/* 125 */       this.log.error("Error while loading " + getClass().getSimpleName() + ", file: " + file.getPath(), e);
/*     */     }
/*     */     finally {
/*     */       
/* 129 */       LineIterator.closeQuietly(it);
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
/*     */   protected abstract void parse(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveData() {
/* 149 */     String desc = "./data/static_data/" + getSaveFile();
/*     */     
/* 151 */     this.log.info("Saving " + desc);
/*     */     
/* 153 */     FileWriter fr = null;
/*     */     
/*     */     try {
/* 156 */       fr = new FileWriter(desc);
/*     */       
/* 158 */       saveEntries(fr);
/*     */       
/* 160 */       fr.flush();
/*     */       
/* 162 */       return true;
/*     */     }
/* 164 */     catch (Exception e) {
/*     */       
/* 166 */       this.log.fatal("Error while saving " + desc, e);
/* 167 */       return false;
/*     */     }
/*     */     finally {
/*     */       
/* 171 */       IOUtils.closeQuietly(fr);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract String getSaveFile();
/*     */   
/*     */   protected void saveEntries(FileWriter fileWriter) throws Exception {}
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\DataLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
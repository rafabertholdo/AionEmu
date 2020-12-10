/*     */ package com.aionemu.gameserver.cache;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.HTMLConfig;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
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
/*     */ public final class HTMLCache
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(HTMLCache.class);
/*     */   
/*  47 */   private static final FileFilter HTML_FILTER = new FileFilter()
/*     */     {
/*     */       
/*     */       public boolean accept(File file)
/*     */       {
/*  52 */         return (file.isDirectory() || file.getName().endsWith(".xhtml"));
/*     */       }
/*     */     };
/*     */   
/*  56 */   private static final File HTML_ROOT = new File(HTMLConfig.HTML_ROOT);
/*     */   
/*     */   private static final class SingletonHolder
/*     */   {
/*  60 */     private static final HTMLCache INSTANCE = new HTMLCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public static HTMLCache getInstance() {
/*  65 */     return SingletonHolder.INSTANCE;
/*     */   }
/*     */   
/*  68 */   private FastMap<String, String> cache = new FastMap(16000);
/*     */   
/*     */   private int loadedFiles;
/*     */   private int size;
/*     */   private static final String[] TAGS_TO_COMPACT;
/*     */   
/*     */   private HTMLCache() {
/*  75 */     reload(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reload(boolean deleteCacheFile) {
/*  81 */     this.cache.clear();
/*  82 */     this.loadedFiles = 0;
/*  83 */     this.size = 0;
/*     */     
/*  85 */     File cacheFile = getCacheFile();
/*     */     
/*  87 */     if (deleteCacheFile && cacheFile.exists()) {
/*     */       
/*  89 */       log.info("Cache[HTML]: Deleting cache file... OK.");
/*     */       
/*  91 */       cacheFile.delete();
/*     */     } 
/*     */     
/*  94 */     log.info("Cache[HTML]: Caching started... OK.");
/*     */     
/*  96 */     if (cacheFile.exists()) {
/*     */       
/*  98 */       log.info("Cache[HTML]: Using cache file... OK.");
/*     */       
/* 100 */       ObjectInputStream ois = null;
/*     */       
/*     */       try {
/* 103 */         ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(getCacheFile())));
/*     */         
/* 105 */         this.cache = (FastMap<String, String>)ois.readObject();
/*     */         
/* 107 */         for (String html : this.cache.values())
/*     */         {
/* 109 */           this.loadedFiles++;
/* 110 */           this.size += html.length();
/*     */         }
/*     */       
/* 113 */       } catch (Exception e) {
/*     */         
/* 115 */         log.warn("", e);
/*     */         
/* 117 */         reload(true);
/*     */ 
/*     */         
/*     */         return;
/*     */       } finally {
/* 122 */         IOUtils.closeQuietly(ois);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 127 */       parseDir(HTML_ROOT);
/*     */     } 
/*     */     
/* 130 */     log.info(String.valueOf(this));
/*     */     
/* 132 */     if (cacheFile.exists()) {
/*     */       
/* 134 */       log.info("Cache[HTML]: Compaction skipped!");
/*     */     }
/*     */     else {
/*     */       
/* 138 */       log.info("Cache[HTML]: Compacting htmls... OK.");
/*     */       
/* 140 */       StringBuilder sb = new StringBuilder(8192);
/*     */       
/* 142 */       for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)this.cache.entrySet()) {
/*     */ 
/*     */         
/*     */         try {
/* 146 */           String oldHtml = entry.getValue();
/* 147 */           String newHtml = compactHtml(sb, oldHtml);
/*     */           
/* 149 */           this.size -= oldHtml.length();
/* 150 */           this.size += newHtml.length();
/*     */           
/* 152 */           entry.setValue(newHtml);
/*     */         }
/* 154 */         catch (RuntimeException e) {
/*     */           
/* 156 */           log.warn("Cache[HTML]: Error during compaction of " + (String)entry.getKey(), e);
/*     */         } 
/*     */       } 
/*     */       
/* 160 */       log.info(String.valueOf(this));
/*     */     } 
/*     */     
/* 163 */     if (!cacheFile.exists()) {
/*     */       
/* 165 */       log.info("Cache[HTML]: Creating cache file... OK.");
/*     */       
/* 167 */       ObjectOutputStream oos = null;
/*     */       
/*     */       try {
/* 170 */         oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getCacheFile())));
/*     */         
/* 172 */         oos.writeObject(this.cache);
/*     */       }
/* 174 */       catch (IOException e) {
/*     */         
/* 176 */         log.warn("", e);
/*     */       }
/*     */       finally {
/*     */         
/* 180 */         IOUtils.closeQuietly(oos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private File getCacheFile() {
/* 187 */     return new File(HTMLConfig.HTML_CACHE_FILE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 195 */     String[] tagsToCompact = { "html", "title", "body", "br", "br1", "p", "table", "tr", "td" };
/*     */     
/* 197 */     List<String> list = new ArrayList<String>();
/*     */     
/* 199 */     for (String tag : tagsToCompact) {
/*     */       
/* 201 */       list.add("<" + tag + ">");
/* 202 */       list.add("</" + tag + ">");
/* 203 */       list.add("<" + tag + "/>");
/* 204 */       list.add("<" + tag + " />");
/*     */     } 
/*     */     
/* 207 */     List<String> list2 = new ArrayList<String>();
/*     */     
/* 209 */     for (String tag : list) {
/*     */       
/* 211 */       list2.add(tag);
/* 212 */       list2.add(tag + " ");
/* 213 */       list2.add(" " + tag);
/*     */     } 
/*     */     
/* 216 */     TAGS_TO_COMPACT = list2.<String>toArray(new String[list.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   private String compactHtml(StringBuilder sb, String html) {
/* 221 */     sb.setLength(0);
/* 222 */     sb.append(html);
/*     */     int i;
/* 224 */     for (i = 0; i < sb.length(); i++) {
/* 225 */       if (Character.isWhitespace(sb.charAt(i)))
/* 226 */         sb.setCharAt(i, ' '); 
/*     */     } 
/* 228 */     replaceAll(sb, "  ", " ");
/*     */     
/* 230 */     replaceAll(sb, "< ", "<");
/* 231 */     replaceAll(sb, " >", ">");
/*     */     
/* 233 */     for (i = 0; i < TAGS_TO_COMPACT.length; i += 3) {
/*     */       
/* 235 */       replaceAll(sb, TAGS_TO_COMPACT[i + 1], TAGS_TO_COMPACT[i]);
/* 236 */       replaceAll(sb, TAGS_TO_COMPACT[i + 2], TAGS_TO_COMPACT[i]);
/*     */     } 
/*     */     
/* 239 */     replaceAll(sb, "  ", " ");
/*     */ 
/*     */     
/* 242 */     int fromIndex = 0;
/* 243 */     int toIndex = sb.length();
/*     */     
/* 245 */     while (fromIndex < toIndex && sb.charAt(fromIndex) == ' ') {
/* 246 */       fromIndex++;
/*     */     }
/* 248 */     while (fromIndex < toIndex && sb.charAt(toIndex - 1) == ' ') {
/* 249 */       toIndex--;
/*     */     }
/* 251 */     return sb.substring(fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   private void replaceAll(StringBuilder sb, String pattern, String value) {
/* 256 */     for (int index = 0; (index = sb.indexOf(pattern, index)) != -1;) {
/* 257 */       sb.replace(index, index + pattern.length(), value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void reloadPath(File f) {
/* 262 */     parseDir(f);
/*     */     
/* 264 */     log.info("Cache[HTML]: Reloaded specified path.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseDir(File dir) {
/* 269 */     for (File file : dir.listFiles(HTML_FILTER)) {
/*     */       
/* 271 */       if (!file.isDirectory()) {
/* 272 */         loadFile(file);
/*     */       } else {
/* 274 */         parseDir(file);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String loadFile(File file) {
/* 280 */     if (isLoadable(file)) {
/*     */       
/* 282 */       BufferedInputStream bis = null;
/*     */       
/*     */       try {
/* 285 */         bis = new BufferedInputStream(new FileInputStream(file));
/* 286 */         byte[] raw = new byte[bis.available()];
/* 287 */         bis.read(raw);
/*     */         
/* 289 */         String content = new String(raw, HTMLConfig.HTML_ENCODING);
/* 290 */         String relpath = getRelativePath(HTML_ROOT, file);
/*     */         
/* 292 */         this.size += content.length();
/*     */         
/* 294 */         String oldContent = (String)this.cache.get(relpath);
/* 295 */         if (oldContent == null) {
/* 296 */           this.loadedFiles++;
/*     */         } else {
/* 298 */           this.size -= oldContent.length();
/*     */         } 
/* 300 */         this.cache.put(relpath, content);
/*     */         
/* 302 */         return content;
/*     */       }
/* 304 */       catch (Exception e) {
/*     */         
/* 306 */         log.warn("Problem with htm file:", e);
/*     */       }
/*     */       finally {
/*     */         
/* 310 */         IOUtils.closeQuietly(bis);
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHTML(String path) {
/* 319 */     return (String)this.cache.get(path);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isLoadable(File file) {
/* 324 */     return (file.exists() && !file.isDirectory() && HTML_FILTER.accept(file));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean pathExists(String path) {
/* 329 */     return this.cache.containsKey(path);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 335 */     return "Cache[HTML]: " + String.format("%.3f", new Object[] { Float.valueOf(this.size / 1024.0F) }) + " kilobytes on " + this.loadedFiles + " file(s) loaded.";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRelativePath(File base, File file) {
/* 341 */     return file.toURI().getPath().substring(base.toURI().getPath().length());
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\cache\HTMLCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
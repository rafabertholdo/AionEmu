package com.aionemu.gameserver.cache;

import com.aionemu.gameserver.configs.main.HTMLConfig;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javolution.util.FastMap;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
























public final class HTMLCache
{
  private static final Logger log = Logger.getLogger(HTMLCache.class);
  
  private static final FileFilter HTML_FILTER = new FileFilter()
    {
      
      public boolean accept(File file)
      {
        return (file.isDirectory() || file.getName().endsWith(".xhtml"));
      }
    };
  
  private static final File HTML_ROOT = new File(HTMLConfig.HTML_ROOT);
  
  private static final class SingletonHolder
  {
    private static final HTMLCache INSTANCE = new HTMLCache();
  }

  
  public static HTMLCache getInstance() {
    return SingletonHolder.INSTANCE;
  }
  
  private FastMap<String, String> cache = new FastMap(16000);
  
  private int loadedFiles;
  private int size;
  private static final String[] TAGS_TO_COMPACT;
  
  private HTMLCache() {
    reload(false);
  }


  
  public synchronized void reload(boolean deleteCacheFile) {
    this.cache.clear();
    this.loadedFiles = 0;
    this.size = 0;
    
    File cacheFile = getCacheFile();
    
    if (deleteCacheFile && cacheFile.exists()) {
      
      log.info("Cache[HTML]: Deleting cache file... OK.");
      
      cacheFile.delete();
    } 
    
    log.info("Cache[HTML]: Caching started... OK.");
    
    if (cacheFile.exists()) {
      
      log.info("Cache[HTML]: Using cache file... OK.");
      
      ObjectInputStream ois = null;
      
      try {
        ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(getCacheFile())));
        
        this.cache = (FastMap<String, String>)ois.readObject();
        
        for (String html : this.cache.values())
        {
          this.loadedFiles++;
          this.size += html.length();
        }
      
      } catch (Exception e) {
        
        log.warn("", e);
        
        reload(true);

        
        return;
      } finally {
        IOUtils.closeQuietly(ois);
      }
    
    } else {
      
      parseDir(HTML_ROOT);
    } 
    
    log.info(String.valueOf(this));
    
    if (cacheFile.exists()) {
      
      log.info("Cache[HTML]: Compaction skipped!");
    }
    else {
      
      log.info("Cache[HTML]: Compacting htmls... OK.");
      
      StringBuilder sb = new StringBuilder(8192);
      
      for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)this.cache.entrySet()) {

        
        try {
          String oldHtml = entry.getValue();
          String newHtml = compactHtml(sb, oldHtml);
          
          this.size -= oldHtml.length();
          this.size += newHtml.length();
          
          entry.setValue(newHtml);
        }
        catch (RuntimeException e) {
          
          log.warn("Cache[HTML]: Error during compaction of " + (String)entry.getKey(), e);
        } 
      } 
      
      log.info(String.valueOf(this));
    } 
    
    if (!cacheFile.exists()) {
      
      log.info("Cache[HTML]: Creating cache file... OK.");
      
      ObjectOutputStream oos = null;
      
      try {
        oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getCacheFile())));
        
        oos.writeObject(this.cache);
      }
      catch (IOException e) {
        
        log.warn("", e);
      }
      finally {
        
        IOUtils.closeQuietly(oos);
      } 
    } 
  }

  
  private File getCacheFile() {
    return new File(HTMLConfig.HTML_CACHE_FILE);
  }




  
  static {
    String[] tagsToCompact = { "html", "title", "body", "br", "br1", "p", "table", "tr", "td" };
    
    List<String> list = new ArrayList<String>();
    
    for (String tag : tagsToCompact) {
      
      list.add("<" + tag + ">");
      list.add("</" + tag + ">");
      list.add("<" + tag + "/>");
      list.add("<" + tag + " />");
    } 
    
    List<String> list2 = new ArrayList<String>();
    
    for (String tag : list) {
      
      list2.add(tag);
      list2.add(tag + " ");
      list2.add(" " + tag);
    } 
    
    TAGS_TO_COMPACT = list2.<String>toArray(new String[list.size()]);
  }

  
  private String compactHtml(StringBuilder sb, String html) {
    sb.setLength(0);
    sb.append(html);
    int i;
    for (i = 0; i < sb.length(); i++) {
      if (Character.isWhitespace(sb.charAt(i)))
        sb.setCharAt(i, ' '); 
    } 
    replaceAll(sb, "  ", " ");
    
    replaceAll(sb, "< ", "<");
    replaceAll(sb, " >", ">");
    
    for (i = 0; i < TAGS_TO_COMPACT.length; i += 3) {
      
      replaceAll(sb, TAGS_TO_COMPACT[i + 1], TAGS_TO_COMPACT[i]);
      replaceAll(sb, TAGS_TO_COMPACT[i + 2], TAGS_TO_COMPACT[i]);
    } 
    
    replaceAll(sb, "  ", " ");

    
    int fromIndex = 0;
    int toIndex = sb.length();
    
    while (fromIndex < toIndex && sb.charAt(fromIndex) == ' ') {
      fromIndex++;
    }
    while (fromIndex < toIndex && sb.charAt(toIndex - 1) == ' ') {
      toIndex--;
    }
    return sb.substring(fromIndex, toIndex);
  }

  
  private void replaceAll(StringBuilder sb, String pattern, String value) {
    for (int index = 0; (index = sb.indexOf(pattern, index)) != -1;) {
      sb.replace(index, index + pattern.length(), value);
    }
  }
  
  public void reloadPath(File f) {
    parseDir(f);
    
    log.info("Cache[HTML]: Reloaded specified path.");
  }

  
  public void parseDir(File dir) {
    for (File file : dir.listFiles(HTML_FILTER)) {
      
      if (!file.isDirectory()) {
        loadFile(file);
      } else {
        parseDir(file);
      } 
    } 
  }
  
  public String loadFile(File file) {
    if (isLoadable(file)) {
      
      BufferedInputStream bis = null;
      
      try {
        bis = new BufferedInputStream(new FileInputStream(file));
        byte[] raw = new byte[bis.available()];
        bis.read(raw);
        
        String content = new String(raw, HTMLConfig.HTML_ENCODING);
        String relpath = getRelativePath(HTML_ROOT, file);
        
        this.size += content.length();
        
        String oldContent = (String)this.cache.get(relpath);
        if (oldContent == null) {
          this.loadedFiles++;
        } else {
          this.size -= oldContent.length();
        } 
        this.cache.put(relpath, content);
        
        return content;
      }
      catch (Exception e) {
        
        log.warn("Problem with htm file:", e);
      }
      finally {
        
        IOUtils.closeQuietly(bis);
      } 
    } 
    
    return null;
  }

  
  public String getHTML(String path) {
    return (String)this.cache.get(path);
  }

  
  private boolean isLoadable(File file) {
    return (file.exists() && !file.isDirectory() && HTML_FILTER.accept(file));
  }

  
  public boolean pathExists(String path) {
    return this.cache.containsKey(path);
  }


  
  public String toString() {
    return "Cache[HTML]: " + String.format("%.3f", new Object[] { Float.valueOf(this.size / 1024.0F) }) + " kilobytes on " + this.loadedFiles + " file(s) loaded.";
  }


  
  public static String getRelativePath(File base, File file) {
    return file.toURI().getPath().substring(base.toURI().getPath().length());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\cache\HTMLCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

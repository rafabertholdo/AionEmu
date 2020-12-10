package com.aionemu.commons.services;

import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javolution.util.FastMap;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class ScriptService {
  private static final Logger log = Logger.getLogger(ScriptService.class);

  private final Map<File, ScriptManager> map = (Map<File, ScriptManager>) (new FastMap()).shared();

  public void load(String file) throws RuntimeException {
    load(new File(file));
  }

  public void load(File file) throws RuntimeException {
    if (file.isFile()) {

      loadFile(file);
    } else if (file.isDirectory()) {

      loadDir(file);
    }
  }

  private void loadFile(File file) {
    if (this.map.containsKey(file)) {
      throw new IllegalArgumentException("ScriptManager by file:" + file + " already loaded");
    }

    ScriptManager sm = new ScriptManager();

    try {
      sm.load(file);
    } catch (Exception e) {

      log.error(e);
      throw new RuntimeException(e);
    }

    this.map.put(file, sm);
  }

  private void loadDir(File dir) {
    for (Object file : FileUtils.listFiles(dir, new String[] { "xml" }, false)) {
      loadFile((File) file);
    }
  }

  public void unload(File file) throws IllegalArgumentException {
    ScriptManager sm = this.map.remove(file);
    if (sm == null) {
      throw new IllegalArgumentException("ScriptManager by file " + file + " is not loaded.");
    }

    sm.shutdown();
  }

  public void reload(File file) throws IllegalArgumentException {
    ScriptManager sm = this.map.get(file);
    if (sm == null) {
      throw new IllegalArgumentException("ScriptManager by file " + file + " is not loaded.");
    }

    sm.reload();
  }

  public void addScriptManager(ScriptManager scriptManager, File file) {
    if (this.map.containsKey(file)) {
      throw new IllegalArgumentException("ScriptManager by file " + file + " is already loaded.");
    }

    this.map.put(file, scriptManager);
  }

  public Map<File, ScriptManager> getLoadedScriptManagers() {
    return Collections.unmodifiableMap(this.map);
  }

  public void shutdown() {
    for (Iterator<Map.Entry<File, ScriptManager>> it = this.map.entrySet().iterator(); it.hasNext();) {

      try {
        ((ScriptManager) ((Map.Entry) it.next()).getValue()).shutdown();
      } catch (Exception e) {

        log.warn("An exception occured during shudown procedure.", e);
      }

      it.remove();
    }
  }
}

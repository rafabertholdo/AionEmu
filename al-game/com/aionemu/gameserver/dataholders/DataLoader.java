package com.aionemu.gameserver.dataholders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.log4j.Logger;

abstract class DataLoader {
  protected Logger log = Logger.getLogger(getClass().getName());

  private static final String PATH = "./data/static_data/";

  private File dataFile;

  DataLoader(String file) {
    this.dataFile = new File("./data/static_data/" + file);
  }

  protected void loadData() {
    if (this.dataFile.isDirectory()) {

      Collection<?> files = FileUtils.listFiles(this.dataFile,
          FileFilterUtils.andFileFilter(
              FileFilterUtils.andFileFilter(FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter("new")),
                  FileFilterUtils.suffixFileFilter(".txt")),
              HiddenFileFilter.VISIBLE),
          HiddenFileFilter.VISIBLE);

      for (Object file1 : files) {
        File f = (File) file1;
        loadFile(f);
      }

    } else {

      loadFile(this.dataFile);
    }
  }

  private void loadFile(File file) {
    LineIterator it = null;

    try {
      it = FileUtils.lineIterator(file);
      while (it.hasNext()) {
        String line = it.nextLine();
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        parse(line);
      }

    } catch (IOException e) {

      this.log.error("Error while loading " + getClass().getSimpleName() + ", file: " + file.getPath(), e);
    } finally {

      LineIterator.closeQuietly(it);
    }
  }

  protected abstract void parse(String paramString);

  public boolean saveData() {
    String desc = "./data/static_data/" + getSaveFile();

    this.log.info("Saving " + desc);

    FileWriter fr = null;

    try {
      fr = new FileWriter(desc);

      saveEntries(fr);

      fr.flush();

      return true;
    } catch (Exception e) {

      this.log.fatal("Error while saving " + desc, e);
      return false;
    } finally {

      IOUtils.closeQuietly(fr);
    }
  }

  protected abstract String getSaveFile();

  protected void saveEntries(FileWriter fileWriter) throws Exception {
  }
}

package com.aionemu.commons.versionning;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.apache.log4j.Logger;

public class Version {
  private static final Logger log = Logger.getLogger(Version.class);

  private String version;
  private String revision;
  private long date = -1L;

  public Version() {
  }

  public Version(Class<?> c) {
    loadInformation(c);
  }

  public void loadInformation(Class<?> c) {
    File jarName = null;

    try {
      jarName = Locator.getClassSource(c);
      JarFile jarFile = new JarFile(jarName);

      Attributes attrs = jarFile.getManifest().getMainAttributes();

      setVersion(attrs);

      setRevision(attrs);

      setDate(attrs);
    } catch (IOException e) {

      log.error("Unable to get Soft information\nFile name '" + ((jarName == null) ? "null" : jarName.getAbsolutePath())
          + "' isn't a valid jar", e);
    }
  }

  public void transferInfo(String jarName, String type, File fileToWrite) {
    try {
      if (!fileToWrite.exists()) {

        log.error("Unable to Find File :" + fileToWrite.getName() + " Please Update your " + type);

        return;
      }
      JarFile jarFile = new JarFile("./" + jarName);

      Manifest manifest = jarFile.getManifest();

      OutputStream fos = new FileOutputStream(fileToWrite);
      manifest.write(fos);
      fos.close();
    } catch (IOException e) {

      log.error("Error, " + e);
    }
  }

  public void setVersion(Attributes attrs) {
    String version = attrs.getValue("Implementation-Version");

    if (version != null) {
      this.version = version;
    } else {
      this.version = "-1";
    }
  }

  public String getVersion() {
    return this.version;
  }

  public void setRevision(Attributes attrs) {
    String revision = attrs.getValue("Implementation-Build");

    if (revision != null) {
      this.revision = revision;
    } else {
      this.revision = "-1";
    }
  }

  public String getRevision() {
    return this.revision;
  }

  public void setDate(Attributes attrs) {
    String buildTime = attrs.getValue("Implementation-Time");

    if (buildTime != null) {
      this.date = Long.parseLong(buildTime);
    } else {
      this.date = -1L;
    }
  }

  public long getDate() {
    return this.date;
  }
}

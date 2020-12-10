package com.aionemu.commons.scripting.scriptmanager;

import com.aionemu.commons.scripting.impl.javacompiler.ScriptCompilerImpl;
import java.io.File;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javolution.text.TextBuilder;

@XmlRootElement(name = "scriptinfo")
@XmlAccessorType(XmlAccessType.NONE)
public class ScriptInfo {
  @XmlAttribute(required = true)
  private File root;
  @XmlElement(name = "library")
  private List<File> libraries;
  @XmlElement(name = "scriptinfo")
  private List<ScriptInfo> scriptInfos;
  @XmlElement(name = "compiler")
  private String compilerClass = ScriptCompilerImpl.class.getName();

  public File getRoot() {
    return this.root;
  }

  public void setRoot(File root) {
    this.root = root;
  }

  public List<File> getLibraries() {
    return this.libraries;
  }

  public void setLibraries(List<File> libraries) {
    this.libraries = libraries;
  }

  public List<ScriptInfo> getScriptInfos() {
    return this.scriptInfos;
  }

  public void setScriptInfos(List<ScriptInfo> scriptInfos) {
    this.scriptInfos = scriptInfos;
  }

  public String getCompilerClass() {
    return this.compilerClass;
  }

  public void setCompilerClass(String compilerClass) {
    this.compilerClass = compilerClass;
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScriptInfo that = (ScriptInfo) o;

    return this.root.equals(that.root);
  }

  public int hashCode() {
    return this.root.hashCode();
  }

  public String toString() {
    TextBuilder tb = TextBuilder.newInstance();

    tb.append("ScriptInfo");
    tb.append("{root=").append(this.root);
    tb.append(", libraries=").append(this.libraries);
    tb.append(", compilerClass='").append(this.compilerClass).append('\'');
    tb.append(", scriptInfos=").append(this.scriptInfos);
    tb.append('}');

    String toString = tb.toString();

    TextBuilder.recycle(tb);

    return toString;
  }
}

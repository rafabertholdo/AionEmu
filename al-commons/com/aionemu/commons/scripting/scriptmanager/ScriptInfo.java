/*     */ package com.aionemu.commons.scripting.scriptmanager;
/*     */ 
/*     */ import com.aionemu.commons.scripting.impl.javacompiler.ScriptCompilerImpl;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javolution.text.TextBuilder;
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
/*     */ @XmlRootElement(name = "scriptinfo")
/*     */ @XmlAccessorType(XmlAccessType.NONE)
/*     */ public class ScriptInfo
/*     */ {
/*     */   @XmlAttribute(required = true)
/*     */   private File root;
/*     */   @XmlElement(name = "library")
/*     */   private List<File> libraries;
/*     */   @XmlElement(name = "scriptinfo")
/*     */   private List<ScriptInfo> scriptInfos;
/*     */   @XmlElement(name = "compiler")
/*  65 */   private String compilerClass = ScriptCompilerImpl.class.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getRoot() {
/*  75 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoot(File root) {
/*  86 */     this.root = root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<File> getLibraries() {
/*  96 */     return this.libraries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLibraries(List<File> libraries) {
/* 107 */     this.libraries = libraries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ScriptInfo> getScriptInfos() {
/* 117 */     return this.scriptInfos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScriptInfos(List<ScriptInfo> scriptInfos) {
/* 128 */     this.scriptInfos = scriptInfos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompilerClass() {
/* 138 */     return this.compilerClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompilerClass(String compilerClass) {
/* 149 */     this.compilerClass = compilerClass;
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
/*     */   public boolean equals(Object o) {
/* 162 */     if (this == o)
/* 163 */       return true; 
/* 164 */     if (o == null || getClass() != o.getClass()) {
/* 165 */       return false;
/*     */     }
/* 167 */     ScriptInfo that = (ScriptInfo)o;
/*     */     
/* 169 */     return this.root.equals(that.root);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 180 */     return this.root.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 186 */     TextBuilder tb = TextBuilder.newInstance();
/*     */     
/* 188 */     tb.append("ScriptInfo");
/* 189 */     tb.append("{root=").append(this.root);
/* 190 */     tb.append(", libraries=").append(this.libraries);
/* 191 */     tb.append(", compilerClass='").append(this.compilerClass).append('\'');
/* 192 */     tb.append(", scriptInfos=").append(this.scriptInfos);
/* 193 */     tb.append('}');
/*     */     
/* 195 */     String toString = tb.toString();
/*     */     
/* 197 */     TextBuilder.recycle(tb);
/*     */     
/* 199 */     return toString;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\scriptmanager\ScriptInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
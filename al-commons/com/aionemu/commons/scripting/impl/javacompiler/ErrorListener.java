/*    */ package com.aionemu.commons.scripting.impl.javacompiler;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import javax.tools.Diagnostic;
/*    */ import javax.tools.DiagnosticListener;
/*    */ import javax.tools.JavaFileObject;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ErrorListener
/*    */   implements DiagnosticListener<JavaFileObject>
/*    */ {
/* 37 */   private static final Logger log = Logger.getLogger(ErrorListener.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
/* 48 */     StringBuffer buffer = new StringBuffer();
/* 49 */     buffer.append("Java Compiler ").append(diagnostic.getKind()).append(": ").append(diagnostic.getMessage(Locale.ENGLISH)).append("\n").append("Source: ").append(((JavaFileObject)diagnostic.getSource()).getName()).append("\n").append("Line: ").append(diagnostic.getLineNumber()).append("\n").append("Column: ").append(diagnostic.getColumnNumber());
/*    */ 
/*    */ 
/*    */     
/* 53 */     log.error(buffer.toString());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\ErrorListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
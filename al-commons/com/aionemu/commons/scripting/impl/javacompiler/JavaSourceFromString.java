/*    */ package com.aionemu.commons.scripting.impl.javacompiler;
/*    */ 
/*    */ import java.net.URI;
/*    */ import javax.tools.JavaFileObject;
/*    */ import javax.tools.SimpleJavaFileObject;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JavaSourceFromString
/*    */   extends SimpleJavaFileObject
/*    */ {
/*    */   private final String code;
/*    */   
/*    */   public JavaSourceFromString(String className, String code) {
/* 46 */     super(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
/*    */     
/* 48 */     this.code = code;
/*    */   }
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
/*    */   public CharSequence getCharContent(boolean ignoreEncodingErrors) {
/* 61 */     return this.code;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\impl\javacompiler\JavaSourceFromString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
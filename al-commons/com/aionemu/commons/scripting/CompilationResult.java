/*    */ package com.aionemu.commons.scripting;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javolution.text.TextBuilder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompilationResult
/*    */ {
/*    */   private final Class[] compiledClasses;
/*    */   private final ScriptClassLoader classLoader;
/*    */   
/*    */   public CompilationResult(Class[] compiledClasses, ScriptClassLoader classLoader) {
/* 53 */     this.compiledClasses = compiledClasses;
/* 54 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ScriptClassLoader getClassLoader() {
/* 64 */     return this.classLoader;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class[] getCompiledClasses() {
/* 75 */     return this.compiledClasses;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     TextBuilder tb = TextBuilder.newInstance();
/* 83 */     tb.append("CompilationResult");
/* 84 */     tb.append("{classLoader=").append(this.classLoader);
/* 85 */     tb.append(", compiledClasses=").append((this.compiledClasses == null) ? "null" : Arrays.<Class<?>[]>asList((Class<?>[][])this.compiledClasses).toString());
/*    */     
/* 87 */     tb.append('}');
/*    */     
/* 89 */     String toString = tb.toString();
/*    */     
/* 91 */     TextBuilder.recycle(tb);
/*    */     
/* 93 */     return toString;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\CompilationResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
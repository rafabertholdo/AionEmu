/*    */ package com.aionemu.commons.scripting;
/*    */ 
/*    */ import com.aionemu.commons.scripting.impl.ScriptContextImpl;
/*    */ import java.io.File;
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
/*    */ public final class ScriptContextFactory
/*    */ {
/*    */   public static ScriptContext getScriptContext(File root, ScriptContext parent) throws InstantiationException {
/*    */     ScriptContextImpl ctx;
/* 45 */     if (parent == null) {
/*    */       
/* 47 */       ctx = new ScriptContextImpl(root);
/*    */     }
/*    */     else {
/*    */       
/* 51 */       ctx = new ScriptContextImpl(root, parent);
/* 52 */       parent.addChildScriptContext((ScriptContext)ctx);
/*    */     } 
/* 54 */     return (ScriptContext)ctx;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\ScriptContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
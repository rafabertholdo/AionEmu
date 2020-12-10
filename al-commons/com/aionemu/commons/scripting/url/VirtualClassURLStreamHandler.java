/*    */ package com.aionemu.commons.scripting.url;
/*    */ 
/*    */ import com.aionemu.commons.scripting.ScriptClassLoader;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
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
/*    */ public class VirtualClassURLStreamHandler
/*    */   extends URLStreamHandler
/*    */ {
/*    */   public static final String HANDLER_PROTOCOL = "aescript://";
/*    */   private final ScriptClassLoader cl;
/*    */   
/*    */   public VirtualClassURLStreamHandler(ScriptClassLoader cl) {
/* 51 */     this.cl = cl;
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
/*    */ 
/*    */   
/*    */   protected URLConnection openConnection(URL u) throws IOException {
/* 66 */     return new VirtualClassURLConnection(u, this.cl);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scriptin\\url\VirtualClassURLStreamHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
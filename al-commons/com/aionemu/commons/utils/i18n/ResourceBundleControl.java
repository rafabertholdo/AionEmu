/*     */ package com.aionemu.commons.utils.i18n;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Locale;
/*     */ import java.util.PropertyResourceBundle;
/*     */ import java.util.ResourceBundle;
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
/*     */ 
/*     */ 
/*     */ public class ResourceBundleControl
/*     */   extends ResourceBundle.Control
/*     */ {
/*  61 */   private String encoding = "UTF-8";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundleControl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundleControl(String encoding) {
/*  78 */     this.encoding = encoding;
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
/*     */   
/*     */   public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
/*  92 */     String bundleName = toBundleName(baseName, locale);
/*  93 */     ResourceBundle bundle = null;
/*  94 */     if (format.equals("java.class")) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/*  99 */         Class<? extends ResourceBundle> bundleClass = (Class)loader.loadClass(bundleName);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 104 */         if (ResourceBundle.class.isAssignableFrom(bundleClass))
/*     */         {
/* 106 */           bundle = bundleClass.newInstance();
/*     */         }
/*     */         else
/*     */         {
/* 110 */           throw new ClassCastException(bundleClass.getName() + " cannot be cast to ResourceBundle");
/*     */         }
/*     */       
/* 113 */       } catch (ClassNotFoundException ignored) {}
/*     */ 
/*     */     
/*     */     }
/* 117 */     else if (format.equals("java.properties")) {
/*     */       
/* 119 */       final String resourceName = toResourceName(bundleName, "properties");
/* 120 */       final ClassLoader classLoader = loader;
/* 121 */       final boolean reloadFlag = reload;
/* 122 */       InputStreamReader isr = null;
/*     */ 
/*     */       
/*     */       try {
/* 126 */         InputStream stream = AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*     */             {
/*     */               public InputStream run() throws IOException
/*     */               {
/* 130 */                 InputStream is = null;
/* 131 */                 if (reloadFlag) {
/*     */                   
/* 133 */                   URL url = classLoader.getResource(resourceName);
/* 134 */                   if (url != null) {
/*     */                     
/* 136 */                     URLConnection connection = url.openConnection();
/* 137 */                     if (connection != null)
/*     */                     {
/*     */ 
/*     */                       
/* 141 */                       connection.setUseCaches(false);
/* 142 */                       is = connection.getInputStream();
/*     */                     }
/*     */                   
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 148 */                   is = classLoader.getResourceAsStream(resourceName);
/*     */                 } 
/* 150 */                 return is;
/*     */               }
/*     */             });
/* 153 */         if (stream != null)
/*     */         {
/* 155 */           isr = new InputStreamReader(stream, this.encoding);
/*     */         }
/*     */       }
/* 158 */       catch (PrivilegedActionException e) {
/*     */         
/* 160 */         throw (IOException)e.getException();
/*     */       } 
/* 162 */       if (isr != null) {
/*     */         try
/*     */         {
/*     */           
/* 166 */           bundle = new PropertyResourceBundle(isr);
/*     */         }
/*     */         finally
/*     */         {
/* 170 */           isr.close();
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 176 */       throw new IllegalArgumentException("unknown format: " + format);
/*     */     } 
/* 178 */     return bundle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 188 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncoding(String encoding) {
/* 199 */     this.encoding = encoding;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\i18n\ResourceBundleControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.utils.chathandlers;
/*    */ 
/*    */ import com.aionemu.commons.scripting.classlistener.ClassListener;
/*    */ import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
/*    */ import com.aionemu.commons.utils.ClassUtils;
/*    */ import java.lang.reflect.Modifier;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ChatHandlersLoader
/*    */   extends DefaultClassListener
/*    */   implements ClassListener
/*    */ {
/* 20 */   private static final Logger logger = Logger.getLogger(ChatHandlersLoader.class);
/*    */   
/*    */   private final AdminCommandChatHandler adminCCH;
/*    */ 
/*    */   
/*    */   public ChatHandlersLoader(AdminCommandChatHandler handler) {
/* 26 */     this.adminCCH = handler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postLoad(Class<?>[] classes) {
/* 33 */     for (Class<?> c : classes) {
/*    */       
/* 35 */       if (logger.isDebugEnabled()) {
/* 36 */         logger.debug("Load class " + c.getName());
/*    */       }
/* 38 */       if (isValidClass(c))
/*    */       {
/*    */         
/* 41 */         if (ClassUtils.isSubclass(c, AdminCommand.class)) {
/*    */           
/* 43 */           Class<? extends AdminCommand> tmp = (Class)c;
/* 44 */           if (tmp != null) {
/*    */             
/*    */             try {
/* 47 */               this.adminCCH.registerAdminCommand(tmp.newInstance());
/*    */             }
/* 49 */             catch (InstantiationException e) {
/*    */ 
/*    */               
/* 52 */               e.printStackTrace();
/*    */             }
/* 54 */             catch (IllegalAccessException e) {
/*    */ 
/*    */               
/* 57 */               e.printStackTrace();
/*    */             } 
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/* 63 */     super.postLoad(classes);
/*    */     
/* 65 */     logger.info("Loaded " + this.adminCCH.getSize() + " admin command handlers.");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void preUnload(Class<?>[] classes) {
/* 71 */     if (logger.isDebugEnabled()) {
/* 72 */       for (Class<?> c : classes) {
/* 73 */         logger.debug("Unload class " + c.getName());
/*    */       }
/*    */     }
/* 76 */     super.preUnload(classes);
/*    */     
/* 78 */     this.adminCCH.clearHandlers();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidClass(Class<?> clazz) {
/* 83 */     int modifiers = clazz.getModifiers();
/*    */     
/* 85 */     if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
/* 86 */       return false;
/*    */     }
/* 88 */     if (!Modifier.isPublic(modifiers)) {
/* 89 */       return false;
/*    */     }
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\ChatHandlersLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
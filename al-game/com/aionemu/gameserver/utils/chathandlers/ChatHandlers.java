/*    */ package com.aionemu.gameserver.utils.chathandlers;
/*    */ 
/*    */ import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
/*    */ import com.aionemu.gameserver.GameServerError;
/*    */ import java.io.File;
/*    */ import javolution.util.FastList;
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
/*    */ public class ChatHandlers
/*    */ {
/*    */   private FastList<ChatHandler> handlers;
/* 38 */   public static final File CHAT_DESCRIPTOR_FILE = new File("./data/scripts/system/handlers.xml");
/*    */   
/*    */   private ScriptManager sm;
/*    */ 
/*    */   
/*    */   public static final ChatHandlers getInstance() {
/* 44 */     return SingletonHolder.instance;
/*    */   }
/*    */ 
/*    */   
/*    */   private ChatHandlers() {
/* 49 */     this.handlers = new FastList();
/* 50 */     this.sm = new ScriptManager();
/* 51 */     createChatHandlers();
/*    */   }
/*    */ 
/*    */   
/*    */   void addChatHandler(ChatHandler ch) {
/* 56 */     this.handlers.add(ch);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FastList<ChatHandler> getHandlers() {
/* 63 */     return this.handlers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void createChatHandlers() {
/* 73 */     AdminCommandChatHandler adminCCH = new AdminCommandChatHandler();
/* 74 */     addChatHandler(adminCCH);
/*    */ 
/*    */     
/* 77 */     this.sm.setGlobalClassListener(new ChatHandlersLoader(adminCCH));
/*    */ 
/*    */     
/*    */     try {
/* 81 */       this.sm.load(CHAT_DESCRIPTOR_FILE);
/*    */     }
/* 83 */     catch (Exception e) {
/*    */       
/* 85 */       throw new GameServerError("Can't initialize chat handlers.", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static class SingletonHolder
/*    */   {
/* 92 */     protected static final ChatHandlers instance = new ChatHandlers();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\ChatHandlers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
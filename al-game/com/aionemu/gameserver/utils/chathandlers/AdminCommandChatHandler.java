/*     */ package com.aionemu.gameserver.utils.chathandlers;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class AdminCommandChatHandler
/*     */   implements ChatHandler
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(AdminCommandChatHandler.class);
/*     */   
/*  42 */   private Map<String, AdminCommand> commands = (Map<String, AdminCommand>)new FastMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerAdminCommand(AdminCommand command) {
/*  51 */     if (command == null) {
/*  52 */       throw new NullPointerException("Command instance cannot be null");
/*     */     }
/*  54 */     String commandName = command.getCommandName();
/*     */     
/*  56 */     AdminCommand old = this.commands.put(commandName, command);
/*  57 */     if (old != null)
/*     */     {
/*  59 */       log.warn("Overriding handler for command " + commandName + " from " + old.getClass().getName() + " to " + command.getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatHandlerResponse handleChatMessage(ChatType chatType, String message, Player sender) {
/*  67 */     if (!message.startsWith("//"))
/*     */     {
/*  69 */       return new ChatHandlerResponse(false, message);
/*     */     }
/*     */ 
/*     */     
/*  73 */     String[] commandAndParams = message.split(" ", 2);
/*     */     
/*  75 */     String command = commandAndParams[0].substring(2);
/*  76 */     AdminCommand admc = this.commands.get(command);
/*     */     
/*  78 */     if (OptionsConfig.LOG_GMAUDIT) {
/*     */       
/*  80 */       if (sender.getAccessLevel() == 0) {
/*  81 */         log.info("[ADMIN COMMAND] > [Name: " + sender.getName() + "]: The player has tried to use the command without have the rights :");
/*     */       }
/*  83 */       if (sender.getTarget() != null && sender.getTarget() instanceof Creature) {
/*     */         
/*  85 */         Creature target = (Creature)sender.getTarget();
/*     */         
/*  87 */         log.info("[ADMIN COMMAND] > [Name: " + sender.getName() + "][Target : " + target.getName() + "]: " + message);
/*     */       } else {
/*     */         
/*  90 */         log.info("[ADMIN COMMAND] > [Name: " + sender.getName() + "]: " + message);
/*     */       } 
/*     */     } 
/*  93 */     if (admc == null) {
/*     */       
/*  95 */       PacketSendUtility.sendMessage(sender, "<There is no such admin command: " + command + ">");
/*  96 */       return ChatHandlerResponse.BLOCKED_MESSAGE;
/*     */     } 
/*     */     
/*  99 */     String[] params = new String[0];
/* 100 */     if (commandAndParams.length > 1) {
/* 101 */       params = commandAndParams[1].split(" ", admc.getSplitSize());
/*     */     }
/* 103 */     admc.executeCommand(sender, params);
/* 104 */     return ChatHandlerResponse.BLOCKED_MESSAGE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clearHandlers() {
/* 113 */     this.commands.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 122 */     return this.commands.size();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\AdminCommandChatHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
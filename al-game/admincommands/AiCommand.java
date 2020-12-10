/*    */ package admincommands;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.administration.AdminConfig;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
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
/*    */ public class AiCommand
/*    */   extends AdminCommand
/*    */ {
/*    */   public AiCommand() {
/* 34 */     super("ai");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void executeCommand(Player admin, String[] params) {
/* 40 */     if (admin.getAccessLevel() < AdminConfig.COMMAND_AI) {
/*    */       
/* 42 */       PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     if (params == null || params.length < 1) {
/*    */       
/* 48 */       PacketSendUtility.sendMessage(admin, "syntax //ai <info|event|state>");
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     VisibleObject target = admin.getTarget();
/*    */     
/* 54 */     if (target == null || !(target instanceof Npc)) {
/*    */       
/* 56 */       PacketSendUtility.sendMessage(admin, "Select target first (Npc only)");
/*    */       
/*    */       return;
/*    */     } 
/* 60 */     Npc npc = (Npc)target;
/*    */     
/* 62 */     if (params[0].equals("info")) {
/*    */       
/* 64 */       PacketSendUtility.sendMessage(admin, "Ai state: " + npc.getAi().getAiState());
/* 65 */       PacketSendUtility.sendMessage(admin, "Ai desires size: " + npc.getAi().desireQueueSize());
/* 66 */       PacketSendUtility.sendMessage(admin, "Ai task scheduled: " + npc.getAi().isScheduled());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\AiCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
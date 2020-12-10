/*    */ package admincommands;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.administration.AdminConfig;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ public class Announce
/*    */   extends AdminCommand
/*    */ {
/*    */   public Announce() {
/* 38 */     super("announce");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSplitSize() {
/* 44 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void executeCommand(Player admin, String[] params) {
/* 51 */     if (admin.getAccessLevel() < AdminConfig.COMMAND_ANNOUNCE) {
/*    */       
/* 53 */       PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     if (params == null || params.length != 2) {
/*    */       
/* 59 */       PacketSendUtility.sendMessage(admin, "Syntax: //announce <anonymous|name> <message>");
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 65 */     if ("anonymous".startsWith(params[0].toLowerCase())) {
/*    */       
/* 67 */       message = "Announce: ";
/*    */     }
/* 69 */     else if ("name".startsWith(params[0].toLowerCase())) {
/*    */       
/* 71 */       message = admin.getName() + ": ";
/*    */     }
/*    */     else {
/*    */       
/* 75 */       PacketSendUtility.sendMessage(admin, "Syntax: //announce <anonymous|name> <message>");
/*    */       return;
/*    */     } 
/* 78 */     String message = message + params[1];
/*    */     
/* 80 */     for (Player player : World.getInstance().getAllPlayers())
/*    */     {
/* 82 */       PacketSendUtility.sendSysMessage(player, message);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\Announce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
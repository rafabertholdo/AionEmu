/*    */ package admincommands;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.administration.AdminConfig;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.Util;
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
/*    */ public class AddTitle
/*    */   extends AdminCommand
/*    */ {
/*    */   public AddTitle() {
/* 36 */     super("addtitle");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void executeCommand(Player admin, String[] params) {
/* 42 */     if (admin.getAccessLevel() < AdminConfig.COMMAND_ADDTITLE) {
/*    */       
/* 44 */       PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     if (params.length < 1 || params.length > 2) {
/*    */       
/* 50 */       PacketSendUtility.sendMessage(admin, "syntax //addtitle title_id [playerName]");
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     int titleId = Integer.parseInt(params[0]);
/* 55 */     if (titleId > 50 || titleId < 1) {
/*    */       
/* 57 */       PacketSendUtility.sendMessage(admin, "title id " + titleId + " is invalid (must be between 1 and 50)");
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     Player target = null;
/* 62 */     if (params.length == 2) {
/*    */       
/* 64 */       target = World.getInstance().findPlayer(Util.convertName(params[1]));
/* 65 */       if (target == null) {
/*    */         
/* 67 */         PacketSendUtility.sendMessage(admin, "player " + params[1] + " was not found");
/*    */ 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } else {
/* 73 */       VisibleObject o = admin.getTarget();
/* 74 */       if (!(o instanceof Player) || o == null)
/*    */       {
/* 76 */         target = admin;
/*    */       }
/*    */     } 
/*    */     
/* 80 */     titleId = target.getCommonData().getRace().getRaceId() * 50 + titleId;
/* 81 */     if (!target.getTitleList().addTitle(titleId)) {
/*    */       
/* 83 */       PacketSendUtility.sendMessage(admin, "you can't add title #" + titleId + " to " + (target.equals(admin) ? "yourself" : target.getName()));
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 88 */     else if (target.equals(admin)) {
/*    */       
/* 90 */       PacketSendUtility.sendMessage(admin, "you added to yourself title #" + titleId);
/*    */     }
/*    */     else {
/*    */       
/* 94 */       PacketSendUtility.sendMessage(admin, "you added to " + target.getName() + " title #" + titleId);
/* 95 */       PacketSendUtility.sendMessage(target, admin.getName() + " gave you title #" + titleId);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\AddTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
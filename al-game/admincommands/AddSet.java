/*     */ package admincommands;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.administration.AdminConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.itemset.ItemPart;
/*     */ import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.Util;
/*     */ import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ public class AddSet
/*     */   extends AdminCommand
/*     */ {
/*     */   public AddSet() {
/*  40 */     super("addset");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeCommand(Player admin, String[] params) {
/*  46 */     if (admin.getAccessLevel() < AdminConfig.COMMAND_ADDSET) {
/*     */       
/*  48 */       PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
/*     */       
/*     */       return;
/*     */     } 
/*  52 */     if (params.length == 0 || params.length > 2) {
/*     */       
/*  54 */       PacketSendUtility.sendMessage(admin, "syntax //addset <player> <itemset ID>");
/*  55 */       PacketSendUtility.sendMessage(admin, "syntax //addset <itemset ID>");
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     int itemSetId = 0;
/*  60 */     Player receiver = null;
/*     */ 
/*     */     
/*     */     try {
/*  64 */       itemSetId = Integer.parseInt(params[0]);
/*     */       
/*  66 */       receiver = admin;
/*     */     }
/*  68 */     catch (NumberFormatException e) {
/*     */       
/*  70 */       receiver = World.getInstance().findPlayer(Util.convertName(params[0]));
/*     */       
/*  72 */       if (receiver == null) {
/*     */         
/*  74 */         PacketSendUtility.sendMessage(admin, "Could not find a player by that name.");
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/*  80 */         itemSetId = Integer.parseInt(params[1]);
/*     */       }
/*  82 */       catch (NumberFormatException ex) {
/*     */ 
/*     */         
/*  85 */         PacketSendUtility.sendMessage(admin, "You must give number to itemset ID.");
/*     */         
/*     */         return;
/*  88 */       } catch (Exception ex2) {
/*     */         
/*  90 */         PacketSendUtility.sendMessage(admin, "Occurs an error.");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  95 */     ItemSetTemplate itemSet = DataManager.ITEM_SET_DATA.getItemSetTemplate(itemSetId);
/*  96 */     if (itemSet == null) {
/*     */       
/*  98 */       PacketSendUtility.sendMessage(admin, "ItemSet does not exist with id " + itemSetId);
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     if (receiver.getInventory().getNumberOfFreeSlots() < itemSet.getItempart().size()) {
/*     */       
/* 104 */       PacketSendUtility.sendMessage(admin, "Inventory needs at least " + itemSet.getItempart().size() + " free slots.");
/*     */       
/*     */       return;
/*     */     } 
/* 108 */     for (ItemPart setPart : itemSet.getItempart()) {
/*     */       
/* 110 */       if (!ItemService.addItem(receiver, setPart.getItemid(), 1L)) {
/*     */         
/* 112 */         PacketSendUtility.sendMessage(admin, "Item " + setPart.getItemid() + " couldn't be added");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 117 */     PacketSendUtility.sendMessage(admin, "Item Set added successfully");
/* 118 */     PacketSendUtility.sendMessage(receiver, "Admin gives you an item set");
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\AddSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
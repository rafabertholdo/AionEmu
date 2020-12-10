/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.cache.HTMLCache;
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.configs.main.GSConfig;
/*     */ import com.aionemu.gameserver.model.account.Account;
/*     */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CHANNEL_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ENTER_WORLD_CHECK;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GAME_TIME;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_INFLUENCE_RATIO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_COOLDOWN;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MACRO_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_ID;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SPAWN;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PRICES;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_RECIPE_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SIEGE_LOCATION_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UI_SETTINGS;
/*     */ import com.aionemu.gameserver.services.AllianceService;
/*     */ import com.aionemu.gameserver.services.BrokerService;
/*     */ import com.aionemu.gameserver.services.ChatService;
/*     */ import com.aionemu.gameserver.services.ClassChangeService;
/*     */ import com.aionemu.gameserver.services.GroupService;
/*     */ import com.aionemu.gameserver.services.HTMLService;
/*     */ import com.aionemu.gameserver.services.KiskService;
/*     */ import com.aionemu.gameserver.services.LegionService;
/*     */ import com.aionemu.gameserver.services.MailService;
/*     */ import com.aionemu.gameserver.services.PetitionService;
/*     */ import com.aionemu.gameserver.services.PlayerService;
/*     */ import com.aionemu.gameserver.services.PunishmentService;
/*     */ import com.aionemu.gameserver.services.StigmaService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.VersionningService;
/*     */ import com.aionemu.gameserver.utils.rates.Rates;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.List;
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
/*     */ public class CM_ENTER_WORLD
/*     */   extends AionClientPacket
/*     */ {
/*     */   private int objectId;
/*     */   
/*     */   public CM_ENTER_WORLD(int opcode) {
/*  91 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/* 100 */     this.objectId = readD();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/* 109 */     AionConnection client = (AionConnection)getConnection();
/* 110 */     Account account = client.getAccount();
/* 111 */     PlayerAccountData playerAccData = client.getAccount().getPlayerAccountData(this.objectId);
/*     */     
/* 113 */     if (playerAccData == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     Player player = PlayerService.getPlayer(this.objectId, account);
/*     */     
/* 121 */     if (player != null && client.setActivePlayer(player)) {
/*     */       
/* 123 */       player.setClientConnection(client);
/*     */ 
/*     */ 
/*     */       
/* 127 */       Player player2 = World.getInstance().findPlayer(player.getObjectId());
/* 128 */       if (player2 != null) {
/*     */         
/* 130 */         player2.onLoggedOut();
/* 131 */         World.getInstance().removeObject((AionObject)player2);
/*     */       } 
/* 133 */       World.getInstance().storeObject((AionObject)player);
/*     */       
/* 135 */       StigmaService.onPlayerLogin(player);
/* 136 */       sendPacket((AionServerPacket)new SM_SKILL_LIST(player));
/*     */       
/* 138 */       if (player.getSkillCoolDowns() != null) {
/* 139 */         sendPacket((AionServerPacket)new SM_SKILL_COOLDOWN(player.getSkillCoolDowns()));
/*     */       }
/* 141 */       if (player.getItemCoolDowns() != null) {
/* 142 */         sendPacket((AionServerPacket)new SM_ITEM_COOLDOWN(player.getItemCoolDowns()));
/*     */       }
/* 144 */       sendPacket((AionServerPacket)new SM_QUEST_LIST(player));
/* 145 */       sendPacket((AionServerPacket)new SM_RECIPE_LIST(player.getRecipeList().getRecipeList()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       sendPacket((AionServerPacket)new SM_ENTER_WORLD_CHECK());
/*     */       
/* 152 */       byte[] uiSettings = player.getPlayerSettings().getUiSettings();
/* 153 */       byte[] shortcuts = player.getPlayerSettings().getShortcuts();
/*     */       
/* 155 */       if (uiSettings != null) {
/* 156 */         sendPacket((AionServerPacket)new SM_UI_SETTINGS(uiSettings, 0));
/*     */       }
/* 158 */       if (shortcuts != null) {
/* 159 */         sendPacket((AionServerPacket)new SM_UI_SETTINGS(shortcuts, 1));
/*     */       }
/*     */       
/* 162 */       int cubeSize = player.getCubeSize();
/* 163 */       player.getInventory().setLimit(27 + cubeSize * 9);
/*     */ 
/*     */       
/* 166 */       Storage inventory = player.getInventory();
/* 167 */       List<Item> equipedItems = player.getEquipment().getEquippedItems();
/* 168 */       if (equipedItems.size() != 0)
/*     */       {
/* 170 */         sendPacket((AionServerPacket)new SM_INVENTORY_INFO(player.getEquipment().getEquippedItems(), cubeSize));
/*     */       }
/*     */       
/* 173 */       List<Item> unequipedItems = inventory.getAllItems();
/* 174 */       int itemsSize = unequipedItems.size();
/*     */       
/* 176 */       if (itemsSize != 0) {
/*     */         
/* 178 */         int index = 0;
/* 179 */         while (index + 10 < itemsSize) {
/*     */           
/* 181 */           sendPacket((AionServerPacket)new SM_INVENTORY_INFO(unequipedItems.subList(index, index + 10), cubeSize));
/* 182 */           index += 10;
/*     */         } 
/* 184 */         sendPacket((AionServerPacket)new SM_INVENTORY_INFO(unequipedItems.subList(index, itemsSize), cubeSize));
/*     */       } 
/*     */       
/* 187 */       sendPacket((AionServerPacket)new SM_INVENTORY_INFO());
/*     */       
/* 189 */       PlayerService.playerLoggedIn(player);
/*     */       
/* 191 */       sendPacket((AionServerPacket)new SM_STATS_INFO(player));
/*     */       
/* 193 */       sendPacket((AionServerPacket)new SM_CUBE_UPDATE(player, 6, player.getCommonData().getAdvencedStigmaSlotSize()));
/*     */       
/* 195 */       KiskService.onLogin(player);
/* 196 */       TeleportService.sendSetBindPoint(player);
/*     */ 
/*     */       
/* 199 */       if (player.isInAlliance()) {
/* 200 */         AllianceService.getInstance().onLogin(player);
/*     */       }
/* 202 */       sendPacket((AionServerPacket)new SM_PLAYER_ID((AionObject)player));
/*     */       
/* 204 */       sendPacket((AionServerPacket)new SM_MACRO_LIST(player));
/* 205 */       sendPacket((AionServerPacket)new SM_GAME_TIME());
/* 206 */       player.getController().updateNearbyQuests();
/*     */       
/* 208 */       sendPacket((AionServerPacket)new SM_TITLE_LIST(player));
/* 209 */       sendPacket((AionServerPacket)new SM_CHANNEL_INFO(player.getPosition()));
/* 210 */       sendPacket((AionServerPacket)new SM_PLAYER_SPAWN(player));
/* 211 */       sendPacket((AionServerPacket)new SM_EMOTION_LIST());
/* 212 */       sendPacket((AionServerPacket)new SM_INFLUENCE_RATIO());
/* 213 */       sendPacket((AionServerPacket)new SM_SIEGE_LOCATION_INFO());
/*     */       
/* 215 */       sendPacket((AionServerPacket)new SM_PRICES(player.getPrices()));
/* 216 */       sendPacket((AionServerPacket)new SM_ABYSS_RANK(player.getAbyssRank()));
/*     */       
/* 218 */       for (String message : getWelcomeMessage()) {
/* 219 */         PacketSendUtility.sendMessage(player, message);
/*     */       }
/* 221 */       if (player.isInPrison()) {
/* 222 */         PunishmentService.updatePrisonStatus(player);
/*     */       }
/* 224 */       if (player.isLegionMember()) {
/* 225 */         LegionService.getInstance().onLogin(player);
/*     */       }
/* 227 */       if (player.isInGroup()) {
/* 228 */         GroupService.getInstance().onLogin(player);
/*     */       }
/* 230 */       player.setRates(Rates.getRatesFor(client.getAccount().getMembership()));
/*     */       
/* 232 */       ClassChangeService.showClassChangeDialog(player);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 237 */       MailService.getInstance().onPlayerLogin(player);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 242 */       BrokerService.getInstance().onPlayerLogin(player);
/*     */ 
/*     */ 
/*     */       
/* 246 */       if (!GSConfig.DISABLE_CHAT_SERVER) {
/* 247 */         ChatService.onPlayerLogin(player);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 252 */       PetitionService.getInstance().onPlayerLogin(player);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       player.getLifeStats().updateCurrentStats();
/*     */       
/* 259 */       if (CustomConfig.ENABLE_HTML_WELCOME) {
/* 260 */         HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("welcome.xhtml"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getWelcomeMessage() {
/* 270 */     return new String[] { "Welcome to " + GSConfig.SERVER_NAME + ", powered by Aion Lightning revision " + VersionningService.getGameRevision(), "This software is under GPL. See our website for more info: http://www.aion-lightning.com", "And remember, our source is based on Aion Unique." };
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_ENTER_WORLD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
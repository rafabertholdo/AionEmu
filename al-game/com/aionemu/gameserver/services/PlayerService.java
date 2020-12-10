/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.configs.main.CacheConfig;
/*     */ import com.aionemu.gameserver.configs.main.GSConfig;
/*     */ import com.aionemu.gameserver.controllers.FlyController;
/*     */ import com.aionemu.gameserver.controllers.PlayerController;
/*     */ import com.aionemu.gameserver.controllers.ReviveController;
/*     */ import com.aionemu.gameserver.controllers.SummonController;
/*     */ import com.aionemu.gameserver.controllers.effect.EffectController;
/*     */ import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
/*     */ import com.aionemu.gameserver.dao.AbyssRankDAO;
/*     */ import com.aionemu.gameserver.dao.BlockListDAO;
/*     */ import com.aionemu.gameserver.dao.FriendListDAO;
/*     */ import com.aionemu.gameserver.dao.InventoryDAO;
/*     */ import com.aionemu.gameserver.dao.ItemCooldownsDAO;
/*     */ import com.aionemu.gameserver.dao.ItemStoneListDAO;
/*     */ import com.aionemu.gameserver.dao.MailDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerAppearanceDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerEffectsDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerLifeStatsDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerMacrossesDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerPunishmentsDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerQuestListDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerRecipesDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerSettingsDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerSkillListDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerTitleListDAO;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.dataholders.PlayerInitialData;
/*     */ import com.aionemu.gameserver.dataholders.PlayerStatsData;
/*     */ import com.aionemu.gameserver.model.account.Account;
/*     */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Equipment;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.FriendList;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.MacroList;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.StorageType;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.listeners.TitleChangeListener;
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
/*     */ import com.aionemu.gameserver.model.legion.LegionMember;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.utils.collections.cachemap.CacheMap;
/*     */ import com.aionemu.gameserver.utils.collections.cachemap.CacheMapFactory;
/*     */ import com.aionemu.gameserver.world.KnownList;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.List;
/*     */ import javolution.util.FastList;
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
/*     */ 
/*     */ 
/*     */ public class PlayerService
/*     */ {
/*  95 */   private static final Logger log = Logger.getLogger(PlayerService.class);
/*  96 */   private static final CacheMap<Integer, Player> playerCache = CacheMapFactory.createSoftCacheMap("Player", "player");
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
/*     */   public static boolean isFreeName(String name) {
/* 108 */     return !((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).isNameUsed(name);
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
/*     */   public static boolean isValidName(String name) {
/* 120 */     return GSConfig.CHAR_NAME_PATTERN.matcher(name).matches();
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
/*     */   public static boolean storeNewPlayer(Player player, String accountName, int accountId) {
/* 132 */     return (((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).saveNewPlayer(player.getCommonData(), accountId, accountName) && ((PlayerAppearanceDAO)DAOManager.getDAO(PlayerAppearanceDAO.class)).store(player) && ((PlayerSkillListDAO)DAOManager.getDAO(PlayerSkillListDAO.class)).storeSkills(player) && ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).store(player.getDirtyItemsToUpdate()) && ((PlayerTitleListDAO)DAOManager.getDAO(PlayerTitleListDAO.class)).storeTitles(player));
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
/*     */   private static void storePlayer(Player player) {
/* 146 */     ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).storePlayer(player);
/* 147 */     ((PlayerSkillListDAO)DAOManager.getDAO(PlayerSkillListDAO.class)).storeSkills(player);
/* 148 */     ((PlayerSettingsDAO)DAOManager.getDAO(PlayerSettingsDAO.class)).saveSettings(player);
/* 149 */     ((PlayerQuestListDAO)DAOManager.getDAO(PlayerQuestListDAO.class)).store(player);
/* 150 */     ((PlayerTitleListDAO)DAOManager.getDAO(PlayerTitleListDAO.class)).storeTitles(player);
/* 151 */     ((AbyssRankDAO)DAOManager.getDAO(AbyssRankDAO.class)).storeAbyssRank(player);
/* 152 */     ((PlayerPunishmentsDAO)DAOManager.getDAO(PlayerPunishmentsDAO.class)).storePlayerPunishments(player);
/* 153 */     ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).store(player.getDirtyItemsToUpdate());
/* 154 */     ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).save(player);
/* 155 */     ((MailDAO)DAOManager.getDAO(MailDAO.class)).storeMailbox(player);
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
/*     */   public static Player getPlayer(int playerObjId, Account account) {
/* 167 */     Player player = (Player)playerCache.get(Integer.valueOf(playerObjId));
/* 168 */     if (player != null) {
/* 169 */       return player;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     PlayerAccountData playerAccountData = account.getPlayerAccountData(playerObjId);
/* 176 */     PlayerCommonData pcd = playerAccountData.getPlayerCommonData();
/* 177 */     PlayerAppearance appearance = playerAccountData.getAppereance();
/*     */     
/* 179 */     player = new Player(new PlayerController(), pcd, appearance);
/*     */     
/* 181 */     LegionMember legionMember = LegionService.getInstance().getLegionMember(player.getObjectId());
/* 182 */     if (legionMember != null) {
/* 183 */       player.setLegionMember(legionMember);
/*     */     }
/* 185 */     if (GroupService.getInstance().isGroupMember(playerObjId)) {
/* 186 */       GroupService.getInstance().setGroup(player);
/*     */     }
/* 188 */     if (AllianceService.getInstance().isAllianceMember(playerObjId)) {
/* 189 */       AllianceService.getInstance().setAlliance(player);
/*     */     }
/* 191 */     MacroList macroses = ((PlayerMacrossesDAO)DAOManager.getDAO(PlayerMacrossesDAO.class)).restoreMacrosses(playerObjId);
/* 192 */     player.setMacroList(macroses);
/*     */     
/* 194 */     player.setSkillList(((PlayerSkillListDAO)DAOManager.getDAO(PlayerSkillListDAO.class)).loadSkillList(playerObjId));
/* 195 */     player.setKnownlist(new KnownList((VisibleObject)player));
/* 196 */     player.setFriendList(((FriendListDAO)DAOManager.getDAO(FriendListDAO.class)).load(player));
/* 197 */     player.setBlockList(((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).load(player));
/* 198 */     player.setTitleList(((PlayerTitleListDAO)DAOManager.getDAO(PlayerTitleListDAO.class)).loadTitleList(playerObjId));
/*     */     
/* 200 */     ((PlayerSettingsDAO)DAOManager.getDAO(PlayerSettingsDAO.class)).loadSettings(player);
/* 201 */     ((AbyssRankDAO)DAOManager.getDAO(AbyssRankDAO.class)).loadAbyssRank(player);
/* 202 */     PlayerStatsData playerStatsData = DataManager.PLAYER_STATS_DATA;
/* 203 */     player.setPlayerStatsTemplate(playerStatsData.getTemplate(player));
/*     */     
/* 205 */     player.setGameStats(new PlayerGameStats(playerStatsData, player));
/*     */     
/* 207 */     Equipment equipment = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadEquipment(player);
/* 208 */     ItemService.loadItemStones(equipment.getEquippedItemsWithoutStigma());
/* 209 */     equipment.setOwner(player);
/* 210 */     player.setEquipment(equipment);
/*     */     
/* 212 */     player.setLifeStats(new PlayerLifeStats(player));
/* 213 */     player.setEffectController((EffectController)new PlayerEffectController((Creature)player));
/* 214 */     player.setFlyController(new FlyController(player));
/* 215 */     player.setReviveController(new ReviveController(player));
/*     */     
/* 217 */     player.setQuestStateList(((PlayerQuestListDAO)DAOManager.getDAO(PlayerQuestListDAO.class)).load(player));
/* 218 */     player.setRecipeList(((PlayerRecipesDAO)DAOManager.getDAO(PlayerRecipesDAO.class)).load(player.getObjectId()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     Storage accWarehouse = account.getAccountWarehouse();
/*     */     
/* 225 */     player.setStorage(accWarehouse, StorageType.ACCOUNT_WAREHOUSE);
/*     */     
/* 227 */     Storage inventory = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadStorage(player, player.getObjectId(), StorageType.CUBE);
/* 228 */     ItemService.loadItemStones(inventory.getStorageItems());
/*     */     
/* 230 */     player.setStorage(inventory, StorageType.CUBE);
/*     */     
/* 232 */     Storage warehouse = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadStorage(player, player.getObjectId(), StorageType.REGULAR_WAREHOUSE);
/* 233 */     ItemService.loadItemStones(warehouse.getStorageItems());
/*     */     
/* 235 */     player.setStorage(warehouse, StorageType.REGULAR_WAREHOUSE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     player.getEquipment().onLoadApplyEquipmentStats();
/*     */     
/* 242 */     ((PlayerPunishmentsDAO)DAOManager.getDAO(PlayerPunishmentsDAO.class)).loadPlayerPunishments(player);
/*     */     
/* 244 */     ItemService.restoreKinah(player);
/*     */ 
/*     */     
/* 247 */     player.getController().updatePassiveStats();
/*     */     
/* 249 */     ((PlayerEffectsDAO)DAOManager.getDAO(PlayerEffectsDAO.class)).loadPlayerEffects(player);
/*     */     
/* 251 */     ((ItemCooldownsDAO)DAOManager.getDAO(ItemCooldownsDAO.class)).loadItemCooldowns(player);
/*     */     
/* 253 */     if (player.getCommonData().getTitleId() > 0)
/*     */     {
/* 255 */       TitleChangeListener.onTitleChange((CreatureGameStats)player.getGameStats(), player.getCommonData().getTitleId(), true);
/*     */     }
/* 257 */     player.getGameStats().recomputeStats();
/*     */     
/* 259 */     ((PlayerLifeStatsDAO)DAOManager.getDAO(PlayerLifeStatsDAO.class)).loadPlayerLifeStat(player);
/*     */     
/* 261 */     InstanceService.onPlayerLogin(player);
/*     */     
/* 263 */     if (CacheConfig.CACHE_PLAYERS) {
/* 264 */       playerCache.put(Integer.valueOf(playerObjId), player);
/*     */     }
/* 266 */     return player;
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
/*     */   public static Player newPlayer(PlayerCommonData playerCommonData, PlayerAppearance playerAppearance) {
/* 278 */     PlayerInitialData playerInitialData = DataManager.PLAYER_INITIAL_DATA;
/* 279 */     PlayerInitialData.LocationData ld = playerInitialData.getSpawnLocation(playerCommonData.getRace());
/*     */     
/* 281 */     WorldPosition position = World.getInstance().createPosition(ld.getMapId(), ld.getX(), ld.getY(), ld.getZ(), ld.getHeading());
/* 282 */     playerCommonData.setPosition(position);
/*     */     
/* 284 */     Player newPlayer = new Player(new PlayerController(), playerCommonData, playerAppearance);
/*     */ 
/*     */     
/* 287 */     SkillLearnService.addNewSkills(newPlayer, true);
/*     */ 
/*     */     
/* 290 */     PlayerInitialData.PlayerCreationData playerCreationData = playerInitialData.getPlayerCreationData(playerCommonData.getPlayerClass());
/*     */ 
/*     */     
/* 293 */     List<PlayerInitialData.PlayerCreationData.ItemType> items = playerCreationData.getItems();
/*     */     
/* 295 */     Storage playerInventory = new Storage(StorageType.CUBE);
/* 296 */     Storage regularWarehouse = new Storage(StorageType.REGULAR_WAREHOUSE);
/* 297 */     Storage accountWarehouse = new Storage(StorageType.ACCOUNT_WAREHOUSE);
/*     */     
/* 299 */     Equipment equipment = new Equipment(newPlayer);
/* 300 */     newPlayer.setStorage(playerInventory, StorageType.CUBE);
/* 301 */     newPlayer.setStorage(regularWarehouse, StorageType.REGULAR_WAREHOUSE);
/* 302 */     newPlayer.setStorage(accountWarehouse, StorageType.ACCOUNT_WAREHOUSE);
/* 303 */     newPlayer.setEquipment(equipment);
/* 304 */     newPlayer.setMailbox(new Mailbox());
/*     */     
/* 306 */     for (PlayerInitialData.PlayerCreationData.ItemType itemType : items) {
/*     */       
/* 308 */       int itemId = itemType.getTemplate().getTemplateId();
/* 309 */       Item item = ItemService.newItem(itemId, itemType.getCount());
/* 310 */       if (item == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 315 */       ItemTemplate itemTemplate = item.getItemTemplate();
/* 316 */       item.setOwnerId(newPlayer.getObjectId());
/* 317 */       if (itemTemplate.isArmor() || itemTemplate.isWeapon()) {
/*     */         
/* 319 */         item.setEquipped(true);
/* 320 */         FastList<ItemSlot> fastList = ItemSlot.getSlotsFor(itemTemplate.getItemSlot());
/* 321 */         item.setEquipmentSlot(((ItemSlot)fastList.get(0)).getSlotIdMask());
/* 322 */         equipment.onLoadHandler(item);
/*     */         continue;
/*     */       } 
/* 325 */       ItemService.onLoadHandler(newPlayer, newPlayer.getInventory(), item);
/*     */     } 
/* 327 */     equipment.onLoadApplyEquipmentStats();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     playerInventory.setPersistentState(PersistentState.UPDATE_REQUIRED);
/* 333 */     equipment.setPersistentState(PersistentState.UPDATE_REQUIRED);
/* 334 */     return newPlayer;
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
/*     */   public static void playerLoggedIn(Player player) {
/* 347 */     if (log.isDebugEnabled()) {
/* 348 */       log.debug("Player logged in: " + player.getName() + " Account: " + player.getClientConnection().getAccount().getName());
/*     */     }
/* 350 */     player.getCommonData().setOnline(true);
/* 351 */     ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).onlinePlayer(player, true);
/* 352 */     player.getFriendList().setStatus(FriendList.Status.ONLINE);
/* 353 */     player.onLoggedIn();
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
/*     */   
/*     */   public static void playerLoggedOut(Player player) {
/* 368 */     if (log.isDebugEnabled()) {
/* 369 */       log.debug("Player logged out: " + player.getName() + " Account: " + player.getClientConnection().getAccount().getName());
/*     */     }
/* 371 */     player.onLoggedOut();
/*     */ 
/*     */     
/* 374 */     if (player.isInPrison()) {
/*     */       
/* 376 */       long prisonTimer = System.currentTimeMillis() - player.getStartPrison();
/* 377 */       prisonTimer = player.getPrisonTimer() - prisonTimer;
/*     */       
/* 379 */       player.setPrisonTimer(prisonTimer);
/*     */       
/* 381 */       log.debug("Update prison timer to " + (prisonTimer / 1000L) + " seconds !");
/*     */     } 
/*     */ 
/*     */     
/* 385 */     ((PlayerEffectsDAO)DAOManager.getDAO(PlayerEffectsDAO.class)).storePlayerEffects(player);
/* 386 */     ((ItemCooldownsDAO)DAOManager.getDAO(ItemCooldownsDAO.class)).storeItemCooldowns(player);
/* 387 */     ((PlayerLifeStatsDAO)DAOManager.getDAO(PlayerLifeStatsDAO.class)).updatePlayerLifeStat(player);
/* 388 */     player.getEffectController().removeAllEffects();
/*     */     
/* 390 */     player.getLifeStats().cancelAllTasks();
/*     */     
/* 392 */     if (player.getLifeStats().isAlreadyDead()) {
/* 393 */       TeleportService.moveToBindLocation(player, false);
/*     */     }
/* 395 */     if (DuelService.getInstance().isDueling(player.getObjectId())) {
/* 396 */       DuelService.getInstance().loseDuel(player);
/*     */     }
/* 398 */     if (player.getSummon() != null) {
/* 399 */       player.getSummon().getController().release(SummonController.UnsummonType.LOGOUT);
/*     */     }
/* 401 */     PunishmentService.stopPrisonTask(player, true);
/*     */     
/* 403 */     player.getCommonData().setOnline(false);
/* 404 */     player.getCommonData().setLastOnline(new Timestamp(System.currentTimeMillis()));
/*     */     
/* 406 */     player.setClientConnection(null);
/*     */     
/* 408 */     if (player.isLegionMember()) {
/* 409 */       LegionService.getInstance().onLogout(player);
/*     */     }
/* 411 */     if (player.isInGroup()) {
/* 412 */       GroupService.getInstance().scheduleRemove(player);
/*     */     }
/* 414 */     if (player.isInAlliance()) {
/* 415 */       AllianceService.getInstance().onLogout(player);
/*     */     }
/* 417 */     player.getController().delete();
/* 418 */     ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).onlinePlayer(player, false);
/*     */     
/* 420 */     if (!GSConfig.DISABLE_CHAT_SERVER) {
/* 421 */       ChatService.onPlayerLogout(player);
/*     */     }
/* 423 */     storePlayer(player);
/* 424 */     player.getEquipment().setOwner(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void playerLoggedOutDelay(final Player player, int delay) {
/* 430 */     player.getController().stopMoving();
/*     */     
/* 432 */     ThreadPoolManager.getInstance().scheduleTaskManager(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 436 */             PlayerService.playerLoggedOut(player);
/*     */           }
/*     */         },  delay);
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
/*     */   public static boolean cancelPlayerDeletion(PlayerAccountData accData) {
/* 451 */     if (accData.getDeletionDate() == null) {
/* 452 */       return true;
/*     */     }
/* 454 */     if (accData.getDeletionDate().getTime() > System.currentTimeMillis()) {
/*     */       
/* 456 */       accData.setDeletionDate(null);
/* 457 */       storeDeletionTime(accData);
/* 458 */       return true;
/*     */     } 
/* 460 */     return false;
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
/*     */   public static void deletePlayer(PlayerAccountData accData) {
/* 472 */     if (accData.getDeletionDate() != null) {
/*     */       return;
/*     */     }
/* 475 */     accData.setDeletionDate(new Timestamp(System.currentTimeMillis() + 300000L));
/* 476 */     storeDeletionTime(accData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deletePlayerFromDB(int playerId) {
/* 487 */     ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).deletePlayer(playerId);
/* 488 */     ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).deletePlayerItems(playerId);
/* 489 */     ((PlayerLifeStatsDAO)DAOManager.getDAO(PlayerLifeStatsDAO.class)).deletePlayerLifeStat(playerId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void storeDeletionTime(PlayerAccountData accData) {
/* 500 */     ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).updateDeletionTime(accData.getPlayerCommonData().getPlayerObjId(), accData.getDeletionDate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void storeCreationTime(int objectId, Timestamp creationDate) {
/* 511 */     ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).storeCreationTime(objectId, creationDate);
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
/*     */   
/*     */   public static void addMacro(Player player, int macroOrder, String macroXML) {
/* 526 */     if (player.getMacroList().addMacro(macroOrder, macroXML))
/*     */     {
/* 528 */       ((PlayerMacrossesDAO)DAOManager.getDAO(PlayerMacrossesDAO.class)).addMacro(player.getObjectId(), macroOrder, macroXML);
/*     */     }
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
/*     */   public static void removeMacro(Player player, int macroOrder) {
/* 542 */     if (player.getMacroList().removeMacro(macroOrder))
/*     */     {
/* 544 */       ((PlayerMacrossesDAO)DAOManager.getDAO(PlayerMacrossesDAO.class)).deleteMacro(player.getObjectId(), macroOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Player getCachedPlayer(int playerObjectId) {
/* 555 */     return (Player)playerCache.get(Integer.valueOf(playerObjectId));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\PlayerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.CacheConfig;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.controllers.FlyController;
import com.aionemu.gameserver.controllers.PlayerController;
import com.aionemu.gameserver.controllers.ReviveController;
import com.aionemu.gameserver.controllers.SummonController;
import com.aionemu.gameserver.controllers.effect.EffectController;
import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import com.aionemu.gameserver.dao.AbyssRankDAO;
import com.aionemu.gameserver.dao.BlockListDAO;
import com.aionemu.gameserver.dao.FriendListDAO;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.ItemCooldownsDAO;
import com.aionemu.gameserver.dao.ItemStoneListDAO;
import com.aionemu.gameserver.dao.MailDAO;
import com.aionemu.gameserver.dao.PlayerAppearanceDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dao.PlayerEffectsDAO;
import com.aionemu.gameserver.dao.PlayerLifeStatsDAO;
import com.aionemu.gameserver.dao.PlayerMacrossesDAO;
import com.aionemu.gameserver.dao.PlayerPunishmentsDAO;
import com.aionemu.gameserver.dao.PlayerQuestListDAO;
import com.aionemu.gameserver.dao.PlayerRecipesDAO;
import com.aionemu.gameserver.dao.PlayerSettingsDAO;
import com.aionemu.gameserver.dao.PlayerSkillListDAO;
import com.aionemu.gameserver.dao.PlayerTitleListDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.PlayerInitialData;
import com.aionemu.gameserver.dataholders.PlayerStatsData;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.MacroList;
import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import com.aionemu.gameserver.model.gameobjects.stats.listeners.TitleChangeListener;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.legion.LegionMember;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMap;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMapFactory;
import com.aionemu.gameserver.world.KnownList;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldPosition;
import java.sql.Timestamp;
import java.util.List;
import javolution.util.FastList;
import org.apache.log4j.Logger;




























public class PlayerService
{
  private static final Logger log = Logger.getLogger(PlayerService.class);
  private static final CacheMap<Integer, Player> playerCache = CacheMapFactory.createSoftCacheMap("Player", "player");









  
  public static boolean isFreeName(String name) {
    return !((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).isNameUsed(name);
  }








  
  public static boolean isValidName(String name) {
    return GSConfig.CHAR_NAME_PATTERN.matcher(name).matches();
  }








  
  public static boolean storeNewPlayer(Player player, String accountName, int accountId) {
    return (((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).saveNewPlayer(player.getCommonData(), accountId, accountName) && ((PlayerAppearanceDAO)DAOManager.getDAO(PlayerAppearanceDAO.class)).store(player) && ((PlayerSkillListDAO)DAOManager.getDAO(PlayerSkillListDAO.class)).storeSkills(player) && ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).store(player.getDirtyItemsToUpdate()) && ((PlayerTitleListDAO)DAOManager.getDAO(PlayerTitleListDAO.class)).storeTitles(player));
  }










  
  private static void storePlayer(Player player) {
    ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).storePlayer(player);
    ((PlayerSkillListDAO)DAOManager.getDAO(PlayerSkillListDAO.class)).storeSkills(player);
    ((PlayerSettingsDAO)DAOManager.getDAO(PlayerSettingsDAO.class)).saveSettings(player);
    ((PlayerQuestListDAO)DAOManager.getDAO(PlayerQuestListDAO.class)).store(player);
    ((PlayerTitleListDAO)DAOManager.getDAO(PlayerTitleListDAO.class)).storeTitles(player);
    ((AbyssRankDAO)DAOManager.getDAO(AbyssRankDAO.class)).storeAbyssRank(player);
    ((PlayerPunishmentsDAO)DAOManager.getDAO(PlayerPunishmentsDAO.class)).storePlayerPunishments(player);
    ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).store(player.getDirtyItemsToUpdate());
    ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).save(player);
    ((MailDAO)DAOManager.getDAO(MailDAO.class)).storeMailbox(player);
  }








  
  public static Player getPlayer(int playerObjId, Account account) {
    Player player = (Player)playerCache.get(Integer.valueOf(playerObjId));
    if (player != null) {
      return player;
    }



    
    PlayerAccountData playerAccountData = account.getPlayerAccountData(playerObjId);
    PlayerCommonData pcd = playerAccountData.getPlayerCommonData();
    PlayerAppearance appearance = playerAccountData.getAppereance();
    
    player = new Player(new PlayerController(), pcd, appearance);
    
    LegionMember legionMember = LegionService.getInstance().getLegionMember(player.getObjectId());
    if (legionMember != null) {
      player.setLegionMember(legionMember);
    }
    if (GroupService.getInstance().isGroupMember(playerObjId)) {
      GroupService.getInstance().setGroup(player);
    }
    if (AllianceService.getInstance().isAllianceMember(playerObjId)) {
      AllianceService.getInstance().setAlliance(player);
    }
    MacroList macroses = ((PlayerMacrossesDAO)DAOManager.getDAO(PlayerMacrossesDAO.class)).restoreMacrosses(playerObjId);
    player.setMacroList(macroses);
    
    player.setSkillList(((PlayerSkillListDAO)DAOManager.getDAO(PlayerSkillListDAO.class)).loadSkillList(playerObjId));
    player.setKnownlist(new KnownList((VisibleObject)player));
    player.setFriendList(((FriendListDAO)DAOManager.getDAO(FriendListDAO.class)).load(player));
    player.setBlockList(((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).load(player));
    player.setTitleList(((PlayerTitleListDAO)DAOManager.getDAO(PlayerTitleListDAO.class)).loadTitleList(playerObjId));
    
    ((PlayerSettingsDAO)DAOManager.getDAO(PlayerSettingsDAO.class)).loadSettings(player);
    ((AbyssRankDAO)DAOManager.getDAO(AbyssRankDAO.class)).loadAbyssRank(player);
    PlayerStatsData playerStatsData = DataManager.PLAYER_STATS_DATA;
    player.setPlayerStatsTemplate(playerStatsData.getTemplate(player));
    
    player.setGameStats(new PlayerGameStats(playerStatsData, player));
    
    Equipment equipment = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadEquipment(player);
    ItemService.loadItemStones(equipment.getEquippedItemsWithoutStigma());
    equipment.setOwner(player);
    player.setEquipment(equipment);
    
    player.setLifeStats(new PlayerLifeStats(player));
    player.setEffectController((EffectController)new PlayerEffectController((Creature)player));
    player.setFlyController(new FlyController(player));
    player.setReviveController(new ReviveController(player));
    
    player.setQuestStateList(((PlayerQuestListDAO)DAOManager.getDAO(PlayerQuestListDAO.class)).load(player));
    player.setRecipeList(((PlayerRecipesDAO)DAOManager.getDAO(PlayerRecipesDAO.class)).load(player.getObjectId()));



    
    Storage accWarehouse = account.getAccountWarehouse();
    
    player.setStorage(accWarehouse, StorageType.ACCOUNT_WAREHOUSE);
    
    Storage inventory = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadStorage(player, player.getObjectId(), StorageType.CUBE);
    ItemService.loadItemStones(inventory.getStorageItems());
    
    player.setStorage(inventory, StorageType.CUBE);
    
    Storage warehouse = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadStorage(player, player.getObjectId(), StorageType.REGULAR_WAREHOUSE);
    ItemService.loadItemStones(warehouse.getStorageItems());
    
    player.setStorage(warehouse, StorageType.REGULAR_WAREHOUSE);



    
    player.getEquipment().onLoadApplyEquipmentStats();
    
    ((PlayerPunishmentsDAO)DAOManager.getDAO(PlayerPunishmentsDAO.class)).loadPlayerPunishments(player);
    
    ItemService.restoreKinah(player);

    
    player.getController().updatePassiveStats();
    
    ((PlayerEffectsDAO)DAOManager.getDAO(PlayerEffectsDAO.class)).loadPlayerEffects(player);
    
    ((ItemCooldownsDAO)DAOManager.getDAO(ItemCooldownsDAO.class)).loadItemCooldowns(player);
    
    if (player.getCommonData().getTitleId() > 0)
    {
      TitleChangeListener.onTitleChange((CreatureGameStats)player.getGameStats(), player.getCommonData().getTitleId(), true);
    }
    player.getGameStats().recomputeStats();
    
    ((PlayerLifeStatsDAO)DAOManager.getDAO(PlayerLifeStatsDAO.class)).loadPlayerLifeStat(player);
    
    InstanceService.onPlayerLogin(player);
    
    if (CacheConfig.CACHE_PLAYERS) {
      playerCache.put(Integer.valueOf(playerObjId), player);
    }
    return player;
  }








  
  public static Player newPlayer(PlayerCommonData playerCommonData, PlayerAppearance playerAppearance) {
    PlayerInitialData playerInitialData = DataManager.PLAYER_INITIAL_DATA;
    PlayerInitialData.LocationData ld = playerInitialData.getSpawnLocation(playerCommonData.getRace());
    
    WorldPosition position = World.getInstance().createPosition(ld.getMapId(), ld.getX(), ld.getY(), ld.getZ(), ld.getHeading());
    playerCommonData.setPosition(position);
    
    Player newPlayer = new Player(new PlayerController(), playerCommonData, playerAppearance);

    
    SkillLearnService.addNewSkills(newPlayer, true);

    
    PlayerInitialData.PlayerCreationData playerCreationData = playerInitialData.getPlayerCreationData(playerCommonData.getPlayerClass());

    
    List<PlayerInitialData.PlayerCreationData.ItemType> items = playerCreationData.getItems();
    
    Storage playerInventory = new Storage(StorageType.CUBE);
    Storage regularWarehouse = new Storage(StorageType.REGULAR_WAREHOUSE);
    Storage accountWarehouse = new Storage(StorageType.ACCOUNT_WAREHOUSE);
    
    Equipment equipment = new Equipment(newPlayer);
    newPlayer.setStorage(playerInventory, StorageType.CUBE);
    newPlayer.setStorage(regularWarehouse, StorageType.REGULAR_WAREHOUSE);
    newPlayer.setStorage(accountWarehouse, StorageType.ACCOUNT_WAREHOUSE);
    newPlayer.setEquipment(equipment);
    newPlayer.setMailbox(new Mailbox());
    
    for (PlayerInitialData.PlayerCreationData.ItemType itemType : items) {
      
      int itemId = itemType.getTemplate().getTemplateId();
      Item item = ItemService.newItem(itemId, itemType.getCount());
      if (item == null) {
        continue;
      }

      
      ItemTemplate itemTemplate = item.getItemTemplate();
      item.setOwnerId(newPlayer.getObjectId());
      if (itemTemplate.isArmor() || itemTemplate.isWeapon()) {
        
        item.setEquipped(true);
        FastList<ItemSlot> fastList = ItemSlot.getSlotsFor(itemTemplate.getItemSlot());
        item.setEquipmentSlot(((ItemSlot)fastList.get(0)).getSlotIdMask());
        equipment.onLoadHandler(item);
        continue;
      } 
      ItemService.onLoadHandler(newPlayer, newPlayer.getInventory(), item);
    } 
    equipment.onLoadApplyEquipmentStats();



    
    playerInventory.setPersistentState(PersistentState.UPDATE_REQUIRED);
    equipment.setPersistentState(PersistentState.UPDATE_REQUIRED);
    return newPlayer;
  }









  
  public static void playerLoggedIn(Player player) {
    if (log.isDebugEnabled()) {
      log.debug("Player logged in: " + player.getName() + " Account: " + player.getClientConnection().getAccount().getName());
    }
    player.getCommonData().setOnline(true);
    ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).onlinePlayer(player, true);
    player.getFriendList().setStatus(FriendList.Status.ONLINE);
    player.onLoggedIn();
  }











  
  public static void playerLoggedOut(Player player) {
    if (log.isDebugEnabled()) {
      log.debug("Player logged out: " + player.getName() + " Account: " + player.getClientConnection().getAccount().getName());
    }
    player.onLoggedOut();

    
    if (player.isInPrison()) {
      
      long prisonTimer = System.currentTimeMillis() - player.getStartPrison();
      prisonTimer = player.getPrisonTimer() - prisonTimer;
      
      player.setPrisonTimer(prisonTimer);
      
      log.debug("Update prison timer to " + (prisonTimer / 1000L) + " seconds !");
    } 

    
    ((PlayerEffectsDAO)DAOManager.getDAO(PlayerEffectsDAO.class)).storePlayerEffects(player);
    ((ItemCooldownsDAO)DAOManager.getDAO(ItemCooldownsDAO.class)).storeItemCooldowns(player);
    ((PlayerLifeStatsDAO)DAOManager.getDAO(PlayerLifeStatsDAO.class)).updatePlayerLifeStat(player);
    player.getEffectController().removeAllEffects();
    
    player.getLifeStats().cancelAllTasks();
    
    if (player.getLifeStats().isAlreadyDead()) {
      TeleportService.moveToBindLocation(player, false);
    }
    if (DuelService.getInstance().isDueling(player.getObjectId())) {
      DuelService.getInstance().loseDuel(player);
    }
    if (player.getSummon() != null) {
      player.getSummon().getController().release(SummonController.UnsummonType.LOGOUT);
    }
    PunishmentService.stopPrisonTask(player, true);
    
    player.getCommonData().setOnline(false);
    player.getCommonData().setLastOnline(new Timestamp(System.currentTimeMillis()));
    
    player.setClientConnection(null);
    
    if (player.isLegionMember()) {
      LegionService.getInstance().onLogout(player);
    }
    if (player.isInGroup()) {
      GroupService.getInstance().scheduleRemove(player);
    }
    if (player.isInAlliance()) {
      AllianceService.getInstance().onLogout(player);
    }
    player.getController().delete();
    ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).onlinePlayer(player, false);
    
    if (!GSConfig.DISABLE_CHAT_SERVER) {
      ChatService.onPlayerLogout(player);
    }
    storePlayer(player);
    player.getEquipment().setOwner(null);
  }


  
  public static void playerLoggedOutDelay(final Player player, int delay) {
    player.getController().stopMoving();
    
    ThreadPoolManager.getInstance().scheduleTaskManager(new Runnable()
        {
          public void run()
          {
            PlayerService.playerLoggedOut(player);
          }
        },  delay);
  }









  
  public static boolean cancelPlayerDeletion(PlayerAccountData accData) {
    if (accData.getDeletionDate() == null) {
      return true;
    }
    if (accData.getDeletionDate().getTime() > System.currentTimeMillis()) {
      
      accData.setDeletionDate(null);
      storeDeletionTime(accData);
      return true;
    } 
    return false;
  }








  
  public static void deletePlayer(PlayerAccountData accData) {
    if (accData.getDeletionDate() != null) {
      return;
    }
    accData.setDeletionDate(new Timestamp(System.currentTimeMillis() + 300000L));
    storeDeletionTime(accData);
  }







  
  public static void deletePlayerFromDB(int playerId) {
    ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).deletePlayer(playerId);
    ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).deletePlayerItems(playerId);
    ((PlayerLifeStatsDAO)DAOManager.getDAO(PlayerLifeStatsDAO.class)).deletePlayerLifeStat(playerId);
  }







  
  private static void storeDeletionTime(PlayerAccountData accData) {
    ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).updateDeletionTime(accData.getPlayerCommonData().getPlayerObjId(), accData.getDeletionDate());
  }







  
  public static void storeCreationTime(int objectId, Timestamp creationDate) {
    ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).storeCreationTime(objectId, creationDate);
  }











  
  public static void addMacro(Player player, int macroOrder, String macroXML) {
    if (player.getMacroList().addMacro(macroOrder, macroXML))
    {
      ((PlayerMacrossesDAO)DAOManager.getDAO(PlayerMacrossesDAO.class)).addMacro(player.getObjectId(), macroOrder, macroXML);
    }
  }









  
  public static void removeMacro(Player player, int macroOrder) {
    if (player.getMacroList().removeMacro(macroOrder))
    {
      ((PlayerMacrossesDAO)DAOManager.getDAO(PlayerMacrossesDAO.class)).deleteMacro(player.getObjectId(), macroOrder);
    }
  }






  
  public static Player getCachedPlayer(int playerObjectId) {
    return (Player)playerCache.get(Integer.valueOf(playerObjectId));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\PlayerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

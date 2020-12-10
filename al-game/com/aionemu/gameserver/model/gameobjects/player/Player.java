package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.controllers.CreatureController;
import com.aionemu.gameserver.controllers.FlyController;
import com.aionemu.gameserver.controllers.PlayerController;
import com.aionemu.gameserver.controllers.ReviveController;
import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.controllers.effect.EffectController;
import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import com.aionemu.gameserver.dao.AbyssRankDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dao.PlayerQuestListDAO;
import com.aionemu.gameserver.dao.PlayerSkillListDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.model.items.ItemCooldown;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.model.legion.LegionMember;
import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.BrokerService;
import com.aionemu.gameserver.services.ExchangeService;
import com.aionemu.gameserver.skillengine.task.CraftingTask;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.rates.Rates;
import com.aionemu.gameserver.utils.rates.RegularRates;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class Player extends Creature {
  private static final Logger log = Logger.getLogger(Player.class);

  private PlayerAppearance playerAppearance;
  private PlayerAppearance savedPlayerAppearance;
  private LegionMember legionMember;
  private MacroList macroList;
  private SkillList skillList;
  private FriendList friendList;
  private BlockList blockList;
  private ResponseRequester requester;
  private boolean lookingForGroup = false;
  private Storage inventory;
  private Storage regularWarehouse;
  private Storage accountWarehouse;
  private Equipment equipment;
  private Mailbox mailbox;
  private PrivateStore store;
  private PlayerStatsTemplate playerStatsTemplate;
  private TitleList titleList;
  private PlayerSettings playerSettings;
  private QuestStateList questStateList;
  private List<Integer> nearbyQuestList = new ArrayList<Integer>();
  private ZoneInstance zoneInstance;
  private PlayerGroup playerGroup;
  private AbyssRank abyssRank;
  private Rates rates;
  private RecipeList recipeList;
  private int flyState = 0;
  private boolean isTrading;
  private long prisonTimer = 0L;

  private long startPrison;

  private boolean invul;

  private FlyController flyController;

  private ReviveController reviveController;

  private CraftingTask craftingTask;

  private int flightTeleportId;

  private int flightDistance;

  private Summon summon;

  private Kisk kisk;
  private Prices prices;
  private boolean isGagged = false;
  private Map<Integer, ItemCooldown> itemCoolDowns;
  private static final int CUBE_SPACE = 9;
  private static final int WAREHOUSE_SPACE = 8;
  private AionConnection clientConnection;
  private PlayerAlliance playerAlliance;

  public Player(PlayerController controller, PlayerCommonData plCommonData, PlayerAppearance appereance) {
    super(plCommonData.getPlayerObjId(), (CreatureController) controller, null, plCommonData,
        plCommonData.getPosition());
    this.playerAppearance = appereance;

    this.prices = new Prices();
    this.requester = new ResponseRequester(this);
    this.questStateList = new QuestStateList();
    this.titleList = new TitleList();
    controller.setOwner((VisibleObject) this);
  }

  public PlayerCommonData getCommonData() {
    return (PlayerCommonData) this.objectTemplate;
  }

  public void setCommonData(PlayerCommonData playerCommonData) {
    this.objectTemplate = playerCommonData;
  }

  public String getName() {
    return getCommonData().getName();
  }

  public PlayerAppearance getPlayerAppearance() {
    return this.playerAppearance;
  }

  public void setPlayerAppearance(PlayerAppearance playerAppearance) {
    this.playerAppearance = playerAppearance;
  }

  public PlayerAppearance getSavedPlayerAppearance() {
    return this.savedPlayerAppearance;
  }

  public void setSavedPlayerAppearance(PlayerAppearance savedPlayerAppearance) {
    this.savedPlayerAppearance = savedPlayerAppearance;
  }

  public void setClientConnection(AionConnection clientConnection) {
    this.clientConnection = clientConnection;
  }

  public AionConnection getClientConnection() {
    return this.clientConnection;
  }

  public MacroList getMacroList() {
    return this.macroList;
  }

  public void setMacroList(MacroList macroList) {
    this.macroList = macroList;
  }

  public SkillList getSkillList() {
    return this.skillList;
  }

  public void setSkillList(SkillList skillList) {
    this.skillList = skillList;
  }

  public FriendList getFriendList() {
    return this.friendList;
  }

  public boolean isLookingForGroup() {
    return this.lookingForGroup;
  }

  public void setLookingForGroup(boolean lookingForGroup) {
    this.lookingForGroup = lookingForGroup;
  }

  public void setFriendList(FriendList list) {
    this.friendList = list;
  }

  public BlockList getBlockList() {
    return this.blockList;
  }

  public void setBlockList(BlockList list) {
    this.blockList = list;
  }

  public PlayerLifeStats getLifeStats() {
    return (PlayerLifeStats) super.getLifeStats();
  }

  public void setLifeStats(PlayerLifeStats lifeStats) {
    setLifeStats((CreatureLifeStats) lifeStats);
  }

  public PlayerGameStats getGameStats() {
    return (PlayerGameStats) super.getGameStats();
  }

  public void setGameStats(PlayerGameStats gameStats) {
    setGameStats((CreatureGameStats) gameStats);
  }

  public ResponseRequester getResponseRequester() {
    return this.requester;
  }

  public boolean isOnline() {
    return (getClientConnection() != null);
  }

  public int getCubeSize() {
    return getCommonData().getCubeSize();
  }

  public PlayerClass getPlayerClass() {
    return getCommonData().getPlayerClass();
  }

  public Gender getGender() {
    return getCommonData().getGender();
  }

  public PlayerController getController() {
    return (PlayerController) super.getController();
  }

  public byte getLevel() {
    return (byte) getCommonData().getLevel();
  }

  public Equipment getEquipment() {
    return this.equipment;
  }

  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }

  public PrivateStore getStore() {
    return this.store;
  }

  public void setStore(PrivateStore store) {
    if (this.store != null && store == null)
      this.store.clear();
    this.store = store;
  }

  public QuestStateList getQuestStateList() {
    return this.questStateList;
  }

  public void setQuestStateList(QuestStateList questStateList) {
    this.questStateList = questStateList;
  }

  public PlayerStatsTemplate getPlayerStatsTemplate() {
    return this.playerStatsTemplate;
  }

  public void setPlayerStatsTemplate(PlayerStatsTemplate playerStatsTemplate) {
    this.playerStatsTemplate = playerStatsTemplate;
  }

  public List<Integer> getNearbyQuests() {
    return this.nearbyQuestList;
  }

  public RecipeList getRecipeList() {
    return this.recipeList;
  }

  public void setRecipeList(RecipeList recipeList) {
    this.recipeList = recipeList;
  }

  public void setStorage(Storage storage, StorageType storageType) {
    if (storageType == StorageType.CUBE) {
      this.inventory = storage;
    }

    if (storageType == StorageType.REGULAR_WAREHOUSE) {
      this.regularWarehouse = storage;
    }

    if (storageType == StorageType.ACCOUNT_WAREHOUSE) {
      this.accountWarehouse = storage;
    }
  }

  public Storage getStorage(int storageType) {
    if (storageType == StorageType.REGULAR_WAREHOUSE.getId()) {
      return this.regularWarehouse;
    }
    if (storageType == StorageType.ACCOUNT_WAREHOUSE.getId()) {
      return this.accountWarehouse;
    }
    if (storageType == StorageType.LEGION_WAREHOUSE.getId()) {
      return (Storage) getLegion().getLegionWarehouse();
    }
    if (storageType == StorageType.CUBE.getId()) {
      return this.inventory;
    }
    return null;
  }

  public List<Item> getDirtyItemsToUpdate() {
    List<Item> dirtyItems = new ArrayList<Item>();

    Storage cubeStorage = getStorage(StorageType.CUBE.getId());
    if (cubeStorage.getPersistentState() == PersistentState.UPDATE_REQUIRED) {

      dirtyItems.addAll(cubeStorage.getAllItems());
      dirtyItems.addAll(cubeStorage.getDeletedItems());
      cubeStorage.setPersistentState(PersistentState.UPDATED);
    }

    Storage regularWhStorage = getStorage(StorageType.REGULAR_WAREHOUSE.getId());
    if (regularWhStorage.getPersistentState() == PersistentState.UPDATE_REQUIRED) {

      dirtyItems.addAll(regularWhStorage.getAllItems());
      dirtyItems.addAll(regularWhStorage.getDeletedItems());
      regularWhStorage.setPersistentState(PersistentState.UPDATED);
    }

    Storage accountWhStorage = getStorage(StorageType.ACCOUNT_WAREHOUSE.getId());
    if (accountWhStorage.getPersistentState() == PersistentState.UPDATE_REQUIRED) {

      dirtyItems.addAll(accountWhStorage.getAllItems());
      dirtyItems.addAll(accountWhStorage.getDeletedItems());
      accountWhStorage.setPersistentState(PersistentState.UPDATED);
    }

    Equipment equipment = getEquipment();
    if (equipment.getPersistentState() == PersistentState.UPDATE_REQUIRED) {

      dirtyItems.addAll(equipment.getEquippedItems());
      equipment.setPersistentState(PersistentState.UPDATED);
    }

    return dirtyItems;
  }

  public List<Item> getAllItems() {
    List<Item> allItems = new ArrayList<Item>();

    Storage cubeStorage = getStorage(StorageType.CUBE.getId());
    allItems.addAll(cubeStorage.getAllItems());

    Storage regularWhStorage = getStorage(StorageType.REGULAR_WAREHOUSE.getId());
    allItems.addAll(regularWhStorage.getStorageItems());

    Storage accountWhStorage = getStorage(StorageType.ACCOUNT_WAREHOUSE.getId());
    allItems.addAll(accountWhStorage.getStorageItems());

    Equipment equipment = getEquipment();
    allItems.addAll(equipment.getEquippedItems());

    return allItems;
  }

  public Storage getInventory() {
    return this.inventory;
  }

  public void setCubesize(int cubesize) {
    getCommonData().setCubesize(cubesize);
    getInventory().setLimit(getInventory().getLimit() + cubesize * 9);
  }

  public PlayerSettings getPlayerSettings() {
    return this.playerSettings;
  }

  public void setPlayerSettings(PlayerSettings playerSettings) {
    this.playerSettings = playerSettings;
  }

  public ZoneInstance getZoneInstance() {
    return this.zoneInstance;
  }

  public void setZoneInstance(ZoneInstance zoneInstance) {
    this.zoneInstance = zoneInstance;
  }

  public TitleList getTitleList() {
    return this.titleList;
  }

  public void setTitleList(TitleList titleList) {
    this.titleList = titleList;
    titleList.setOwner(this);
  }

  public PlayerGroup getPlayerGroup() {
    return this.playerGroup;
  }

  public void setPlayerGroup(PlayerGroup playerGroup) {
    this.playerGroup = playerGroup;
  }

  public AbyssRank getAbyssRank() {
    return this.abyssRank;
  }

  public void setAbyssRank(AbyssRank abyssRank) {
    this.abyssRank = abyssRank;
  }

  public PlayerEffectController getEffectController() {
    return (PlayerEffectController) super.getEffectController();
  }

  public void initializeAi() {
  }

  public void onLoggedIn() {
    getController().addTask(TaskId.PLAYER_UPDATE, ThreadPoolManager.getInstance().scheduleAtFixedRate(
        new GeneralUpdateTask(this), (OptionsConfig.PLAYER_GENERAL * 1000), (OptionsConfig.PLAYER_GENERAL * 1000)));
  }

  public void onLoggedOut() {
    this.requester.denyAll();
    this.friendList.setStatus(FriendList.Status.OFFLINE);
    BrokerService.getInstance().removePlayerCache(this);
    ExchangeService.getInstance().cancelExchange(this);
  }

  public boolean isLegionMember() {
    return (this.legionMember != null);
  }

  public void setLegionMember(LegionMember legionMember) {
    this.legionMember = legionMember;
  }

  public LegionMember getLegionMember() {
    return this.legionMember;
  }

  public Legion getLegion() {
    if (this.legionMember != null) {
      return this.legionMember.getLegion();
    }
    return null;
  }

  public boolean sameObjectId(int objectId) {
    return (getObjectId() == objectId);
  }

  public boolean hasStore() {
    if (getStore() != null)
      return true;
    return false;
  }

  public void resetLegionMember() {
    setLegionMember((LegionMember) null);
  }

  public boolean isInGroup() {
    return (this.playerGroup != null);
  }

  public byte getAccessLevel() {
    return getClientConnection().getAccount().getAccessLevel();
  }

  public String getAcountName() {
    return getClientConnection().getAccount().getName();
  }

  public Rates getRates() {
    if (this.rates == null)
      this.rates = (Rates) new RegularRates();
    return this.rates;
  }

  public void setRates(Rates rates) {
    this.rates = rates;
  }

  public int getWarehouseSize() {
    return getCommonData().getWarehouseSize();
  }

  public void setWarehouseSize(int warehouseSize) {
    getCommonData().setWarehouseSize(warehouseSize);
    getWarehouse().setLimit(getWarehouse().getLimit() + warehouseSize * 8);
  }

  public Storage getWarehouse() {
    return this.regularWarehouse;
  }

  public int getFlyState() {
    return this.flyState;
  }

  public void setFlyState(int flyState) {
    this.flyState = flyState;
  }

  public boolean isTrading() {
    return this.isTrading;
  }

  public void setTrading(boolean isTrading) {
    this.isTrading = isTrading;
  }

  public boolean isInPrison() {
    return (this.prisonTimer != 0L);
  }

  public void setPrisonTimer(long prisonTimer) {
    if (prisonTimer < 0L) {
      prisonTimer = 0L;
    }
    this.prisonTimer = prisonTimer;
  }

  public long getPrisonTimer() {
    return this.prisonTimer;
  }

  public long getStartPrison() {
    return this.startPrison;
  }

  public void setStartPrison(long start) {
    this.startPrison = start;
  }

  public boolean isProtectionActive() {
    return isInVisualState(CreatureVisualState.BLINKING);
  }

  public boolean isInvul() {
    return this.invul;
  }

  public void setInvul(boolean invul) {
    this.invul = invul;
  }

  public void setMailbox(Mailbox mailbox) {
    this.mailbox = mailbox;
  }

  public Mailbox getMailbox() {
    return this.mailbox;
  }

  public FlyController getFlyController() {
    return this.flyController;
  }

  public void setFlyController(FlyController flyController) {
    this.flyController = flyController;
  }

  public ReviveController getReviveController() {
    return this.reviveController;
  }

  public void setReviveController(ReviveController reviveController) {
    this.reviveController = reviveController;
  }

  public int getLastOnline() {
    Timestamp lastOnline = getCommonData().getLastOnline();
    if (lastOnline == null || isOnline()) {
      return 0;
    }
    return (int) (lastOnline.getTime() / 1000L);
  }

  public void setCraftingTask(CraftingTask craftingTask) {
    this.craftingTask = craftingTask;
  }

  public CraftingTask getCraftingTask() {
    return this.craftingTask;
  }

  public void setFlightTeleportId(int flightTeleportId) {
    this.flightTeleportId = flightTeleportId;
  }

  public int getFlightTeleportId() {
    return this.flightTeleportId;
  }

  public void setFlightDistance(int flightDistance) {
    this.flightDistance = flightDistance;
  }

  public int getFlightDistance() {
    return this.flightDistance;
  }

  public boolean isUsingFlyTeleport() {
    return (isInState(CreatureState.FLIGHT_TELEPORT) && this.flightTeleportId != 0);
  }

  public boolean isGM() {
    return (getAccessLevel() > 0);
  }

  public boolean isEnemyNpc(Npc npc) {
    return (npc instanceof com.aionemu.gameserver.model.gameobjects.Monster || npc.isAggressiveTo(this));
  }

  public boolean isEnemyPlayer(Player player) {
    return (player.getCommonData().getRace() != getCommonData().getRace() || getController().isDueling(player));
  }

  public boolean isEnemySummon(Summon summon) {
    return (summon.getMaster() != null && isEnemyPlayer(summon.getMaster()));
  }

  public boolean isFriend(Player player) {
    return (player.getCommonData().getRace() == getCommonData().getRace() && !getController().isDueling(player));
  }

  public String getTribe() {
    switch (getCommonData().getRace()) {

      case ELYOS:
        return "PC";
    }
    return "PC_DARK";
  }

  public boolean isAggressiveTo(Creature creature) {
    return creature.isAggroFrom(this);
  }

  public boolean isAggroFrom(Npc npc) {
    String currentTribe = npc.getTribe();

    if (npc.getLevel() + 10 <= getLevel()) {
      return false;
    }
    return isAggroIconTo(currentTribe);
  }

  public boolean isAggroIconTo(String npcTribe) {
    switch (getCommonData().getRace()) {

      case ELYOS:
        if (DataManager.TRIBE_RELATIONS_DATA.isGuardDark(npcTribe))
          return true;
        return DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(npcTribe, "PC");
      case ASMODIANS:
        if (DataManager.TRIBE_RELATIONS_DATA.isGuardLight(npcTribe))
          return true;
        return DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(npcTribe, "PC_DARK");
    }
    return false;
  }

  protected boolean canSeeNpc(Npc npc) {
    return true;
  }

  protected boolean canSeePlayer(Player player) {
    return (player.getVisualState() <= getSeeState());
  }

  public Summon getSummon() {
    return this.summon;
  }

  public void setSummon(Summon summon) {
    this.summon = summon;
  }

  public void setKisk(Kisk newKisk) {
    this.kisk = newKisk;
  }

  public Kisk getKisk() {
    return this.kisk;
  }

  public boolean isItemUseDisabled(int delayId) {
    if (this.itemCoolDowns == null || !this.itemCoolDowns.containsKey(Integer.valueOf(delayId))) {
      return false;
    }
    Long coolDown = Long.valueOf(((ItemCooldown) this.itemCoolDowns.get(Integer.valueOf(delayId))).getReuseTime());
    if (coolDown == null) {
      return false;
    }

    if (coolDown.longValue() < System.currentTimeMillis()) {

      this.itemCoolDowns.remove(Integer.valueOf(delayId));
      return false;
    }

    return true;
  }

  public long getItemCoolDown(int itemMask) {
    if (this.itemCoolDowns == null || !this.itemCoolDowns.containsKey(Integer.valueOf(itemMask))) {
      return 0L;
    }
    return ((ItemCooldown) this.itemCoolDowns.get(Integer.valueOf(itemMask))).getReuseTime();
  }

  public Map<Integer, ItemCooldown> getItemCoolDowns() {
    return this.itemCoolDowns;
  }

  public void addItemCoolDown(int delayId, long time, int useDelay) {
    if (this.itemCoolDowns == null) {
      this.itemCoolDowns = (Map<Integer, ItemCooldown>) (new FastMap()).shared();
    }
    this.itemCoolDowns.put(Integer.valueOf(delayId), new ItemCooldown(time, useDelay));
  }

  public void removeItemCoolDown(int itemMask) {
    if (this.itemCoolDowns == null)
      return;
    this.itemCoolDowns.remove(Integer.valueOf(itemMask));
  }

  public Prices getPrices() {
    return this.prices;
  }

  public void setGagged(boolean isGagged) {
    this.isGagged = isGagged;
  }

  public boolean isGagged() {
    return this.isGagged;
  }

  private class GeneralUpdateTask implements Runnable {
    private Player player;

    private GeneralUpdateTask(Player player) {
      this.player = player;
    }

    public void run() {
      try {
        ((AbyssRankDAO) DAOManager.getDAO(AbyssRankDAO.class)).storeAbyssRank(this.player);
        ((PlayerSkillListDAO) DAOManager.getDAO(PlayerSkillListDAO.class)).storeSkills(this.player);
        ((PlayerQuestListDAO) DAOManager.getDAO(PlayerQuestListDAO.class)).store(this.player);
        ((PlayerDAO) DAOManager.getDAO(PlayerDAO.class)).storePlayer(this.player);
      } catch (Exception ex) {

        Player.log
            .error(("Exception during periodic saving of player " + this.player.getName() + " " + ex.getCause() != null)
                ? ex.getCause().getMessage()
                : "null");
      }
    }
  }

  public void setPlayerAlliance(PlayerAlliance playerAlliance) {
    this.playerAlliance = playerAlliance;
  }

  public PlayerAlliance getPlayerAlliance() {
    return this.playerAlliance;
  }

  public boolean isInAlliance() {
    return (this.playerAlliance != null);
  }
}

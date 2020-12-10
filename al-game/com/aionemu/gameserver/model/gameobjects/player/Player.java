/*      */ package com.aionemu.gameserver.model.gameobjects.player;
/*      */ 
/*      */ import com.aionemu.commons.database.dao.DAOManager;
/*      */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*      */ import com.aionemu.gameserver.controllers.CreatureController;
/*      */ import com.aionemu.gameserver.controllers.FlyController;
/*      */ import com.aionemu.gameserver.controllers.PlayerController;
/*      */ import com.aionemu.gameserver.controllers.ReviveController;
/*      */ import com.aionemu.gameserver.controllers.VisibleObjectController;
/*      */ import com.aionemu.gameserver.controllers.effect.EffectController;
/*      */ import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
/*      */ import com.aionemu.gameserver.dao.AbyssRankDAO;
/*      */ import com.aionemu.gameserver.dao.PlayerDAO;
/*      */ import com.aionemu.gameserver.dao.PlayerQuestListDAO;
/*      */ import com.aionemu.gameserver.dao.PlayerSkillListDAO;
/*      */ import com.aionemu.gameserver.dataholders.DataManager;
/*      */ import com.aionemu.gameserver.model.Gender;
/*      */ import com.aionemu.gameserver.model.PlayerClass;
/*      */ import com.aionemu.gameserver.model.Race;
/*      */ import com.aionemu.gameserver.model.TaskId;
/*      */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*      */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*      */ import com.aionemu.gameserver.model.gameobjects.Item;
/*      */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*      */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*      */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*      */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*      */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*      */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*      */ import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
/*      */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*      */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
/*      */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
/*      */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
/*      */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*      */ import com.aionemu.gameserver.model.items.ItemCooldown;
/*      */ import com.aionemu.gameserver.model.legion.Legion;
/*      */ import com.aionemu.gameserver.model.legion.LegionMember;
/*      */ import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
/*      */ import com.aionemu.gameserver.network.aion.AionConnection;
/*      */ import com.aionemu.gameserver.services.BrokerService;
/*      */ import com.aionemu.gameserver.services.ExchangeService;
/*      */ import com.aionemu.gameserver.skillengine.task.CraftingTask;
/*      */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*      */ import com.aionemu.gameserver.utils.rates.Rates;
/*      */ import com.aionemu.gameserver.utils.rates.RegularRates;
/*      */ import com.aionemu.gameserver.world.zone.ZoneInstance;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javolution.util.FastMap;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Player
/*      */   extends Creature
/*      */ {
/*   81 */   private static final Logger log = Logger.getLogger(Player.class);
/*      */   
/*      */   private PlayerAppearance playerAppearance;
/*      */   private PlayerAppearance savedPlayerAppearance;
/*      */   private LegionMember legionMember;
/*      */   private MacroList macroList;
/*      */   private SkillList skillList;
/*      */   private FriendList friendList;
/*      */   private BlockList blockList;
/*      */   private ResponseRequester requester;
/*      */   private boolean lookingForGroup = false;
/*      */   private Storage inventory;
/*      */   private Storage regularWarehouse;
/*      */   private Storage accountWarehouse;
/*      */   private Equipment equipment;
/*      */   private Mailbox mailbox;
/*      */   private PrivateStore store;
/*      */   private PlayerStatsTemplate playerStatsTemplate;
/*      */   private TitleList titleList;
/*      */   private PlayerSettings playerSettings;
/*      */   private QuestStateList questStateList;
/*  102 */   private List<Integer> nearbyQuestList = new ArrayList<Integer>();
/*      */   private ZoneInstance zoneInstance;
/*      */   private PlayerGroup playerGroup;
/*      */   private AbyssRank abyssRank;
/*      */   private Rates rates;
/*      */   private RecipeList recipeList;
/*  108 */   private int flyState = 0;
/*      */   private boolean isTrading;
/*  110 */   private long prisonTimer = 0L;
/*      */   
/*      */   private long startPrison;
/*      */   
/*      */   private boolean invul;
/*      */   
/*      */   private FlyController flyController;
/*      */   
/*      */   private ReviveController reviveController;
/*      */   
/*      */   private CraftingTask craftingTask;
/*      */   
/*      */   private int flightTeleportId;
/*      */   
/*      */   private int flightDistance;
/*      */   
/*      */   private Summon summon;
/*      */   
/*      */   private Kisk kisk;
/*      */   private Prices prices;
/*      */   private boolean isGagged = false;
/*      */   private Map<Integer, ItemCooldown> itemCoolDowns;
/*      */   private static final int CUBE_SPACE = 9;
/*      */   private static final int WAREHOUSE_SPACE = 8;
/*      */   private AionConnection clientConnection;
/*      */   private PlayerAlliance playerAlliance;
/*      */   
/*      */   public Player(PlayerController controller, PlayerCommonData plCommonData, PlayerAppearance appereance) {
/*  138 */     super(plCommonData.getPlayerObjId(), (CreatureController)controller, null, plCommonData, plCommonData.getPosition());
/*  139 */     this.playerAppearance = appereance;
/*      */     
/*  141 */     this.prices = new Prices();
/*  142 */     this.requester = new ResponseRequester(this);
/*  143 */     this.questStateList = new QuestStateList();
/*  144 */     this.titleList = new TitleList();
/*  145 */     controller.setOwner((VisibleObject)this);
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerCommonData getCommonData() {
/*  150 */     return (PlayerCommonData)this.objectTemplate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommonData(PlayerCommonData playerCommonData) {
/*  158 */     this.objectTemplate = playerCommonData;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  164 */     return getCommonData().getName();
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerAppearance getPlayerAppearance() {
/*  169 */     return this.playerAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerAppearance(PlayerAppearance playerAppearance) {
/*  175 */     this.playerAppearance = playerAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerAppearance getSavedPlayerAppearance() {
/*  185 */     return this.savedPlayerAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSavedPlayerAppearance(PlayerAppearance savedPlayerAppearance) {
/*  195 */     this.savedPlayerAppearance = savedPlayerAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientConnection(AionConnection clientConnection) {
/*  205 */     this.clientConnection = clientConnection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AionConnection getClientConnection() {
/*  216 */     return this.clientConnection;
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroList getMacroList() {
/*  221 */     return this.macroList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMacroList(MacroList macroList) {
/*  226 */     this.macroList = macroList;
/*      */   }
/*      */ 
/*      */   
/*      */   public SkillList getSkillList() {
/*  231 */     return this.skillList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSkillList(SkillList skillList) {
/*  236 */     this.skillList = skillList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FriendList getFriendList() {
/*  246 */     return this.friendList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLookingForGroup() {
/*  256 */     return this.lookingForGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLookingForGroup(boolean lookingForGroup) {
/*  266 */     this.lookingForGroup = lookingForGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFriendList(FriendList list) {
/*  277 */     this.friendList = list;
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockList getBlockList() {
/*  282 */     return this.blockList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlockList(BlockList list) {
/*  287 */     this.blockList = list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerLifeStats getLifeStats() {
/*  296 */     return (PlayerLifeStats)super.getLifeStats();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLifeStats(PlayerLifeStats lifeStats) {
/*  305 */     setLifeStats((CreatureLifeStats)lifeStats);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerGameStats getGameStats() {
/*  314 */     return (PlayerGameStats)super.getGameStats();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameStats(PlayerGameStats gameStats) {
/*  323 */     setGameStats((CreatureGameStats)gameStats);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResponseRequester getResponseRequester() {
/*  333 */     return this.requester;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOnline() {
/*  338 */     return (getClientConnection() != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCubeSize() {
/*  343 */     return getCommonData().getCubeSize();
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerClass getPlayerClass() {
/*  348 */     return getCommonData().getPlayerClass();
/*      */   }
/*      */ 
/*      */   
/*      */   public Gender getGender() {
/*  353 */     return getCommonData().getGender();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerController getController() {
/*  364 */     return (PlayerController)super.getController();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getLevel() {
/*  370 */     return (byte)getCommonData().getLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Equipment getEquipment() {
/*  379 */     return this.equipment;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEquipment(Equipment equipment) {
/*  385 */     this.equipment = equipment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateStore getStore() {
/*  393 */     return this.store;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStore(PrivateStore store) {
/*  401 */     if (this.store != null && store == null)
/*  402 */       this.store.clear(); 
/*  403 */     this.store = store;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QuestStateList getQuestStateList() {
/*  411 */     return this.questStateList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuestStateList(QuestStateList questStateList) {
/*  420 */     this.questStateList = questStateList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerStatsTemplate getPlayerStatsTemplate() {
/*  428 */     return this.playerStatsTemplate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerStatsTemplate(PlayerStatsTemplate playerStatsTemplate) {
/*  437 */     this.playerStatsTemplate = playerStatsTemplate;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Integer> getNearbyQuests() {
/*  442 */     return this.nearbyQuestList;
/*      */   }
/*      */ 
/*      */   
/*      */   public RecipeList getRecipeList() {
/*  447 */     return this.recipeList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecipeList(RecipeList recipeList) {
/*  452 */     this.recipeList = recipeList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStorage(Storage storage, StorageType storageType) {
/*  461 */     if (storageType == StorageType.CUBE)
/*      */     {
/*  463 */       this.inventory = storage;
/*      */     }
/*      */     
/*  466 */     if (storageType == StorageType.REGULAR_WAREHOUSE)
/*      */     {
/*  468 */       this.regularWarehouse = storage;
/*      */     }
/*      */     
/*  471 */     if (storageType == StorageType.ACCOUNT_WAREHOUSE)
/*      */     {
/*  473 */       this.accountWarehouse = storage;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Storage getStorage(int storageType) {
/*  484 */     if (storageType == StorageType.REGULAR_WAREHOUSE.getId()) {
/*  485 */       return this.regularWarehouse;
/*      */     }
/*  487 */     if (storageType == StorageType.ACCOUNT_WAREHOUSE.getId()) {
/*  488 */       return this.accountWarehouse;
/*      */     }
/*  490 */     if (storageType == StorageType.LEGION_WAREHOUSE.getId()) {
/*  491 */       return (Storage)getLegion().getLegionWarehouse();
/*      */     }
/*  493 */     if (storageType == StorageType.CUBE.getId()) {
/*  494 */       return this.inventory;
/*      */     }
/*  496 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Item> getDirtyItemsToUpdate() {
/*  506 */     List<Item> dirtyItems = new ArrayList<Item>();
/*      */     
/*  508 */     Storage cubeStorage = getStorage(StorageType.CUBE.getId());
/*  509 */     if (cubeStorage.getPersistentState() == PersistentState.UPDATE_REQUIRED) {
/*      */       
/*  511 */       dirtyItems.addAll(cubeStorage.getAllItems());
/*  512 */       dirtyItems.addAll(cubeStorage.getDeletedItems());
/*  513 */       cubeStorage.setPersistentState(PersistentState.UPDATED);
/*      */     } 
/*      */     
/*  516 */     Storage regularWhStorage = getStorage(StorageType.REGULAR_WAREHOUSE.getId());
/*  517 */     if (regularWhStorage.getPersistentState() == PersistentState.UPDATE_REQUIRED) {
/*      */       
/*  519 */       dirtyItems.addAll(regularWhStorage.getAllItems());
/*  520 */       dirtyItems.addAll(regularWhStorage.getDeletedItems());
/*  521 */       regularWhStorage.setPersistentState(PersistentState.UPDATED);
/*      */     } 
/*      */     
/*  524 */     Storage accountWhStorage = getStorage(StorageType.ACCOUNT_WAREHOUSE.getId());
/*  525 */     if (accountWhStorage.getPersistentState() == PersistentState.UPDATE_REQUIRED) {
/*      */       
/*  527 */       dirtyItems.addAll(accountWhStorage.getAllItems());
/*  528 */       dirtyItems.addAll(accountWhStorage.getDeletedItems());
/*  529 */       accountWhStorage.setPersistentState(PersistentState.UPDATED);
/*      */     } 
/*      */     
/*  532 */     Equipment equipment = getEquipment();
/*  533 */     if (equipment.getPersistentState() == PersistentState.UPDATE_REQUIRED) {
/*      */       
/*  535 */       dirtyItems.addAll(equipment.getEquippedItems());
/*  536 */       equipment.setPersistentState(PersistentState.UPDATED);
/*      */     } 
/*      */     
/*  539 */     return dirtyItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Item> getAllItems() {
/*  548 */     List<Item> allItems = new ArrayList<Item>();
/*      */     
/*  550 */     Storage cubeStorage = getStorage(StorageType.CUBE.getId());
/*  551 */     allItems.addAll(cubeStorage.getAllItems());
/*      */     
/*  553 */     Storage regularWhStorage = getStorage(StorageType.REGULAR_WAREHOUSE.getId());
/*  554 */     allItems.addAll(regularWhStorage.getStorageItems());
/*      */     
/*  556 */     Storage accountWhStorage = getStorage(StorageType.ACCOUNT_WAREHOUSE.getId());
/*  557 */     allItems.addAll(accountWhStorage.getStorageItems());
/*      */     
/*  559 */     Equipment equipment = getEquipment();
/*  560 */     allItems.addAll(equipment.getEquippedItems());
/*      */     
/*  562 */     return allItems;
/*      */   }
/*      */ 
/*      */   
/*      */   public Storage getInventory() {
/*  567 */     return this.inventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCubesize(int cubesize) {
/*  576 */     getCommonData().setCubesize(cubesize);
/*  577 */     getInventory().setLimit(getInventory().getLimit() + cubesize * 9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerSettings getPlayerSettings() {
/*  585 */     return this.playerSettings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerSettings(PlayerSettings playerSettings) {
/*  594 */     this.playerSettings = playerSettings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneInstance getZoneInstance() {
/*  602 */     return this.zoneInstance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZoneInstance(ZoneInstance zoneInstance) {
/*  611 */     this.zoneInstance = zoneInstance;
/*      */   }
/*      */ 
/*      */   
/*      */   public TitleList getTitleList() {
/*  616 */     return this.titleList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTitleList(TitleList titleList) {
/*  621 */     this.titleList = titleList;
/*  622 */     titleList.setOwner(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerGroup getPlayerGroup() {
/*  630 */     return this.playerGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerGroup(PlayerGroup playerGroup) {
/*  639 */     this.playerGroup = playerGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbyssRank getAbyssRank() {
/*  647 */     return this.abyssRank;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAbyssRank(AbyssRank abyssRank) {
/*  656 */     this.abyssRank = abyssRank;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerEffectController getEffectController() {
/*  662 */     return (PlayerEffectController)super.getEffectController();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initializeAi() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLoggedIn() {
/*  677 */     getController().addTask(TaskId.PLAYER_UPDATE, ThreadPoolManager.getInstance().scheduleAtFixedRate(new GeneralUpdateTask(this), (OptionsConfig.PLAYER_GENERAL * 1000), (OptionsConfig.PLAYER_GENERAL * 1000)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLoggedOut() {
/*  688 */     this.requester.denyAll();
/*  689 */     this.friendList.setStatus(FriendList.Status.OFFLINE);
/*  690 */     BrokerService.getInstance().removePlayerCache(this);
/*  691 */     ExchangeService.getInstance().cancelExchange(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLegionMember() {
/*  699 */     return (this.legionMember != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegionMember(LegionMember legionMember) {
/*  708 */     this.legionMember = legionMember;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegionMember getLegionMember() {
/*  716 */     return this.legionMember;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Legion getLegion() {
/*  724 */     if (this.legionMember != null) {
/*  725 */       return this.legionMember.getLegion();
/*      */     }
/*  727 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sameObjectId(int objectId) {
/*  737 */     return (getObjectId() == objectId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasStore() {
/*  745 */     if (getStore() != null)
/*  746 */       return true; 
/*  747 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetLegionMember() {
/*  755 */     setLegionMember((LegionMember)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInGroup() {
/*  765 */     return (this.playerGroup != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getAccessLevel() {
/*  775 */     return getClientConnection().getAccount().getAccessLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAcountName() {
/*  785 */     return getClientConnection().getAccount().getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rates getRates() {
/*  793 */     if (this.rates == null)
/*  794 */       this.rates = (Rates)new RegularRates(); 
/*  795 */     return this.rates;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRates(Rates rates) {
/*  804 */     this.rates = rates;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWarehouseSize() {
/*  812 */     return getCommonData().getWarehouseSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWarehouseSize(int warehouseSize) {
/*  820 */     getCommonData().setWarehouseSize(warehouseSize);
/*  821 */     getWarehouse().setLimit(getWarehouse().getLimit() + warehouseSize * 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Storage getWarehouse() {
/*  829 */     return this.regularWarehouse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFlyState() {
/*  837 */     return this.flyState;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFlyState(int flyState) {
/*  842 */     this.flyState = flyState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTrading() {
/*  850 */     return this.isTrading;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrading(boolean isTrading) {
/*  858 */     this.isTrading = isTrading;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInPrison() {
/*  866 */     return (this.prisonTimer != 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrisonTimer(long prisonTimer) {
/*  874 */     if (prisonTimer < 0L) {
/*  875 */       prisonTimer = 0L;
/*      */     }
/*  877 */     this.prisonTimer = prisonTimer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getPrisonTimer() {
/*  885 */     return this.prisonTimer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getStartPrison() {
/*  893 */     return this.startPrison;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStartPrison(long start) {
/*  901 */     this.startPrison = start;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isProtectionActive() {
/*  909 */     return isInVisualState(CreatureVisualState.BLINKING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvul() {
/*  919 */     return this.invul;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInvul(boolean invul) {
/*  930 */     this.invul = invul;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMailbox(Mailbox mailbox) {
/*  935 */     this.mailbox = mailbox;
/*      */   }
/*      */ 
/*      */   
/*      */   public Mailbox getMailbox() {
/*  940 */     return this.mailbox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FlyController getFlyController() {
/*  948 */     return this.flyController;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlyController(FlyController flyController) {
/*  956 */     this.flyController = flyController;
/*      */   }
/*      */ 
/*      */   
/*      */   public ReviveController getReviveController() {
/*  961 */     return this.reviveController;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReviveController(ReviveController reviveController) {
/*  966 */     this.reviveController = reviveController;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLastOnline() {
/*  971 */     Timestamp lastOnline = getCommonData().getLastOnline();
/*  972 */     if (lastOnline == null || isOnline()) {
/*  973 */       return 0;
/*      */     }
/*  975 */     return (int)(lastOnline.getTime() / 1000L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCraftingTask(CraftingTask craftingTask) {
/*  984 */     this.craftingTask = craftingTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CraftingTask getCraftingTask() {
/*  993 */     return this.craftingTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlightTeleportId(int flightTeleportId) {
/* 1002 */     this.flightTeleportId = flightTeleportId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFlightTeleportId() {
/* 1011 */     return this.flightTeleportId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlightDistance(int flightDistance) {
/* 1020 */     this.flightDistance = flightDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFlightDistance() {
/* 1029 */     return this.flightDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingFlyTeleport() {
/* 1037 */     return (isInState(CreatureState.FLIGHT_TELEPORT) && this.flightTeleportId != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isGM() {
/* 1042 */     return (getAccessLevel() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnemyNpc(Npc npc) {
/* 1055 */     return (npc instanceof com.aionemu.gameserver.model.gameobjects.Monster || npc.isAggressiveTo(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnemyPlayer(Player player) {
/* 1069 */     return (player.getCommonData().getRace() != getCommonData().getRace() || getController().isDueling(player));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnemySummon(Summon summon) {
/* 1080 */     return (summon.getMaster() != null && isEnemyPlayer(summon.getMaster()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFriend(Player player) {
/* 1093 */     return (player.getCommonData().getRace() == getCommonData().getRace() && !getController().isDueling(player));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTribe() {
/* 1099 */     switch (getCommonData().getRace()) {
/*      */       
/*      */       case ELYOS:
/* 1102 */         return "PC";
/*      */     } 
/* 1104 */     return "PC_DARK";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAggressiveTo(Creature creature) {
/* 1111 */     return creature.isAggroFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAggroFrom(Npc npc) {
/* 1117 */     String currentTribe = npc.getTribe();
/*      */     
/* 1119 */     if (npc.getLevel() + 10 <= getLevel()) {
/* 1120 */       return false;
/*      */     }
/* 1122 */     return isAggroIconTo(currentTribe);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAggroIconTo(String npcTribe) {
/* 1133 */     switch (getCommonData().getRace()) {
/*      */       
/*      */       case ELYOS:
/* 1136 */         if (DataManager.TRIBE_RELATIONS_DATA.isGuardDark(npcTribe))
/* 1137 */           return true; 
/* 1138 */         return DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(npcTribe, "PC");
/*      */       case ASMODIANS:
/* 1140 */         if (DataManager.TRIBE_RELATIONS_DATA.isGuardLight(npcTribe))
/* 1141 */           return true; 
/* 1142 */         return DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(npcTribe, "PC_DARK");
/*      */     } 
/* 1144 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canSeeNpc(Npc npc) {
/* 1150 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canSeePlayer(Player player) {
/* 1156 */     return (player.getVisualState() <= getSeeState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Summon getSummon() {
/* 1164 */     return this.summon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSummon(Summon summon) {
/* 1172 */     this.summon = summon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKisk(Kisk newKisk) {
/* 1180 */     this.kisk = newKisk;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Kisk getKisk() {
/* 1188 */     return this.kisk;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemUseDisabled(int delayId) {
/* 1198 */     if (this.itemCoolDowns == null || !this.itemCoolDowns.containsKey(Integer.valueOf(delayId))) {
/* 1199 */       return false;
/*      */     }
/* 1201 */     Long coolDown = Long.valueOf(((ItemCooldown)this.itemCoolDowns.get(Integer.valueOf(delayId))).getReuseTime());
/* 1202 */     if (coolDown == null) {
/* 1203 */       return false;
/*      */     }
/*      */     
/* 1206 */     if (coolDown.longValue() < System.currentTimeMillis()) {
/*      */       
/* 1208 */       this.itemCoolDowns.remove(Integer.valueOf(delayId));
/* 1209 */       return false;
/*      */     } 
/*      */     
/* 1212 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getItemCoolDown(int itemMask) {
/* 1222 */     if (this.itemCoolDowns == null || !this.itemCoolDowns.containsKey(Integer.valueOf(itemMask))) {
/* 1223 */       return 0L;
/*      */     }
/* 1225 */     return ((ItemCooldown)this.itemCoolDowns.get(Integer.valueOf(itemMask))).getReuseTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, ItemCooldown> getItemCoolDowns() {
/* 1233 */     return this.itemCoolDowns;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItemCoolDown(int delayId, long time, int useDelay) {
/* 1244 */     if (this.itemCoolDowns == null) {
/* 1245 */       this.itemCoolDowns = (Map<Integer, ItemCooldown>)(new FastMap()).shared();
/*      */     }
/* 1247 */     this.itemCoolDowns.put(Integer.valueOf(delayId), new ItemCooldown(time, useDelay));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItemCoolDown(int itemMask) {
/* 1256 */     if (this.itemCoolDowns == null)
/*      */       return; 
/* 1258 */     this.itemCoolDowns.remove(Integer.valueOf(itemMask));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Prices getPrices() {
/* 1266 */     return this.prices;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGagged(boolean isGagged) {
/* 1274 */     this.isGagged = isGagged;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isGagged() {
/* 1282 */     return this.isGagged;
/*      */   }
/*      */   
/*      */   private class GeneralUpdateTask
/*      */     implements Runnable
/*      */   {
/*      */     private Player player;
/*      */     
/*      */     private GeneralUpdateTask(Player player) {
/* 1291 */       this.player = player;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/*      */       try {
/* 1299 */         ((AbyssRankDAO)DAOManager.getDAO(AbyssRankDAO.class)).storeAbyssRank(this.player);
/* 1300 */         ((PlayerSkillListDAO)DAOManager.getDAO(PlayerSkillListDAO.class)).storeSkills(this.player);
/* 1301 */         ((PlayerQuestListDAO)DAOManager.getDAO(PlayerQuestListDAO.class)).store(this.player);
/* 1302 */         ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).storePlayer(this.player);
/*      */       }
/* 1304 */       catch (Exception ex) {
/*      */         
/* 1306 */         Player.log.error(("Exception during periodic saving of player " + this.player.getName() + " " + ex.getCause() != null) ? ex.getCause().getMessage() : "null");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerAlliance(PlayerAlliance playerAlliance) {
/* 1314 */     this.playerAlliance = playerAlliance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerAlliance getPlayerAlliance() {
/* 1322 */     return this.playerAlliance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInAlliance() {
/* 1330 */     return (this.playerAlliance != null);
/*      */   }
/*      */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Player.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
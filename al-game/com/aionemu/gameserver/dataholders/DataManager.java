package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.dataholders.loadingutils.XmlDataLoader;
import org.apache.log4j.Logger;

public final class DataManager {
  static Logger log = Logger.getLogger(DataManager.class);

  public static NpcData NPC_DATA;

  public static GatherableData GATHERABLE_DATA;

  public static SpawnsData SPAWNS_DATA;

  public static WorldMapsData WORLD_MAPS_DATA;

  public static TradeListData TRADE_LIST_DATA;

  public static PlayerExperienceTable PLAYER_EXPERIENCE_TABLE;

  public static TeleporterData TELEPORTER_DATA;

  public static TeleLocationData TELELOCATION_DATA;

  public static CubeExpandData CUBEEXPANDER_DATA;

  public static WarehouseExpandData WAREHOUSEEXPANDER_DATA;

  public static BindPointData BIND_POINT_DATA;

  public static QuestsData QUEST_DATA;

  public static QuestScriptsData QUEST_SCRIPTS_DATA;

  public static PlayerStatsData PLAYER_STATS_DATA;

  public static SummonStatsData SUMMON_STATS_DATA;

  public static ItemData ITEM_DATA;

  public static TitleData TITLE_DATA;

  public static PlayerInitialData PLAYER_INITIAL_DATA;

  public static SkillData SKILL_DATA;

  public static SkillTreeData SKILL_TREE_DATA;

  public static WalkerData WALKER_DATA;

  public static ZoneData ZONE_DATA;

  public static GoodsListData GOODSLIST_DATA;

  public static TribeRelationsData TRIBE_RELATIONS_DATA;

  public static RecipeData RECIPE_DATA;

  public static PortalData PORTAL_DATA;

  public static ItemSetData ITEM_SET_DATA;

  public static NpcSkillData NPC_SKILL_DATA;

  public static PetSkillData PET_SKILL_DATA;

  public static SiegeLocationData SIEGE_LOCATION_DATA;

  private XmlDataLoader loader;

  public static final DataManager getInstance() {
    return SingletonHolder.instance;
  }

  private DataManager() {
    this.loader = XmlDataLoader.getInstance();
    StaticData data = this.loader.loadStaticData();

    WORLD_MAPS_DATA = data.worldMapsData;
    PLAYER_EXPERIENCE_TABLE = data.playerExperienceTable;
    PLAYER_STATS_DATA = data.playerStatsData;
    SUMMON_STATS_DATA = data.summonStatsData;
    ITEM_DATA = data.itemData;
    NPC_DATA = data.npcData;
    GATHERABLE_DATA = data.gatherableData;
    PLAYER_INITIAL_DATA = data.playerInitialData;
    SKILL_DATA = data.skillData;
    SKILL_TREE_DATA = data.skillTreeData;
    SPAWNS_DATA = data.spawnsData;
    TITLE_DATA = data.titleData;
    TRADE_LIST_DATA = data.tradeListData;
    TELEPORTER_DATA = data.teleporterData;
    TELELOCATION_DATA = data.teleLocationData;
    CUBEEXPANDER_DATA = data.cubeExpandData;
    WAREHOUSEEXPANDER_DATA = data.warehouseExpandData;
    BIND_POINT_DATA = data.bindPointData;
    QUEST_DATA = data.questData;
    QUEST_SCRIPTS_DATA = data.questsScriptData;
    ZONE_DATA = data.zoneData;
    WALKER_DATA = data.walkerData;
    GOODSLIST_DATA = data.goodsListData;
    TRIBE_RELATIONS_DATA = data.tribeRelationsData;
    RECIPE_DATA = data.recipeData;
    PORTAL_DATA = data.portalData;
    ITEM_SET_DATA = data.itemSetData;
    NPC_SKILL_DATA = data.npcSkillData;
    PET_SKILL_DATA = data.petSkillData;
    SIEGE_LOCATION_DATA = data.siegeLocationData;
  }

  private static class SingletonHolder {
    protected static final DataManager instance = new DataManager();
  }
}

/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.loadingutils.XmlDataLoader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataManager
/*     */ {
/*  38 */   static Logger log = Logger.getLogger(DataManager.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public static NpcData NPC_DATA;
/*     */ 
/*     */ 
/*     */   
/*     */   public static GatherableData GATHERABLE_DATA;
/*     */ 
/*     */ 
/*     */   
/*     */   public static SpawnsData SPAWNS_DATA;
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldMapsData WORLD_MAPS_DATA;
/*     */ 
/*     */ 
/*     */   
/*     */   public static TradeListData TRADE_LIST_DATA;
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlayerExperienceTable PLAYER_EXPERIENCE_TABLE;
/*     */ 
/*     */ 
/*     */   
/*     */   public static TeleporterData TELEPORTER_DATA;
/*     */ 
/*     */ 
/*     */   
/*     */   public static TeleLocationData TELELOCATION_DATA;
/*     */ 
/*     */   
/*     */   public static CubeExpandData CUBEEXPANDER_DATA;
/*     */ 
/*     */   
/*     */   public static WarehouseExpandData WAREHOUSEEXPANDER_DATA;
/*     */ 
/*     */   
/*     */   public static BindPointData BIND_POINT_DATA;
/*     */ 
/*     */   
/*     */   public static QuestsData QUEST_DATA;
/*     */ 
/*     */   
/*     */   public static QuestScriptsData QUEST_SCRIPTS_DATA;
/*     */ 
/*     */   
/*     */   public static PlayerStatsData PLAYER_STATS_DATA;
/*     */ 
/*     */   
/*     */   public static SummonStatsData SUMMON_STATS_DATA;
/*     */ 
/*     */   
/*     */   public static ItemData ITEM_DATA;
/*     */ 
/*     */   
/*     */   public static TitleData TITLE_DATA;
/*     */ 
/*     */   
/*     */   public static PlayerInitialData PLAYER_INITIAL_DATA;
/*     */ 
/*     */   
/*     */   public static SkillData SKILL_DATA;
/*     */ 
/*     */   
/*     */   public static SkillTreeData SKILL_TREE_DATA;
/*     */ 
/*     */   
/*     */   public static WalkerData WALKER_DATA;
/*     */ 
/*     */   
/*     */   public static ZoneData ZONE_DATA;
/*     */ 
/*     */   
/*     */   public static GoodsListData GOODSLIST_DATA;
/*     */ 
/*     */   
/*     */   public static TribeRelationsData TRIBE_RELATIONS_DATA;
/*     */ 
/*     */   
/*     */   public static RecipeData RECIPE_DATA;
/*     */ 
/*     */   
/*     */   public static PortalData PORTAL_DATA;
/*     */ 
/*     */   
/*     */   public static ItemSetData ITEM_SET_DATA;
/*     */ 
/*     */   
/*     */   public static NpcSkillData NPC_SKILL_DATA;
/*     */ 
/*     */   
/*     */   public static PetSkillData PET_SKILL_DATA;
/*     */ 
/*     */   
/*     */   public static SiegeLocationData SIEGE_LOCATION_DATA;
/*     */ 
/*     */   
/*     */   private XmlDataLoader loader;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final DataManager getInstance() {
/* 144 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private DataManager() {
/* 149 */     this.loader = XmlDataLoader.getInstance();
/* 150 */     StaticData data = this.loader.loadStaticData();
/*     */     
/* 152 */     WORLD_MAPS_DATA = data.worldMapsData;
/* 153 */     PLAYER_EXPERIENCE_TABLE = data.playerExperienceTable;
/* 154 */     PLAYER_STATS_DATA = data.playerStatsData;
/* 155 */     SUMMON_STATS_DATA = data.summonStatsData;
/* 156 */     ITEM_DATA = data.itemData;
/* 157 */     NPC_DATA = data.npcData;
/* 158 */     GATHERABLE_DATA = data.gatherableData;
/* 159 */     PLAYER_INITIAL_DATA = data.playerInitialData;
/* 160 */     SKILL_DATA = data.skillData;
/* 161 */     SKILL_TREE_DATA = data.skillTreeData;
/* 162 */     SPAWNS_DATA = data.spawnsData;
/* 163 */     TITLE_DATA = data.titleData;
/* 164 */     TRADE_LIST_DATA = data.tradeListData;
/* 165 */     TELEPORTER_DATA = data.teleporterData;
/* 166 */     TELELOCATION_DATA = data.teleLocationData;
/* 167 */     CUBEEXPANDER_DATA = data.cubeExpandData;
/* 168 */     WAREHOUSEEXPANDER_DATA = data.warehouseExpandData;
/* 169 */     BIND_POINT_DATA = data.bindPointData;
/* 170 */     QUEST_DATA = data.questData;
/* 171 */     QUEST_SCRIPTS_DATA = data.questsScriptData;
/* 172 */     ZONE_DATA = data.zoneData;
/* 173 */     WALKER_DATA = data.walkerData;
/* 174 */     GOODSLIST_DATA = data.goodsListData;
/* 175 */     TRIBE_RELATIONS_DATA = data.tribeRelationsData;
/* 176 */     RECIPE_DATA = data.recipeData;
/* 177 */     PORTAL_DATA = data.portalData;
/* 178 */     ITEM_SET_DATA = data.itemSetData;
/* 179 */     NPC_SKILL_DATA = data.npcSkillData;
/* 180 */     PET_SKILL_DATA = data.petSkillData;
/* 181 */     SIEGE_LOCATION_DATA = data.siegeLocationData;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 187 */     protected static final DataManager instance = new DataManager();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\DataManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
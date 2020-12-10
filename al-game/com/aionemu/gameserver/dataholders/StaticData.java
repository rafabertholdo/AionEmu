/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlRootElement(name = "ae_static_data")
/*     */ @XmlAccessorType(XmlAccessType.NONE)
/*     */ public class StaticData
/*     */ {
/*     */   @XmlElement(name = "world_maps")
/*     */   public WorldMapsData worldMapsData;
/*     */   @XmlElement(name = "npc_trade_list")
/*     */   public TradeListData tradeListData;
/*     */   @XmlElement(name = "npc_teleporter")
/*     */   public TeleporterData teleporterData;
/*     */   @XmlElement(name = "teleport_location")
/*     */   public TeleLocationData teleLocationData;
/*     */   @XmlElement(name = "bind_points")
/*     */   public BindPointData bindPointData;
/*     */   @XmlElement(name = "quests")
/*     */   public QuestsData questData;
/*     */   @XmlElement(name = "quest_scripts")
/*     */   public QuestScriptsData questsScriptData;
/*     */   @XmlElement(name = "player_experience_table")
/*     */   public PlayerExperienceTable playerExperienceTable;
/*     */   @XmlElement(name = "player_stats_templates")
/*     */   public PlayerStatsData playerStatsData;
/*     */   @XmlElement(name = "summon_stats_templates")
/*     */   public SummonStatsData summonStatsData;
/*     */   @XmlElement(name = "item_templates")
/*     */   public ItemData itemData;
/*     */   @XmlElement(name = "npc_templates")
/*     */   public NpcData npcData;
/*     */   @XmlElement(name = "player_initial_data")
/*     */   public PlayerInitialData playerInitialData;
/*     */   @XmlElement(name = "skill_data")
/*     */   public SkillData skillData;
/*     */   @XmlElement(name = "skill_tree")
/*     */   public SkillTreeData skillTreeData;
/*     */   @XmlElement(name = "cube_expander")
/*     */   public CubeExpandData cubeExpandData;
/*     */   @XmlElement(name = "warehouse_expander")
/*     */   public WarehouseExpandData warehouseExpandData;
/*     */   @XmlElement(name = "player_titles")
/*     */   public TitleData titleData;
/*     */   @XmlElement(name = "gatherable_templates")
/*     */   public GatherableData gatherableData;
/*     */   @XmlElement(name = "npc_walker")
/*     */   public WalkerData walkerData;
/*     */   @XmlElement(name = "zones")
/*     */   public ZoneData zoneData;
/*     */   @XmlElement(name = "goodslists")
/*     */   public GoodsListData goodsListData;
/*     */   @XmlElement(name = "spawns")
/*     */   public SpawnsData spawnsData;
/*     */   @XmlElement(name = "tribe_relations")
/*     */   public TribeRelationsData tribeRelationsData;
/*     */   @XmlElement(name = "recipe_templates")
/*     */   public RecipeData recipeData;
/*     */   @XmlElement(name = "portal_templates")
/*     */   public PortalData portalData;
/*     */   @XmlElement(name = "item_sets")
/*     */   public ItemSetData itemSetData;
/*     */   @XmlElement(name = "npc_skill_templates")
/*     */   public NpcSkillData npcSkillData;
/*     */   @XmlElement(name = "pet_skill_templates")
/*     */   public PetSkillData petSkillData;
/*     */   @XmlElement(name = "siege_locations")
/*     */   public SiegeLocationData siegeLocationData;
/*     */   
/*     */   private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
/* 129 */     DataManager.log.info("Loaded world maps data: " + this.worldMapsData.size() + " maps");
/* 130 */     DataManager.log.info("Loaded player exp table: " + this.playerExperienceTable.getMaxLevel() + " levels");
/* 131 */     DataManager.log.info("Loaded " + this.playerStatsData.size() + " player stat templates");
/* 132 */     DataManager.log.info("Loaded " + this.summonStatsData.size() + " summon stat templates");
/* 133 */     DataManager.log.info("Loaded " + this.itemData.size() + " item templates");
/* 134 */     DataManager.log.info("Loaded " + this.npcData.size() + " npc templates");
/* 135 */     DataManager.log.info("Loaded " + this.playerInitialData.size() + " initial player templates");
/* 136 */     DataManager.log.info("Loaded " + this.tradeListData.size() + " trade lists");
/* 137 */     DataManager.log.info("Loaded " + this.teleporterData.size() + " npc teleporter templates");
/* 138 */     DataManager.log.info("Loaded " + this.teleLocationData.size() + " teleport locations");
/* 139 */     DataManager.log.info("Loaded " + this.skillData.size() + " skill templates");
/* 140 */     DataManager.log.info("Loaded " + this.skillTreeData.size() + " skill learn entries");
/* 141 */     DataManager.log.info("Loaded " + this.cubeExpandData.size() + " cube expand entries");
/* 142 */     DataManager.log.info("Loaded " + this.warehouseExpandData.size() + " warehouse expand entries");
/* 143 */     DataManager.log.info("Loaded " + this.bindPointData.size() + " bind point entries");
/* 144 */     DataManager.log.info("Loaded " + this.questData.size() + " quest data entries");
/* 145 */     DataManager.log.info("Loaded " + this.gatherableData.size() + " gatherable entries");
/* 146 */     DataManager.log.info("Loaded " + this.titleData.size() + " title entries");
/* 147 */     DataManager.log.info("Loaded " + this.walkerData.size() + " walker routes");
/* 148 */     DataManager.log.info("Loaded " + this.zoneData.size() + " zone entries");
/* 149 */     DataManager.log.info("Loaded " + this.goodsListData.size() + " goodslist entries");
/* 150 */     DataManager.log.info("Loaded " + this.spawnsData.size() + " spawn entries");
/* 151 */     DataManager.log.info("Loaded " + this.tribeRelationsData.size() + " tribe relation entries");
/* 152 */     DataManager.log.info("Loaded " + this.recipeData.size() + " recipe entries");
/* 153 */     DataManager.log.info("Loaded " + this.portalData.size() + " portal entries");
/* 154 */     DataManager.log.info("Loaded " + this.itemSetData.size() + " item set entries");
/* 155 */     DataManager.log.info("Loaded " + this.npcSkillData.size() + " npc skill list entries");
/* 156 */     DataManager.log.info("Loaded " + this.petSkillData.size() + " pet skill list entries");
/* 157 */     DataManager.log.info("Loaded " + this.siegeLocationData.size() + " siege location entries");
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\StaticData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
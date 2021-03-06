package com.aionemu.gameserver.dataholders;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ae_static_data")
@XmlAccessorType(XmlAccessType.NONE)
public class StaticData {
  @XmlElement(name = "world_maps")
  public WorldMapsData worldMapsData;
  @XmlElement(name = "npc_trade_list")
  public TradeListData tradeListData;
  @XmlElement(name = "npc_teleporter")
  public TeleporterData teleporterData;
  @XmlElement(name = "teleport_location")
  public TeleLocationData teleLocationData;
  @XmlElement(name = "bind_points")
  public BindPointData bindPointData;
  @XmlElement(name = "quests")
  public QuestsData questData;
  @XmlElement(name = "quest_scripts")
  public QuestScriptsData questsScriptData;
  @XmlElement(name = "player_experience_table")
  public PlayerExperienceTable playerExperienceTable;
  @XmlElement(name = "player_stats_templates")
  public PlayerStatsData playerStatsData;
  @XmlElement(name = "summon_stats_templates")
  public SummonStatsData summonStatsData;
  @XmlElement(name = "item_templates")
  public ItemData itemData;
  @XmlElement(name = "npc_templates")
  public NpcData npcData;
  @XmlElement(name = "player_initial_data")
  public PlayerInitialData playerInitialData;
  @XmlElement(name = "skill_data")
  public SkillData skillData;
  @XmlElement(name = "skill_tree")
  public SkillTreeData skillTreeData;
  @XmlElement(name = "cube_expander")
  public CubeExpandData cubeExpandData;
  @XmlElement(name = "warehouse_expander")
  public WarehouseExpandData warehouseExpandData;
  @XmlElement(name = "player_titles")
  public TitleData titleData;
  @XmlElement(name = "gatherable_templates")
  public GatherableData gatherableData;
  @XmlElement(name = "npc_walker")
  public WalkerData walkerData;
  @XmlElement(name = "zones")
  public ZoneData zoneData;
  @XmlElement(name = "goodslists")
  public GoodsListData goodsListData;
  @XmlElement(name = "spawns")
  public SpawnsData spawnsData;
  @XmlElement(name = "tribe_relations")
  public TribeRelationsData tribeRelationsData;
  @XmlElement(name = "recipe_templates")
  public RecipeData recipeData;
  @XmlElement(name = "portal_templates")
  public PortalData portalData;
  @XmlElement(name = "item_sets")
  public ItemSetData itemSetData;
  @XmlElement(name = "npc_skill_templates")
  public NpcSkillData npcSkillData;
  @XmlElement(name = "pet_skill_templates")
  public PetSkillData petSkillData;
  @XmlElement(name = "siege_locations")
  public SiegeLocationData siegeLocationData;

  private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
    DataManager.log.info("Loaded world maps data: " + this.worldMapsData.size() + " maps");
    DataManager.log.info("Loaded player exp table: " + this.playerExperienceTable.getMaxLevel() + " levels");
    DataManager.log.info("Loaded " + this.playerStatsData.size() + " player stat templates");
    DataManager.log.info("Loaded " + this.summonStatsData.size() + " summon stat templates");
    DataManager.log.info("Loaded " + this.itemData.size() + " item templates");
    DataManager.log.info("Loaded " + this.npcData.size() + " npc templates");
    DataManager.log.info("Loaded " + this.playerInitialData.size() + " initial player templates");
    DataManager.log.info("Loaded " + this.tradeListData.size() + " trade lists");
    DataManager.log.info("Loaded " + this.teleporterData.size() + " npc teleporter templates");
    DataManager.log.info("Loaded " + this.teleLocationData.size() + " teleport locations");
    DataManager.log.info("Loaded " + this.skillData.size() + " skill templates");
    DataManager.log.info("Loaded " + this.skillTreeData.size() + " skill learn entries");
    DataManager.log.info("Loaded " + this.cubeExpandData.size() + " cube expand entries");
    DataManager.log.info("Loaded " + this.warehouseExpandData.size() + " warehouse expand entries");
    DataManager.log.info("Loaded " + this.bindPointData.size() + " bind point entries");
    DataManager.log.info("Loaded " + this.questData.size() + " quest data entries");
    DataManager.log.info("Loaded " + this.gatherableData.size() + " gatherable entries");
    DataManager.log.info("Loaded " + this.titleData.size() + " title entries");
    DataManager.log.info("Loaded " + this.walkerData.size() + " walker routes");
    DataManager.log.info("Loaded " + this.zoneData.size() + " zone entries");
    DataManager.log.info("Loaded " + this.goodsListData.size() + " goodslist entries");
    DataManager.log.info("Loaded " + this.spawnsData.size() + " spawn entries");
    DataManager.log.info("Loaded " + this.tribeRelationsData.size() + " tribe relation entries");
    DataManager.log.info("Loaded " + this.recipeData.size() + " recipe entries");
    DataManager.log.info("Loaded " + this.portalData.size() + " portal entries");
    DataManager.log.info("Loaded " + this.itemSetData.size() + " item set entries");
    DataManager.log.info("Loaded " + this.npcSkillData.size() + " npc skill list entries");
    DataManager.log.info("Loaded " + this.petSkillData.size() + " pet skill list entries");
    DataManager.log.info("Loaded " + this.siegeLocationData.size() + " siege location entries");
  }
}

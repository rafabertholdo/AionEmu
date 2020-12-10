package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.DayNightSpawnManager;
import gnu.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "spawns")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpawnsData {
  @XmlElement(name = "spawn")
  protected List<SpawnGroup> spawnGroups;
  @XmlTransient
  private TIntObjectHashMap<ArrayList<SpawnGroup>> spawnsByMapId = new TIntObjectHashMap();

  @XmlTransient
  private TIntObjectHashMap<ArrayList<SpawnGroup>> spawnsByNpcID = new TIntObjectHashMap();

  @XmlTransient
  private TIntObjectHashMap<ArrayList<SpawnGroup>> spawnsByMapIdNew = new TIntObjectHashMap();
  @XmlTransient
  private int counter = 0;

  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.spawnsByMapId.clear();
    this.spawnsByMapIdNew.clear();
    this.spawnsByNpcID.clear();

    for (SpawnGroup spawnGroup : this.spawnGroups) {

      for (SpawnTemplate template : spawnGroup.getObjects()) {

        template.setSpawnGroup(spawnGroup);
        if (spawnGroup.getSpawnTime() != null) {
          DayNightSpawnManager.getInstance().addSpawnTemplate(template);
        }
      }

      if (spawnGroup.getSpawnTime() != null)
        continue;
      addNewSpawnGroup(spawnGroup, spawnGroup.getMapid(), spawnGroup.getNpcid(), false);
      this.counter += spawnGroup.getObjects().size();
    }
    this.spawnGroups = null;
  }

  public SpawnTemplate getFirstSpawnByNpcId(int npcId) {
    List<SpawnGroup> spawnGroups = (List<SpawnGroup>) this.spawnsByNpcID.get(npcId);
    if (spawnGroups == null) {
      return null;
    }
    for (SpawnGroup spawnGroup : spawnGroups) {

      if (spawnGroup.getObjects() != null) {
        return spawnGroup.getObjects().get(0);
      }
    }
    return null;
  }

  public List<SpawnGroup> getSpawnsForWorld(int worldId) {
    return (List<SpawnGroup>) this.spawnsByMapId.get(worldId);
  }

  public List<SpawnGroup> getNewSpawnsForWorld(int worldId) {
    return (List<SpawnGroup>) this.spawnsByMapIdNew.get(worldId);
  }

  public int size() {
    return this.counter;
  }

  public void addNewSpawnGroup(SpawnGroup spawnGroup, int worldId, int npcId, boolean isNew) {
    ArrayList<SpawnGroup> mapSpawnGroups = (ArrayList<SpawnGroup>) this.spawnsByMapId.get(worldId);
    if (mapSpawnGroups == null) {

      mapSpawnGroups = new ArrayList<SpawnGroup>();
      this.spawnsByMapId.put(worldId, mapSpawnGroups);
    }
    mapSpawnGroups.add(spawnGroup);

    ArrayList<SpawnGroup> npcIdSpawnGroups = (ArrayList<SpawnGroup>) this.spawnsByNpcID.get(npcId);
    if (npcIdSpawnGroups == null) {

      npcIdSpawnGroups = new ArrayList<SpawnGroup>();
      this.spawnsByNpcID.put(npcId, npcIdSpawnGroups);
    }
    npcIdSpawnGroups.add(spawnGroup);

    if (isNew) {

      ArrayList<SpawnGroup> mapNewSpawnGroups = (ArrayList<SpawnGroup>) this.spawnsByMapIdNew.get(worldId);
      if (mapNewSpawnGroups == null) {

        mapNewSpawnGroups = new ArrayList<SpawnGroup>();
        this.spawnsByMapIdNew.put(worldId, mapNewSpawnGroups);
      }
      mapNewSpawnGroups.add(spawnGroup);
    }
  }

  public void removeSpawn(SpawnTemplate spawn) {
    if (spawn.getSpawnGroup().size() > 1) {

      spawn.getSpawnGroup().getObjects().remove(spawn);

      return;
    }
    List<SpawnGroup> worldSpawns = (List<SpawnGroup>) this.spawnsByMapId.get(spawn.getWorldId());
    if (worldSpawns != null) {
      worldSpawns.remove(spawn.getSpawnGroup());
    }

    List<SpawnGroup> worldNewSpawns = (List<SpawnGroup>) this.spawnsByMapIdNew.get(spawn.getWorldId());
    if (worldNewSpawns != null) {
      worldNewSpawns.remove(spawn.getSpawnGroup());
    }

    List<SpawnGroup> spawnsByNpc = (List<SpawnGroup>) this.spawnsByNpcID.get(spawn.getSpawnGroup().getNpcid());
    if (spawnsByNpc != null) {
      spawnsByNpc.remove(spawn.getSpawnGroup());
    }
  }

  public List<SpawnGroup> getSpawnGroups() {
    if (this.spawnGroups == null)
      this.spawnGroups = new ArrayList<SpawnGroup>();
    return this.spawnGroups;
  }

  public void setSpawns(List<SpawnGroup> spawns) {
    this.spawnGroups = spawns;
    afterUnmarshal(null, null);
  }
}

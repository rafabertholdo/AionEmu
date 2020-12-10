package com.aionemu.gameserver.world;

import com.aionemu.gameserver.model.templates.WorldMapTemplate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javolution.util.FastMap;

public class WorldMap {
  private WorldMapTemplate worldMapTemplate;
  private AtomicInteger nextInstanceId = new AtomicInteger(0);

  private Map<Integer, WorldMapInstance> instances = (Map<Integer, WorldMapInstance>) (new FastMap()).shared();

  private World world;

  public WorldMap(WorldMapTemplate worldMapTemplate, World world) {
    this.world = world;
    this.worldMapTemplate = worldMapTemplate;

    if (worldMapTemplate.getTwinCount() != 0) {

      for (int i = 1; i <= worldMapTemplate.getTwinCount(); i++) {
        int nextId = getNextInstanceId();
        addInstance(nextId);
      }

    } else {

      int nextId = getNextInstanceId();
      addInstance(nextId);
    }
  }

  public String getName() {
    return this.worldMapTemplate.getName();
  }

  public int getWaterLevel() {
    return this.worldMapTemplate.getWaterLevel();
  }

  public int getDeathLevel() {
    return this.worldMapTemplate.getDeathLevel();
  }

  public WorldType getWorldType() {
    return this.worldMapTemplate.getWorldType();
  }

  public Integer getMapId() {
    return this.worldMapTemplate.getMapId();
  }

  public int getInstanceCount() {
    int twinCount = this.worldMapTemplate.getTwinCount();
    return (twinCount > 0) ? twinCount : 1;
  }

  public WorldMapInstance getWorldMapInstance() {
    return getWorldMapInstance(1);
  }

  public WorldMapInstance getWorldMapInstanceById(int instanceId) {
    if (this.worldMapTemplate.getTwinCount() != 0) {
      if (instanceId > this.worldMapTemplate.getTwinCount()) {
        throw new IllegalArgumentException(
            "WorldMapInstance " + this.worldMapTemplate.getMapId() + " has lower instances count than " + instanceId);
      }
    }
    return getWorldMapInstance(instanceId);
  }

  private WorldMapInstance getWorldMapInstance(int instanceId) {
    return this.instances.get(Integer.valueOf(instanceId));
  }

  public void removeWorldMapInstance(int instanceId) {
    this.instances.remove(Integer.valueOf(instanceId));
  }

  public WorldMapInstance addInstance(int instanceId) {
    WorldMapInstance worldMapInstance = null;
    if (this.worldMapTemplate.getMapId().intValue() == WorldMapType.RESHANTA.getId()) {
      worldMapInstance = new WorldMapInstance3D(this, instanceId);
    } else {
      worldMapInstance = new WorldMapInstance(this, instanceId);
    }
    this.instances.put(Integer.valueOf(instanceId), worldMapInstance);
    return worldMapInstance;
  }

  public World getWorld() {
    return this.world;
  }

  public int getNextInstanceId() {
    return this.nextInstanceId.incrementAndGet();
  }

  public boolean isInstanceType() {
    return this.worldMapTemplate.isInstance();
  }

  public Collection<WorldMapInstance> getAllWorldMapInstances() {
    return this.instances.values();
  }

  public Set<Integer> getInstanceIds() {
    return this.instances.keySet();
  }
}

package com.aionemu.gameserver.world;

import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import javolution.util.FastMap;

public class WorldMapInstance {
  public static final int regionSize = OptionsConfig.REGION_SIZE;

  protected static final int maxWorldSize = OptionsConfig.MAX_WORLD_SIZE;

  protected static final int offset = 1000;

  private final WorldMap parent;

  protected final Map<Integer, MapRegion> regions = (Map<Integer, MapRegion>) (new FastMap()).shared();

  private final Map<Integer, VisibleObject> worldMapObjects = (Map<Integer, VisibleObject>) (new FastMap()).shared();

  private final Map<Integer, Player> worldMapPlayers = (Map<Integer, Player>) (new FastMap()).shared();

  private final Set<Integer> registeredObjects = Collections
      .newSetFromMap((Map<Integer, Boolean>) (new FastMap()).shared());

  private PlayerGroup registeredGroup = null;

  private Future<?> emptyInstanceTask = null;

  private int instanceId;

  public WorldMapInstance(WorldMap parent, int instanceId) {
    this.parent = parent;
    this.instanceId = instanceId;
  }

  public Integer getMapId() {
    return getParent().getMapId();
  }

  public WorldMap getParent() {
    return this.parent;
  }

  MapRegion getRegion(VisibleObject object) {
    return getRegion(object.getX(), object.getY(), object.getZ());
  }

  MapRegion getRegion(float x, float y, float z) {
    Integer regionId = getRegionId(x, y, z);
    MapRegion region = this.regions.get(regionId);
    if (region == null) {
      synchronized (this) {

        region = this.regions.get(regionId);
        if (region == null) {
          region = createMapRegion(regionId);
        }
      }
    }
    return region;
  }

  protected Integer getRegionId(float x, float y, float z) {
    return Integer.valueOf(((int) x + 1000) / regionSize * maxWorldSize + ((int) y + 1000) / regionSize);
  }

  protected MapRegion createMapRegion(Integer regionId) {
    MapRegion r = new MapRegion(regionId, this, false);
    this.regions.put(regionId, r);

    int rx = regionId.intValue() / maxWorldSize;
    int ry = regionId.intValue() % maxWorldSize;

    for (int x = rx - 1; x <= rx + 1; x++) {

      for (int y = ry - 1; y <= ry + 1; y++) {

        if (x != rx || y != ry) {

          int neighbourId = x * maxWorldSize + y;

          MapRegion neighbour = this.regions.get(Integer.valueOf(neighbourId));
          if (neighbour != null) {

            r.addNeighbourRegion(neighbour);
            neighbour.addNeighbourRegion(r);
          }
        }
      }
    }
    return r;
  }

  public World getWorld() {
    return getParent().getWorld();
  }

  public void addObject(VisibleObject object) {
    if (this.worldMapObjects.put(Integer.valueOf(object.getObjectId()), object) != null) {
      throw new DuplicateAionObjectException();
    }
    if (object instanceof Player) {
      this.worldMapPlayers.put(Integer.valueOf(object.getObjectId()), (Player) object);
    }
  }

  public void removeObject(AionObject object) {
    this.worldMapObjects.remove(Integer.valueOf(object.getObjectId()));
    if (object instanceof Player) {
      this.worldMapPlayers.remove(Integer.valueOf(object.getObjectId()));
    }
  }

  public int getInstanceId() {
    return this.instanceId;
  }

  public boolean isInInstance(int objId) {
    return this.worldMapPlayers.containsKey(Integer.valueOf(objId));
  }

  public Collection<VisibleObject> getAllWorldMapObjects() {
    return this.worldMapObjects.values();
  }

  public Collection<Player> getAllWorldMapPlayers() {
    return this.worldMapPlayers.values();
  }

  public void registerGroup(PlayerGroup group) {
    this.registeredGroup = group;
    register(group.getGroupId());
  }

  public void register(int objectId) {
    this.registeredObjects.add(Integer.valueOf(objectId));
  }

  public int getRegisteredObjectsSize() {
    return this.registeredObjects.size();
  }

  public boolean isRegistered(int objectId) {
    return this.registeredObjects.contains(Integer.valueOf(objectId));
  }

  public Future<?> getEmptyInstanceTask() {
    return this.emptyInstanceTask;
  }

  public void setEmptyInstanceTask(Future<?> emptyInstanceTask) {
    this.emptyInstanceTask = emptyInstanceTask;
  }

  public PlayerGroup getRegisteredGroup() {
    return this.registeredGroup;
  }

  public int playersCount() {
    return this.worldMapPlayers.size();
  }
}

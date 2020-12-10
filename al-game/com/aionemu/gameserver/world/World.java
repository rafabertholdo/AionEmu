package com.aionemu.gameserver.world;

import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.WorldMapTemplate;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.exceptions.AlreadySpawnedException;
import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
import com.aionemu.gameserver.world.exceptions.WorldMapNotExistException;
import java.util.Collection;
import java.util.Map;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class World {
  private static final Logger log = Logger.getLogger(World.class);

  private final FastMap<String, Player> playersByName = (new FastMap(NetworkConfig.MAX_ONLINE_PLAYERS)).shared();
  private final FastMap<Integer, AionObject> allObjects = (new FastMap(100000)).shared();
  private final Map<Integer, WorldMap> worldMaps = (Map<Integer, WorldMap>) (new FastMap()).shared();

  private World() {
    for (WorldMapTemplate template : DataManager.WORLD_MAPS_DATA) {
      this.worldMaps.put(template.getMapId(), new WorldMap(template, this));
    }
    log.info("World: " + this.worldMaps.size() + " worlds map created.");
  }

  public static final World getInstance() {
    return SingletonHolder.instance;
  }

  public void storeObject(AionObject object) {
    if (this.allObjects.put(Integer.valueOf(object.getObjectId()), object) != null) {
      throw new DuplicateAionObjectException();
    }
    if (object instanceof Player) {
      this.playersByName.put(object.getName().toLowerCase(), object);
    }
  }

  public void removeObject(AionObject object) {
    this.allObjects.remove(Integer.valueOf(object.getObjectId()));
    if (object instanceof Player)
      this.playersByName.remove(object.getName().toLowerCase());
    if (object instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
      IDFactory.getInstance().releaseId(object.getObjectId());
    }
  }

  public Collection<Player> getAllPlayers() {
    return this.playersByName.values();
  }

  public Collection<AionObject> getAllObjects() {
    return this.allObjects.values();
  }

  public Player findPlayer(String name) {
    return (Player) this.playersByName.get(name.toLowerCase());
  }

  public Player findPlayer(int objectId) {
    AionObject object = (AionObject) this.allObjects.get(Integer.valueOf(objectId));
    if (object instanceof Player)
      return (Player) this.allObjects.get(Integer.valueOf(objectId));
    return null;
  }

  public AionObject findAionObject(int objectId) {
    return (AionObject) this.allObjects.get(Integer.valueOf(objectId));
  }

  public WorldMap getWorldMap(int id) {
    WorldMap map = this.worldMaps.get(Integer.valueOf(id));

    if (map == null)
      throw new WorldMapNotExistException("Map: " + id + " not exist!");
    return map;
  }

  public void updatePosition(VisibleObject object, float newX, float newY, float newZ, byte newHeading) {
    updatePosition(object, newX, newY, newZ, newHeading, true);
  }

  public void updatePosition(VisibleObject object, float newX, float newY, float newZ, byte newHeading,
      boolean updateKnownList) {
    if (!object.isSpawned()) {
      return;
    }
    object.getPosition().setXYZH(newX, newY, newZ, newHeading);

    MapRegion oldRegion = object.getActiveRegion();
    if (oldRegion == null) {

      log.warn(String.format("CHECKPOINT: oldregion is null, object coordinates - %d %d %d",
          new Object[] { Float.valueOf(object.getX()), Float.valueOf(object.getY()), Float.valueOf(object.getY()) }));

      return;
    }
    MapRegion newRegion = oldRegion.getParent().getRegion(object);

    if (newRegion != oldRegion) {

      oldRegion.remove(object);
      newRegion.add(object);
      object.getPosition().setMapRegion(newRegion);
    }

    if (updateKnownList) {
      object.getKnownList().updateKnownList();
    }
  }

  public void setPosition(VisibleObject object, int mapId, float x, float y, float z, byte heading) {
    int instanceId = 1;
    if (object.getWorldId() == mapId) {
      instanceId = object.getInstanceId();
    }
    setPosition(object, mapId, instanceId, x, y, z, heading);
  }

  public void setPosition(VisibleObject object, int mapId, int instance, float x, float y, float z, byte heading) {
    if (object.isSpawned())
      despawn(object);
    object.getPosition().setXYZH(x, y, z, heading);
    object.getPosition().setMapId(mapId);
    object.getPosition().setMapRegion(getWorldMap(mapId).getWorldMapInstanceById(instance).getRegion(object));
  }

  public WorldPosition createPosition(int mapId, float x, float y, float z, byte heading) {
    WorldPosition position = new WorldPosition();
    position.setXYZH(x, y, z, heading);
    position.setMapId(mapId);
    position.setMapRegion(getWorldMap(mapId).getWorldMapInstance().getRegion(x, y, z));
    return position;
  }

  public void spawn(VisibleObject object) {
    if (object.isSpawned()) {
      throw new AlreadySpawnedException();
    }
    object.getPosition().setIsSpawned(true);
    if (object.getSpawn() != null)
      object.getSpawn().setSpawned(true, object.getInstanceId());
    object.getActiveRegion().getParent().addObject(object);
    object.getActiveRegion().add(object);

    object.getKnownList().updateKnownList();
  }

  public void despawn(VisibleObject object) {
    if (object.getActiveRegion() != null) {

      if (object.getActiveRegion().getParent() != null)
        object.getActiveRegion().getParent().removeObject((AionObject) object);
      object.getActiveRegion().remove(object);
    }
    object.getPosition().setIsSpawned(false);
    if (object.getSpawn() != null)
      object.getSpawn().setSpawned(false, object.getInstanceId());
    object.getKnownList().clearKnownList();
  }

  private static class SingletonHolder {
    protected static final World instance = new World();
  }
}

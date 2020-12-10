package com.aionemu.gameserver.world;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import javolution.util.FastList;
import javolution.util.FastMap;

public class MapRegion {
  private final Integer regionId;
  private final WorldMapInstance parent;
  private final FastList<MapRegion> neighbours;
  private final FastMap<Integer, VisibleObject> objects = (new FastMap()).shared();

  private int playerCount = 0;

  MapRegion(Integer id, WorldMapInstance parent, boolean is3D) {
    if (is3D) {
      this.neighbours = new FastList(27);
    } else {
      this.neighbours = new FastList(9);
    }
    this.regionId = id;
    this.parent = parent;
    this.neighbours.add(this);
  }

  public Integer getMapId() {
    return getParent().getMapId();
  }

  public World getWorld() {
    return getParent().getWorld();
  }

  public Integer getRegionId() {
    return this.regionId;
  }

  public boolean isActive() {
    return (this.playerCount > 0);
  }

  public WorldMapInstance getParent() {
    return this.parent;
  }

  public FastMap<Integer, VisibleObject> getVisibleObjects() {
    return this.objects;
  }

  public FastList<MapRegion> getNeighbours() {
    return this.neighbours;
  }

  void addNeighbourRegion(MapRegion neighbour) {
    this.neighbours.add(neighbour);
  }

  void add(VisibleObject object) {
    if (this.objects.put(Integer.valueOf(object.getObjectId()), object) == null) {
      if (object instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
        this.playerCount++;
      }
    }
  }

  void remove(VisibleObject object) {
    if (this.objects.remove(Integer.valueOf(object.getObjectId())) != null) {
      if (object instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
        this.playerCount--;
      }
    }
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MapRegion other = (MapRegion) obj;
    if (this.regionId == null) {

      if (other.regionId != null) {
        return false;
      }
    } else if (!this.regionId.equals(other.regionId)) {
      return false;
    }
    return true;
  }
}

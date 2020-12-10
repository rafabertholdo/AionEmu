package com.aionemu.gameserver.services;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.ZoneData;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.zone.ZoneTemplate;
import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.MapRegion;
import com.aionemu.gameserver.world.WorldPosition;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public final class ZoneService extends AbstractFIFOPeriodicTaskManager<Player> {
  private Map<ZoneName, ZoneInstance> zoneMap;
  private Map<Integer, Collection<ZoneInstance>> zoneByMapIdMap;
  private ZoneData zoneData;
  private static final long DROWN_PERIOD = 2000L;

  public static final ZoneService getInstance() {
    return SingletonHolder.instance;
  }

  private ZoneService() {
    super(4000);
    this.zoneData = DataManager.ZONE_DATA;
    this.zoneMap = new HashMap<ZoneName, ZoneInstance>();
    this.zoneByMapIdMap = new HashMap<Integer, Collection<ZoneInstance>>();
    initializeZones();
  }

  protected void callTask(Player player) {
    if (player != null) {
      byte mask;
      while ((mask = player.getController().getZoneUpdateMask()) != 0) {

        for (ZoneUpdateMode mode : VALUES) {
          mode.tryUpdateZone(player, mask);
        }
      }
    }
  }

  private static final ZoneUpdateMode[] VALUES = ZoneUpdateMode.values();

  public enum ZoneUpdateMode {
    ZONE_UPDATE {
      public void zoneTask(Player player) {
        player.getController().updateZoneImpl();
        player.getController().checkWaterLevel();
      }
    },
    ZONE_REFRESH {
      public void zoneTask(Player player) {
        player.getController().refreshZoneImpl();
      }
    };

    private final byte MASK;

    ZoneUpdateMode() {
      this.MASK = (byte) (1 << ordinal());
    }

    public byte mask() {
      return this.MASK;
    }

    protected final void tryUpdateZone(Player player, byte mask) {
      if ((mask & mask()) == mask()) {

        zoneTask(player);
        player.getController().removeZoneUpdateMask(this);
      }
    }

    protected abstract void zoneTask(Player param1Player);
  }

  private void initializeZones() {
    Iterator<ZoneTemplate> iterator = this.zoneData.iterator();
    while (iterator.hasNext()) {

      ZoneTemplate template = iterator.next();
      ZoneInstance instance = new ZoneInstance(template);
      this.zoneMap.put(template.getName(), instance);

      Collection<ZoneInstance> zoneListForMap = this.zoneByMapIdMap.get(Integer.valueOf(template.getMapid()));
      if (zoneListForMap == null) {

        zoneListForMap = createZoneSetCollection();
        this.zoneByMapIdMap.put(Integer.valueOf(template.getMapid()), zoneListForMap);
      }
      zoneListForMap.add(instance);
    }

    for (ZoneInstance zoneInstance : this.zoneMap.values()) {

      ZoneTemplate template = zoneInstance.getTemplate();

      Collection<ZoneInstance> neighbors = createZoneSetCollection();
      for (ZoneName zone : template.getLink()) {
        neighbors.add(this.zoneMap.get(zone));
      }
      zoneInstance.setNeighbors(neighbors);
    }
  }

  private Collection<ZoneInstance> createZoneSetCollection() {
    SortedSet<ZoneInstance> collection = new TreeSet<ZoneInstance>(new Comparator<ZoneInstance>() {

      public int compare(ZoneInstance o1, ZoneInstance o2) {
        return (o1.getPriority() > o2.getPriority()) ? 1 : -1;
      }
    });

    return collection;
  }

  public void checkZone(Player player) {
    ZoneInstance currentInstance = player.getZoneInstance();
    if (currentInstance == null) {
      return;
    }

    Collection<ZoneInstance> neighbors = currentInstance.getNeighbors();
    if (neighbors == null) {
      return;
    }
    for (ZoneInstance zone : neighbors) {

      if (checkPointInZone(zone, player.getPosition())) {

        player.setZoneInstance(zone);
        player.getController().onEnterZone(zone);
        player.getController().onLeaveZone(currentInstance);
        return;
      }
    }
  }

  public void findZoneInCurrentMap(Player player) {
    MapRegion mapRegion = player.getActiveRegion();
    if (mapRegion == null) {
      return;
    }
    Collection<ZoneInstance> zones = this.zoneByMapIdMap.get(mapRegion.getMapId());
    if (zones == null) {

      player.getController().resetZone();

      return;
    }
    for (ZoneInstance zone : zones) {

      if (checkPointInZone(zone, player.getPosition())) {

        player.setZoneInstance(zone);
        player.getController().onEnterZone(zone);
        return;
      }
    }
  }

  public boolean isInsideZone(Player player, ZoneName zoneName) {
    ZoneInstance zoneInstance = this.zoneMap.get(zoneName);
    if (zoneInstance == null) {
      return false;
    }
    return checkPointInZone(zoneInstance, player.getPosition());
  }

  private boolean checkPointInZone(ZoneInstance zone, WorldPosition position) {
    int corners = zone.getCorners();
    float[] xCoords = zone.getxCoordinates();
    float[] yCoords = zone.getyCoordinates();

    float top = zone.getTop();
    float bottom = zone.getBottom();

    float x = position.getX();
    float y = position.getY();
    float z = position.getZ();

    if (top != 0.0F || bottom != 0.0F) {
      if (z > top || z < bottom) {
        return false;
      }
    }
    int j = corners - 1;
    boolean inside = false;

    for (int i = 0; i < corners; i++) {

      if ((yCoords[i] < y && yCoords[j] >= y) || (yCoords[j] < y && yCoords[i] >= y)) {
        if (xCoords[i] + (y - yCoords[i]) / (yCoords[j] - yCoords[i]) * (xCoords[j] - xCoords[i]) < x) {
          inside = !inside;
        }
      }
      j = i;
    }

    return inside;
  }

  public void startDrowning(Player player) {
    if (!isDrowning(player)) {
      scheduleDrowningTask(player);
    }
  }

  public void stopDrowning(Player player) {
    if (isDrowning(player)) {
      player.getController().cancelTask(TaskId.DROWN);
    }
  }

  private boolean isDrowning(Player player) {
    return !(player.getController().getTask(TaskId.DROWN) == null);
  }

  private void scheduleDrowningTask(final Player player) {
    player.getController().addTask(TaskId.DROWN, ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
          {
            public void run()
            {
              int value = Math.round((player.getLifeStats().getMaxHp() / 10));
              
              if (!player.getLifeStats().isAlreadyDead()) {
                
                player.getLifeStats().reduceHp(value, null);
                player.getLifeStats().sendHpPacketUpdate();
              }
              else {
                
                ZoneService.this.stopDrowning(player);
              } 
            }
          }0L, 2000L));
  }

  protected String getCalledMethodName() {
    return "zoneService()";
  }

  private static class SingletonHolder {
    protected static final ZoneService instance = new ZoneService();
  }
}

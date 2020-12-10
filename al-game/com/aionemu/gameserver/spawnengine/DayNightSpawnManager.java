package com.aionemu.gameserver.spawnengine;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTime;
import com.aionemu.gameserver.utils.gametime.DayTime;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import com.aionemu.gameserver.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

public class DayNightSpawnManager {
  private static Logger log = Logger.getLogger(DayNightSpawnManager.class);

  private final List<SpawnTemplate> daySpawns;

  private final List<SpawnTemplate> nightSpawns;
  private final List<VisibleObject> spawnedObjects;
  private SpawnTime currentSpawnTime = null;

  public static final DayNightSpawnManager getInstance() {
    return SingletonHolder.instance;
  }

  private DayNightSpawnManager() {
    this.daySpawns = new ArrayList<SpawnTemplate>();
    this.nightSpawns = new ArrayList<SpawnTemplate>();
    this.spawnedObjects = new ArrayList<VisibleObject>();
    log.info("DayNightSpawnManager: Day/Night handler initialized");
  }

  public void addSpawnTemplate(SpawnTemplate spawnTemplate) {
    if (spawnTemplate.getSpawnGroup().getSpawnTime() == SpawnTime.DAY) {
      this.daySpawns.add(spawnTemplate);
    } else {
      this.nightSpawns.add(spawnTemplate);
    }
  }

  private void spawnNpcs(List<SpawnTemplate> spawns) {
    for (SpawnTemplate spawnTemplate : spawns) {

      Set<Integer> instanceIds = World.getInstance().getWorldMap(spawnTemplate.getWorldId()).getInstanceIds();
      for (Integer instanceId : instanceIds) {

        VisibleObject object = SpawnEngine.getInstance().spawnObject(spawnTemplate, instanceId.intValue());
        if (object != null) {
          this.spawnedObjects.add(object);
        }
      }
    }
  }

  private void deleteObjects() {
    for (VisibleObject object : this.spawnedObjects)
      object.getController().delete();
    this.spawnedObjects.clear();
  }

  public void notifyChangeMode() {
    deleteObjects();
    DayTime dayTime = GameTimeManager.getGameTime().getDayTime();
    if (dayTime == DayTime.NIGHT && (this.currentSpawnTime == null || this.currentSpawnTime == SpawnTime.DAY)) {

      spawnNpcs(this.nightSpawns);
      this.currentSpawnTime = SpawnTime.NIGHT;
      log.info("DayNightSpawnManager: " + this.spawnedObjects.size() + " night objects spawned.");

    } else if (dayTime != DayTime.NIGHT
        && (this.currentSpawnTime == null || this.currentSpawnTime == SpawnTime.NIGHT)) {

      spawnNpcs(this.daySpawns);
      this.currentSpawnTime = SpawnTime.DAY;
      log.info("DayNightSpawnManager: " + this.spawnedObjects.size() + " day objects spawned.");
    }
  }

  private static class SingletonHolder {
    protected static final DayNightSpawnManager instance = new DayNightSpawnManager();
  }
}

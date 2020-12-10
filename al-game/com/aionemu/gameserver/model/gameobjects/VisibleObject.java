package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.world.KnownList;
import com.aionemu.gameserver.world.MapRegion;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldPosition;
import com.aionemu.gameserver.world.WorldType;

public abstract class VisibleObject extends AionObject {
  protected VisibleObjectTemplate objectTemplate;
  private WorldPosition position;
  private KnownList knownlist;
  private final VisibleObjectController<? extends VisibleObject> controller;
  private VisibleObject target;
  private SpawnTemplate spawn;

  public VisibleObject(int objId, VisibleObjectController<? extends VisibleObject> controller,
      SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, WorldPosition position) {
    super(Integer.valueOf(objId));
    this.controller = controller;
    this.position = position;
    this.spawn = spawnTemplate;
    this.objectTemplate = objectTemplate;
  }

  public MapRegion getActiveRegion() {
    return this.position.getMapRegion();
  }

  public int getInstanceId() {
    return this.position.getInstanceId();
  }

  public int getWorldId() {
    return this.position.getMapId();
  }

  public WorldType getWorldType() {
    return World.getInstance().getWorldMap(getWorldId()).getWorldType();
  }

  public float getX() {
    return this.position.getX();
  }

  public float getY() {
    return this.position.getY();
  }

  public float getZ() {
    return this.position.getZ();
  }

  public byte getHeading() {
    return this.position.getHeading();
  }

  public WorldPosition getPosition() {
    return this.position;
  }

  public boolean isSpawned() {
    return this.position.isSpawned();
  }

  public boolean isInWorld() {
    return (World.getInstance().findAionObject(getObjectId()) != null);
  }

  public boolean isInInstance() {
    return this.position.isInstanceMap();
  }

  public void setKnownlist(KnownList knownlist) {
    this.knownlist = knownlist;
  }

  public KnownList getKnownList() {
    return this.knownlist;
  }

  public VisibleObjectController<? extends VisibleObject> getController() {
    return this.controller;
  }

  public VisibleObject getTarget() {
    return this.target;
  }

  public void setTarget(VisibleObject creature) {
    this.target = creature;
  }

  public boolean isTargeting(int objectId) {
    return (this.target != null && this.target.getObjectId() == objectId);
  }

  public SpawnTemplate getSpawn() {
    return this.spawn;
  }

  public void setSpawn(SpawnTemplate spawn) {
    this.spawn = spawn;
  }

  public VisibleObjectTemplate getObjectTemplate() {
    return this.objectTemplate;
  }

  public void setObjectTemplate(VisibleObjectTemplate objectTemplate) {
    this.objectTemplate = objectTemplate;
  }
}

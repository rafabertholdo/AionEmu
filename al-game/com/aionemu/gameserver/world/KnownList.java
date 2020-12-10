package com.aionemu.gameserver.world;

import com.aionemu.commons.utils.SingletonMap;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.utils.MathUtil;
import java.util.Map;

public class KnownList {
  private static final float visibilityDistance = 95.0F;
  private static final float maxZvisibleDistance = 95.0F;
  private final VisibleObject owner;
  private final Map<Integer, VisibleObject> knownObjects = (Map<Integer, VisibleObject>) (new SingletonMap())
      .setShared();

  private long lastUpdate;

  public KnownList(VisibleObject owner) {
    this.owner = owner;
  }

  public VisibleObject getOwner() {
    return this.owner;
  }

  public final Map<Integer, VisibleObject> getKnownObjects() {
    return this.knownObjects;
  }

  public final synchronized void updateKnownList() {
    if (System.currentTimeMillis() - this.lastUpdate < 100L || !this.owner.getActiveRegion().isActive()) {
      return;
    }
    updateKnownListImpl();

    this.lastUpdate = System.currentTimeMillis();
  }

  protected void updateKnownListImpl() {
    forgetVisibleObjects();
    findVisibleObjects();
  }

  public final void clearKnownList() {
    for (VisibleObject object : getKnownObjects().values()) {

      removeKnownObject(object, false);
      object.getKnownList().removeKnownObject(getOwner(), false);
    }

    getKnownObjects().clear();
  }

  public boolean knowns(AionObject object) {
    return getKnownObjects().containsKey(Integer.valueOf(object.getObjectId()));
  }

  protected boolean addKnownObject(VisibleObject object) {
    if (object == null || object == getOwner()) {
      return false;
    }
    if (!checkObjectInRange(getOwner(), object)) {
      return false;
    }
    if (getKnownObjects().put(Integer.valueOf(object.getObjectId()), object) != null) {
      return false;
    }
    getOwner().getController().see(object);

    return true;
  }

  private final boolean removeKnownObject(VisibleObject object, boolean isOutOfRange) {
    if (object == null) {
      return false;
    }
    if (getKnownObjects().remove(Integer.valueOf(object.getObjectId())) == null) {
      return false;
    }
    getOwner().getController().notSee(object, isOutOfRange);

    return true;
  }

  private final void forgetVisibleObjects() {
    for (VisibleObject object : getKnownObjects().values()) {

      forgetVisibleObject(object);
      object.getKnownList().forgetVisibleObject(getOwner());
    }
  }

  private final boolean forgetVisibleObject(VisibleObject object) {
    if (checkObjectInRange(getOwner(), object)) {
      return false;
    }
    return removeKnownObject(object, true);
  }

  protected void findVisibleObjects() {
    if (getOwner() == null || !getOwner().isSpawned()) {
      return;
    }
    for (MapRegion region : getOwner().getActiveRegion().getNeighbours()) {

      for (VisibleObject object : region.getVisibleObjects().values()) {

        if (!object.isSpawned())
          continue;
        addKnownObject(object);
        object.getKnownList().addKnownObject(getOwner());
      }
    }
  }

  protected final boolean checkObjectInRange(VisibleObject owner, VisibleObject newObject) {
    if (Math.abs(owner.getZ() - newObject.getZ()) > 95.0F) {
      return false;
    }
    return MathUtil.isInRange(owner, newObject, 95.0F);
  }
}

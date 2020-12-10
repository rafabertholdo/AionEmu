package com.aionemu.gameserver.model.legion;

import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;

public class LegionWarehouse extends Storage {
  private Legion legion;

  public LegionWarehouse(Legion legion) {
    super(StorageType.LEGION_WAREHOUSE);
    this.legion = legion;
    setLimit(legion.getWarehouseSlots());
    setOwnerId(legion.getLegionId());
  }

  public Legion getLegion() {
    return this.legion;
  }

  public void setOwnerLegion(Legion legion) {
    this.legion = legion;
  }
}

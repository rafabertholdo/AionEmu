package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.controllers.GatherableController;
import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.model.templates.GatherableTemplate;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.world.WorldPosition;

public class Gatherable extends VisibleObject {
  public Gatherable(SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, int objId,
      GatherableController controller) {
    super(objId, (VisibleObjectController<? extends VisibleObject>) controller, spawnTemplate, objectTemplate,
        new WorldPosition());
    controller.setOwner(this);
  }

  public String getName() {
    return this.objectTemplate.getName();
  }

  public GatherableTemplate getObjectTemplate() {
    return (GatherableTemplate) this.objectTemplate;
  }

  public GatherableController getController() {
    return (GatherableController) super.getController();
  }
}

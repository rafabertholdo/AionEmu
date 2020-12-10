package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.controllers.StaticObjectController;
import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.world.WorldPosition;

public class StaticObject extends VisibleObject {
  public StaticObject(int objectId, StaticObjectController controller, SpawnTemplate spawnTemplate,
      VisibleObjectTemplate objectTemplate) {
    super(objectId, (VisibleObjectController<? extends VisibleObject>) controller, spawnTemplate, objectTemplate,
        new WorldPosition());
    controller.setOwner(this);
  }

  public String getName() {
    return this.objectTemplate.getName();
  }
}

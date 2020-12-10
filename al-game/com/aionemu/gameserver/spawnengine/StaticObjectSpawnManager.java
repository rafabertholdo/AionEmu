package com.aionemu.gameserver.spawnengine;

import com.aionemu.gameserver.controllers.StaticObjectController;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.StaticObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.KnownList;
import com.aionemu.gameserver.world.World;

























public class StaticObjectSpawnManager
{
  public static void spawnGroup(SpawnGroup spawnGroup, int instanceIndex) {
    ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(spawnGroup.getNpcid());
    
    if (itemTemplate == null) {
      return;
    }
    int pool = spawnGroup.getPool();
    for (int i = 0; i < pool; i++) {
      
      SpawnTemplate spawn = spawnGroup.getNextAvailableTemplate(instanceIndex);
      int objectId = IDFactory.getInstance().nextId();
      StaticObject staticObject = new StaticObject(objectId, new StaticObjectController(), spawn, (VisibleObjectTemplate)itemTemplate);
      staticObject.setKnownlist(new KnownList((VisibleObject)staticObject));
      bringIntoWorld((VisibleObject)staticObject, spawn, instanceIndex);
    } 
  }







  
  private static void bringIntoWorld(VisibleObject visibleObject, SpawnTemplate spawn, int instanceIndex) {
    World world = World.getInstance();
    world.storeObject((AionObject)visibleObject);
    world.setPosition(visibleObject, spawn.getWorldId(), instanceIndex, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getHeading());
    world.spawn(visibleObject);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\spawnengine\StaticObjectSpawnManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

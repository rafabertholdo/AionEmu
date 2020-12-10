package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.spawn.SpawnTime;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.gametime.DayTime;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import com.aionemu.gameserver.world.World;
import java.util.concurrent.Future;





























public class RespawnService
{
  public static Future<?> scheduleDecayTask(final Npc npc) {
    int respawnInterval = npc.getSpawn().getSpawnGroup().getInterval();
    int decayInterval = Math.round(respawnInterval * 0.8F);
    if (decayInterval > 240) {
      decayInterval = 240;
    }
    return ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          
          public void run()
          {
            npc.getController().onDespawn(false);
          }
        },  (decayInterval * 1000));
  }




  
  public static Future<?> scheduleRespawnTask(final VisibleObject visibleObject) {
    final World world = World.getInstance();
    int interval = visibleObject.getSpawn().getSpawnGroup().getInterval();
    
    return ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          
          public void run()
          {
            SpawnTime spawnTime = visibleObject.getSpawn().getSpawnGroup().getSpawnTime();
            if (spawnTime != null) {
              
              DayTime dayTime = GameTimeManager.getGameTime().getDayTime();
              if (!spawnTime.isAllowedDuring(dayTime)) {
                return;
              }
            } 
            int instanceId = visibleObject.getInstanceId();
            int worldId = visibleObject.getSpawn().getWorldId();
            boolean instanceExists = InstanceService.isInstanceExist(worldId, instanceId);
            
            if (visibleObject.getSpawn().isNoRespawn(instanceId) || !instanceExists) {
              
              visibleObject.getController().delete();
            }
            else {
              
              visibleObject.getSpawn().getSpawnGroup().exchangeSpawn(visibleObject);
              world.setPosition(visibleObject, worldId, visibleObject.getSpawn().getX(), visibleObject.getSpawn().getY(), visibleObject.getSpawn().getZ(), visibleObject.getSpawn().getHeading());
              
              visibleObject.getController().onRespawn();
              world.spawn(visibleObject);
            } 
          }
        }(interval * 1000));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\RespawnService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

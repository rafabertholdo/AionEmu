/*    */ package com.aionemu.gameserver.services;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.templates.spawn.SpawnTime;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import com.aionemu.gameserver.utils.gametime.DayTime;
/*    */ import com.aionemu.gameserver.utils.gametime.GameTimeManager;
/*    */ import com.aionemu.gameserver.world.World;
/*    */ import java.util.concurrent.Future;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RespawnService
/*    */ {
/*    */   public static Future<?> scheduleDecayTask(final Npc npc) {
/* 43 */     int respawnInterval = npc.getSpawn().getSpawnGroup().getInterval();
/* 44 */     int decayInterval = Math.round(respawnInterval * 0.8F);
/* 45 */     if (decayInterval > 240) {
/* 46 */       decayInterval = 240;
/*    */     }
/* 48 */     return ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 53 */             npc.getController().onDespawn(false);
/*    */           }
/*    */         },  (decayInterval * 1000));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Future<?> scheduleRespawnTask(final VisibleObject visibleObject) {
/* 63 */     final World world = World.getInstance();
/* 64 */     int interval = visibleObject.getSpawn().getSpawnGroup().getInterval();
/*    */     
/* 66 */     return ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 71 */             SpawnTime spawnTime = visibleObject.getSpawn().getSpawnGroup().getSpawnTime();
/* 72 */             if (spawnTime != null) {
/*    */               
/* 74 */               DayTime dayTime = GameTimeManager.getGameTime().getDayTime();
/* 75 */               if (!spawnTime.isAllowedDuring(dayTime)) {
/*    */                 return;
/*    */               }
/*    */             } 
/* 79 */             int instanceId = visibleObject.getInstanceId();
/* 80 */             int worldId = visibleObject.getSpawn().getWorldId();
/* 81 */             boolean instanceExists = InstanceService.isInstanceExist(worldId, instanceId);
/*    */             
/* 83 */             if (visibleObject.getSpawn().isNoRespawn(instanceId) || !instanceExists) {
/*    */               
/* 85 */               visibleObject.getController().delete();
/*    */             }
/*    */             else {
/*    */               
/* 89 */               visibleObject.getSpawn().getSpawnGroup().exchangeSpawn(visibleObject);
/* 90 */               world.setPosition(visibleObject, worldId, visibleObject.getSpawn().getX(), visibleObject.getSpawn().getY(), visibleObject.getSpawn().getZ(), visibleObject.getSpawn().getHeading());
/*    */               
/* 92 */               visibleObject.getController().onRespawn();
/* 93 */               world.spawn(visibleObject);
/*    */             } 
/*    */           }
/*    */         }(interval * 1000));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\RespawnService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
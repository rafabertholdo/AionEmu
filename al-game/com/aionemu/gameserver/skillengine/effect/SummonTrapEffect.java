/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.TaskId;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Trap;
/*    */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.spawnengine.SpawnEngine;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import java.util.concurrent.Future;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "SummonTrapEffect")
/*    */ public class SummonTrapEffect
/*    */   extends SummonEffect
/*    */ {
/*    */   @XmlAttribute(name = "skill_id", required = true)
/*    */   protected int skillId;
/*    */   @XmlAttribute(name = "time", required = true)
/*    */   protected int time;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 50 */     Creature effector = effect.getEffector();
/* 51 */     SpawnEngine spawnEngine = SpawnEngine.getInstance();
/* 52 */     float x = effector.getX();
/* 53 */     float y = effector.getY();
/* 54 */     float z = effector.getZ();
/* 55 */     byte heading = effector.getHeading();
/* 56 */     int worldId = effector.getWorldId();
/* 57 */     int instanceId = effector.getInstanceId();
/*    */     
/* 59 */     SpawnTemplate spawn = spawnEngine.addNewSpawn(worldId, instanceId, this.npcId, x, y, z, heading, 0, 0, true, true);
/* 60 */     final Trap trap = spawnEngine.spawnTrap(spawn, instanceId, effector, this.skillId);
/*    */     
/* 62 */     Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 67 */             trap.getController().onDespawn(true);
/*    */           }
/*    */         },  (this.time * 1000));
/* 70 */     trap.getController().addTask(TaskId.DESPAWN, task);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 76 */     super.calculate(effect);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SummonTrapEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
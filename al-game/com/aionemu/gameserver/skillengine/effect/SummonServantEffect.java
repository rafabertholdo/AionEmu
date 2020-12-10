/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.TaskId;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Servant;
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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "SummonServantEffect")
/*    */ public class SummonServantEffect
/*    */   extends SummonEffect
/*    */ {
/*    */   @XmlAttribute(name = "skill_id", required = true)
/*    */   protected int skillId;
/*    */   @XmlAttribute(name = "hp_ratio", required = true)
/*    */   protected int hpRatio;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 51 */     Creature effector = effect.getEffector();
/* 52 */     SpawnEngine spawnEngine = SpawnEngine.getInstance();
/* 53 */     float x = effector.getX();
/* 54 */     float y = effector.getY();
/* 55 */     float z = effector.getZ();
/* 56 */     byte heading = effector.getHeading();
/* 57 */     int worldId = effector.getWorldId();
/* 58 */     int instanceId = effector.getInstanceId();
/*    */     
/* 60 */     SpawnTemplate spawn = spawnEngine.addNewSpawn(worldId, instanceId, this.npcId, x, y, z, heading, 0, 0, true, true);
/* 61 */     final Servant servant = spawnEngine.spawnServant(spawn, instanceId, effector, this.skillId, this.hpRatio);
/*    */     
/* 63 */     Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 68 */             servant.getController().onDespawn(true);
/*    */           }
/*    */         },  60000L);
/* 71 */     servant.getController().addTask(TaskId.DESPAWN, task);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 77 */     effect.addSucessEffect(this);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SummonServantEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
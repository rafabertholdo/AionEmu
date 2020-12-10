/*    */ package com.aionemu.gameserver.model.gameobjects;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.StaticObjectController;
/*    */ import com.aionemu.gameserver.controllers.VisibleObjectController;
/*    */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*    */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*    */ import com.aionemu.gameserver.world.WorldPosition;
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
/*    */ public class StaticObject
/*    */   extends VisibleObject
/*    */ {
/*    */   public StaticObject(int objectId, StaticObjectController controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate) {
/* 33 */     super(objectId, (VisibleObjectController<? extends VisibleObject>)controller, spawnTemplate, objectTemplate, new WorldPosition());
/* 34 */     controller.setOwner(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 40 */     return this.objectTemplate.getName();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\StaticObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
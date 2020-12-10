/*    */ package com.aionemu.gameserver.model.gameobjects;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.GatherableController;
/*    */ import com.aionemu.gameserver.controllers.VisibleObjectController;
/*    */ import com.aionemu.gameserver.model.templates.GatherableTemplate;
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
/*    */ public class Gatherable
/*    */   extends VisibleObject
/*    */ {
/*    */   public Gatherable(SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, int objId, GatherableController controller) {
/* 34 */     super(objId, (VisibleObjectController<? extends VisibleObject>)controller, spawnTemplate, objectTemplate, new WorldPosition());
/* 35 */     controller.setOwner(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 41 */     return this.objectTemplate.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GatherableTemplate getObjectTemplate() {
/* 48 */     return (GatherableTemplate)this.objectTemplate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GatherableController getController() {
/* 55 */     return (GatherableController)super.getController();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Gatherable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.aionemu.gameserver.controllers;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class VisibleObjectController<T extends VisibleObject>
/*    */ {
/*    */   private T owner;
/*    */   
/*    */   public void setOwner(T owner) {
/* 44 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T getOwner() {
/* 52 */     return this.owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void see(VisibleObject object) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void notSee(VisibleObject object, boolean isOutOfRange) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void delete() {
/* 83 */     if (getOwner().isSpawned()) {
/* 84 */       World.getInstance().despawn((VisibleObject)getOwner());
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 89 */     World.getInstance().removeObject((AionObject)getOwner());
/*    */   }
/*    */   
/*    */   public void onRespawn() {}
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\VisibleObjectController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
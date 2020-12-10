/*    */ package com.aionemu.gameserver.world;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
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
/*    */ public final class StaticObjectKnownList
/*    */   extends KnownList
/*    */ {
/*    */   public StaticObjectKnownList(VisibleObject owner) {
/* 33 */     super(owner);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final boolean addKnownObject(VisibleObject object) {
/* 44 */     if (object instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
/* 45 */       return super.addKnownObject(object);
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void findVisibleObjects() {
/* 56 */     if (getOwner() == null || !getOwner().isSpawned()) {
/*    */       return;
/*    */     }
/* 59 */     for (MapRegion region : getOwner().getActiveRegion().getNeighbours()) {
/*    */       
/* 61 */       for (VisibleObject object : region.getVisibleObjects().values()) {
/*    */         
/* 63 */         if (!(object instanceof com.aionemu.gameserver.model.gameobjects.player.Player)) {
/*    */           continue;
/*    */         }
/* 66 */         addKnownObject(object);
/* 67 */         object.getKnownList().addKnownObject(getOwner());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\StaticObjectKnownList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
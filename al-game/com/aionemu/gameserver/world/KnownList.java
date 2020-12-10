/*     */ package com.aionemu.gameserver.world;
/*     */ 
/*     */ import com.aionemu.commons.utils.SingletonMap;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KnownList
/*     */ {
/*     */   private static final float visibilityDistance = 95.0F;
/*     */   private static final float maxZvisibleDistance = 95.0F;
/*     */   private final VisibleObject owner;
/*  42 */   private final Map<Integer, VisibleObject> knownObjects = (Map<Integer, VisibleObject>)(new SingletonMap()).setShared();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastUpdate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KnownList(VisibleObject owner) {
/*  53 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VisibleObject getOwner() {
/*  61 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Map<Integer, VisibleObject> getKnownObjects() {
/*  69 */     return this.knownObjects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void updateKnownList() {
/*  77 */     if (System.currentTimeMillis() - this.lastUpdate < 100L || !this.owner.getActiveRegion().isActive()) {
/*     */       return;
/*     */     }
/*  80 */     updateKnownListImpl();
/*     */     
/*  82 */     this.lastUpdate = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateKnownListImpl() {
/*  87 */     forgetVisibleObjects();
/*  88 */     findVisibleObjects();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clearKnownList() {
/*  96 */     for (VisibleObject object : getKnownObjects().values()) {
/*     */       
/*  98 */       removeKnownObject(object, false);
/*  99 */       object.getKnownList().removeKnownObject(getOwner(), false);
/*     */     } 
/*     */     
/* 102 */     getKnownObjects().clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean knowns(AionObject object) {
/* 113 */     return getKnownObjects().containsKey(Integer.valueOf(object.getObjectId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean addKnownObject(VisibleObject object) {
/* 124 */     if (object == null || object == getOwner()) {
/* 125 */       return false;
/*     */     }
/* 127 */     if (!checkObjectInRange(getOwner(), object)) {
/* 128 */       return false;
/*     */     }
/* 130 */     if (getKnownObjects().put(Integer.valueOf(object.getObjectId()), object) != null) {
/* 131 */       return false;
/*     */     }
/* 133 */     getOwner().getController().see(object);
/*     */     
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean removeKnownObject(VisibleObject object, boolean isOutOfRange) {
/* 146 */     if (object == null) {
/* 147 */       return false;
/*     */     }
/* 149 */     if (getKnownObjects().remove(Integer.valueOf(object.getObjectId())) == null) {
/* 150 */       return false;
/*     */     }
/* 152 */     getOwner().getController().notSee(object, isOutOfRange);
/*     */     
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private final void forgetVisibleObjects() {
/* 159 */     for (VisibleObject object : getKnownObjects().values()) {
/*     */       
/* 161 */       forgetVisibleObject(object);
/* 162 */       object.getKnownList().forgetVisibleObject(getOwner());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final boolean forgetVisibleObject(VisibleObject object) {
/* 168 */     if (checkObjectInRange(getOwner(), object)) {
/* 169 */       return false;
/*     */     }
/* 171 */     return removeKnownObject(object, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void findVisibleObjects() {
/* 179 */     if (getOwner() == null || !getOwner().isSpawned()) {
/*     */       return;
/*     */     }
/* 182 */     for (MapRegion region : getOwner().getActiveRegion().getNeighbours()) {
/*     */       
/* 184 */       for (VisibleObject object : region.getVisibleObjects().values()) {
/*     */         
/* 186 */         if (!object.isSpawned())
/*     */           continue; 
/* 188 */         addKnownObject(object);
/* 189 */         object.getKnownList().addKnownObject(getOwner());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean checkObjectInRange(VisibleObject owner, VisibleObject newObject) {
/* 197 */     if (Math.abs(owner.getZ() - newObject.getZ()) > 95.0F) {
/* 198 */       return false;
/*     */     }
/* 200 */     return MathUtil.isInRange(owner, newObject, 95.0F);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\KnownList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
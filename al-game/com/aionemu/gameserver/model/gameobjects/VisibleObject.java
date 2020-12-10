/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.VisibleObjectController;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.world.KnownList;
/*     */ import com.aionemu.gameserver.world.MapRegion;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
/*     */ import com.aionemu.gameserver.world.WorldType;
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
/*     */ public abstract class VisibleObject
/*     */   extends AionObject
/*     */ {
/*     */   protected VisibleObjectTemplate objectTemplate;
/*     */   private WorldPosition position;
/*     */   private KnownList knownlist;
/*     */   private final VisibleObjectController<? extends VisibleObject> controller;
/*     */   private VisibleObject target;
/*     */   private SpawnTemplate spawn;
/*     */   
/*     */   public VisibleObject(int objId, VisibleObjectController<? extends VisibleObject> controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, WorldPosition position) {
/*  51 */     super(Integer.valueOf(objId));
/*  52 */     this.controller = controller;
/*  53 */     this.position = position;
/*  54 */     this.spawn = spawnTemplate;
/*  55 */     this.objectTemplate = objectTemplate;
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public MapRegion getActiveRegion() {
/*  90 */     return this.position.getMapRegion();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInstanceId() {
/*  95 */     return this.position.getInstanceId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldId() {
/* 105 */     return this.position.getMapId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 115 */     return World.getInstance().getWorldMap(getWorldId()).getWorldType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/* 125 */     return this.position.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/* 135 */     return this.position.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getZ() {
/* 145 */     return this.position.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getHeading() {
/* 155 */     return this.position.getHeading();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldPosition getPosition() {
/* 165 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpawned() {
/* 175 */     return this.position.isSpawned();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInWorld() {
/* 184 */     return (World.getInstance().findAionObject(getObjectId()) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInInstance() {
/* 194 */     return this.position.isInstanceMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKnownlist(KnownList knownlist) {
/* 204 */     this.knownlist = knownlist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KnownList getKnownList() {
/* 214 */     return this.knownlist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VisibleObjectController<? extends VisibleObject> getController() {
/* 224 */     return this.controller;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VisibleObject getTarget() {
/* 233 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(VisibleObject creature) {
/* 242 */     this.target = creature;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTargeting(int objectId) {
/* 252 */     return (this.target != null && this.target.getObjectId() == objectId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnTemplate getSpawn() {
/* 262 */     return this.spawn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawn(SpawnTemplate spawn) {
/* 270 */     this.spawn = spawn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VisibleObjectTemplate getObjectTemplate() {
/* 278 */     return this.objectTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectTemplate(VisibleObjectTemplate objectTemplate) {
/* 286 */     this.objectTemplate = objectTemplate;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\VisibleObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
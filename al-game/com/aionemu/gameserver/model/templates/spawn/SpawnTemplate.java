/*     */ package com.aionemu.gameserver.model.templates.spawn;
/*     */ 
/*     */ import java.util.BitSet;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "object")
/*     */ public class SpawnTemplate
/*     */ {
/*     */   @XmlTransient
/*     */   private SpawnGroup spawnGroup;
/*     */   @XmlAttribute(name = "rw")
/*     */   private int randomWalk;
/*     */   @XmlAttribute(name = "w")
/*     */   private int walkerId;
/*     */   @XmlAttribute(name = "h")
/*     */   private byte heading;
/*     */   @XmlAttribute(name = "z")
/*     */   private float z;
/*     */   @XmlAttribute(name = "y")
/*     */   private float y;
/*     */   @XmlAttribute(name = "x")
/*     */   private float x;
/*     */   @XmlAttribute(name = "staticid")
/*     */   private int staticid;
/*     */   @XmlAttribute(name = "fly")
/*     */   private int npcfly;
/*     */   @XmlTransient
/*  59 */   private BitSet spawnState = new BitSet();
/*     */   @XmlTransient
/*  61 */   private BitSet noRespawn = new BitSet();
/*     */   @XmlTransient
/*  63 */   private BitSet restingState = new BitSet();
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
/*     */   public SpawnTemplate() {}
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
/*     */   public SpawnTemplate(float x, float y, float z, byte heading, int walkerId, int randomWalk, int npcfly) {
/*  86 */     this.x = x;
/*  87 */     this.y = y;
/*  88 */     this.z = z;
/*  89 */     this.heading = heading;
/*  90 */     this.walkerId = walkerId;
/*  91 */     this.randomWalk = randomWalk;
/*  92 */     this.npcfly = npcfly;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWorldId() {
/*  97 */     return this.spawnGroup.getMapid();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getX() {
/* 102 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getY() {
/* 107 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getZ() {
/* 112 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getHeading() {
/* 117 */     return this.heading;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWalkerId() {
/* 122 */     return this.walkerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setRandomWalkNr(int rw) {
/* 130 */     if (this.randomWalk == 0)
/*     */     {
/* 132 */       this.randomWalk = rw;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRandomWalkNr() {
/* 138 */     return this.randomWalk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRandomWalk() {
/* 143 */     return (this.randomWalk > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNpcFlyState() {
/* 148 */     return this.npcfly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnGroup getSpawnGroup() {
/* 156 */     return this.spawnGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnGroup(SpawnGroup spawnGroup) {
/* 164 */     this.spawnGroup = spawnGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isResting(int instance) {
/* 172 */     return this.restingState.get(instance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResting(boolean isResting, int instance) {
/* 180 */     this.restingState.set(instance, isResting);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpawned(int instance) {
/* 188 */     return this.spawnState.get(instance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawned(boolean isSpawned, int instance) {
/* 196 */     this.spawnState.set(instance, isSpawned);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNoRespawn(int instance) {
/* 206 */     return this.noRespawn.get(instance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNoRespawn(boolean noRespawn, int instance) {
/* 214 */     this.noRespawn.set(instance, noRespawn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStaticid() {
/* 222 */     return this.staticid;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\spawn\SpawnTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
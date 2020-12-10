/*     */ package com.aionemu.gameserver.model.geometry;
/*     */ 
/*     */ import java.awt.Point;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractArea
/*     */   implements Area
/*     */ {
/*     */   private final int minZ;
/*     */   private final int maxZ;
/*     */   
/*     */   protected AbstractArea(int minZ, int maxZ) {
/*  50 */     if (minZ > maxZ)
/*     */     {
/*  52 */       throw new IllegalArgumentException("minZ(" + minZ + ") > maxZ(" + maxZ + ")");
/*     */     }
/*  54 */     this.minZ = minZ;
/*  55 */     this.maxZ = maxZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside2D(Point point) {
/*  64 */     return isInside2D(point.x, point.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside3D(Point3D point) {
/*  73 */     return isInside3D(point.getX(), point.getY(), point.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside3D(int x, int y, int z) {
/*  82 */     return (isInsideZ(z) && isInside2D(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsideZ(Point3D point) {
/*  91 */     return isInsideZ(point.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsideZ(int z) {
/* 100 */     return (z >= getMinZ() && z <= getMaxZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance2D(Point point) {
/* 109 */     return getDistance2D(point.x, point.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance3D(Point3D point) {
/* 118 */     return getDistance3D(point.getX(), point.getY(), point.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getClosestPoint(Point point) {
/* 127 */     return getClosestPoint(point.x, point.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3D getClosestPoint(Point3D point) {
/* 136 */     return getClosestPoint(point.getX(), point.getY(), point.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3D getClosestPoint(int x, int y, int z) {
/*     */     int zCoord;
/* 145 */     Point closest2d = getClosestPoint(x, y);
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (isInsideZ(z)) {
/*     */       
/* 151 */       zCoord = z;
/*     */     }
/* 153 */     else if (z < getMinZ()) {
/*     */       
/* 155 */       zCoord = getMinZ();
/*     */     }
/*     */     else {
/*     */       
/* 159 */       zCoord = getMaxZ();
/*     */     } 
/*     */     
/* 162 */     return new Point3D(closest2d.x, closest2d.y, zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinZ() {
/* 170 */     return this.minZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxZ() {
/* 178 */     return this.maxZ;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\geometry\AbstractArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
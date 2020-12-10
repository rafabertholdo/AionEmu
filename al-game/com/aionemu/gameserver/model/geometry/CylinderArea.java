/*     */ package com.aionemu.gameserver.model.geometry;
/*     */ 
/*     */ import com.aionemu.gameserver.utils.MathUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CylinderArea
/*     */   extends AbstractArea
/*     */ {
/*     */   private final int centerX;
/*     */   private final int centerY;
/*     */   private final int radius;
/*     */   
/*     */   public CylinderArea(Point center, int radius, int minZ, int maxZ) {
/*  61 */     this(center.x, center.y, radius, minZ, maxZ);
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
/*     */   public CylinderArea(int x, int y, int radius, int minZ, int maxZ) {
/*  80 */     super(minZ, maxZ);
/*  81 */     this.centerX = x;
/*  82 */     this.centerY = y;
/*  83 */     this.radius = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside2D(int x, int y) {
/*  92 */     return (MathUtil.getDistance(this.centerX, this.centerY, x, y) < this.radius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance2D(int x, int y) {
/* 101 */     if (isInside2D(x, y))
/*     */     {
/* 103 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 107 */     return Math.abs(MathUtil.getDistance(this.centerX, this.centerY, x, y) - this.radius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance3D(int x, int y, int z) {
/* 117 */     if (isInside3D(x, y, z))
/*     */     {
/* 119 */       return 0.0D;
/*     */     }
/* 121 */     if (isInsideZ(z))
/*     */     {
/* 123 */       return getDistance2D(x, y);
/*     */     }
/*     */ 
/*     */     
/* 127 */     if (z < getMinZ())
/*     */     {
/* 129 */       return MathUtil.getDistance(this.centerX, this.centerY, getMinZ(), x, y, z);
/*     */     }
/*     */ 
/*     */     
/* 133 */     return MathUtil.getDistance(this.centerX, this.centerY, getMaxZ(), x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getClosestPoint(int x, int y) {
/* 144 */     if (isInside2D(x, y))
/*     */     {
/* 146 */       return new Point(x, y);
/*     */     }
/*     */ 
/*     */     
/* 150 */     int vX = x - this.centerX;
/* 151 */     int vY = y - this.centerY;
/* 152 */     double magV = MathUtil.getDistance(this.centerX, this.centerY, x, y);
/* 153 */     double pointX = this.centerX + vX / magV * this.radius;
/* 154 */     double pointY = this.centerY + vY / magV * this.radius;
/* 155 */     return new Point((int)Math.round(pointX), (int)Math.round(pointY));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\geometry\CylinderArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.aionemu.gameserver.model.geometry;
/*     */ 
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RectangleArea
/*     */   extends AbstractArea
/*     */ {
/*     */   private final int minX;
/*     */   private final int maxX;
/*     */   private final int minY;
/*     */   private final int maxY;
/*     */   
/*     */   public RectangleArea(Point p1, Point p2, Point p3, Point p4, int minZ, int maxZ) {
/*  71 */     super(minZ, maxZ);
/*     */     
/*  73 */     Rectangle r = new Rectangle();
/*  74 */     r.add(p1);
/*  75 */     r.add(p2);
/*  76 */     r.add(p3);
/*  77 */     r.add(p4);
/*     */     
/*  79 */     this.minX = (int)r.getMinX();
/*  80 */     this.maxX = (int)r.getMaxX();
/*  81 */     this.minY = (int)r.getMinY();
/*  82 */     this.maxY = (int)r.getMaxY();
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
/*     */   public RectangleArea(int minX, int minY, int maxX, int maxY, int minZ, int maxZ) {
/* 103 */     super(minZ, maxZ);
/* 104 */     this.minX = minX;
/* 105 */     this.maxX = maxX;
/* 106 */     this.minY = minY;
/* 107 */     this.maxY = maxY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside2D(int x, int y) {
/* 116 */     return (x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance2D(int x, int y) {
/* 125 */     if (isInside2D(x, y))
/*     */     {
/* 127 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 131 */     Point cp = getClosestPoint(x, y);
/* 132 */     return MathUtil.getDistance(x, y, cp.x, cp.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance3D(int x, int y, int z) {
/* 142 */     if (isInside3D(x, y, z))
/*     */     {
/* 144 */       return 0.0D;
/*     */     }
/* 146 */     if (isInsideZ(z))
/*     */     {
/* 148 */       return getDistance2D(x, y);
/*     */     }
/*     */ 
/*     */     
/* 152 */     Point3D cp = getClosestPoint(x, y, z);
/* 153 */     return MathUtil.getDistance(x, y, z, cp.getX(), cp.getY(), cp.getZ());
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
/* 164 */     if (isInside2D(x, y))
/*     */     {
/* 166 */       return new Point(x, y);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 171 */     Point closestPoint = MathUtil.getClosestPointOnSegment(this.minX, this.minY, this.maxX, this.minY, x, y);
/* 172 */     double distance = MathUtil.getDistance(x, y, closestPoint.x, closestPoint.y);
/*     */ 
/*     */     
/* 175 */     Point cp = MathUtil.getClosestPointOnSegment(this.minX, this.maxY, this.maxX, this.maxY, x, y);
/* 176 */     double d = MathUtil.getDistance(x, y, cp.x, cp.y);
/* 177 */     if (d < distance) {
/*     */       
/* 179 */       closestPoint = cp;
/* 180 */       distance = d;
/*     */     } 
/*     */ 
/*     */     
/* 184 */     cp = MathUtil.getClosestPointOnSegment(this.minX, this.minY, this.minX, this.maxY, x, y);
/* 185 */     d = MathUtil.getDistance(x, y, cp.x, cp.y);
/* 186 */     if (d < distance) {
/*     */       
/* 188 */       closestPoint = cp;
/* 189 */       distance = d;
/*     */     } 
/*     */ 
/*     */     
/* 193 */     cp = MathUtil.getClosestPointOnSegment(this.maxX, this.minY, this.maxX, this.maxY, x, y);
/* 194 */     d = MathUtil.getDistance(x, y, cp.x, cp.y);
/* 195 */     if (d < distance)
/*     */     {
/* 197 */       closestPoint = cp;
/*     */     }
/*     */ 
/*     */     
/* 201 */     return closestPoint;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\geometry\RectangleArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
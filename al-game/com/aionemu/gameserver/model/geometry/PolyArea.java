/*     */ package com.aionemu.gameserver.model.geometry;
/*     */ 
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.util.Collection;
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
/*     */ public class PolyArea
/*     */   extends AbstractArea
/*     */ {
/*     */   private final int[] xPoints;
/*     */   private final int[] yPoints;
/*     */   private final Polygon poly;
/*     */   
/*     */   public PolyArea(Collection<Point> points, int zMin, int zMax) {
/*  61 */     this(points.<Point>toArray(new Point[points.size()]), zMin, zMax);
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
/*     */   public PolyArea(Point[] points, int zMin, int zMax) {
/*  76 */     super(zMin, zMax);
/*     */     
/*  78 */     if (points.length < 3)
/*     */     {
/*  80 */       throw new IllegalArgumentException("Not enough points, needed at least 3 but got " + points.length);
/*     */     }
/*     */     
/*  83 */     this.xPoints = new int[points.length];
/*  84 */     this.yPoints = new int[points.length];
/*     */     
/*  86 */     Polygon polygon = new Polygon();
/*  87 */     for (int i = 0, n = points.length; i < n; i++) {
/*     */       
/*  89 */       Point p = points[i];
/*  90 */       polygon.addPoint(p.x, p.y);
/*  91 */       this.xPoints[i] = p.x;
/*  92 */       this.yPoints[i] = p.y;
/*     */     } 
/*  94 */     this.poly = polygon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside2D(int x, int y) {
/* 103 */     return this.poly.contains(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance2D(int x, int y) {
/* 112 */     if (isInside2D(x, y))
/*     */     {
/* 114 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 118 */     Point cp = getClosestPoint(x, y);
/* 119 */     return MathUtil.getDistance(cp.x, cp.y, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance3D(int x, int y, int z) {
/* 129 */     if (isInside3D(x, y, z))
/*     */     {
/* 131 */       return 0.0D;
/*     */     }
/* 133 */     if (isInsideZ(z))
/*     */     {
/* 135 */       return getDistance2D(x, y);
/*     */     }
/*     */ 
/*     */     
/* 139 */     Point3D cp = getClosestPoint(x, y, z);
/* 140 */     return MathUtil.getDistance(cp.getX(), cp.getY(), cp.getZ(), x, y, z);
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
/* 151 */     Point closestPoint = null;
/* 152 */     double closestDistance = 0.0D;
/*     */     
/* 154 */     for (int i = 0; i < this.xPoints.length; i++) {
/*     */       
/* 156 */       int nextIndex = i + 1;
/* 157 */       if (nextIndex == this.xPoints.length)
/*     */       {
/* 159 */         nextIndex = 0;
/*     */       }
/*     */       
/* 162 */       int p1x = this.xPoints[i];
/* 163 */       int p1y = this.yPoints[i];
/* 164 */       int p2x = this.xPoints[nextIndex];
/* 165 */       int p2y = this.yPoints[nextIndex];
/*     */       
/* 167 */       Point point = MathUtil.getClosestPointOnSegment(p1x, p1y, p2x, p2y, x, y);
/*     */       
/* 169 */       if (closestPoint == null) {
/*     */         
/* 171 */         closestPoint = point;
/* 172 */         closestDistance = MathUtil.getDistance(closestPoint.x, closestPoint.y, x, y);
/*     */       }
/*     */       else {
/*     */         
/* 176 */         double newDistance = MathUtil.getDistance(point.x, point.y, x, y);
/* 177 */         if (newDistance < closestDistance) {
/*     */           
/* 179 */           closestPoint = point;
/* 180 */           closestDistance = newDistance;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     return closestPoint;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\geometry\PolyArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
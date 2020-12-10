package com.aionemu.gameserver.model.geometry;

import com.aionemu.gameserver.utils.MathUtil;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Collection;














































public class PolyArea
  extends AbstractArea
{
  private final int[] xPoints;
  private final int[] yPoints;
  private final Polygon poly;
  
  public PolyArea(Collection<Point> points, int zMin, int zMax) {
    this(points.<Point>toArray(new Point[points.size()]), zMin, zMax);
  }











  
  public PolyArea(Point[] points, int zMin, int zMax) {
    super(zMin, zMax);
    
    if (points.length < 3)
    {
      throw new IllegalArgumentException("Not enough points, needed at least 3 but got " + points.length);
    }
    
    this.xPoints = new int[points.length];
    this.yPoints = new int[points.length];
    
    Polygon polygon = new Polygon();
    for (int i = 0, n = points.length; i < n; i++) {
      
      Point p = points[i];
      polygon.addPoint(p.x, p.y);
      this.xPoints[i] = p.x;
      this.yPoints[i] = p.y;
    } 
    this.poly = polygon;
  }





  
  public boolean isInside2D(int x, int y) {
    return this.poly.contains(x, y);
  }





  
  public double getDistance2D(int x, int y) {
    if (isInside2D(x, y))
    {
      return 0.0D;
    }

    
    Point cp = getClosestPoint(x, y);
    return MathUtil.getDistance(cp.x, cp.y, x, y);
  }






  
  public double getDistance3D(int x, int y, int z) {
    if (isInside3D(x, y, z))
    {
      return 0.0D;
    }
    if (isInsideZ(z))
    {
      return getDistance2D(x, y);
    }

    
    Point3D cp = getClosestPoint(x, y, z);
    return MathUtil.getDistance(cp.getX(), cp.getY(), cp.getZ(), x, y, z);
  }







  
  public Point getClosestPoint(int x, int y) {
    Point closestPoint = null;
    double closestDistance = 0.0D;
    
    for (int i = 0; i < this.xPoints.length; i++) {
      
      int nextIndex = i + 1;
      if (nextIndex == this.xPoints.length)
      {
        nextIndex = 0;
      }
      
      int p1x = this.xPoints[i];
      int p1y = this.yPoints[i];
      int p2x = this.xPoints[nextIndex];
      int p2y = this.yPoints[nextIndex];
      
      Point point = MathUtil.getClosestPointOnSegment(p1x, p1y, p2x, p2y, x, y);
      
      if (closestPoint == null) {
        
        closestPoint = point;
        closestDistance = MathUtil.getDistance(closestPoint.x, closestPoint.y, x, y);
      }
      else {
        
        double newDistance = MathUtil.getDistance(point.x, point.y, x, y);
        if (newDistance < closestDistance) {
          
          closestPoint = point;
          closestDistance = newDistance;
        } 
      } 
    } 
    
    return closestPoint;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\geometry\PolyArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

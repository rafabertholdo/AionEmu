package com.aionemu.gameserver.utils;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.geometry.Point3D;
import java.awt.Point;











































































































public class MathUtil
{
  public static double getDistance(Point point1, Point point2) {
    return getDistance(point1.x, point1.y, point2.x, point2.y);
  }















  
  public static double getDistance(int x1, int y1, int x2, int y2) {
    long dx = (x2 - x1);
    long dy = (y2 - y1);


    
    return Math.sqrt((dx * dx + dy * dy));
  }










  
  public static double getDistance(Point3D point1, Point3D point2) {
    return getDistance(point1.getX(), point1.getY(), point1.getZ(), point2.getX(), point2.getY(), point2.getZ());
  }


















  
  public static double getDistance(float x1, float y1, float z1, float x2, float y2, float z2) {
    float dx = x1 - x2;
    float dy = y1 - y2;
    float dz = z1 - z2;

    
    return Math.sqrt((dx * dx + dy * dy + dz * dz));
  }









  
  public static double getDistance(VisibleObject object, float x, float y, float z) {
    return getDistance(object.getX(), object.getY(), object.getZ(), x, y, z);
  }












  
  public static Point getClosestPointOnSegment(Point ss, Point se, Point p) {
    return getClosestPointOnSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
  }

















  
  public static Point getClosestPointOnSegment(int sx1, int sy1, int sx2, int sy2, int px, int py) {
    Point closestPoint;
    double xDelta = (sx2 - sx1);
    double yDelta = (sy2 - sy1);
    
    if (xDelta == 0.0D && yDelta == 0.0D)
    {
      throw new IllegalArgumentException("Segment start equals segment end");
    }
    
    double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

    
    if (u < 0.0D) {
      
      closestPoint = new Point(sx1, sy1);
    }
    else if (u > 1.0D) {
      
      closestPoint = new Point(sx2, sy2);
    }
    else {
      
      closestPoint = new Point((int)Math.round(sx1 + u * xDelta), (int)Math.round(sy1 + u * yDelta));
    } 
    
    return closestPoint;
  }












  
  public static double getDistanceToSegment(Point ss, Point se, Point p) {
    return getDistanceToSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
  }


















  
  public static double getDistanceToSegment(int sx1, int sy1, int sx2, int sy2, int px, int py) {
    Point closestPoint = getClosestPointOnSegment(sx1, sy1, sx2, sy2, px, py);
    return getDistance(closestPoint.x, closestPoint.y, px, py);
  }









  
  public static boolean isInRange(VisibleObject object1, VisibleObject object2, float range) {
    if (object1.getWorldId() != object2.getWorldId() || object1.getInstanceId() != object2.getInstanceId()) {
      return false;
    }
    float dx = object2.getX() - object1.getX();
    float dy = object2.getY() - object1.getY();
    return (dx * dx + dy * dy < range * range);
  }









  
  public static boolean isIn3dRange(VisibleObject object1, VisibleObject object2, float range) {
    if (object1.getWorldId() != object2.getWorldId()) {
      return false;
    }
    float dx = object2.getX() - object1.getX();
    float dy = object2.getY() - object1.getY();
    float dz = object2.getZ() - object1.getZ();
    return (dx * dx + dy * dy + dz * dz < range * range);
  }









  
  public static final float calculateAngleFrom(float obj1X, float obj1Y, float obj2X, float obj2Y) {
    float angleTarget = (float)Math.toDegrees(Math.atan2((obj2Y - obj1Y), (obj2X - obj1X)));
    if (angleTarget < 0.0F)
      angleTarget = 360.0F + angleTarget; 
    return angleTarget;
  }







  
  public static float calculateAngleFrom(VisibleObject obj1, VisibleObject obj2) {
    return calculateAngleFrom(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
  }






  
  public static final float convertHeadingToDegree(byte clientHeading) {
    float degree = (clientHeading * 3);
    return degree;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\MathUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */

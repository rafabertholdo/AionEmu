package com.aionemu.gameserver.model.geometry;

import com.aionemu.gameserver.utils.MathUtil;
import java.awt.Point;
import java.awt.Rectangle;

public class RectangleArea extends AbstractArea {
  private final int minX;
  private final int maxX;
  private final int minY;
  private final int maxY;

  public RectangleArea(Point p1, Point p2, Point p3, Point p4, int minZ, int maxZ) {
    super(minZ, maxZ);

    Rectangle r = new Rectangle();
    r.add(p1);
    r.add(p2);
    r.add(p3);
    r.add(p4);

    this.minX = (int) r.getMinX();
    this.maxX = (int) r.getMaxX();
    this.minY = (int) r.getMinY();
    this.maxY = (int) r.getMaxY();
  }

  public RectangleArea(int minX, int minY, int maxX, int maxY, int minZ, int maxZ) {
    super(minZ, maxZ);
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
  }

  public boolean isInside2D(int x, int y) {
    return (x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY);
  }

  public double getDistance2D(int x, int y) {
    if (isInside2D(x, y)) {
      return 0.0D;
    }

    Point cp = getClosestPoint(x, y);
    return MathUtil.getDistance(x, y, cp.x, cp.y);
  }

  public double getDistance3D(int x, int y, int z) {
    if (isInside3D(x, y, z)) {
      return 0.0D;
    }
    if (isInsideZ(z)) {
      return getDistance2D(x, y);
    }

    Point3D cp = getClosestPoint(x, y, z);
    return MathUtil.getDistance(x, y, z, cp.getX(), cp.getY(), cp.getZ());
  }

  public Point getClosestPoint(int x, int y) {
    if (isInside2D(x, y)) {
      return new Point(x, y);
    }

    Point closestPoint = MathUtil.getClosestPointOnSegment(this.minX, this.minY, this.maxX, this.minY, x, y);
    double distance = MathUtil.getDistance(x, y, closestPoint.x, closestPoint.y);

    Point cp = MathUtil.getClosestPointOnSegment(this.minX, this.maxY, this.maxX, this.maxY, x, y);
    double d = MathUtil.getDistance(x, y, cp.x, cp.y);
    if (d < distance) {

      closestPoint = cp;
      distance = d;
    }

    cp = MathUtil.getClosestPointOnSegment(this.minX, this.minY, this.minX, this.maxY, x, y);
    d = MathUtil.getDistance(x, y, cp.x, cp.y);
    if (d < distance) {

      closestPoint = cp;
      distance = d;
    }

    cp = MathUtil.getClosestPointOnSegment(this.maxX, this.minY, this.maxX, this.maxY, x, y);
    d = MathUtil.getDistance(x, y, cp.x, cp.y);
    if (d < distance) {
      closestPoint = cp;
    }

    return closestPoint;
  }
}
